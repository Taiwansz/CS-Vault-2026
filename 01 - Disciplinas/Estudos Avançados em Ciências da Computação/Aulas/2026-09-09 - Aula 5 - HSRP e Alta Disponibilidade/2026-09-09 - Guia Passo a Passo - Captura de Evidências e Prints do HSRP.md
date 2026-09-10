# Guia Passo a Passo: Captura de Evidências e Prints do HSRP

**Data:** 2026-09-09  
**Disciplina:** Estudos Avançados em Ciências da Computação  
**Professor:** Paulo Sérgio Granato  
**Turma:** N13208A (Semestre 2026.2)  
**Grupo:** 03  
**Integrantes:**
- Matheus Sousa dos Santos (RA: 52319400)
- Felipe Guarnieri Pinete (RA: 52319337)
- Guilherme Gustavo Weber (RA: 52420339)

---

## 1. Objetivo deste Guia

Este documento orienta a captura das 5 evidências obrigatórias exigidas na **Seção 17** da atividade prática de **HSRP e Alta Disponibilidade**. 

Para executar os passos, utilize o arquivo de simulação já configurado:
- Caminho no Vault: `CS-Vault-2026/01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/GRUPO_03_HSRP_ALTA_DISPONIBILIDADE.pkt`
- Caminho em Downloads: `C:\Users\52319400\Downloads\GRUPO_03_HSRP_ALTA_DISPONIBILIDADE.pkt`

> **Atalho recomendado no Windows para capturar a tela:**  
> Pressione as teclas `Win + Shift + S`, selecione a área desejada e salve a imagem.

---

## 2. Evidência 1: Topologia Completa

### O que o professor exige
Mostrar todos os equipamentos montados, seus nomes obrigatórios e as conexões físicas.

### Onde e como fazer
1. Abra o arquivo `GRUPO_03_HSRP_ALTA_DISPONIBILIDADE.pkt` no Cisco Packet Tracer.
2. Na tela principal (Logical Workspace), verifique se todos os dispositivos estão visíveis:
   - `R1-PRINCIPAL` (Cisco 2911)
   - `R2-BACKUP` (Cisco 2911)
   - `SW1-LAN` (Cisco 2960)
   - `PC1`
   - `PC2`
   - `SERVER1`
3. Certifique-se de que todos os cabos diretos estão conectados e com luzes verdes operacionais.

### O que deve aparecer no print
- A visão panorâmica da rede montada.
- Todos os 6 dispositivos com seus rótulos visíveis.
- Os links em estado ativo (pontos verdes).

**Sugestão de nome do arquivo:** `evidencia_01_topologia_completa.png`

---

## 3. Evidência 2: HSRP em Operação Normal (Active e Standby)

### O que o professor exige
Mostrar o estado dos dois roteadores antes de qualquer falha:
- `R1-PRINCIPAL` = `Active`
- `R2-BACKUP` = `Standby`

### Onde e como fazer
1. Clique no roteador `R1-PRINCIPAL` e acesse a aba **CLI**.
2. Pressione `Enter`, digite os comandos abaixo e tecle Enter:
   ```text
   enable
   show standby brief
   ```
3. Mantenha a janela do R1 aberta.
4. Clique no roteador `R2-BACKUP` e acesse a aba **CLI**.
5. Digite os comandos:
   ```text
   enable
   show standby brief
   ```
6. Posicione as duas janelas CLI lado a lado na tela.

### O que deve aparecer no print
- **No R1-PRINCIPAL:**
  - Interface: `Gi0/0`
  - Group: `1`
  - Priority: `110`
  - State: `Active`
  - Active: `local`
  - Standby: `192.168.10.3`
  - Virtual IP: `192.168.10.1`
- **No R2-BACKUP:**
  - Interface: `Gi0/0`
  - Group: `1`
  - Priority: `90`
  - State: `Standby`
  - Active: `192.168.10.2`
  - Standby: `local`
  - Virtual IP: `192.168.10.1`

**Sugestão de nome do arquivo:** `evidencia_02_hsrp_estado_normal.png`

---

## 4. Evidência 3: Testes de Comunicação Inicial

### O que o professor exige
Comprovar que os computadores alcançam o Gateway Virtual e os demais nós da rede.

### Onde e como fazer
1. Clique no `PC1`.
2. Acesse a aba **Desktop** e abra o aplicativo **Command Prompt**.
3. Execute os seguintes comandos de teste:
   ```cmd
   ping 192.168.10.1
   ping 192.168.10.2
   ping 192.168.10.3
   ping 192.168.10.20
   ping 192.168.10.100
   ```
4. Se o primeiro pacote de algum ping falhar por resolução de ARP (`Request timed out`), repita o comando para obter 100% de taxa de sucesso (`Lost = 0`).

### O que deve aparecer no print
- A janela do Prompt de Comando do `PC1`.
- As respostas `Reply from 192.168.10.1: bytes=32 time<1ms TTL=255`.
- As estatísticas indicando sucesso na comunicação com o Gateway Virtual, roteadores físicos, PC2 e SERVER1.

**Sugestão de nome do arquivo:** `evidencia_03_testes_ping_comunicacao.png`

---

## 5. Evidência 4: Simulação de Falha no Roteador Principal

### O que o professor exige
Mostrar o `R1-PRINCIPAL` indisponível.

### Onde e como fazer
1. Clique no `R1-PRINCIPAL` e abra a aba **CLI**.
2. Digite os seguintes comandos para derrubar a interface conectada à rede local:
   ```text
   enable
   configure terminal
   interface GigabitEthernet0/0
   shutdown
   ```
3. O roteador exibirá o log no terminal:
   `%LINK-5-CHANGED: Interface GigabitEthernet0/0, changed state to administratively down`
   `%LINEPROTO-5-UPDOWN: Line protocol on Interface GigabitEthernet0/0, changed state to down`
4. Deixe a janela do CLI aberta com essas mensagens e posicione-a de forma que dê para ver a topologia ao fundo.

### O que deve aparecer no print
- O terminal do `R1-PRINCIPAL` com o comando `shutdown` e as mensagens de interface *down*.
- Os triângulos de link entre o `R1-PRINCIPAL` e o `SW1-LAN` vermelhos (apagados), confirmando a falha física.

**Sugestão de nome do arquivo:** `evidencia_04_falha_r1_shutdown.png`

---

## 6. Evidência 5: Failover Concluído e Continuidade de Tráfego

### O que o professor exige
Mostrar:
1. `R2-BACKUP` assumindo o estado `Active`.
2. A comunicação dos computadores continuando normalmente através do Gateway Virtual.

### Onde e como fazer
1. Aguarde cerca de 10 segundos após a queda do R1 (tempo para expirar o *Holdtime* do HSRP).
2. Clique no `R2-BACKUP`, abra a aba **CLI** e execute:
   ```text
   show standby brief
   ```
   *Observe que o campo State mudou de `Standby` para `Active`.*
3. Imediatamente abra o `PC1` > **Desktop** > **Command Prompt** e execute novamente:
   ```cmd
   ping 192.168.10.1
   ping 192.168.10.100
   ```
4. Posicione a janela do CLI do R2 ao lado do Command Prompt do PC1.

### O que deve aparecer no print
- A tabela do `R2-BACKUP` com `State: Active` e `Active: local`.
- O prompt do `PC1` respondendo ao ping para o gateway virtual `192.168.10.1` e para o `SERVER1` sem que nenhuma configuração do computador tenha sido alterada.

**Sugestão de nome do arquivo:** `evidencia_05_failover_r2_active_ping.png`

---

## 7. Passo Adicional: Restauração do R1 (Preempt)

Para deixar a simulação pronta novamente após os prints:
1. Acesse o CLI do `R1-PRINCIPAL`.
2. Digite:
   ```text
   enable
   configure terminal
   interface GigabitEthernet0/0
   no shutdown
   end
   ```
3. Em poucos segundos o enlace volta a ficar verde e, devido à instrução `standby 1 preempt`, o `R1` reassume automaticamente como `Active`.

---

## 8. Tabela Resumo para Entrega

| Evidência | Janela / Tela do Packet Tracer | Ação Principal | Nome Recomendado do Arquivo |
| :--- | :--- | :--- | :--- |
| **1. Topologia** | Tela inicial da topologia lógica | Capturar visão geral com nomes e cabos verdes | `evidencia_01_topologia_completa.png` |
| **2. HSRP Normal** | CLI do R1 e CLI do R2 | Executar `show standby brief` nos dois | `evidencia_02_hsrp_estado_normal.png` |
| **3. Pings** | Desktop > Command Prompt do PC1 | Disparar pings para `192.168.10.1`, `R1`, `R2`, `PC2` e `SERVER1` | `evidencia_03_testes_ping_comunicacao.png` |
| **4. Falha de R1** | CLI do R1 + Enlace no simulador | Aplicar `shutdown` na Gi0/0 de R1 | `evidencia_04_falha_r1_shutdown.png` |
| **5. Failover** | CLI do R2 + Command Prompt do PC1 | Executar `show standby brief` em R2 e ping no PC1 | `evidencia_05_failover_r2_active_ping.png` |
