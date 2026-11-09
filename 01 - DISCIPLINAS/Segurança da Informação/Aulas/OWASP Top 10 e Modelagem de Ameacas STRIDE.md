---
tipo: aula
disciplina: Segurança da Informação
tema: "OWASP Top 10, Modelagem de Ameacas STRIDE e Seguranca em APIs"
status: concluido
---

> [!NOTE] Navegacao no Cofre
> **Painel Geral:** [[00 - Dashboard]] | **Indice de Aulas:** [[Índice Geral de Aulas]]
> **Disciplina:** [[Segurança da Informação]] | **Nucleo ENADE:** [[08 - Formacao Geral, Etica, LGPD e Sociedade Digital (Do Basico ao Avancado)]]

# OWASP Top 10 e Modelagem de Ameacas STRIDE

> [!INFO] **Visao Geral do Modulo**
> A seguranca da informacao moderna nao e um recurso adicional colocado no final do projeto; e uma propriedade arquitetural que deve nascer com o sistema atraves de metodologias como **Secure by Design** e **Threat Modeling**.

---

## 1. A Metodologia de Modelagem de Ameacas STRIDE (Microsoft)

O modelo STRIDE categoriza os vetores de ameaca contra os quais o software deve ser blindado:

| Letra | Ameaca | Propriedade de Seguranca Violada | Mitigacao Tecnica |
| :---: | :--- | :--- | :--- |
| **S** | **Spoofing** (Falsificacao de Identidade) | Autenticidade | Autenticacao multifator (MFA), certificados digitais e tokens criptograficos assinados (JWT com HMAC/RSA). |
| **T** | **Tampering** (Adulteracao de Dados) | Integridade | Hashes criptograficos (SHA-256), assinaturas digitais, TLS e permissões rigorosas de escrita. |
| **R** | **Repudiation** (Repudio / Negacao de Acao) | Nao-Repudio | Trilhas de auditoria seguras (*audit logs* imutaveis protegidos contra exclusao). |
| **I** | **Information Disclosure** (Vazamento de Informacao) | Confidencialidade | Criptografia em transito (TLS 1.3), criptografia em repouso (AES-256) e sanitizacao de stack traces em producao. |
| **D** | **Denial of Service** (Negacao de Servico) | Disponibilidade | Rate limiting, circuit breakers, balanceadores de carga com protecao DDoS e filas assincronas. |
| **E** | **Elevation of Privilege** (Elevacao de Privilegio) | Autorizacao | Principio do menor privilegio (PoLP), RBAC (Role-Based Access Control) e validacao de permissao a cada requisicao no servidor. |

---

## 2. Vulnerabilidades Criticas do OWASP Top 10

1. **A01: Broken Access Control (Controle de Acesso Quebrado):**
 - Inclui **IDOR (Insecure Direct Object Reference)**, onde o usuario altera um identificador na URL (`/api/usuario/105` para `/api/usuario/106`) e o backend entrega os dados sem verificar a posse.
2. **A02: Cryptographic Failures (Falhas Criptograficas):**
 - Transmissao de dados em texto plano (HTTP em vez de HTTPS), uso de algoritmos obsoletos (MD5, SHA-1, DES) ou chaves criptograficas hardcoded no codigo-fonte.
3. **A03: Injection (Injecao de Codigo / SQL Injection):**
 - Dados nao confiaveis fornecidos pelo usuario sao concatenados diretamente em comandos SQL ou comandos do sistema operacional.
 - **Mitigacao Absoluta:** *Prepared Statements* (consultas parametrizadas) e ORMs seguros.
