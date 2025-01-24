# Usa uma imagem oficial do OpenJDK com suporte ao Java 21
FROM eclipse-temurin:21-jdk-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o conteúdo do projeto para dentro do container
COPY . .

# Expõe a porta padrão da aplicação Spring Boot (se configurada no seu projeto)
EXPOSE 8080

# Comando padrão para executar o JAR
CMD ["java", "-jar", "target/PDV-0.0.1-SNAPSHOT.jar"]
