FROM openjdk:18-jdk as builder

WORKDIR /app/msvc-account

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
COPY --from=builder /app/msvc-account/target/msvc-account-0.0.1-SNAPSHOT.jar .
EXPOSE 8001

CMD ["java", "-jar", "msvc-account-0.0.1-SNAPSHOT.jar"]