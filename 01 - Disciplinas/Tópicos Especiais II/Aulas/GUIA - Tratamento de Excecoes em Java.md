# GUIA COMPLETO — Tratamento de Exceções em Java
**Disciplina:** Tópicos Especiais II
**Autor:** ThSyr para Matheus (Taiwansz)
**Versão:** 2.0 — Reformulado com explicação de base
**Última atualização:** 2026-09-22

---

## ANTES DE TUDO — Por que exceções existem?

Imagine que você está dirigindo e o GPS diz "vire à direita em 200m".
Mas a rua foi bloqueada. O GPS não sabe o que fazer — ele **travou**.

Isso é o que acontece quando um programa Java tenta fazer algo impossível:
- Dividir um número por zero
- Ler um texto que não existe
- Transformar "ABC" em número

O Java **não trava em silêncio**. Ele lança uma **exceção** — uma mensagem de socorro que diz exatamente o que deu errado, onde e por quê.

Se você não capturar essa mensagem de socorro, o programa **cai**. Morreu. Encerrou.

O `try-catch` é a sua rede de segurança. Você diz:
> "Tente fazer isso. Se algo der errado, eu trato aqui — o programa não morre."

---

## PARTE 1 — O que é uma Exceção, de verdade?

Uma exceção é um **objeto Java** que representa um erro.
Quando algo dá errado, a JVM (a máquina que roda Java) cria esse objeto e o **lança** pelo ar.

Se ninguém **capturar** esse objeto, ele sobe pela pilha de chamadas até não ter mais ninguém — e o programa encerra com uma mensagem de erro feia na tela, chamada de **stack trace**.

Um stack trace parece assim:
```
Exception in thread "main" java.lang.ArithmeticException: / by zero
    at GeradorCentral.calcularCarga(GeradorCentral.java:4)
    at Main.main(Main.java:10)
```

**Como ler isso:**
- `java.lang.ArithmeticException` → qual foi a exceção
- `: / by zero` → o motivo
- `at GeradorCentral.calcularCarga(GeradorCentral.java:4)` → em qual classe, qual método, qual linha aconteceu
- `at Main.main(Main.java:10)` → quem chamou esse método

Você vai ver isso em prova prática. Leia de cima para baixo — a primeira linha é o problema, as seguintes são o caminho que o código percorreu até chegar lá.

---

## PARTE 2 — A Família das Exceções

Todas as exceções em Java fazem parte de uma família. É importante saber quem é filho de quem porque isso afeta como você captura.

```
Throwable  ← raiz de tudo que pode ser lançado
│
├── Error
│   └── OutOfMemoryError, StackOverflowError...
│       ↑ JVM em colapso. Não tem o que fazer. Nunca capture isso.
│
└── Exception  ← o que você vai lidar em 99% dos casos
    │
    ├── IOException, SQLException...
    │   ↑ CHECKED — compilador OBRIGA você a tratar
    │     Se você não tratar, o código NEM COMPILA
    │
    └── RuntimeException
        ├── ArithmeticException      ← int dividido por zero
        ├── NullPointerException     ← usar objeto que é null
        ├── NumberFormatException    ← parseInt("ABC")
        ├── IllegalArgumentException ← você mesmo lança quando o valor é inválido
        └── ArrayIndexOutOfBoundsException ← índice fora do array
            ↑ UNCHECKED — compilador não obriga tratar
              Mas se não tratar e acontecer, o programa morre
```

**Resumo que importa para a prova:**

| Tipo | Herda de | Compilador obriga? |
|---|---|---|
| Checked | `Exception` | SIM — trate ou declare `throws` |
| Unchecked | `RuntimeException` | NÃO — mas se acontecer sem catch, mata o programa |

---

## PARTE 3 — A Anatomia do try-catch

```java
try {
    // Coloque AQUI o código que pode dar errado
    // Só o código de risco — nada mais
} catch (TipoDeExcecao nomeVariavel) {
    // O que fazer SE der esse tipo de erro
    // nomeVariavel é o objeto da exceção — use e.getMessage() para ver a mensagem
}
```

**Por que só o código de risco no try?**
Porque o `try` é caro cognitivamente — ele monitora tudo que está dentro. Se você jogar código desnecessário dentro, fica difícil saber de onde veio o erro.

**O que é `e`?**
É o objeto da exceção capturada. Pense como um envelope com informações sobre o erro.
- `e.getMessage()` → abre o envelope e lê a mensagem de texto
- `e.getClass()` → diz o tipo da exceção
- `e.printStackTrace()` → imprime o stack trace completo

---

## PARTE 4 — Os 7 Padrões da Prova — Explicados do Zero

---

### Padrão 1 — try-catch simples (ArithmeticException)

**O problema:** divisão de inteiro por zero.

Por que `int / 0` é diferente de `double / 0`?
- `double / 0` → Java retorna `Infinity` (infinito matemático). Não quebra.
- `int / 0` → Java lança `ArithmeticException`. Quebra. Números inteiros não têm representação de infinito.

**A solução:**
```java
public class GeradorCentral {
    public static int calcularCarga(int cargaTotal, int geradoresAtivos) {
        try {
            return cargaTotal / geradoresAtivos; // ← pode lançar ArithmeticException
        } catch (ArithmeticException e) {
            // chegamos aqui SOMENTE se geradoresAtivos for zero
            System.out.println("Alerta: divisao por zero!");
            return 0; // valor seguro — o programa continua
        }
    }
}
```

**O fluxo na cabeça:**
```
calcularCarga(1000, 5)  → tudo certo → retorna 200
calcularCarga(1000, 0)  → ArithmeticException → catch captura → imprime alerta → retorna 0
```

---

### Padrão 2 — Multi-catch (várias exceções diferentes)

**O problema:** o mesmo bloco try pode lançar tipos diferentes de exceção.

Quando você chama `Integer.parseInt(leitura)`:
- Se `leitura` for `"ABC"` → `NumberFormatException` (texto não é número)
- Se `leitura` for `null` → `NullPointerException` (não existe nada para converter)

São erros diferentes com causas diferentes. Você quer respostas diferentes para cada um.

**A solução:**
```java
public class CaldeiraNorte {
    public static int lerPressao(String leitura) {
        try {
            return Integer.parseInt(leitura);
        } catch (NumberFormatException e) {
            // o texto veio mas não era número válido
            return -1;
        } catch (NullPointerException e) {
            // não veio nada — referência null
            return -2;
        }
    }
}
```

**Regra de ordem dos catch:**
- `NumberFormatException` e `NullPointerException` são irmãs — nenhuma é pai da outra. Ordem não importa entre elas.
- Se fosse `Exception` (pai) e `NumberFormatException` (filho), o pai deve vir POR ÚLTIMO. Se o pai vier primeiro, ele engole tudo e o filho nunca é alcançado.

```java
// ERRADO — Exception captura tudo, NumberFormatException nunca executa
} catch (Exception e) { ... }
} catch (NumberFormatException e) { ... }  // código morto

// CERTO — específico primeiro
} catch (NumberFormatException e) { ... }
} catch (Exception e) { ... }
```

---

### Padrão 3 — finally (garantia absoluta de execução)

**O problema:** alguns recursos precisam ser liberados SEMPRE.
Arquivos, conexões, travas físicas — se você esquecer de liberar, o sistema trava.

O `finally` executa em TODO cenário:
- ✅ O try funcionou normalmente
- ✅ O try lançou uma exceção que o catch capturou
- ✅ O catch lançou outra exceção
- ✅ Alguém chamou `return` dentro do try

A única exceção à regra: `System.exit()` — que encerra a JVM na força bruta.

**A solução:**
```java
public class EsteiraControle {
    public static void operar(Motor motor, Trava trava, double velocidade) {
        try {
            motor.ativar(velocidade); // pode lançar qualquer Exception
        } catch (Exception e) {
            // qualquer erro — imprime a mensagem
            System.out.println(e.getMessage());
        } finally {
            trava.liberar(); // SEMPRE executa — não importa o que aconteceu acima
        }
    }
}
```

**Por que não colocar `trava.liberar()` só no catch?**
Porque se o `try` funcionar sem erro, o `catch` não executa — e a trava ficaria presa.
O `finally` resolve isso: executa nos dois cenários.

---

### Padrão 4 — throw (você mesmo lança a exceção)

**O problema:** a lógica do seu código tem regras. Se alguém passar um valor inválido, você quer sinalizar isso ativamente.

`throw` = você pegando a exceção e lançando você mesmo.

**A solução:**
```java
public class BracoRobotico {
    public void calibrar(double precisao) throws IllegalArgumentException {
        if (precisao < 0.0 || precisao > 1.0) {
            throw new IllegalArgumentException("Precisao invalida!");
            // a linha abaixo NUNCA executa se chegou aqui
        }
        System.out.println("Braço calibrado: " + precisao);
    }
}
```

**Por que `||` e não `&&`?**
A condição é: "se o valor estiver fora do intervalo [0.0, 1.0]".
- Fora do intervalo = menor que 0.0 OU maior que 1.0
- Com `&&`: seria "menor que 0.0 E maior que 1.0 ao mesmo tempo" — impossível

Pense assim: você quer rejeitar valores abaixo de 0 e também valores acima de 1.
São duas condições separadas de rejeição — logo `||`.

---

### Padrão 5 — Exceção customizada (você cria a sua própria)

**O problema:** as exceções genéricas do Java não carregam semântica do seu domínio.
`Exception("erro")` não diz nada. `SobrecargaException("Pistão em risco")` diz muito.

**Como criar:**
```java
// Passo 1: declare a classe herdando de Exception (Checked) ou RuntimeException (Unchecked)
public class SobrecargaException extends Exception {

    // Passo 2: construtor que recebe a mensagem
    public SobrecargaException(String msg) {
        super(msg); // ← OBRIGATÓRIO — repassa para Exception guardar a mensagem
    }
}
```

**O que o `super(msg)` faz?**
`Exception` (a classe pai) tem um campo interno que guarda a mensagem.
Quando você chama `super(msg)`, você está dizendo: "pai, guarda essa mensagem pra mim".
Depois, quem capturar sua exceção consegue recuperar com `e.getMessage()`.

Se você esquecer o `super(msg)`, `e.getMessage()` retorna `null`.

---

### Padrão 6 — throws na assinatura + lançamento de Checked Exception

**O problema:** quando seu método pode lançar uma Checked Exception, o compilador exige que você declare isso na assinatura. É um contrato público: "quem me chamar, saiba que posso lançar isso."

```java
public class SensorPressao {
    //                    ↓ declaração obrigatória para Checked Exception
    public void checarPressao(int bar) throws SobrecargaException {
        if (bar > 300) {
            throw new SobrecargaException("Pressao excede 300 bar!");
        }
        System.out.println("Pressão nominal: " + bar + " bar");
    }
}
```

**Quem chama esse método é obrigado a tratar:**
```java
try {
    sensor.checarPressao(450);
} catch (SobrecargaException e) {
    System.out.println("ALERTA: " + e.getMessage());
}
// Se não colocar o try-catch aqui, o código NEM COMPILA
```

**`throw` vs `throws` — a confusão mais comum:**

| Palavra | Onde fica | O que faz |
|---|---|---|
| `throw` | Dentro do método | Lança a exceção agora |
| `throws` | Na assinatura do método | Avisa que o método PODE lançar |

```java
//                              throws — na assinatura
public void metodo() throws MinhaExcecao {
    throw new MinhaExcecao("erro"); // throw — dentro do método
}
```

---

### Padrão 7 — Loop resiliente (try-catch dentro do for)

**O problema:** você tem uma lista de itens para processar. Um item pode estar corrompido.
Se um falha, os outros não podem ser prejudicados.

**A decisão arquitetural:**

```java
// ERRADO — try fora do loop
// Um item corrompido para TUDO
try {
    for (String item : itens) {
        processar(item);
    }
} catch (Exception e) {
    System.out.println("Falha: " + item);
    // os itens depois do corrompido NUNCA são processados
}
```

```java
// CERTO — try dentro do loop
// Cada item tem sua própria rede de segurança
for (String item : itens) {
    try {
        processar(item);
    } catch (Exception e) {
        System.out.println("Falha no item: " + item);
        // o loop CONTINUA — próxima iteração começa normalmente
    }
}
```

**Como memorizar:** o `catch` não para o loop. Ele trata o erro e a iteração termina normalmente — a próxima começa. É como um funcionário que tropeça mas não cai: ele se levanta e continua a linha de produção.

---

## PARTE 5 — Fluxograma de Decisão em Prova

Use isso para decodificar o enunciado rapidamente:

```
Enunciado menciona...
│
├── "capturar" ou "tratar" um erro específico
│   └── → try { código } catch (TipoExato e) { tratamento }
│
├── "garantir", "sempre", "mesmo se falhar", "recurso físico"
│   └── → finally { liberação }
│
├── "dois tipos de erro diferentes"
│   └── → multi-catch com dois blocos catch
│
├── "valor inválido", "fora do intervalo", "dispare uma exceção"
│   └── → if (condição inválida) throw new TipoExcecao("mensagem")
│         → || para limites opostos (< min OU > max)
│
├── "crie uma exceção customizada"
│   └── → class NomeException extends Exception { ... }
│         → Checked? extends Exception
│         → Unchecked? extends RuntimeException
│
├── "método que lança exceção checada"
│   └── → void metodo() throws NomeException { ... }
│
└── "processar lote", "não abortar", "continuar mesmo com falha"
    └── → for (item : itens) { try { } catch { } }
          → try-catch DENTRO do for
```

---

## PARTE 6 — Como Ler um Stack Trace

Quando o Java imprime um erro na tela sem você ter tratado, parece assim:

```
Exception in thread "main" java.lang.ArithmeticException: / by zero
    at GeradorCentral.calcularCarga(GeradorCentral.java:4)
    at Main.main(Main.java:10)
```

**Linha por linha:**

```
Exception in thread "main"          → aconteceu na thread principal
java.lang.ArithmeticException       → tipo da exceção (caminho completo do pacote)
: / by zero                         → mensagem — o que realmente deu errado
at GeradorCentral.calcularCarga(...)→ onde aconteceu (classe.método, arquivo:linha)
at Main.main(Main.java:10)          → quem chamou o método que falhou
```

**Regra prática:**
- Leia a primeira linha → descobre o tipo e o motivo
- Leia a segunda linha → descobre onde corrigir
- As linhas abaixo são o caminho de quem chamou — útil para contexto

---

## PARTE 7 — Armadilhas Clássicas

### Armadilha 1 — `&&` no lugar de `||`
```java
// ERRADO — nenhum número pode ser < 0 E > 1 ao mesmo tempo
if (valor < 0 && valor > 1) // nunca é verdade

// CERTO — rejeitar o que está abaixo OU acima do intervalo
if (valor < 0 || valor > 1)
```

### Armadilha 2 — Catch na ordem errada (pai antes do filho)
```java
// ERRADO
catch (Exception e) { }           // engole tudo
catch (NumberFormatException e) { } // nunca executa

// CERTO
catch (NumberFormatException e) { } // específico primeiro
catch (Exception e) { }           // genérico por último
```

### Armadilha 3 — Esquecer `throws` para Checked Exception
```java
// ERRADO — não compila se SobrecargaException extends Exception
public void metodo() {
    throw new SobrecargaException("erro");
}

// CERTO
public void metodo() throws SobrecargaException {
    throw new SobrecargaException("erro");
}
```

### Armadilha 4 — Esquecer `super(msg)` na exceção customizada
```java
// ERRADO — e.getMessage() vai retornar null
public class MinhaExcecao extends Exception {
    public MinhaExcecao(String msg) {
        // sem super(msg) — mensagem perdida
    }
}

// CERTO
public class MinhaExcecao extends Exception {
    public MinhaExcecao(String msg) {
        super(msg); // mensagem salva no pai
    }
}
```

### Armadilha 5 — try-catch fora do loop quando o enunciado pede resiliência
```java
// ERRADO para "não abortar o lote"
try { for (item : lista) { processar(item); } } catch (Exception e) { }

// CERTO
for (item : lista) { try { processar(item); } catch (Exception e) { } }
```

---

## PARTE 8 — Exercícios de Fixação (contextos variados)

**Regra: tente resolver sem olhar o gabarito. Só consulte depois.**

**1.** Uma pizzaria recebe pedidos como String. Se o texto for inválido converta para -1, se for null converta para -2. Método: `static int lerNumeroPedido(String s)`.

**2.** Um elevador só opera entre os andares 1 e 50. Crie a exceção `AndarInvalidoException` (Checked) e o método `irPara(int andar) throws AndarInvalidoException` que a lança se o andar for inválido.

**3.** Uma impressora tem um cartucho que deve ser liberado mesmo se a impressão falhar. Método: `imprimir(Documento doc, Cartucho c)` — use try-catch-finally.

**4.** Um sistema de notas recebe um array de Strings com notas dos alunos. Algumas podem estar corrompidas. Converta cada uma com `Double.parseDouble()`, trate falhas individualmente e imprima "Nota ignorada: [valor]" quando falhar.

**5.** Um banco tenta sacar um valor. Se o saldo for insuficiente, lance `SaldoInsuficienteException` (Checked) com a mensagem "Saldo insuficiente para saque de R$ X". Crie a exceção e o método `sacar(double valor, double saldo)`.

---

## PARTE 9 — Gabarito dos Exercícios

```java
// 1 — Pizzaria
public static int lerNumeroPedido(String s) {
    try {
        return Integer.parseInt(s);
    } catch (NumberFormatException e) {
        return -1;
    } catch (NullPointerException e) {
        return -2;
    }
}

// 2a — Exceção customizada
public class AndarInvalidoException extends Exception {
    public AndarInvalidoException(String msg) {
        super(msg);
    }
}

// 2b — Método do elevador
public void irPara(int andar) throws AndarInvalidoException {
    if (andar < 1 || andar > 50) {
        throw new AndarInvalidoException("Andar invalido: " + andar);
    }
    System.out.println("Indo para o andar " + andar);
}

// 3 — Impressora
public void imprimir(Documento doc, Cartucho c) {
    try {
        doc.imprimir();
    } catch (Exception e) {
        System.out.println(e.getMessage());
    } finally {
        c.liberar();
    }
}

// 4 — Notas resilientes
public static void processarNotas(String[] notas) {
    for (String nota : notas) {
        try {
            double valor = Double.parseDouble(nota);
            System.out.println("Nota: " + valor);
        } catch (Exception e) {
            System.out.println("Nota ignorada: " + nota);
        }
    }
}

// 5a — Exceção customizada
public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String msg) {
        super(msg);
    }
}

// 5b — Método saque
public void sacar(double valor, double saldo) throws SaldoInsuficienteException {
    if (saldo < valor) {
        throw new SaldoInsuficienteException(
            "Saldo insuficiente para saque de R$ " + valor
        );
    }
    System.out.println("Saque de R$ " + valor + " realizado.");
}
```

---

## COLA RÁPIDA — Para Revisar na Véspera da Prova

```
EXCEÇÃO           → O QUE CAUSA
ArithmeticEx      → int / 0
NullPointerEx     → objeto.método() quando objeto é null
NumberFormatEx    → Integer.parseInt("ABC")
IllegalArgumentEx → você lança quando o valor não faz sentido
ArrayIndexEx      → array[10] quando o array tem 5 posições

PALAVRA-CHAVE     → ONDE FICA     → O QUE FAZ
try               → bloco         → tenta executar o código de risco
catch             → bloco         → captura e trata a exceção
finally           → bloco         → SEMPRE executa (liberar recursos)
throw             → dentro método → lança a exceção agora
throws            → na assinatura → avisa que o método pode lançar

HERANÇA           → TIPO          → COMPILADOR OBRIGA?
extends Exception → Checked       → SIM — trate ou declare throws
extends RuntimeEx → Unchecked     → NÃO — mas se não tratar, mata o programa

LOOP              → COMPORTAMENTO
try fora do for   → 1 falha = loop para
try dentro do for → 1 falha = item ignorado, loop continua
```

---

*Registrado por ThSyr — Lobo Parietal: [[Java_Excecoes_e_Resiliencia]]*
*Versão 2.0 — Calibrada para Matheus: explica o porquê de cada mecanismo, não só o como.*
