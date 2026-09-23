# -*- coding: utf-8 -*-
"""
Jogo da Velha com Tkinter — v3 (Quem começa? + Heatmap + Auto‑Treino)
---------------------------------------------------------------------
• Decisão da IA: MINIMAX com memoização (joga perfeito — não perde).
• Aprendizado online: modelo preditivo de oponente por frequências (Markov 1ª ordem).
• Heatmap/percentuais por casa (previsão do humano).
• Auto‑Treino (simulação de partidas para alimentar o modelo).
• NOVO: Frame "Quem começa?" com botões **Eu** (azul) e **IA** (laranja) para definir quem dá o primeiro lance.
  Ao escolher, a rodada é reiniciada; se a IA começar, ela joga automaticamente.

Controles
- Duplo clique em uma casa vazia para jogar (você é o CÍRCULO azul).
- A IA é o QUADRADO laranja.
- Botões: Reiniciar (R), Limpar aprendizado, Auto‑treinar (100), Auto‑treinar (1000).
- Caixa: "Mostrar probabilidades" ativa o heatmap e os percentuais em cada casa.
- Frame: "Quem começa?" → botões **Eu** e **IA**.

Arquivo de aprendizado
- opponent_model.json no diretório do script.

Requisitos
- Python 3.x com Tkinter.
"""

import json
import os
import random
import tkinter as tk
from tkinter import messagebox
from typing import Dict, List, Optional, Tuple

# ======================== Configs visuais ========================
CELL = 140
GAP = 8
GRID = 3
W = H = CELL * GRID
COR_BG = "#ffffff"
COR_LINHA = "#e5e7eb"  # cinza claro
COR_O = "#1e90ff"      # azul do jogador (círculo)
COR_X = "#ff8c00"      # laranja da IA (quadrado)
COR_PREV = "#9ca3af"   # cinza para destaque de previsão
COR_HEAT = "#93c5fd"   # azul clarinho para heatmap das probabilidades
COR_TEXTO = "#0f172a"  # quase-preto para textos

# ======================== Utilidades de tabuleiro ========================
LINHAS_VITORIA = [
    (0, 1, 2), (3, 4, 5), (6, 7, 8),  # linhas
    (0, 3, 6), (1, 4, 7), (2, 5, 8),  # colunas
    (0, 4, 8), (2, 4, 6),             # diagonais
]


def outro(j: str) -> str:
    return "O" if j == "X" else "X"


def tab_para_chave(tab: List[str]) -> str:
    return "".join(tab)


def casas_livres(tab: List[str]) -> List[int]:
    return [i for i, v in enumerate(tab) if v == "-"]


def vencedor(tab: List[str]) -> Optional[str]:
    for a, b, c in LINHAS_VITORIA:
        if tab[a] != "-" and tab[a] == tab[b] == tab[c]:
            return tab[a]
    return None


def linha_vencedora(tab: List[str]) -> Optional[Tuple[int, int, int]]:
    for trio in LINHAS_VITORIA:
        a, b, c = trio
        if tab[a] != "-" and tab[a] == tab[b] == tab[c]:
            return trio
    return None

# ======================== Modelo preditivo do oponente ========================
class ModeloOponente:
    """Aprende frequências de jogadas humanas por estado.

    Armazena um dicionário: estado(str) -> lista de 9 contadores (int).
    """

    def __init__(self, caminho="opponent_model.json"):
        self.caminho = caminho
        self.freqs: Dict[str, List[int]] = {}
        self._carregar()

    def _carregar(self):
        if os.path.exists(self.caminho):
            try:
                with open(self.caminho, "r", encoding="utf-8") as f:
                    data = json.load(f)
                    self.freqs = {k: list(map(int, v)) for k, v in data.items()}
            except Exception:
                self.freqs = {}
        else:
            self.freqs = {}

    def salvar(self):
        try:
            with open(self.caminho, "w", encoding="utf-8") as f:
                json.dump(self.freqs, f, ensure_ascii=False, indent=2)
        except Exception:
            pass

    def registrar(self, estado: str, jogada: int):
        if estado not in self.freqs:
            self.freqs[estado] = [0] * 9
        self.freqs[estado][jogada] += 1

    def prever(self, estado: str, livres: List[int]) -> Optional[int]:
        if not livres:
            return None
        if estado in self.freqs:
            cont = self.freqs[estado]
            best = max(livres, key=lambda i: cont[i])
            if cont[best] > 0:
                return best
        # fallback heurístico
        prioridades = [4, 0, 2, 6, 8, 1, 3, 5, 7]  # centro, cantos, laterais
        for i in prioridades:
            if i in livres:
                return i
        return livres[0]

# ======================== IA Minimax (invencível) ========================
class MinimaxIA:
    def __init__(self, eu: str = "X", oponente: str = "O", modelo_op: Optional[ModeloOponente] = None):
        self.eu = eu
        self.op = oponente
        self.memo: Dict[Tuple[str, str], Tuple[int, Optional[int]]] = {}
        self.modelo_op = modelo_op

    def melhor_jogada(self, tab: List[str]) -> int:
        self.memo.clear()
        score, move = self._minimax(tab, self.eu)
        assert move is not None
        return move

    def _minimax(self, tab: List[str], jogador: str) -> Tuple[int, Optional[int]]:
        win = vencedor(tab)
        if win == self.eu:
            return 1, None
        if win == self.op:
            return -1, None
        livres = casas_livres(tab)
        if not livres:
            return 0, None

        chave = (tab_para_chave(tab), jogador)
        if chave in self.memo:
            return self.memo[chave]

        melhor_mov = None
        if jogador == self.eu:
            melhor_val = -2
            moves = self._ordenar_movimentos(tab, jogador, livres)
            for m in moves:
                tab[m] = jogador
                val, _ = self._minimax(tab, outro(jogador))
                tab[m] = "-"
                if val > melhor_val:
                    melhor_val, melhor_mov = val, m
                if melhor_val == 1:
                    break
        else:
            melhor_val = 2
            moves = self._ordenar_movimentos(tab, jogador, livres)
            if self.modelo_op is not None:
                pred = self.modelo_op.prever(tab_para_chave(tab), livres)
                if pred in moves:
                    moves = [pred] + [x for x in moves if x != pred]
            for m in moves:
                tab[m] = jogador
                val, _ = self._minimax(tab, outro(jogador))
                tab[m] = "-"
                if val < melhor_val:
                    melhor_val, melhor_mov = val, m
                if melhor_val == -1:
                    break

        self.memo[chave] = (melhor_val, melhor_mov)
        return self.memo[chave]

    def _ordenar_movimentos(self, tab: List[str], jogador: str, livres: List[int]) -> List[int]:
        def eh_vitoria_imediata(m: int, p: str) -> bool:
            tab[m] = p
            r = vencedor(tab) == p
            tab[m] = "-"
            return r
        wins = [m for m in livres if eh_vitoria_imediata(m, jogador)]
        if wins:
            return wins + [m for m in livres if m not in wins]
        bloco = [m for m in livres if eh_vitoria_imediata(m, outro(jogador))]
        def prioridade(m: int) -> int:
            if m in bloco:
                return 0
            if m == 4:
                return 1
            if m in (0, 2, 6, 8):
                return 2
            return 3
        return sorted(livres, key=prioridade)

# ======================== Aplicação Tkinter ========================
class App:
    def __init__(self, root: tk.Tk):
        self.root = root
        self.root.title("Jogo da Velha — IA Minimax + Aprendizado de Oponente (v3)")
        self.root.configure(bg=COR_BG)

        # Canvas principal
        self.canvas = tk.Canvas(root, width=W, height=H, bg=COR_BG, highlightthickness=0)
        self.canvas.grid(row=0, column=0, padx=12, pady=12, columnspan=3)

        # Linha de botões 1
        self.btn_reiniciar = tk.Button(root, text="Reiniciar (R)", command=self.reiniciar)
        self.btn_reiniciar.grid(row=1, column=0, pady=(0, 6), sticky="ew", padx=12)

        self.btn_limpar = tk.Button(root, text="Limpar aprendizado", command=self.limpar_aprendizado)
        self.btn_limpar.grid(row=1, column=1, pady=(0, 6), sticky="ew")

        self.lbl_status = tk.Label(root, text="Você é o CÍRCULO azul. Dê duplo clique para jogar.", bg=COR_BG, fg=COR_TEXTO)
        self.lbl_status.grid(row=1, column=2, pady=(0, 6), sticky="e", padx=12)

        # Linha de botões 2 (novidades)
        self.var_show_probs = tk.BooleanVar(value=False)
        self.chk_probs = tk.Checkbutton(root, text="Mostrar probabilidades", variable=self.var_show_probs,
                                        command=self._desenhar_tabuleiro, bg=COR_BG)
        self.chk_probs.grid(row=2, column=0, sticky="w", padx=12, pady=(0, 10))

        self.btn_treinar100 = tk.Button(root, text="Auto‑treinar (100)", command=lambda: self.auto_treinar(100))
        self.btn_treinar100.grid(row=2, column=1, sticky="ew", pady=(0, 10))

        self.btn_treinar1000 = tk.Button(root, text="Auto‑treinar (1000)", command=lambda: self.auto_treinar(1000))
        self.btn_treinar1000.grid(row=2, column=2, sticky="ew", padx=12, pady=(0, 10))

        # ============ NOVO: Quem começa? ============
        self.frm_comeca = tk.LabelFrame(root, text="Quem começa?", bg=COR_BG, fg=COR_TEXTO, labelanchor="n")
        self.frm_comeca.grid(row=3, column=0, columnspan=3, sticky="ew", padx=12, pady=(0, 12))

        self.btn_start_hum = tk.Button(
            self.frm_comeca, text="Eu", command=lambda: self.definir_inicio("humano"),
            bg=COR_O, fg="white", activebackground=COR_O, activeforeground="white", width=12
        )
        self.btn_start_hum.grid(row=0, column=0, padx=(8, 6), pady=8)

        self.btn_start_ia = tk.Button(
            self.frm_comeca, text="IA", command=lambda: self.definir_inicio("ia"),
            bg=COR_X, fg="white", activebackground=COR_X, activeforeground="white", width=12
        )
        self.btn_start_ia.grid(row=0, column=1, padx=(6, 8), pady=8)

        # Estado do jogo
        self.tab = ["-"] * 9
        self.humano = "O"  # círculo azul
        self.ia = "X"       # quadrado laranja
        self.comeca = self.humano  # quem começa a próxima/atual rodada
        self.turno = self.comeca
        self.fim = False
        self.pred_highlight_id: Optional[int] = None
        self.win_line_id: Optional[int] = None
        self.last_pred_estado: Optional[str] = None

        # Placar
        self.v_hum = 0
        self.v_ia = 0
        self.empates = 0

        # Aprendizado + IA
        self.modelo_op = ModeloOponente()
        self.motor = MinimaxIA(eu=self.ia, oponente=self.humano, modelo_op=self.modelo_op)

        # Ligações
        self.canvas.bind("<Double-Button-1>", self.on_duplo_clique)
        self.root.bind("<KeyPress-r>", lambda e: self.reiniciar())
        self.root.protocol("WM_DELETE_WINDOW", self.on_fechar)

        self._desenhar_grid()
        self._desenhar_tabuleiro()
        self._atualizar_previsao()
        # Se a IA estiver configurada para começar, dispara a primeira jogada
        if self.turno == self.ia:
            self.root.after(250, self._jogada_ia)

    # ---------- Desenho ----------
    def _desenhar_grid(self):
        self.canvas.delete("grid")
        for i in range(1, GRID):
            y = i * CELL
            self.canvas.create_line(GAP, y, W - GAP, y, fill=COR_LINHA, width=3, tags="grid")
            x = i * CELL
            self.canvas.create_line(x, GAP, x, H - GAP, fill=COR_LINHA, width=3, tags="grid")

    def _coords_casa(self, idx: int) -> Tuple[int, int, int, int]:
        r = idx // GRID
        c = idx % GRID
        x0 = c * CELL + GAP
        y0 = r * CELL + GAP
        x1 = (c + 1) * CELL - GAP
        y1 = (r + 1) * CELL - GAP
        return x0, y0, x1, y1

    def _desenhar_probabilidades(self):
        self.canvas.delete("probs")
        if not self.var_show_probs.get():
            return
        if self.turno != self.humano or self.fim:
            return
        livres = casas_livres(self.tab)
        if not livres:
            return
        estado = tab_para_chave(self.tab)
        probs = self._probs(estado, livres)  # idx -> p em [0,1]
        for i, p in probs.items():
            x0, y0, x1, y1 = self._coords_casa(i)
            cor = self._blend("#ffffff", COR_HEAT, p)
            self.canvas.create_rectangle(x0 + 3, y0 + 3, x1 - 3, y1 - 3,
                                         fill=cor, width=0, tags="probs")
            self.canvas.create_text((x0 + x1) // 2, (y0 + y1) // 2,
                                    text=f"{int(p * 100 + 0.5)}%",
                                    fill=COR_TEXTO, font=("TkDefaultFont", 14, "bold"), tags="probs")

    def _desenhar_tabuleiro(self):
        # ordem: heatmap -> peças -> linha vitória -> previsão
        self._desenhar_probabilidades()
        self.canvas.delete("peca")
        for i, v in enumerate(self.tab):
            x0, y0, x1, y1 = self._coords_casa(i)
            if v == "O":
                self.canvas.create_oval(x0 + 10, y0 + 10, x1 - 10, y1 - 10,
                                        width=10, outline=COR_O, tags="peca")
            elif v == "X":
                self.canvas.create_rectangle(x0 + 16, y0 + 16, x1 - 16, y1 - 16,
                                             width=0, fill=COR_X, tags="peca")
        self.canvas.delete("winline")
        trio = linha_vencedora(self.tab)
        if trio:
            a, b, c = trio
            ax, ay = self._centro(a)
            cx, cy = self._centro(c)
            self.win_line_id = self.canvas.create_line(ax, ay, cx, cy, width=10, fill="#22c55e", capstyle=tk.ROUND, tags="winline")
        else:
            self.win_line_id = None
        self._desenhar_previsao()

    def _centro(self, idx: int) -> Tuple[int, int]:
        x0, y0, x1, y1 = self._coords_casa(idx)
        return (x0 + x1) // 2, (y0 + y1) // 2

    def _desenhar_previsao(self):
        self.canvas.delete("prev")
        if self.turno == self.humano and not self.fim:
            livres = casas_livres(self.tab)
            estado = tab_para_chave(self.tab)
            pred = self.modelo_op.prever(estado, livres)
            if pred is not None:
                x0, y0, x1, y1 = self._coords_casa(pred)
                self.pred_highlight_id = self.canvas.create_rectangle(
                    x0 + 6, y0 + 6, x1 - 6, y1 - 6,
                    outline=COR_PREV, width=3, dash=(5, 4), tags="prev"
                )
                self.last_pred_estado = estado
        else:
            self.pred_highlight_id = None
            self.last_pred_estado = None

    def _atualizar_previsao(self):
        self._desenhar_previsao()

    # ---------- Eventos ----------
    def on_duplo_clique(self, event):
        if self.fim or self.turno != self.humano:
            return
        idx = self._evento_para_indice(event)
        if idx is None or self.tab[idx] != "-":
            return
        self._jogar(idx, self.humano)
        if not self.fim:
            self.root.after(200, self._jogada_ia)

    def _evento_para_indice(self, event) -> Optional[int]:
        c = event.x // CELL
        r = event.y // CELL
        if 0 <= r < GRID and 0 <= c < GRID:
            return r * GRID + c
        return None

    # ---------- Mecânica ----------
    def _jogar(self, idx: int, jogador: str):
        if jogador == self.humano and self.last_pred_estado is not None:
            self.modelo_op.registrar(self.last_pred_estado, idx)
            self.modelo_op.salvar()
        self.tab[idx] = jogador
        self.turno = outro(jogador)
        self._pos_jogada()

    def _pos_jogada(self):
        win = vencedor(self.tab)
        self._desenhar_tabuleiro()
        if win or not casas_livres(self.tab):
            self.fim = True
            if win == self.humano:
                self.v_hum += 1
                self._status(f"Você venceu! Placar: Você {self.v_hum} x {self.v_ia} IA (empates {self.empates})")
            elif win == self.ia:
                self.v_ia += 1
                self._status(f"A IA venceu. Placar: Você {self.v_hum} x {self.v_ia} IA (empates {self.empates})")
            else:
                self.empates += 1
                self._status(f"Empate! Placar: Você {self.v_hum} x {self.v_ia} IA (empates {self.empates})")
        else:
            if self.turno == self.humano:
                self._status("Sua vez: duplo clique em uma casa vazia.")
            else:
                self._status("Vez da IA…")

    def _jogada_ia(self):
        if self.fim or self.turno != self.ia:
            return
        move = self.motor.melhor_jogada(self.tab[:])
        self._jogar(move, self.ia)

    def _status(self, msg: str):
        self.lbl_status.config(text=msg)

    def definir_inicio(self, quem: str):
        """Define quem começa ('humano' ou 'ia') e reinicia a rodada de imediato."""
        if quem == "humano":
            self.comeca = self.humano
        else:
            self.comeca = self.ia
        self.reiniciar()
        if self.turno == self.ia and not self.fim:
            # deixa a IA começar logo após a reinicialização
            self._status("IA começa…")
            self.root.after(250, self._jogada_ia)

    def reiniciar(self):
        self.tab = ["-"] * 9
        self.turno = self.comeca
        self.fim = False
        self.pred_highlight_id = None
        self.win_line_id = None
        self._desenhar_tabuleiro()
        if self.turno == self.humano:
            self._status("Você é o CÍRCULO azul. Dê duplo clique para jogar.")
        else:
            self._status("IA começa…")
        self._atualizar_previsao()

    def limpar_aprendizado(self):
        if messagebox.askyesno("Confirmar", "Apagar o aprendizado salvo do oponente?"):
            self.modelo_op.freqs.clear()
            try:
                if os.path.exists(self.modelo_op.caminho):
                    os.remove(self.modelo_op.caminho)
            except Exception:
                pass
            self._status("Aprendizado limpo. A IA continuará perfeita (minimax), mas sem previsões anteriores.")
            self._atualizar_previsao()

    # ---------- Probabilidades / Heatmap ----------
    def _probs(self, estado: str, livres: List[int]) -> Dict[int, float]:
        if not livres:
            return {}
        cont = self.modelo_op.freqs.get(estado, [0] * 9)
        # Laplace smoothing ( +1 ) para evitar zero absoluto
        nums = [(i, cont[i] + 1) for i in livres]
        s = sum(v for _, v in nums)
        if s == 0:
            p = 1.0 / len(livres)
            return {i: p for i in livres}
        return {i: v / s for i, v in nums}

    def _blend(self, c1: str, c2: str, t: float) -> str:
        t = max(0.0, min(1.0, float(t)))
        def h2r(s):
            return int(s, 16)
        r1, g1, b1 = h2r(c1[1:3]), h2r(c1[3:5]), h2r(c1[5:7])
        r2, g2, b2 = h2r(c2[1:3]), h2r(c2[3:5]), h2r(c2[5:7])
        r = int(r1 + (r2 - r1) * t)
        g = int(g1 + (g2 - g1) * t)
        b = int(b1 + (b2 - b1) * t)
        return f"#{r:02x}{g:02x}{b:02x}"

    # ---------- Auto‑treino (simulação) ----------
    def auto_treinar(self, n: int = 100):
        """Simula n partidas: humano (O) gera jogadas previstas/heurísticas; IA responde com minimax.
        Atualiza as frequências do modelo do oponente sem mexer no tabuleiro atual.
        """
        registrados = 0
        for _ in range(max(1, int(n))):
            tab = ['-'] * 9
            turno = self.humano
            while True:
                win = vencedor(tab)
                if win or not casas_livres(tab):
                    break
                livres = casas_livres(tab)
                if turno == self.humano:
                    estado = tab_para_chave(tab)
                    pred = self.modelo_op.prever(estado, livres)
                    m = pred if pred is not None else random.choice(livres)
                    self.modelo_op.registrar(estado, m)
                    tab[m] = self.humano
                    registrados += 1
                    turno = self.ia
                else:
                    m = self.motor.melhor_jogada(tab[:])
                    tab[m] = self.ia
                    turno = self.humano
        self.modelo_op.salvar()
        self._status(f"Auto‑treino concluído: {n} partidas simuladas, {registrados} lances humanos registrados.")
        self._atualizar_previsao()
        self._desenhar_tabuleiro()

    def on_fechar(self):
        self.modelo_op.salvar()
        self.root.destroy()

# ======================== Main ========================
if __name__ == "__main__":
    root = tk.Tk()
    app = App(root)
    root.mainloop()
