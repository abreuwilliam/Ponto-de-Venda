# Usando uma imagem base adequada para Java e Maven
FROM eclipse-temurin:17-jdk AS build

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# Instalar dependências necessárias
RUN apt-get update && apt-get install -y unzip curl && rm -rf /var/lib/apt/lists/*

# Instalar o SDKMAN e Java
RUN curl -s https://get.sdkman.io | bash \
    && bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java 21"

# Copiar todos os arquivos do projeto para o contêiner
COPY . .

# Garantir que o script mvnw tenha permissão de execução
RUN chmod +x mvnw

# Executar o comando Maven para compilar o projeto
RUN --mount=type=cache,target=/root/.m2/repository ./mvnw clean package -DskipTests

# Fase final: criar imagem para execução do aplicativo
FROM eclipse-temurin:17-jre

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# Copiar o artefato gerado da fase de construção
COPY --from=build /app/target/*.jar app.jar

# Expor a porta padrão do aplicativo
EXPOSE 8080

# Comando para iniciar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
