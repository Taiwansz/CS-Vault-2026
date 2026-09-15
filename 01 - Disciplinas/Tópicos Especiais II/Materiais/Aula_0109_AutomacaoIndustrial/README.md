# Aula 01/09/2026 — Automação Industrial e Polimorfismo

Exercícios práticos de Programação Orientada a Objetos em Java ministrados pelo Prof. Luiz Claudio Chiavini Oliveira Junior.

## Missões do Laboratório
1. **Missão 1 (Gerador Central):** Criação da classe `Maquina` com encapsulamento (`nome`, `potencia`), construtores, getters e setters.
2. **Missão 2 (Ciclo Operacional):** Implementação do método `operar()` na classe base `Maquina`.
3. **Missão 3 (Esteira Linha A):** Herança com `Esteira extends Maquina`, atributo específico de `velocidade` e chamada de `super()`.
4. **Missão 4 (Braço Robótico BR-01):** Sobrescrita de método (`@Override`) com comportamento especializado de solda.
5. **Missão 5 (Prensa Hidráulica):** Classe abstrata `EstacaoIndustrial` e implementação concreta da classe `Prensa`.
6. **Missão 6 (Rede de Sensores):** Interface `Monitoravel` com contrato de telemetria térmica implementada por `SensorTermico`.

## Arquivos
- `Maquina.java`: Classe base de máquinas industriais
- `Esteira.java`: Especialização de máquina de transporte contínuo
- `BracoRobotico.java`: Especialização de máquina de solda robótica
- `EstacaoIndustrial.java`: Classe abstrata para estações pesadas
- `Prensa.java`: Implementação concreta de prensa hidráulica
- `Monitoravel.java`: Interface de monitoramento e telemetria
- `SensorTermico.java`: Implementação de sensor térmico industrial
- `PainelDeControle.java`: Ponto de entrada e teste polimórfico

## Compilação e Execução
```bash
javac *.java
java PainelDeControle
```
