FROM eclipse-temurin:17
LABEL maintainer="valbertth"
WORKDIR /app
COPY target/docker-0.0.1-SNAPSHOT.jar /app/aula-docker.jar
ENTRYPOINT ["java","-jar", "aula-docker.jar"]