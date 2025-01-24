# Usar uma imagem base oficial do OpenJDK com suporte ao Java 21
FROM eclipse-temurin:21-jdk-jammy

# Atualizar e instalar dependências necessárias
RUN apt-get update && apt-get install -y unzip curl zip

# Mensagem para verificar o progresso
RUN echo "Dependências instaladas com sucesso!"

# Instalar o SDKMAN
RUN curl -s https://get.sdkman.io | bash

# Configurar o SDKMAN no ambiente
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java 21"

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos da aplicação
COPY . .

# Expor a porta usada pela aplicação
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "target/PDV-0.0.1-SNAPSHOT.jar"]
