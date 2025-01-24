# Usar a imagem base do Ubuntu
FROM ubuntu:22.04

# Instalar pacotes essenciais
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    zip \
    bash \
    && apt-get clean

# Baixar e instalar o OpenJDK 21 diretamente do site oficial
RUN curl -sSL https://download.java.net/java/ga/jdk21/13/GPL/openjdk-21_linux-x64_bin.tar.gz -o /tmp/openjdk-21.tar.gz

# Criar o diretório de instalação e descompactar o JDK
RUN mkdir -p /opt/jdk && \
    tar -xzf /tmp/openjdk-21.tar.gz -C /opt/jdk --strip-components=1

# Configurar variáveis de ambiente para o Java
ENV JAVA_HOME=/opt/jdk
ENV PATH=$JAVA_HOME/bin:$PATH

# Verificar se o Java foi instalado corretamente
RUN java -version

# Definir o comando de inicialização
CMD ["bash"]
