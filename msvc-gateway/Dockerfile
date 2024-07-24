FROM openjdk:18-jdk as builder

WORKDIR /app/msvc-gateway

COPY ./pom.xml ./
COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .

RUN ./mvnw dependency:go-offline

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:18-jdk

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/msvc-gateway/target/msvc-gateway-0.0.1-SNAPSHOT.jar .
EXPOSE 8090

CMD ["java", "-jar", "msvc-gateway-0.0.1-SNAPSHOT.jar"]