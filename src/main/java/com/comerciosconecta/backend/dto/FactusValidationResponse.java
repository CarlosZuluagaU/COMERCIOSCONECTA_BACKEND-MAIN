package com.comerciosconecta.backend.dto;



import java.util.List;
import java.util.Map;

public class FactusValidationResponse {

    private String status;
    private String message;

    private Map<String, Object> data; // Factus a veces envía mapa general

    private Map<String, List<String>> errors; // Cuando hay Validation error

    // Constructor vacío
    public FactusValidationResponse() {
    }

    // Constructor completo
    public FactusValidationResponse(String status, String message,
                                    Map<String, Object> data,
                                    Map<String, List<String>> errors) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    // Getters y Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }
}

