-- src/main/resources/import.sql

-- DELETE existing data (optional, but good for clean restarts in dev)
DELETE FROM ItemPedido;
DELETE FROM Pagamento;
DELETE FROM Pedido;
DELETE FROM Betoneira;
DELETE FROM Endereco; -- Delete addresses before clients due to foreign key constraints
DELETE FROM Cliente;
DELETE FROM Fabricante;
DELETE FROM TipoBetoneira;

-- Insert Fabricante (removido o id explícito)
INSERT INTO Fabricante (nome) VALUES ('Fabricante A');
INSERT INTO Fabricante (nome) VALUES ('Fabricante B');

-- Insert TipoBetoneira (removido o id explícito)
INSERT INTO TipoBetoneira (nome) VALUES ('Pequena');
INSERT INTO TipoBetoneira (nome) VALUES ('Média');
INSERT INTO TipoBetoneira (nome) VALUES ('Grande');

-- Insert Betoneira (Products) - removido o id explícito e corrigido o campo 'tipo'
-- Agora usando 'tipo' com o nome da enumeração como string, em vez de tipoBetoneira_id
INSERT INTO Betoneira (nome, preco, quantidadeEstoque, fabricante_id, tipo) VALUES ('Betoneira Compacta', 1500.00, 10, 1, 'MISTURADOR');
INSERT INTO Betoneira (nome, preco, quantidadeEstoque, fabricante_id, tipo) VALUES ('Betoneira Profissional', 3000.00, 5, 1, 'CONCRETO');
INSERT INTO Betoneira (nome, preco, quantidadeEstoque, fabricante_id, tipo) VALUES ('Super Betoneira', 5000.00, 2, 2, 'PORTATIL'); -- Mapeando id 3 de TipoBetoneiraEnum
INSERT INTO Betoneira (nome, preco, quantidadeEstoque, fabricante_id, tipo) VALUES ('Mini Betoneira', 800.00, 20, 2, 'MISTURADOR');

-- Insert Cliente (User) - IMPORTANT for security testing (removido o id explícito)
-- Senhas já foram atualizadas anteriormente.
INSERT INTO Cliente (nome, email, senha, cpf, telefone, role) VALUES ('João Silva', 'joao@example.com', '$2a$10$H3NFbBEFjreeSnMcGXCoqOinImxlWXXa5VaOVAkmfmayEMprQIyZC', '111.222.333-44', '63999998888', 'USER');
INSERT INTO Cliente (nome, email, senha, cpf, telefone, role) VALUES ('Maria Admin', 'admin@example.com', '$2a$10$98NdXT9.EmWPuPBnsvAfxeIhumDh70TE5v2lwb3blsk7d4jfWsqgC', '555.666.777-88', '63988887777', 'ADMIN');

-- Insert Enderecos (Addresses) - Linked to Clients (removido o id explícito)
-- Os IDs de cliente são gerados automaticamente pelo banco de dados na ordem de inserção.
INSERT INTO Endereco (logradouro, numero, bairro, cep, cliente_id) VALUES ('Rua A', '123', 'Centro', '77000-000', 1);
INSERT INTO Endereco (logradouro, numero, bairro, cep, cliente_id) VALUES ('Av. B', '456', 'Jardim', '77000-001', 2);


-- Insert Pedido - Corrigido os nomes das colunas de FK para corresponderem ao modelo (cliente_id, endereco_entrega_id)
INSERT INTO Pedido (dataDoPedido, statusPedido, totalPedido, cliente_id, endereco_entrega_id) VALUES ('2025-05-01 10:00:00', 'PAGO', 1500.00, 1, 1);
INSERT INTO Pedido (dataDoPedido, statusPedido, totalPedido, cliente_id, endereco_entrega_id) VALUES ('2025-05-02 11:30:00', 'PENDENTE', 3000.00, 1, 1);
INSERT INTO Pedido (dataDoPedido, statusPedido, totalPedido, cliente_id, endereco_entrega_id) VALUES ('2025-05-03 14:00:00', 'PAGO', 5000.00, 2, 2);

-- Insert ItemPedido - Corrigido os nomes das colunas de FK e a ordem, além de remover o id explícito
-- betoneira_id e pedido_id agora referenciam os IDs gerados automaticamente pelas tabelas Betoneira e Pedido.
INSERT INTO ItemPedido (quantidade, precoUnitario, betoneira_id, pedido_id) VALUES (1, 1500.00, 1, 1);
INSERT INTO ItemPedido (quantidade, precoUnitario, betoneira_id, pedido_id) VALUES (1, 3000.00, 2, 2);
INSERT INTO ItemPedido (quantidade, precoUnitario, betoneira_id, pedido_id) VALUES (1, 5000.00, 3, 3);
INSERT INTO ItemPedido (quantidade, precoUnitario, betoneira_id, pedido_id) VALUES (1, 800.00, 4, 1);


-- Insert Pagamento - Corrigido o nome da coluna de FK para corresponder ao modelo (pedido_id)
INSERT INTO Pagamento (dataPagamento, status, tipoPagamento, valor, pedido_id) VALUES ('2025-05-01 10:05:00', 'APROVADO', 'CARTAO_CREDITO', 1500.00, 1);
INSERT INTO Pagamento (dataPagamento, status, tipoPagamento, valor, pedido_id) VALUES ('2025-05-03 14:10:00', 'APROVADO', 'BOLETO', 5000.00, 3);