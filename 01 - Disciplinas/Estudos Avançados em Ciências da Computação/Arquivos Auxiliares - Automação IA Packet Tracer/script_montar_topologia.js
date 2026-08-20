// ==============================================================================
// SCRIPT DE AUTOMAÇÃO - CISCO PACKET TRACER (SCRIPT ENGINE)
// Topologia: 1 Roteador (2911), 2 Switches (2960), 4 PCs (Administração & Vendas)
// ==============================================================================

try {
    var net = ipc.network();
    var lw = ipc.appWindow().getActiveWorkspace().getLogicalWorkspace();

    // --------------------------------------------------------------------------
    // 1. CRIAR DISPOSITIVOS NO CANVAS LÓGICO
    // NOTA: lw.addDevice() retorna o nome gerado (String, ex: "Router0").
    // Devemos obter o objeto com net.getDevice(nome) antes de chamar .setName()!
    // --------------------------------------------------------------------------
    
    // Roteador Central (2911)
    var r1Name = lw.addDevice("router", "2911", 450, 100);
    var r1 = net.getDevice(r1Name);
    if (r1) { r1.setName("R1"); }

    // Switches de Acesso (2960-24TT)
    var swAdmName = lw.addDevice("switch", "2960-24TT", 250, 250);
    var swAdm = net.getDevice(swAdmName);
    if (swAdm) { swAdm.setName("SW-ADM"); }

    var swVenName = lw.addDevice("switch", "2960-24TT", 650, 250);
    var swVen = net.getDevice(swVenName);
    if (swVen) { swVen.setName("SW-VENDAS"); }

    // Hosts - Setor Administração (192.168.10.0/24)
    var pcAdm1Name = lw.addDevice("pc", "PC-PT", 150, 420);
    var pcAdm1 = net.getDevice(pcAdm1Name);
    if (pcAdm1) { pcAdm1.setName("PC-ADM-01"); }

    var pcAdm2Name = lw.addDevice("pc", "PC-PT", 350, 420);
    var pcAdm2 = net.getDevice(pcAdm2Name);
    if (pcAdm2) { pcAdm2.setName("PC-ADM-02"); }

    // Hosts - Setor Vendas (192.168.20.0/24)
    var pcVen1Name = lw.addDevice("pc", "PC-PT", 550, 420);
    var pcVen1 = net.getDevice(pcVen1Name);
    if (pcVen1) { pcVen1.setName("PC-VENDAS-01"); }

    var pcVen2Name = lw.addDevice("pc", "PC-PT", 750, 420);
    var pcVen2 = net.getDevice(pcVen2Name);
    if (pcVen2) { pcVen2.setName("PC-VENDAS-02"); }

    // --------------------------------------------------------------------------
    // 2. CONEXÃO DE CABOS (8100 = Cabo Direto / Copper Straight-Through)
    // --------------------------------------------------------------------------
    // R1 para Switches
    lw.createLink("R1", "GigabitEthernet0/0", "SW-ADM", "GigabitEthernet0/1", 8100);
    lw.createLink("R1", "GigabitEthernet0/1", "SW-VENDAS", "GigabitEthernet0/1", 8100);

    // SW-ADM para PCs de Administração
    lw.createLink("SW-ADM", "FastEthernet0/1", "PC-ADM-01", "FastEthernet0", 8100);
    lw.createLink("SW-ADM", "FastEthernet0/2", "PC-ADM-02", "FastEthernet0", 8100);

    // SW-VENDAS para PCs de Vendas
    lw.createLink("SW-VENDAS", "FastEthernet0/1", "PC-VENDAS-01", "FastEthernet0", 8100);
    lw.createLink("SW-VENDAS", "FastEthernet0/2", "PC-VENDAS-02", "FastEthernet0", 8100);

    // --------------------------------------------------------------------------
    // 3. CONFIGURAÇÃO IP ESTÁTICA DOS COMPUTADORES
    // --------------------------------------------------------------------------
    if (pcAdm1) {
        var p = pcAdm1.getPort("FastEthernet0");
        if (p) p.setIpSubnetMask("192.168.10.10", "255.255.255.0");
        pcAdm1.setDefaultGateway("192.168.10.1");
    }

    if (pcAdm2) {
        var p = pcAdm2.getPort("FastEthernet0");
        if (p) p.setIpSubnetMask("192.168.10.20", "255.255.255.0");
        pcAdm2.setDefaultGateway("192.168.10.1");
    }

    if (pcVen1) {
        var p = pcVen1.getPort("FastEthernet0");
        if (p) p.setIpSubnetMask("192.168.20.10", "255.255.255.0");
        pcVen1.setDefaultGateway("192.168.20.1");
    }

    if (pcVen2) {
        var p = pcVen2.getPort("FastEthernet0");
        if (p) p.setIpSubnetMask("192.168.20.20", "255.255.255.0");
        pcVen2.setDefaultGateway("192.168.20.1");
    }

    // --------------------------------------------------------------------------
    // 4. CONFIGURAÇÃO IOS DO ROTEADOR R1 VIA CLI
    // --------------------------------------------------------------------------
    if (r1) {
        r1.enterCommand("enable");
        r1.enterCommand("configure terminal");
        r1.enterCommand("hostname R1");

        // Sub-rede Administração (192.168.10.0/24)
        r1.enterCommand("interface GigabitEthernet0/0");
        r1.enterCommand("description Gateway - Rede Administracao (192.168.10.0/24)");
        r1.enterCommand("ip address 192.168.10.1 255.255.255.0");
        r1.enterCommand("no shutdown");
        r1.enterCommand("exit");

        // Sub-rede Vendas (192.168.20.0/24)
        r1.enterCommand("interface GigabitEthernet0/1");
        r1.enterCommand("description Gateway - Rede Vendas (192.168.20.0/24)");
        r1.enterCommand("ip address 192.168.20.1 255.255.255.0");
        r1.enterCommand("no shutdown");
        r1.enterCommand("end");

        // Salvar na memória NVRAM
        r1.enterCommand("write memory");
    }

    if (typeof alert !== "undefined") {
        alert("Topologia criada e configurada com sucesso!");
    }

} catch (e) {
    if (typeof alert !== "undefined") {
        alert("Erro na execução do script:\n" + e + "\nLinha: " + (e.lineNumber || "N/A"));
    }
}
