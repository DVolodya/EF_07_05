# Используем базовый образ OpenJDK
FROM openjdk:11-jdk as build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл pom.xml и загружаем зависимости
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Запускаем приложение
FROM openjdk:11-jdk
WORKDIR /app
COPY --from=build /app/target/EF_07_05.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
