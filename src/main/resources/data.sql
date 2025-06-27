-- Inserir usuário admin

INSERT INTO usuario (id, username, password, role)
VALUES (1, 'william_admin', '$2a$12$jeTsXeTVrNwhFyxzvTY52OEBCmgmkW7/UC2sqIzXYGSMhHUsYHuK.', 'ADMIN');

INSERT INTO usuario (id, username, password, role)
VALUES (2, 'william_user', '$2a$12$jeTsXeTVrNwhFyxzvTY52OEBCmgmkW7/UC2sqIzXYGSMhHUsYHuK.', 'USER');

-- Inserir produtos no estoque
INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (1, 2, 'caneta', 3.80, 5);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (2, 3, 'caderno de materia', 5.89, 0);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (3, 4, 'caderno de desenho', 7.50, 10);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (4, 5, 'lapis', 1.50, 20);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (5, 6, 'borracha', 0.99, 15);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (6, 7, 'régua', 2.50, 8);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (7, 8, 'tesoura', 4.20, 12);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (8, 9, 'cola', 3.00, 6);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (9, 10, 'marcador', 2.80, 9);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (10, 11, 'papel sulfite', 1.20, 25);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (11, 12, 'pastas', 3.50, 4);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (12, 13, 'clipes', 0.50, 30);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (13, 14, 'fita adesiva', 1.80, 7);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (14, 15, 'marca texto', 2.20, 11);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (15, 16, 'papel cartolina', 2.00, 5);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (16, 17, 'papel crepom', 1.50, 8);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (17, 18, 'papel colorido', 1.70, 10);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (18, 19, 'papel adesivo', 2.90, 6);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (19, 20, 'papel fotográfico', 3.50, 3);

INSERT INTO estoque (id, codigo_Produto, produto, preco, quantidade_Estoque)
VALUES (20, 21, 'papel cartão', 2.40, 9);
