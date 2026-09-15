---
tipo: aula
disciplina: "Tópicos Especiais II"
data: 2026-09-01
assunto: "Automação Industrial - Herança, Polimorfismo, Classes Abstratas e Interfaces (Missões 1 a 6)"
professor: "Luiz Claudio Chiavini Oliveira Junior"
status: concluido
---

# 🏭 Aula 01/09 — Automação Industrial e Arquitetura Orientada a Objetos

> [!info] 📌 Informações da Aula
> - **Data:** 01/09/2026
> - **Disciplina:** [[01 - Disciplinas/Tópicos Especiais II/Tópicos Especiais II|Tópicos Especiais II]]
> - **Professor:** Luiz Claudio Chiavini Oliveira Junior
> - **Tópicos:** Encapsulamento, Herança (`extends`), Sobrescrita de Métodos (`@Override`), Classes Abstratas e Interfaces (`implements`)

---

## 🎯 Contexto e Missões do Laboratório

Nesta aula prática, o desafio consistiu em modelar um ecossistema de controle fabril e automação de manufatura dividido em 6 missões progressivas de POO em Java:

```
                  ┌──────────────────────┐
                  │    <<interface>>     │
                  │     Monitoravel      │
                  └──────────┬───────────┘
                             │ implements
                  ┌──────────┴───────────┐
                  │    SensorTermico     │
                  └──────────────────────┘

                  ┌──────────────────────┐
                  │       Maquina        │
                  ├──────────────────────┤
                  │ - nome: String       │
                  │ - potencia: int      │
                  ├──────────────────────┤
                  │ + operar()           │
                  └──────────┬───────────┘
                             ▲
                ┌────────────┴────────────┐
                │                         │
     ┌──────────┴──────────┐   ┌──────────┴──────────┐
     │       Esteira       │   │    BracoRobotico    │
     ├─────────────────────┤   ├─────────────────────┤
     │ - velocidade: double│   │ + operar()          │
     ├─────────────────────┤   └─────────────────────┘
     │ + operar()          │
     └─────────────────────┘

                  ┌──────────────────────┐
                  │      <<abstract>>    │
                  │   EstacaoIndustrial  │
                  ├──────────────────────┤
                  │+ executarCiclo()*    │
                  └──────────┬───────────┘
                             ▲
                  ┌──────────┴───────────┐
                  │        Prensa        │
                  ├──────────────────────┤
                  │ + executarCiclo()    │
                  └──────────────────────┘
```

### 1. Missão 1: Gerador Central (Encapsulamento)
- Definição da classe base `Maquina`.
- Atributos privados: `nome` e `potencia`.
- Construtor com passagem de parâmetros e métodos de acesso (getters e setters).

### 2. Missão 2: Ciclo Operacional Padrão
- Implementação do método `public void operar()` na classe `Maquina`.
- Define o comportamento padrão de execução de qualquer máquina do parque fabril.

### 3. Missão 3: Esteira Linha A (Herança Especializada)
- Criação da classe `Esteira` herdando de `Maquina` via `extends`.
- Introdução de atributo específico de velocidade (`double velocidade`).
- Utilização de `super(nome, potencia)` para invocação do construtor da superclasse e sobrescrita de `operar()`.

### 4. Missão 4: Braço Robótico BR-01 (Polimorfismo e Sobrescrita)
- Criação da classe `BracoRobotico` especializando `Maquina`.
- Sobrescrita do método `operar()` com `@Override`, aplicando a lógica de solda robotizada.

### 5. Missão 5: Estações Pesadas (Classes Abstratas)
- Definição da classe abstrata `EstacaoIndustrial` contendo o método abstrato `public abstract void executarCiclo();`.
- Implementação da classe concreta `Prensa`, fornecendo a implementação do ciclo com carga de 200 toneladas.

### 6. Missão 6: Rede de Sensores (Interfaces e Telemetria)
- Criação da interface `Monitoravel` com o método `double lerTemperatura();`.
- Implementação da classe `SensorTermico`, conectando a telemetria ao ecossistema de dados.

---

## 💻 Código Fonte Organizado no Cofre

Todos os arquivos estão organizados na pasta de materiais:
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_0109_AutomacaoIndustrial/Maquina.java|Maquina.java]] — Classe base com atributos encapsulados e método operar
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_0109_AutomacaoIndustrial/Esteira.java|Esteira.java]] — Subclasse de esteira rolante com velocidade variável
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_0109_AutomacaoIndustrial/BracoRobotico.java|BracoRobotico.java]] — Subclasse de braço mecânico para soldagem de precisão
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_0109_AutomacaoIndustrial/EstacaoIndustrial.java|EstacaoIndustrial.java]] — Classe abstrata base para postos de trabalho pesados
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_0109_AutomacaoIndustrial/Prensa.java|Prensa.java]] — Subclasse concreta de prensagem hidráulica
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_0109_AutomacaoIndustrial/Monitoravel.java|Monitoravel.java]] — Interface de contrato para telemetria de sensores
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_0109_AutomacaoIndustrial/SensorTermico.java|SensorTermico.java]] — Leitura térmica de temperatura industrial
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_0109_AutomacaoIndustrial/PainelDeControle.java|PainelDeControle.java]] — Ponto de execução e orquestração polimórfica
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Aula_0109_AutomacaoIndustrial/README.md|README.md]] — Guia de compilação e execução
