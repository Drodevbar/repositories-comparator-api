FROM maven:3-jdk-11-slim

EXPOSE 9090

WORKDIR /app

COPY . .

RUN mvn package

RUN mv ./target/*.jar app.jar

RUN chmod 777 app.jar

ENTRYPOINT ["java","-jar","app.jar"]

