-- Criar banco de dados
CREATE DATABASE ThAspectDB;

-- Selecionar o banco de dados para uso
USE ThAspectDB;

-- Criar usuário e conceder privilégios
DROP USER IF EXISTS 'Matheus'@'localhost';
CREATE USER 'Matheus'@'localhost' IDENTIFIED BY 'Ms122789!';
GRANT ALL PRIVILEGES ON ThAspectDB.* TO 'Matheus'@'localhost';
FLUSH PRIVILEGES;

-- Criar tabela Clientes
CREATE TABLE Clientes (
    ID_Cliente INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    Senha VARCHAR(255) NOT NULL,
    Endereco TEXT NOT NULL,
    Telefone VARCHAR(20),
    Data_Cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela Produtos
CREATE TABLE Produtos (
    ID_Produto INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    Tamanho VARCHAR(50),
    Preco DECIMAL(10, 2) NOT NULL,
    Quantidade_Estoque INT NOT NULL
);

-- Criar tabela Carrinho
CREATE TABLE Carrinho (
    ID_Carrinho INT AUTO_INCREMENT PRIMARY KEY,
    ID_Cliente INT,
    Data_Criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ID_Cliente) REFERENCES Clientes(ID_Cliente)
);

-- Criar tabela Carrinho_Itens
CREATE TABLE Carrinho_Itens (
    ID_Item INT AUTO_INCREMENT PRIMARY KEY,
    ID_Carrinho INT,
    ID_Produto INT,
    Quantidade INT NOT NULL,
    Preco_Unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (ID_Carrinho) REFERENCES Carrinho(ID_Carrinho),
    FOREIGN KEY (ID_Produto) REFERENCES Produtos(ID_Produto)
);

-- Criar tabela Pedidos
CREATE TABLE Pedidos (
    ID_Pedido INT AUTO_INCREMENT PRIMARY KEY,
    ID_Cliente INT,
    Data_Pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Status VARCHAR(50),
    Total_Pedido DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (ID_Cliente) REFERENCES Clientes(ID_Cliente)
);

-- Criar tabela Pedidos_Produtos
CREATE TABLE Pedidos_Produtos (
    ID_Pedido_Produto INT AUTO_INCREMENT PRIMARY KEY,
    ID_Pedido INT,
    ID_Produto INT,
    Quantidade INT NOT NULL,
    Preco_Unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (ID_Pedido) REFERENCES Pedidos(ID_Pedido),
    FOREIGN KEY (ID_Produto) REFERENCES Produtos(ID_Produto)
);

-- Criar tabela Cupons
CREATE TABLE Cupons (
    ID_Cupom INT AUTO_INCREMENT PRIMARY KEY,
    Codigo VARCHAR(50) NOT NULL UNIQUE,
    Descricao TEXT,
    Desconto DECIMAL(5, 2) NOT NULL,
    Data_Expiracao DATE NOT NULL
);

-- Criar tabela Recomendações
CREATE TABLE Recomendacoes (
    ID_Recomendacao INT AUTO_INCREMENT PRIMARY KEY,
    ID_Cliente INT,
    ID_Produto INT,
    Data_Recomendacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ID_Cliente) REFERENCES Clientes(ID_Cliente),
    FOREIGN KEY (ID_Produto) REFERENCES Produtos(ID_Produto)
);
