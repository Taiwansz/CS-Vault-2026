# Notas de Documentacao Interna — Cisco Packet Tracer (NexaTech)

Cole cada bloco abaixo em uma nota separada no Cisco Packet Tracer (ferramenta **Place Note / Tecla N**), posicionando-a proximo a respectiva area da topologia. Cada bloco possui menos de 1.500 caracteres em texto simples.

---

## N01 — Identificacao e Legenda (Canto superior esquerdo)

```text
[N01] PROJETO INTEGRADOR NEXATECH - IDENTIFICACAO
Disciplina: Redes de Computadores Convergentes
Autor: Matheus Sousa dos Santos
Ambiente: Cisco Packet Tracer 8.2.1
Topologia: 7 Roteadores (2911), 4 Switches (2960-24TT), 5 PCs, 4 Servidores (20 equipamentos)

LEGENDA DAS AREAS LOGICAS (RECONHECIMENTO VISUAL):
- Azul / Cinza: OSPF Area 0 (Backbone RC1-RC2-RC3, Matriz RA-RB, Data Center RC3-SW-DC)
- Verde: OSPF Area 10 Stub (Filial RC2-RF e LAN 192.168.20.0/24)
- Amarelo / Laranja: Data Center (192.168.30.0/24 - SRV-CORPORATIVO)
- Vermelho / Borda: Link Externo (203.0.113.0/30) e Rede Simulada ISP (198.51.100.0/24)
```

---

## N02 — Plano de Endereçamento IPv4 (Centro superior ou lateral esquerda)

```text
[N02] PLANO DE ENDERECAMENTO IPv4
Redes WAN e Backbone:
- Backbone OSPF (SW-CORE): 10.0.0.0/29 | RC1 G0/0 .1 | RC2 G0/0 .2 | RC3 G0/0 .3
- WAN A (RC1 G0/1 - RA G0/1): 10.0.1.0/30 (.1 / .2)
- WAN B (RC2 G0/1 - RB G0/1): 10.0.1.4/30 (.5 / .6)
- WAN Filial (RC2 G0/2 - RF G0/1): 10.0.1.8/30 (.9 / .10)
- Link Externo (RC3 G0/2 - ISP G0/0): 203.0.113.0/30 (.1 / .2)

Redes LAN e Externa:
- Matriz (VLAN 10): 192.168.10.0/24 | VIP HSRP G10: .1 | RA G0/0 .2 | RB G0/0 .3
  DHCP Legitimo (Fa0/24): .10 (pool .101-.150) | Rogue (Fa0/10): .200 | PC1-PC3: DHCP
- Filial: 192.168.20.0/24 | RF G0/0 .1 | PC4 .10 | PC5 .11
- Data Center: 192.168.30.0/24 | RC3 G0/1 .1 | SRV-CORPORATIVO .10
- Rede Externa: 198.51.100.0/24 | ISP G0/1 .1 | SRV-EXTERNO .10

Loopbacks /32 (Router-IDs OSPF):
RC1 1.1.1.1 | RC2 2.2.2.2 | RC3 3.3.3.3 | RA 4.4.4.4 | RB 5.5.5.5 | RF 6.6.6.6
```

---

## N03 — Arquitetura OSPF Avancado (Proximo ao Backbone SW-CORE)

```text
[N03] OSPF AVANCADO (PROCESSO 1 | REF-BW 1000 Mbps)
Areas e Papeis:
- Area 0 (Backbone): RC1 (Interno/DR), RC2 (ABR), RC3 (ASBR/DROTHER), RA (Interno), RB (Interno).
- Area 10 Stub (nao Totally Stubby): RC2 (ABR) e RF (Interno). Bloqueia LSAs Tipo 5; recebe LSAs Tipo 3 inter-area e rota padrao O*IA 0.0.0.0/0 do ABR.
- Eleicao Backbone (10.0.0.0/29): RC1 prio 200 (DR), RC2 prio 100 (BDR), RC3 prio 0 (DROTHER).

Engenharia de Trafego (Custos):
- Caminho Principal Core->Matriz: RC1 G0/0 (10) + G0/1 (10) + RA G0/0 (1) = 21.
- Caminho Backup Core->Matriz: RC2 G0/0 (10) + G0/1 (50) + RB G0/0 (1) = 61.
- Passive-interfaces: RC3 G0/1 (DC) e RF G0/0 (Filial). RA/RB G0/0 NAO sao passivas para manter adjacencia na LAN Matriz (caminho alternativo se RC1 G0/0 falhar).
- ASBR (RC3): static 0.0.0.0/0 e 198.51.100.0/24 -> 203.0.113.2; default-information originate + redistribute static subnets (O*E2 e O E2).

Verificacao: show ip ospf (ABR em RC2, ASBR em RC3), show ip ospf neighbor, show ip ospf database, show ip route ospf.
```

---

## N04 — Alta Disponibilidade HSRP (Proximo a RA, RB e SW-MATRIZ)

```text
[N04] ALTA DISPONIBILIDADE HSRP (GRUPO 10)
- VIP (Gateway PC1-PC3): 192.168.10.1
- RA G0/0 (192.168.10.2): Prioridade 120 | Preempt Habilitado | Estado Inicial: Active
- RB G0/0 (192.168.10.3): Prioridade 100 | Preempt Habilitado | Estado Inicial: Standby

Tracking e Failover:
- RA monitora WAN A (G0/1 line-protocol) via track 1 com decremento de 40 pontos.
- Se RA G0/1 cair: prioridade cai de 120 para 80 (< 100 de RB). RB assume como Active via preempt sem alterar o IP de gateway dos clientes.
- Quando RA G0/1 retorna: prioridade volta a 120 e RA retoma o papel Active automaticamente.
- Verificacao: show standby brief | show track (RA).
```

---

## N05 — Seguranca em Switches Camada 2 (Proximo a SW-MATRIZ e SW-FILIAL)

```text
[N05] SEGURANCA L2 (SW-MATRIZ E SW-FILIAL)
SW-MATRIZ (VLAN 10 MATRIZ | VLAN 999 BLACKHOLE):
- Port Security (Fa0/1-Fa0/3): max 1 MAC, sticky, violation shutdown, portfast + bpduguard.
- DHCP Snooping: global e VLAN 10; no ip dhcp snooping information option; limit rate 15 em Fa0/1-3 e Fa0/10.
  Trusted: Fa0/24 (DHCP-LEGITIMO). Untrusted: demais portas, incluindo Fa0/10 (DHCP-NAO-AUTORIZADO).
- Storm Control: broadcast level 20.00 em Fa0/1-3. (Obs: validar suporte via show storm-control no PT 8.2.1; se nao suportado na versao simulada, registrar aqui).
- Portas ociosas: VLAN 999 BLACKHOLE + shutdown.

SW-FILIAL: Port Security (max 1, sticky, violation shutdown), bpduguard e storm-control 20.00 em Fa0/1-2.
Verificacao: show port-security | show ip dhcp snooping | show ip dhcp snooping binding | show storm-control.
```

---

## N06 — Log de Troubleshooting (8 Cenarios) (Proximo a base inferior)

```text
[N06] REGISTRO DE TROUBLESHOOTING (8 CENARIOS)
Status inicial: inferido pela configuracao (marcar "Testado no PT 8.2.1: SIM" somente apos execucao real).

1. Falha WAN A (RA G0/1 shut) | Camada 1/2/3 | Sintoma: track 1 down, prio RA=80 | Cmd: show standby brief, show track | Correcao: RB assume Active; no shut em RA G0/1 recupera Active.
2. Falha Backbone RC1 G0/0 | Camada 3 | Sintoma: queda adjacencia DR | Cmd: show ip ospf neighbor, tracert | Correcao: RC2 vira DR, RA rota via RB; no shut + reeleicao restaura RC1 DR.
3. IP errado PC4 (192.168.21.10) | Camada 3 | Sintoma: falha ping gateway .20.1 | Cmd: ipconfig, ping | Correcao: restaurar 192.168.20.10/24.
4. Sem gateway em PC5 | Camada 3 | Sintoma: ping local ok, remoto falha | Cmd: ipconfig, tracert | Correcao: reconfigurar gateway 192.168.20.1.
5. Intruso em Fa0/1 SW-MATRIZ | Camada 2 | Sintoma: porta err-disabled | Cmd: show port-security int fa0/1 | Correcao: remover intruso, religar PC1, shut/no shut Fa0/1.
6. Rogue DHCP em Fa0/10 | Camada 2/7 | Sintoma: oferta ileg. descartada | Cmd: show ip dhcp snooping binding | Correcao: politica untrusted bloqueia automaticamente.
7. Custo RC2 G0/1 alterado (50->5) | Camada 3 | Sintoma: desvio para RB (10.0.0.2) | Cmd: show ip route 192.168.10.0 | Correcao: restaurar ip ospf cost 50.
8. Area errada em RF G0/1 (area 0) | Camada 3 | Sintoma: adjacencia cai (mismatch) | Cmd: show ip ospf int g0/1 | Correcao: retornar network 10.0.1.8 0.0.0.3 area 10.
```

---

## N07 — Questao 1: OSPF Custos vs Bandwidth (Area de Analise)

```text
[N07] Q1 - OSPF: CUSTOS E BANDWIDTH
O algoritmo SPF escolhe a rota de menor custo acumulado das interfaces de saida ate o destino. Portanto, alterar o custo de uma interface modifica o caminho preferencial mesmo sem falha fisica (engenharia de trafego). Nesta rede, RC2 G0/1 recebeu ip ospf cost 50 para que o fluxo Core->Matriz prefira RC1->RA (custo 21) em vez de RC2->RB (custo 61), mantendo simetria com o HSRP Active em RA. No cenario 7, reduzir o custo de RC2 G0/1 para 5 inverte essa escolha sem queda de link.

Diferenca tecnica:
- bandwidth <kbps>: altera apenas o parametro logico usado na formula automatica (custo = reference-bandwidth / bandwidth) e influencia outros subsistemas (QoS, EIGRP, SNMP), sem alterar a velocidade fisica real. Com o padrao antigo de 100 Mbps, FastEthernet e GigabitEthernet empatavam em custo 1, exigindo auto-cost reference-bandwidth 1000.
- ip ospf cost <valor>: define a metrica diretamente na interface, afeta exclusivamente o OSPF e sobrescreve o calculo automatico.
```

---

## N08 — Questao 2: DR, BDR, ABR, ASBR e LSAs (Area de Analise)

```text
[N08] Q2 - ARQUITETURA: DR, BDR, ABR, ASBR E LSAs
DR e BDR sao papeis por segmento multiacesso (aqui 10.0.0.0/29): reduzem adjacencias completas de n(n-1)/2 para 2(n-1). O DR (RC1) origina o LSA Tipo 2 (Network) representando o segmento; o BDR (RC2) assume sem resincronizacao total se o DR cair; prioridade 0 (RC3) impede eleicao. A eleicao nao e preemptiva.

ABR e ASBR sao papeis do roteador na topologia:
- ABR (RC2): conecta Area 0 e Area 10, mantem uma LSDB por area e gera LSAs Tipo 3 (Summary) para anunciar redes entre areas. Como a Area 10 e Stub, RC2 bloqueia LSAs Tipo 5 externos dentro dela e injeta uma rota padrao via LSA Tipo 3 (O*IA 0.0.0.0/0).
- ASBR (RC3): redistribui rotas externas (static/default do ISP) para o dominio OSPF via LSAs Tipo 5 (AS-External), propagados nas areas normais (RC1, RA, RB mostram O*E2 e O E2). O LSA Tipo 4 (gerado pelo ABR quando ha outras areas normais) localiza o ASBR.
```

---

## N09 — Questao 3: HSRP, VRRP e GLBP (Area de Analise)

```text
[N09] Q3 - ALTA DISPONIBILIDADE: VRRP E GLBP VS HSRP
- VRRP (RFC 5798): e protocolo padrao aberto (IETF), enquanto o HSRP e proprietario Cisco. Em ambientes heterogeneos (ex: roteadores Cisco + Juniper/MikroTik/Huawei), o VRRP permite interoperabilidade de gateway virtual entre fabricantes distintos, alem de permitir usar o IP real da interface master como VIP e ter preempcao ativa por padrao.
- GLBP (Cisco): seria mais adequado quando se deseja balanceamento de carga ativo-ativo usando um unico IP virtual. O AVG (Active Virtual Gateway) responde as requisicoes ARP dos hosts alternando ate 4 enderecos MAC virtuais (AVFs), aproveitando simultaneamente os uplinks de RA (WAN A) e RB (WAN B) sem precisar dividir hosts manualmente entre multiplos grupos HSRP, onde RB fica ocioso como Standby.

Limitacao no simulador: os roteadores 2911 do Packet Tracer 8.2.1 nao implementam VRRP ou GLBP; a comparacao e estritamente conceitual.
```

---

## N10 — Questao 4: Diagnostico em Camadas (Area de Analise)

```text
[N10] Q4 - DIAGNOSTICO EM CAMADAS (BOTTOM-UP)
1. Camada Fisica (L1): show ip interface brief mostra status "down/down" ou "administratively down", LED vermelho no link e erros/quedas em show interfaces (cabo, porta desligada ou mismatch de velocidade/duplex).
2. Erro de VLAN (L2): porta esta "up/up", mas hosts na mesma sub-rede nao se comunicam. show vlan brief e show interfaces switchport revelam VLAN incorreta ou porta em BLACKHOLE; ARP fica incompleto e show mac address-table nao aprende o MAC na VLAN esperada.
3. Ausencia de Rota (L3): ping para o gateway local funciona, mas falha para redes remotas ("Destination host unreachable"). show ip route nao lista o prefixo de destino e traceroute interrompe no roteador sem rota.
4. Incompatibilidade de Temporizadores OSPF (L3): link L1/L2 esta up e o ping direto ponto a ponto funciona, porem show ip ospf neighbor nao atinge FULL. show ip ospf interface mostra Hello/Dead divergentes (ou mismatch de Area/Stub flag, como no Cenario 8), confirmavel por debug ip ospf events (desligar apos coleta).
```

---

## N11 — Questao 5: Defesa em Profundidade L2 (Area de Analise)

```text
[N11] Q5 - SEGURANCA: COMPLEMENTARIDADE EM CAMADA 2
Port Security, DHCP Snooping e Storm Control atuam em vetores distintos e nao se substituem:
- Port Security controla identidade de hardware na porta (limita quantidade e MACs aprendidos/sticky e aplica violation shutdown), barrando maquinas intrusas ou inundacao da tabela CAM, mas nao inspeciona payloads DHCP nem taxa de broadcast dentro da cota permitida.
- DHCP Snooping inspeciona mensagens DHCP na camada L2/L7, bloqueando pacotes OFFER/ACK oriundos de portas untrusted (como Fa0/10) e montando a tabela binding (base para DAI e IP Source Guard), mas nao restringe MACs nem tempestades genericas.
- Storm Control limita a taxa de broadcast/multicast/unicast (20% em Fa0/1-3) contra loops ou DoS de inundacao, sem autenticar MAC ou validar servidores DHCP.

Exemplo pratico: um servidor DHCP rogue conectado com MAC clonado burla o Port Security, mas e bloqueado pelo DHCP Snooping; um host autorizado gerando storm de broadcast passa pelos dois primeiros e e contido pelo Storm Control.
```

---

## N12 — Questao 6: Evolucao Tecnologica e Limitacoes PT (Area de Analise)

```text
[N12] Q6 - EVOLUCAO TECNOLOGICA E LIMITACOES NO PT
- Autenticacao OSPF (MD5/HMAC-SHA): essencial em segmentos acessiveis a usuarios ou terceiros, como a LAN Matriz (RA/RB G0/0), impedindo que um roteador falso injete rotas na Area 0.
- OSPFv3: obrigatorio na adocao de IPv6 ou pilha dupla (dual-stack).
- BFD (Bidirectional Forwarding Detection): justificavel para deteccao de falha em milissegundos quando a queda nao derruba a interface fisica — caso critico do backbone via SW-CORE, onde sem BFD o OSPF aguarda o Dead Interval (40 s) para convergir; tambem acelera o HSRP.
- Sumarizacao de Rotas (area 10 range no ABR ou summary-address no ASBR): reduz LSAs Tipo 3/5 e isola instabilidades quando a filial cresce, exigindo enderecamento hierarquico contiguo.
- Automacao (NETCONF/RESTCONF, Ansible, Python): garante padronizacao, auditoria e backups em larga escala.

Limitacoes no Packet Tracer: subconjunto reduzido do IOS; BFD ausente; suporte parcial/variavel a OSPFv3, autenticacao, storm-control e tracking; tempo simulado nao reflete convergencia real e nao ha API de automacao em producao. Recursos ausentes ficam apenas como analise conceitual (nao comprovado neste simulador).
```

---

## N13 — Indice de Comandos de Verificacao (Proximo ao Data Center / Lateral direita)

```text
[N13] INDICE DE VERIFICACAO RAPIDA POR EQUIPAMENTO
- Todos IOS: show ip interface brief | show running-config | show logging
- Roteadores OSPF (RC1-3, RA, RB, RF):
  show ip ospf | show ip ospf neighbor | show ip ospf interface
  show ip ospf database | show ip route | show ip route ospf
- HSRP (RA, RB):
  show standby brief | show track (RA)
- Switches (SW-MATRIZ, SW-FILIAL):
  show port-security | show port-security interface fa0/1
  show ip dhcp snooping | show ip dhcp snooping binding
  show storm-control | show vlan brief
- Hosts (PC1-PC5):
  ipconfig /all | ping <destino> | tracert <destino>
```
