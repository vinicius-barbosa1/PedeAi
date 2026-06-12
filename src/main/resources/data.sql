-- ============================================================
-- data.sql — PedeAí
-- Nomes de tabelas e colunas em snake_case (gerado pelo Hibernate)
-- ============================================================

-- ============================================================
-- CATEGORIAS DE PRODUTO
-- ============================================================
INSERT INTO categoria_produto (nome, descricao) VALUES
    ('Lanches',    'Hambúrgueres, sanduíches e wraps artesanais'),
    ('Pizzas',     'Pizzas tradicionais e especiais em diversos sabores'),
    ('Bebidas',    'Refrigerantes, sucos, águas e bebidas especiais'),
    ('Sobremesas', 'Doces, sorvetes e opções de fim de refeição'),
    ('Saudáveis',  'Opções leves, saladas e pratos com baixo teor calórico');

-- ============================================================
-- PRODUTOS
-- ============================================================

-- Lanches (categoria_produto_id = 1)
INSERT INTO produto (nome, descricao, preco, disponivel, categoria_produto_id) VALUES
    ('X-Burguer Clássico',   'Pão, carne 150g, queijo, alface e tomate',             18.90, true,  1),
    ('X-Bacon Duplo',        'Pão, duas carnes 150g, bacon, queijo e molho especial', 29.90, true,  1),
    ('Wrap Frango Grelhado', 'Frango grelhado, cream cheese, alface e tomate',        22.50, true,  1),
    ('X-Veggie',             'Hambúrguer de grão-de-bico, queijo e legumes frescos',  24.00, false, 1);

-- Pizzas (categoria_produto_id = 2)
INSERT INTO produto (nome, descricao, preco, disponivel, categoria_produto_id) VALUES
    ('Pizza Margherita', 'Molho de tomate, mussarela e manjericão fresco',     39.90, true,  2),
    ('Pizza Pepperoni',  'Molho de tomate, mussarela e pepperoni fatiado',     44.90, true,  2),
    ('Pizza Portuguesa', 'Presunto, mussarela, ovos, cebola e azeitonas',      42.90, false, 2);

-- Bebidas (categoria_produto_id = 3)
INSERT INTO produto (nome, descricao, preco, disponivel, categoria_produto_id) VALUES
    ('Coca-Cola Lata 350ml',  'Refrigerante gelado',                             6.00, true,  3),
    ('Suco de Laranja 500ml', 'Suco natural espremido na hora',                 10.00, true,  3),
    ('Água Mineral 500ml',    'Água sem gás',                                    4.00, true,  3),
    ('Milkshake Chocolate',   'Milkshake cremoso de chocolate com chantilly',   16.00, false, 3);

-- Sobremesas (categoria_produto_id = 4)
INSERT INTO produto (nome, descricao, preco, disponivel, categoria_produto_id) VALUES
    ('Brownie com Sorvete', 'Brownie quente com bola de sorvete de creme', 14.90, true, 4),
    ('Petit Gâteau',        'Bolinho quente de chocolate com sorvete',     18.00, true, 4);

-- Saudáveis (categoria_produto_id = 5)
INSERT INTO produto (nome, descricao, preco, disponivel, categoria_produto_id) VALUES
    ('Salada Caesar', 'Alface romana, croutons, parmesão e molho caesar', 21.00, true, 5),
    ('Bowl Proteico', 'Frango, arroz integral, brócolis e molho shoyu',   27.00, true, 5);

-- ============================================================
-- FORMAS DE PAGAMENTO
-- ============================================================
INSERT INTO forma_pagamento (nome, descricao) VALUES
    ('Cartão de Crédito', 'Pagamento via cartão de crédito, parcelamento disponível'),
    ('Cartão de Débito',  'Pagamento via cartão de débito à vista'),
    ('Pix',               'Transferência instantânea via chave Pix'),
    ('Dinheiro',          'Pagamento em espécie no momento da entrega');

-- ============================================================
-- CLIENTES
-- ============================================================
INSERT INTO cliente (nome, cpf, telefone, email) VALUES
    ('Ana Lima',        '12345678901', '79900000001', 'ana.lima@email.com'),
    ('Bruno Souza',     '23456789012', '79900000002', 'bruno.souza@email.com'),
    ('Carla Mendes',    '34567890123', '79900000003', 'carla.mendes@email.com'),
    ('Daniel Ferreira', '45678901234', '79900000004', 'daniel.ferreira@email.com'),
    ('Elisa Rocha',     '56789012345', '79900000005', 'elisa.rocha@email.com');
-- Elisa: cliente sem pedidos (cobre esse caso de uso)

-- ============================================================
-- ENDEREÇOS
-- ============================================================

-- Ana (cliente_id = 1): 2 endereços
INSERT INTO endereco (endereco, numero, complemento, bairro, cidade, estado, cep, cliente_id) VALUES
    ('Rua das Flores', 100, 'Apto 12', 'Centro',        'Aracaju', 'SE', '49000001', 1),
    ('Av. Beira Mar',  350, NULL,      'Coroa do Meio', 'Aracaju', 'SE', '49025010', 1);

-- Bruno (cliente_id = 2)
INSERT INTO endereco (endereco, numero, complemento, bairro, cidade, estado, cep, cliente_id) VALUES
    ('Rua Laranjeiras', 22, NULL, 'Salgado Filho', 'Aracaju', 'SE', '49080100', 2);

-- Carla (cliente_id = 3)
INSERT INTO endereco (endereco, numero, complemento, bairro, cidade, estado, cep, cliente_id) VALUES
    ('Travessa das Pedras', 8, 'Casa B', 'Suíssa', 'Aracaju', 'SE', '49050200', 3);

-- Daniel (cliente_id = 4): 2 endereços
INSERT INTO endereco (endereco, numero, complemento, bairro, cidade, estado, cep, cliente_id) VALUES
    ('Rua dos Cajueiros', 77,  NULL,      'Luzia',   'Aracaju', 'SE', '49045000', 4),
    ('Rua São João',      210, 'Bloco C', 'Atalaia', 'Aracaju', 'SE', '49037100', 4);

-- Elisa (cliente_id = 5): endereço sem pedidos
INSERT INTO endereco (endereco, numero, complemento, bairro, cidade, estado, cep, cliente_id) VALUES
    ('Av. Francisco Porto', 5, NULL, 'Grageru', 'Aracaju', 'SE', '49025300', 5);

-- ============================================================
-- PEDIDOS
-- ============================================================

-- Ana (cliente_id=1): status variados, datas distintas, endereços distintos
INSERT INTO pedido (data_hora, status, valor_total, cliente_id, endereco_id) VALUES
    ('2024-11-15 12:30:00', 'ENTREGUE',          48.80, 1, 1),
    ('2025-01-20 19:00:00', 'ENTREGUE',          84.80, 1, 2),
    ('2025-03-10 20:15:00', 'CANCELADO',         39.90, 1, 1);

-- Bruno (cliente_id=2)
INSERT INTO pedido (data_hora, status, valor_total, cliente_id, endereco_id) VALUES
    ('2025-01-05 13:00:00', 'ENTREGUE',          55.90, 2, 3),
    ('2025-04-22 21:00:00', 'EM_PREPARO',        27.00, 2, 3);

-- Carla (cliente_id=3)
INSERT INTO pedido (data_hora, status, valor_total, cliente_id, endereco_id) VALUES
    ('2025-02-14 18:45:00', 'ENTREGUE',          60.90, 3, 4);

-- Daniel (cliente_id=4): endereços diferentes por pedido
INSERT INTO pedido (data_hora, status, valor_total, cliente_id, endereco_id) VALUES
    ('2025-03-01 11:00:00', 'ENTREGUE',          35.90, 4, 5),
    ('2025-05-30 20:00:00', 'SAIU_PARA_ENTREGA', 62.90, 4, 6);

-- ============================================================
-- ITENS DE PEDIDO
-- ============================================================

-- Pedido 1 (Ana): X-Burguer + Coca-Cola
INSERT INTO itens_pedido (quantidade, preco_unitario, sub_total, pedido_id, produto_id) VALUES
    (2, 18.90, 37.80, 1, 1),
    (2,  6.00, 12.00, 1, 8);

-- Pedido 2 (Ana): Pizza Pepperoni + Brownie + Suco
INSERT INTO itens_pedido (quantidade, preco_unitario, sub_total, pedido_id, produto_id) VALUES
    (1, 44.90, 44.90, 2, 6),
    (1, 14.90, 14.90, 2, 12),
    (1, 10.00, 10.00, 2, 9);

-- Pedido 3 (Ana, cancelado): Margherita
INSERT INTO itens_pedido (quantidade, preco_unitario, sub_total, pedido_id, produto_id) VALUES
    (1, 39.90, 39.90, 3, 5);

-- Pedido 4 (Bruno): X-Bacon Duplo + Suco + Água
INSERT INTO itens_pedido (quantidade, preco_unitario, sub_total, pedido_id, produto_id) VALUES
    (1, 29.90, 29.90, 4, 2),
    (2, 10.00, 20.00, 4, 9),
    (1,  6.00,  6.00, 4, 8);

-- Pedido 5 (Bruno, em preparo): Bowl Proteico
INSERT INTO itens_pedido (quantidade, preco_unitario, sub_total, pedido_id, produto_id) VALUES
    (1, 27.00, 27.00, 5, 15);

-- Pedido 6 (Carla): Margherita + Petit Gâteau + Água
INSERT INTO itens_pedido (quantidade, preco_unitario, sub_total, pedido_id, produto_id) VALUES
    (1, 39.90, 39.90, 6, 5),
    (1, 18.00, 18.00, 6, 13),
    (1,  4.00,  4.00, 6, 10);

-- Pedido 7 (Daniel): Salada Caesar + Água + Coca-Cola
INSERT INTO itens_pedido (quantidade, preco_unitario, sub_total, pedido_id, produto_id) VALUES
    (1, 21.00, 21.00, 7, 14),
    (2,  4.00,  8.00, 7, 10),
    (1,  6.00,  6.00, 7, 8);

-- Pedido 8 (Daniel, saiu p/ entrega): Wrap + Pizza Pepperoni + Brownie
INSERT INTO itens_pedido (quantidade, preco_unitario, sub_total, pedido_id, produto_id) VALUES
    (1, 22.50, 22.50, 8, 3),
    (1, 44.90, 44.90, 8, 6),
    (1, 14.90, 14.90, 8, 12);

-- ============================================================
-- PAGAMENTOS
-- pedidos 5 e 8 (abertos) e 3 (cancelado-estornado) sem pagamento registrado
-- ============================================================
INSERT INTO pagamento (valor_pago, data_hora, pedido_id, forma_pagamento_id) VALUES
    (48.80, '2024-11-15 12:45:00', 1, 1),  -- Ana p1: crédito
    (84.80, '2025-01-20 19:10:00', 2, 3),  -- Ana p2: Pix
    (55.90, '2025-01-05 13:15:00', 4, 2),  -- Bruno p4: débito
    (60.90, '2025-02-14 18:50:00', 6, 1),  -- Carla p6: crédito
    (35.90, '2025-03-01 11:20:00', 7, 4);  -- Daniel p7: dinheiro