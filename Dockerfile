FROM maven:3.8.5-openjdk-17 as build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean package

FROM openjdk:17-jdk-alpine

RUN apk update && \
    apk add --no-cache maven && \
    apk add --no-cache postgresql-client && \
    apk add --no-cache bash

COPY --from=build /app/target/forumHub-0.0.1-SNAPSHOT.jar /app/app.jar


WORKDIR /app

ENV POSTGRES_DB_HOST=postgres
ENV POSTGRES_DB_NAME=${POSTGRES_DB_NAME}
ENV POSTGRES_DB_USER=${POSTGRES_DB_USER}
ENV POSTGRES_DB_PASSWORD=${POSTGRES_DB_PASSWORD}
ENV JWT_API_SECRET=${JWT_API_SECRET}



ENTRYPOINT ["java", "-jar", "app.jar"]