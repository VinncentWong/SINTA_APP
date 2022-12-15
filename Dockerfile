FROM maven:eclipse-temurin AS project_manager
RUN mkdir /app
WORKDIR /app
COPY src pom.xml ./
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:19-jre-alpine
COPY --from=project_manager /app/target/sinta_app-0.0.1-SNAPSHOT-jar-with-dependencies.jar .
ENTRYPOINT ["java", "-jar", "sinta_app-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]