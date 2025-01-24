# Use uma imagem base do Ubuntu
FROM ubuntu:22.04

# Definir o diretório de trabalho
WORKDIR /app

# Atualizar pacotes e instalar dependências necessárias
RUN apt-get update && \
    apt-get install -y \
    unzip \
    curl \
    zip \
    openjdk-21-jdk \
    bash \
    && apt-get clean

# Verificar se o Java foi instalado corretamente
RUN java -version

# Exibir uma mensagem de sucesso
RUN echo "Java instalado com sucesso sem o SDKMAN"

# Definir o comando padrão
CMD ["bash"]
