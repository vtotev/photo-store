FROM eclipse-temurin:18-alpine
VOLUME /tmp
COPY target/*.jar PhotoStore.jar
ENTRYPOINT ["java", "-jar", "/PhotoStore.jar"]
EXPOSE 8080