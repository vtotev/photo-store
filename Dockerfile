FROM oracle:17
VOLUME /tmp
COPY target/*.jar PhotoStore-0.0.2-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/PhotoStore-0.0.2-SNAPSHOT.jar"]
EXPOSE 8080