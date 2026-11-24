"""Cria a atividade RSTP no Cisco Packet Tracer 8.2.1.

Ambiente suportado: Windows, tela 1366x768, escala 100%, Packet Tracer fechado.
O Packet Tracer nao possui API publica; por isso a automacao controla a GUI.
"""

from __future__ import annotations

import ctypes
from ctypes import wintypes
import os
import subprocess
import sys
import time
from pathlib import Path

import pyautogui as p
import pyperclip


PT_EXE = Path(r"C:\Program Files\Cisco Packet Tracer 8.2.1\bin\PacketTracer.exe")
SAIDA = Path.home() / "Atividade_1_RSTP.pkt"

p.PAUSE = 0.10
p.FAILSAFE = True


SW1 = """enable
configure terminal
hostname SW1
vlan 10
 name Usuarios
vlan 20
 name Pesquisa
vlan 30
 name Administrativo
vlan 99
 name Gerenciamento
exit
spanning-tree mode rapid-pvst
spanning-tree vlan 10,20,30,99 root primary
interface range gigabitEthernet0/1-2
 switchport mode trunk
 switchport trunk allowed vlan 10,20,30,99
 no shutdown
exit
end
copy running-config startup-config

"""

SW2 = """enable
configure terminal
hostname SW2
vlan 10
 name Usuarios
vlan 20
 name Pesquisa
vlan 30
 name Administrativo
vlan 99
 name Gerenciamento
exit
spanning-tree mode rapid-pvst
interface range gigabitEthernet0/1-2
 switchport mode trunk
 switchport trunk allowed vlan 10,20,30,99
 no shutdown
exit
end
copy running-config startup-config

"""

SW3 = """enable
configure terminal
hostname SW3
vlan 10
 name Usuarios
vlan 20
 name Pesquisa
vlan 30
 name Administrativo
vlan 99
 name Gerenciamento
exit
spanning-tree mode rapid-pvst
interface range gigabitEthernet0/1-2
 switchport mode trunk
 switchport trunk allowed vlan 10,20,30,99
 no shutdown
exit
interface fastEthernet0/1
 switchport mode access
 switchport access vlan 10
 spanning-tree portfast
 spanning-tree bpduguard enable
 no shutdown
exit
interface fastEthernet0/2
 switchport mode access
 switchport access vlan 20
 spanning-tree portfast
 spanning-tree bpduguard enable
 no shutdown
exit
interface fastEthernet0/3
 switchport mode access
 switchport access vlan 30
 spanning-tree portfast
 spanning-tree bpduguard enable
 no shutdown
exit
end
copy running-config startup-config

"""


def click(x: int, y: int, wait: float = 0.0) -> None:
    p.click(x, y)
    if wait:
        time.sleep(wait)


def drag(source: tuple[int, int], target: tuple[int, int]) -> None:
    p.moveTo(*source)
    p.dragTo(*target, duration=0.35, button="left")
    time.sleep(0.25)


def paste(text: str) -> None:
    pyperclip.copy(text)
    p.hotkey("ctrl", "v")


def localizar_janela(pid: int, limite: float = 15.0) -> int:
    """Localiza a janela principal pelo PID e ignora processos auxiliares."""
    user32 = ctypes.windll.user32
    final = time.time() + limite
    while time.time() < final:
        encontrados: list[int] = []

        @ctypes.WINFUNCTYPE(wintypes.BOOL, wintypes.HWND, wintypes.LPARAM)
        def coletar(hwnd: int, _lparam: int) -> bool:
            janela_pid = wintypes.DWORD()
            user32.GetWindowThreadProcessId(hwnd, ctypes.byref(janela_pid))
            if janela_pid.value == pid and user32.IsWindowVisible(hwnd):
                encontrados.append(hwnd)
            return True

        user32.EnumWindows(coletar, 0)
        if encontrados:
            return encontrados[0]
        time.sleep(0.25)
    raise RuntimeError("A janela principal do Packet Tracer nao apareceu.")


def ativar_e_maximizar(hwnd: int) -> None:
    user32 = ctypes.windll.user32
    user32.ShowWindow(hwnd, 3)  # SW_MAXIMIZE
    # A tecla Alt permite ao processo assumir o primeiro plano no Windows.
    p.press("alt")
    user32.SetForegroundWindow(hwnd)
    time.sleep(1.0)


def configurar_switch(posicao: tuple[int, int], configuracao: str) -> None:
    click(*posicao, wait=1.8)
    click(547, 62, wait=1.5)  # guia CLI
    click(700, 600)
    p.press("enter")
    time.sleep(0.8)
    paste(configuracao)
    time.sleep(2.3)
    p.hotkey("alt", "f4")
    time.sleep(0.4)


def escolher_porta_switch(posicao: tuple[int, int], menu_x: int, gi: int) -> None:
    click(*posicao, wait=0.15)
    click(menu_x, 565 if gi == 1 else 587, wait=0.15)


def ligar_switches(
    a: tuple[int, int], menu_a: int, gi_a: int,
    b: tuple[int, int], menu_b: int, gi_b: int,
) -> None:
    click(350, 630)  # Copper Cross-Over
    escolher_porta_switch(a, menu_a, gi_a)
    escolher_porta_switch(b, menu_b, gi_b)


def configurar_pc(posicao: tuple[int, int], ip: str, gateway: str) -> None:
    click(*posicao, wait=1.0)
    click(544, 62, wait=0.7)  # Desktop
    click(468, 115, wait=0.7)  # IP Configuration

    # Static e campos IPv4 da janela do PC-PT no Packet Tracer 8.2.1.
    click(586, 158)
    click(700, 209)
    paste(ip)
    click(700, 244)
    paste("255.255.255.0")
    click(700, 279)
    paste(gateway)
    time.sleep(0.2)
    p.hotkey("alt", "f4")
    time.sleep(0.4)


def adicionar_nota(posicao: tuple[int, int], texto: str) -> None:
    click(146, 95)
    click(*posicao)
    paste(texto)
    p.hotkey("ctrl", "enter")
    p.press("escape")


def main() -> None:
    if p.size() != (1366, 768):
        raise SystemExit(
            f"Resolucao detectada: {p.size().width}x{p.size().height}. "
            "Use 1366x768 e escala do Windows em 100%."
        )
    if not PT_EXE.exists():
        raise SystemExit(f"Packet Tracer nao encontrado em: {PT_EXE}")

    processo = subprocess.Popen(
        [str(PT_EXE)], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL
    )
    janela = localizar_janela(processo.pid)
    ativar_e_maximizar(janela)
    time.sleep(5)

    # Tres switches 2960.
    click(20, 635)
    click(55, 695)
    for destino in ((360, 230), (700, 230), (530, 400)):
        drag((230, 630), destino)

    # Tres PCs.
    click(55, 635)
    for destino in ((440, 520), (530, 520), (620, 520)):
        drag((230, 630), destino)

    # CLI completa dos switches.
    configurar_switch((360, 230), SW1)
    configurar_switch((700, 230), SW2)
    configurar_switch((530, 400), SW3)

    # Paleta de conexoes e triangulo em GigabitEthernet.
    click(122, 635)
    ligar_switches((360, 230), 440, 1, (700, 230), 780, 1)
    ligar_switches((360, 230), 440, 2, (530, 400), 600, 1)
    ligar_switches((700, 230), 780, 2, (530, 400), 600, 2)

    # Automatic Connection escolhe Fa0 nos PCs e Fa0/1-3 livres no SW3.
    for pc in ((440, 520), (530, 520), (620, 520)):
        click(230, 630)
        click(*pc)
        click(530, 400)
        time.sleep(0.25)
    p.press("escape")

    configurar_pc((440, 520), "192.168.10.10", "192.168.10.1")
    configurar_pc((530, 520), "192.168.20.10", "192.168.20.1")
    configurar_pc((620, 520), "192.168.30.10", "192.168.30.1")

    # Identificacao visual e comandos de validacao exigidos.
    adicionar_nota(
        (830, 205),
        "ATIVIDADE 1 - RSTP / RAPID-PVST\n"
        "SW1: Root primario VLANs 10,20,30,99\n"
        "Trunks: somente VLANs 10,20,30,99\n"
        "SW3 Fa0/1: PC1 VLAN 10 - 192.168.10.10/24\n"
        "SW3 Fa0/2: PC2 VLAN 20 - 192.168.20.10/24\n"
        "SW3 Fa0/3: PC3 VLAN 30 - 192.168.30.10/24\n"
        "Acesso: PortFast + BPDU Guard\n"
        "Validar: show spanning-tree vlan 10,20,30,99\n"
        "show interfaces trunk\nshow spanning-tree inconsistentports",
    )

    # Aguarda convergencia e salva o arquivo independente.
    time.sleep(8)
    p.hotkey("ctrl", "shift", "s")
    time.sleep(1.2)
    paste(str(SAIDA))
    p.press("enter")
    time.sleep(2)
    p.press("left")
    p.press("enter")
    time.sleep(1)

    print(f"Concluido: {SAIDA}")
    print("O Packet Tracer permaneceu aberto para inspecao.")


if __name__ == "__main__":
    try:
        main()
    except p.FailSafeException:
        print("Automacao cancelada: o mouse foi movido para um canto da tela.")
        sys.exit(2)
