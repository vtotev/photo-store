FROM openjdk:18
VOLUME /tmp
COPY target/*.jar PhotoStore.jar
ENTRYPOINT ["java", "-jar", "/PhotoStore.jar"]
EXPOSE 8080