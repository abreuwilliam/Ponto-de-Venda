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
    bash \
    && apt-get clean

# Instalar o SDKMAN
RUN curl -s "https://get.sdkman.io" | bash

# Definir variáveis de ambiente
ENV SDKMAN_DIR=/root/.sdkman
ENV PATH=$SDKMAN_DIR/bin:$PATH

# Instalar o Java via SDKMAN
RUN bash -c "source $SDKMAN_DIR/bin/sdkman-init.sh && sdk install java 21"

# Verificar se o Java foi instalado corretamente
RUN java -version

# Exibir uma mensagem de sucesso
RUN echo "Java e SDKMAN instalados com sucesso"

# Definir o comando padrão
CMD ["bash"]
