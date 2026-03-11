#FROM eclipse-temurin:17-jdk
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

# Usa una imagen base con JDK 17
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml
COPY src src

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

# Etapa final
FROM eclipse-temurin:17-jre-jammy  

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Variable para el perfil activo
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-prod}


CMD ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "app.jar"]




