#!/usr/bin/env python3
"""Gerador do script PTBuilder para o Projeto Integrador NexaTech."""

from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent
CONFIG_DIR = BASE_DIR / "CONFIGURACOES"
OUTPUT_JS = BASE_DIR / "NexaTech_PTBuilder.js"

DEVICES = [
    ("RC1", "2911", 300, 250),
    ("RC2", "2911", 500, 400),
    ("RC3", "2911", 700, 250),
    ("RA", "2911", 200, 420),
    ("RB", "2911", 380, 530),
    ("RF", "2911", 760, 530),
    ("ISP", "2911", 900, 130),
    ("SW-CORE", "2960-24TT", 500, 250),
    ("SW-MATRIZ", "2960-24TT", 300, 650),
    ("SW-FILIAL", "2960-24TT", 760, 660),
    ("SW-DC", "2960-24TT", 900, 350),
    ("PC1", "PC-PT", 120, 800),
    ("PC2", "PC-PT", 220, 800),
    ("PC3", "PC-PT", 320, 800),
    ("PC4", "PC-PT", 700, 800),
    ("PC5", "PC-PT", 830, 800),
    ("DHCP-LEGITIMO", "Server-PT", 430, 800),
    ("DHCP-NAO-AUTORIZADO", "Server-PT", 540, 760),
    ("SRV-CORPORATIVO", "Server-PT", 1080, 350),
    ("SRV-EXTERNO", "Server-PT", 1080, 130),
]

LINKS = [
    ("SW-CORE", "FastEthernet0/1", "RC1", "GigabitEthernet0/0", "straight"),
    ("SW-CORE", "FastEthernet0/2", "RC2", "GigabitEthernet0/0", "straight"),
    ("SW-CORE", "FastEthernet0/3", "RC3", "GigabitEthernet0/0", "straight"),
    ("RC1", "GigabitEthernet0/1", "RA", "GigabitEthernet0/1", "straight"),
    ("RC2", "GigabitEthernet0/1", "RB", "GigabitEthernet0/1", "straight"),
    ("RC2", "GigabitEthernet0/2", "RF", "GigabitEthernet0/1", "straight"),
    ("RC3", "GigabitEthernet0/2", "ISP", "GigabitEthernet0/0", "straight"),
    ("SW-MATRIZ", "FastEthernet0/1", "PC1", "FastEthernet0", "straight"),
    ("SW-MATRIZ", "FastEthernet0/2", "PC2", "FastEthernet0", "straight"),
    ("SW-MATRIZ", "FastEthernet0/3", "PC3", "FastEthernet0", "straight"),
    ("SW-MATRIZ", "FastEthernet0/10", "DHCP-NAO-AUTORIZADO", "FastEthernet0", "straight"),
    ("SW-MATRIZ", "FastEthernet0/24", "DHCP-LEGITIMO", "FastEthernet0", "straight"),
    ("SW-MATRIZ", "GigabitEthernet0/1", "RA", "GigabitEthernet0/0", "straight"),
    ("SW-MATRIZ", "GigabitEthernet0/2", "RB", "GigabitEthernet0/0", "straight"),
    ("SW-FILIAL", "FastEthernet0/1", "PC4", "FastEthernet0", "straight"),
    ("SW-FILIAL", "FastEthernet0/2", "PC5", "FastEthernet0", "straight"),
    ("SW-FILIAL", "GigabitEthernet0/1", "RF", "GigabitEthernet0/0", "straight"),
    ("SW-DC", "FastEthernet0/1", "SRV-CORPORATIVO", "FastEthernet0", "straight"),
    ("SW-DC", "GigabitEthernet0/1", "RC3", "GigabitEthernet0/1", "straight"),
    ("ISP", "GigabitEthernet0/1", "SRV-EXTERNO", "FastEthernet0", "straight"),
]

IOS_ORDER = [
    "SW-CORE",
    "SW-MATRIZ",
    "SW-FILIAL",
    "SW-DC",
    "RC1",
    "RC2",
    "RC3",
    "RA",
    "RB",
    "RF",
    "ISP",
]

END_DEVICE_IPS = [
    ("PC1", True, None, None, None, None),
    ("PC2", True, None, None, None, None),
    ("PC3", True, None, None, None, None),
    ("PC4", False, "192.168.20.10", "255.255.255.0", "192.168.20.1", "192.168.30.10"),
    ("PC5", False, "192.168.20.11", "255.255.255.0", "192.168.20.1", "192.168.30.10"),
    ("DHCP-LEGITIMO", False, "192.168.10.10", "255.255.255.0", "192.168.10.1", "192.168.30.10"),
    ("DHCP-NAO-AUTORIZADO", False, "192.168.10.200", "255.255.255.0", "192.168.10.1", None),
    ("SRV-CORPORATIVO", False, "192.168.30.10", "255.255.255.0", "192.168.30.1", None),
    ("SRV-EXTERNO", False, "198.51.100.10", "255.255.255.0", "198.51.100.1", None),
]


def load_ios_config(device_name: str) -> str:
    """Carrega e higieniza o arquivo de configuracao IOS em modo de configuracao."""
    path = CONFIG_DIR / f"{device_name}.txt"
    raw = path.read_text(encoding="utf-8")
    normalized = raw.replace("\r\n", "\n").replace("\r", "\n")
    lines = [line.strip() for line in normalized.split("\n") if line.strip()]
    joined = "\n".join(lines)
    if "`" in joined or "${" in joined:
        raise ValueError(f"Caractere proibido para template literal em {path}")
    try:
        joined.encode("ascii")
    except UnicodeEncodeError as exc:
        raise ValueError(f"Caractere nao-ASCII detectado em {path}: {exc}") from exc
    return joined


def format_pc_ip_call(entry: tuple) -> str:
    """Formata a chamada configurePcIp conforme parametros definidos."""
    name, dhcp, ip, mask, gw, dns = entry
    if dhcp:
        return f'configurePcIp("{name}", true);'
    args = [f'"{name}"', "false", f'"{ip}"', f'"{mask}"', f'"{gw}"']
    if dns is not None:
        args.append(f'"{dns}"')
    return f'configurePcIp({", ".join(args)});'


def build_js() -> str:
    """Gera o script completo PTBuilder em ASCII puro com LF."""
    out = [
        "// Projeto Integrador de Redes de Computadores - NexaTech",
        "// Gerado automaticamente por gerar_script_ptbuilder.py",
        "",
        "// 1. Insercao de dispositivos (20 equipamentos)",
    ]
    for name, model, x, y in DEVICES:
        out.append(f'addDevice("{name}", "{model}", {x}, {y});')

    out.append("")
    out.append("// 2. Cabeamento logico (20 enlaces straight-through)")
    for d1, p1, d2, p2, cable in LINKS:
        out.append(f'addLink("{d1}", "{p1}", "{d2}", "{p2}", "{cable}");')

    out.append("")
    out.append("// 3. Configuracao IOS (switches primeiro, roteadores em seguida)")
    for dev in IOS_ORDER:
        cfg = load_ios_config(dev)
        out.append(f'configureIosDevice("{dev}", `')
        out.append(cfg)
        out.append("`);")
        out.append("")

    out.append("// 4. Enderecamento IPv4 de PCs e Servidores")
    for entry in END_DEVICE_IPS:
        out.append(format_pc_ip_call(entry))

    out.append("")
    js_text = "\n".join(out)
    js_text.encode("ascii")
    return js_text


def main() -> None:
    js_text = build_js()
    with OUTPUT_JS.open("w", encoding="ascii", newline="\n") as fh:
        fh.write(js_text)
    print(f"Script gerado com sucesso: {OUTPUT_JS} ({len(js_text)} bytes)")


if __name__ == "__main__":
    main()
