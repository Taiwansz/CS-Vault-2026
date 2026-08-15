---
tipo: ferramenta-interativa
disciplina: Estudos Avançados em Ciências da Computação
tags:
  - redes
  - sub-redes
  - vlsm
  - cidr
  - calculadora
  - cisco
  - ipv4
---

# 🔢 Calculadora Automática de Sub-redes & VLSM (Variable Length Subnet Mask)

> [!info] 📌 Ferramenta de Dimensionamento e Planejamento IPv4
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Laboratório TechSolutions:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-12 - Resolução - Exercício Prático Aula 2 - TechSolutions|Resolução Exercício Aula 2 (TechSolutions)]]
> - **Terminal Cisco:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/Terminal Interativo - Cisco IOS Catalyst 3560|Terminal Interativo Cisco IOS]]
> - **Simulador OSI:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/Simulador Interativo - Modelo OSI e Encapsulamento|Simulador do Modelo OSI]]

---

```dataviewjs
const container = dv.el("div", "", { cls: "vlsm-calc-wrapper" });

container.innerHTML = `
<div style="background: #0d1117; border: 2px solid #30363d; border-radius: 8px; font-family: 'Segoe UI', system-ui, sans-serif; color: #e2e8f0; box-shadow: 0 4px 16px rgba(0,0,0,0.6); overflow: hidden; margin-bottom: 16px;">

  <!-- Header -->
  <div style="background: #161b22; border-bottom: 1px solid #30363d; padding: 10px 14px; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 8px;">
    <div style="color: #58a6ff; font-weight: bold; font-size: 13px; display: flex; align-items: center; gap: 8px;">
      <span style="width: 10px; height: 10px; background: #56B4E9; border-radius: 50%; display: inline-block; box-shadow: 0 0 8px #56B4E9;"></span>
      <span>CALCULADORA AUTOMÁTICA DE SUB-REDES VLSM</span>
    </div>
    <div style="display: flex; gap: 6px;">
      <button id="vlsmPresetBtn" style="background: #21262d; color: #58a6ff; border: 1px solid #30363d; border-radius: 4px; padding: 3px 10px; font-size: 11px; cursor: pointer; font-weight: 600;">Carregar Lab TechSolutions</button>
      <button id="vlsmResetBtn" style="background: #21262d; color: #8b949e; border: 1px solid #30363d; border-radius: 4px; padding: 3px 8px; font-size: 11px; cursor: pointer;">Limpar</button>
    </div>
  </div>

  <div style="padding: 14px;">
    <!-- Network Base Input -->
    <div style="display: flex; gap: 12px; align-items: center; margin-bottom: 14px; background: #161b22; padding: 10px; border-radius: 6px; border: 1px solid #30363d;">
      <div>
        <label style="font-size: 12px; color: #8b949e; display: block; margin-bottom: 3px;">Rede Principal IPv4:</label>
        <input type="text" id="vlsmBaseIp" value="192.168.0.0" style="background: #0d1117; color: #f0f6fc; border: 1px solid #30363d; border-radius: 4px; padding: 4px 8px; font-family: monospace; font-size: 13px; width: 140px;" />
      </div>
      <div>
        <label style="font-size: 12px; color: #8b949e; display: block; margin-bottom: 3px;">Prefixo CIDR:</label>
        <select id="vlsmBasePrefix" style="background: #0d1117; color: #f0f6fc; border: 1px solid #30363d; border-radius: 4px; padding: 4px 8px; font-family: monospace; font-size: 13px;">
          <option value="24" selected>/24 (255.255.255.0 - 256 IPs)</option>
          <option value="23">/23 (255.255.254.0 - 512 IPs)</option>
          <option value="22">/22 (255.255.252.0 - 1024 IPs)</option>
          <option value="16">/16 (255.255.0.0 - 65536 IPs)</option>
          <option value="8">/8 (255.0.0.0 - 16M IPs)</option>
        </select>
      </div>
      <div style="margin-top: 16px;">
        <button id="vlsmCalcBtn" style="background: #238636; color: #fff; border: 1px solid #2ea043; border-radius: 4px; padding: 5px 14px; font-size: 12px; font-weight: bold; cursor: pointer;">Calcular VLSM</button>
      </div>
    </div>

    <!-- Requirements Rows -->
    <div style="margin-bottom: 12px;">
      <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 6px;">
        <span style="font-size: 12px; font-weight: bold; color: #58a6ff;">Setores / Sub-redes Solicitadas:</span>
        <button id="vlsmAddRowBtn" style="background: #21262d; color: #4ade80; border: 1px solid #30363d; border-radius: 4px; padding: 2px 8px; font-size: 11px; cursor: pointer;">+ Adicionar Setor</button>
      </div>
      <div id="vlsmRowsContainer" style="display: flex; flex-direction: column; gap: 6px;">
        <!-- Injected by JS -->
      </div>
    </div>

    <!-- Results Table -->
    <div id="vlsmResultsArea" style="margin-top: 14px;">
      <!-- Results Table Injected Here -->
    </div>
  </div>
</div>
`;

// VLSM Logic
let subnets = [
  { name: "VLAN 40 - VOZ", hosts: 50 },
  { name: "VLAN 10 - ADMINISTRATIVO", hosts: 30 },
  { name: "VLAN 20 - FINANCEIRO", hosts: 15 },
  { name: "VLAN 30 - TI", hosts: 10 },
  { name: "VLAN 99 - NATIVA / LINKS", hosts: 2 }
];

const rowsCont = container.querySelector("#vlsmRowsContainer");
const resultsArea = container.querySelector("#vlsmResultsArea");
const baseIpInput = container.querySelector("#vlsmBaseIp");
const basePrefixSelect = container.querySelector("#vlsmBasePrefix");

function renderRows() {
  rowsCont.innerHTML = "";
  subnets.forEach((sub, idx) => {
    const row = document.createElement("div");
    row.style = "display: flex; gap: 8px; align-items: center;";
    row.innerHTML = `
      <input type="text" value="${sub.name}" data-idx="${idx}" class="vlsm-name-input" style="flex: 1.5; background: #161b22; color: #f0f6fc; border: 1px solid #30363d; border-radius: 4px; padding: 4px 8px; font-size: 12px;" placeholder="Nome da VLAN / Setor" />
      <input type="number" value="${sub.hosts}" data-idx="${idx}" class="vlsm-hosts-input" min="1" max="10000" style="width: 100px; background: #161b22; color: #56B4E9; border: 1px solid #30363d; border-radius: 4px; padding: 4px 8px; font-family: monospace; font-size: 12px;" placeholder="Hosts" />
      <span style="font-size: 11px; color: #8b949e;">hosts</span>
      <button data-idx="${idx}" class="vlsm-del-btn" style="background: transparent; color: #f87171; border: 1px solid #da3633; border-radius: 4px; padding: 2px 8px; font-size: 11px; cursor: pointer;">&times;</button>
    `;
    rowsCont.appendChild(row);
  });

  // Attach event listeners
  rowsCont.querySelectorAll(".vlsm-name-input").forEach(inp => {
    inp.addEventListener("input", (e) => {
      subnets[e.target.getAttribute("data-idx")].name = e.target.value;
    });
  });

  rowsCont.querySelectorAll(".vlsm-hosts-input").forEach(inp => {
    inp.addEventListener("input", (e) => {
      subnets[e.target.getAttribute("data-idx")].hosts = parseInt(e.target.value) || 0;
    });
  });

  rowsCont.querySelectorAll(".vlsm-del-btn").forEach(btn => {
    btn.addEventListener("click", (e) => {
      const idx = parseInt(e.target.getAttribute("data-idx"));
      subnets.splice(idx, 1);
      renderRows();
      calculateVLSM();
    });
  });
}

function ipToInt(ip) {
  return ip.split('.').reduce((acc, octet) => (acc << 8) + parseInt(octet, 10), 0) >>> 0;
}

function intToIp(int) {
  return [(int >>> 24) & 255, (int >>> 16) & 255, (int >>> 8) & 255, int & 255].join('.');
}

function calculateVLSM() {
  const baseIpStr = baseIpInput.value.trim();
  const basePrefix = parseInt(basePrefixSelect.value);

  // Sort subnets descending by hosts (fundamental VLSM rule)
  const sorted = [...subnets].map((s, id) => ({ ...s, origId: id })).sort((a, b) => b.hosts - a.hosts);

  let currentIpInt = ipToInt(baseIpStr);
  const maxIpInt = (currentIpInt + Math.pow(2, 32 - basePrefix) - 1) >>> 0;

  let allocated = [];
  let overflow = false;

  for (let s of sorted) {
    if (s.hosts <= 0) continue;
    // Find smallest power of 2 where 2^h - 2 >= hosts
    let h = 1;
    while ((Math.pow(2, h) - 2) < s.hosts) {
      h++;
    }
    const blockSize = Math.pow(2, h);
    const prefix = 32 - h;
    const maskInt = (0xFFFFFFFF << h) >>> 0;
    const maskStr = intToIp(maskInt);

    const netIpInt = currentIpInt;
    const firstIpInt = netIpInt + 1;
    const bcastIpInt = netIpInt + blockSize - 1;
    const lastIpInt = bcastIpInt - 1;

    if (bcastIpInt > maxIpInt) {
      overflow = true;
    }

    allocated.push({
      name: s.name,
      needed: s.hosts,
      usableHosts: blockSize - 2,
      blockSize: blockSize,
      prefix: prefix,
      mask: maskStr,
      network: intToIp(netIpInt),
      firstHost: intToIp(firstIpInt),
      lastHost: intToIp(lastIpInt),
      broadcast: intToIp(bcastIpInt)
    });

    currentIpInt = (bcastIpInt + 1) >>> 0;
  }

  let html = "";
  if (overflow) {
    html += `
      <div style="background: #1f1315; border: 1px solid #da3633; color: #f85149; padding: 10px; border-radius: 6px; font-size: 12px; margin-bottom: 12px;">
        🚨 <strong>Alerta de Esgotamento:</strong> O espaço de endereçamento da rede base /${basePrefix} foi excedido com a quantidade de hosts solicitados! Escolha uma rede maior (ex: /23 ou /22).
      </div>
    `;
  }

  html += `
    <div style="font-size: 13px; font-weight: bold; color: #58a6ff; margin-bottom: 8px;">📊 Tabela VLSM Calculada:</div>
    <div style="overflow-x: auto;">
      <table style="width: 100%; border-collapse: collapse; font-size: 12px; font-family: monospace; text-align: left;">
        <thead>
          <tr style="background: #161b22; border-bottom: 2px solid #30363d; color: #56B4E9;">
            <th style="padding: 6px 8px;">Sub-rede / Setor</th>
            <th style="padding: 6px 8px;">Hosts Req.</th>
            <th style="padding: 6px 8px;">Prefixo</th>
            <th style="padding: 6px 8px;">Máscara</th>
            <th style="padding: 6px 8px;">Rede</th>
            <th style="padding: 6px 8px;">Faixa Utilizável</th>
            <th style="padding: 6px 8px;">Broadcast</th>
          </tr>
        </thead>
        <tbody>
  `;

  allocated.forEach((a, i) => {
    const bg = i % 2 === 0 ? "#0d1117" : "#161b22";
    html += `
      <tr style="background: ${bg}; border-bottom: 1px solid #21262d;">
        <td style="padding: 6px 8px; font-weight: bold; color: #f0f6fc;">${a.name}</td>
        <td style="padding: 6px 8px; color: #e69f00;">${a.needed} (${a.usableHosts} máx)</td>
        <td style="padding: 6px 8px; color: #58a6ff;">/${a.prefix}</td>
        <td style="padding: 6px 8px; color: #8b949e;">${a.mask}</td>
        <td style="padding: 6px 8px; color: #4ade80; font-weight: bold;">${a.network}</td>
        <td style="padding: 6px 8px; color: #c9d1d9;">${a.firstHost} &rarr; ${a.lastHost}</td>
        <td style="padding: 6px 8px; color: #f87171;">${a.broadcast}</td>
      </tr>
    `;
  });

  html += `
        </tbody>
      </table>
    </div>
  `;

  resultsArea.innerHTML = html;
}

container.querySelector("#vlsmCalcBtn").addEventListener("click", calculateVLSM);
container.querySelector("#vlsmAddRowBtn").addEventListener("click", () => {
  subnets.push({ name: `Setor ${subnets.length + 1}`, hosts: 20 });
  renderRows();
  calculateVLSM();
});

container.querySelector("#vlsmPresetBtn").addEventListener("click", () => {
  baseIpInput.value = "192.168.10.0";
  basePrefixSelect.value = "24";
  subnets = [
    { name: "VLAN 40 - VOZ (IP Phones)", hosts: 50 },
    { name: "VLAN 10 - ADMINISTRATIVO", hosts: 30 },
    { name: "VLAN 20 - FINANCEIRO", hosts: 15 },
    { name: "VLAN 30 - TI", hosts: 10 },
    { name: "VLAN 99 - NATIVA (Trunks)", hosts: 2 }
  ];
  renderRows();
  calculateVLSM();
});

container.querySelector("#vlsmResetBtn").addEventListener("click", () => {
  subnets = [{ name: "Setor 1", hosts: 50 }, { name: "Setor 2", hosts: 20 }];
  renderRows();
  calculateVLSM();
});

renderRows();
calculateVLSM();
```

---

## 📚 Fundamentos do Cálculo VLSM (Variable Length Subnet Mask)

O **VLSM** elimina o desperdício de endereços IPv4 ao atribuir máscaras com tamanhos personalizados baseados na necessidade exata de cada setor:

1. **Ordenação Decrescente Obrigatória:** Sempre calcule primeiro a sub-rede que exige a maior quantidade de hosts (ex: Setor de Voz com 50 hosts antes do Setor de TI com 10 hosts).
2. **Fórmula da Potência de 2:** Para $N$ hosts necessários, encontra-se o menor expoente $h$ tal que:
   $$2^h - 2 \ge N$$
   *(Os 2 endereços subtraídos são o IP de Rede e o IP de Broadcast).*
3. **Prefixo CIDR:** O prefixo da sub-rede é dado por $32 - h$.
