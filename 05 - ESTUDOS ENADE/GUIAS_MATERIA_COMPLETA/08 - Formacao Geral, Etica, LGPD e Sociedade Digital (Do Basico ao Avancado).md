---
tipo: guia-enade
disciplina: "Formacao Geral, Legislacao e Etica Profissional"
nivel: "Do Basico ao Avancado ENADE"
data: 2026-11-09
autor: "ThSyr Cognitive Sentinel"
---

> [!NOTE] Navegacao de Estudos ENADE
> **Central:** [[00 - Dashboard]] | **Hub Mestre ENADE:** [[INDEX_ESTUDOS_ENADE]]
> **Modulo Anterior:** [[07 - Engenharia de Software, SOLID e Padroes de Projeto (Do Basico ao Avancado)]]

# 08 — Formacao Geral, Etica, LGPD e Sociedade Digital (Do Basico ao Nivel ENADE)

> [!INFO] **Objetivo deste Guia**
> As primeiras 5 questoes do ENADE (e as questoes interdisciplinares especificas) cobram a capacidade do engenheiro de avaliar o impacto social, legal e etico da tecnologia. Este guia consolida o Marco Civil da Internet, a LGPD, os dilemas eticos de Inteligencia Artificial (Deepfakes, C2PA e Vies Algoritmico) e a sustentabilidade digital (Right to Repair e DMA).

---

## CAMADA 1: MARCO CIVIL DA INTERNET (LEI Nº 12.965/2014)

O Marco Civil e a constituicao da internet brasileira, estabelecendo principios, garantias, direitos e deveres para o uso da rede no pais.

### Principios Fundamentais:
1. **Neutralidade de Rede (Art. 9º):** Os provedores de transmissao devem tratar quaisquer pacotes de dados de forma isonomica, sem distincao por conteudo, origem, destino, servico, terminal ou aplicacao. E proibido degradar ou priorizar o trafego de um concorrente de streaming por interesses comerciais.
2. **Guarda de Registros de Conexao e Acesso a Aplicacoes:**
 - *Provedores de Acesso (Ex: Claro, Vivo):* Devem guardar os registros de conexao (IP, data, hora de inicio e fim) sob sigilo pelo prazo minimo de **1 ano**.
 - *Provedores de Aplicacoes (Ex: Google, Meta):* Devem guardar os registros de acesso a aplicacoes (IP, data e hora) pelo prazo minimo de **6 meses**.
 - A quebra de sigilo desses registros exige **obrigatoriamente ordem judicial previa**.

### Responsabilidade Civil por Danos de Terceiros (Art. 19 e Art. 21):
- **Regra Geral (Art. 19):** Para assegurar a liberdade de expressao e impedir a censura privada, o provedor de aplicacao (redes sociais) **so responde civilmente por danos decorrentes de conteudo publicado por terceiros se, apos ordem judicial especifica, nao tomar as providencias para tornar indisponivel o conteudo**.
- **A Excecao Crucial (Art. 21 — Nudez ou Sexo Nao Consentido):** Em casos de divulgacao nao consentida de imagens, videos ou materiais contendo cenas de nudez ou atos sexuais de carater privado, a responsabilidade do provedor torna-se subsidiaria a partir da **notificacao extrajudicial direta feita pela propria vitima ou seu representante**, sem necessidade de ordem judicial previa.

---

## CAMADA 2: LEI GERAL DE PROTECAO DE DADOS (LGPD — LEI Nº 13.709/2018)

A LGPD regula o tratamento de dados pessoais em meios digitais e fisicos, no setor publico e privado.

### Conceitos Centrais:
- **Dado Pessoal:** Informacao relacionada a pessoa natural identificada ou identificavel (nome, CPF, IP, email, geolocalizacao).
- **Dado Pessoal Sensivel:** Dado sobre origem racial/etnica, conviccao religiosa, opiniao politica, filiacao a sindicato, saude, vida sexual, **dado genetico ou biometrico**. Possui regime protetivo muito mais severo.
- **Dado Anonimizado:** Dado relativo ao titular que nao possa ser identificado, considerando a utilizacao de meios tecnicos razoaveis e disponiveis na ocasiao de seu tratamento. **Nao e considerado dado pessoal** (desde que a anonimizacao nao seja reversivel).

### Principios do Tratamento de Dados (Art. 6º):
1. **Finalidade:** Propositos legitimos, especificos e informados ao titular.
2. **Necessidade (Minimizacao de Dados):** Coleta restrita ao minimo estritamente necessario para a realizacao das finalidades.
3. **Seguranca e Prevencao:** A adocao de medidas tecnicas e administrativas aptas a proteger os dados de acessos nao autorizados e incidentes de seguranca.
4. **Nao Discriminacao:** Impossibilidade de realizacao de tratamento para fins discriminatorios ilicitos ou abusivos.

### As 10 Bases Legais da LGPD (Art. 7º):
O consentimento e apenas UMA das bases legais. Um banco ou hospital pode tratar seus dados SEM o seu consentimento com base em:
1. *Consentimento do titular.*
2. *Cumprimento de obrigacao legal ou regulatoria.*
3. *Execucao de politicas publicas.*
4. *Realizacao de estudos por orgao de pesquisa.*
5. *Execucao de contrato.*
6. *Exercicio regular de direitos em processo judicial.*
7. *Protecao da vida ou da incolumidade fisica.*
8. *Tutela da saude.*
9. *Atendimento aos interesses legitimos do controlador (Legitimo Interesse).*
10. *Protecao do credito.*

### Incidentes de Seguranca e Responsabilidade do Engenheiro:
- A supressao de etapas de teste de intrusao (*pentest*) ou modelagem de ameacas para acelerar lancamentos no mercado configura **negligencia tecnica grave**.
- A corporacao responde de forma objetiva, sujeita a multas da **ANPD (Autoridade Nacional de Protecao de Dados)** de ate **2% do faturamento da empresa (limitada a R$ 50 milhoes por infracao)**, alem de indenizacoes civeis aos correntistas e titulares lesados.

---

## CAMADA 3: ETICA, INTELIGENCIA ARTIFICIAL E DEEPFAKES

### 3.1 Deepfakes, Democracia e Marcas d'Agua Criptograficas (C2PA)
- Modelos generativos (GANs e Difusao) permitem criar desinformacao em video e audio hiper-realistas.
- **Insuficiencia do Fact-Checking:** A checagem de fatos tradicional e inoperante em periodos eleitorais devido a assimetria temporal (o conteudo viraliza em minutos por mensageiros privados, enquanto a pericia leva dias) e ao *Efeito de Influencia Continua* (o ser humano mantem a impressao emocional negativa mesmo apos o desmentido formal).
- **O Padrao C2PA (Coalition for Content Provenance and Authenticity):**
 - Utiliza Criptografia de Chave Publica (PKI).
 - No instante da captura ou sintese, calcula o hash criptografico (SHA-256) dos dados e dos metadados (autor, dispositivo, data/hora).
 - Assina esse manifesto com uma chave privada e certificado de autoridade confiavel.
- **Desafios e Vulnerabilidades Praticas:**
 1. *Furo Analogico (Analog Hole):* Fotografar a tela do computador ou regravar o audio por um microfone externo quebra a cadeia de assinatura original, gerando uma copia "nao assinada" de dificil rastreio.
 2. *Strip de Metadados:* Redes sociais (WhatsApp, Instagram) removem metadados EXIF e C2PA por padrao para economizar largura de banda e preservar privacidade.

### 3.2 Vies Algoritmico e Reconhecimento Facial em Smart Cities
- Softwares de visao computacional baseados em Aprendizado Profundo (*Deep Learning*) aplicados a seguranca publica apresentam **disparidades criticas de falsos positivos em populacoes negras e pardas**.
- **Causa Raiz Tecnica:** Desbalanceamento demografico das bases de imagens historicas de treino (predominancia de rostos caucasianos) e retroalimentacao por abordagens policiais historicas seletivas, reproduzindo e amplificando o racismo estrutural analagico sob uma capa de "neutralidade matematica".

---

## CAMADA 4: SUSTENTABILIDADE E REGULACAO ANTITRUSTE (DMA EUROPEU)

### 4.1 Lixo Eletronico (E-Waste) e o Direito de Reparar (Right to Repair)
- Mais de 60 milhoes de toneladas de residuos eletronicos sao descartados anualmente, com menos de 25% reciclados formalmente. Metais pesados (chumbo, mercurio e cadmio) contaminam os lencois freaticos.
- **Causa Central:** A obsolescencia programada (induzida por atualizacoes de software que degradam o hardware antigo) combinada ao monopolio corporativo de assistencia tecnica.
- **O Movimento Right to Repair e o ODS 12 da ONU:** Exigem que fabricantes disponibilizem esquematicos eletronicos, pecas de reposicao originais e ferramentas de firmware a precos justos para oficinas independentes e usuarios finais, prolongando o ciclo de vida util dos aparelhos.
- **No Brasil:** A Politica Nacional de Residuos Solidos (PNRS — Lei nº 12.305/2010) impoe o dever formal de **logistica reversa compartilhada** a importadores e fabricantes de eletronicos.

### 4.2 Lei dos Mercados Digitais da Uniao Europeia (DMA — Digital Markets Act)
Regula corporacoes classificadas como **Gatekeepers** (empresas que controlam sistemas operacionais, lojas de aplicativos e navegadores com poder desproporcional de mercado):
- **Vedacao a Auto-Preferencia (Self-Preferencing):** Proibe que a plataforma favoreca seus proprios servicos em detrimento de concorrentes (ex: Google priorizar o Google Shopping nos resultados de busca).
- **Obrigatoriedade de Sideloading:** Forca sistemas operacionais moveis (como o iOS da Apple na Europa) a permitirem a instalacao de lojas de aplicativos concorrentes de terceiros.
- **Interoperabilidade Aberta:** Exige que grandes mensageiros instantaneos abram seus protocolos para interoperar com plataformas concorrentes menores.
- **Sancoes:** Multas de ate 10% do faturamento global anual (ou 20% em reincidencia).

---

## CAMADA 5: ARMADILHAS CLASSICAS DO ENADE

1. **Responsabilidade do Engenheiro por Ordens Superiores:** Em questoes sobre etica profissional (SBC/IEEE/ACM), o engenheiro que atende ordens de chefes corporativos para pular testes de seguranca ou violar privacidade age com **INFRACAO ETICA GRAVE**. O dever com a sociedade e o rigor tecnico suplantam qualquer obediencia hierarquica cega.
2. **Consentimento na LGPD:** Dizer que "todo e qualquer tratamento de dados pessoais exige consentimento expresso do usuario" e **FALSO**. Ha outras 9 bases legais expressas na lei (como obrigacao legal e legitimo interesse).
3. **Neutralidade de Rede e Planos Promocionais:** Acordos onde operadoras nao cobram dados para o WhatsApp (*zero-rating*) sao polemicos e frequentemente auditados contra o principio da neutralidade do Art. 9º do Marco Civil da Internet.

---

## Conexoes no Cofre e Disciplinas Praticas

- **Hub Geral:** [[INDEX_ESTUDOS_ENADE]] | [[00 - Dashboard]]
- **Simulado de Referencia:** [[Simulado 04 - Ciencia da Computacao Completo 20 Questoes 2026-11-09]]
- **Disciplinas Praticas:** [[Interação Humano Computador e Sistemas Multimídia]], [[Segurança da Informação]], [[Trabalho de Conclusão de Curso II]]
