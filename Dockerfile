FROM eclipse-temurin:21-jdk AS build


WORKDIR /app

RUN apt-get update && apt-get install -y unzip curl && rm -rf /var/lib/apt/lists/*

# SDKMAN não é necessário se o Eclipse Temurin já está na versão certa
# Você já está usando eclipse-temurin:17-jdk, então pode remover essas linhas
# RUN curl -s https://get.sdkman.io | bash \
#     && bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java 21"

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:21-jre


WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
