DELETE FROM ItemPedido;
DELETE FROM Pagamento;
DELETE FROM Pedido;
DELETE FROM Betoneira;
DELETE FROM Endereco;
DELETE FROM Cliente;
DELETE FROM Fabricante;
DELETE FROM TipoBetoneira;

INSERT INTO Fabricante (nome) VALUES ('Fabricante A');
INSERT INTO Fabricante (nome) VALUES ('Fabricante B');

INSERT INTO TipoBetoneira (nome) VALUES ('Pequena');
INSERT INTO TipoBetoneira (nome) VALUES ('Média');
INSERT INTO TipoBetoneira (nome) VALUES ('Grande');

INSERT INTO Betoneira (nome, preco, quantidadeEstoque, fabricante_id, tipo, nomeImagem) VALUES ('Betoneira Compacta', 1500.00, 10, 1, 'MISTURADOR', '5a286fef14a61c657a3288baaac472ec.jpeg');
INSERT INTO Betoneira (nome, preco, quantidadeEstoque, fabricante_id, tipo, nomeImagem) VALUES ('Betoneira Profissional', 3000.00, 5, 1, 'CONCRETO', '60ca2974b3fae3dd7b0d6427ba779f70.jpeg');
INSERT INTO Betoneira (nome, preco, quantidadeEstoque, fabricante_id, tipo, nomeImagem) VALUES ('Super Betoneira', 5000.00, 2, 2, 'PORTATIL', '9e810b34fa7147b84fcf4fcd1eea2229.jpeg');
INSERT INTO Betoneira (nome, preco, quantidadeEstoque, fabricante_id, tipo, nomeImagem) VALUES ('Mini Betoneira', 800.00, 20, 2, 'MISTURADOR', 'aeac8c814ee90cca4a268804bb0d1e7d.jpeg');

INSERT INTO Administrador (username, email, senha, cpf) VALUES ('admin1', 'joao@example.com', '$2a$10$H3NFbBEFjreeSnMcGXCoqOinImxlWXXa5VaOVAkmfmayEMprQIyZC', '111.222.333-44');

INSERT INTO Cliente (nome, email, senha, cpf) VALUES ('joao', 'joao@example.com', '$2a$10$H3NFbBEFjreeSnMcGXCoqOinImxlWXXa5VaOVAkmfmayEMprQIyZC', '111.222.333-44';
INSERT INTO Cliente (nome, email, senha, cpf) VALUES ('maria', 'admin@example.com', '$2a$10$98NdXT9.EmWPuPBnsvAfxeIhumDh70TE5v2lwb3blsk7d4jfWsqgC', '555.666.777-88');

INSERT INTO Endereco (logradouro, numero, bairro, cep, cliente_id) VALUES ('Rua A', '123', 'Centro', '77000-000', 1);
INSERT INTO Endereco (logradouro, numero, bairro, cep, cliente_id) VALUES ('Av. B', '456', 'Jardim', '77000-001', 2);

INSERT INTO Pedido (dataDoPedido, statusPedido, totalPedido, cliente_id, endereco_entrega_id) VALUES ('2025-05-01 10:00:00', 'PAGO', 1500.00, 1, 1);
INSERT INTO Pedido (dataDoPedido, statusPedido, totalPedido, cliente_id, endereco_entrega_id) VALUES ('2025-05-02 11:30:00', 'PENDENTE', 3000.00, 1, 1);
INSERT INTO Pedido (dataDoPedido, statusPedido, totalPedido, cliente_id, endereco_entrega_id) VALUES ('2025-05-03 14:00:00', 'PAGO', 5000.00, 2, 2);

INSERT INTO ItemPedido (quantidade, precoUnitario, betoneira_id, pedido_id) VALUES (1, 1500.00, 1, 1);
INSERT INTO ItemPedido (quantidade, precoUnitario, betoneira_id, pedido_id) VALUES (1, 3000.00, 2, 2);
INSERT INTO ItemPedido (quantidade, precoUnitario, betoneira_id, pedido_id) VALUES (1, 5000.00, 3, 3);
INSERT INTO ItemPedido (quantidade, precoUnitario, betoneira_id, pedido_id) VALUES (1, 800.00, 4, 1);

INSERT INTO Pagamento (dataPagamento, status, tipoPagamento, valor, pedido_id) VALUES ('2025-05-01 10:05:00', 'APROVADO', 'CARTAO_CREDITO', 1500.00, 1);
INSERT INTO Pagamento (dataPagamento, status, tipoPagamento, valor, pedido_id) VALUES ('2025-05-03 14:10:00', 'APROVADO', 'BOLETO', 5000.00, 3);