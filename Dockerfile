# Usando uma imagem base do OpenJDK 17 (com JDK para garantir que o Spring Boot funcione corretamente)
FROM openjdk:17-jdk-slim

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiando o arquivo JAR para o contêiner
COPY target/order-service-1.0-SNAPSHOT.jar app.jar

# Expondo a porta que o Spring Boot vai rodar
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
