# Etapa 1: Imagem base para compilação
FROM openjdk:21-jdk-slim AS build

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de configuração e dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código-fonte para o container
COPY src ./src

# Compila o projeto
RUN mvn package -DskipTests

## Use uma imagem base leve
FROM ubuntu:22.04

# Atualiza os pacotes e instala dependências
RUN apt-get update && apt-get install -y curl zip unzip bash git

# Instala o SDKMAN!
RUN curl -s "https://get.sdkman.io" | bash && \
    bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java 21"

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY . /app

# Instala Maven
RUN apt-get install -y maven

# Compila o projeto
RUN mvn clean package -DskipTests

# Expõe a porta do aplicativo
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "target/PDV-0.0.1-SNAPSHOT.jar"]
