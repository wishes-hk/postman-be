# Builder Stage
FROM openjdk:17-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Execution Stage
FROM openjdk:17-jdk-alpine
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Do not use root user to run the app
RUN addgroup --system appuser && adduser -S -s /bin/false -G appuser appuser
RUN chown -R appuser:appuser /app
USER appuser
ENTRYPOINT ["java","-cp","app:app/lib/*","com.wishes.postmanbe.PostmanBeApplication"]
