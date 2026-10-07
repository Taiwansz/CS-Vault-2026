---
tipo: aula
disciplina: "Tópicos Especiais II"
data: 2026-10-06
assunto: "Modelagem Dimensional, Star Schema, Ralph Kimball e Integridade de Dados em Operações Aéreas ANAC"
professor: "Luiz Claudio Chiavini Oliveira Junior"
status: concluido
---

# Aula 06/10 — Modelagem Dimensional, Star Schema e Telemetria ANAC

> [!info] Informações da Aula
> - **Data:** 06/10/2026
> - **Disciplina:** [[01 - Disciplinas/Tópicos Especiais II/Tópicos Especiais II|Tópicos Especiais II]]
> - **Professor:** Luiz Claudio Chiavini Oliveira Junior
> - **Foco:** Modelagem Dimensional (Star Schema), Ralph Kimball vs 3NF de Codd, DDL no MySQL Workbench (schema `aula0610`), Deteccao Forense de BOM e Integridade Referencial no InnoDB.
> - **Artefato Complementar:** [[2026-10-06_a_gazeta_dos_grandes_sistemas.html]]

---

## 1. Fundamentos: OLTP vs OLAP e a Abordagem Dimensional de Ralph Kimball

A aula aprofundou a distincao estrutural entre ambientes transacionais operacionais e armazens analiticos corporativos:

| Dimensao | OLTP (Online Transaction Processing) | OLAP / Data Warehouse (Ralph Kimball) |
|---|---|---|
| **Objetivo Primario** | Eficiencia maxima de escrita, atomicidade e consistencia | Velocidade analitica de leitura e simplicidade conceitual |
| **Normalizacao** | 3ª Forma Normal (3NF) de Edgar F. Codd (zero redundancia) | Desnormalizacao controlada via Modelos Dimensionais |
| **Estrutura Tipica** | Dezenas de tabelas fragmentadas com relacionamentos 1:N | Star Schema (Tabela Fato central cercada por Dimensoes) |
| **Operacao Dominante** | `INSERT`, `UPDATE`, `DELETE` pontuais | `SELECT` agregado com `GROUP BY`, janelas e juncoes em estrela |
| **Custo Computacional** | Baixo custo por transacao; alto custo em relatorios complexos | Consultas velozes ($O(1)$ a $O(n)$ com indices dimensionais) |

---

## 2. DDL Executada em Bancada (Schema `aula0610`)

### Criacao do Schema e da Dimensao de Aeroportos

```sql
-- Criacao do banco de dados analitico da aula
CREATE DATABASE aula0610;
USE aula0610;

-- Criacao da tabela dimensional de aeroportos (Star Schema)
CREATE TABLE dim_aeroporto (
    Aeroporto_ICAO VARCHAR(4) NOT NULL PRIMARY KEY,
    Aeroporto_IATA VARCHAR(4) NOT NULL,
    Nome_Aeroporto VARCHAR(50) NOT NULL,
    Cidade VARCHAR(50) NOT NULL,
    UF VARCHAR(50) NOT NULL,
    Tipo VARCHAR(50) NOT NULL,
    Capacidade_slot VARCHAR(50) NOT NULL
);

-- Inspecao preliminar dos registros
SELECT * FROM dim_aeroporto;
```

### Vinculacao das Restricoes de Chave Estrangeira (Integridade Referencial)

A consolidacao da tabela fato exigiu o estabelecimento formal das relacoes com as dimensoes perifericas para assegurar que nenhum voo orfao ingressasse na camada analitica:

```sql
-- 1. Chave Estrangeira para Companhia Aerea
ALTER TABLE fato_operacoes_voos_anac
    ADD CONSTRAINT fk_fato_companhia_aerea
    FOREIGN KEY (Companhia_ICAO)
    REFERENCES dim_companhia_aerea (Companhia_ICAO);

-- 2. Chaves Estrangeiras para Aeroporto (Origem e Destino)
ALTER TABLE fato_operacoes_voos_anac
    ADD CONSTRAINT fk_fato_aeroporto_origem
    FOREIGN KEY (Aeroporto_Origem_ICAO)
    REFERENCES dim_aeroporto (Aeroporto_ICAO);

ALTER TABLE fato_operacoes_voos_anac
    ADD CONSTRAINT fk_fato_aeroporto_destino
    FOREIGN KEY (Aeroporto_Destino_ICAO)
    REFERENCES dim_aeroporto (Aeroporto_ICAO);

-- 3. Chave Estrangeira para Motivo do Atraso
ALTER TABLE fato_operacoes_voos_anac
    ADD CONSTRAINT fk_fato_motivo_atraso
    FOREIGN KEY (Motivo_Atraso_Codigo)
    REFERENCES dim_motivo_atraso (Motivo_Codigo);
```

---

## 3. Analise Forense: Obstaculos de Persistencia e Solucoes Tecnicas

1. **Vulnerabilidade do Byte Order Mark (BOM UTF-8 `\ufeff`):**
   - Na ingestao via assistentes de importacao do Workbench, arquivos exportados por planilhas corporativas inserem bytes invisiveis no inicio do cabeçalho.
   - O caractere invisivel corrompe o nome da primeira coluna (ex: `ï»¿Aeroporto_ICAO`), quebrando juncoes relacionais e consultas catalogadas.
   - *Solucao:* Higienizacao previa de codificacao em UTF-8 estrito sem BOM e especificacao deterministica manual via scripts DDL.

2. **Erro 1170 do InnoDB (Colunas `TEXT` sem Comprimento de Chave):**
   - Tentativas de definir chaves estrangeiras ou primarias em campos tipados generica e promiscuamente como `TEXT` sao barradas pelo motor InnoDB (`BLOB/TEXT column used in key specification without a key length`).
   - *Solucao:* Padronizacao estrita de codigos internacionais aeronauticos sob `VARCHAR(4)` para ICAO/IATA e chaves curtas inteiras/alfanumericas indexaveis.

---

## 4. Contexto Regulatorio e Telemetria Operacional (ANAC 400)

- **Regra D15 de Tolerancia:** Considera-se pontual a decolagem que ocorre em ate 15 minutos do horario planehado de tabela (STD - Scheduled Time of Departure).
- **Efeito Domino (Reactionary Delay):** Apos o primeiro atraso operacional em aeroportos saturados de slots (SBSP/SBGR), a jornada da tripulacao atinge limites da Lei do Aeronauta (Lei 13.475/2017) e a malha propaga retardos exponenciais para aeroportos satelites.
- **Passivo da Resolucao ANAC 400:** Exigencia de assistencia material compulsoria (comunicacao a partir de 1h, alimentacao a partir de 2h e hospedagem/translado a partir de 4h), impondo contingencias financeiras imediatas as operadoras aereas.

---

## 5. Artefatos Produzidos na Sessao

- **Periodico de Imprensa Historica BroadSheet:** [[2026-10-06_a_gazeta_dos_grandes_sistemas.html]]
- **Captura em Alta Resolucao:** [[preview_broadsheet_20261006.png]]
- **Script SQL Higienizado:** Registrado nas secoes de DDL acima e sincronizado no cofre academico.
