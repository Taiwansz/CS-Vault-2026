# 🚚 Sistema de Otimização de Rotas de Entrega

Sistema completo de otimização de rotas desenvolvido em **Spring Boot** com implementação de algoritmos clássicos de roteamento e logística.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Requisitos](#requisitos)
- [Instalação e Execução](#instalação-e-execução)
- [Problemas Implementados](#problemas-implementados)
- [Exemplos de Uso](#exemplos-de-uso)
- [Resultados](#resultados)

## 🎯 Sobre o Projeto

Este projeto implementa 4 problemas clássicos de otimização de rotas aplicados à logística de entregas:

1. **Caminho Mais Curto** - Algoritmo de Dijkstra
2. **Caixeiro Viajante (TSP)** - Comparação entre heurística e solução ótima
3. **Entregas com Janelas de Tempo** - Planejamento temporal de rotas
4. **Roteamento de Veículos (VRP)** - Otimização de frota com restrições de capacidade

## 🛠 Tecnologias

- **Java 17**
- **Spring Boot 3.2.0**
- **Maven 3.6+**

## 📁 Estrutura do Projeto

```
src/main/java/com/example/routing/
├── 📦 models/
│   ├── Grafo.java              # Estrutura de dados para grafos ponderados
│   ├── Cliente.java            # Modelo de cliente com janelas de tempo
│   └── Rota.java               # Modelo de rota com métricas
│
├── 🧮 algorithms/
│   ├── Dijkstra.java           # Algoritmo de menor caminho
│   ├── TSP.java                # Problema do Caixeiro Viajante
│   ├── EntregaComTempo.java    # Roteamento com restrições temporais
│   └── VRP.java                # Problema de Roteamento de Veículos
│
├── 🔧 service/
│   └── RotasService.java       # Service principal com os 4 problemas
│
├── 🛠 utils/
│   ├── GrafoVisualizer.java    # Visualização de grafos e rotas
│   └── TimeUtils.java          # Utilitários de manipulação de tempo
│
└── 🚀 Main.java                # Classe principal Spring Boot
```

## 📋 Requisitos

- **Java 17** ou superior
- **Maven 3.6+**
- Sistema operacional: Windows, Linux ou macOS

## 🚀 Instalação e Execução

### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd Grafos
```

### 2. Compile o projeto

```bash
mvn clean compile
```

### 3. Execute o sistema

```bash
mvn spring-boot:run
```

O sistema executará automaticamente os 4 problemas e exibirá os resultados no console.

## 🧩 Problemas Implementados

### 1️⃣ Problema 1: Caminho Mais Curto (Dijkstra)

**Objetivo:** Encontrar o menor caminho entre dois pontos em um grafo ponderado.

**Algoritmo:** Dijkstra  
**Complexidade:** O((V + E) log V)

**Aplicação Prática:**
- Entrega única
- Rota mais rápida entre dois pontos
- Navegação GPS

**Exemplo:**
```
Grafo: A --8-- B --3-- D --6-- F
       |       |       |       |
       5       3       4       2
       |       |       |       |
       C ------+-------+------ E

Resultado: A → C → E → F (14 km)
```

---

### 2️⃣ Problema 2: Caixeiro Viajante (TSP)

**Objetivo:** Encontrar a rota mais curta que visita todos os pontos exatamente uma vez e retorna ao ponto inicial.

**Algoritmos Implementados:**
- **Vizinho Mais Próximo** (heurística rápida)
- **Força Bruta** (solução ótima para até 8 pontos)

**Aplicação Prática:**
- Múltiplas entregas com um único veículo
- Otimização de rota de vendedores
- Coleta de resíduos

**Comparação de Métodos:**
```
🔍 Vizinho Mais Próximo: 93.0 km (rápido, aproximado)
✅ Força Bruta (Ótimo):  93.0 km (lento, exato)
📊 Diferença: 0.0%
```

---

### 3️⃣ Problema 3: Entregas com Janelas de Tempo

**Objetivo:** Planejar rotas respeitando horários de atendimento dos clientes.

**Restrições:**
- Janela de tempo para cada cliente (início e fim)
- Tempo de viagem entre locais
- Tempo de serviço em cada cliente

**Aplicação Prática:**
- Entregas agendadas
- Serviços com hora marcada
- Logística just-in-time

**Exemplo de Saída:**
```
✅ Entrega 1: C2 - Av. B, 456
   🚗 Viagem: 15 min
   ⏰ Chegada: 08:15
   🕐 Janela: 08:30-09:30
   ⏳ Espera: 15 min
   📦 Serviço: 20 min (08:30 - 08:50)
```

---

### 4️⃣ Problema 4: Roteamento de Veículos (VRP)

**Objetivo:** Otimizar rotas para múltiplos veículos com restrições de capacidade.

**Restrições:**
- Capacidade máxima de cada veículo
- Demanda de cada cliente
- Todos os clientes devem ser atendidos

**Aplicação Prática:**
- Frota de caminhões de entrega
- Distribuição de mercadorias
- Coleta de produtos

**Exemplo de Resultado:**
```
🚚 Veículo 1: CD → C3 → C4 → C6 → CD
   📦 Carga: 95kg / 100kg (95.0%)
   📏 Distância: 41.0km

🚚 Veículo 2: CD → C5 → C7 → CD
   📦 Carga: 75kg / 100kg (75.0%)
   📏 Distância: 36.0km

🚚 Veículo 3: CD → C8 → C1 → CD
   📦 Carga: 60kg / 100kg (60.0%)
   📏 Distância: 19.0km

📊 Total: 3 veículos, 96.0km percorridos
```

## 📊 Resultados

### Saída Completa do Sistema

```
======================================================================
SISTEMA DE OTIMIZAÇÃO DE ROTAS DE ENTREGA
======================================================================

============================================================
ESTRUTURA DO GRAFO
============================================================
A → B(8.0km), C(5.0km)
B → A(8.0km), D(3.0km)
C → A(5.0km), D(4.0km), E(7.0km)
D → B(3.0km), C(4.0km), F(6.0km)
E → C(7.0km), F(2.0km)
F → D(6.0km), E(2.0km)
============================================================

============================================================
PROBLEMA 1: Entrega Única - Caminho Mais Curto
============================================================
📦 Entrega de: A → F
🛣️  Melhor rota: A → C → E → F
📏 Distância total: 14,0 km
============================================================

============================================================
PROBLEMA 2: Múltiplas Entregas - TSP
============================================================
COMPARAÇÃO DE MÉTODOS
============================================================
🔍 Vizinho Mais Próximo:
   Rota: CD → C1 → C5 → C3 → C4 → C2 → CD
   Distância: 93,0 km

✅ Solução Ótima (Força Bruta):
   Rota: CD → C1 → C5 → C3 → C4 → C2 → CD
   Distância: 93,0 km

📊 Diferença: 0,00 km (0,0% pior)
============================================================

======================================================================
PROBLEMA 3: PLANEJAMENTO DE ROTA COM JANELAS DE TEMPO
======================================================================
Início: 08:00

❌ Entrega 1: C2 - Av. B, 456
   🚗 Viagem: 15 min
   ⏰ Chegada: 08:15
   🕐 Janela permitida: 08:30-09:30
   ⏳ Espera: 15 min
   📦 Serviço: 20 min (08:30 - 08:50)

✅ Entrega 2: C1 - Rua A, 123
   🚗 Viagem: 25 min
   ⏰ Chegada: 09:15
   🕐 Janela permitida: 09:00-10:30
   📦 Serviço: 15 min (09:15 - 09:30)

❌ Entrega 3: C3 - Rua C, 789
   🚗 Viagem: 20 min
   ⏰ Chegada: 09:50
   🕐 Janela permitida: 10:00-11:00
   ⏳ Espera: 10 min
   📦 Serviço: 10 min (10:00 - 10:10)

🏁 Retorno ao CD: 10:40
⏱️  Tempo total: 160 minutos
======================================================================

======================================================================
PROBLEMA 4: ROTEAMENTO DE VEÍCULOS (VRP)
======================================================================
Veículos disponíveis: 3
Capacidade por veículo: 100kg

🚚 Veículo 1:
   Rota: CD → C3 → C4 → C6 → CD
   📦 Carga: 95kg / 100kg (95,0%)
   📏 Distância: 41,0km
      • C3: 25kg
      • C4: 50kg
      • C6: 20kg

🚚 Veículo 2:
   Rota: CD → C5 → C7 → CD
   📦 Carga: 75kg / 100kg (75,0%)
   📏 Distância: 36,0km
      • C5: 35kg
      • C7: 40kg

🚚 Veículo 3:
   Rota: CD → C8 → C1 → CD
   📦 Carga: 60kg / 100kg (60,0%)
   📏 Distância: 19,0km
      • C8: 30kg
      • C1: 30kg

======================================================================
📊 RESUMO:
   Total de veículos usados: 3
   Distância total percorrida: 96,0km
   Média por veículo: 32,0km
======================================================================
```

## 🎓 Conceitos Aplicados

- **Teoria dos Grafos**
- **Algoritmos Gulosos** (Greedy)
- **Programação Dinâmica**
- **Heurísticas de Otimização**
- **Pesquisa Operacional**
- **Otimização Combinatória**

## 📚 Referências

- Dijkstra, E. W. (1959). "A note on two problems in connexion with graphs"
- Christofides, N. (1976). "Worst-case analysis of a new heuristic for the travelling salesman problem"
- Dantzig, G. B., & Ramser, J. H. (1959). "The truck dispatching problem"

## 👨‍💻 Autor

Desenvolvido como projeto acadêmico de Teoria dos Grafos e Otimização.

## 📄 Licença

Este projeto é de código aberto e está disponível para fins educacionais.

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!
