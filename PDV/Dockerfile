FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

RUN apt-get update && apt-get install -y unzip curl && rm -rf /var/lib/apt/lists/*

RUN curl -s https://get.sdkman.io | bash \
    && bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java 21"

# Copiar os arquivos do projeto para o contêiner
COPY . .

# Garantir permissões de execução para o Maven Wrapper
RUN chmod +x /app/mvnw

# Construir o projeto
RUN --mount=type=cache,target=/root/.m2/repository ./mvnw clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiar o JAR compilado
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
