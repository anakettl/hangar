FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY gradlew .
COPY gradle gradle

RUN chmod +x gradlew

CMD ["./gradlew", "bootRun"]