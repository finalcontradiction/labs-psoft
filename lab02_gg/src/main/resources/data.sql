insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, PRECO)
values(10001,'Leite Integral', '87654321-B', 'Parmalat', 4.5);
insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, PRECO)
values(10002,'Arroz Integral', '87654322-B', 'Tio Joao', 5.5);
insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, PRECO)
values(10003,'Sabao em Po', '87654323-B', 'OMO', 12);
insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, PRECO)
values(10004,'Agua Sanitaria', '87654324-C', 'Dragao', 3);
insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, PRECO)
values(10005,'Creme Dental', '87654325-C', 'Colgate', 2.5);
insert into produto (ID, NOME, CODIGO_BARRA, FABRICANTE, PRECO)
values(10006,'Leite Desnatado', '98765432-C', 'Parmalat', 6);
insert into lote (ID, PRODUTO_ID, NUMERO_DE_ITENS, DATA_VALIDADE)
values(1, 10005, 8, '2023-03-02');
insert into lote (ID, PRODUTO_ID, NUMERO_DE_ITENS, DATA_VALIDADE)
values(2, 10001, 5, '2023-03-02');
insert into lote (ID, PRODUTO_ID, NUMERO_DE_ITENS, DATA_VALIDADE)
values(3, 10001, 7, '2023-01-31');
insert into lote (ID, PRODUTO_ID, NUMERO_DE_ITENS, DATA_VALIDADE)
values(4, 10001, 4, '2023-02-20');
insert into lote (ID, PRODUTO_ID, NUMERO_DE_ITENS, DATA_VALIDADE)
values(5, 10001, 9, '2023-02-05');
insert into lote (ID, PRODUTO_ID, NUMERO_DE_ITENS, DATA_VALIDADE)
values(6, 10006, 10, '2023-02-16');
insert into cliente (ID, CPF, NOME, IDADE, ENDERECO)
values(1, 00011100011, 'Fulano', 25, 'Rua ... ');
