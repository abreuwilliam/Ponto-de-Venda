# Usar a imagem base do Ubuntu
FROM ubuntu:jammy

# Definir variáveis de ambiente para evitar perguntas durante a instalação
ENV DEBIAN_FRONTEND=noninteractive

# Atualizar pacotes e instalar dependências necessárias (unzip, curl, zip)
RUN apt-get update && apt-get install -y unzip curl zip

# Baixar e instalar o SDKMAN
RUN curl -s https://get.sdkman.io | bash

# Configurar o SDKMAN
RUN echo "source ~/.sdkman/bin/sdkman-init.sh" >> ~/.bashrc

# Instalar a versão do Java desejada com SDKMAN
RUN bash -c "source ~/.bashrc && sdk install java 21"

# Verificar a instalação do Java
RUN bash -c "source ~/.bashrc && java -version"

# Configuração do diretório de trabalho (opcional, mas útil)
WORKDIR /app

# Copiar o código da aplicação para o contêiner
# COPY . /app

# Definir o comando para iniciar o contêiner
# CMD ["java", "-jar", "seu-arquivo.jar"]

# Expor a porta que o aplicativo irá usar
# EXPOSE 8080
