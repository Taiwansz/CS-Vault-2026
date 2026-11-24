#!/usr/bin/env python3
"""Auditor automatizado do kit NexaTech para Cisco Packet Tracer."""

import re
import sys
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent
CONFIG_DIR = BASE_DIR / "CONFIGURACOES"
JS_PATH = BASE_DIR / "NexaTech_PTBuilder.js"
NOTAS_PATH = BASE_DIR / "NOTAS_PACKET_TRACER.md"

EMOJI_RE = re.compile(r"[\U0001F300-\U0001FAFF\u2600-\u27BF\uFE0F\u200D]")

IOS_DEVICES = [
    "RC1",
    "RC2",
    "RC3",
    "RA",
    "RB",
    "RF",
    "ISP",
    "SW-CORE",
    "SW-MATRIZ",
    "SW-FILIAL",
    "SW-DC",
]

END_DEVICES = [
    "PC1",
    "PC2",
    "PC3",
    "PC4",
    "PC5",
    "DHCP-LEGITIMO",
    "DHCP-NAO-AUTORIZADO",
    "SRV-CORPORATIVO",
    "SRV-EXTERNO",
]

VALID_ROUTER_PORTS = {"GigabitEthernet0/0", "GigabitEthernet0/1", "GigabitEthernet0/2"}
VALID_SWITCH_PORTS = {f"FastEthernet0/{i}" for i in range(1, 25)} | {
    "GigabitEthernet0/1",
    "GigabitEthernet0/2",
}
VALID_END_PORTS = {"FastEthernet0"}


def check(name: str, condition: bool, detail: str = "") -> bool:
    status = "PASS" if condition else "FAIL"
    msg = f"[{status}] {name}"
    if detail:
        msg += f" -> {detail}"
    print(msg)
    return condition


def main() -> int:
    ok = True

    ok &= check("JS gerado existe", JS_PATH.is_file(), str(JS_PATH))
    if not ok:
        return 1

    raw_js_bytes = JS_PATH.read_bytes()
    ok &= check("JS sem BOM UTF-8", not raw_js_bytes.startswith(b"\xef\xbb\xbf"))
    ok &= check("JS sem CR (\\r)", b"\r" not in raw_js_bytes)
    try:
        js_text = raw_js_bytes.decode("ascii")
        ok &= check("JS estritamente ASCII", True)
    except UnicodeDecodeError as exc:
        ok &= check("JS estritamente ASCII", False, str(exc))
        js_text = raw_js_bytes.decode("utf-8", errors="replace")

    add_devices = re.findall(r'^addDevice\("([^"]+)",\s*"([^"]+)",\s*\d+,\s*\d+\);', js_text, re.M)
    add_links = re.findall(
        r'^addLink\("([^"]+)",\s*"([^"]+)",\s*"([^"]+)",\s*"([^"]+)",\s*"([^"]+)"\);',
        js_text,
        re.M,
    )
    ios_calls = re.findall(r'^configureIosDevice\("([^"]+)",\s*`\n(.*?)\n`\);', js_text, re.M | re.S)
    pc_calls = re.findall(r'^configurePcIp\("([^"]+)",', js_text, re.M)

    ok &= check("Contagem addDevice == 20", len(add_devices) == 20, f"encontrado {len(add_devices)}")
    ok &= check("Contagem addLink == 20", len(add_links) == 20, f"encontrado {len(add_links)}")
    ok &= check("Contagem configureIosDevice == 11", len(ios_calls) == 11, f"encontrado {len(ios_calls)}")
    ok &= check("Contagem configurePcIp == 9", len(pc_calls) == 9, f"encontrado {len(pc_calls)}")

    dev_models = {name: model for name, model in add_devices}
    expected_all = set(IOS_DEVICES) | set(END_DEVICES)
    ok &= check("Nomes dos 20 dispositivos conferem", set(dev_models) == expected_all)
    ok &= check("Todos os 11 IOS configurados", [d for d, _ in ios_calls] == [
        "SW-CORE", "SW-MATRIZ", "SW-FILIAL", "SW-DC",
        "RC1", "RC2", "RC3", "RA", "RB", "RF", "ISP"
    ])
    ok &= check("Todos os 9 endpoints configurados", set(pc_calls) == set(END_DEVICES))

    # Consistencia de portas e enlaces
    used_ports = set()
    link_ports_ok = True
    link_err = ""
    for d1, p1, d2, p2, cable in add_links:
        if cable != "straight":
            link_ports_ok = False
            link_err = f"cabo invalido {cable}"
            break
        for dev, port in ((d1, p1), (d2, p2)):
            if dev not in dev_models:
                link_ports_ok = False
                link_err = f"dispositivo desconhecido {dev}"
                break
            model = dev_models[dev]
            if model == "2911" and port not in VALID_ROUTER_PORTS:
                link_ports_ok = False
                link_err = f"porta invalida {dev}:{port}"
            elif model == "2960-24TT" and port not in VALID_SWITCH_PORTS:
                link_ports_ok = False
                link_err = f"porta invalida {dev}:{port}"
            elif model in ("PC-PT", "Server-PT") and port not in VALID_END_PORTS:
                link_ports_ok = False
                link_err = f"porta invalida {dev}:{port}"
            if (dev, port) in used_ports:
                link_ports_ok = False
                link_err = f"porta duplicada {dev}:{port}"
            used_ports.add((dev, port))
    ok &= check("Consistencia de portas e enlaces", link_ports_ok, link_err)

    # Leitura dos .txt e checagens de configuracao
    cfgs = {}
    forbidden_found = []
    for dev in IOS_DEVICES:
        p = CONFIG_DIR / f"{dev}.txt"
        raw = p.read_bytes()
        if b"\r" in raw:
            forbidden_found.append(f"{dev}.txt contem CR")
        txt = raw.decode("utf-8")
        cfgs[dev] = txt
        for ln in txt.splitlines():
            s = ln.strip().lower()
            if s in ("end", "configure terminal", "conf t", "enable"):
                forbidden_found.append(f"{dev}.txt linha proibida: {ln}")
    ok &= check("Sem CR ou end/configure terminal/enable", len(forbidden_found) == 0, "; ".join(forbidden_found))

    # Consistencia entre interfaces configuradas nos .txt e enlaces
    for dev in IOS_DEVICES:
        dev_linked_ports = {port for d, port in used_ports if d == dev}
        for port in dev_linked_ports:
            # Verifica se a porta aparece em interface <port> ou range cobrindo-a
            if dev == "SW-CORE" and port.startswith("FastEthernet0/"):
                present = f"interface {port}" in cfgs[dev]
            elif dev == "SW-MATRIZ" and port in ("FastEthernet0/1", "FastEthernet0/2", "FastEthernet0/3"):
                present = "interface range FastEthernet0/1 - 3" in cfgs[dev]
            elif dev == "SW-FILIAL" and port in ("FastEthernet0/1", "FastEthernet0/2"):
                present = "interface range FastEthernet0/1 - 2" in cfgs[dev]
            else:
                present = f"interface {port}" in cfgs[dev]
            ok &= check(f"Porta conectada configurada ({dev} {port})", present)

    # Requisitos OSPF
    expected_rids = {"RC1": "1.1.1.1", "RC2": "2.2.2.2", "RC3": "3.3.3.3", "RA": "4.4.4.4", "RB": "5.5.5.5", "RF": "6.6.6.6"}
    for dev, rid in expected_rids.items():
        ok &= check(
            f"OSPF router-id e auto-cost em {dev}",
            f"router-id {rid}" in cfgs[dev] and "auto-cost reference-bandwidth 1000" in cfgs[dev],
        )

    ok &= check("Area 10 stub em RC2 e RF", "area 10 stub" in cfgs["RC2"] and "area 10 stub" in cfgs["RF"])
    ok &= check("Prioridades DR/BDR (200/100/0)",
                "ip ospf priority 200" in cfgs["RC1"]
                and "ip ospf priority 100" in cfgs["RC2"]
                and "ip ospf priority 0" in cfgs["RC3"])
    ok &= check("ASBR RC3 default-information + redistribute static",
                "default-information originate" in cfgs["RC3"]
                and "redistribute static subnets" in cfgs["RC3"])
    ok &= check("Passive-interface correta (RC3 G0/1, RF G0/0; RA/RB nao passivas)",
                "passive-interface GigabitEthernet0/1" in cfgs["RC3"]
                and "passive-interface GigabitEthernet0/0" in cfgs["RF"]
                and "passive-interface" not in cfgs["RA"]
                and "passive-interface" not in cfgs["RB"])
    ok &= check("Custos OSPF distintos (10 vs 50 vs 1)",
                "ip ospf cost 10" in cfgs["RC1"]
                and "ip ospf cost 50" in cfgs["RC2"]
                and "ip ospf cost 50" in cfgs["RB"]
                and "ip ospf cost 1" in cfgs["RA"])

    # Requisitos HSRP
    ok &= check("HSRP RA (prio 120, preempt, track 40) e RB (prio 100, preempt)",
                "standby 10 ip 192.168.10.1" in cfgs["RA"]
                and "standby 10 priority 120" in cfgs["RA"]
                and "standby 10 preempt" in cfgs["RA"]
                and "standby 10 track 1 decrement 40" in cfgs["RA"]
                and "track 1 interface GigabitEthernet0/1 line-protocol" in cfgs["RA"]
                and "standby 10 ip 192.168.10.1" in cfgs["RB"]
                and "standby 10 priority 100" in cfgs["RB"]
                and "standby 10 preempt" in cfgs["RB"])

    # Requisitos Seguranca L2
    for sw, rng in (("SW-MATRIZ", "FastEthernet0/1 - 3"), ("SW-FILIAL", "FastEthernet0/1 - 2")):
        txt = cfgs[sw]
        ok &= check(
            f"Port Security + Storm Control em {sw} ({rng})",
            rng in txt
            and "switchport port-security maximum 1" in txt
            and "switchport port-security mac-address sticky" in txt
            and "switchport port-security violation shutdown" in txt
            and "storm-control broadcast level 20.00" in txt,
        )

    trust_matches = re.findall(r"ip dhcp snooping trust", cfgs["SW-MATRIZ"])
    ok &= check(
        "DHCP Snooping global, VLAN 10 e trust exclusivo em Fa0/24",
        "ip dhcp snooping" in cfgs["SW-MATRIZ"]
        and "ip dhcp snooping vlan 10" in cfgs["SW-MATRIZ"]
        and "no ip dhcp snooping information option" in cfgs["SW-MATRIZ"]
        and len(trust_matches) == 1
        and "interface FastEthernet0/24" in cfgs["SW-MATRIZ"].split("ip dhcp snooping trust")[0].splitlines()[-5:],
    )

    # Emojis em todos os arquivos gerados
    emoji_hits = []
    for path in list(BASE_DIR.rglob("*.txt")) + list(BASE_DIR.rglob("*.md")) + list(BASE_DIR.rglob("*.py")) + list(BASE_DIR.rglob("*.js")):
        content = path.read_text(encoding="utf-8")
        if EMOJI_RE.search(content):
            emoji_hits.append(path.name)
    ok &= check("Zero emojis em todos os arquivos do projeto", len(emoji_hits) == 0, ", ".join(emoji_hits))

    # Tamanho dos blocos de notas N01..N13
    notas_txt = NOTAS_PATH.read_text(encoding="utf-8")
    blocks = re.findall(r"```text\n(.*?)\n```", notas_txt, re.S)
    too_long = [f"bloco {i+1}: {len(b)} chars" for i, b in enumerate(blocks) if len(b) > 1500]
    ok &= check("13 blocos de notas <= 1500 chars", len(blocks) == 13 and len(too_long) == 0, f"blocos={len(blocks)} {too_long}")

    print("\nRESUMO FINAL:", "APROVADO (100%)" if ok else "REPROVADO")
    return 0 if ok else 1


if __name__ == "__main__":
    sys.exit(main())
