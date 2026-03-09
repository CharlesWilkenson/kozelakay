# Dockerfile
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Optional: healthcheck endpoint in your Spring Boot app (e.g., /actuator/health)
#HEALTHCHECK --interval=10s --timeout=3s --retries=5 CMD
# wget -qO- http://localhost:8080/actuator/health | grep '"status":"UP"' || exit 1

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
