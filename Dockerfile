FROM openjdk:17-oracle

WORKDIR ./app

COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .

COPY src src
RUN chmod +x mvnw
RUN ./mvnw package -Dtest=!softuni.photostore.PhotoStoreApplicationTests

ENTRYPOINT ["java", "-jar", "target/PhotoStore-0.0.2-SNAPSHOT.jar"]
EXPOSE 8080