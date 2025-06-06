
CREATE DATABASE estoque;
USE estoque;
CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50),
    tamanho ENUM('Pequeno', 'Médio', 'Grande'),
    embalagem ENUM('Lata', 'Vidro', 'Plástico')
);
CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    preco_unitario DECIMAL(10,2),
    unidade VARCHAR(20),
    quantidade_estoque INT,
    quantidade_minima INT,
    quantidade_maxima INT,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);
CREATE TABLE movimento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    produto_id INT,
    quantidade INT,
    tipo ENUM('ENTRADA', 'SAIDA'),
    data_movimentacao DATETIME,
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);


