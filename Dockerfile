FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/myapp.jar myapp.jar
ENTRYPOINT ["java", "-jar", "/myapp.jar"]
