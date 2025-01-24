# Imagem base com OpenJDK 21
FROM eclipse-temurin:21-jdk-jammy AS build

# Diretório de trabalho
WORKDIR /app

# Copiar os arquivos do projeto para o container
COPY . /app

# Compilar o projeto com Maven (opcional: ajustar para seu projeto)
RUN ./mvnw clean package -DskipTests

# Segunda fase: imagem para execução
FROM eclipse-temurin:21-jre-jammy

# Diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR gerado da fase anterior
COPY --from=build /app/target/*.jar app.jar

# Expor a porta padrão do aplicativo
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]
