# Imagem base
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY . /app/

# Exclui arquivos temporários desnecessários
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Construção do projeto (se necessário)
# Adicione comandos específicos para compilar seu projeto

# Define o comando de execução padrão
CMD ["java", "-jar", "target/PDV-0.0.1-SNAPSHOT.jar"]
