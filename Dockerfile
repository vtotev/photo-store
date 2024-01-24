FROM openjdk:17-oracle
WORKDIR /app

COPY .mvn .
COPY mvnw .
COPY mvnw.cmd .

COPY src src

RUN ./mvnw package

ENTRYPOINT ["java", "-jar", "target/PhotoStore-0.0.2-SNAPSHOT.jar"]
EXPOSE 8080