FROM openjdk:17-oracle
WORKDIR /app

RUN apt-get update && \
    apt-get install -y maven && \
    mvn dependency:go-offline

COPY src src

RUN mvn package

ENTRYPOINT ["java", "-jar", "target/PhotoStore-0.0.2-SNAPSHOT.jar"]
EXPOSE 8080