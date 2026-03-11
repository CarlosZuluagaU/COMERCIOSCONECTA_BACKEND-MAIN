package com.comerciosconecta.backend.service;

import com.comerciosconecta.backend.dto.FactusBillRequest;
import com.comerciosconecta.backend.dto.FactusValidationResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class FactusClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String clientId;
    private final String clientSecret;
    private final String username;
    private final String password;

    private final AtomicReference<String> cachedToken = new AtomicReference<>(null);
    private long tokenExpiryMillis = 0;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FactusClient(RestTemplate restTemplate,
                        @Value("${factus.base-url}") String baseUrl,
                        @Value("${factus.client-id}") String clientId,
                        @Value("${factus.client-secret}") String clientSecret,
                        @Value("${factus.username}") String username,
                        @Value("${factus.password}") String password) {

        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.username = username;
        this.password = password;
    }

    // ============================================================================================
    //                                   TOKEN
    // ============================================================================================
    public synchronized String getAccessToken() {
        System.out.println(" ENTRANDO A getAccessToken()");
        long now = System.currentTimeMillis();

        if (cachedToken.get() != null && now < tokenExpiryMillis) {
            return cachedToken.get();
        }

        String url = baseUrl + "/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("username", username);
        form.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(form, headers);

        try {
            ResponseEntity<JsonNode> resp =
                    restTemplate.postForEntity(url, request, JsonNode.class);

            System.out.println("🔵 RESPUESTA TOKEN FACTUS:");
            System.out.println(resp.getBody());

            if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
                throw new RuntimeException("Error obteniendo OAuth2 token de Factus: " + resp.getStatusCode());
            }

            JsonNode body = resp.getBody();

            String token = body.path("access_token").asText(null);
            if (token == null) {
                throw new RuntimeException("No se recibió access_token: " + body);
            }

            int expires = body.path("expires_in").asInt(3600);

            cachedToken.set(token);
            tokenExpiryMillis = now + (expires - 30) * 1000L;

            return token;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener token de Factus: " + e.getMessage(), e);
        }
    }

    // ============================================================================================
    //                            OBTENER RANGOS DE NUMERACIÓN
    // ============================================================================================
    public String getFirstNumberingRangeId() {
        System.out.println("️ ENTRANDO A getFirstNumberingRangeId()");
        String token = getAccessToken();

        String url = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "/v1/numbering-ranges")
                .queryParam("filter[is_active]", "1")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<JsonNode> resp = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
            JsonNode body = resp.getBody();

            System.out.println("FACTUS RESPONSE (Numbering Ranges):");

            if (body == null) {
                System.out.println("⚠️ body es NULL");
                return null;
            }

            // DEBUG: Mostrar estructura completa
            System.out.println("🔵 Estructura completa del JSON:");
            System.out.println(body.toPrettyString());

            // Extraer el array de data correctamente
            JsonNode dataArray = body.path("data").path("data");

            System.out.println("🔵 Data array isArray: " + dataArray.isArray());
            System.out.println("🔵 Data array size: " + (dataArray.isArray() ? dataArray.size() : "N/A"));

            if (!dataArray.isArray() || dataArray.isEmpty()) {
                System.out.println("⚠️ No se encontraron rangos de numeración en data.data");
                return null;
            }

            // Buscar específicamente "Factura de Venta"
            System.out.println("🔵 Buscando rango para 'Factura de Venta'...");

            for (JsonNode node : dataArray) {
                String document = node.path("document").asText("");
                String id = node.path("id").asText();
                int isActive = node.path("is_active").asInt();

                System.out.println("📄 Revisando: Document='" + document + "', ID=" + id + ", Activo=" + isActive);

                if ("Factura de Venta".equals(document.trim()) && isActive == 1) {
                    System.out.println(" ENCONTRADO: Factura de Venta con ID: " + id);
                    return id;
                }
            }

            // Si no encontró Factura de Venta, mostrar todos los disponibles
            System.out.println("️ No se encontró 'Factura de Venta'. Rangos disponibles:");
            for (JsonNode node : dataArray) {
                String document = node.path("document").asText("");
                String id = node.path("id").asText();
                int isActive = node.path("is_active").asInt();
                System.out.println("   - " + document + " (ID: " + id + ", Activo: " + isActive + ")");
            }

            // Forzar ID 8 como fallback
            System.out.println(" Usando ID 8 como fallback forzado");
            return "8";

        } catch (Exception e) {
            System.out.println("Error al obtener numbering ranges: " + e.getMessage());
            e.printStackTrace();
            // Forzar ID 8 en caso de error
            return "8";
        }
    }

    // ============================================================================================
    //                                      CREAR FACTURA
    // ============================================================================================
    public FactusValidationResponse crearFactura(FactusBillRequest requestDto) {
        String token = getAccessToken();

        String url = baseUrl + "/v1/bills/validate";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<FactusBillRequest> entity = new HttpEntity<>(requestDto, headers);

        try {
            ResponseEntity<FactusValidationResponse> resp =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            FactusValidationResponse.class
                    );

            return resp.getBody();

        } catch (HttpStatusCodeException ex) {
            System.out.println(" Error de Factus - Status: " + ex.getStatusCode());
            System.out.println(" Response Body: " + ex.getResponseBodyAsString());

            FactusValidationResponse errorResponse = new FactusValidationResponse();
            errorResponse.setStatus("ERROR");
            errorResponse.setMessage("Error de validación en Factus: " + ex.getStatusCode());

            try {
                JsonNode errorJson = objectMapper.readTree(ex.getResponseBodyAsString());

                // Manejar diferentes estructuras de error de Factus
                if (errorJson.has("errors")) {
                    Map<String, List<String>> errorsMap = objectMapper.convertValue(
                            errorJson.path("errors"),
                            new TypeReference<Map<String, List<String>>>() {}
                    );
                    errorResponse.setErrors(errorsMap);
                } else if (errorJson.has("data") && errorJson.path("data").has("errors")) {
                    Map<String, List<String>> errorsMap = objectMapper.convertValue(
                            errorJson.path("data").path("errors"),
                            new TypeReference<Map<String, List<String>>>() {}
                    );
                    errorResponse.setErrors(errorsMap);
                } else if (errorJson.has("message")) {
                    errorResponse.setMessage(errorJson.path("message").asText());
                }

            } catch (Exception e) {
                errorResponse.setMessage("Error no JSON de Factus: " + ex.getResponseBodyAsString());
            }

            return errorResponse;
        } catch (Exception e) {
            System.out.println(" Error inesperado al crear factura: " + e.getMessage());

            FactusValidationResponse errorResponse = new FactusValidationResponse();
            errorResponse.setStatus("ERROR");
            errorResponse.setMessage("Error inesperado: " + e.getMessage());
            return errorResponse;
        }
    }
}