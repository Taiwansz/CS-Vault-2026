# Roteiro de Montagem, Validacao e Testes — Projeto Integrador NexaTech

Siga exatamente esta sequencia no **Cisco Packet Tracer 8.2.1** para gerar o arquivo unico `Projeto_Integrador_Redes_NexaTech.pkt` com pontuacao maxima. Tempo estimado: 45 a 60 minutos.

---

## 1. Instalacao do Modulo PTBuilder e Geracao da Topologia Base

1. Abra o **Cisco Packet Tracer 8.2.1**.
2. No menu superior, acesse **Extensions > Scripting > Configure PT Script Modules**.
3. Clique em **Add...** e selecione:
   `C:\Users\52319400\Downloads\INSTALADORES\Builder.pts`
4. Feche a janela de configuracao de modulos.
5. Acesse **Extensions > Builder Code Editor**.
6. Cole o conteudo de `NexaTech_PTBuilder.js` (ja copiado automaticamente para a area de transferencia; caso tenha perdido, abra o arquivo na pasta do projeto e copie tudo).
7. Clique em **Run** e aguarde a criacao dos 20 equipamentos, 20 cabos e aplicacao das 11 configuracoes IOS.

---

## 2. Configuracoes Obrigatorias na Interface Grafica (GUI)

O PTBuilder configura IPs das placas de rede, mas nao preenche os servicos internos dos servidores (`Server-PT`). Configure os tres servidores abaixo pela aba **Services**:

1. **DHCP-LEGITIMO** (`192.168.10.10`):
   - Aba **Services > DHCP**:
     - Service: **On**
     - Pool Name: `serverPool`
     - Default Gateway: `192.168.10.1`
     - DNS Server: `192.168.30.10`
     - Start IP Address: `192.168.10.101`
     - Subnet Mask: `255.255.255.0`
     - Maximum Number of Users: `50`
     - Clique em **Save**.
2. **DHCP-NAO-AUTORIZADO** (`192.168.10.200`):
   - Aba **Services > DHCP**:
     - Service: **On**
     - Pool Name: `serverPool`
     - Default Gateway: `192.168.10.200`
     - DNS Server: `192.168.10.200`
     - Start IP Address: `192.168.10.210`
     - Subnet Mask: `255.255.255.0`
     - Maximum Number of Users: `20`
     - Clique em **Save**.
3. **SRV-CORPORATIVO** (`192.168.30.10`):
   - Aba **Services > DNS**:
     - DNS Service: **On**
     - Name: `intranet.nexatech.local` | Type: **A Record** | Address: `192.168.30.10` | Clique em **Add**.
   - Confirme na aba **Services > HTTP** que HTTP/HTTPS estao **On** (padrao do PT).
4. **Renovar DHCP em PC1, PC2 e PC3**:
   - Em cada PC (`PC1`, `PC2`, `PC3`), abra **Desktop > IP Configuration**, clique em **Static** e volte para **DHCP**.
   - Saida esperada: IPs `192.168.10.101`, `192.168.10.102`, `192.168.10.103`, mascara `255.255.255.0`, Default Gateway `192.168.10.1` e DNS `192.168.30.10`.

---

## 3. Convergencia Inicial e Conferencia de Eleicao DR/BDR

1. Clique em **Fast Forward Time** (`Alt+D`) 3 a 4 vezes ate todos os links ficarem verdes e as adjacencias OSPF e HSRP estabilizarem.
2. No CLI de **RC3**, execute:
   ```ios
   show ip ospf neighbor
   ```
   - Saida esperada no segmento `GigabitEthernet0/0`:
     - `1.1.1.1` (RC1) em estado `FULL/DR`
     - `2.2.2.2` (RC2) em estado `FULL/BDR`
3. **Se o DR/BDR nao estiver exatamente como RC1=DR e RC2=BDR** (pois a eleicao OSPF nao e preemptiva e depende da ordem de subida):
   - Execute `interface g0/0` -> `shutdown` em **RC1**, **RC2** e **RC3**.
   - Em seguida, dentro de uma janela de 10 segundos, execute `no shutdown` na `GigabitEthernet0/0` de **RC1**, depois **RC2** e **RC3** (alternativamente, execute `clear ip ospf process` -> `yes` nos tres roteadores).
   - Repita `show ip ospf neighbor` em RC3 e confirme `1.1.1.1` como `DR` e `2.2.2.2` como `BDR`.

---

## 4. Verificacao de Comandos Sensiveis a Versao do Packet Tracer

Execute os comandos abaixo antes dos testes de falha para validar o suporte do IOS simulado:

1. Em **RA**, execute:
   ```ios
   show standby brief
   show track
   ```
   - Saida esperada: grupo `10`, estado `Active`, prioridade `120`, IP virtual `192.168.10.1` e objeto `Track 1` rastreando `GigabitEthernet0/1 line-protocol` (`Up`).
   - **Contingencia caso o PT 8.2.1 tenha rejeitado `track 1 interface ...`:**
     ```ios
     configure terminal
     interface GigabitEthernet0/0
      no standby 10 track 1 decrement 40
      standby 10 track GigabitEthernet0/1 40
     end
     write memory
     ```
2. Em **SW-MATRIZ**, execute:
   ```ios
   show storm-control
   show ip dhcp snooping
   show port-security
   ```
   - Saida esperada: DHCP Snooping habilitado na VLAN 10 com `Fa0/24` como `Trusted = yes` e demais portas `no`; Port Security habilitado em `Fa0/1-3` (`Max 1`, `SecurityViolation Shutdown`); Storm Control `20.00%` em `Fa0/1-3`.
   - **Contingencia:** se `show storm-control` indicar que o recurso nao foi aceito pelo switch simulado no PT 8.2.1, deixe essa observacao explicita na nota **N05** dentro da topologia (o proprio enunciado preve que recursos ausentes no simulador nao sao penalizados quando documentados).

---

## 5. Bateria de Baseline (Conectividade, Roteamento e LSDB)

Confirme todos os testes abaixo antes de introduzir falhas:

1. **PC1 > Command Prompt:**
   - `ping 192.168.30.10` -> sucesso (Data Center).
   - `ping 198.51.100.10` -> sucesso (Servidor Externo).
   - `tracert 198.51.100.10` -> saida esperada passando por `192.168.10.2` (RA), `10.0.1.1` (RC1), `10.0.0.3` (RC3), `203.0.113.2` (ISP), `198.51.100.10`.
2. **PC4 > Command Prompt:**
   - `ping 192.168.10.1` -> sucesso (VIP HSRP da Matriz).
   - `ping 198.51.100.10` -> sucesso (Servidor Externo).
3. **RC1 > CLI (`show ip route`):**
   - Saida esperada: rotas `O` na Area 0, `O IA 192.168.20.0/24` (vindo do ABR RC2), `O*E2 0.0.0.0/0` e `O E2 198.51.100.0/24` (vindos do ASBR RC3).
4. **RF > CLI (`show ip route` e `show ip ospf database`):**
   - Saida esperada: rotas `O IA` para redes da Area 0 (`192.168.10.0/24`, `192.168.30.0/24`, `10.0.0.0/29`), rota padrao `O*IA 0.0.0.0/0` via `10.0.1.9` (RC2) e **ausencia total de LSAs Tipo 5 (`Type-5 AS External`)** no `show ip ospf database`, comprovando a Area 10 Stub.
   - Em **RC2** (`show ip ospf`) confirme `It is an area border router`; em **RC3** (`show ip ospf`) confirme `It is an autonomous system boundary router`.

---

## 6. Execucao dos 8 Cenarios de Troubleshooting (Etapa 5)

Execute um cenario por vez, diagnostique, corrija e atualize a nota **N06** para `"Testado no PT 8.2.1: SIM"` somente para os cenarios efetivamente rodados por voce:

1. **Cenario 1 — Queda do enlace WAN A (`RA G0/1`):**
   - Falha: em **RA**, `interface GigabitEthernet0/1` -> `shutdown`.
   - Diagnostico: em **RA** e **RB**, `show standby brief` e `show track`. RA cai de prioridade `120` para `80` e vira `Standby`; RB (`100`) assume `Active`. Em **PC1**, `ping 192.168.30.10` continua respondendo atraves de RB.
   - Correcao: `no shutdown` em `RA G0/1`. Com `Fast Forward`, RA volta a `120` e retoma `Active` por preempcao.
2. **Cenario 2 — Queda temporaria de `RC1 G0/0`:**
   - Falha: em **RC1**, `interface GigabitEthernet0/0` -> `shutdown`.
   - Diagnostico: em **RC3** e **RA**, `show ip ospf neighbor` e `show ip route`. RC2 assume como `DR` no backbone; em **PC1**, `tracert 192.168.30.10` mostra o trafego saindo por RA (`192.168.10.2`), cruzando a LAN da matriz para RB (`192.168.10.3`) e subindo via WAN B (`10.0.1.5`).
   - Correcao: `no shutdown` em `RC1 G0/0`. Como a eleicao DR nao e preemptiva, para restaurar o projeto padrao execute `shutdown` e `no shutdown` em `RC2 G0/0` (e em `RC3 G0/0` se necessario) e confirme em **RC3** `show ip ospf neighbor` (`RC1 = DR`, `RC2 = BDR`).
3. **Cenario 3 — IP incorreto em `PC4`:**
   - Falha: em **PC4**, altere o IP para `192.168.21.10`.
   - Diagnostico: `ping 192.168.20.1` falha; `ipconfig` evidencia sub-rede errada.
   - Correcao: restaure `192.168.20.10` / `255.255.255.0` / gateway `192.168.20.1` e valide o ping para `192.168.30.10`.
4. **Cenario 4 — Ausencia de gateway em `PC5`:**
   - Falha: em **PC5**, apague o campo Default Gateway.
   - Diagnostico: `ping 192.168.20.10` (PC4) funciona; `ping 192.168.30.10` falha; `ipconfig` mostra gateway vazio.
   - Correcao: restaure gateway `192.168.20.1` e valide o ping remoto.
5. **Cenario 5 — Dispositivo nao autorizado em porta com Port Security:**
   - Falha: desconecte o cabo de `PC1` em `SW-MATRIZ Fa0/1`, adicione um PC temporario chamado `INTRUSO`, conecte-o em `SW-MATRIZ Fa0/1`, atribua IP `192.168.10.99/24` e dispare `ping 192.168.10.1`.
   - Diagnostico: a porta cai imediatamente; em **SW-MATRIZ**, `show port-security interface fa0/1` exibe `Port Status: Secure-shutdown` e `Security Violation Count: 1`.
   - Correcao: delete o equipamento `INTRUSO`, reconecte `PC1` em `SW-MATRIZ Fa0/1` e execute na `Fa0/1` de `SW-MATRIZ`: `shutdown` seguido de `no shutdown`. Renove ou teste `ping 192.168.10.1` em `PC1` e confirme `Secure-up`.
6. **Cenario 6 — Servidor DHCP nao autorizado (`Fa0/10`):**
   - Falha/Teste: com `DHCP-NAO-AUTORIZADO` ativo na `Fa0/10` (untrusted), execute em **PC3** `ipconfig /release` e `ipconfig /renew` (ou alterne Static -> DHCP).
   - Diagnostico: em **SW-MATRIZ**, `show ip dhcp snooping` e `show ip dhcp snooping binding` confirmam que o lease recebido por PC3 continua sendo da faixa `192.168.10.101-150` do `DHCP-LEGITIMO` (`.10`), nunca `.210+`. Opcional: no modo Simulation filtrando apenas `DHCP`, observe o pacote `Offer` vindo da `Fa0/10` sendo descartado pelo `SW-MATRIZ`.
7. **Cenario 7 — Alteracao de custo OSPF (`RC2 G0/1`):**
   - Falha/Teste: em **RC2**, `interface GigabitEthernet0/1` -> `ip ospf cost 5`.
   - Diagnostico: em **RC3**, `show ip route 192.168.10.0` mostra o next-hop mudando de `10.0.0.1` (RC1) para `10.0.0.2` (RC2), pois o custo via RB cai de 61 para 16.
   - Correcao: restaure `ip ospf cost 50` em `RC2 G0/1` e confirme o retorno do next-hop para `10.0.0.1`.
8. **Cenario 8 — Area OSPF incorreta em `RF G0/1`:**
   - Falha: em **RF**, `router ospf 1` -> `no network 10.0.1.8 0.0.0.3 area 10` -> `network 10.0.1.8 0.0.0.3 area 0`.
   - Diagnostico: a adjacencia com RC2 cai; `show ip ospf neighbor` fica vazio e `show ip ospf interface g0/1` evidencia area/stub mismatch em relacao a `RC2 G0/2`.
   - Correcao: `no network 10.0.1.8 0.0.0.3 area 0` e `network 10.0.1.8 0.0.0.3 area 10`; confirme retorno de `FULL` em `show ip ospf neighbor`.

---

## 7. Checklist de Estado Final e Persistencia NVRAM

Antes de salvar, confirme:
- Exatamente **20 dispositivos** na tela (`INTRUSO` removido).
- Todas as interfaces utilizadas estao `up/up` e todas as portas ociosas permanecem em `shutdown` na VLAN `999` (`BLACKHOLE`).
- `RC1` e `DR` no backbone; `RC2` e `BDR`; `RA` e `Active` no HSRP grupo `10`.
- `PC1`, `PC2` e `PC3` possuem IP valido por DHCP (`192.168.10.101-103`, gateway `192.168.10.1`).
- Execute `write memory` em todos os **11 dispositivos IOS** (`RC1`, `RC2`, `RC3`, `RA`, `RB`, `RF`, `ISP`, `SW-CORE`, `SW-MATRIZ`, `SW-FILIAL`, `SW-DC`).

---

## 8. Documentacao Visual e Notas dentro do Packet Tracer

1. Use o **Drawing Palette** (`Draw Rectangle` / cor suave com preenchimento) para delimitar visualmente:
   - **Area 0 (Azul):** `SW-CORE`, `RC1`, `RC2`, `RC3`, `RA`, `RB`, `SW-MATRIZ`, `PC1-3`, `DHCPs`.
   - **Area 10 Stub (Verde):** enlace `RC2-RF`, `RF`, `SW-FILIAL`, `PC4-5`.
   - **Data Center (Amarelo):** `SW-DC`, `SRV-CORPORATIVO`.
   - **Rede Externa (Vermelho claro):** enlace `RC3-ISP`, `ISP`, `SRV-EXTERNO`.
2. Com a ferramenta **Place Note (tecla N)**, cole as 13 notas (**N01** a **N13**) do arquivo `NOTAS_PACKET_TRACER.md` nos respectivos setores e adicione etiquetas curtas nos enlaces (`WAN A 10.0.1.0/30`, `WAN B 10.0.1.4/30`, `WAN Filial 10.0.1.8/30`, `VIP HSRP 192.168.10.1`).

---

## 9. Entrega Final

1. Salve o arquivo via **File > Save As...** exatamente com o nome:
   `Projeto_Integrador_Redes_NexaTech.pkt`
   dentro da pasta `PROJETO INTEGRADOR NEXATECH`.
2. Reabra o `.pkt` salvo uma vez para confirmar que todas as configuracoes, interfaces e notas persistiram corretamente.

---

## 10. Preparacao para Apresentacao / Sabatina

Antes da aula de quarta-feira, revise principalmente os blocos **N03** (eleicao DR/BDR nao-preemptiva, papel do ABR na Area 10 Stub bloqueando Type-5, papel do ASBR gerando Type-5/O*E2, por que RA/RB G0/0 nao sao passivas), **N04** (tracking HSRP -40 e retorno por preempt) e **N07 a N12** (respostas conceituais de arquitetura, seguranca L2 e limitacoes do simulador).
