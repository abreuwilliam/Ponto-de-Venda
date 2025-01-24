# Usar uma imagem base oficial do OpenJDK com suporte ao Java 21
FROM eclipse-temurin:21-jdk-jammy

# Mensagem para garantir que o Dockerfile está sendo lido
RUN echo "Dockerfile está sendo lido com sucesso!" 

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar todo o conteúdo do diretório atual para o diretório /app no container
COPY . .

# Instalar dependências adicionais (se necessário)
RUN apt-get update && apt-get install -y curl unzip

# Expõe a porta 8080 do container (caso a aplicação utilize essa porta)
EXPOSE 8080

# Mensagem para verificar se a etapa de instalação de pacotes foi concluída
RUN echo "Dependências instaladas!"

# Comando para compilar a aplicação usando Maven (caso você use Maven)
RUN ./mvnw clean package -DskipTests

# Comando para iniciar a aplicação Java com o JAR gerado (ajustar o nome do JAR conforme necessário)
CMD ["java", "-jar", "target/PDV-0.0.1-SNAPSHOT.jar"]
