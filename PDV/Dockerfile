# Usa uma imagem oficial do OpenJDK com suporte ao Java 21
FROM eclipse-temurin:21-jdk-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o conteúdo do projeto para dentro do container
COPY . .

# Instala unzip, curl e zip (necessários para o SDKMAN)
RUN apt-get update && apt-get install -y unzip curl zip

# Instala o SDKMAN
RUN curl -s https://get.sdkman.io | bash

# Inicializa o SDKMAN e instala o Java 21
RUN /bin/bash -c "source ~/.sdkman/bin/sdkman-init.sh && sdk install java 21"

# Expõe a porta padrão da aplicação Spring Boot
EXPOSE 8080

# Comando padrão para executar o JAR
CMD ["java", "-jar", "target/PDV-0.0.1-SNAPSHOT.jar"]
