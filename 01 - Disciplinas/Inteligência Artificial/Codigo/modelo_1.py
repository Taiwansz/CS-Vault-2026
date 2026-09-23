import tkinter as tk
from tkinter import messagebox

# --- Configurações iniciais ---
TAM = 100  # Tamanho das células

class JogoDaVelha:
    def __init__(self, raiz):
        self.raiz = raiz
        self.raiz.title('Jogo da Velha com IA')
        self.canvas = tk.Canvas(raiz, width=TAM*3, height=TAM*3, bg='white')
        self.canvas.pack()
        self.jogador = "O"  # O começa (círculo azul)
        self.tabuleiro = [['' for _ in range(3)] for _ in range(3)]
        self.historico = []  # Memória simples de jogadas
        self.desenha_tabuleiro()
        self.canvas.bind('<Double-Button-1>', self.jogada_jogador)

    def desenha_tabuleiro(self):
        self.canvas.delete('all')
        for i in range(1, 3):
            self.canvas.create_line(i*TAM, 0, i*TAM, TAM*3, width=3)
            self.canvas.create_line(0, i*TAM, TAM*3, i*TAM, width=3)
        for i in range(3):
            for j in range(3):
                x0, y0 = j*TAM+10, i*TAM+10
                x1, y1 = (j+1)*TAM-10, (i+1)*TAM-10
                if self.tabuleiro[i][j] == "O":
                    self.canvas.create_oval(x0, y0, x1, y1, outline='blue', width=4)
                elif self.tabuleiro[i][j] == "X":
                    self.canvas.create_rectangle(x0, y0, x1, y1, outline='orange', width=4)

    def jogada_jogador(self, event):
        col = event.x // TAM
        row = event.y // TAM
        if self.tabuleiro[row][col] == '' and self.jogador == "O":
            self.tabuleiro[row][col] = "O"
            self.historico.append((row, col, "O"))
            self.jogador = "X"
            self.desenha_tabuleiro()
            if self.verifica_vitoria("O"):
                self.finaliza("Parabéns! Você venceu.")
                return
            elif self.empatou():
                self.finaliza("Empate!")
                return
            self.raiz.after(600, self.jogada_ia)

    def jogada_ia(self):
        melhor = self.minimax(self.tabuleiro, True)[1]
        if melhor:
            row, col = melhor
            self.tabuleiro[row][col] = "X"
            self.historico.append((row, col, "X"))
        self.jogador = "O"
        self.desenha_tabuleiro()
        if self.verifica_vitoria("X"):
            self.finaliza("A IA venceu!")
        elif self.empatou():
            self.finaliza("Empate!")

    def verifica_vitoria(self, p):
        t = self.tabuleiro
        return any(all(t[i][j]==p for j in range(3)) for i in range(3)) or \
               any(all(t[i][j]==p for i in range(3)) for j in range(3)) or \
               all(t[i][i]==p for i in range(3)) or \
               all(t[i][2-i]==p for i in range(3))

    def empatou(self):
        return all(self.tabuleiro[i][j] != '' for i in range(3) for j in range(3))

    def finaliza(self, msg):
        self.desenha_tabuleiro()
        messagebox.showinfo("Fim de jogo", msg)
        self.tabuleiro = [['' for _ in range(3)] for _ in range(3)]
        self.jogador = "O"
        self.desenha_tabuleiro()
        self.historico.clear()

    def minimax(self, tab, ia):
        if self.verifica_vitoria("X"):
            return 1, None
        if self.verifica_vitoria("O"):
            return -1, None
        if all(tab[i][j] != '' for i in range(3) for j in range(3)):
            return 0, None

        moves = []
        for i in range(3):
            for j in range(3):
                if tab[i][j] == '':
                    novo_tab = [row[:] for row in tab]
                    novo_tab[i][j] = "X" if ia else "O"
                    res = self.minimax(novo_tab, not ia)[0]
                    moves.append((res, (i, j)))
        if ia:
            # Maximiza IA (X)
            melhor = max(moves, key=lambda x: x[0])
        else:
            # Minimiza jogador (O)
            melhor = min(moves, key=lambda x: x[0])
        return melhor

# --- Execução principal ---
root = tk.Tk()
app = JogoDaVelha(root)
root.mainloop()
