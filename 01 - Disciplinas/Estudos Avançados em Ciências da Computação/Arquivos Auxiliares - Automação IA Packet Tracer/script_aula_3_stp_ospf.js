// ==============================================================================
// SCRIPT DE AUTOMAÇÃO - CISCO PACKET TRACER (SCRIPT ENGINE / MCP)
// ATIVIDADE PRÁTICA: STP/RSTP + OSPF (AULA 3) COM ANOTAÇÕES COMPLETAS
// ==============================================================================

try {
    var net = ipc.network();
    var lw = ipc.appWindow().getActiveWorkspace().getLogicalWorkspace();

    // --------------------------------------------------------------------------
    // 0. LIMPEZA PREVENTIVA
    // --------------------------------------------------------------------------
    var devicesToRemove = [
        'SW1', 'SW2', 'SW3', 'SW4',
        'R1', 'R2',
        'PC1', 'PC2', 'PC3', 'PC4', 'PC5',
        'PC6', 'PC7', 'PC8', 'PC9', 'PC10'
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

    // Helper para adicionar blocos de texto (anotações)
    function putNote(x, y, text) {
        var z = (typeof lw.getIncNoteZOrder === 'function') ? lw.getIncNoteZOrder() : 0;
        lw.addNote(x, y, z, text);
    }

    // --------------------------------------------------------------------------
    // 1. CRIAR DISPOSITIVOS NO CANVAS
    // (type: 0 = Router, 1 = Switch, 8 = PC)
    // --------------------------------------------------------------------------
    // Switches da Rede A (192.168.10.0/24)
    var sw1 = addDev(1, '2960-24TT', 'SW1', 200, 180);
    var sw2 = addDev(1, '2960-24TT', 'SW2', 550, 180);

    // Switches da Rede B (192.168.20.0/24)
    var sw3 = addDev(1, '2960-24TT', 'SW3', 200, 550);
    var sw4 = addDev(1, '2960-24TT', 'SW4', 550, 550);

    // Roteadores Central (WAN 10.0.0.0/30)
    var r1 = addDev(0, '2911', 'R1', 375, 300);
    var r2 = addDev(0, '2911', 'R2', 375, 430);

    // PCs - Setor Administrativo (Rede A)
    var pc1 = addDev(8, 'PC-PT', 'PC1', 100, 60);
    var pc2 = addDev(8, 'PC-PT', 'PC2', 200, 60);
    var pc3 = addDev(8, 'PC-PT', 'PC3', 300, 60);
    var pc4 = addDev(8, 'PC-PT', 'PC4', 480, 60);
    var pc5 = addDev(8, 'PC-PT', 'PC5', 580, 60);

    // PCs - Setor Operacional (Rede B)
    var pc6  = addDev(8, 'PC-PT', 'PC6', 100, 680);
    var pc7  = addDev(8, 'PC-PT', 'PC7', 200, 680);
    var pc8  = addDev(8, 'PC-PT', 'PC8', 300, 680);
    var pc9  = addDev(8, 'PC-PT', 'PC9', 480, 680);
    var pc10 = addDev(8, 'PC-PT', 'PC10', 580, 680);

    // --------------------------------------------------------------------------
    // 2. CONEXÕES DE CABOS (8100 = Straight, 8101 = Cross)
    // --------------------------------------------------------------------------
    // Links Redundantes entre Switches (Rede A: SW1 <-> SW2)
    lw.createLink('SW1', 'GigabitEthernet0/1', 'SW2', 'GigabitEthernet0/1', 8101);
    lw.createLink('SW1', 'GigabitEthernet0/2', 'SW2', 'GigabitEthernet0/2', 8101);

    // Links Redundantes entre Switches (Rede B: SW3 <-> SW4)
    lw.createLink('SW3', 'GigabitEthernet0/1', 'SW4', 'GigabitEthernet0/1', 8101);
    lw.createLink('SW3', 'GigabitEthernet0/2', 'SW4', 'GigabitEthernet0/2', 8101);

    // Links LAN Switches para Roteadores
    lw.createLink('SW1', 'FastEthernet0/24', 'R1', 'GigabitEthernet0/0', 8100);
    lw.createLink('SW3', 'FastEthernet0/24', 'R2', 'GigabitEthernet0/0', 8100);

    // Link WAN Roteador R1 <-> R2
    lw.createLink('R1', 'GigabitEthernet0/1', 'R2', 'GigabitEthernet0/1', 8101);

    // PCs da Rede A conectados aos Switches
    lw.createLink('SW1', 'FastEthernet0/1', 'PC1', 'FastEthernet0', 8100);
    lw.createLink('SW1', 'FastEthernet0/2', 'PC2', 'FastEthernet0', 8100);
    lw.createLink('SW1', 'FastEthernet0/3', 'PC3', 'FastEthernet0', 8100);
    lw.createLink('SW2', 'FastEthernet0/1', 'PC4', 'FastEthernet0', 8100);
    lw.createLink('SW2', 'FastEthernet0/2', 'PC5', 'FastEthernet0', 8100);

    // PCs da Rede B conectados aos Switches
    lw.createLink('SW3', 'FastEthernet0/1', 'PC6', 'FastEthernet0', 8100);
    lw.createLink('SW3', 'FastEthernet0/2', 'PC7', 'FastEthernet0', 8100);
    lw.createLink('SW3', 'FastEthernet0/3', 'PC8', 'FastEthernet0', 8100);
    lw.createLink('SW4', 'FastEthernet0/1', 'PC9', 'FastEthernet0', 8100);
    lw.createLink('SW4', 'FastEthernet0/2', 'PC10', 'FastEthernet0', 8100);

    // --------------------------------------------------------------------------
    // 3. CONFIGURAÇÃO IP ESTÁTICA DOS COMPUTADORES
    // --------------------------------------------------------------------------
    function configureHost(dev, ip, mask, gw) {
        if (!dev) return;
        var p = dev.getPort('FastEthernet0');
        if (p) { p.setIpSubnetMask(ip, mask); }
        dev.setDefaultGateway(gw);
    }

    // Rede A (Gateway: 192.168.10.1)
    configureHost(pc1, '192.168.10.11', '255.255.255.0', '192.168.10.1');
    configureHost(pc2, '192.168.10.12', '255.255.255.0', '192.168.10.1');
    configureHost(pc3, '192.168.10.13', '255.255.255.0', '192.168.10.1');
    configureHost(pc4, '192.168.10.14', '255.255.255.0', '192.168.10.1');
    configureHost(pc5, '192.168.10.15', '255.255.255.0', '192.168.10.1');

    // Rede B (Gateway: 192.168.20.1)
    configureHost(pc6,  '192.168.20.11', '255.255.255.0', '192.168.20.1');
    configureHost(pc7,  '192.168.20.12', '255.255.255.0', '192.168.20.1');
    configureHost(pc8,  '192.168.20.13', '255.255.255.0', '192.168.20.1');
    configureHost(pc9,  '192.168.20.14', '255.255.255.0', '192.168.20.1');
    configureHost(pc10, '192.168.20.15', '255.255.255.0', '192.168.20.1');

    // --------------------------------------------------------------------------
    // 4. CONFIGURAÇÃO IOS DOS SWITCHES (RSTP, ROOT BRIDGES, PORTFAST, BPDUGUARD)
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

    // SW1 (Root Primary da Rede A - Priority 4096)
    sendCli(sw1, [
        'enable',
        'configure terminal',
        'hostname SW1',
        'spanning-tree mode rapid-pvst',
        'spanning-tree vlan 1 priority 4096',
        'interface range FastEthernet0/1 - 3',
        'spanning-tree portfast',
        'spanning-tree bpduguard enable',
        'end',
        'write memory'
    ]);

    // SW2 (Root Secondary da Rede A - Priority 28672 -> porta bloqueada fica no SW2)
    sendCli(sw2, [
        'enable',
        'configure terminal',
        'hostname SW2',
        'spanning-tree mode rapid-pvst',
        'spanning-tree vlan 1 priority 28672',
        'interface range FastEthernet0/1 - 2',
        'spanning-tree portfast',
        'spanning-tree bpduguard enable',
        'end',
        'write memory'
    ]);

    // SW3 (Root Primary da Rede B - Priority 4096)
    sendCli(sw3, [
        'enable',
        'configure terminal',
        'hostname SW3',
        'spanning-tree mode rapid-pvst',
        'spanning-tree vlan 1 priority 4096',
        'interface range FastEthernet0/1 - 3',
        'spanning-tree portfast',
        'spanning-tree bpduguard enable',
        'end',
        'write memory'
    ]);

    // SW4 (Root Secondary da Rede B - Priority 28672 -> porta bloqueada fica no SW4)
    sendCli(sw4, [
        'enable',
        'configure terminal',
        'hostname SW4',
        'spanning-tree mode rapid-pvst',
        'spanning-tree vlan 1 priority 28672',
        'interface range FastEthernet0/1 - 2',
        'spanning-tree portfast',
        'spanning-tree bpduguard enable',
        'end',
        'write memory'
    ]);

    // --------------------------------------------------------------------------
    // 5. CONFIGURAÇÃO IOS DOS ROTEADORES (IPs + OSPF PROCESSO 1 ÁREA 0)
    // --------------------------------------------------------------------------
    // R1 (Rede A e WAN)
    sendCli(r1, [
        'enable',
        'configure terminal',
        'hostname R1',
        'interface GigabitEthernet0/0',
        'description LAN-Rede-A (Administrativo)',
        'ip address 192.168.10.1 255.255.255.0',
        'no shutdown',
        'exit',
        'interface GigabitEthernet0/1',
        'description WAN-to-R2',
        'ip address 10.0.0.1 255.255.255.252',
        'no shutdown',
        'exit',
        'router ospf 1',
        'network 192.168.10.0 0.0.0.255 area 0',
        'network 10.0.0.0 0.0.0.3 area 0',
        'end',
        'write memory'
    ]);

    // R2 (Rede B e WAN)
    sendCli(r2, [
        'enable',
        'configure terminal',
        'hostname R2',
        'interface GigabitEthernet0/0',
        'description LAN-Rede-B (Operacional)',
        'ip address 192.168.20.1 255.255.255.0',
        'no shutdown',
        'exit',
        'interface GigabitEthernet0/1',
        'description WAN-to-R1',
        'ip address 10.0.0.2 255.255.255.252',
        'no shutdown',
        'exit',
        'router ospf 1',
        'network 192.168.20.0 0.0.0.255 area 0',
        'network 10.0.0.0 0.0.0.3 area 0',
        'end',
        'write memory'
    ]);

    // --------------------------------------------------------------------------
    // 6. BLOCOS DE TEXTO / ANOTAÇÕES NO CANVAS
    // --------------------------------------------------------------------------
    // Header Rede A
    putNote(280, 10, 'REDE A - ADMINISTRATIVO (192.168.10.0/24)\nGateway: 192.168.10.1 | STP: Rapid-PVST+');

    // PCs Rede A
    putNote(80, 35, 'PC1: 192.168.10.11');
    putNote(180, 35, 'PC2: 192.168.10.12');
    putNote(280, 35, 'PC3: 192.168.10.13');
    putNote(460, 35, 'PC4: 192.168.10.14');
    putNote(560, 35, 'PC5: 192.168.10.15');

    // Switches Rede A
    putNote(100, 210, 'SW1 (Root Primary)\nPrioridade: 4096');
    putNote(560, 210, 'SW2 (Root Secundário)\nPrioridade: 28672\n[Gi0/2 Bloqueada]');
    putNote(320, 150, 'Links Redundantes\nGi0/1 e Gi0/2');

    // Roteadores & WAN
    putNote(440, 280, 'R1 (2911)\nGi0/0: 192.168.10.1/24\nGi0/1: 10.0.0.1/30\nOSPF 1 Area 0');
    putNote(390, 355, 'WAN: 10.0.0.0/30 (Area 0)\nR1: 10.0.0.1 <-> R2: 10.0.0.2');
    putNote(440, 420, 'R2 (2911)\nGi0/0: 192.168.20.1/24\nGi0/1: 10.0.0.2/30\nOSPF 1 Area 0');

    // Header Rede B
    putNote(280, 625, 'REDE B - OPERACIONAL (192.168.20.0/24)\nGateway: 192.168.20.1 | STP: Rapid-PVST+');

    // Switches Rede B
    putNote(100, 580, 'SW3 (Root Primary)\nPrioridade: 4096');
    putNote(560, 580, 'SW4 (Root Secundário)\nPrioridade: 28672\n[Gi0/2 Bloqueada]');
    putNote(320, 520, 'Links Redundantes\nGi0/1 e Gi0/2');

    // PCs Rede B
    putNote(80, 720, 'PC6: 192.168.20.11');
    putNote(180, 720, 'PC7: 192.168.20.12');
    putNote(280, 720, 'PC8: 192.168.20.13');
    putNote(460, 720, 'PC9: 192.168.20.14');
    putNote(560, 720, 'PC10: 192.168.20.15');

    // Identificação do Grupo
    putNote(680, 40, '==========================================\nINTEGRANTES DO GRUPO:\n• MATHEUS SOUSA DOS SANTOS - RA: 52319400\n• FELIPE GUARNIERI PINETE - RA: 52319337\n• GUILHERME GUSTAVO WEBER - RA: 52420339\n==========================================');

    if (typeof alert !== "undefined") {
        alert("Topologia da Aula 3 completa com anotações e nomes do grupo!");
    }

} catch (e) {
    if (typeof alert !== "undefined") {
        alert("Erro na execução do script:\n" + e);
    }
}
