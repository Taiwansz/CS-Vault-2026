# GUIA DE ESTUDOS — Tratamento de Exceções em Java
**Disciplina:** Tópicos Especiais II
**Autor:** ThSyr para Matheus (Taiwansz)
**Última atualização:** 2026-09-22

> Este guia foi construído a partir das 7 questões da prova de 22/09/2026.
> O objetivo é que você consiga resolver qualquer variação dessas questões sem auxílio.

---

## 1. MAPA MENTAL — A Hierarquia que Você Precisa Saber

```
Throwable
│
├── Error              ← JVM em colapso. NUNCA capture isso.
│
└── Exception
    │
    ├── [CHECKED]      ← Compilador OBRIGA tratamento (try-catch ou throws)
    │   ├── IOException
    │   ├── SQLException
    │   └── SuaExcecaoCustomizada extends Exception  ← você cria assim
    │
    └── RuntimeException  ← [UNCHECKED] Compilador não obriga
        ├── ArithmeticException       ← divisão int por zero
        ├── NullPointerException      ← acessar referência null
        ├── NumberFormatException     ← Integer.parseInt("ABC")
        ├── IllegalArgumentException  ← argumento inválido
        └── ArrayIndexOutOfBoundsException
```

**Regra de ouro:**
- `extends Exception` → Checked → compilador obriga tratamento
- `extends RuntimeException` → Unchecked → tratamento opcional

---

## 2. OS 5 BLOCOS FUNDAMENTAIS

### 2.1 try-catch básico
```java
try {
    // código de risco
} catch (TipoDeExcecao e) {
    // o que fazer quando falha
}
```

### 2.2 Multi-catch (várias exceções diferentes)
```java
try {
    // código de risco
} catch (NumberFormatException e) {
    // texto inválido
} catch (NullPointerException e) {
    // referência nula
}
// REGRA: se uma for subclasse da outra, a mais específica vem PRIMEIRO
```

### 2.3 try-catch-finally (recurso que SEMPRE precisa ser liberado)
```java
try {
    recurso.usar();
} catch (Exception e) {
    System.out.println(e.getMessage());
} finally {
    recurso.liberar(); // executa SEMPRE — com ou sem exceção
}
```

### 2.4 throw (lançar exceção manualmente)
```java
public void metodo(int valor) {
    if (valor < 0) {
        throw new IllegalArgumentException("Valor negativo!");
    }
}
```

### 2.5 throws (declarar que o método pode propagar)
```java
public void metodo() throws MinhaExcecao {
    throw new MinhaExcecao("algo deu errado");
}
```

---

## 3. EXCEÇÃO CUSTOMIZADA — Como criar do zero

```java
// Passo 1: herdar de Exception (Checked) ou RuntimeException (Unchecked)
public class MinhaExcecao extends Exception {

    // Passo 2: construtor que recebe mensagem e repassa para super
    public MinhaExcecao(String msg) {
        super(msg);
    }
}
```

**Para usar:**
```java
public void checar(int valor) throws MinhaExcecao {
    if (valor > 100) {
        throw new MinhaExcecao("Valor excede o limite!");
    }
}
```

---

## 4. LOOP RESILIENTE — A questão mais tricky

**Cenário:** processar uma lista onde itens podem falhar individualmente.

### ERRADO — try-catch fora do loop
```java
// Um item corrompido MATA o loop inteiro
try {
    for (String item : lista) {
        processar(item);  // falha aqui → para tudo
    }
} catch (Exception e) {
    // itens restantes nunca são processados
}
```

### CERTO — try-catch dentro do loop
```java
// Cada item é isolado — falha individual não derruba os outros
for (String item : lista) {
    try {
        processar(item);
    } catch (Exception e) {
        System.out.println("Falha no item: " + item);
        // loop CONTINUA para o próximo item
    }
}
```

**Memorize:** se o enunciado disser "não pode abortar o loop" ou "processar os restantes" → try-catch DENTRO.

---

## 5. ARMADILHAS DE PROVA (erros que você não pode cometer)

### Armadilha 1 — Operador lógico errado na validação
```java
// ERRADO — condição impossível (nenhum número é < 0 E > 1 ao mesmo tempo)
if (valor < 0 && valor > 1) { ... }

// CERTO
if (valor < 0 || valor > 1) { ... }
```

### Armadilha 2 — Ordem errada no multi-catch com herança
```java
// ERRADO — Exception captura tudo, NumberFormatException nunca é alcançado
catch (Exception e) { ... }
catch (NumberFormatException e) { ... }  // ← código morto

// CERTO — mais específico primeiro
catch (NumberFormatException e) { ... }
catch (Exception e) { ... }
```

### Armadilha 3 — Esquecer throws na assinatura para Checked Exception
```java
// ERRADO — compilador recusa se MinhaExcecao extends Exception
public void metodo() {
    throw new MinhaExcecao("erro");  // ← erro de compilação
}

// CERTO
public void metodo() throws MinhaExcecao {
    throw new MinhaExcecao("erro");
}
```

### Armadilha 4 — Esquecer o super(msg) na exceção customizada
```java
// ERRADO — mensagem não fica disponível via e.getMessage()
public MinhaExcecao(String msg) {
    // sem super(msg)
}

// CERTO
public MinhaExcecao(String msg) {
    super(msg);  // repassa para Exception
}
```

---

## 6. EXCEÇÕES MAIS COBRADAS EM PROVA — Cola Rápida

| Exceção | O que dispara | Tipo |
|---|---|---|
| `ArithmeticException` | `int / 0` | Unchecked |
| `NullPointerException` | acessar objeto null | Unchecked |
| `NumberFormatException` | `Integer.parseInt("ABC")` | Unchecked |
| `IllegalArgumentException` | argumento fora do domínio esperado | Unchecked |
| `ArrayIndexOutOfBoundsException` | índice além do tamanho do array | Unchecked |
| `IOException` | leitura/escrita de arquivo | Checked |

---

## 7. CHECKLIST ANTES DE ENTREGAR A PROVA

Antes de finalizar cada questão, passe por esse checklist:

- [ ] O bloco `try` contém apenas a operação de risco (não código desnecessário)
- [ ] O `catch` captura o tipo **correto** de exceção (não apenas `Exception` quando pedem específica)
- [ ] Se há herança entre exceções no multi-catch, a mais específica vem **primeiro**
- [ ] O `finally` existe quando o enunciado fala em "garantir", "sempre liberar", "mesmo se falhar"
- [ ] Se criei exceção customizada: `extends Exception` ou `extends RuntimeException` + `super(msg)`
- [ ] Se o método lança Checked Exception: `throws NomeExcecao` está na assinatura
- [ ] Se o enunciado pede para não abortar o loop: try-catch está **dentro** do for
- [ ] Condição de validação usa `||` (ou), não `&&` (e) quando são limites opostos

---

## 8. TEMPLATE MENTAL — Como ler o enunciado e agir

```
Enunciado diz...                     → Você faz...
─────────────────────────────────────────────────────────────────
"capturar ArithmeticException"       → try { divisao } catch (ArithmeticException e)
"imprimir e.getMessage()"            → System.out.println(e.getMessage())
"retornar valor seguro"              → return 0 (ou -1, -2 conforme enunciado)
"garantir liberação mesmo se falhar" → bloco finally
"não pode abortar o loop"            → try-catch DENTRO do for
"valores fora do intervalo"          → if (x < min || x > max) throw new ...
"crie uma exceção customizada"       → class XExcecao extends Exception
"checked exception"                  → extends Exception + throws na assinatura
"unchecked exception"                → extends RuntimeException
"texto corrompido / null"            → multi-catch NumberFormat + NullPointer
```

---

## 9. EXERCÍCIO DE FIXAÇÃO — Resolva sem consultar

**Tente implementar cada um abaixo antes de olhar o gabarito:**

1. Método `dividir(int a, int b)`: captura divisão por zero, retorna -1 com alerta.
2. Método `converter(String s)`: converte para Double, trata texto inválido (retorna 0.0) e null (retorna -1.0).
3. Método `abrirArquivo(Arquivo a, Lock lock)`: abre o arquivo no try, trata Exception, garante liberação do lock no finally.
4. Método `setIdade(int idade)`: lança IllegalArgumentException se idade < 0 ou > 150.
5. Classe `LimiteExcedidoException` Checked com construtor de String.
6. Método `verificarSaldo(double saldo)` que lança `LimiteExcedidoException` se saldo < 0.
7. Método `processarPedidos(String[] pedidos)`: processa cada um, falha individual não aborta o lote.

---

## 10. GABARITO DO EXERCÍCIO DE FIXAÇÃO

```java
// 1
public static int dividir(int a, int b) {
    try { return a / b; }
    catch (ArithmeticException e) {
        System.out.println("Alerta: divisao por zero!");
        return -1;
    }
}

// 2
public static double converter(String s) {
    try { return Double.parseDouble(s); }
    catch (NumberFormatException e) { return 0.0; }
    catch (NullPointerException e) { return -1.0; }
}

// 3
public static void abrirArquivo(Arquivo a, Lock lock) {
    try { a.abrir(); }
    catch (Exception e) { System.out.println(e.getMessage()); }
    finally { lock.liberar(); }
}

// 4
public void setIdade(int idade) throws IllegalArgumentException {
    if (idade < 0 || idade > 150)
        throw new IllegalArgumentException("Idade invalida!");
}

// 5
public class LimiteExcedidoException extends Exception {
    public LimiteExcedidoException(String msg) { super(msg); }
}

// 6
public void verificarSaldo(double saldo) throws LimiteExcedidoException {
    if (saldo < 0) throw new LimiteExcedidoException("Saldo negativo!");
}

// 7
public static void processarPedidos(String[] pedidos) {
    for (String pedido : pedidos) {
        try { processar(pedido); }
        catch (Exception e) { System.out.println("Falha: " + pedido); }
    }
}
```

---

*Registrado por ThSyr — Lobo Parietal: [[Java_Excecoes_e_Resiliencia]]*
