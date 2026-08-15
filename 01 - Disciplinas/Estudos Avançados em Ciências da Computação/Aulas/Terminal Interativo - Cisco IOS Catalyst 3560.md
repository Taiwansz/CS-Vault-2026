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

> [!info] 🕹️ Simulador de Console Cisco IOS
> Use o terminal abaixo para digitar comandos reais do seu laboratório da **TechSolutions**.
> O console simula em tempo real o comportamento dos switches `SW-CORE-1`, `SW-CORE-2` e `SW-ACCESS-1`.
> - 💡 **Dica:** Digite `help` ou `?` para ver os comandos disponíveis. Use as setas `↑` e `↓` para navegar no histórico!

---

<div style="background-color: #0d1117; border: 2px solid #30363d; border-radius: 8px; padding: 16px; font-family: 'Consolas', 'Courier New', monospace; color: #c9d1d9; box-shadow: 0 4px 12px rgba(0,0,0,0.5);">

  <!-- Barra de Ferramentas do Terminal -->
  <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #30363d; padding-bottom: 10px; margin-bottom: 12px;">
    <div>
      <span style="color: #56B4E9; font-weight: bold;">● CISCO CATALYST 3560 CONSOLE</span>
      <span style="color: #8b949e; font-size: 0.85em; margin-left: 10px;">(Lab TechSolutions - Aula 2)</span>
    </div>
    <div>
      <label style="color: #8b949e; font-size: 0.85em; margin-right: 6px;">Dispositivo:</label>
      <select id="ciscoDeviceSelect" style="background: #161b22; color: #56B4E9; border: 1px solid #30363d; border-radius: 4px; padding: 2px 8px;" onchange="changeDevice(this.value)">
        <option value="SW-CORE-1">SW-CORE-1 (Core LACP Active)</option>
        <option value="SW-CORE-2">SW-CORE-2 (Core LACP Passive)</option>
        <option value="SW-ACCESS-1">SW-ACCESS-1 (Acesso Setor 1)</option>
      </select>
      <button onclick="clearTerminal()" style="background: #21262d; color: #c9d1d9; border: 1px solid #30363d; border-radius: 4px; padding: 2px 8px; margin-left: 8px; cursor: pointer;">Limpar</button>
    </div>
  </div>

  <!-- Janela de Saída do Terminal -->
  <div id="ciscoOutput" style="height: 380px; overflow-y: auto; white-space: pre-wrap; font-size: 13px; line-height: 1.4; color: #e6edf3; padding-right: 8px;">
Cisco IOS Software, C3560 Software (C3560-ADVIPSERVICESK9-M), Version 12.2(37)SE1.
TechSolutions Campus Core Switch.

Digite <span style="color: #56B4E9; font-weight: bold;">help</span> ou <span style="color: #56B4E9; font-weight: bold;">?</span> para listar comandos, ou tente:
• <span style="color: #E69F00;">show vlan brief</span>
• <span style="color: #E69F00;">show etherchannel summary</span>
• <span style="color: #E69F00;">show interfaces trunk</span>
• <span style="color: #E69F00;">show lacp neighbor</span>
• <span style="color: #E69F00;">ping 192.168.10.12</span>
• <span style="color: #E69F00;">ping 192.168.20.11</span>
----------------------------------------------------------------------
</div>

  <!-- Linha de Prompt e Entrada de Comando -->
  <div style="display: flex; align-items: center; margin-top: 10px; border-top: 1px solid #21262d; padding-top: 10px;">
    <span id="ciscoPrompt" style="color: #56B4E9; font-weight: bold; margin-right: 8px;">SW-CORE-1#</span>
    <input type="text" id="ciscoInput" style="flex: 1; background: transparent; border: none; outline: none; color: #58a6ff; font-family: 'Consolas', 'Courier New', monospace; font-size: 14px;" autofocus autocomplete="off" spellcheck="false" placeholder="Digite um comando Cisco IOS..." onkeydown="handleCiscoKey(event)" />
  </div>
</div>

<script>
let currentDev = "SW-CORE-1";
let cmdHistory = [];
let histIdx = -1;

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
Gi0/1       10,20,30,40,99`,

    "show interfaces fastethernet 0/5 switchport": `Name: Fa0/5
Switchport: Enabled
Administrative Mode: static access
Operational Mode: static access
Access Mode VLAN: 10 (ADMINISTRATIVO)
Voice VLAN: 40 (VOZ)
Operational Voice VLAN: 40`
  }
};

function changeDevice(dev) {
  currentDev = dev;
  document.getElementById("ciscoPrompt").innerText = dev + "#";
  printOutput("\n[Contexto alterado para " + dev + "]");
}

function clearTerminal() {
  document.getElementById("ciscoOutput").innerHTML = "";
}

function printOutput(text) {
  const out = document.getElementById("ciscoOutput");
  out.innerHTML += text + "\n";
  out.scrollTop = out.scrollHeight;
}

function handleCiscoKey(e) {
  if (e.key === "Enter") {
    const input = document.getElementById("ciscoInput");
    const cmd = input.value.trim();
    if (!cmd) return;

    cmdHistory.push(cmd);
    histIdx = cmdHistory.length;

    printOutput("<span style='color:#56B4E9; font-weight:bold;'>" + currentDev + "#</span> " + cmd);
    executeCmd(cmd.toLowerCase());
    input.value = "";
  } else if (e.key === "ArrowUp") {
    if (histIdx > 0) {
      histIdx--;
      document.getElementById("ciscoInput").value = cmdHistory[histIdx];
    }
  } else if (e.key === "ArrowDown") {
    if (histIdx < cmdHistory.length - 1) {
      histIdx++;
      document.getElementById("ciscoInput").value = cmdHistory[histIdx];
    } else {
      histIdx = cmdHistory.length;
      document.getElementById("ciscoInput").value = "";
    }
  }
}

function executeCmd(cmd) {
  if (cmd === "help" || cmd === "?") {
    printOutput(`Comandos suportados no simulador:
  show vlan brief
  show etherchannel summary
  show interfaces trunk
  show lacp neighbor
  show ip interface brief
  show mac address-table
  show run / show running-config
  ping <ip_address>  (ex: ping 192.168.10.12 ou ping 192.168.20.11)
  clear
  enable / conf t`);
    return;
  }

  if (cmd === "clear") {
    clearTerminal();
    return;
  }

  if (cmd === "enable" || cmd === "en") {
    printOutput(currentDev + "# (Modo privilegiado ativo)");
    return;
  }

  if (cmd === "conf t" || cmd === "configure terminal") {
    printOutput("Enter configuration commands, one per line. End with CNTL/Z.\n" + currentDev + "(config)# exit\n" + currentDev + "#");
    return;
  }

  if (cmd.startsWith("ping")) {
    const target = cmd.split(" ")[1];
    if (target === "192.168.10.12" || target === "192.168.20.12" || target === "192.168.30.12") {
      printOutput("Type escape sequence to abort.\nSending 5, 100-byte ICMP Echos to " + target + ", timeout is 2 seconds:\n!!!!!\n<span style='color:#00e676;'>Success rate is 100 percent (5/5), round-trip min/avg/max = 1/2/4 ms</span>");
    } else if (target === "192.168.20.11" || target === "192.168.30.11") {
      printOutput("Type escape sequence to abort.\nSending 5, 100-byte ICMP Echos to " + target + ", timeout is 2 seconds:\n.....\n<span style='color:#ff5252;'>Success rate is 0 percent (0/5) [Destino em VLAN distinta sem roteador inter-VLAN]</span>");
    } else {
      printOutput("Sending 5, 100-byte ICMP Echos to " + (target || "0.0.0.0") + "...\n.....\nSuccess rate is 0 percent (0/5)");
    }
    return;
  }

  const devData = database[currentDev] || {};
  if (devData[cmd]) {
    printOutput(devData[cmd]);
  } else if (cmd === "show run" || cmd === "show running-config") {
    printOutput("Building configuration...\n!\nhostname " + currentDev + "\nvlan 10,20,30,40,99\ninterface Port-channel1\n switchport mode trunk\n switchport trunk native vlan 99\nend");
  } else {
    printOutput("% Invalid input detected at '^' marker.\n(Digite 'help' para ver os comandos implementados)");
  }
}
</script>
