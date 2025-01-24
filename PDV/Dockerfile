# Use uma imagem base do Ubuntu
FROM ubuntu:22.04

# Definir o diretório de trabalho
WORKDIR /app

# Instalar pacotes necessários
RUN apt-get update && \
    apt-get install -y \
    curl \
    unzip \
    zip \
    bash \
    && apt-get clean

# Baixar o JDK 21 diretamente do site oficial
RUN curl -LO https://download.java.net/java/ga/jdk21/13/GPL/openjdk-21_linux-x64_bin.tar.gz

# Descompactar o JDK
RUN tar -xvf openjdk-21_linux-x64_bin.tar.gz -C /opt/

# Configurar variáveis de ambiente para o Java
ENV JAVA_HOME=/opt/jdk-21
ENV PATH=$JAVA_HOME/bin:$PATH

# Verificar se o Java foi instalado corretamente
RUN java -version

# Exibir uma mensagem de sucesso
RUN echo "Java instalado manualmente com sucesso"

# Definir o comando padrão
CMD ["bash"]
