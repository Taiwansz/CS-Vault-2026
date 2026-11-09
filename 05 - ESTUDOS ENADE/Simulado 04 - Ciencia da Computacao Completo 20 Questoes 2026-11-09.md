---
tipo: simulado
categoria: "Estudos ENADE"
data: 2026-11-09
origem: "UniEduK / UniMAX — Simulado 2 ENADE (Ciência da Computação)"
total_questoes: 20
questoes_discursivas: 3
questoes_objetivas: 17
---

# Simulado 2 ENADE 2026 — Ciência da Computação (20 Questões Completas)

> [!INFO] **Metadados da Avaliação**
> - **Instituição:** UniEduK / UniMAX
> - **Curso:** Ciência da Computação (Formandos 2026)
> - **Data de Aplicação:** 09/11/2026
> - **Formato:** Prova Integrada — 5 Questões de Formação Geral + 15 Questões de Componente Específico.
> - **Aproveitamento Previsto:** 100% de precisão nos gabaritos e discursivas calibradas.

---

## PARTE 1 — FORMAÇÃO GERAL (QUESTÕES 1 A 5)

### QUESTÃO 1 (Discursiva) — Deepfakes, Democracia e Marcas d’Água Criptográficas

**Texto-Base:**
Avanço de difusão e GANs na síntese de áudios/vídeos (deepfakes). Circulação massiva em mensageria privada momentos antes da votação, escapando do tempo de resposta da checagem tradicional. Padrão C2PA (Coalition for Content Provenance and Authenticity).

**Comandos e Respostas:**

**a) Impacto na democracia e insuficiência do fact-checking:**
> As deepfakes prejudicam as eleições porque enganam as pessoas bem na reta final da votação. Criam vídeos e áudios falsos de candidatos falando coisas graves, e o eleitor acaba decidindo seu voto com base em uma mentira. A liberdade de expressão serve para discutir ideias de verdade, e não para espalhar mentiras fabricadas para manipular a eleição.
> 
> Desmentir depois não resolve por dois motivos:
> 1. Não dá tempo: O vídeo falso se espalha muito rápido em grupos de WhatsApp e Telegram logo antes de a votação começar. Até a justiça ou os jornais provarem que o vídeo é falso, a eleição já acabou e o eleitor já votou enganado.
> 2. A mentira gruda na cabeça: Quem assiste a um vídeo polêmico guarda aquela impressão negativa. O desmentido quase nunca alcança as mesmas pessoas que viram o boato primeiro, e muita gente nem acredita na correção.

**b) Mecanismo C2PA e desafio prático:**
> O C2PA funciona usando assinatura digital. Quando a câmera tira a foto ou a inteligência artificial gera a imagem, o sistema cria um código único (chamado hash) do arquivo, juntando informações como data, hora, aparelho e quem criou. Tudo isso é lacrado com uma assinatura digital oficial. Se qualquer pessoa tentar cortar um pedaço ou editar a foto depois, o código não bate mais e o sistema avisa na hora que a imagem foi alterada.
> 
> Desafio prático no dia a dia:
> Se alguém simplesmente pegar outro celular e filmar a tela do computador onde o vídeo falso está passando, esse novo vídeo perde o lacre digital e o sistema não consegue mais rastrear a origem. Além disso, aplicativos como WhatsApp e Instagram costumam apagar esses dados extras das fotos e vídeos para economizar internet e deixar o arquivo mais leve, o que atrapalha essa verificação.

---

### QUESTÃO 2 — Sustentabilidade, Lixo Eletrônico e Direito de Reparar (Right to Repair)
- **Gabarito:** **B**
- **Fundamentação:** O movimento pelo Direito de Reparar (Right to Repair) e o ODS 12 estabelecem que fabricantes devem fornecer manuais esquemáticos, firmwares e peças de reposição a preços acessíveis para oficinas independentes e usuários finais, reduzindo a geração prematura de lixo eletrônico (e-waste).
- **Pegadinhas:** A alternativa A afirma que a obsolescência é ecologicamente positiva; a C alega que lixo eletrônico é inerte/biodegradável; a D diz que a legislação brasileira proíbe logística reversa (a PNRS Lei 12.305/2010 a torna obrigatória).

---

### QUESTÃO 3 — Mercados Digitais e Regulação Antitruste (DMA Europeu)
- **Gabarito:** **B**
- **Fundamentação:** O Digital Markets Act (DMA) proíbe a prática de favorecimento próprio (self-preferencing) e impõe a abertura para lojas concorrentes (sideloading) e interoperabilidade, impedindo que Gatekeepers fechem mercados e sufoquem concorrentes em serviços adjacentes.
- **Pegadinhas:** A diz que monopólios verticais sempre inovam mais; C diz que network effects dispensam regulação estatal; E afirma que as regras do DMA são voluntárias e sem multas (multas podem atingir 10% do faturamento global).

---

### QUESTÃO 4 — Cidades Inteligentes e Reconhecimento Facial (Asserção-Razão)
- **Gabarito:** **A** (As asserções I e II são proposições verdadeiras, e a II é uma justificativa correta da I).
- **Fundamentação:** A asserção I é verdadeira pois a vigilância biométrica acrítica amplia detenções indevidas por falsos positivos em minorias; a asserção II é verdadeira e justifica diretamente a I, pois os modelos de deep learning refletem e amplificam o viés das bases de treino desbalanceadas e dos registros policiais históricos.

---

### QUESTÃO 5 — Cibersegurança e Responsabilidade Civil do Engenheiro
- **Gabarito:** **B**
- **Fundamentação:** A eliminação deliberada de threat modeling e pentests configura negligência grave (violação do dever de prevenção e segurança da LGPD, art. 6º, VII e art. 46), gerando responsabilidade civil e administrativa perante a ANPD e os titulares lesados.
- **Pegadinhas:** A afirma que a culpa é só do hacker; C diz que a LGPD permite cortar segurança para preservar lucro; D defende que a lealdade cega à chefia anula a ética do engenheiro.

---

## PARTE 2 — COMPONENTE ESPECÍFICO (QUESTÕES 6 A 20)

### QUESTÃO 6 (Discursiva) — Mochila Fracionária vs. Mochila 0/1 (Programação Dinâmica)

**Comandos e Respostas:**

**a) Estratégia da Mochila Fracionária, complexidade e por que falha na 0/1:**
> A estratégia é o Algoritmo Guloso. A gente calcula o valor por quilo de cada item, ordena do maior para o menor e vai colocando na mochila até encher. Se o último não couber inteiro, pega só o pedaço que falta. O tempo é O(n log n) por causa da ordenação.
> 
> Na Mochila 0/1 isso não funciona porque não dá para dividir o item.
> Exemplo simples:
> Mochila cabe 50 kg.
> - Item 1: peso 10 kg, valor 60 (vale 6 por kg)
> - Item 2: peso 20 kg, valor 100 (vale 5 por kg)
> - Item 3: peso 30 kg, valor 120 (vale 4 por kg)
> 
> Pelo método guloso, você pega o item 1 e o 2 (soma 30 kg e valor 160). Sobram 20 kg livres, mas o item 3 pesa 30 kg e não cabe.
> A melhor escolha seria pegar direto o item 2 e o item 3 (peso 50 kg e valor 220). O guloso erra porque deixa espaço vazio sobrando.

**b) Equação de recorrência, matriz dp[i][w], complexidades e por que é pseudo-polinomial:**
> A tabela dp[i][w] guarda o maior valor que dá para conseguir usando os primeiros 'i' itens em uma mochila de capacidade 'w'.
> 
> Fórmula:
> - Se o item não cabe: `dp[i][w] = dp[i-1][w]`
> - Se o item cabe: `dp[i][w] = max(dp[i-1][w], dp[i-1][w - peso[i]] + valor[i])`
> 
> Complexidade:
> - Tempo: O(n * W)
> - Espaço: O(n * W)
> 
> É pseudo-polinomial porque o tempo depende do valor do número W (a capacidade da mochila), e não do tamanho dele em bits. Se o número W for muito grande, o algoritmo fica lento.

---

### QUESTÃO 7 (Discursiva) — Java Moderno: Virtual Threads (Loom) e Thread Pinning

**Comandos e Respostas:**

**a) Comportamento em I/O bloqueante (Virtual Threads vs. Platform Threads):**
> Na thread comum antiga, quando o sistema espera uma resposta de rede ou banco de dados, a thread real do sistema operacional fica travada sem fazer nada, gastando memória da máquina.
> 
> Nas Virtual Threads, quando o código trava esperando o banco ou a rede, a JVM solta a thread real do sistema para ela atender outras tarefas, e guarda a Virtual Thread na memória. Quando a resposta do banco chega, a JVM coloca a Virtual Thread de volta em qualquer thread livre para continuar rodando.

**b) Fenômeno do Thread Pinning, caso clássico e impacto:**
> Thread Pinning é quando a Virtual Thread fica "presa" na thread real do sistema e a JVM não consegue soltar ela durante a espera.
> 
> Isso acontece principalmente quando a chamada de banco ou rede é feita dentro de um bloco 'synchronized' antigo do Java.
> 
> O problema disso é que existem poucas threads reais na máquina (normalmente o número de núcleos da CPU). Se várias ficarem presas esperando o banco dentro de um synchronized, o servidor trava e nenhuma outra Virtual Thread consegue rodar.

---

### QUESTÃO 8 — Estruturas de Dados: Tries vs. Árvores AVL
- **Gabarito:** **B**
- **Fundamentação:** A busca de prefixo em uma Trie consome tempo estritamente $O(m)$, onde $m$ é o comprimento da string buscada, sendo independente da quantidade $N$ de palavras indexadas. Na AVL balanceada, cada nó exige comparação completa de strings, custando $O(m \cdot \log N)$.

---

### QUESTÃO 9 — Algoritmos Distribuídos: Hashing Consistente
- **Gabarito:** **A**
- **Fundamentação:** O Hashing Consistente projeta nós servidores e chaves de dados no mesmo anel de hash circular uniforme ($0$ a $2^b - 1$). A chave é atribuída ao primeiro nó com valor de hash $\ge$ ao hash da chave no sentido horário, minimizando a redistribuição de dados em caso de churn.

---

### QUESTÃO 10 — Grafos: Componentes Fortemente Conexos (SCC) e Tarjan
- **Gabarito:** **A**
- **Fundamentação:** Os algoritmos clássicos de Tarjan e Kosaraju-Sharir calculam todos os SCCs de um grafo direcionado em tempo estritamente linear $O(|V| + |E|)$. A condensação de um grafo em seus SCCs forma invariavelmente um Grafo Acíclico Direcionado (DAG).

---

### QUESTÃO 11 — Estruturas de Dados: Segment Tree vs. Fenwick Tree (BIT)
- **Gabarito:** **A**
- **Fundamentação:** Um vetor de soma de prefixos estático permite consulta em $O(1)$, mas atualização pontual custa $O(n)$. Tanto a Segment Tree quanto a BIT executam ambas as operações (atualização pontual e consulta de intervalo) em tempo balanceado de $O(\log n)$.

---

### QUESTÃO 12 — Grafos: Dijkstra e Heaps
- **Gabarito:** **B**
- **Fundamentação:**
  - Com Heap Binário: cada vértice é extraído ($|V| \log |V|$) e cada aresta é relaxada ($|E| \log |V|$), resultando em $O((|V| + |E|) \log |V|)$.
  - Com Heap de Fibonacci: a operação `decrease-key` roda em $O(1)$ amortizado, reduzindo o custo total para $O(|E| + |V| \log |V|)$.

---

### QUESTÃO 13 — Ordenação Linear O(n): Counting Sort e Radix Sort
- **Gabarito:** **B**
- **Fundamentação:** O limite inferior teórico de $\Omega(n \log n)$ aplica-se exclusivamente a ordenações baseadas em comparações diretas de chaves. O Counting Sort e o Radix Sort quebram esse piso porque utilizam as chaves diretamente como índices de tabelas de contagem de frequência ou fatiam dígitos sob a restrição de universo limitado.

---

### QUESTÃO 14 — Java Moderno: Records e Imutabilidade
- **Gabarito:** **B**
- **Fundamentação:** Um `record` herda implicitamente de `java.lang.Record`, o que veda herança direta de classes concretas (`extends`), mas permite implementar interfaces (`implements`). Seus campos são `final` e os métodos `equals()`, `hashCode()`, `toString()` e acessores de leitura são sintetizados pelo compilador.

---

### QUESTÃO 15 — Java Moderno: Sealed Classes e Pattern Matching para switch
- **Gabarito:** **B**
- **Fundamentação:** O compilador Java realiza a verificação de exaustividade (exhaustiveness check). Como a interface selada autoriza exclusivamente os três subtipos mapeados no `switch`, a cláusula `default:` torna-se desnecessária.

---

### QUESTÃO 16 — Java Concorrência: CompletableFuture e Exceções
- **Gabarito:** **C** (`Recuperado: Falha de comunicacao`)
- **Fundamentação:** A exceção lançada na thread assíncrona é encapsulada em uma `CompletionException`. O handler `.exceptionally(...)` intercepta a falha, recupera a causa original via `ex.getCause().getMessage()` e retorna a string de contingência com sucesso para o `.join()`.

---

### QUESTÃO 17 — Java: Boas Práticas e Idiomas de Uso do Optional
- **Gabarito:** **D**
- **Fundamentação:** O propósito canônico de `Optional<T>` é figurar estritamente como tipo de retorno de métodos para sinalizar ausência. O uso cru de `.get()` é um anti-padrão; a abordagem correta é o encadeamento funcional via `.map()`, `.flatMap()`, `.filter()` e `.orElseGet()`.

---

### QUESTÃO 18 — JVM: Gerenciamento de Memória e Metaspace
- **Gabarito:** **B**
- **Fundamentação:** O Metaspace opera em memória nativa do SO. Sem o teto limitador `-XX:MaxMetaspaceSize`, aplicações com geração intensiva de classes dinâmicas em tempo de execução (CGLIB, proxies, ClassLoaders com vazamento) podem devorar toda a RAM do host, gerando `java.lang.OutOfMemoryError: Metaspace`.

---

### QUESTÃO 19 — SOLID: Inversão de Dependência (DIP) e Injeção
- **Gabarito:** **B**
- **Fundamentação:** O Princípio da Inversão de Dependência postula que módulos de alto nível devem depender de abstrações, e não de implementações concretas de baixo nível. A refatoração correta tipa o campo como interface `PedidoRepository` e injeta a instância concreta pelo construtor público.

---

### QUESTÃO 20 — Java Collections: Escalabilidade com ConcurrentHashMap
- **Gabarito:** **A**
- **Fundamentação:** O wrapper legado `Collections.synchronizedMap` sincroniza cada método através de um único monitor lock em todo o objeto, serializando todas as leituras e escritas e tornando-se um gargalo sob alta concorrência. O `ConcurrentHashMap` utiliza particionamento interno em buckets com CAS e locks granulares.
