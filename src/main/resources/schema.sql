-- Criação da tabela de usuários
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    nome VARCHAR(100),
    senha VARCHAR(100)
);

-- Criação da tabela de estoque
CREATE TABLE IF NOT EXISTS estoque (
    id BIGINT PRIMARY KEY,
    codigo_Produto INT NOT NULL UNIQUE,
    produto VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    quantidade_Estoque INT NOT NULL
);
