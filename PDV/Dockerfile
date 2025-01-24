# Etapa 1: Usar uma imagem base com o Ubuntu
FROM ubuntu:22.04 AS build

# Atualiza os pacotes e instala dependências
RUN apt-get update && apt-get install -y curl zip unzip bash git apt-transport-https

# Instala o SDKMAN!
RUN curl -s "https://get.sdkman.io" | bash

# Configura o SDKMAN! no Dockerfile
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java 21"

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY . /app

# Instala Maven
RUN apt-get install -y maven

# Compila o projeto
RUN mvn clean package -DskipTests

# Etapa 2: Imagem para execução
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho para a aplicação
WORKDIR /app

# Copia o arquivo JAR gerado
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta do aplicativo
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]
