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

> [!info] 📌 Guia de Referência e Simulador do Modelo OSI
> - **Disciplina:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Estudos Avançados em Ciências da Computação|Estudos Avançados em Ciências da Computação]]
> - **Laboratório TechSolutions:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/2026-08-12 - Resolução - Exercício Prático Aula 2 - TechSolutions|Resolução Exercício Aula 2 (TechSolutions)]]
> - **Terminal Cisco:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Aulas/Terminal Interativo - Cisco IOS Catalyst 3560|Terminal Interativo Cisco IOS]]
> - **Arquivo HTML Fonte:** [[01 - Disciplinas/Estudos Avançados em Ciências da Computação/Materiais/Simulador_Modelo_OSI.html|Simulador_Modelo_OSI.html]]

---

## 🎮 Simulador Interativo do Modelo OSI

<iframe srcdoc="&lt;!DOCTYPE html&gt;
&lt;html lang=&quot;pt-BR&quot;&gt;
&lt;head&gt;
  &lt;meta charset=&quot;UTF-8&quot;&gt;
  &lt;title&gt;Simulador Interativo do Modelo OSI&lt;/title&gt;
  &lt;style&gt;
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body {
      background-color: #0b0f19;
      color: #e2e8f0;
      font-family: &#x27;Segoe UI&#x27;, system-ui, -apple-system, sans-serif;
      padding: 14px;
      height: 100vh;
      display: flex;
      flex-direction: column;
      overflow: hidden;
    }
    .app-container {
      display: flex;
      flex: 1;
      gap: 14px;
      overflow: hidden;
    }
    .left-panel {
      flex: 1.1;
      display: flex;
      flex-direction: column;
      gap: 6px;
      overflow-y: auto;
      padding-right: 6px;
    }
    .right-panel {
      flex: 1.4;
      background: #0d1117;
      border: 1px solid #30363d;
      border-radius: 8px;
      display: flex;
      flex-direction: column;
      overflow: hidden;
      box-shadow: 0 4px 16px rgba(0,0,0,0.5);
    }
    .panel-header {
      background: #161b22;
      border-bottom: 1px solid #30363d;
      padding: 10px 14px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .panel-title {
      color: #58a6ff;
      font-weight: 700;
      font-size: 13px;
    }
    /* Layer Cards */
    .layer-card {
      background: #161b22;
      border: 1px solid #30363d;
      border-radius: 6px;
      padding: 10px 12px;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: space-between;
      transition: all 0.2s ease;
      user-select: none;
    }
    .layer-card:hover {
      border-color: #58a6ff;
      transform: translateX(3px);
    }
    .layer-card.active {
      border-color: #58a6ff;
      background: #1f293d;
      box-shadow: 0 0 10px rgba(88, 166, 255, 0.25);
    }
    .layer-num {
      font-weight: 800;
      font-size: 15px;
      width: 32px;
      height: 32px;
      border-radius: 6px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 10px;
    }
    .layer-info {
      flex: 1;
    }
    .layer-name {
      font-weight: 700;
      font-size: 13px;
      color: #f0f6fc;
    }
    .layer-pdu {
      font-size: 11px;
      color: #8b949e;
    }
    /* Okabe-Ito Colorblind-Safe Layer Badges */
    .l7 { background: #d55e00; color: #fff; } /* Vermilion */
    .l6 { background: #e69f00; color: #fff; } /* Orange */
    .l5 { background: #f0e442; color: #000; } /* Yellow */
    .l4 { background: #009e73; color: #fff; } /* Teal */
    .l3 { background: #56b4e9; color: #000; } /* Sky Blue */
    .l2 { background: #0072b2; color: #fff; } /* Deep Blue */
    .l1 { background: #8b949e; color: #fff; } /* Slate */

    /* Right Panel Tabs &amp; Details */
    .tab-bar {
      background: #161b22;
      border-bottom: 1px solid #30363d;
      display: flex;
    }
    .tab-btn {
      flex: 1;
      padding: 8px 12px;
      background: transparent;
      border: none;
      border-bottom: 2px solid transparent;
      color: #8b949e;
      font-size: 12px;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.2s;
    }
    .tab-btn.active {
      color: #58a6ff;
      border-bottom-color: #58a6ff;
      background: #0d1117;
    }
    .tab-content {
      flex: 1;
      padding: 14px;
      overflow-y: auto;
      font-size: 13px;
      line-height: 1.5;
    }
    .badge {
      display: inline-block;
      padding: 2px 8px;
      border-radius: 12px;
      font-size: 11px;
      font-weight: 600;
      background: #21262d;
      color: #79c0ff;
      border: 1px solid #30363d;
      margin: 2px 3px 2px 0;
    }
    .section-title {
      font-size: 12px;
      font-weight: 700;
      color: #58a6ff;
      text-transform: uppercase;
      letter-spacing: 0.5px;
      margin: 12px 0 6px 0;
      border-bottom: 1px solid #21262d;
      padding-bottom: 3px;
    }
    /* Encapsulation Animation Box */
    .encap-box {
      background: #090d13;
      border: 1px solid #30363d;
      border-radius: 6px;
      padding: 12px;
      margin-top: 10px;
    }
    .frame-wrapper {
      display: flex;
      border: 2px solid #58a6ff;
      border-radius: 6px;
      overflow: hidden;
      margin: 12px 0;
      font-family: &#x27;Consolas&#x27;, monospace;
      font-size: 11px;
      text-align: center;
    }
    .frame-hdr {
      padding: 10px 6px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      border-right: 1px solid #30363d;
      font-weight: bold;
    }
    .btn-action {
      background: #238636;
      color: #fff;
      border: 1px solid #2ea043;
      border-radius: 4px;
      padding: 6px 12px;
      font-size: 12px;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.2s;
    }
    .btn-action:hover { background: #2ea043; }
    .trouble-btn {
      background: #21262d;
      color: #f85149;
      border: 1px solid #da3633;
      border-radius: 4px;
      padding: 4px 8px;
      font-size: 11px;
      cursor: pointer;
      margin: 3px;
    }
    .trouble-btn:hover { background: #b62324; color: #fff; }
  &lt;/style&gt;
&lt;/head&gt;
&lt;body&gt;

  &lt;div class=&quot;panel-header&quot; style=&quot;border-radius: 8px 8px 0 0; margin-bottom: 8px; border: 1px solid #30363d;&quot;&gt;
    &lt;div class=&quot;panel-title&quot;&gt;🌐 SIMULADOR INTERATIVO: MODELO OSI &amp; ENCAPSULAMENTO PDU&lt;/div&gt;
    &lt;div style=&quot;font-size: 12px; color: #8b949e;&quot;&gt;Clique em uma camada ou use o simulador de pacotes&lt;/div&gt;
  &lt;/div&gt;

  &lt;div class=&quot;app-container&quot;&gt;
    &lt;!-- Left: 7 Layers List --&gt;
    &lt;div class=&quot;left-panel&quot;&gt;
      &lt;div class=&quot;layer-card&quot; onclick=&quot;selectLayer(7)&quot;&gt;
        &lt;div style=&quot;display: flex; align-items: center;&quot;&gt;
          &lt;div class=&quot;layer-num l7&quot;&gt;7&lt;/div&gt;
          &lt;div class=&quot;layer-info&quot;&gt;
            &lt;div class=&quot;layer-name&quot;&gt;Aplicação (Application)&lt;/div&gt;
            &lt;div class=&quot;layer-pdu&quot;&gt;PDU: Dados | Interação direta com softwares&lt;/div&gt;
          &lt;/div&gt;
        &lt;/div&gt;
        &lt;span class=&quot;badge&quot;&gt;HTTP, DNS, SSH, DHCP&lt;/span&gt;
      &lt;/div&gt;

      &lt;div class=&quot;layer-card&quot; onclick=&quot;selectLayer(6)&quot;&gt;
        &lt;div style=&quot;display: flex; align-items: center;&quot;&gt;
          &lt;div class=&quot;layer-num l6&quot;&gt;6&lt;/div&gt;
          &lt;div class=&quot;layer-info&quot;&gt;
            &lt;div class=&quot;layer-name&quot;&gt;Apresentação (Presentation)&lt;/div&gt;
            &lt;div class=&quot;layer-pdu&quot;&gt;PDU: Dados | Formatação, Criptografia e Compressão&lt;/div&gt;
          &lt;/div&gt;
        &lt;/div&gt;
        &lt;span class=&quot;badge&quot;&gt;TLS/SSL, JSON, JPEG&lt;/span&gt;
      &lt;/div&gt;

      &lt;div class=&quot;layer-card&quot; onclick=&quot;selectLayer(5)&quot;&gt;
        &lt;div style=&quot;display: flex; align-items: center;&quot;&gt;
          &lt;div class=&quot;layer-num l5&quot;&gt;5&lt;/div&gt;
          &lt;div class=&quot;layer-info&quot;&gt;
            &lt;div class=&quot;layer-name&quot; style=&quot;color:#000;&quot;&gt;Sessão (Session)&lt;/div&gt;
            &lt;div class=&quot;layer-pdu&quot; style=&quot;color:#333;&quot;&gt;PDU: Dados | Estabelecimento e Controle de Diálogo&lt;/div&gt;
          &lt;/div&gt;
        &lt;/div&gt;
        &lt;span class=&quot;badge&quot;&gt;RPC, NetBIOS, Sockets&lt;/span&gt;
      &lt;/div&gt;

      &lt;div class=&quot;layer-card&quot; onclick=&quot;selectLayer(4)&quot;&gt;
        &lt;div style=&quot;display: flex; align-items: center;&quot;&gt;
          &lt;div class=&quot;layer-num l4&quot;&gt;4&lt;/div&gt;
          &lt;div class=&quot;layer-info&quot;&gt;
            &lt;div class=&quot;layer-name&quot;&gt;Transporte (Transport)&lt;/div&gt;
            &lt;div class=&quot;layer-pdu&quot;&gt;PDU: Segmento | Comunicação Fim-a-Fim e Portas&lt;/div&gt;
          &lt;/div&gt;
        &lt;/div&gt;
        &lt;span class=&quot;badge&quot;&gt;TCP, UDP (Portas L4)&lt;/span&gt;
      &lt;/div&gt;

      &lt;div class=&quot;layer-card&quot; onclick=&quot;selectLayer(3)&quot;&gt;
        &lt;div style=&quot;display: flex; align-items: center;&quot;&gt;
          &lt;div class=&quot;layer-num l3&quot;&gt;3&lt;/div&gt;
          &lt;div class=&quot;layer-info&quot;&gt;
            &lt;div class=&quot;layer-name&quot; style=&quot;color:#000;&quot;&gt;Rede (Network)&lt;/div&gt;
            &lt;div class=&quot;layer-pdu&quot; style=&quot;color:#333;&quot;&gt;PDU: Pacote | Endereçamento Lógico &amp; Roteamento&lt;/div&gt;
          &lt;/div&gt;
        &lt;/div&gt;
        &lt;span class=&quot;badge&quot;&gt;IPv4, IPv6, ICMP, OSPF&lt;/span&gt;
      &lt;/div&gt;

      &lt;div class=&quot;layer-card active&quot; onclick=&quot;selectLayer(2)&quot;&gt;
        &lt;div style=&quot;display: flex; align-items: center;&quot;&gt;
          &lt;div class=&quot;layer-num l2&quot;&gt;2&lt;/div&gt;
          &lt;div class=&quot;layer-info&quot;&gt;
            &lt;div class=&quot;layer-name&quot;&gt;Enlace de Dados (Data Link)&lt;/div&gt;
            &lt;div class=&quot;layer-pdu&quot;&gt;PDU: Quadro (Frame) | MAC, VLANs, Trunks &amp; LACP&lt;/div&gt;
          &lt;/div&gt;
        &lt;/div&gt;
        &lt;span class=&quot;badge&quot;&gt;Ethernet, 802.1Q, LACP&lt;/span&gt;
      &lt;/div&gt;

      &lt;div class=&quot;layer-card&quot; onclick=&quot;selectLayer(1)&quot;&gt;
        &lt;div style=&quot;display: flex; align-items: center;&quot;&gt;
          &lt;div class=&quot;layer-num l1&quot;&gt;1&lt;/div&gt;
          &lt;div class=&quot;layer-info&quot;&gt;
            &lt;div class=&quot;layer-name&quot;&gt;Física (Physical)&lt;/div&gt;
            &lt;div class=&quot;layer-pdu&quot;&gt;PDU: Bits | Sinais elétricos, ópticos e cabos&lt;/div&gt;
          &lt;/div&gt;
        &lt;/div&gt;
        &lt;span class=&quot;badge&quot;&gt;RJ45, Fibra, 1000BASE-T&lt;/span&gt;
      &lt;/div&gt;
    &lt;/div&gt;

    &lt;!-- Right: Inspector &amp; Packet Simulator --&gt;
    &lt;div class=&quot;right-panel&quot;&gt;
      &lt;div class=&quot;tab-bar&quot;&gt;
        &lt;button class=&quot;tab-btn active&quot; id=&quot;tabBtnDetails&quot; onclick=&quot;setTab(&#x27;details&#x27;)&quot;&gt;🔍 Detalhes da Camada&lt;/button&gt;
        &lt;button class=&quot;tab-btn&quot; id=&quot;tabBtnEncap&quot; onclick=&quot;setTab(&#x27;encap&#x27;)&quot;&gt;📦 Simulador de Encapsulamento&lt;/button&gt;
        &lt;button class=&quot;tab-btn&quot; id=&quot;tabBtnTrouble&quot; onclick=&quot;setTab(&#x27;trouble&#x27;)&quot;&gt;🚨 Troubleshooting OSI&lt;/button&gt;
      &lt;/div&gt;

      &lt;div class=&quot;tab-content&quot; id=&quot;tabContent&quot;&gt;
        &lt;!-- Injected dynamically by JS --&gt;
      &lt;/div&gt;
    &lt;/div&gt;
  &lt;/div&gt;

  &lt;script&gt;
    const osiData = {
      7: {
        name: &quot;Camada 7 — Aplicação (Application)&quot;,
        pdu: &quot;Dados (Data)&quot;,
        desc: &quot;Interface direta com as aplicações do usuário. Fornece protocolos e serviços para navegação web, envio de e-mails, resolução de nomes e transferência de arquivos.&quot;,
        devices: [&quot;Host (PC / Servidor)&quot;, &quot;Proxy de Aplicação&quot;, &quot;Firewall NGFW (Layer 7 WAF)&quot;],
        protocols: [&quot;HTTP/HTTPS (Porta 80/443)&quot;, &quot;DNS (Porta 53)&quot;, &quot;SSH (Porta 22)&quot;, &quot;DHCP (Porta 67/68)&quot;, &quot;Telnet&quot;, &quot;SNMP&quot;],
        security: &quot;Ataques de injeção SQL, XSS, DoS de aplicação e exploração de API. Protegido por WAF e autenticação multifator (MFA).&quot;,
        ciscoContext: &quot;Configuração de serviços de rede como servidores DHCP locais ou agentes de gerenciamento SNMP nos switches.&quot;
      },
      6: {
        name: &quot;Camada 6 — Apresentação (Presentation)&quot;,
        pdu: &quot;Dados (Data)&quot;,
        desc: &quot;Responsável pela tradução de formatos de dados, compressão e criptografia/descriptografia, garantindo que sistemas heterogêneos consigam interpretar a mensagem.&quot;,
        devices: [&quot;Host / SO&quot;, &quot;Aceleradores Criptográficos (TLS Offloading)&quot;],
        protocols: [&quot;TLS/SSL&quot;, &quot;JSON&quot;, &quot;XML&quot;, &quot;JPEG&quot;, &quot;MPEG&quot;, &quot;ASCII&quot;, &quot;Unicode / UTF-8&quot;],
        security: &quot;Vulnerabilidades em bibliotecas de criptografia e serialização de dados.&quot;,
        ciscoContext: &quot;Geração de chaves RSA para SSH (`crypto key generate rsa modulus 1024`) para cifrar sessões de terminal.&quot;
      },
      5: {
        name: &quot;Camada 5 — Sessão (Session)&quot;,
        pdu: &quot;Dados (Data)&quot;,
        desc: &quot;Gerencia, sincroniza e encerra as conexões lógicas (sessões) e checkpoints de diálogo entre duas aplicações cliente-servidor.&quot;,
        devices: [&quot;Host / Sistema Operacional&quot;],
        protocols: [&quot;RPC (Remote Procedure Call)&quot;, &quot;NetBIOS&quot;, &quot;PPTP&quot;, &quot;Sockets POSIX&quot;],
        security: &quot;Ataques de Session Hijacking (Sequestro de Sessão) e Man-in-the-Middle.&quot;,
        ciscoContext: &quot;Manutenção de sessões de gerenciamento VTY (linhas 0 a 4) abertas durante o comando `configure terminal`.&quot;
      },
      4: {
        name: &quot;Camada 4 — Transporte (Transport)&quot;,
        pdu: &quot;Segmento (TCP) / Datagrama (UDP)&quot;,
        desc: &quot;Responsável pela comunicação de ponta a ponta, controle de fluxo, multiplexação por portas lógicas (0 a 65535) e garantia de entrega com confirmação (ACK).&quot;,
        devices: [&quot;Firewalls L4 (Stateful)&quot;, &quot;Balanceadores de Carga L4&quot;],
        protocols: [&quot;TCP (Orientado a conexão / 3-way handshake)&quot;, &quot;UDP (Sem conexão / Baixa latência para Voz VoIP)&quot;],
        security: &quot;Ataques de TCP SYN Flood e Port Scanning. Mitigado por Rate Limiting e Firewalls Stateful.&quot;,
        ciscoContext: &quot;QoS para pacotes de voz UDP (RTP) na Voice VLAN para evitar jitter e perda de pacotes.&quot;
      },
      3: {
        name: &quot;Camada 3 — Rede (Network)&quot;,
        pdu: &quot;Pacote (Packet)&quot;,
        desc: &quot;Responsável pelo endereçamento lógico (IPv4 / IPv6) e roteamento de pacotes através de múltiplas sub-redes e sistemas autônomos.&quot;,
        devices: [&quot;Roteadores (Routers)&quot;, &quot;Switches de Camada 3 (Multilayer Switch)&quot;, &quot;Firewalls&quot;],
        protocols: [&quot;IPv4&quot;, &quot;IPv6&quot;, &quot;ICMP (Ping)&quot;, &quot;OSPF&quot;, &quot;EIGRP&quot;, &quot;BGP&quot;, &quot;ARP&quot;],
        security: &quot;Ataques de IP Spoofing, ICMP Smurf e roteamento malicioso. Mitigado por ACLs e IP Source Guard.&quot;,
        ciscoContext: &quot;Roteamento Inter-VLAN via SVIs (`interface Vlan10`, `ip routing`) e Default Gateways dos hosts (192.168.X.1).&quot;
      },
      2: {
        name: &quot;Camada 2 — Enlace de Dados (Data Link)&quot;,
        pdu: &quot;Quadro (Frame)&quot;,
        desc: &quot;Comutação de quadros na rede local baseada em endereços físicos MAC de 48 bits. Segmenta domínios de broadcast com VLANs e agrega enlaces com EtherChannel.&quot;,
        devices: [&quot;Switches L2 (Cisco Catalyst)&quot;, &quot;Placas de Rede (NIC)&quot;, &quot;Bridges&quot;],
        protocols: [&quot;Ethernet (IEEE 802.3)&quot;, &quot;IEEE 802.1Q (VLAN Tagging)&quot;, &quot;LACP (IEEE 802.3ad)&quot;, &quot;STP (802.1D)&quot;, &quot;CDP&quot;, &quot;LLDP&quot;],
        security: &quot;VLAN Hopping, MAC Flooding, STP Manipulation, DHCP Spoofing. Protegido por Port-Security e VLAN Nativa isolada.&quot;,
        ciscoContext: &quot;O foco central da **Aula 2 (TechSolutions)**: Port-Channel 1 unindo Gig0/1 + Gig0/2 em LACP, Trunks 802.1Q e Voice VLAN.&quot;
      },
      1: {
        name: &quot;Camada 1 — Física (Physical)&quot;,
        pdu: &quot;Bits (0s e 1s)&quot;,
        desc: &quot;Transmissão e recepção do fluxo bruto de bits através do meio físico, especificando pinagens, conectores, voltagens e frequências.&quot;,
        devices: [&quot;Cabos Par Trançado (Cat5e, Cat6)&quot;, &quot;Cabos de Fibra Óptica&quot;, &quot;Transceivers SFP&quot;, &quot;Hubs&quot;],
        protocols: [&quot;100BASE-TX&quot;, &quot;1000BASE-T (Gigabit Ethernet)&quot;, &quot;10GBASE-SR&quot;, &quot;PoE (IEEE 802.3af/at)&quot;],
        security: &quot;Grampos físicos (tapping) e corte de cabos. Mitigado por redundância física no EtherChannel.&quot;,
        ciscoContext: &quot;Portas PoE alimentando os Telefones IP Cisco 7960 e links Gigabit operando a 1 Gbps full-duplex.&quot;
      }
    };

    let selectedLayer = 2;
    let currentTab = &quot;details&quot;;

    function selectLayer(l) {
      selectedLayer = l;
      document.querySelectorAll(&quot;.layer-card&quot;).forEach((card, idx) =&gt; {
        card.classList.toggle(&quot;active&quot;, (7 - idx) === l);
      });
      renderTab();
    }

    function setTab(tab) {
      currentTab = tab;
      document.getElementById(&quot;tabBtnDetails&quot;).classList.toggle(&quot;active&quot;, tab === &quot;details&quot;);
      document.getElementById(&quot;tabBtnEncap&quot;).classList.toggle(&quot;active&quot;, tab === &quot;encap&quot;);
      document.getElementById(&quot;tabBtnTrouble&quot;).classList.toggle(&quot;active&quot;, tab === &quot;trouble&quot;);
      renderTab();
    }

    function renderTab() {
      const container = document.getElementById(&quot;tabContent&quot;);
      if (currentTab === &quot;details&quot;) {
        const d = osiData[selectedLayer];
        container.innerHTML = `
          &lt;div style=&quot;font-size: 16px; font-weight: bold; color: #58a6ff; margin-bottom: 4px;&quot;&gt;${d.name}&lt;/div&gt;
          &lt;div style=&quot;color: #e69f00; font-weight: 600; margin-bottom: 8px;&quot;&gt;PDU da Camada: &lt;span style=&quot;color:#fff;&quot;&gt;${d.pdu}&lt;/span&gt;&lt;/div&gt;
          &lt;p style=&quot;color: #c9d1d9; margin-bottom: 12px;&quot;&gt;${d.desc}&lt;/p&gt;

          &lt;div class=&quot;section-title&quot;&gt;🔌 Equipamentos &amp; Dispositivos&lt;/div&gt;
          &lt;div&gt;${d.devices.map(dev =&gt; `&lt;span class=&quot;badge&quot; style=&quot;border-color:#58a6ff;&quot;&gt;${dev}&lt;/span&gt;`).join(&quot;&quot;)}&lt;/div&gt;

          &lt;div class=&quot;section-title&quot;&gt;📜 Protocolos &amp; Tecnologias&lt;/div&gt;
          &lt;div&gt;${d.protocols.map(p =&gt; `&lt;span class=&quot;badge&quot;&gt;${p}&lt;/span&gt;`).join(&quot;&quot;)}&lt;/div&gt;

          &lt;div class=&quot;section-title&quot;&gt;🔒 Postura de Segurança &amp; Ameaças&lt;/div&gt;
          &lt;div style=&quot;color: #f0883e;&quot;&gt;${d.security}&lt;/div&gt;

          &lt;div class=&quot;section-title&quot;&gt;🏢 Conexão com o Laboratório TechSolutions&lt;/div&gt;
          &lt;div style=&quot;color: #7ee787; background:#161b22; border-left:3px solid #2ea043; padding:8px 10px; border-radius:0 4px 4px 0;&quot;&gt;
            ${d.ciscoContext}
          &lt;/div&gt;
        `;
      } else if (currentTab === &quot;encap&quot;) {
        container.innerHTML = `
          &lt;div style=&quot;font-size: 15px; font-weight: bold; color: #58a6ff; margin-bottom: 6px;&quot;&gt;📦 Simulador de Encapsulamento (Fluxo PDU)&lt;/div&gt;
          &lt;p style=&quot;color: #8b949e; margin-bottom: 10px;&quot;&gt;Veja como os dados do usuário são empacotados com cabeçalhos de cada camada até virarem bits no cabo:&lt;/p&gt;

          &lt;div style=&quot;display:flex; gap:8px; margin-bottom:12px;&quot;&gt;
            &lt;button class=&quot;btn-action&quot; onclick=&quot;simulatePacket(&#x27;ping&#x27;)&quot;&gt;Simular Pacote ICMP (Ping)&lt;/button&gt;
            &lt;button class=&quot;btn-action&quot; style=&quot;background:#0284c7; border-color:#38bdf8;&quot; onclick=&quot;simulatePacket(&#x27;voip&#x27;)&quot;&gt;Simular Quadro de Voz (Voice VLAN)&lt;/button&gt;
          &lt;/div&gt;

          &lt;div id=&quot;encapVisualArea&quot;&gt;
            &lt;div style=&quot;color:#94a3b8; font-style:italic;&quot;&gt;Clique em um dos botões acima para gerar a estrutura do quadro!&lt;/div&gt;
          &lt;/div&gt;
        `;
      } else if (currentTab === &quot;trouble&quot;) {
        container.innerHTML = `
          &lt;div style=&quot;font-size: 15px; font-weight: bold; color: #f85149; margin-bottom: 6px;&quot;&gt;🚨 Troubleshooting Baseado em Camadas OSI&lt;/div&gt;
          &lt;p style=&quot;color: #8b949e; margin-bottom: 12px;&quot;&gt;Selecione um sintoma de falha para diagnosticar a camada responsável:&lt;/p&gt;

          &lt;div&gt;
            &lt;button class=&quot;trouble-btn&quot; onclick=&quot;showTrouble(1)&quot;&gt;🔌 Falha 1: Cabo de rede desconectado / Porta em Shutdown&lt;/button&gt;
            &lt;button class=&quot;trouble-btn&quot; onclick=&quot;showTrouble(2)&quot;&gt;🏷️ Falha 2: Porta no EtherChannel com flag (s) Suspended&lt;/button&gt;
            &lt;button class=&quot;trouble-btn&quot; onclick=&quot;showTrouble(3)&quot;&gt;🌐 Falha 3: PC-ADM-01 não pinga PC-FIN-01 (Inter-VLAN)&lt;/button&gt;
            &lt;button class=&quot;trouble-btn&quot; onclick=&quot;showTrouble(4)&quot;&gt;🛑 Falha 4: Erro de Native VLAN Mismatch no Trunk&lt;/button&gt;
          &lt;/div&gt;

          &lt;div id=&quot;troubleResult&quot; style=&quot;margin-top:14px;&quot;&gt;&lt;/div&gt;
        `;
      }
    }

    function simulatePacket(type) {
      const area = document.getElementById(&quot;encapVisualArea&quot;);
      if (type === &quot;ping&quot;) {
        area.innerHTML = `
          &lt;div class=&quot;encap-box&quot;&gt;
            &lt;div style=&quot;font-weight:bold; color:#7ee787; margin-bottom:6px;&quot;&gt;Quadro Ethernet 802.1Q (Ping entre VLAN 10):&lt;/div&gt;
            &lt;div class=&quot;frame-wrapper&quot;&gt;
              &lt;div class=&quot;frame-hdr&quot; style=&quot;background:#0072b2; color:#fff; flex:1.2;&quot;&gt;
                &lt;div&gt;L2 Header&lt;/div&gt;
                &lt;div style=&quot;font-size:9px;&quot;&gt;MAC Dst / Src&lt;/div&gt;
              &lt;/div&gt;
              &lt;div class=&quot;frame-hdr&quot; style=&quot;background:#f0e442; color:#000; flex:1;&quot;&gt;
                &lt;div&gt;Tag 802.1Q&lt;/div&gt;
                &lt;div style=&quot;font-size:9px;&quot;&gt;VLAN ID: 10&lt;/div&gt;
              &lt;/div&gt;
              &lt;div class=&quot;frame-hdr&quot; style=&quot;background:#56b4e9; color:#000; flex:1.5;&quot;&gt;
                &lt;div&gt;L3 IP Header&lt;/div&gt;
                &lt;div style=&quot;font-size:9px;&quot;&gt;192.168.10.11 &amp;rarr; .12&lt;/div&gt;
              &lt;/div&gt;
              &lt;div class=&quot;frame-hdr&quot; style=&quot;background:#009e73; color:#fff; flex:1.2;&quot;&gt;
                &lt;div&gt;ICMP Echo&lt;/div&gt;
                &lt;div style=&quot;font-size:9px;&quot;&gt;Type 8 (Request)&lt;/div&gt;
              &lt;/div&gt;
              &lt;div class=&quot;frame-hdr&quot; style=&quot;background:#8b949e; color:#fff; flex:0.8;&quot;&gt;
                &lt;div&gt;FCS&lt;/div&gt;
                &lt;div style=&quot;font-size:9px;&quot;&gt;CRC-32&lt;/div&gt;
              &lt;/div&gt;
            &lt;/div&gt;
            &lt;div style=&quot;font-size:12px; color:#c9d1d9;&quot;&gt;
              • &lt;strong&gt;Caminho:&lt;/strong&gt; PC-ADM-01 &amp;rarr; SW-ACCESS-1 &amp;rarr; SW-CORE-1 &amp;rarr; [Port-Channel 1 LACP] &amp;rarr; SW-CORE-2 &amp;rarr; SW-ACCESS-2 &amp;rarr; PC-ADM-02.
            &lt;/div&gt;
          &lt;/div&gt;
        `;
      } else {
        area.innerHTML = `
          &lt;div class=&quot;encap-box&quot;&gt;
            &lt;div style=&quot;font-weight:bold; color:#38bdf8; margin-bottom:6px;&quot;&gt;Quadro de Telefonia IP (Voice VLAN com Prioridade QoS):&lt;/div&gt;
            &lt;div class=&quot;frame-wrapper&quot;&gt;
              &lt;div class=&quot;frame-hdr&quot; style=&quot;background:#0072b2; color:#fff; flex:1.1;&quot;&gt;
                &lt;div&gt;L2 Ethernet&lt;/div&gt;
                &lt;div style=&quot;font-size:9px;&quot;&gt;MAC IP-Phone&lt;/div&gt;
              &lt;/div&gt;
              &lt;div class=&quot;frame-hdr&quot; style=&quot;background:#d55e00; color:#fff; flex:1.1;&quot;&gt;
                &lt;div&gt;802.1Q + CoS&lt;/div&gt;
                &lt;div style=&quot;font-size:9px;&quot;&gt;VLAN 40 (CoS 5)&lt;/div&gt;
              &lt;/div&gt;
              &lt;div class=&quot;frame-hdr&quot; style=&quot;background:#56b4e9; color:#000; flex:1.3;&quot;&gt;
                &lt;div&gt;IP (DSCP EF)&lt;/div&gt;
                &lt;div style=&quot;font-size:9px;&quot;&gt;Expedited Forwarding&lt;/div&gt;
              &lt;/div&gt;
              &lt;div class=&quot;frame-hdr&quot; style=&quot;background:#009e73; color:#fff; flex:1;&quot;&gt;
                &lt;div&gt;UDP / RTP&lt;/div&gt;
                &lt;div style=&quot;font-size:9px;&quot;&gt;Áudio Voz&lt;/div&gt;
              &lt;/div&gt;
              &lt;div class=&quot;frame-hdr&quot; style=&quot;background:#e69f00; color:#fff; flex:1.5;&quot;&gt;
                &lt;div&gt;Payload G.711&lt;/div&gt;
                &lt;div style=&quot;font-size:9px;&quot;&gt;Voz Digitalizada&lt;/div&gt;
              &lt;/div&gt;
            &lt;/div&gt;
            &lt;div style=&quot;font-size:12px; color:#c9d1d9;&quot;&gt;
              • &lt;strong&gt;Marcação QoS:&lt;/strong&gt; A tag 802.1Q carrega os 3 bits de prioridade (CoS 5), garantindo que os switches coloquem os pacotes de áudio na fila prioritária de transmissão (*Strict Priority Queue*).
            &lt;/div&gt;
          &lt;/div&gt;
        `;
      }
    }

    function showTrouble(id) {
      const res = document.getElementById(&quot;troubleResult&quot;);
      if (id === 1) {
        res.innerHTML = `
          &lt;div style=&quot;background:#161b22; border-left:4px solid #8b949e; padding:10px; border-radius:4px;&quot;&gt;
            &lt;div style=&quot;color:#8b949e; font-weight:bold;&quot;&gt;Camada Afetada: CAMADA 1 (FÍSICA)&lt;/div&gt;
            &lt;p style=&quot;color:#c9d1d9; font-size:12px; margin-top:4px;&quot;&gt;
              • &lt;strong&gt;Sintoma:&lt;/strong&gt; Interface no estado &lt;code&gt;administratively down&lt;/code&gt; ou sem link elétrico.&lt;br/&gt;
              • &lt;strong&gt;Diagnóstico:&lt;/strong&gt; &lt;code&gt;show interfaces status&lt;/code&gt; mostra porta desconectada.&lt;br/&gt;
              • &lt;strong&gt;Comando de Correção:&lt;/strong&gt; &lt;code&gt;interface FastEthernet0/1&lt;/code&gt; &amp;rarr; &lt;code&gt;no shutdown&lt;/code&gt;.
            &lt;/p&gt;
          &lt;/div&gt;
        `;
      } else if (id === 2) {
        res.innerHTML = `
          &lt;div style=&quot;background:#161b22; border-left:4px solid #0072b2; padding:10px; border-radius:4px;&quot;&gt;
            &lt;div style=&quot;color:#56b4e9; font-weight:bold;&quot;&gt;Camada Afetada: CAMADA 2 (ENLACE DE DADOS / LACP)&lt;/div&gt;
            &lt;p style=&quot;color:#c9d1d9; font-size:12px; margin-top:4px;&quot;&gt;
              • &lt;strong&gt;Sintoma:&lt;/strong&gt; O bundle EtherChannel suspende a porta para evitar loops e vazamento.&lt;br/&gt;
              • &lt;strong&gt;Causa:&lt;/strong&gt; Inconsistência de velocidade, duplex ou VLAN nativa nas portas físicas do canal.&lt;br/&gt;
              • &lt;strong&gt;Comando de Diagnóstico:&lt;/strong&gt; &lt;code&gt;show etherchannel summary&lt;/code&gt;.
            &lt;/p&gt;
          &lt;/div&gt;
        `;
      } else if (id === 3) {
        res.innerHTML = `
          &lt;div style=&quot;background:#161b22; border-left:4px solid #56b4e9; padding:10px; border-radius:4px;&quot;&gt;
            &lt;div style=&quot;color:#56b4e9; font-weight:bold;&quot;&gt;Camada Afetada: CAMADA 3 (REDE / ROTEAMENTO)&lt;/div&gt;
            &lt;p style=&quot;color:#c9d1d9; font-size:12px; margin-top:4px;&quot;&gt;
              • &lt;strong&gt;Sintoma:&lt;/strong&gt; Hosts em VLANs distintas não se comunicam (Request timed out).&lt;br/&gt;
              • &lt;strong&gt;Causa:&lt;/strong&gt; Falta de roteamento inter-VLAN. Switches L2 isolam o broadcast e não roteiam pacotes IP.&lt;br/&gt;
              • &lt;strong&gt;Comando de Correção:&lt;/strong&gt; Habilitar &lt;code&gt;ip routing&lt;/code&gt; e configurar SVIs com IP em switch L3.
            &lt;/p&gt;
          &lt;/div&gt;
        `;
      } else if (id === 4) {
        res.innerHTML = `
          &lt;div style=&quot;background:#161b22; border-left:4px solid #d55e00; padding:10px; border-radius:4px;&quot;&gt;
            &lt;div style=&quot;color:#f0883e; font-weight:bold;&quot;&gt;Camada Afetada: CAMADA 2 (TRUNKING 802.1Q)&lt;/div&gt;
            &lt;p style=&quot;color:#c9d1d9; font-size:12px; margin-top:4px;&quot;&gt;
              • &lt;strong&gt;Sintoma:&lt;/strong&gt; Mensagens de CDP alertando &lt;code&gt;Native VLAN mismatch discovered&lt;/code&gt;.&lt;br/&gt;
              • &lt;strong&gt;Causa:&lt;/strong&gt; Um lado configurado com VLAN 99 e o outro com VLAN 1.&lt;br/&gt;
              • &lt;strong&gt;Comando de Correção:&lt;/strong&gt; &lt;code&gt;switchport trunk native vlan 99&lt;/code&gt; em ambos os lados.
            &lt;/p&gt;
          &lt;/div&gt;
        `;
      }
    }

    // Initial render
    renderTab();
  &lt;/script&gt;
&lt;/body&gt;
&lt;/html&gt;" width="100%" height="680px" style="border: 2px solid #30363d; border-radius: 8px; background-color: #0b0f19;" frameborder="0"></iframe>

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

---

## 🔍 Resumo do Processo de Encapsulamento
1. **Origem (Host emissor):** Os dados da aplicação descem as camadas (7 $ightarrow$ 1), recebendo cabeçalhos em cada etapa (*Headers*).
2. **Camada 4:** Adiciona porta de origem e destino (ex: TCP 443 ou UDP 5060 para voz).
3. **Camada 3:** Adiciona endereço IP de origem e destino (ex: `192.168.10.11` $ightarrow$ `192.168.10.12`).
4. **Camada 2:** Adiciona endereços MAC e a tag **802.1Q** com o VLAN ID (ex: VLAN 10 ou Voice VLAN 40 com CoS 5).
5. **Camada 1:** Converte o quadro Ethernet completo em impulsos elétricos ou luz e transmite pelo cabo físico.
