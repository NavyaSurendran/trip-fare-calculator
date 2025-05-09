FROM gradle:8.7-jdk17 AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts /app/
COPY src /app/src
COPY src/main/resources/taps.csv /app/
RUN gradle build --no-daemon
CMD ["gradle", "run", "--no-daemon"]
