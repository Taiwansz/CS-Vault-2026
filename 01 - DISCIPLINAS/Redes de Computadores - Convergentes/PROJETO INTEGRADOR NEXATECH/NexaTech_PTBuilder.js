// Projeto Integrador de Redes de Computadores - NexaTech
// Gerado automaticamente por gerar_script_ptbuilder.py

// 1. Insercao de dispositivos (20 equipamentos)
addDevice("RC1", "2911", 300, 250);
addDevice("RC2", "2911", 500, 400);
addDevice("RC3", "2911", 700, 250);
addDevice("RA", "2911", 200, 420);
addDevice("RB", "2911", 380, 530);
addDevice("RF", "2911", 760, 530);
addDevice("ISP", "2911", 900, 130);
addDevice("SW-CORE", "2960-24TT", 500, 250);
addDevice("SW-MATRIZ", "2960-24TT", 300, 650);
addDevice("SW-FILIAL", "2960-24TT", 760, 660);
addDevice("SW-DC", "2960-24TT", 900, 350);
addDevice("PC1", "PC-PT", 120, 800);
addDevice("PC2", "PC-PT", 220, 800);
addDevice("PC3", "PC-PT", 320, 800);
addDevice("PC4", "PC-PT", 700, 800);
addDevice("PC5", "PC-PT", 830, 800);
addDevice("DHCP-LEGITIMO", "Server-PT", 430, 800);
addDevice("DHCP-NAO-AUTORIZADO", "Server-PT", 540, 760);
addDevice("SRV-CORPORATIVO", "Server-PT", 1080, 350);
addDevice("SRV-EXTERNO", "Server-PT", 1080, 130);

// 2. Cabeamento logico (20 enlaces straight-through)
addLink("SW-CORE", "FastEthernet0/1", "RC1", "GigabitEthernet0/0", "straight");
addLink("SW-CORE", "FastEthernet0/2", "RC2", "GigabitEthernet0/0", "straight");
addLink("SW-CORE", "FastEthernet0/3", "RC3", "GigabitEthernet0/0", "straight");
addLink("RC1", "GigabitEthernet0/1", "RA", "GigabitEthernet0/1", "straight");
addLink("RC2", "GigabitEthernet0/1", "RB", "GigabitEthernet0/1", "straight");
addLink("RC2", "GigabitEthernet0/2", "RF", "GigabitEthernet0/1", "straight");
addLink("RC3", "GigabitEthernet0/2", "ISP", "GigabitEthernet0/0", "straight");
addLink("SW-MATRIZ", "FastEthernet0/1", "PC1", "FastEthernet0", "straight");
addLink("SW-MATRIZ", "FastEthernet0/2", "PC2", "FastEthernet0", "straight");
addLink("SW-MATRIZ", "FastEthernet0/3", "PC3", "FastEthernet0", "straight");
addLink("SW-MATRIZ", "FastEthernet0/10", "DHCP-NAO-AUTORIZADO", "FastEthernet0", "straight");
addLink("SW-MATRIZ", "FastEthernet0/24", "DHCP-LEGITIMO", "FastEthernet0", "straight");
addLink("SW-MATRIZ", "GigabitEthernet0/1", "RA", "GigabitEthernet0/0", "straight");
addLink("SW-MATRIZ", "GigabitEthernet0/2", "RB", "GigabitEthernet0/0", "straight");
addLink("SW-FILIAL", "FastEthernet0/1", "PC4", "FastEthernet0", "straight");
addLink("SW-FILIAL", "FastEthernet0/2", "PC5", "FastEthernet0", "straight");
addLink("SW-FILIAL", "GigabitEthernet0/1", "RF", "GigabitEthernet0/0", "straight");
addLink("SW-DC", "FastEthernet0/1", "SRV-CORPORATIVO", "FastEthernet0", "straight");
addLink("SW-DC", "GigabitEthernet0/1", "RC3", "GigabitEthernet0/1", "straight");
addLink("ISP", "GigabitEthernet0/1", "SRV-EXTERNO", "FastEthernet0", "straight");

// 3. Configuracao IOS (switches primeiro, roteadores em seguida)
configureIosDevice("SW-CORE", `
hostname SW-CORE
no ip domain-lookup
banner motd #NexaTech | SW-CORE | Acesso restrito#
line con 0
logging synchronous
exit
vlan 999
name BLACKHOLE
exit
interface FastEthernet0/1
description Backbone RC1 G0/0
switchport mode access
spanning-tree portfast
no shutdown
exit
interface FastEthernet0/2
description Backbone RC2 G0/0
switchport mode access
spanning-tree portfast
no shutdown
exit
interface FastEthernet0/3
description Backbone RC3 G0/0
switchport mode access
spanning-tree portfast
no shutdown
exit
interface range FastEthernet0/4 - 24, GigabitEthernet0/1 - 2
description NAO-UTILIZADA
switchport mode access
switchport access vlan 999
shutdown
exit
`);

configureIosDevice("SW-MATRIZ", `
hostname SW-MATRIZ
no ip domain-lookup
banner motd #NexaTech | SW-MATRIZ | Acesso restrito#
line con 0
logging synchronous
exit
vlan 10
name MATRIZ
exit
vlan 999
name BLACKHOLE
exit
ip dhcp snooping
ip dhcp snooping vlan 10
no ip dhcp snooping information option
interface range FastEthernet0/1 - 3
description Estacoes Autorizadas Matriz (PC1-PC3)
switchport mode access
switchport access vlan 10
switchport port-security
switchport port-security maximum 1
switchport port-security mac-address sticky
switchport port-security violation shutdown
storm-control broadcast level 20.00
ip dhcp snooping limit rate 15
spanning-tree portfast
spanning-tree bpduguard enable
no shutdown
exit
interface FastEthernet0/10
description DHCP-NAO-AUTORIZADO (UNTRUSTED)
switchport mode access
switchport access vlan 10
ip dhcp snooping limit rate 15
spanning-tree portfast
no shutdown
exit
interface FastEthernet0/24
description DHCP-LEGITIMO (TRUSTED)
switchport mode access
switchport access vlan 10
ip dhcp snooping trust
spanning-tree portfast
no shutdown
exit
interface GigabitEthernet0/1
description Uplink RA G0/0
switchport mode access
switchport access vlan 10
no shutdown
exit
interface GigabitEthernet0/2
description Uplink RB G0/0
switchport mode access
switchport access vlan 10
no shutdown
exit
interface range FastEthernet0/4 - 9, FastEthernet0/11 - 23
description NAO-UTILIZADA
switchport mode access
switchport access vlan 999
shutdown
exit
`);

configureIosDevice("SW-FILIAL", `
hostname SW-FILIAL
no ip domain-lookup
banner motd #NexaTech | SW-FILIAL | Acesso restrito#
line con 0
logging synchronous
exit
vlan 999
name BLACKHOLE
exit
interface range FastEthernet0/1 - 2
description Estacoes Autorizadas Filial (PC4-PC5)
switchport mode access
switchport port-security
switchport port-security maximum 1
switchport port-security mac-address sticky
switchport port-security violation shutdown
storm-control broadcast level 20.00
spanning-tree portfast
spanning-tree bpduguard enable
no shutdown
exit
interface GigabitEthernet0/1
description Uplink RF G0/0
switchport mode access
no shutdown
exit
interface range FastEthernet0/3 - 24, GigabitEthernet0/2
description NAO-UTILIZADA
switchport mode access
switchport access vlan 999
shutdown
exit
`);

configureIosDevice("SW-DC", `
hostname SW-DC
no ip domain-lookup
banner motd #NexaTech | SW-DC | Acesso restrito#
line con 0
logging synchronous
exit
vlan 999
name BLACKHOLE
exit
interface FastEthernet0/1
description SRV-CORPORATIVO Fa0
switchport mode access
spanning-tree portfast
no shutdown
exit
interface GigabitEthernet0/1
description Uplink RC3 G0/1
switchport mode access
no shutdown
exit
interface range FastEthernet0/2 - 24, GigabitEthernet0/2
description NAO-UTILIZADA
switchport mode access
switchport access vlan 999
shutdown
exit
`);

configureIosDevice("RC1", `
hostname RC1
no ip domain-lookup
banner motd #NexaTech | RC1 | Acesso restrito#
line con 0
logging synchronous
exit
interface Loopback0
description Router-ID OSPF RC1
ip address 1.1.1.1 255.255.255.255
exit
interface GigabitEthernet0/0
description Backbone OSPF - SW-CORE Fa0/1
ip address 10.0.0.1 255.255.255.248
ip ospf priority 200
ip ospf cost 10
no shutdown
exit
interface GigabitEthernet0/1
description WAN A - RA G0/1
ip address 10.0.1.1 255.255.255.252
ip ospf cost 10
no shutdown
exit
interface GigabitEthernet0/2
description NAO-UTILIZADA
shutdown
exit
router ospf 1
router-id 1.1.1.1
auto-cost reference-bandwidth 1000
log-adjacency-changes
network 1.1.1.1 0.0.0.0 area 0
network 10.0.0.0 0.0.0.7 area 0
network 10.0.1.0 0.0.0.3 area 0
exit
`);

configureIosDevice("RC2", `
hostname RC2
no ip domain-lookup
banner motd #NexaTech | RC2 | Acesso restrito#
line con 0
logging synchronous
exit
interface Loopback0
description Router-ID OSPF RC2
ip address 2.2.2.2 255.255.255.255
exit
interface GigabitEthernet0/0
description Backbone OSPF - SW-CORE Fa0/2
ip address 10.0.0.2 255.255.255.248
ip ospf priority 100
ip ospf cost 10
no shutdown
exit
interface GigabitEthernet0/1
description WAN B - RB G0/1
ip address 10.0.1.5 255.255.255.252
ip ospf cost 50
no shutdown
exit
interface GigabitEthernet0/2
description WAN Filial - RF G0/1 (Area 10 Stub)
ip address 10.0.1.9 255.255.255.252
ip ospf cost 10
no shutdown
exit
router ospf 1
router-id 2.2.2.2
auto-cost reference-bandwidth 1000
log-adjacency-changes
area 10 stub
network 2.2.2.2 0.0.0.0 area 0
network 10.0.0.0 0.0.0.7 area 0
network 10.0.1.4 0.0.0.3 area 0
network 10.0.1.8 0.0.0.3 area 10
exit
`);

configureIosDevice("RC3", `
hostname RC3
no ip domain-lookup
banner motd #NexaTech | RC3 | Acesso restrito#
line con 0
logging synchronous
exit
interface Loopback0
description Router-ID OSPF RC3
ip address 3.3.3.3 255.255.255.255
exit
interface GigabitEthernet0/0
description Backbone OSPF - SW-CORE Fa0/3
ip address 10.0.0.3 255.255.255.248
ip ospf priority 0
ip ospf cost 10
no shutdown
exit
interface GigabitEthernet0/1
description LAN Data Center - SW-DC Gi0/1
ip address 192.168.30.1 255.255.255.0
ip ospf cost 1
no shutdown
exit
interface GigabitEthernet0/2
description Link Externo - ISP G0/0
ip address 203.0.113.1 255.255.255.252
no shutdown
exit
ip route 0.0.0.0 0.0.0.0 203.0.113.2
ip route 198.51.100.0 255.255.255.0 203.0.113.2
router ospf 1
router-id 3.3.3.3
auto-cost reference-bandwidth 1000
log-adjacency-changes
passive-interface GigabitEthernet0/1
network 3.3.3.3 0.0.0.0 area 0
network 10.0.0.0 0.0.0.7 area 0
network 192.168.30.0 0.0.0.255 area 0
default-information originate
redistribute static subnets
exit
`);

configureIosDevice("RA", `
hostname RA
no ip domain-lookup
banner motd #NexaTech | RA | Acesso restrito#
line con 0
logging synchronous
exit
interface Loopback0
description Router-ID OSPF RA
ip address 4.4.4.4 255.255.255.255
exit
interface GigabitEthernet0/1
description WAN A - RC1 G0/1
ip address 10.0.1.2 255.255.255.252
ip ospf cost 10
no shutdown
exit
track 1 interface GigabitEthernet0/1 line-protocol
interface GigabitEthernet0/0
description LAN Matriz (HSRP Active) - SW-MATRIZ Gi0/1
ip address 192.168.10.2 255.255.255.0
ip ospf cost 1
standby 10 ip 192.168.10.1
standby 10 priority 120
standby 10 preempt
standby 10 track 1 decrement 40
standby 10 track GigabitEthernet0/1
no shutdown
exit
interface GigabitEthernet0/2
description NAO-UTILIZADA
shutdown
exit
router ospf 1
router-id 4.4.4.4
auto-cost reference-bandwidth 1000
log-adjacency-changes
network 4.4.4.4 0.0.0.0 area 0
network 10.0.1.0 0.0.0.3 area 0
network 192.168.10.0 0.0.0.255 area 0
exit
`);

configureIosDevice("RB", `
hostname RB
no ip domain-lookup
banner motd #NexaTech | RB | Acesso restrito#
line con 0
logging synchronous
exit
interface Loopback0
description Router-ID OSPF RB
ip address 5.5.5.5 255.255.255.255
exit
interface GigabitEthernet0/1
description WAN B - RC2 G0/1
ip address 10.0.1.6 255.255.255.252
ip ospf cost 50
no shutdown
exit
interface GigabitEthernet0/0
description LAN Matriz (HSRP Standby) - SW-MATRIZ Gi0/2
ip address 192.168.10.3 255.255.255.0
ip ospf cost 1
standby 10 ip 192.168.10.1
standby 10 priority 100
standby 10 preempt
no shutdown
exit
interface GigabitEthernet0/2
description NAO-UTILIZADA
shutdown
exit
router ospf 1
router-id 5.5.5.5
auto-cost reference-bandwidth 1000
log-adjacency-changes
network 5.5.5.5 0.0.0.0 area 0
network 10.0.1.4 0.0.0.3 area 0
network 192.168.10.0 0.0.0.255 area 0
exit
`);

configureIosDevice("RF", `
hostname RF
no ip domain-lookup
banner motd #NexaTech | RF | Acesso restrito#
line con 0
logging synchronous
exit
interface Loopback0
description Router-ID OSPF RF
ip address 6.6.6.6 255.255.255.255
exit
interface GigabitEthernet0/1
description WAN Filial - RC2 G0/2 (Area 10 Stub)
ip address 10.0.1.10 255.255.255.252
ip ospf cost 10
no shutdown
exit
interface GigabitEthernet0/0
description LAN Filial - SW-FILIAL Gi0/1
ip address 192.168.20.1 255.255.255.0
ip ospf cost 1
no shutdown
exit
interface GigabitEthernet0/2
description NAO-UTILIZADA
shutdown
exit
router ospf 1
router-id 6.6.6.6
auto-cost reference-bandwidth 1000
log-adjacency-changes
area 10 stub
passive-interface GigabitEthernet0/0
network 6.6.6.6 0.0.0.0 area 10
network 10.0.1.8 0.0.0.3 area 10
network 192.168.20.0 0.0.0.255 area 10
exit
`);

configureIosDevice("ISP", `
hostname ISP
no ip domain-lookup
banner motd #NexaTech | ISP | Acesso restrito#
line con 0
logging synchronous
exit
interface GigabitEthernet0/0
description Link Externo - RC3 G0/2
ip address 203.0.113.2 255.255.255.252
no shutdown
exit
interface GigabitEthernet0/1
description Rede Externa Simulada - SRV-EXTERNO Fa0
ip address 198.51.100.1 255.255.255.0
no shutdown
exit
interface GigabitEthernet0/2
description NAO-UTILIZADA
shutdown
exit
ip route 10.0.0.0 255.0.0.0 203.0.113.1
ip route 192.168.0.0 255.255.0.0 203.0.113.1
`);

// 4. Enderecamento IPv4 de PCs e Servidores
configurePcIp("PC1", true);
configurePcIp("PC2", true);
configurePcIp("PC3", true);
configurePcIp("PC4", false, "192.168.20.10", "255.255.255.0", "192.168.20.1", "192.168.30.10");
configurePcIp("PC5", false, "192.168.20.11", "255.255.255.0", "192.168.20.1", "192.168.30.10");
configurePcIp("DHCP-LEGITIMO", false, "192.168.10.10", "255.255.255.0", "192.168.10.1", "192.168.30.10");
configurePcIp("DHCP-NAO-AUTORIZADO", false, "192.168.10.200", "255.255.255.0", "192.168.10.1");
configurePcIp("SRV-CORPORATIVO", false, "192.168.30.10", "255.255.255.0", "192.168.30.1");
configurePcIp("SRV-EXTERNO", false, "198.51.100.10", "255.255.255.0", "198.51.100.1");
