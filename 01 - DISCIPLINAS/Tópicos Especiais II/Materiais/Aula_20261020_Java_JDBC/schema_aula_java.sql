-- ==============================================================================
-- Topicos Especiais II — Aula 20/10/2026
-- Professor: Luiz Claudio Chiavini Oliveira Junior
-- Script SQL Oficial: Modelagem do Schema aula_java e Tabela alunos
-- ==============================================================================

-- 1. Criacao e Selecao do Database
CREATE DATABASE IF NOT EXISTS aula_java
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE aula_java;

-- 2. Criacao da Tabela de Entidade alunos
CREATE TABLE IF NOT EXISTS alunos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade INT DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 3. Verificacao de Estrutura
DESCRIBE alunos;

-- 4. Insercao de Teste de Bancada (Executada via Java PreparedStatement)
-- INSERT INTO alunos(nome, idade) VALUES ('Matheus', 21);

-- 5. Consulta e Auditoria de Registros
SELECT id, nome, idade FROM alunos;
