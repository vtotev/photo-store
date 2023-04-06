FROM eclipe-temurin:17jdk-alpine
VOLUME /tmp
COPY target/*.jar PhotoStore.jar
ENTRYPOINT ["java", "-jar", "/PhotoStore.jar"]
EXPOSE 8080