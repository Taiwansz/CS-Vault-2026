---
tipo: ferramenta-interativa
disciplina: Estudos Avançados em Ciências da Computação
tags:
  - osi
  - modelo-osi
  - redes
  - encapsulamento
  - pdu
  - cisco
---

# 🌐 Modelo OSI Interativo: 7 Camadas, Encapsulamento e Troubleshooting

> [!info] 📌 Guia de Referência e Simulador do Modelo OSI (Executado Nativamente via DataviewJS)
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Laboratório TechSolutions:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-12 - Resolução - Exercício Prático Aula 2 - TechSolutions|Resolução Exercício Aula 2 (TechSolutions)]]
> - **Terminal Cisco:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/Terminal Interativo - Cisco IOS Catalyst 3560|Terminal Interativo Cisco IOS]]
> - **Arquivo HTML Fonte:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/Simulador_Modelo_OSI.html|Simulador_Modelo_OSI.html]]

---

```dataviewjs
const container = dv.el("div", "", { cls: "osi-sim-wrapper" });

container.innerHTML = `
<div style="background: #0d1117; border: 2px solid #30363d; border-radius: 8px; font-family: 'Segoe UI', system-ui, sans-serif; color: #e2e8f0; box-shadow: 0 4px 16px rgba(0,0,0,0.6); overflow: hidden; margin-bottom: 16px;">

  <!-- Header -->
  <div style="background: #161b22; border-bottom: 1px solid #30363d; padding: 10px 14px; display: flex; justify-content: space-between; align-items: center;">
    <div style="color: #58a6ff; font-weight: bold; font-size: 13px;">🌐 MODELO OSI & ENCAPSULAMENTO INTERATIVO</div>
    <div style="font-size: 11px; color: #8b949e;">Clique nas camadas ou nos botões de simulação</div>
  </div>

  <div style="display: flex; flex-direction: row; gap: 12px; padding: 12px; min-height: 480px;">

    <!-- Left: 7 Layers List -->
    <div style="flex: 1.1; display: flex; flex-direction: column; gap: 6px;">
      <button class="osi-layer-btn" data-layer="7" style="display:flex; align-items:center; justify-content:space-between; background:#161b22; border:1px solid #30363d; border-radius:6px; padding:8px 10px; cursor:pointer; color:#fff; text-align:left; width:100%;">
        <div style="display:flex; align-items:center; gap:8px;">
          <span style="background:#d55e00; color:#fff; font-weight:bold; border-radius:4px; width:26px; height:26px; display:flex; align-items:center; justify-content:center;">7</span>
          <div>
            <div style="font-weight:bold; font-size:12px;">Aplicação</div>
            <div style="font-size:10px; color:#8b949e;">PDU: Dados (HTTP, DNS)</div>
          </div>
        </div>
      </button>

      <button class="osi-layer-btn" data-layer="6" style="display:flex; align-items:center; justify-content:space-between; background:#161b22; border:1px solid #30363d; border-radius:6px; padding:8px 10px; cursor:pointer; color:#fff; text-align:left; width:100%;">
        <div style="display:flex; align-items:center; gap:8px;">
          <span style="background:#e69f00; color:#fff; font-weight:bold; border-radius:4px; width:26px; height:26px; display:flex; align-items:center; justify-content:center;">6</span>
          <div>
            <div style="font-weight:bold; font-size:12px;">Apresentação</div>
            <div style="font-size:10px; color:#8b949e;">PDU: Dados (TLS, JSON)</div>
          </div>
        </div>
      </button>

      <button class="osi-layer-btn" data-layer="5" style="display:flex; align-items:center; justify-content:space-between; background:#161b22; border:1px solid #30363d; border-radius:6px; padding:8px 10px; cursor:pointer; color:#fff; text-align:left; width:100%;">
        <div style="display:flex; align-items:center; gap:8px;">
          <span style="background:#f0e442; color:#000; font-weight:bold; border-radius:4px; width:26px; height:26px; display:flex; align-items:center; justify-content:center;">5</span>
          <div>
            <div style="font-weight:bold; font-size:12px; color:#f0e442;">Sessão</div>
            <div style="font-size:10px; color:#8b949e;">PDU: Dados (RPC, Sockets)</div>
          </div>
        </div>
      </button>

      <button class="osi-layer-btn" data-layer="4" style="display:flex; align-items:center; justify-content:space-between; background:#161b22; border:1px solid #30363d; border-radius:6px; padding:8px 10px; cursor:pointer; color:#fff; text-align:left; width:100%;">
        <div style="display:flex; align-items:center; gap:8px;">
          <span style="background:#009e73; color:#fff; font-weight:bold; border-radius:4px; width:26px; height:26px; display:flex; align-items:center; justify-content:center;">4</span>
          <div>
            <div style="font-weight:bold; font-size:12px;">Transporte</div>
            <div style="font-size:10px; color:#8b949e;">PDU: Segmento (TCP, UDP)</div>
          </div>
        </div>
      </button>

      <button class="osi-layer-btn" data-layer="3" style="display:flex; align-items:center; justify-content:space-between; background:#161b22; border:1px solid #30363d; border-radius:6px; padding:8px 10px; cursor:pointer; color:#fff; text-align:left; width:100%;">
        <div style="display:flex; align-items:center; gap:8px;">
          <span style="background:#56b4e9; color:#000; font-weight:bold; border-radius:4px; width:26px; height:26px; display:flex; align-items:center; justify-content:center;">3</span>
          <div>
            <div style="font-weight:bold; font-size:12px; color:#56b4e9;">Rede</div>
            <div style="font-size:10px; color:#8b949e;">PDU: Pacote (IPv4, ICMP)</div>
          </div>
        </div>
      </button>

      <button class="osi-layer-btn" data-layer="2" style="display:flex; align-items:center; justify-content:space-between; background:#1f293d; border:1px solid #58a6ff; border-radius:6px; padding:8px 10px; cursor:pointer; color:#fff; text-align:left; width:100%;">
        <div style="display:flex; align-items:center; gap:8px;">
          <span style="background:#0072b2; color:#fff; font-weight:bold; border-radius:4px; width:26px; height:26px; display:flex; align-items:center; justify-content:center;">2</span>
          <div>
            <div style="font-weight:bold; font-size:12px; color:#58a6ff;">Enlace de Dados (TechSolutions)</div>
            <div style="font-size:10px; color:#8b949e;">PDU: Quadro (MAC, VLANs, LACP)</div>
          </div>
        </div>
      </button>

      <button class="osi-layer-btn" data-layer="1" style="display:flex; align-items:center; justify-content:space-between; background:#161b22; border:1px solid #30363d; border-radius:6px; padding:8px 10px; cursor:pointer; color:#fff; text-align:left; width:100%;">
        <div style="display:flex; align-items:center; gap:8px;">
          <span style="background:#8b949e; color:#fff; font-weight:bold; border-radius:4px; width:26px; height:26px; display:flex; align-items:center; justify-content:center;">1</span>
          <div>
            <div style="font-weight:bold; font-size:12px;">Física</div>
            <div style="font-size:10px; color:#8b949e;">PDU: Bits (Cabos, PoE, SFP)</div>
          </div>
        </div>
      </button>
    </div>

    <!-- Right: Inspector -->
    <div style="flex: 1.4; background:#161b22; border:1px solid #30363d; border-radius:6px; padding:12px; display:flex; flex-direction:column;">
      <div style="display:flex; gap:6px; margin-bottom:10px; border-bottom:1px solid #30363d; padding-bottom:8px;">
        <button id="osiTabDetails" style="background:#21262d; color:#58a6ff; border:1px solid #30363d; border-radius:4px; padding:3px 8px; font-size:11px; cursor:pointer; font-weight:bold;">Detalhes da Camada</button>
        <button id="osiTabEncap" style="background:transparent; color:#8b949e; border:none; padding:3px 8px; font-size:11px; cursor:pointer;">Simulador de Encapsulamento</button>
        <button id="osiTabTrouble" style="background:transparent; color:#8b949e; border:none; padding:3px 8px; font-size:11px; cursor:pointer;">Troubleshooting OSI</button>
      </div>

      <div id="osiDynamicContent" style="flex:1; overflow-y:auto; font-size:12px; line-height:1.45;"></div>
    </div>
  </div>
</div>
`;

// Logic
const osiData = {
  7: {
    name: "Camada 7 — Aplicação (Application)",
    pdu: "Dados (Data)",
    desc: "Interface direta com aplicações. Fornece protocolos para web, e-mail, DNS e gerência.",
    devices: "Host (PC / Servidor), Proxy, Firewall WAF",
    protocols: "HTTP/HTTPS (80/443), DNS (53), SSH (22), DHCP (67/68)",
    cisco: "Serviços locais no switch como servidor DHCP ou agentes SNMP."
  },
  6: {
    name: "Camada 6 — Apresentação (Presentation)",
    pdu: "Dados (Data)",
    desc: "Tradução de dados, compressão e criptografia (TLS/SSL).",
    devices: "Host / Sistema Operacional",
    protocols: "TLS/SSL, JSON, XML, JPEG, UTF-8",
    cisco: "Criptografia RSA para conexões SSH no Cisco IOS."
  },
  5: {
    name: "Camada 5 — Sessão (Session)",
    pdu: "Dados (Data)",
    desc: "Estabelece, gerencia e finaliza sessões de comunicação entre aplicações.",
    devices: "Host / Sistema Operacional",
    protocols: "RPC, NetBIOS, Sockets POSIX",
    cisco: "Manutenção de sessões VTY (linhas 0-4) no switch."
  },
  4: {
    name: "Camada 4 — Transporte (Transport)",
    pdu: "Segmento (TCP) / Datagrama (UDP)",
    desc: "Comunicação fim-a-fim, portas lógicas e controle de fluxo.",
    devices: "Firewalls Stateful, Balanceadores L4",
    protocols: "TCP (Confiável), UDP (Baixa latência para voz)",
    cisco: "QoS para pacotes de voz UDP (RTP) na Voice VLAN 40."
  },
  3: {
    name: "Camada 3 — Rede (Network)",
    pdu: "Pacote (Packet)",
    desc: "Endereçamento lógico IPv4/IPv6 e roteamento entre sub-redes.",
    devices: "Roteadores, Switches de Camada 3 (Multilayer)",
    protocols: "IPv4, IPv6, ICMP (Ping), OSPF, ARP",
    cisco: "Roteamento Inter-VLAN via SVIs e Gateways padrão (192.168.X.1)."
  },
  2: {
    name: "Camada 2 — Enlace de Dados (Data Link)",
    pdu: "Quadro (Frame)",
    desc: "Comutação de quadros na rede local por endereços MAC. Foco principal do lab TechSolutions.",
    devices: "Switches L2 (Cisco 3560), Placas de Rede (NIC)",
    protocols: "Ethernet, IEEE 802.1Q (VLANs), LACP (IEEE 802.3ad), STP",
    cisco: "Port-Channel 1 (LACP) unindo Gig0/1 + Gig0/2, Trunks e Voice VLAN 40."
  },
  1: {
    name: "Camada 1 — Física (Physical)",
    pdu: "Bits (0s e 1s)",
    desc: "Transmissão bruta de sinais elétricos e ópticos através do meio físico.",
    devices: "Cabos Cat5e/Cat6, Fibra Óptica, Portas PoE",
    protocols: "1000BASE-T (Gigabit), PoE (IEEE 802.3af)",
    cisco: "Portas PoE alimentando os Telefones IP Cisco 7960 e cabos Gigabit."
  }
};

let curLayer = 2;
let curTab = "details";

const contentArea = container.querySelector("#osiDynamicContent");
const tabDet = container.querySelector("#osiTabDetails");
const tabEnc = container.querySelector("#osiTabEncap");
const tabTro = container.querySelector("#osiTabTrouble");
const layerBtns = container.querySelectorAll(".osi-layer-btn");

function renderOsi() {
  if (curTab === "details") {
    const d = osiData[curLayer];
    contentArea.innerHTML = `
      <div style="font-size:14px; font-weight:bold; color:#58a6ff; margin-bottom:4px;">${d.name}</div>
      <div style="color:#e69f00; font-weight:bold; margin-bottom:8px;">PDU: <span style="color:#fff;">${d.pdu}</span></div>
      <p style="color:#c9d1d9; margin-bottom:8px;">${d.desc}</p>
      <div style="font-weight:bold; color:#58a6ff; margin-top:8px;">Equipamentos:</div>
      <div style="color:#8b949e;">${d.devices}</div>
      <div style="font-weight:bold; color:#58a6ff; margin-top:8px;">Protocolos:</div>
      <div style="color:#8b949e;">${d.protocols}</div>
      <div style="font-weight:bold; color:#3fb950; margin-top:10px; background:#0d1117; padding:6px 8px; border-left:3px solid #2ea043; border-radius:0 4px 4px 0;">
        <strong>Lab TechSolutions:</strong> ${d.cisco}
      </div>
    `;
  } else if (curTab === "encap") {
    contentArea.innerHTML = `
      <div style="font-size:13px; font-weight:bold; color:#58a6ff; margin-bottom:6px;">📦 Simulador de Encapsulamento (Fluxo PDU)</div>
      <div style="display:flex; gap:6px; margin-bottom:10px;">
        <button id="simPingBtn" style="background:#238636; color:#fff; border:1px solid #2ea043; border-radius:4px; padding:4px 8px; font-size:11px; cursor:pointer;">Simular Pacote ICMP (Ping)</button>
        <button id="simVoipBtn" style="background:#0284c7; color:#fff; border:1px solid #38bdf8; border-radius:4px; padding:4px 8px; font-size:11px; cursor:pointer;">Simular Quadro VoIP (Voice VLAN)</button>
      </div>
      <div id="simResArea" style="background:#090d13; border:1px solid #30363d; border-radius:4px; padding:8px;">
        <div style="color:#8b949e; font-style:italic;">Clique em um botão acima para gerar a anatomia do quadro!</div>
      </div>
    `;

    contentArea.querySelector("#simPingBtn").addEventListener("click", () => {
      contentArea.querySelector("#simResArea").innerHTML = `
        <div style="color:#4ade80; font-weight:bold; margin-bottom:4px;">Quadro Ethernet com Tag 802.1Q (Ping VLAN 10):</div>
        <div style="display:flex; border:1px solid #58a6ff; border-radius:4px; overflow:hidden; font-size:10px; font-family:monospace; text-align:center; margin:6px 0;">
          <div style="background:#0072b2; color:#fff; padding:6px 4px; flex:1.2;">L2 MAC<br/><span style="font-size:9px;">Dst/Src</span></div>
          <div style="background:#f0e442; color:#000; padding:6px 4px; flex:1;">802.1Q<br/><span style="font-size:9px;">VLAN 10</span></div>
          <div style="background:#56b4e9; color:#000; padding:6px 4px; flex:1.5;">L3 IPv4<br/><span style="font-size:9px;">192.168.10.11</span></div>
          <div style="background:#009e73; color:#fff; padding:6px 4px; flex:1;">ICMP Echo<br/><span style="font-size:9px;">Type 8</span></div>
          <div style="background:#8b949e; color:#fff; padding:6px 4px; flex:0.8;">FCS<br/><span style="font-size:9px;">CRC-32</span></div>
        </div>
        <div style="color:#c9d1d9; font-size:11px;">Trânsito via Port-Channel 1 (LACP) sem necessidade de roteador L3.</div>
      `;
    });

    contentArea.querySelector("#simVoipBtn").addEventListener("click", () => {
      contentArea.querySelector("#simResArea").innerHTML = `
        <div style="color:#38bdf8; font-weight:bold; margin-bottom:4px;">Quadro de Voz (Voice VLAN 40 + QoS CoS 5):</div>
        <div style="display:flex; border:1px solid #38bdf8; border-radius:4px; overflow:hidden; font-size:10px; font-family:monospace; text-align:center; margin:6px 0;">
          <div style="background:#0072b2; color:#fff; padding:6px 4px; flex:1.1;">L2 MAC<br/><span style="font-size:9px;">IP Phone</span></div>
          <div style="background:#d55e00; color:#fff; padding:6px 4px; flex:1.1;">802.1Q<br/><span style="font-size:9px;">VLAN 40 (CoS 5)</span></div>
          <div style="background:#56b4e9; color:#000; padding:6px 4px; flex:1.3;">IP (DSCP EF)<br/><span style="font-size:9px;">Expedited</span></div>
          <div style="background:#009e73; color:#fff; padding:6px 4px; flex:1;">UDP / RTP<br/><span style="font-size:9px;">Áudio</span></div>
          <div style="background:#e69f00; color:#fff; padding:6px 4px; flex:1.2;">Payload<br/><span style="font-size:9px;">Voz G.711</span></div>
        </div>
        <div style="color:#c9d1d9; font-size:11px;">Prioridade garantida contra latência e jitter no switch.</div>
      `;
    });
  } else if (curTab === "trouble") {
    contentArea.innerHTML = `
      <div style="font-size:13px; font-weight:bold; color:#f85149; margin-bottom:6px;">🚨 Diagnóstico por Camadas OSI</div>
      <div style="display:flex; flex-direction:column; gap:4px; margin-bottom:10px;">
        <button class="osi-tro-btn" data-t="1" style="background:#21262d; color:#f85149; border:1px solid #da3633; border-radius:4px; padding:4px 8px; font-size:11px; cursor:pointer; text-align:left;">🔌 Falha L1: Cabo desconectado / Porta em Shutdown</button>
        <button class="osi-tro-btn" data-t="2" style="background:#21262d; color:#f85149; border:1px solid #da3633; border-radius:4px; padding:4px 8px; font-size:11px; cursor:pointer; text-align:left;">🏷️ Falha L2: Porta no EtherChannel suspensa (s)</button>
        <button class="osi-tro-btn" data-t="3" style="background:#21262d; color:#f85149; border:1px solid #da3633; border-radius:4px; padding:4px 8px; font-size:11px; cursor:pointer; text-align:left;">🌐 Falha L3: Ping inter-VLAN falha (Sem rota L3)</button>
      </div>
      <div id="osiTroRes" style="background:#090d13; border:1px solid #30363d; border-radius:4px; padding:8px; font-size:11px; color:#c9d1d9;"></div>
    `;

    contentArea.querySelectorAll(".osi-tro-btn").forEach(b => {
      b.addEventListener("click", () => {
        const t = b.getAttribute("data-t");
        const res = contentArea.querySelector("#osiTroRes");
        if (t === "1") {
          res.innerHTML = "<strong style='color:#8b949e;'>Camada 1 (Física):</strong> Link apagado. Execute <code style='color:#58a6ff;'>no shutdown</code> na interface.";
        } else if (t === "2") {
          res.innerHTML = "<strong style='color:#56b4e9;'>Camada 2 (Enlace/LACP):</strong> Inconsistência de velocidade/VLAN nativa. Execute <code style='color:#58a6ff;'>show etherchannel summary</code> para identificar a porta.";
        } else if (t === "3") {
          res.innerHTML = "<strong style='color:#f0e442;'>Camada 3 (Rede):</strong> Hosts em sub-redes distintas. Requer roteador inter-VLAN ou switch L3 com <code style='color:#58a6ff;'>ip routing</code>.";
        }
      });
    });
  }
}

tabDet.addEventListener("click", () => {
  curTab = "details";
  tabDet.style.color = "#58a6ff"; tabDet.style.background = "#21262d";
  tabEnc.style.color = "#8b949e"; tabEnc.style.background = "transparent";
  tabTro.style.color = "#8b949e"; tabTro.style.background = "transparent";
  renderOsi();
});

tabEnc.addEventListener("click", () => {
  curTab = "encap";
  tabEnc.style.color = "#58a6ff"; tabEnc.style.background = "#21262d";
  tabDet.style.color = "#8b949e"; tabDet.style.background = "transparent";
  tabTro.style.color = "#8b949e"; tabTro.style.background = "transparent";
  renderOsi();
});

tabTro.addEventListener("click", () => {
  curTab = "trouble";
  tabTro.style.color = "#58a6ff"; tabTro.style.background = "#21262d";
  tabDet.style.color = "#8b949e"; tabDet.style.background = "transparent";
  tabEnc.style.color = "#8b949e"; tabEnc.style.background = "transparent";
  renderOsi();
});

layerBtns.forEach(btn => {
  btn.addEventListener("click", () => {
    layerBtns.forEach(b => {
      b.style.background = "#161b22";
      b.style.borderColor = "#30363d";
    });
    btn.style.background = "#1f293d";
    btn.style.borderColor = "#58a6ff";
    curLayer = parseInt(btn.getAttribute("data-layer"));
    if (curTab !== "details") {
      curTab = "details";
      tabDet.click();
    } else {
      renderOsi();
    }
  });
});

renderOsi();
```

---

## 📊 Tabela Completa de Consulta Rápida (7 Camadas OSI)

| Camada | Nome | PDU | Equipamentos Principais | Protocolos / Padrões | Função Principal |
|:---:|---|:---:|---|---|---|
| **7** | **Aplicação** | Dados | Host, Firewall WAF | HTTP, HTTPS, DNS, SSH, DHCP | Interface direta com o usuário e programas |
| **6** | **Apresentação** | Dados | Host / SO | TLS/SSL, JSON, XML, JPEG | Tradução, criptografia e compressão |
| **5** | **Sessão** | Dados | Host / SO | RPC, NetBIOS, Sockets | Controle e sincronização de diálogos |
| **4** | **Transporte** | Segmento | Firewall L4, Balanceador | TCP, UDP (Portas lógicas) | Comunicação fim-a-fim e controle de fluxo |
| **3** | **Rede** | Pacote | Roteador, Switch L3 | IPv4, IPv6, ICMP, OSPF, ARP | Endereçamento lógico e roteamento |
| **2** | **Enlace** | Quadro | Switch L2, Placa de Rede | Ethernet, IEEE 802.1Q, LACP, STP | Comutação física por MAC e segmentação VLAN |
| **1** | **Física** | Bits | Cabos Cat6/Fibra, Transceiver | 1000BASE-T, PoE (802.3af) | Transmissão de sinais brutos no meio físico |
