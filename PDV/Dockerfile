# Etapa 1: Usar uma imagem base com o Java 21 pré-instalado
FROM openjdk:21-jdk-slim AS build

# Atualiza os pacotes e instala dependências
RUN apt-get update && apt-get install -y curl zip unzip bash git apt-transport-https

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY . /app

# Instala o Maven
RUN apt-get install -y maven

# Compila o projeto com Maven
RUN mvn clean package -DskipTests

# Etapa 2: Imagem para execução (usando a imagem OpenJDK 21 para execução)
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho para a aplicação
WORKDIR /app

# Copia o arquivo JAR gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta do aplicativo
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]
