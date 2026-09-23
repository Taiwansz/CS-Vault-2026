import tkinter as tk
from tkinter import simpledialog, messagebox
import json
import os
from sentence_transformers import SentenceTransformer, util

# Carrega modelos de linguagem para embeddings (pode ser 'distiluse-base-multilingual-cased')
model = SentenceTransformer('distiluse-base-multilingual-cased')

DB_FILE = "ia_db.json"

# Carregar base de conhecimento
def load_db():
    if os.path.exists(DB_FILE):
        with open(DB_FILE, 'r', encoding='utf-8') as f:
            return json.load(f)
    return {}

# Salvar base de conhecimento
def save_db(db):
    with open(DB_FILE, 'w', encoding='utf-8') as f:
        json.dump(db, f, ensure_ascii=False, indent=2)

knowledge = load_db()

# Função para buscar a resposta mais parecida
def search_answer(question):
    if not knowledge:
        return None, None
    perguntas = list(knowledge.keys())
    embeddings1 = model.encode(question, convert_to_tensor=True)
    embeddings2 = model.encode(perguntas, convert_to_tensor=True)
    cos_scores = util.pytorch_cos_sim(embeddings1, embeddings2)[0].tolist()
    # Encontra maior similaridade
    max_score = max(cos_scores)
    idx = cos_scores.index(max_score)
    if max_score > 0.7:
        return perguntas[idx], knowledge[perguntas[idx]]
    return None, None

# Tkinter UI
class ChatBotApp:
    def __init__(self, root):
        self.root = root
        self.root.title("IA Chatbot Deep Learning com JSON")
        
        self.edit_question = tk.Entry(root, width=80)
        self.edit_question.pack(pady=5)
        
        self.btn_ask = tk.Button(root, text="Perguntar", command=self.process_question)
        self.btn_ask.pack(pady=5)
        
        self.response_label = tk.Label(root, text="", font=('Arial', 14), wraplength=500)
        self.response_label.pack(pady=10)
        
        self.shape_canvas = tk.Canvas(root, width=60, height=60)
        self.shape_id = self.shape_canvas.create_oval(10, 10, 50, 50, fill="grey")
        self.shape_canvas.pack()
        
        # Para novo input de aprendizado
        self.edit_learn = tk.Entry(root, width=80)
        self.btn_learn = tk.Button(root, text="OK (Aprender)", command=self.save_new_answer)
        self.learn_question = None

    # Estado dos shapes
    def set_shape_color(self, color):
        self.shape_canvas.itemconfig(self.shape_id, fill=color)
    
    # Pergunta do usuário
    def process_question(self):
        q = self.edit_question.get().strip()
        self.response_label.config(text="")
        self.set_shape_color("yellow")
        self.root.update_idletasks()
        if not q:
            self.response_label.config(text="Digite uma pergunta.")
            self.set_shape_color("red")
            return
        # Buscar similaridade
        pergunta_salva, resposta = search_answer(q)
        if resposta:
            self.response_label.config(text=f"Resposta: {resposta}")
            self.set_shape_color("green")
            # Permite que usuário corrija, se quiser
            self.show_learn(resposta)
        else:
            self.response_label.config(text="Não sei responder. Por favor, ensine!")
            self.set_shape_color("red")
            self.show_learn("")
            self.learn_question = q
    
    # Mostrar input de aprendizado
    def show_learn(self, default=""):
        self.edit_learn.pack(pady=5)
        self.edit_learn.delete(0, tk.END)
        self.edit_learn.insert(0, default)
        self.btn_learn.pack(pady=5)
    
    # Salvar nova resposta e voltar ao estado normal
    def save_new_answer(self):
        resp = self.edit_learn.get().strip()
        if self.learn_question and resp:
            knowledge[self.learn_question] = resp
            save_db(knowledge)
            self.response_label.config(text="Aprendido! Fique à vontade para perguntar novamente.")
            self.set_shape_color("green")
        else:
            self.response_label.config(text="Resposta inválida.")
            self.set_shape_color("red")
        self.edit_learn.pack_forget()
        self.btn_learn.pack_forget()
        self.learn_question = None

# Rodar a aplicação
if __name__ == "__main__":
    root = tk.Tk()
    app = ChatBotApp(root)
    root.mainloop()
