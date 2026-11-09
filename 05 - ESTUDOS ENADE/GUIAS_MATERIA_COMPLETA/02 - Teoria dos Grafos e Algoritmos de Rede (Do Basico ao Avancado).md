---
tipo: guia-enade
disciplina: "Teoria dos Grafos"
nivel: "Do Basico ao Avancado ENADE"
data: 2026-11-09
autor: "ThSyr Cognitive Sentinel"
---

# 02 — Teoria dos Grafos e Algoritmos de Rede (Do Basico ao Nivel ENADE)

> [!INFO] **Objetivo deste Guia**
> Grafos sao uma das areas com maior peso no ENADE de Ciencia da Computacao. Este guia consolida desde os conceitos estruturais de adjacencia ate a decomposicao linear de Componentes Fortemente Conexos (Tarjan), Menor Caminho com diferentes Heaps e Arvores Geradoras Minimas.

---

## CAMADA 1: O BASICO (O QUE E UM GRAFO E COMO REPRESENTA-LO)

Um grafo $G = (V, E)$ e formado por um conjunto de vertices (ou nos) $V$ e um conjunto de arestas (ou arcos) $E$.

- **Direcionado (Digrafo):** As arestas possuem sentido de ida ($u \to v$ nao implica $v \to u$).
- **Nao-Direcionado:** As arestas sao bidirecionais ($u - v$).
- **Ponderado:** Cada aresta possui um custo ou peso associado $w(u, v)$.
- **Grafo Conexo:** Em grafos nao-direcionados, ha pelo menos um caminho entre qualquer par de vertices.

### Matriz de Adjacencia vs. Lista de Adjacencia:

| Criterio | Matriz de Adjacencia $M[V][V]$ | Lista de Adjacencia `List<List<Edge>>` |
| :--- | :---: | :---: |
| **Espaco de Memoria** | $O(|V|^2)$ (Desperdicado em grafos esparsos) | $O(|V| + |E|)$ (Ideal para a maioria dos problemas reais) |
| **Checar se ha aresta $u \to v$** | $O(1)$ imediato | $O(\text{grau}(u))$ |
| **Iterar sobre vizinhos de $u$** | $O(|V|)$ (tem que ler a linha inteira) | $O(\text{grau}(u))$ (le apenas os vizinhos reais) |

---

## CAMADA 2: TRAVESSIAS FUNDAMENTAIS (BFS E DFS)

### 2.1 Busca em Largura (Breadth-First Search — BFS)
- **Mecanica:** Explora o grafo em "camadas concêntricas" a partir da origem, visitando todos os vizinhos a distancia 1, depois distancia 2, etc.
- **Estrutura de Suporte:** Fila (Queue — FIFO).
- **Complexidade:** $O(|V| + |E|)$.
- **Propriedade de Ouro no ENADE:** Em grafos **sem pesos** (ou pesos uniformes), a BFS garante encontrar o **menor caminho em numero de arestas**.

### 2.2 Busca em Profundidade (Depth-First Search — DFS)
- **Mecanica:** Segue explorando um ramo o mais fundo possivel antes de retroceder (*backtracking*).
- **Estrutura de Suporte:** Pilha (Stack — LIFO ou pilha de recursao do sistema).
- **Complexidade:** $O(|V| + |E|)$.
- **Classificacao de Arestas:** Na arvore de DFS, uma aresta pode ser de *Arvore*, de *Retorno* (aponta para um ancestral, indicando ciclo), de *Avanco* ou de *Cruzamento*.
- **Ordenacao Topologica:** Em um Grafo Aciclico Direcionado (DAG), a ordenacao topologica e a ordem inversa dos tempos de finalizacao da DFS. Serve para agendar tarefas com dependencias de precedencia.

---

## CAMADA 3: COMPONENTES FORTEMENTE CONEXOS (SCC)

Em um digrafo, um subgrafo maximal $C$ e um **Componente Fortemente Conexo (SCC)** se, para todo par $u, v \in C$, existe um caminho direcionado de $u$ ate $v$ e de $v$ ate $u$.

### 3.1 Algoritmo de Tarjan (Uma Unica Passagem de DFS)
- **Como funciona:** Mantem duas variaveis por no: `disc[u]` (tempo de descoberta) e `low[u]` (o menor tempo de descoberta alcancavel a partir de $u$ via arestas de retorno na pilha).
- Quando `low[u] == disc[u]`, o vertice $u$ e a raiz de um SCC. Todos os nos na pilha ate $u$ sao desempilhados e formam aquele componente.
- **Complexidade:** Estritamente linear $O(|V| + |E|)$.

### 3.2 Algoritmo de Kosaraju-Sharir (Duas Passagens de DFS)
1. Executa a primeira DFS em $G$ e empilha os vertices por ordem de termino.
2. Inverte o sentido de todas as arestas de $G$ para obter o grafo transposto $G^T$.
3. Executa a segunda DFS em $G^T$, desempilhando os vertices da pilha criada no passo 1. Cada arvore formada nessa segunda passagem e um SCC.
- **Complexidade:** Linear $O(|V| + |E|)$.

> [!IMPORTANT]
> **Propriedade do Grafo Condensado $G^{SCC}$:**  
> Se substituirmos cada SCC por um unico super-vertice e mantivermos as arestas entre eles, o grafo resultante $G^{SCC}$ e **obrigatoriamente um Grafo Aciclico Direcionado (DAG)**. Ele **NUNCA** contera ciclos direcionados!

---

## CAMADA 4: ALGORITMOS DE MENOR CAMINHO

### 4.1 Algoritmo de Dijkstra (Pesos Nao-Negativos)
- **Principio:** Algoritmo guloso baseado em relaxamento de arestas a partir do vertice mais proximo ainda nao visitado.
- **Condicao Obrigatoria:** **Todos os pesos de arestas devem ser nao-negativos ($w(u, v) \ge 0$)**. Se houver aresta negativa, Dijkstra pode produzir respostas erradas.
- **Complexidade dependendo da Fila de Prioridades:**
  - *Com Array simples:* $O(|V|^2)$.
  - *Com Heap Binario (PriorityQueue padrao):* $O((|V| + |E|) \log |V|)$.
  - *Com Heap de Fibonacci:* Como a operacao `decrease-key` roda em $O(1)$ amortizado, o tempo total cai para $O(|E| + |V| \log |V|)$. E teoricamente superior para grafos densos onde $|E| \approx |V|^2$.

### 4.2 Algoritmo de Bellman-Ford (Aceita Arestas Negativas)
- **Mecanica:** Relaxa todas as $|E|$ arestas do grafo por exatamente $|V|-1$ vezes.
- **Complexidade:** $O(|V| \cdot |E|)$.
- **Detecção de Ciclos Negativos:** Se na $|V|$-ésima passagem alguma aresta ainda puder ser relaxada, o grafo contem um **ciclo de custo negativo**, tornando o conceito de menor caminho indefinido ($-\infty$).

### 4.3 Algoritmo de Floyd-Warshall (Todos os Pares de Vertices)
- **Mecanica:** Programacao dinamica com 3 lacos aninhados (`for k, for i, for j`).
- **Recorrencia:** $dist[i][j] = \min(dist[i][j], dist[i][k] + dist[k][j])$.
- **Complexidade:** Tempo $O(|V|^3)$ e espaco $O(|V|^2)$.

---

## CAMADA 5: ARVORE GERADORA MINIMA (MST)

Uma MST e um subgrafo aciclico que conecta todos os vertices de um grafo nao-direcionado ponderado com o menor somatorio total de pesos de arestas possivel ($|V|-1$ arestas).

### 5.1 Algoritmo de Kruskal
- **Abordagem:** Orientado a arestas (Guloso).
- **Mecanica:**
  1. Ordena todas as $|E|$ arestas pelo peso em ordem crescente.
  2. Percorre as arestas e inclui na arvore se ela nao formar ciclo.
  3. Para testar ciclos em tempo quase constante, utiliza a estrutura de dados **Union-Find (Disjoint Set Union — DSU)** com *Path Compression* e *Union by Rank*.
- **Complexidade:** $O(|E| \log |E|)$ (dominado pela ordenacao das arestas).

### 5.2 Algoritmo de Prim
- **Abordagem:** Orientado a vertices (Guloso).
- **Mecanica:** Inicia em um vertice arbitrario e cresce a arvore adicionando sempre a aresta mais leve que conecta um no da arvore a um no fora dela, usando uma Fila de Prioridades.
- **Complexidade:** $O(|E| \log |V|)$ com Heap Binario.

---

## CAMADA 6: ARMADILHAS CLASSICAS DO ENADE

1. **Dijkstra com Pesos Negativos:** Se a questao perguntar se Dijkstra resolve grafos com arestas de peso negativo, a resposta e **NAO**. O algoritmo correto para isso e Bellman-Ford.
2. **Ciclos no Grafo Condensado de SCCs:** A questao vai tentar afirmar que o grafo condensado por SCCs precisa de algoritmo para desfazer ciclos. **FALSO**: ele e um DAG por definicao matematica invariante.
3. **Numero de Arestas em Grafo Conexo:** Um grafo conexo com $|V|$ vertices possui no minimo $|V|-1$ arestas (quando e uma arvore). Se possuir exatamente $|V|-1$ arestas e for conexo, ele nao tem ciclos.
