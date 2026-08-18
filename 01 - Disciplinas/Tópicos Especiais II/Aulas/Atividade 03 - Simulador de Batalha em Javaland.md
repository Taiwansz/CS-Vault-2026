---
tipo: aula
disciplina: "Tópicos Especiais II"
data: 2026-08-18
assunto: "Simulador de Batalha em Javaland - POO, Interfaces e Abstração"
---

# 📜 Atividade 03 — Simulador de Batalha em Javaland

> [!info] 📌 Informações da Aula
> - **Data:** 18/08/2026
> - **Disciplina:** [[01 - Disciplinas/Tópicos Especiais II/Tópicos Especiais II|Tópicos Especiais II]]
> - **Professor:** Luiz Claudio Chiavini Oliveira Junior
> - **Tópicos:** Interfaces, Classes Abstratas, Polimorfismo, Interação entre Objetos

---

## ⚔️ O Desafio

O Reino de **"Javaland"** está em guerra. A sua missão é criar um simulador de batalha em turnos utilizando os conceitos de **Programação Orientada a Objetos (POO)** que aprendemos até agora.

Neste projeto, o foco é criar objetos poderosos e fazê-los interagir uns com os outros.

---

## 🛠️ Requisitos do Sistema

Deverá criar as seguintes estruturas no seu projeto:

### 1. A Interface `Magia` (O Contrato)
Nem todas as classes no nosso jogo sabem usar magia. Portanto, crie uma interface chamada `Magia` com o seguinte contrato:
- `void curar()`: Um método que permitirá ao personagem recuperar parte da sua própria vida.

### 2. A Classe Abstrata `Personagem` (A Classe Mãe)
Crie uma classe abstrata `Personagem` que servirá de base para todos os lutadores:
- `nome` (`String`)
- `vida` (`int`)
- `pontosAtaque` (`int`)
- Deve receber o nome, a vida e os pontos de ataque iniciais no construtor.
- `void receberDano(int dano)`: Subtrai o dano da vida atual. Se a vida ficar menor que 0, deve ser ajustada para 0. Imprima uma mensagem.
- `boolean estaVivo()`: Retorna `true` se a vida for maior que 0, e `false` caso contrário.
- **Getters** para os atributos (não crie setters para a vida e ataque, eles só devem mudar pelas regras do jogo).
- `public abstract void atacar(Personagem alvo)`: Método abstrato que recebe outro personagem como parâmetro. É assim que um objeto interage com outro!

### 3. As Classes Filhas (Concretas)

#### ⚔️ `Guerreiro`
- Herda (`extends`) de `Personagem`.
- Ao atacar, o Guerreiro causa o dano exato dos seus `pontosAtaque` ao alvo chamando o método `alvo.receberDano(...)`.
- Deve imprimir: `"O Guerreiro [Nome] atacou [Nome do Alvo] com a sua espada!"`.

#### 🪄 `Mago`
- Herda (`extends`) de `Personagem`.
- Implementa (`implements`) a interface `Magia`.
- O Mago ataca com feitiços. Ele causa dano igual aos seus `pontosAtaque`, mas imprime: `"O Mago [Nome] lançou uma bola de fogo em [Nome do Alvo]!"`.
- Ao usar o método `curar()` da interface, o Mago recupera 20 pontos de vida (não podendo ultrapassar a vida máxima inicial de 100). Imprima: `"O Mago [Nome] curou-se e agora tem [X] de vida."`.

---

## ⚔️ A Classe `Main` (A Arena de Batalha)

Na sua classe principal, você será o Mestre do Jogo:

1. Instancie um Guerreiro (ex: `"Thor"`, 100 de vida, 25 de ataque).
2. Instancie um Mago (ex: `"Gandalf"`, 80 de vida, 30 de ataque).
3. Crie uma estrutura de repetição (`while`) que continue a rodar enquanto o Guerreiro estiver vivo E o Mago estiver vivo.
4. **Lógica de Turnos:**
   - O Guerreiro ataca o Mago: `guerreiro.atacar(mago);`
   - Verifique se o Mago sobreviveu. Se sim, ele contra-ataca ou cura-se.
   - Se a vida do Mago for menor que 30, chame o método `mago.curar()`. Caso contrário, `mago.atacar(guerreiro);`.
   - *(Opcional)* Use `Thread.sleep(1500);` para dar uma pausa de 1,5 segundos entre os ataques e criar suspense no terminal!
5. Quando o loop terminar, verifique quem ficou vivo através do método `estaVivo()` e imprima o vencedor em destaque!

---

## 🌟 Desafio Extra (Para os mais rápidos)

Quer tornar o jogo mais imprevisível? Utilize a classe `java.util.Random` ou `Math.random()` dentro do método `atacar()` do Guerreiro para criar uma **"Chance de Acerto Crítico"**. Exemplo: 20% de probabilidade de o dano do ataque ser multiplicado por 2 naquele turno!
