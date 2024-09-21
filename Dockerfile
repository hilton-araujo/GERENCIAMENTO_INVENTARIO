FROM eclipse-temurin:17-jdk-jammy
COPY . .
RUN ./mvnw clean install -DskipTests
ENTRYPOINT [ "java", "-jav", "target/GERENCIAMENTO_INVENTARIO-0.0.1-SNAPSHOT.jar"]