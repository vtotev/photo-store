FROM eclipse-temurin:18-alpine
VOLUME /tmp
COPY target/*.jar PhotoStore-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/PhotoStore-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080