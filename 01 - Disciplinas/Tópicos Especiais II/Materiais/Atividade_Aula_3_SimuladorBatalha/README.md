# Atividade 03 — Simulador de Batalha em Javaland (18/08/2026)

Projeto prático desenvolvido na disciplina de Tópicos Especiais II (POO Java) ministrada pelo Prof. Luiz Claudio Chiavini Oliveira Junior.

## Objetivos e Conceitos Aplicados
- **Classes Abstratas:** `Personagem` atuando como base para entidades do jogo, definindo contrato de estado e o método abstrato `atacar(Personagem alvo)`.
- **Interfaces:** `Magia` definindo contrato para habilidades arcanas (`curar()`, `fireBall()`).
- **Herança e Polimorfismo:** `Guerreiro` e `Mago` especializando `Personagem`, com comportamentos distintos para ataques e interação direta entre instâncias.
- **Encapsulamento e Regras de Negócio:** Tratamento de dano garantindo que vida não fique negativa e cura restrita à vida máxima do personagem.

## Arquivos do Projeto
- `Personagem.java`: Classe abstrata mãe
- `Magia.java`: Interface de habilidades mágicas
- `Guerreiro.java`: Subclasse combatente corpo-a-corpo
- `Mago.java`: Subclasse conjuradora com cura e magias de fogo
- `Main.java`: Loop de batalha em turnos

## Compilação e Execução
```bash
javac *.java
java Main
```
