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

> [!info] 🕹️ Console de Simulação Cisco IOS (Executado Nativamente via DataviewJS)
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Laboratório:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-12 - Resolução - Exercício Prático Aula 2 - TechSolutions|Resolução Exercício Aula 2 (TechSolutions)]]
> - **Guia de Comandos:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/Guia Rápido - Comandos Cisco IOS Switching e LACP|Guia Rápido de Comandos Cisco IOS]]
> - **Arquivo HTML Fonte:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/Terminal_Cisco_IOS.html|Terminal_Cisco_IOS.html]]

---

```dataviewjs
const container = dv.el("div", "", { cls: "cisco-term-wrapper" });

container.innerHTML = `
<div style="background: #0d1117; border: 2px solid #30363d; border-radius: 8px; font-family: 'Consolas', 'Courier New', monospace; color: #c9d1d9; box-shadow: 0 4px 16px rgba(0,0,0,0.6); overflow: hidden; margin-bottom: 16px;">

  <!-- Header -->
  <div style="background: #161b22; border-bottom: 1px solid #30363d; padding: 10px 14px; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 8px;">
    <div style="display: flex; align-items: center; gap: 8px; font-size: 13px; font-weight: bold; color: #56B4E9;">
      <span style="width: 10px; height: 10px; background: #00e676; border-radius: 50%; display: inline-block; box-shadow: 0 0 8px #00e676;"></span>
      <span>CISCO CATALYST 3560 CONSOLE (TechSolutions)</span>
    </div>
    <div style="display: flex; align-items: center; gap: 8px;">
      <label style="color: #8b949e; font-size: 12px;">Switch:</label>
      <select id="dvTermDevSel" style="background: #21262d; color: #56B4E9; border: 1px solid #30363d; border-radius: 4px; padding: 3px 8px; font-family: inherit; font-size: 12px; cursor: pointer;">
        <option value="SW-CORE-1">SW-CORE-1 (LACP Active)</option>
        <option value="SW-CORE-2">SW-CORE-2 (LACP Passive)</option>
        <option value="SW-ACCESS-1">SW-ACCESS-1 (Acesso Setor 1)</option>
        <option value="SW-ACCESS-2">SW-ACCESS-2 (Acesso Setor 2)</option>
      </select>
      <button id="dvTermClearBtn" style="background: #21262d; color: #c9d1d9; border: 1px solid #30363d; border-radius: 4px; padding: 3px 10px; font-family: inherit; font-size: 12px; cursor: pointer;">Limpar</button>
    </div>
  </div>

  <!-- Quick Action Chips -->
  <div style="background: #111827; border-bottom: 1px solid #1f2937; padding: 8px 12px; display: flex; gap: 6px; flex-wrap: wrap; align-items: center;">
    <span style="font-size: 11px; color: #94a3b8; margin-right: 4px;">Comandos Rápidos:</span>
    <button class="dv-chip" data-cmd="show vlan brief" style="background: #1e293b; color: #38bdf8; border: 1px solid #334155; border-radius: 12px; padding: 3px 10px; font-size: 11px; cursor: pointer;">show vlan brief</button>
    <button class="dv-chip" data-cmd="show etherchannel summary" style="background: #1e293b; color: #38bdf8; border: 1px solid #334155; border-radius: 12px; padding: 3px 10px; font-size: 11px; cursor: pointer;">show etherchannel summary</button>
    <button class="dv-chip" data-cmd="show interfaces trunk" style="background: #1e293b; color: #38bdf8; border: 1px solid #334155; border-radius: 12px; padding: 3px 10px; font-size: 11px; cursor: pointer;">show interfaces trunk</button>
    <button class="dv-chip" data-cmd="show lacp neighbor" style="background: #1e293b; color: #38bdf8; border: 1px solid #334155; border-radius: 12px; padding: 3px 10px; font-size: 11px; cursor: pointer;">show lacp neighbor</button>
    <button class="dv-chip" data-cmd="ping 192.168.10.12" style="background: #1e293b; color: #4ade80; border: 1px solid #334155; border-radius: 12px; padding: 3px 10px; font-size: 11px; cursor: pointer;">ping 192.168.10.12</button>
    <button class="dv-chip" data-cmd="ping 192.168.20.11" style="background: #1e293b; color: #f87171; border: 1px solid #334155; border-radius: 12px; padding: 3px 10px; font-size: 11px; cursor: pointer;">ping 192.168.20.11</button>
    <button class="dv-chip" data-cmd="help" style="background: #1e293b; color: #fbbf24; border: 1px solid #334155; border-radius: 12px; padding: 3px 10px; font-size: 11px; cursor: pointer;">help</button>
  </div>

  <!-- Screen Output -->
  <div id="dvTermScreen" style="background: #090d13; height: 350px; overflow-y: auto; padding: 14px; font-size: 13px; line-height: 1.45; white-space: pre-wrap; color: #e6edf3;">
<span style="color: #38bdf8; font-weight: bold;">Cisco IOS Software, C3560 Software (C3560-ADVIPSERVICESK9-M), Version 12.2(37)SE1.</span>
Console do Switch Inicializado.

Clique nos botões de <span style="color: #fbbf24;">Comandos Rápidos</span> acima ou digite no prompt abaixo:
• <span style="color: #fbbf24;">show vlan brief</span> &rarr; Tabela de VLANs
• <span style="color: #fbbf24;">show etherchannel summary</span> &rarr; Status LACP Port-Channel 1
• <span style="color: #fbbf24;">ping 192.168.10.12</span> &rarr; Teste na mesma VLAN
• <span style="color: #fbbf24;">ping 192.168.20.11</span> &rarr; Teste inter-VLAN
---------------------------------------------------------------------------------
</div>

  <!-- Prompt Line -->
  <div style="display: flex; align-items: center; padding: 10px 14px; background: #161b22; border-top: 1px solid #30363d;">
    <span id="dvTermPrompt" style="color: #38bdf8; font-weight: bold; margin-right: 8px; user-select: none;">SW-CORE-1#</span>
    <input type="text" id="dvTermInput" style="flex: 1; background: transparent; border: none; outline: none; color: #f8fafc; font-family: 'Consolas', monospace; font-size: 14px;" placeholder="Digite um comando Cisco IOS..." autocomplete="off" spellcheck="false" />
  </div>
</div>
`;

// Logic for the interactive terminal
let currentDev = "SW-CORE-1";
let hist = [];
let hIdx = -1;

const database = {
  "SW-CORE-1": {
    "show vlan brief": `VLAN Name                             Status    Ports
---- -------------------------------- --------- -------------------------------
1    default                          active    Fa0/2, Fa0/3, Fa0/4, Fa0/5...
10   ADMINISTRATIVO                   active    
20   FINANCEIRO                       active    
30   TI                               active    
40   VOZ                              active    
99   NATIVA                           active    
1002 fddi-default                     act/unsup 
1003 token-ring-default               act/unsup`,

    "show etherchannel summary": `Flags:  D - down        P - in port-channel
        I - stand-alone s - suspended
        H - Hot-standby (LACP only)
        R - Layer3      S - Layer2
        U - in use      f - failed to allocate aggregator

Group  Port-channel  Protocol    Ports
------+-------------+-----------+-----------------------------------------------
1      Po1(SU)         LACP      Gi0/1(P)    Gi0/2(P)`,

    "show interfaces trunk": `Port        Mode         Encapsulation  Status        Native vlan
Fa0/1       on           802.1q         trunking      99
Po1         on           802.1q         trunking      99

Port        Vlans allowed on trunk
Fa0/1       10,20,30,40,99
Po1         10,20,30,40,99

Port        Vlans allowed and active in management domain
Fa0/1       10,20,30,40,99
Po1         10,20,30,40,99`,

    "show lacp neighbor": `Channel group 1 neighbors

Partner's information:

                  LACP port                        Admin  Oper   Port    Port
Port      Flags   Priority  Dev ID          Age    Key    Key    Number  State
Gi0/1     SP      32768     0001.6492.2A01  14s    0x1    0x1    0x1     0x3C
Gi0/2     SP      32768     0001.6492.2A01  14s    0x1    0x1    0x2     0x3C`,

    "show ip interface brief": `Interface              IP-Address      OK? Method Status                Protocol
Port-channel 1         unassigned      YES manual up                    up
FastEthernet0/1        unassigned      YES manual up                    up
GigabitEthernet0/1     unassigned      YES manual up                    up
GigabitEthernet0/2     unassigned      YES manual up                    up
Vlan1                  unassigned      YES manual administratively down down`,

    "show mac address-table": `          Mac Address Table
-------------------------------------------
Vlan    Mac Address       Type        Ports
----    -----------       --------    -----
  10    0001.423a.1101    DYNAMIC     Fa0/1
  10    0001.423a.1102    DYNAMIC     Po1
  20    0001.423a.2201    DYNAMIC     Fa0/1
  30    0001.423a.3301    DYNAMIC     Fa0/1`
  },

  "SW-CORE-2": {
    "show vlan brief": `VLAN Name                             Status    Ports
---- -------------------------------- --------- -------------------------------
1    default                          active    Fa0/2, Fa0/3, Fa0/4...
10   ADMINISTRATIVO                   active    
20   FINANCEIRO                       active    
30   TI                               active    
40   VOZ                              active    
99   NATIVA                           active`,

    "show etherchannel summary": `Flags:  D - down        P - in port-channel
        S - Layer2      U - in use

Group  Port-channel  Protocol    Ports
------+-------------+-----------+-----------------------------------------------
1      Po1(SU)         LACP      Gi0/1(P)    Gi0/2(P)`,

    "show interfaces trunk": `Port        Mode         Encapsulation  Status        Native vlan
Fa0/1       on           802.1q         trunking      99
Po1         on           802.1q         trunking      99

Port        Vlans allowed on trunk
Fa0/1       10,20,30,40,99
Po1         10,20,30,40,99`
  },

  "SW-ACCESS-1": {
    "show vlan brief": `VLAN Name                             Status    Ports
---- -------------------------------- --------- -------------------------------
1    default                          active    Fa0/1, Fa0/6, Fa0/7...
10   ADMINISTRATIVO                   active    Fa0/2, Fa0/5
20   FINANCEIRO                       active    Fa0/3
30   TI                               active    Fa0/4
40   VOZ                              active    Fa0/5 (voice)
99   NATIVA                           active`,

    "show interfaces trunk": `Port        Mode         Encapsulation  Status        Native vlan
Gi0/1       on           802.1q         trunking      99

Port        Vlans allowed on trunk
Gi0/1       10,20,30,40,99`
  },

  "SW-ACCESS-2": {
    "show vlan brief": `VLAN Name                             Status    Ports
---- -------------------------------- --------- -------------------------------
1    default                          active    Fa0/1, Fa0/6, Fa0/7...
10   ADMINISTRATIVO                   active    Fa0/2, Fa0/5
20   FINANCEIRO                       active    Fa0/3
30   TI                               active    Fa0/4
40   VOZ                              active    Fa0/5 (voice)
99   NATIVA                           active`,

    "show interfaces trunk": `Port        Mode         Encapsulation  Status        Native vlan
Gi0/1       on           802.1q         trunking      99

Port        Vlans allowed on trunk
Gi0/1       10,20,30,40,99`
  }
};

const scr = container.querySelector("#dvTermScreen");
const inp = container.querySelector("#dvTermInput");
const pmt = container.querySelector("#dvTermPrompt");
const sel = container.querySelector("#dvTermDevSel");
const clr = container.querySelector("#dvTermClearBtn");
const chips = container.querySelectorAll(".dv-chip");

function appendLine(t) {
  scr.innerHTML += t + "\n";
  scr.scrollTop = scr.scrollHeight;
}

function execCmd(raw) {
  const c = raw.trim().toLowerCase();
  if (!c) return;

  hist.push(raw.trim());
  hIdx = hist.length;

  appendLine("<span style='color:#38bdf8; font-weight:bold;'>" + currentDev + "#</span> " + raw.trim());

  if (c === "help" || c === "?") {
    appendLine(`Comandos disponíveis:
  • show vlan brief
  • show etherchannel summary
  • show interfaces trunk
  • show lacp neighbor
  • show ip interface brief
  • show mac address-table
  • show run / show running-config
  • ping 192.168.10.12 (VLAN 10)
  • ping 192.168.20.11 (VLAN 20)
  • clear
  • enable / configure terminal`);
    return;
  }

  if (c === "clear") {
    scr.innerHTML = "";
    return;
  }

  if (c === "enable" || c === "en") {
    appendLine(currentDev + "# (Modo EXEC Privilegiado)");
    return;
  }

  if (c === "conf t" || c === "configure terminal") {
    appendLine("Enter configuration commands, one per line. End with CNTL/Z.\n" + currentDev + "(config)# exit\n" + currentDev + "#");
    return;
  }

  if (c.startsWith("ping")) {
    const tgt = c.split(" ")[1] || "";
    if (tgt === "192.168.10.12" || tgt === "192.168.20.12" || tgt === "192.168.30.12") {
      appendLine(`Type escape sequence to abort.
Sending 5, 100-byte ICMP Echos to ` + tgt + `, timeout is 2 seconds:
!!!!!
<span style="color:#4ade80; font-weight:bold;">Success rate is 100 percent (5/5), round-trip min/avg/max = 1/2/4 ms</span>`);
    } else if (tgt === "192.168.20.11" || tgt === "192.168.30.11" || tgt === "192.168.10.11") {
      appendLine(`Type escape sequence to abort.
Sending 5, 100-byte ICMP Echos to ` + tgt + `, timeout is 2 seconds:
.....
<span style="color:#f87171; font-weight:bold;">Success rate is 0 percent (0/5) [Destino em VLAN distinta - Roteamento Inter-VLAN desabilitado]</span>`);
    } else {
      appendLine(`Sending 5, 100-byte ICMP Echos to ` + tgt + `...\n.....\n<span style="color:#f87171; font-weight:bold;">Success rate is 0 percent (0/5)</span>`);
    }
    return;
  }

  const devDb = database[currentDev] || {};
  if (devDb[c]) {
    appendLine(devDb[c]);
  } else if (c === "show run" || c === "show running-config") {
    appendLine(`Building configuration...\n!\nhostname ` + currentDev + `\nvlan 10,20,30,40,99\ninterface Port-channel1\n switchport mode trunk\n switchport trunk native vlan 99\nend`);
  } else {
    appendLine("% Invalid input detected at '^' marker.\n(Digite 'help' para ver os comandos implementados)");
  }
}

sel.addEventListener("change", (e) => {
  currentDev = e.target.value;
  pmt.innerText = currentDev + "#";
  appendLine("\n[Contexto alterado para " + currentDev + "]");
});

clr.addEventListener("click", () => {
  scr.innerHTML = "";
});

chips.forEach(chip => {
  chip.addEventListener("click", () => {
    const cmd = chip.getAttribute("data-cmd");
    inp.value = cmd;
    execCmd(cmd);
    inp.value = "";
  });
});

inp.addEventListener("keydown", (e) => {
  if (e.key === "Enter") {
    execCmd(inp.value);
    inp.value = "";
  } else if (e.key === "ArrowUp") {
    if (hIdx > 0) {
      hIdx--;
      inp.value = hist[hIdx];
    }
  } else if (e.key === "ArrowDown") {
    if (hIdx < hist.length - 1) {
      hIdx++;
      inp.value = hist[hIdx];
    } else {
      hIdx = hist.length;
      inp.value = "";
    }
  }
});
```

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
