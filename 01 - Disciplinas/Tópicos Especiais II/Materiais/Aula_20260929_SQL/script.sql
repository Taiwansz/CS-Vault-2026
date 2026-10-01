-- =============================================================================
-- Disciplina: Topicos Especiais II
-- Data: 29/09/2026
-- Professor: Luiz Claudio Chiavini Oliveira Junior
-- Aluno: Matheus Sousa dos Santos (Taiwansz)
-- Assunto: Modelagem Star Schema (Dimensoes PRODUTO, CLIENTES e Fato VENDAS)
-- SGBD: MySQL 8.x (InnoDB)
-- =============================================================================

CREATE DATABASE IF NOT EXISTS PROJETO;
USE PROJETO;

-- Tabela Dimensao: PRODUTO
CREATE TABLE IF NOT EXISTS PRODUTO (
    ID_PRODUTO VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY,
    NOME_PRODUTO VARCHAR(255) NOT NULL,
    CATEGORIA_PRODUTO VARCHAR(255) NOT NULL,
    MARCA_PRODUTO VARCHAR(255)
);

-- Tabela Dimensao: CLIENTES
CREATE TABLE IF NOT EXISTS CLIENTES (
    ID_CLIENTE VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY,
    NOME_CLIENTE VARCHAR(255) NOT NULL,
    CPF BIGINT NOT NULL UNIQUE,
    CIDADE_CLIENTE VARCHAR(255) NOT NULL,
    ESTADO_CLIENTE VARCHAR(255) NOT NULL
);

-- Tabela Fato: VENDAS
CREATE TABLE IF NOT EXISTS VENDAS (
    ID_VENDA VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY,
    ID_CLIENTE VARCHAR(36),
    ID_PRODUTO VARCHAR(36),
    QUANTIDADE INT NOT NULL,
    VALOR DECIMAL(10, 2),
    -- Integridade Referencial (Chaves Estrangeiras para as Dimensoes)
    CONSTRAINT FK_PRODUTO FOREIGN KEY (ID_PRODUTO) REFERENCES PRODUTO(ID_PRODUTO),
    CONSTRAINT FK_CLIENTE FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTES(ID_CLIENTE)
);
