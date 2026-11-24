// ATIVIDADE 1 - RSTP / RAPID-PVST
// Executar em um arquivo NOVO e vazio pelo Extensions > Builder Code Editor.

if (ipc.network().getDeviceCount() !== 0) {
    throw new Error("Abra um arquivo novo e vazio antes de executar. A atividade deve ser independente.");
}

// Dispositivos
addDevice("SW1", "2960-24TT", 300, 160);
addDevice("SW2", "2960-24TT", 700, 160);
addDevice("SW3", "2960-24TT", 500, 360);
addDevice("PC1", "PC-PT", 350, 540);
addDevice("PC2", "PC-PT", 500, 540);
addDevice("PC3", "PC-PT", 650, 540);

// Triangulo redundante: somente interfaces GigabitEthernet entre switches.
addLink("SW1", "GigabitEthernet0/1", "SW2", "GigabitEthernet0/1", "cross");
addLink("SW1", "GigabitEthernet0/2", "SW3", "GigabitEthernet0/1", "cross");
addLink("SW2", "GigabitEthernet0/2", "SW3", "GigabitEthernet0/2", "cross");

// Acesso no SW3.
addLink("SW3", "FastEthernet0/1", "PC1", "FastEthernet0", "straight");
addLink("SW3", "FastEthernet0/2", "PC2", "FastEthernet0", "straight");
addLink("SW3", "FastEthernet0/3", "PC3", "FastEthernet0", "straight");

configureIosDevice("SW1",
    "hostname SW1\n" +
    "vlan 10\nname Usuarios\n" +
    "vlan 20\nname Pesquisa\n" +
    "vlan 30\nname Administrativo\n" +
    "vlan 99\nname Gerenciamento\n" +
    "exit\n" +
    "spanning-tree mode rapid-pvst\n" +
    "spanning-tree vlan 10,20,30,99 root primary\n" +
    "interface GigabitEthernet0/1\n" +
    "switchport mode trunk\n" +
    "switchport trunk allowed vlan 10,20,30,99\n" +
    "no shutdown\nexit\n" +
    "interface GigabitEthernet0/2\n" +
    "switchport mode trunk\n" +
    "switchport trunk allowed vlan 10,20,30,99\n" +
    "no shutdown\nend"
);

configureIosDevice("SW2",
    "hostname SW2\n" +
    "vlan 10\nname Usuarios\n" +
    "vlan 20\nname Pesquisa\n" +
    "vlan 30\nname Administrativo\n" +
    "vlan 99\nname Gerenciamento\n" +
    "exit\n" +
    "spanning-tree mode rapid-pvst\n" +
    "interface GigabitEthernet0/1\n" +
    "switchport mode trunk\n" +
    "switchport trunk allowed vlan 10,20,30,99\n" +
    "no shutdown\nexit\n" +
    "interface GigabitEthernet0/2\n" +
    "switchport mode trunk\n" +
    "switchport trunk allowed vlan 10,20,30,99\n" +
    "no shutdown\nend"
);

configureIosDevice("SW3",
    "hostname SW3\n" +
    "vlan 10\nname Usuarios\n" +
    "vlan 20\nname Pesquisa\n" +
    "vlan 30\nname Administrativo\n" +
    "vlan 99\nname Gerenciamento\n" +
    "exit\n" +
    "spanning-tree mode rapid-pvst\n" +
    "interface GigabitEthernet0/1\n" +
    "switchport mode trunk\n" +
    "switchport trunk allowed vlan 10,20,30,99\n" +
    "no shutdown\nexit\n" +
    "interface GigabitEthernet0/2\n" +
    "switchport mode trunk\n" +
    "switchport trunk allowed vlan 10,20,30,99\n" +
    "no shutdown\nexit\n" +
    "interface FastEthernet0/1\n" +
    "switchport mode access\n" +
    "switchport access vlan 10\n" +
    "spanning-tree portfast\n" +
    "spanning-tree bpduguard enable\n" +
    "no shutdown\nexit\n" +
    "interface FastEthernet0/2\n" +
    "switchport mode access\n" +
    "switchport access vlan 20\n" +
    "spanning-tree portfast\n" +
    "spanning-tree bpduguard enable\n" +
    "no shutdown\nexit\n" +
    "interface FastEthernet0/3\n" +
    "switchport mode access\n" +
    "switchport access vlan 30\n" +
    "spanning-tree portfast\n" +
    "spanning-tree bpduguard enable\n" +
    "no shutdown\nend"
);

// Enderecamento estatico dos PCs.
configurePcIp("PC1", false, "192.168.10.10", "255.255.255.0", "192.168.10.1");
configurePcIp("PC2", false, "192.168.20.10", "255.255.255.0", "192.168.20.1");
configurePcIp("PC3", false, "192.168.30.10", "255.255.255.0", "192.168.30.1");

// Evidencias: os comandos e suas saidas permanecem no historico da CLI.
var sw1 = ipc.network().getDevice("SW1");
sw1.enterCommand("show spanning-tree vlan 10", "enable");
sw1.enterCommand("show spanning-tree vlan 20", "enable");
sw1.enterCommand("show spanning-tree vlan 30", "enable");
sw1.enterCommand("show spanning-tree vlan 99", "enable");
sw1.enterCommand("show interfaces trunk", "enable");
sw1.enterCommand("show spanning-tree inconsistentports", "enable");

var sw3 = ipc.network().getDevice("SW3");
sw3.enterCommand("show interfaces trunk", "enable");
sw3.enterCommand("show spanning-tree inconsistentports", "enable");
sw3.enterCommand("show spanning-tree interface FastEthernet0/1 detail", "enable");
sw3.enterCommand("show spanning-tree interface FastEthernet0/2 detail", "enable");
sw3.enterCommand("show spanning-tree interface FastEthernet0/3 detail", "enable");

// Documentacao visual no proprio canvas.
var lw = ipc.appWindow().getActiveWorkspace().getLogicalWorkspace();
var z1 = (typeof lw.getIncNoteZOrder === "function") ? lw.getIncNoteZOrder() : 0;
lw.addNote(820, 150, z1,
    "ATIVIDADE 1 - RSTP / RAPID-PVST\n" +
    "SW1: Root Bridge primario - VLANs 10,20,30,99\n" +
    "Trunks: somente VLANs 10,20,30,99\n" +
    "Enlaces redundantes em triangulo; RSTP elimina o loop.");

var z2 = (typeof lw.getIncNoteZOrder === "function") ? lw.getIncNoteZOrder() : 1;
lw.addNote(820, 310, z2,
    "PC1 - VLAN 10 - 192.168.10.10/24 - GW 192.168.10.1\n" +
    "PC2 - VLAN 20 - 192.168.20.10/24 - GW 192.168.20.1\n" +
    "PC3 - VLAN 30 - 192.168.30.10/24 - GW 192.168.30.1\n" +
    "SW3 Fa0/1-3: PortFast e BPDU Guard.");

var z3 = (typeof lw.getIncNoteZOrder === "function") ? lw.getIncNoteZOrder() : 2;
lw.addNote(820, 470, z3,
    "Validacao:\n" +
    "show spanning-tree vlan 10, 20, 30 e 99\n" +
    "show interfaces trunk\n" +
    "show spanning-tree inconsistentports\n" +
    "Sem roteador/L3: nao ha comunicacao entre VLANs.");

// Salvamento automatico do arquivo .pkt independente.
ipc.appWindow().fileSaveAsNoPrompt("C:/Users/52319400/Atividade_1_RSTP.pkt", false);
