---
tipo: aula
disciplina: Técnicas Avançadas de Jogos Digitais
professor: Fabricio
tema: "Arquitetura ECS (Entity Component System) vs Programacao Orientada a Objetos"
status: concluido
---

# Arquitetura ECS vs. OOP Tradicional em Game Engines

> [!INFO] **Visao Geral do Modulo**
> Conforme jogos modernos demandam a simulacao de dezenas de milhares de entidades ativas na tela (como enxames, projeteis e particulas), o paradigma tradicional de Orientacao a Objetos (OOP) sofre com a dispersao de ponteiros na memoria e perda massiva de cache da CPU (*cache misses*). O padrao ECS resolve esse gargalo através de **Data-Oriented Design (DOD)**.

---

## 1. O Problema da OOP Tradicional em Jogos (Hierarquias Profundas e Cache Misses)

Na abordagem OOP classica:
```text
GameObject
└── Actor
    └── Pawn
        └── Monster
            └── FlyingFireDragon
```
- **Heranca Fragil:** Dificuldade de composicao flexivel (ex: se um `FlyingFireDragon` precisa de caracteristicas de um item coletavel, a hierarquia de heranca se quebra).
- **Dispersao na Memoria Heap:** Cada objeto e alocado dinamicamente em enderecos aleatorios de memoria. Quando a CPU percorre uma lista de monstros, ela sofre constantes *Cache Misses* (L1/L2/L3), forçando leituras lentas da RAM.

---

## 2. A Triade do Entity Component System (ECS)

1. **Entity (Entidade):** Um mero identificador numerico inteiro (`int id`). Nao possui logica nem dados; serve apenas como chave de ligacao.
2. **Component (Componente):** Estrutura de dados pura (structs sem metodos) que contem apenas informacoes brutas de estado:
   - `PositionComponent { float x, y, z; }`
   - `VelocityComponent { float vx, vy, vz; }`
   - `RenderMeshComponent { int meshId, materialId; }`
3. **System (Sistema):** Logica pura sem estado. Executa queries sobre todas as entidades que possuem um conjunto especifico de componentes e processa seus dados em lote:
   - Exemplo: `MovementSystem` busca todas as entidades com `Position` e `Velocity` e atualiza suas posicoes.

---

## 3. Data-Oriented Design e Cache Line (SoA vs. AoS)

- **Array of Structures (AoS - Tipico de OOP):**
  `[Pos1, Vel1, Mesh1, Health1], [Pos2, Vel2, Mesh2, Health2]...`
  Ao atualizar o movimento, a CPU carrega campos inuteis (`Mesh`, `Health`) para a Cache Line de 64 bytes.
- **Structure of Arrays (SoA - Padrao de Ouro do ECS):**
  `Pos: [Pos1, Pos2, Pos3, Pos4...]`
  `Vel: [Vel1, Vel2, Vel3, Vel4...]`
  Os dados de `Position` ficam dispostos de forma 100% contigua na memoria RAM. Quando a CPU carrega uma Cache Line, ela traz as posicoes dos proximos 4 a 8 objetos de uma vez, atingindo 100% de aproveitamento do cache L1 e habilitando vetorizacao SIMD automatica.
