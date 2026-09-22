# 2026-09-22 - Tratamento de Exceções em Java

**Disciplina:** Tópicos Especiais II
**Data:** 22/09/2026
**Tema:** Exceções em Java — try-catch, finally, throws, throw, exceções customizadas e loops resilientes

---

## Conceitos Cobertos

### 1. Hierarquia de Exceções em Java

```
Throwable
├── Error (erros irrecuperáveis da JVM — não tratar)
└── Exception
    ├── Checked Exceptions (compilador exige tratamento)
    │   └── IOException, SobrecargaException, etc.
    └── RuntimeException (Unchecked — opcional tratar)
        ├── ArithmeticException
        ├── NullPointerException
        ├── NumberFormatException
        └── IllegalArgumentException
```

---

## Missão 1 — try-catch com ArithmeticException

**Contexto:** Divisão inteira por zero derruba a fábrica.

**Regra:** `int / 0` lança `ArithmeticException` em tempo de execução.

```java
public class GeradorCentral {
    public static int calcularCarga(int cargaTotal, int geradoresAtivos) {
        try {
            return cargaTotal / geradoresAtivos;
        } catch (ArithmeticException e) {
            System.out.println("Alerta: divisao por zero!");
            return 0;
        }
    }
}
```

**Ponto técnico:** `double / 0` retorna `Infinity`, não lança exceção. Só `int / 0` dispara `ArithmeticException`.

---

## Missão 2 — Multi-catch (NumberFormatException + NullPointerException)

**Contexto:** Sensor transmite dados como String. Texto corrompido ou nulo geram falhas distintas.

```java
public class CaldeiraNorte {
    public static int lerPressao(String leitura) {
        try {
            return Integer.parseInt(leitura);
        } catch (NumberFormatException e) {
            return -1; // string não numérica ("ABC")
        } catch (NullPointerException e) {
            return -2; // string nula (null)
        }
    }
}
```

**Regra de ordem:** Quando as exceções são irmãs (sem herança entre elas), a ordem dos catch não importa. Se fossem pai/filho, o mais específico deve vir primeiro.

---

## Missão 3 — try-catch-finally (liberação de recurso físico)

**Contexto:** Trava mecânica DEVE ser liberada mesmo se o motor falhar.

```java
public class EsteiraControle {
    public static void operar(Motor motor, Trava trava, double velocidade) {
        try {
            motor.ativar(velocidade);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            trava.liberar(); // SEMPRE executa
        }
    }
}
```

**Garantia do finally:** Executa em todo cenário — sucesso, exceção capturada, e até se o catch lançar nova exceção.

---

## Missão 4 — throw + validação de argumento

**Contexto:** Braço robótico só aceita precisão entre 0.0 e 1.0.

```java
public class BracoRobotico {
    public void calibrar(double precisao) throws IllegalArgumentException {
        if (precisao < 0.0 || precisao > 1.0) {
            throw new IllegalArgumentException("Precisao invalida!");
        }
        System.out.println("Braço calibrado: " + precisao);
    }
}
```

**Erro clássico:** Usar `&&` em vez de `||` torna a condição impossível — nenhum número é simultaneamente `< 0.0` E `> 1.0`.

**Nota:** `throws` na assinatura é opcional para `RuntimeException` (Unchecked), mas serve como documentação de contrato.

---

## Missão 5 — Criação de Checked Exception customizada

**Contexto:** Domínio industrial exige exceção própria com semântica de negócio.

```java
public class SobrecargaException extends Exception {
    public SobrecargaException(String msg) {
        super(msg); // repassa para Exception — disponibiliza via e.getMessage()
    }
}
```

**Regra:**
- `extends Exception` → Checked — compilador obriga tratamento
- `extends RuntimeException` → Unchecked — tratamento opcional

---

## Missão 6 — throws + lançamento de exceção de domínio

**Contexto:** Sensor de pressão usa SobrecargaException para alertas críticos.

```java
public class SensorPressao {
    public void checarPressao(int bar) throws SobrecargaException {
        if (bar > 300) {
            throw new SobrecargaException("Pressao excede 300 bar!");
        }
        System.out.println("Pressão nominal: " + bar + " bar");
    }
}
```

**Fluxo:** Como é Checked, qualquer chamador é obrigado pelo compilador a envolver em try-catch ou redeclarar `throws SobrecargaException`.

---

## Missão 7 — Loop resiliente (try-catch dentro do loop)

**Contexto:** Lote de itens — um item corrompido não pode abortar os restantes.

```java
public class PainelExpedicao {
    public static void despacharLote(String[] itens) {
        for (String item : itens) {
            try {
                processar(item);
            } catch (Exception e) {
                System.out.println("Falha no item: " + item);
            }
        }
    }

    public static void processar(String item) throws Exception {
        if ("CORROMPIDO".equals(item)) throw new Exception("Dado invalido");
        System.out.println("Despachado: " + item);
    }
}
```

**Decisão arquitetural crítica:**
- try-catch FORA do loop → 1 falha aborta o lote inteiro
- try-catch DENTRO do loop → falha isolada por item, loop continua

---

## Tabela de Padrões

| Padrão | Quando usar |
|---|---|
| `try-catch` | Operação que pode falhar, com tratamento específico |
| `try-catch-finally` | Recursos físicos ou externos que precisam ser liberados |
| `throw` | Validar pré-condições e sinalizar violações de contrato |
| `throws` | Declarar que o método pode propagar uma exceção Checked |
| `extends Exception` | Criar exceção com semântica de domínio (Checked) |
| `extends RuntimeException` | Criar exceção de programação (Unchecked) |
| try-catch dentro de loop | Processar coleções com tolerância a falhas individuais |

---

## Referências Internas

- [[Lobo_Parietal]] — Stacks e padrões de engenharia
