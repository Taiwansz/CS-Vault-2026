# Atividade 3 — Painel de Controle do Drone

## Objetivo

Blindar o painel de controle da startup **DeliveryCopter** para que entradas inválidas nunca encerrem o programa abruptamente.

O programa solicita:

- **Altitude:** número inteiro entre `0` e `120` metros.
- **Velocidade:** número decimal entre `0.0` e `60.0` km/h.

Enquanto algum valor estiver incorreto, o sistema permanece em execução e solicita novamente a informação necessária.

## Conceitos aplicados

- `Scanner` para entrada de dados.
- Laço `while` para manter o painel ativo.
- `try/catch` para tratamento de falhas.
- `InputMismatchException` para letras ou símbolos no lugar de números.
- `IllegalArgumentException` lançada propositalmente com `throw new IllegalArgumentException(...)` quando o número viola a regra de negócio.
- `Locale pt-BR` para aceitar velocidade com vírgula, como `45,5`.

## Arquivo

- [PainelDrone.java](./PainelDrone.java)

## Como executar

```bash
javac PainelDrone.java
java PainelDrone
```

## Cenário de validação

Entrada:

```text
cem
500
40
45,5
```

Comportamento esperado:

```text
=== PAINEL DE CONTROLO DO DRONE ===
Digite a altitude desejada (0 a 120m):
> cem
[ALERTA CRÍTICO] Falha de comunicação: Digite apenas números! Pouso de emergência evitado.

Digite a altitude desejada (0 a 120m):
> 500
[ALERTA DE SEGURANÇA] Altitude inválida: O limite máximo é 120 metros.

Digite a altitude desejada (0 a 120m):
> 40
Altitude aceite. Digite a velocidade (0 a 60 km/h):
> 45,5
[SUCESSO] Dados validados. Drone em rota!
```

## Observação técnica

A variável `altitudeValida` permite que, depois de uma altitude correta, um erro apenas na velocidade não obrigue o operador a digitar a altitude novamente. Assim, o programa retoma exatamente do ponto que ainda precisa ser corrigido.
