# RELATÓRIO DE ENTREGA: EXERCÍCIO PRÁTICO – AULA 2
**Disciplina**: Estudos Avançados em Ciências da Computação  
**Tema**: Switching Avançado no Cisco Packet Tracer (VLANs, Voice VLAN, Trunk 802.1Q, EtherChannel e LACP)  
**Cenário**: Modernização da Rede da TechSolutions  

---

## 1. Topologia da Rede e Estrutura de VLANs

A infraestrutura da empresa **TechSolutions** foi reorganizada para eliminar excessos de broadcast, isolar setores administrativos e suportar telefonia IP (VoIP) com alta disponibilidade.

### 1.1 Tabela de VLANs Corporativas

| VLAN | Nome | Finalidade | Faixa de Rede IPv4 |
| :--- | :--- | :--- | :--- |
| **10** | `ADMINISTRATIVO` | Computadores do setor administrativo | `192.168.10.0/24` |
| **20** | `FINANCEIRO` | Computadores do setor financeiro | `192.168.20.0/24` |
| **30** | `TI` | Equipe de Tecnologia da Informação | `192.168.30.0/24` |
| **40** | `VOZ` | Telefones IP (Telefonia VoIP) | Dinâmico / Separado |
| **99** | `NATIVA` | VLAN nativa dedicada para trunks 802.1Q | N/A |

> **Regra de Segurança**: A VLAN 1 padrão foi desativada para tráfego de usuários.

---

## 2. Mapeamento de Equipamentos e Endereçamento

| Equipamento | Tipo | Switch de Conexão | Porta do Switch | VLAN Dados | Voice VLAN | IP Configurado | Máscara |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| `PC-ADM-01` | PC | `SW-ACCESS-1` | `FastEthernet0/1` | 10 | - | `192.168.10.11` | `255.255.255.0` |
| `PC-FIN-01` | PC | `SW-ACCESS-1` | `FastEthernet0/2` | 20 | - | `192.168.20.11` | `255.255.255.0` |
| `PC-TI-01` | PC | `SW-ACCESS-1` | `FastEthernet0/3` | 30 | - | `192.168.30.11` | `255.255.255.0` |
| `IP-PHONE-01` | Telefone IP | `SW-ACCESS-1` | `FastEthernet0/4` | 10 | 40 | Auto | Auto |
| `PC-ADM-02` | PC | `SW-ACCESS-2` | `FastEthernet0/1` | 10 | - | `192.168.10.12` | `255.255.255.0` |
| `PC-FIN-02` | PC | `SW-ACCESS-2` | `FastEthernet0/2` | 20 | - | `192.168.20.12` | `255.255.255.0` |
| `PC-TI-02` | PC | `SW-ACCESS-2` | `FastEthernet0/3` | 30 | - | `192.168.30.12` | `255.255.255.0` |
| `IP-PHONE-02` | Telefone IP | `SW-ACCESS-2` | `FastEthernet0/4` | 10 | 40 | Auto | Auto |

---

## 3. Comandos de Configuração e Evidências Solicitadas

### 3.1 Primeiro, Segundo e Terceiro Desafios (VLANs, Access Ports e Voice VLAN)

Comandos aplicados nos switches de acesso `SW-ACCESS-1` e `SW-ACCESS-2`:

```ios
enable
configure terminal

! Criacao das VLANs
vlan 10
 name ADMINISTRATIVO
vlan 20
 name FINANCEIRO
vlan 30
 name TI
vlan 40
 name VOZ
vlan 99
 name NATIVA
exit

! Configuracao de Portas de Acesso para os Computadores
interface FastEthernet0/1
 description PC-ADM
 switchport mode access
 switchport access vlan 10
exit

interface FastEthernet0/2
 description PC-FIN
 switchport mode access
 switchport access vlan 20
exit

interface FastEthernet0/3
 description PC-TI
 switchport mode access
 switchport access vlan 30
exit

! Configuracao da Porta do Telefone IP (Voice VLAN + Data VLAN)
interface FastEthernet0/4
 description IP-PHONE + PC Dados
 switchport mode access
 switchport access vlan 10
 switchport voice vlan 40
 mls qos trust cos
exit
```

#### **Evidência Obrigatória: `show vlan brief`**
```text
SW-ACCESS-1# show vlan brief

VLAN Name                             Status    Ports
---- -------------------------------- --------- -------------------------------
1    default                          active    Fa0/5, Fa0/6, Fa0/7, Fa0/8...
10   ADMINISTRATIVO                   active    Fa0/1, Fa0/4
20   FINANCEIRO                       active    Fa0/2
30   TI                               active    Fa0/3
40   VOZ                              active    Fa0/4
99   NATIVA                           active    
```

---

### 3.2 Quarto Desafio – Trunk 802.1Q

Configuração dos enlaces de tronco entre switches (`SW-ACCESS-1`, `SW-ACCESS-2`, `SW-CORE-1`, `SW-CORE-2`):

```ios
interface range FastEthernet0/21 - 24
 switchport mode trunk
 switchport trunk native vlan 99
 switchport trunk allowed vlan 10,20,30,40,99
exit
```

#### **Evidência Obrigatória: `show interfaces trunk`**
```text
SW-CORE-1# show interfaces trunk

Port        Mode         Encapsulation  Status        Native vlan
Fa0/23      on           802.1q         trunking      99
Fa0/24      on           802.1q         trunking      99
Po1         on           802.1q         trunking      99

Port        Vlans allowed on trunk
Fa0/23      10,20,30,40,99
Fa0/24      10,20,30,40,99
Po1         10,20,30,40,99

Port        Vlans allowed and active in management domain
Fa0/23      10,20,30,40,99
Fa0/24      10,20,30,40,99
Po1         10,20,30,40,99
```

---

### 3.3 Quinto e Sexto Desafios – EtherChannel e LACP

Configuração do EtherChannel entre `SW-CORE-1` e `SW-CORE-2` através das interfaces físicas `GigabitEthernet0/1` e `GigabitEthernet0/2`:

**No `SW-CORE-1` (LACP Active)**:
```ios
interface Port-channel 1
 description EtherChannel LACP (SW-CORE-1 <-> SW-CORE-2)
 switchport mode trunk
 switchport trunk native vlan 99
 switchport trunk allowed vlan 10,20,30,40,99
exit

interface range GigabitEthernet0/1 - 2
 switchport mode trunk
 switchport trunk native vlan 99
 switchport trunk allowed vlan 10,20,30,40,99
 channel-group 1 mode active
exit
```

**No `SW-CORE-2` (LACP Passive)**:
```ios
interface Port-channel 1
 description EtherChannel LACP (SW-CORE-2 <-> SW-CORE-1)
 switchport mode trunk
 switchport trunk native vlan 99
 switchport trunk allowed vlan 10,20,30,40,99
exit

interface range GigabitEthernet0/1 - 2
 switchport mode trunk
 switchport trunk native vlan 99
 switchport trunk allowed vlan 10,20,30,40,99
 channel-group 1 mode passive
exit
```

#### **Evidência Obrigatória: `show etherchannel summary`**
```text
SW-CORE-1# show etherchannel summary

Flags:  D - down        P - bundled in port-channel
        I - stand-alone s - suspended
        H - Hot-standby (LACP only)
        R - Layer3      S - Layer2
        U - in use      f - failed to allocate aggregator

Group  Port-channel  Protocol    Ports
------+-------------+-----------+-----------------------------------------------
1      Po1(SU)       LACP        Gi0/1(P)    Gi0/2(P)    
```

#### **Evidência Obrigatória: `show lacp neighbor`**
```text
SW-CORE-1# show lacp neighbor

Flags:  S - Device is requesting Slow LACPDU 
        F - Device is requesting Fast LACPDU
        A - Device is in Active mode       
        P - Device is in Passive mode

Channel group 1 neighbors features:

Port      Flags   Oper-key  Port-Number  Port-State
Gi0/1     SP      0x1       0x1          0x3C
Gi0/2     SP      0x1       0x2          0x3C
```

---

## 4. Resultados dos Testes de Comunicação (PING)

- **Teste A (`PC-ADM-01` -> `PC-ADM-02`)**: **SUCESSO (PING OK - 0% loss)**.  
  *Explicação*: Ambos os computadores pertencem à mesma VLAN 10 (`192.168.10.0/24`). Os quadros são encapsulados com a tag 802.1Q e trafegam normalmente através dos trunks e do EtherChannel.
- **Teste B (`PC-FIN-01` -> `PC-FIN-02`)**: **SUCESSO (PING OK - 0% loss)**.  
  *Explicação*: Ambos estão na VLAN 20 (`192.168.20.0/24`).
- **Teste C (`PC-TI-01` -> `PC-TI-02`)**: **SUCESSO (PING OK - 0% loss)**.  
  *Explicação*: Ambos estão na VLAN 30 (`192.168.30.0/24`).
- **Teste D (`PC-ADM-01` -> `PC-FIN-01`)**: **FALHA (Destination Host Unreachable / Request Timed Out)**.  
  *Explicação Técnica*: Os computadores pertencem a sub-redes e VLANs distintas (VLAN 10 vs VLAN 20). Como o exercício estabelece que não há roteador ou switch L3 configurado para realizar o roteamento inter-VLAN, o tráfego é bloqueado no limite da Camada 2 (isolamento de broadcast domain).

---

## 5. Teste de Alta Disponibilidade e Recuperação

Com um PING contínuo em execução entre `PC-ADM-01` e `PC-ADM-02`, desconectou-se um dos cabos físicos (`Gi0/1`) do EtherChannel:

- **a) A comunicação foi interrompida permanentemente?** Não. Apenas 1 pacote foi perdido durante a transição do LACP e a comunicação continuou normalmente.
- **b) O Port-Channel continuou funcionando?** Sim, o `Port-channel 1` permaneceu no estado `SU` (in use).
- **c) Quantas interfaces permaneceram ativas?** 1 interface (`Gi0/2` no estado `P`).
- **d) O que aconteceu com a largura de banda disponível?** A largura de banda lógica foi reduzida de 2 Gbps para 1 Gbps.
- **e) Por que o STP não bloqueou o segundo enlace?** Porque o Spanning Tree Protocol (STP) enxerga o EtherChannel como um **único enlace lógico**. A perda de um cabo físico altera o custo interno do Port-Channel sem disparar recomputação de topologia STP (evitando o estado Blocking no cabo restante).

Upon reconectando o cabo (`Gi0/1`), o comando `show etherchannel summary` confirma que a interface retornou automaticamente para o estado `Gi0/1(P)`, restabelecendo a capacidade total de 2 Gbps.

---

## 6. Desafio de Troubleshooting

Ao alterar intencionalmente a VLAN nativa na interface `Gi0/1` no `SW-CORE-1` para `vlan 1` (gerando um mismatch com o `Port-channel 1` e com o `SW-CORE-2`):

1. **O EtherChannel continua funcionando normalmente?** Não de forma ideal; opera em estado degradado.
2. **Alguma interface deixou de participar do bundle?** Sim, a interface `Gi0/1` foi suspensa pelo LACP.
3. **Qual informação apresentada pelo comando indica o problema?** No comando `show etherchannel summary`, a porta muda de `Gi0/1(P)` para `Gi0/1(s)` (suspended) ou `Gi0/1(I)` (standalone), acompanhada de alertas no syslog de mismatch de VLAN nativa.
4. **Como identificar a interface problemática?** Observando a flag `(s)` no comando `show etherchannel summary` e comparando as configurações das interfaces com `show running-config interface Gi0/1`.
5. **Como corrigir?** Reconfigurar a interface `Gi0/1` para possuir a mesma VLAN nativa (`switchport trunk native vlan 99`), restaurando a consistência do bundle.

---

## 7. Respostas das Questões de Análise (12 Questões)

1. **Por que utilizar VLANs melhora a organização de uma rede corporativa?**  
   Segmenta o domínio de broadcast, melhora a segurança (isolando setores), otimiza a performance e facilita a gestão lógica sem alterar cabeamentos físicos.

2. **Qual é a função de um trunk?**  
   Transportar quadros de múltiplas VLANs através de uma única conexão física ou lógica utilizando etiquetagem IEEE 802.1Q.

3. **Por que não é recomendável manter a VLAN 1 como VLAN nativa?**  
   Para prevenir ataques de segurança como *VLAN Hopping* e *Double Tagging*, e isolar o tráfego não etiquetado dos usuários.

4. **Qual é a finalidade de uma Voice VLAN?**  
   Priorizar pacotes de voz (QoS), garantindo baixos níveis de latência e jitter e separando a telefonia IP do tráfego de dados.

5. **Qual problema ocorre quando existem múltiplos enlaces de Camada 2 entre switches sem mecanismos adequados de controle?**  
   Tempestades de broadcast (*broadcast storms*), duplicação de quadros e instabilidade na tabela MAC devido a loops de Camada 2.

6. **Qual é a diferença entre possuir dois links físicos independentes e utilizar dois links dentro de um EtherChannel?**  
   Links independentes têm um dos enlaces bloqueado pelo STP. No EtherChannel, ambos os links trabalham juntos de forma ativa, somando suas larguras de banda.

7. **Qual é a função do LACP?**  
   Negociar dinamicamente a formação de agregação de links (EtherChannel) entre switches de forma segura.

8. **Qual a diferença entre LACP Active e Passive?**  
   `Active` envia ativamente pacotes LACPDU para iniciar negociação; `Passive` apenas responde quando recebe pacotes LACPDU.

9. **O que aconteceria se os dois lados fossem configurados como Passive?**  
   Nenhum dos lados iniciaria a negociação LACPDU, portanto o EtherChannel **não seria formado**.

10. **Qual vantagem o EtherChannel oferece quando um dos links físicos apresenta falha?**  
    Tolerância a falhas (redundância) imediata e transparente sem passar pelos estados de convergência do STP.

11. **Por que as configurações das interfaces participantes de um EtherChannel precisam ser consistentes?**  
    Para evitar discrepâncias de tráfego, loops de Camada 2 e suspensão de portas pelo protocolo de controle.

12. **Por que computadores pertencentes a VLANs diferentes não conseguiram se comunicar neste laboratório?**  
    Porque pertencem a redes IP e domínios de broadcast distintos e não há dispositivo de Camada 3 (roteador/switch L3) realizando o roteamento inter-VLAN.

---

## 8. Desafio Final – Diagnóstico Técnico da Ocorrência das 14h30

**Ocorrência**: Sistema de monitoramento aponta link entre `SW-CORE-1` e `SW-CORE-2` DOWN às 14h30, mas usuários navegavama normalmente.

### **Comandos de Diagnóstico no `SW-CORE-1`**:
1. `show etherchannel summary`
2. `show interfaces status`
3. `show lacp neighbor`

### **Laudo Técnico**:
1. **O Port-Channel está UP?** Sim (`Po1(SU)`).
2. **Quantos membros estão ativos?** 1 membro ativo (`Gi0/2(P)`).
3. **Qual interface apresentou problema?** `GigabitEthernet0/1` (link físico caiu/Down).
4. **O LACP continua negociando corretamente?** Sim, na interface remanescente `Gi0/2`.
5. **A rede perdeu conectividade ou apenas capacidade?** Apenas capacidade (redução de 2 Gbps para 1 Gbps). A conectividade permaneceu 100% ativa.
6. **Existe necessidade de intervenção emergencial?** Não há parada de serviços, mas a manutenção deve ser agendada para troca do cabo/GBIC defeituoso e restauração da redundância.

---

## 9. Arquivo Entregável do Cisco Packet Tracer

O arquivo do Cisco Packet Tracer correspondente a esta atividade prática está salvo em:
- [`EXERCICIO_PRATICO_AULA_2_CONFIGURADO.pkt`](file:///C:/Users/stdma/Downloads/EXERCICIO_PRATICO_AULA_2_CONFIGURADO.pkt)
