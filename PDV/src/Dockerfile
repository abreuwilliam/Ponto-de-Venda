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

# Etapa 2: Imagem para execução
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho para a aplicação
WORKDIR /app

# Copia o arquivo JAR gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta em que a aplicação será executada
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]
