# Usar a imagem base do Ubuntu
FROM ubuntu:jammy

# Definir variáveis de ambiente para evitar perguntas durante a instalação
ENV DEBIAN_FRONTEND=noninteractive

# Atualizar pacotes e instalar dependências necessárias (curl, unzip)
RUN apt-get update && apt-get install -y curl unzip tar

# Definir versão do Java que você quer instalar
ENV JAVA_VERSION 21

# Baixar e instalar o OpenJDK 21 manualmente
RUN curl -L https://download.java.net/java/ga/jdk21/36/GPL/openjdk-21_linux-x64_bin.tar.gz -o /tmp/openjdk-21.tar.gz && \
    mkdir -p /usr/lib/jvm && \
    tar -xvzf /tmp/openjdk-21.tar.gz -C /usr/lib/jvm && \
    rm /tmp/openjdk-21.tar.gz

# Definir a variável de ambiente JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/jdk-21

# Adicionar o Java ao PATH
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Verificar a instalação do Java
RUN java -version

# Configuração do diretório de trabalho
WORKDIR /app

# Definir o comando para iniciar o contêiner (exemplo)
# CMD ["java", "-jar", "seu-arquivo.jar"]

# Expor a porta que o aplicativo irá usar
# EXPOSE 8080
