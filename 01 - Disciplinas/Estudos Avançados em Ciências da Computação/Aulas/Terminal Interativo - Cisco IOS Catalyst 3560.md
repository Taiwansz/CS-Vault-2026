---
tipo: ferramenta-interativa
disciplina: Estudos Avançados em Ciências da Computação
tags:
  - cisco
  - terminal
  - simulador
  - ios
  - switching
  - comandos
---

# 💻 Terminal Interativo: Cisco IOS (Catalyst 3560)

> [!info] 🕹️ Console de Simulação Cisco IOS
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Laboratório:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-12 - Resolução - Exercício Prático Aula 2 - TechSolutions|Resolução Exercício Aula 2 (TechSolutions)]]
> - **Guia de Comandos:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/Guia Rápido - Comandos Cisco IOS Switching e LACP|Guia Rápido de Comandos Cisco IOS]]
> - **Arquivo HTML Fonte:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/Terminal_Cisco_IOS.html|Terminal_Cisco_IOS.html]]

---

## 🖥️ Console Interativo

<iframe srcdoc="&lt;!DOCTYPE html&gt;
&lt;html lang=&quot;pt-BR&quot;&gt;
&lt;head&gt;
  &lt;meta charset=&quot;UTF-8&quot;&gt;
  &lt;style&gt;
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body {
      background-color: #0d1117;
      color: #e6edf3;
      font-family: &#x27;Consolas&#x27;, &#x27;Courier New&#x27;, monospace;
      padding: 12px;
      height: 100vh;
      display: flex;
      flex-direction: column;
    }
    .header {
      background: #161b22;
      border: 1px solid #30363d;
      border-radius: 6px 6px 0 0;
      padding: 8px 12px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-wrap: wrap;
      gap: 8px;
    }
    .title {
      color: #58a6ff;
      font-weight: bold;
      font-size: 13px;
      display: flex;
      align-items: center;
      gap: 8px;
    }
    .dot {
      width: 10px;
      height: 10px;
      background: #238636;
      border-radius: 50%;
      box-shadow: 0 0 6px #2ea043;
    }
    .chips-bar {
      background: #161b22;
      border-left: 1px solid #30363d;
      border-right: 1px solid #30363d;
      padding: 6px 12px;
      display: flex;
      flex-wrap: wrap;
      gap: 6px;
      align-items: center;
    }
    .chip {
      background: #21262d;
      color: #79c0ff;
      border: 1px solid #30363d;
      border-radius: 12px;
      padding: 2px 8px;
      font-size: 11px;
      cursor: pointer;
      user-select: none;
      transition: all 0.15s ease;
    }
    .chip:hover {
      background: #1f6feb;
      color: #ffffff;
      border-color: #58a6ff;
    }
    .terminal-screen {
      flex: 1;
      background: #090d13;
      border-left: 1px solid #30363d;
      border-right: 1px solid #30363d;
      padding: 12px;
      overflow-y: auto;
      font-size: 13px;
      line-height: 1.45;
      white-space: pre-wrap;
    }
    .input-bar {
      background: #161b22;
      border: 1px solid #30363d;
      border-radius: 0 0 6px 6px;
      padding: 8px 12px;
      display: flex;
      align-items: center;
    }
    .prompt {
      color: #58a6ff;
      font-weight: bold;
      margin-right: 8px;
      user-select: none;
    }
    input {
      flex: 1;
      background: transparent;
      border: none;
      outline: none;
      color: #58a6ff;
      font-family: inherit;
      font-size: 14px;
    }
    select, button {
      background: #21262d;
      color: #c9d1d9;
      border: 1px solid #30363d;
      border-radius: 4px;
      padding: 3px 8px;
      font-family: inherit;
      font-size: 12px;
      cursor: pointer;
    }
    select:hover, button:hover {
      border-color: #58a6ff;
    }
    .hl-green { color: #3fb950; font-weight: bold; }
    .hl-red { color: #f85149; font-weight: bold; }
    .hl-yellow { color: #d29922; }
  &lt;/style&gt;
&lt;/head&gt;
&lt;body&gt;

  &lt;div class=&quot;header&quot;&gt;
    &lt;div class=&quot;title&quot;&gt;
      &lt;div class=&quot;dot&quot;&gt;&lt;/div&gt;
      &lt;span&gt;CISCO CATALYST 3560 CONSOLE&lt;/span&gt;
    &lt;/div&gt;
    &lt;div&gt;
      &lt;select id=&quot;devSel&quot; onchange=&quot;changeDev(this.value)&quot;&gt;
        &lt;option value=&quot;SW-CORE-1&quot;&gt;SW-CORE-1 (LACP Active)&lt;/option&gt;
        &lt;option value=&quot;SW-CORE-2&quot;&gt;SW-CORE-2 (LACP Passive)&lt;/option&gt;
        &lt;option value=&quot;SW-ACCESS-1&quot;&gt;SW-ACCESS-1 (Acesso Setor 1)&lt;/option&gt;
      &lt;/select&gt;
      &lt;button onclick=&quot;clearOut()&quot;&gt;Limpar&lt;/button&gt;
    &lt;/div&gt;
  &lt;/div&gt;

  &lt;div class=&quot;chips-bar&quot;&gt;
    &lt;span style=&quot;font-size:11px; color:#8b949e;&quot;&gt;Comandos:&lt;/span&gt;
    &lt;button class=&quot;chip&quot; onclick=&quot;runCmd(&#x27;show vlan brief&#x27;)&quot;&gt;show vlan brief&lt;/button&gt;
    &lt;button class=&quot;chip&quot; onclick=&quot;runCmd(&#x27;show etherchannel summary&#x27;)&quot;&gt;show etherchannel summary&lt;/button&gt;
    &lt;button class=&quot;chip&quot; onclick=&quot;runCmd(&#x27;show interfaces trunk&#x27;)&quot;&gt;show interfaces trunk&lt;/button&gt;
    &lt;button class=&quot;chip&quot; onclick=&quot;runCmd(&#x27;show lacp neighbor&#x27;)&quot;&gt;show lacp neighbor&lt;/button&gt;
    &lt;button class=&quot;chip&quot; onclick=&quot;runCmd(&#x27;ping 192.168.10.12&#x27;)&quot;&gt;ping 192.168.10.12 (VLAN 10)&lt;/button&gt;
    &lt;button class=&quot;chip&quot; onclick=&quot;runCmd(&#x27;ping 192.168.20.11&#x27;)&quot;&gt;ping 192.168.20.11 (VLAN 20)&lt;/button&gt;
    &lt;button class=&quot;chip&quot; onclick=&quot;runCmd(&#x27;help&#x27;)&quot;&gt;help&lt;/button&gt;
  &lt;/div&gt;

  &lt;div class=&quot;terminal-screen&quot; id=&quot;screen&quot;&gt;Cisco IOS Software, C3560 Software (C3560-ADVIPSERVICESK9-M), Version 12.2(37)SE1.
TechSolutions Campus Core Switch.

&lt;span class=&quot;hl-yellow&quot;&gt;Clique nos botões azuis acima ou digite comandos no prompt abaixo:&lt;/span&gt;
• Digite &lt;span class=&quot;hl-yellow&quot;&gt;show vlan brief&lt;/span&gt; para inspecionar as VLANs.
• Digite &lt;span class=&quot;hl-yellow&quot;&gt;show etherchannel summary&lt;/span&gt; para checar o bundle LACP.
• Digite &lt;span class=&quot;hl-yellow&quot;&gt;ping 192.168.10.12&lt;/span&gt; para testar conectividade L2.
--------------------------------------------------------------------------------&lt;/div&gt;

  &lt;div class=&quot;input-bar&quot;&gt;
    &lt;span class=&quot;prompt&quot; id=&quot;promptText&quot;&gt;SW-CORE-1#&lt;/span&gt;
    &lt;input type=&quot;text&quot; id=&quot;cmdInput&quot; placeholder=&quot;Digite um comando Cisco IOS...&quot; autofocus /&gt;
  &lt;/div&gt;

  &lt;script&gt;
    let dev = &quot;SW-CORE-1&quot;;
    let hist = [];
    let hIdx = -1;

    const data = {
      &quot;SW-CORE-1&quot;: {
        &quot;show vlan brief&quot;: `VLAN Name                             Status    Ports
---- -------------------------------- --------- -------------------------------
1    default                          active    Fa0/2, Fa0/3, Fa0/4, Fa0/5...
10   ADMINISTRATIVO                   active    
20   FINANCEIRO                       active    
30   TI                               active    
40   VOZ                              active    
99   NATIVA                           active    
1002 fddi-default                     act/unsup 
1003 token-ring-default               act/unsup`,

        &quot;show etherchannel summary&quot;: `Flags:  D - down        P - in port-channel
        I - stand-alone s - suspended
        H - Hot-standby (LACP only)
        R - Layer3      S - Layer2
        U - in use      f - failed to allocate aggregator

Group  Port-channel  Protocol    Ports
------+-------------+-----------+-----------------------------------------------
1      Po1(SU)         LACP      Gi0/1(P)    Gi0/2(P)`,

        &quot;show interfaces trunk&quot;: `Port        Mode         Encapsulation  Status        Native vlan
Fa0/1       on           802.1q         trunking      99
Po1         on           802.1q         trunking      99

Port        Vlans allowed on trunk
Fa0/1       10,20,30,40,99
Po1         10,20,30,40,99

Port        Vlans allowed and active in management domain
Fa0/1       10,20,30,40,99
Po1         10,20,30,40,99`,

        &quot;show lacp neighbor&quot;: `Channel group 1 neighbors

Partner&#x27;s information:

                  LACP port                        Admin  Oper   Port    Port
Port      Flags   Priority  Dev ID          Age    Key    Key    Number  State
Gi0/1     SP      32768     0001.6492.2A01  14s    0x1    0x1    0x1     0x3C
Gi0/2     SP      32768     0001.6492.2A01  14s    0x1    0x1    0x2     0x3C`,

        &quot;show ip interface brief&quot;: `Interface              IP-Address      OK? Method Status                Protocol
Port-channel 1         unassigned      YES manual up                    up
FastEthernet0/1        unassigned      YES manual up                    up
GigabitEthernet0/1     unassigned      YES manual up                    up
GigabitEthernet0/2     unassigned      YES manual up                    up
Vlan1                  unassigned      YES manual administratively down down`,

        &quot;show mac address-table&quot;: `          Mac Address Table
-------------------------------------------
Vlan    Mac Address       Type        Ports
----    -----------       --------    -----
  10    0001.423a.1101    DYNAMIC     Fa0/1
  10    0001.423a.1102    DYNAMIC     Po1
  20    0001.423a.2201    DYNAMIC     Fa0/1
  30    0001.423a.3301    DYNAMIC     Fa0/1`
      },

      &quot;SW-CORE-2&quot;: {
        &quot;show vlan brief&quot;: `VLAN Name                             Status    Ports
---- -------------------------------- --------- -------------------------------
1    default                          active    Fa0/2, Fa0/3, Fa0/4...
10   ADMINISTRATIVO                   active    
20   FINANCEIRO                       active    
30   TI                               active    
40   VOZ                              active    
99   NATIVA                           active`,

        &quot;show etherchannel summary&quot;: `Flags:  D - down        P - in port-channel
        S - Layer2      U - in use

Group  Port-channel  Protocol    Ports
------+-------------+-----------+-----------------------------------------------
1      Po1(SU)         LACP      Gi0/1(P)    Gi0/2(P)`,

        &quot;show interfaces trunk&quot;: `Port        Mode         Encapsulation  Status        Native vlan
Fa0/1       on           802.1q         trunking      99
Po1         on           802.1q         trunking      99

Port        Vlans allowed on trunk
Fa0/1       10,20,30,40,99
Po1         10,20,30,40,99`
      },

      &quot;SW-ACCESS-1&quot;: {
        &quot;show vlan brief&quot;: `VLAN Name                             Status    Ports
---- -------------------------------- --------- -------------------------------
1    default                          active    Fa0/1, Fa0/6, Fa0/7...
10   ADMINISTRATIVO                   active    Fa0/2, Fa0/5
20   FINANCEIRO                       active    Fa0/3
30   TI                               active    Fa0/4
40   VOZ                              active    Fa0/5 (voice)
99   NATIVA                           active`,

        &quot;show interfaces trunk&quot;: `Port        Mode         Encapsulation  Status        Native vlan
Gi0/1       on           802.1q         trunking      99

Port        Vlans allowed on trunk
Gi0/1       10,20,30,40,99`
      }
    };

    const scr = document.getElementById(&quot;screen&quot;);
    const inp = document.getElementById(&quot;cmdInput&quot;);
    const pmt = document.getElementById(&quot;promptText&quot;);
    const sel = document.getElementById(&quot;devSel&quot;);

    function changeDev(newDev) {
      dev = newDev;
      pmt.innerText = dev + &quot;#&quot;;
      sel.value = dev;
      appendLine(&quot;\n[Dispositivo alterado para &quot; + dev + &quot;]&quot;);
    }

    function clearOut() {
      scr.innerHTML = &quot;&quot;;
    }

    function appendLine(txt) {
      scr.innerHTML += txt + &quot;\n&quot;;
      scr.scrollTop = scr.scrollHeight;
    }

    function runCmd(c) {
      inp.value = c;
      exec(c);
      inp.value = &quot;&quot;;
    }

    function exec(raw) {
      const c = raw.trim().toLowerCase();
      if (!c) return;

      hist.push(raw.trim());
      hIdx = hist.length;

      appendLine(&quot;&lt;span style=&#x27;color:#58a6ff; font-weight:bold;&#x27;&gt;&quot; + dev + &quot;#&lt;/span&gt; &quot; + raw.trim());

      if (c === &quot;help&quot; || c === &quot;?&quot;) {
        appendLine(`Comandos suportados:
  show vlan brief
  show etherchannel summary
  show interfaces trunk
  show lacp neighbor
  show ip interface brief
  show mac address-table
  show run
  ping 192.168.10.12 (VLAN 10)
  ping 192.168.20.11 (VLAN 20)
  clear
  enable / conf t`);
        return;
      }

      if (c === &quot;clear&quot;) {
        clearOut();
        return;
      }

      if (c === &quot;enable&quot; || c === &quot;en&quot;) {
        appendLine(dev + &quot;# (Modo EXEC Privilegiado)&quot;);
        return;
      }

      if (c === &quot;conf t&quot; || c === &quot;configure terminal&quot;) {
        appendLine(&quot;Enter configuration commands, one per line. End with CNTL/Z.\n&quot; + dev + &quot;(config)# exit\n&quot; + dev + &quot;#&quot;);
        return;
      }

      if (c.startsWith(&quot;ping&quot;)) {
        const tgt = c.split(&quot; &quot;)[1] || &quot;&quot;;
        if (tgt === &quot;192.168.10.12&quot; || tgt === &quot;192.168.20.12&quot; || tgt === &quot;192.168.30.12&quot;) {
          appendLine(`Type escape sequence to abort.
Sending 5, 100-byte ICMP Echos to ` + tgt + `, timeout is 2 seconds:
!!!!!
&lt;span class=&quot;hl-green&quot;&gt;Success rate is 100 percent (5/5), round-trip min/avg/max = 1/2/4 ms&lt;/span&gt;`);
        } else if (tgt === &quot;192.168.20.11&quot; || tgt === &quot;192.168.30.11&quot; || tgt === &quot;192.168.10.11&quot;) {
          appendLine(`Type escape sequence to abort.
Sending 5, 100-byte ICMP Echos to ` + tgt + `, timeout is 2 seconds:
.....
&lt;span class=&quot;hl-red&quot;&gt;Success rate is 0 percent (0/5) [Destino em sub-rede/VLAN distinta sem roteamento inter-VLAN]&lt;/span&gt;`);
        } else {
          appendLine(`Sending 5, 100-byte ICMP Echos to ` + tgt + `...\n.....\n&lt;span class=&quot;hl-red&quot;&gt;Success rate is 0 percent (0/5)&lt;/span&gt;`);
        }
        return;
      }

      const devDb = data[dev] || {};
      if (devDb[c]) {
        appendLine(devDb[c]);
      } else if (c === &quot;show run&quot; || c === &quot;show running-config&quot;) {
        appendLine(`Building configuration...
!
hostname ` + dev + `
vlan 10,20,30,40,99
interface Port-channel1
 switchport mode trunk
 switchport trunk native vlan 99
end`);
      } else {
        appendLine(&quot;% Invalid input detected at &#x27;^&#x27; marker.\n(Digite &#x27;help&#x27; para ver os comandos implementados)&quot;);
      }
    }

    inp.addEventListener(&quot;keydown&quot;, function(e) {
      if (e.key === &quot;Enter&quot;) {
        exec(inp.value);
        inp.value = &quot;&quot;;
      } else if (e.key === &quot;ArrowUp&quot;) {
        if (hIdx &gt; 0) {
          hIdx--;
          inp.value = hist[hIdx];
        }
      } else if (e.key === &quot;ArrowDown&quot;) {
        if (hIdx &lt; hist.length - 1) {
          hIdx++;
          inp.value = hist[hIdx];
        } else {
          hIdx = hist.length;
          inp.value = &quot;&quot;;
        }
      }
    });
  &lt;/script&gt;
&lt;/body&gt;
&lt;/html&gt;" width="100%" height="580px" style="border: 2px solid #30363d; border-radius: 8px; background-color: #0d1117;" frameborder="0"></iframe>

---

## 📑 Painel Rápido de Saídas dos Comandos (Modo de Leitura)

> [!example]- 🔍 1. show vlan brief (Tabela de VLANs)
> ```text
> VLAN Name                             Status    Ports
> ---- -------------------------------- --------- -------------------------------
> 1    default                          active    Fa0/2, Fa0/3, Fa0/4, Fa0/5...
> 10   ADMINISTRATIVO                   active    Fa0/2
> 20   FINANCEIRO                       active    Fa0/3
> 30   TI                               active    Fa0/4
> 40   VOZ                              active    Fa0/5 (Voice VLAN)
> 99   NATIVA                           active    (VLAN Nativa dos Troncos)
> ```

> [!example]- 🚀 2. show etherchannel summary (Status do Bundle LACP)
> ```text
> Flags:  D - down        P - in port-channel
>         I - stand-alone s - suspended
>         S - Layer2      U - in use
> 
> Group  Port-channel  Protocol    Ports
> ------+-------------+-----------+-----------------------------------------------
> 1      Po1(SU)         LACP      Gi0/1(P)    Gi0/2(P)
> ```

> [!example]- 🌐 3. show interfaces trunk (Enlaces 802.1Q e VLAN Nativa 99)
> ```text
> Port        Mode         Encapsulation  Status        Native vlan
> Fa0/1       on           802.1q         trunking      99
> Po1         on           802.1q         trunking      99
> 
> Port        Vlans allowed on trunk
> Fa0/1       10,20,30,40,99
> Po1         10,20,30,40,99
> ```

> [!example]- 🧪 4. Resultados de Ping (Mesma VLAN vs. Inter-VLAN)
> ```text
> ! Teste A: Mesma VLAN 10 (PC-ADM-01 -> PC-ADM-02)
> ping 192.168.10.12
> Sending 5, 100-byte ICMP Echos to 192.168.10.12...
> !!!!!
> Success rate is 100 percent (5/5)
> 
> ! Teste D: Inter-VLAN sem Roteador L3 (PC-ADM-01 -> PC-FIN-01)
> ping 192.168.20.11
> Sending 5, 100-byte ICMP Echos to 192.168.20.11...
> .....
> Success rate is 0 percent (0/5) [Bloqueio esperado em Camada 2]
> ```
