---
tipo: guia-enade
disciplina: "Engenharia de Software e Orientacao a Objetos"
nivel: "Do Basico ao Avancado ENADE"
data: 2026-11-09
autor: "ThSyr Cognitive Sentinel"
---

# 07 — Engenharia de Software, SOLID e Padroes de Projeto (Do Basico ao Nivel ENADE)

> [!INFO] **Objetivo deste Guia**
> Este modulo aborda os pilares da construcao de software robusto, manutenivel e testavel: processos de software (Cascata vs. Ageis), os 5 principios SOLID, os padroes de projeto GoF mais cobrados e as novidades de imutabilidade e tipagem segura do Java Moderno.

---

## CAMADA 1: O BASICO (PROCESSOS DE SOFTWARE E CICLOS DE VIDA)

- **Modelo em Cascata (Waterfall):** Fases sequenciais e rigidas (Requisitos -> Projeto -> Implementacao -> Testes -> Implantacao). Nao aceita mudancas retroativas facilmente. Ideal apenas para sistemas com requisitos 100% estaveis e regulatorios criticos.
- **Modelo Espiral (Barry Boehm):** Processo iterativo centrado na **analise e mitigacao explicita de riscos** a cada volta da espiral.
- **Metodologias Ageis (Scrum / Kanban):** Foco em entregas incrementais frequentes e adaptacao a mudancas.
  - *Scrum:* Timebox fixo (**Sprints** de 1 a 4 semanas). Papeis definidos: *Product Owner* (define prioridades do backlog), *Scrum Master* (remove impedimentos e zela pelo processo) e *Time de Desenvolvimento* (multidisciplinar e auto-organizavel). Cerimonias: Sprint Planning, Daily Scrum (15 min), Sprint Review e Retrospectiva.
  - *Kanban:* Fluxo continuo, visualizacao do fluxo em quadro e **imposicao de limites de trabalho em progresso (WIP — Work in Progress)** para evitar gargalos operacionais.

---

## CAMADA 2: OS PRINCIPIOS SOLID (ROBERT C. MARTIN)

O SOLID e a cartilha definitiva de boas praticas de Orientacao a Objetos para evitar software fragil, rigido e imovel.

### S — Single Responsibility Principle (Principio da Responsabilidade Unica)
*"Uma classe deve ter um, e apenas um, motivo para mudar."*
- Uma classe de `Relatorio` nao deve calcular metricas financeiras, formatar o layout em HTML e salvar os bytes no banco de dados. Cada responsabilidade pertence a uma classe isolada.

### O — Open/Closed Principle (Principio Aberto/Fechado)
*"Entidades de software devem ser abertas para extensao, mas fechadas para modificacao."*
- Devemos ser capazes de introduzir novos comportamentos (ex: um novo tipo de desconto ou meio de pagamento) adicionando novas classes derivadas de uma interface, sem alterar o codigo das classes existentes testadas.

### L — Liskov Substitution Principle (Principio da Substituicao de Liskov)
*"Subtipos devem ser substituiveis pelos seus tipos base sem quebrar o comportamento do programa."*
- O classico exemplo: Fazer `Quadrado` herdar de `Retangulo`. Se alterar a largura de um retangulo altera a altura de forma oculta no quadrado, o contrato foi violado. Uma funcao que espera a classe base deve funcionar de forma transparente com qualquer subclasse.

### I — Interface Segregation Principle (Principio da Segregacao de Interfaces)
*"Muitas interfaces especificas para o cliente sao melhores do que uma interface geral unica."*
- Nao obrigue uma classe a implementar metodos que ela nao usa. Em vez de uma interface gorda `DispositivoMultifuncional` com `imprimir()`, `escanear()` e `fax()`, crie interfaces coesas `Imprime`, `Escaneia`.

### D — Dependency Inversion Principle (Principio da Inversao de Dependencia)
*"Módulos de alto nivel nao devem depender de modulos de baixo nivel. Ambos devem depender de abstracoes. Abstracoes nao devem depender de detalhes; detalhes devem depender de abstracoes."*

#### O Caso Pratico Classico do ENADE:
```java
// CODIGO ACOPLADO E ERRADO (Viola DIP):
public class ProcessadorPagamento {
    // Depende diretamente da classe concreta de banco
    private MySQLPedidoRepository repository = new MySQLPedidoRepository();
    
    public void processar(Pedido p) {
        repository.salvar(p);
    }
}

// CODIGO REFATORADO CORRETO (Respeita DIP via Injecao de Dependencia):
public class ProcessadorPagamento {
    // Depende de uma interface abstrata
    private final PedidoRepository repository;
    
    // Injetado de fora pelo construtor publico
    public ProcessadorPagamento(PedidoRepository repository) {
        this.repository = repository;
    }
    
    public void processar(Pedido p) {
        this.repository.salvar(p);
    }
}
```

---

## CAMADA 3: PADROES DE PROJETO (GOF — GANG OF FOUR)

Classificados em 3 macro-categorias:

### 3.1 Criacionais (Mecanismos de Instanciacao de Objetos)
- **Factory Method:** Define uma interface para criar um objeto, mas deixa as subclasses decidirem qual classe instanciar.
- **Singleton:** Garante que uma classe tenha apenas uma instancia global em toda a aplicacao e fornece um ponto de acesso global a ela (cuidado com problemas em testes unitarios).
- **Builder:** Separa a construcao de um objeto complexo de sua representacao, permitindo construir passo a passo objetos com dezenas de parametros opcionais.

### 3.2 Estruturais (Composicao de Classes e Objetos)
- **Adapter:** Converte a interface de uma classe em outra interface esperada pelos clientes, permitindo que classes com interfaces incompativeis trabalhem juntas (funciona como um adaptador de tomada).
- **Decorator:** Anexa responsabilidades adicionais a um objeto dinamicamente, oferecendo uma alternativa flexivel a heranca (ex: streams do Java `new BufferedReader(new FileReader("arq.txt"))`).
- **Composite:** Agrupa objetos em estruturas de arvore para representar hierarquias parte-todo (trata objetos individuais e composicoes de objetos de forma uniforme).

### 3.3 Comportamentais (Algoritmos e Atribuicao de Responsabilidades)
- **Strategy:** Define uma familia de algoritmos, encapsula cada um deles e os torna intercambiaveis. Permite que o algoritmo varie independentemente dos clientes que o utilizam (ex: diferentes calculos de frete).
- **Observer:** Define uma dependencia um-para-muitos entre objetos; quando um objeto muda de estado, todos os seus dependentes sao notificados e atualizados automaticamente (padrao Publisher-Subscriber).

---

## CAMADA 4: JAVA MODERNO E QUALIDADE ARQUITETURAL

### 4.1 Records (Java 16) — DTOs Imutaveis Transparentes
- Sintaxe compacta para classes portadoras de dados imutaveis:
```java
public record Ponto(int x, int y) {}
```
- Herda implicitamente de `java.lang.Record` (proibido usar `extends` de outra classe).
- Seus atributos sao implicitamente `private final`.
- O compilador sintetiza automaticamente: construtor canonico, acessores `x()` e `y()`, `equals()`, `hashCode()` e `toString()`.

### 4.2 Sealed Classes (Java 17) & Pattern Matching no Switch (Java 21)
- Permite restringir exatamente quais classes ou interfaces podem estende-la usando a clausula `permits`:
```java
public sealed interface Pagamento permits Pix, Cartao, Boleto {}
```
- Combinado com o Pattern Matching, o compilador realiza o **Exhaustiveness Check (Verificacao de Exaustividade)**: como todos os subtipos autorizados foram tratados no `switch`, a clausula `default:` torna-se dispensavel.

### 4.3 Boas Praticas de Optional
- O objetivo do `Optional<T>` e ser **tipo de retorno de metodos** para eliminar `NullPointerException`.
- **Anti-padrao:** Chamar `.get()` diretamente sem testar `.isPresent()`.
- **Padrao Moderno:** Encadeamento funcional:
```java
return repo.buscarPorId(id)
           .filter(Usuario::isAtivo)
           .map(Usuario::getEmail)
           .orElse("contato@empresa.com");
```

---

## CAMADA 5: ARMADILHAS CLASSICAS DO ENADE

1. **Injecao de Dependencias vs. Inversao de Dependencias:** Inversao de Dependencia (DIP) e o **principio arquitetural** (depender de interfaces). Injecao de Dependencia (DI) e o **padrao de implementacao** (passar o objeto pelo construtor ou anotação `@Autowired`).
2. **Exaustividade em Interfaces Seladas:** Se uma questao perguntar se o `switch` sobre uma classe selada exige `default:`, a resposta e **NAO**, desde que todos os subtipos da clausula `permits` estejam cobertos.
3. **Pirataria de Codigo em Records:** O construtor compacto de um record (`public Ponto { ... }`) nao permite fazer atribuicoes explicitas do tipo `this.x = x`. A atribuicao e automatica do compilador apos o termino das validacoes.
