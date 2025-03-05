FROM gradle:jdk21-corretto AS builder
WORKDIR /app
ARG SERVICE_NAME="patient-service"
COPY ./${SERVICE_NAME} ./${SERVICE_NAME}
COPY ./bom ./bom
COPY ./gradle ./gradle
COPY ./settings.gradle .
RUN ["/bin/sh", "-c", "gradle clean ${SERVICE_NAME}:bootJar"]

FROM amazoncorretto:21-alpine3.21
WORKDIR /app
ARG SERVICE_NAME="patient-service"
ARG SERVICE_VERSION="1.0-SNAPSHOT.jar"
COPY --from=builder /app/${SERVICE_NAME}/build/libs/${SERVICE_NAME}-${SERVICE_VERSION} ./app.jar
ENTRYPOINT ["/bin/sh", "-c", "java ${JAVA_OPTS} -jar ./app.jar"]
