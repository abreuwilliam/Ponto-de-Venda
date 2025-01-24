# Etapa 1: Usar uma imagem base com o Ubuntu
FROM ubuntu:22.04 AS build

# Atualiza os pacotes e instala dependências
RUN apt-get update && apt-get install -y curl zip unzip bash git apt-transport-https openjdk-21-jdk maven

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY . /app

# Compila o projeto com Maven
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
