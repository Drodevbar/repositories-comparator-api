FROM maven:3-jdk-11-slim

EXPOSE 9090

WORKDIR /repositories-comparator-api

COPY . .

RUN mvn package -q && \
    mv ./target/*.jar app.jar && \
    chmod 777 app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

