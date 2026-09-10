// ==============================================================================
// SCRIPT DE AUTOMAÇÃO - CISCO PACKET TRACER (SCRIPT ENGINE / MCP)
// ATIVIDADE PRÁTICA: REDUNDÂNCIA DE GATEWAY E ALTA DISPONIBILIDADE COM HSRP (AULA 5)
// ==============================================================================

try {
    var net = ipc.network();
    var lw = ipc.appWindow().getActiveWorkspace().getLogicalWorkspace();

    // --------------------------------------------------------------------------
    // 0. LIMPEZA PREVENTIVA
    // --------------------------------------------------------------------------
    var devicesToRemove = [
        'R1-PRINCIPAL', 'R2-BACKUP', 'SW1-LAN', 'PC1', 'PC2', 'SERVER1',
        'R1', 'R2', 'SW1'
    ];
    for (var i = 0; i < devicesToRemove.length; i++) {
        var d = net.getDevice(devicesToRemove[i]);
        if (d) {
            try { lw.removeDevice(devicesToRemove[i]); } catch (e) {}
        }
    }

    // Helper para adicionar e renomear dispositivo com segurança
    function addDev(typeInt, model, name, x, y) {
        var autoName = lw.addDevice(typeInt, model, x, y);
        var dev = net.getDevice(autoName);
        if (dev) { dev.setName(name); }
        return dev;
    }

    // Helper para adicionar blocos de texto (anotações no Canvas)
    function putNote(x, y, text) {
        var z = (typeof lw.getIncNoteZOrder === 'function') ? lw.getIncNoteZOrder() : 0;
        lw.addNote(x, y, z, text);
    }

    // --------------------------------------------------------------------------
    // 1. CRIAR DISPOSITIVOS NO CANVAS LÓGICO
    // (type: 0 = Router, 1 = Switch, 8 = PC, 9 = Server)
    // --------------------------------------------------------------------------
    // Roteadores Cisco 2911
    var r1 = addDev(0, '2911', 'R1-PRINCIPAL', 280, 140);
    var r2 = addDev(0, '2911', 'R2-BACKUP',    620, 140);

    // Switch Cisco 2960-24TT
    var sw1 = addDev(1, '2960-24TT', 'SW1-LAN', 450, 290);

    // Hosts (Computadores e Servidor)
    var pc1  = addDev(8, 'PC-PT', 'PC1', 250, 450);
    var pc2  = addDev(8, 'PC-PT', 'PC2', 450, 450);
    var srv1 = addDev(9, 'Server-PT', 'SERVER1', 650, 450);

    // --------------------------------------------------------------------------
    // 2. CONEXÕES DE CABOS (8100 = Straight / Cabo Direto)
    // --------------------------------------------------------------------------
    // Roteadores conectados ao Switch
    lw.createLink('SW1-LAN', 'GigabitEthernet0/1', 'R1-PRINCIPAL', 'GigabitEthernet0/0', 8100);
    lw.createLink('SW1-LAN', 'GigabitEthernet0/2', 'R2-BACKUP',    'GigabitEthernet0/0', 8100);

    // Hosts conectados ao Switch
    lw.createLink('SW1-LAN', 'FastEthernet0/1', 'PC1',     'FastEthernet0', 8100);
    lw.createLink('SW1-LAN', 'FastEthernet0/2', 'PC2',     'FastEthernet0', 8100);
    lw.createLink('SW1-LAN', 'FastEthernet0/3', 'SERVER1', 'FastEthernet0', 8100);

    // --------------------------------------------------------------------------
    // 3. CONFIGURAÇÃO IP ESTÁTICA DOS COMPUTADORES E SERVIDOR
    // Gateway obrigatório: 192.168.10.1 (IP Virtual HSRP)
    // --------------------------------------------------------------------------
    function configureHost(dev, ip, mask, gw) {
        if (!dev) return;
        var p = dev.getPort('FastEthernet0');
        if (p) { p.setIpSubnetMask(ip, mask); }
        dev.setDefaultGateway(gw);
    }

    configureHost(pc1,  '192.168.10.10',  '255.255.255.0', '192.168.10.1');
    configureHost(pc2,  '192.168.10.20',  '255.255.255.0', '192.168.10.1');
    configureHost(srv1, '192.168.10.100', '255.255.255.0', '192.168.10.1');

    // --------------------------------------------------------------------------
    // 4. CONFIGURAÇÃO IOS DOS ROTEADORES E SWITCH
    // --------------------------------------------------------------------------
    function sendCli(dev, cmds) {
        if (!dev) return;
        var cl = dev.getCommandLine();
        if (cl) {
            for (var i = 0; i < cmds.length; i++) {
                cl.enterCommand(cmds[i]);
            }
        }
    }

    // Switch SW1-LAN
    sendCli(sw1, [
        'enable',
        'configure terminal',
        'hostname SW1-LAN',
        'interface GigabitEthernet0/1',
        'no shutdown',
        'interface GigabitEthernet0/2',
        'no shutdown',
        'interface range FastEthernet0/1 - 3',
        'no shutdown',
        'end',
        'write memory'
    ]);

    // Roteador R1-PRINCIPAL (Active - Priority 110 - Preempt)
    sendCli(r1, [
        'enable',
        'configure terminal',
        'hostname R1-PRINCIPAL',
        'interface GigabitEthernet0/0',
        'ip address 192.168.10.2 255.255.255.0',
        'no shutdown',
        'standby 1 ip 192.168.10.1',
        'standby 1 priority 110',
        'standby 1 preempt',
        'end',
        'write memory'
    ]);

    // Roteador R2-BACKUP (Standby - Priority 90 - Preempt)
    sendCli(r2, [
        'enable',
        'configure terminal',
        'hostname R2-BACKUP',
        'interface GigabitEthernet0/0',
        'ip address 192.168.10.3 255.255.255.0',
        'no shutdown',
        'standby 1 ip 192.168.10.1',
        'standby 1 priority 90',
        'standby 1 preempt',
        'end',
        'write memory'
    ]);

    // --------------------------------------------------------------------------
    // 5. ANOTAÇÕES DIDÁTICAS NO CANVAS (LABELS)
    // --------------------------------------------------------------------------
    putNote(260, 40, "========================================================================\n" +
                     "  ATIVIDADE PRATICA - AULA 5: REDUNDANCIA E ALTA DISPONIBILIDADE HSRP  \n" +
                     "  Gateway Virtual: 192.168.10.1 (HSRP Grupo 1) | Sub-rede: 192.168.10.0/24\n" +
                     "========================================================================");

    putNote(150, 190, "[R1-PRINCIPAL - ACTIVE]\n" +
                      "IP Fisico: 192.168.10.2/24\n" +
                      "HSRP Grupo 1: 192.168.10.1\n" +
                      "Priority: 110 (Vence Eleicao)\n" +
                      "Preempt: Habilitado");

    putNote(640, 190, "[R2-BACKUP - STANDBY]\n" +
                      "IP Fisico: 192.168.10.3/24\n" +
                      "HSRP Grupo 1: 192.168.10.1\n" +
                      "Priority: 90 (Fica em Espera)\n" +
                      "Preempt: Habilitado");

    putNote(170, 520, "[PC1]\nIP: 192.168.10.10/24\nMask: 255.255.255.0\nGateway: 192.168.10.1");
    putNote(370, 520, "[PC2]\nIP: 192.168.10.20/24\nMask: 255.255.255.0\nGateway: 192.168.10.1");
    putNote(570, 520, "[SERVER1]\nIP: 192.168.10.100/24\nMask: 255.255.255.0\nGateway: 192.168.10.1");

} catch (err) {
    // Tratamento de erro seguro para não travar o Script Engine
}