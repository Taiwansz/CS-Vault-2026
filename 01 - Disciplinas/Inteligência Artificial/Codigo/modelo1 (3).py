import json
import os
import numpy as np
import tkinter as tk
from tkinter import messagebox
from tensorflow.keras.models import Sequential, load_model
from tensorflow.keras.layers import Embedding, LSTM, Dense
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
import os
os.environ['TF_ENABLE_ONEDNN_OPTS'] = '0'


# === CONFIG ===
MAX_LEN = 10
MODEL_PATH = 'modelo_ia.h5'
DADOS_PATH = 'dados.json'

# === DADOS ===
if os.path.exists(DADOS_PATH):
    with open(DADOS_PATH, 'r', encoding='utf-8') as f:
        dados = json.load(f)
else:
    dados = {"perguntas": [], "respostas": []}

# === TOKENIZER ===
tokenizer = Tokenizer()
tokenizer.fit_on_texts(dados["respostas"])
vocab_size = len(tokenizer.word_index) + 1

# === TREINAMENTO INICIAL ===
def preparar_dados():
    sequences = []
    for resposta in dados["respostas"]:
        tokens = tokenizer.texts_to_sequences([resposta])[0]
        for i in range(3, len(tokens)):
            entrada = tokens[:i]
            saida = tokens[i]
            sequences.append((entrada, saida))
    if not sequences:
        return None, None
    X = pad_sequences([x[0] for x in sequences], maxlen=MAX_LEN)
    y = np.array([x[1] for x in sequences])
    return X, y

def treinar_modelo():
    X, y = preparar_dados()
    if X is None:
        return None
    model = Sequential()
    model.add(Embedding(vocab_size, 50, input_length=MAX_LEN))
    model.add(LSTM(128))
    model.add(Dense(vocab_size, activation='softmax'))
    model.compile(loss='sparse_categorical_crossentropy', optimizer='adam')
    model.fit(X, y, epochs=200, verbose=0)
    model.save(MODEL_PATH)
    return model

# === CARREGAR OU TREINAR MODELO ===
if os.path.exists(MODEL_PATH):
    model = load_model(MODEL_PATH)
else:
    model = treinar_modelo()

# === GERAR RESPOSTA ===
def gerar_texto(seed_text, num_words=10):
    for _ in range(num_words):
        seq = tokenizer.texts_to_sequences([seed_text])[0]
        padded = pad_sequences([seq], maxlen=MAX_LEN)
        pred = model.predict(padded, verbose=0)
        next_word = tokenizer.index_word.get(np.argmax(pred), '')
        if next_word == '':
            break
        seed_text += ' ' + next_word
    return seed_text

# === SALVAR DADOS ===
def salvar_dados():
    with open(DADOS_PATH, 'w', encoding='utf-8') as f:
        json.dump(dados, f, ensure_ascii=False, indent=4)

# === INTERFACE ===
def fazer_pergunta():
    pergunta = pergunta_entry.get().strip()
    if not pergunta:
        return

    resposta_encontrada = None
    for p, r in zip(dados["perguntas"], dados["respostas"]):
        if p.lower() == pergunta.lower():
            resposta_encontrada = r
            break

    if resposta_encontrada:
        resposta_label.config(text=f"Sistema: {resposta_encontrada}")
    else:
        resposta_label.config(text="Sistema: Não sei a resposta, me ensina.")
        ensinar_frame.pack()

def ensinar_resposta():
    nova_pergunta = pergunta_entry.get().strip()
    nova_resposta = resposta_entry.get().strip()

    if not nova_resposta:
        return

    dados["perguntas"].append(nova_pergunta)
    dados["respostas"].append(nova_resposta)
    tokenizer.fit_on_texts(dados["respostas"])

    # Re-treinar modelo
    global model
    model = treinar_modelo()

    salvar_dados()

    resposta_label.config(text="Sistema: Aprendido! Obrigado.")
    resposta_entry.delete(0, tk.END)
    ensinar_frame.pack_forget()

# === GUI ===
root = tk.Tk()
root.title("Assistente Inteligente")

tk.Label(root, text="Pergunta:").pack()
pergunta_entry = tk.Entry(root, width=50)
pergunta_entry.pack()

tk.Button(root, text="Fazer pergunta", command=fazer_pergunta).pack(pady=5)
resposta_label = tk.Label(root, text="Sistema: ", wraplength=400)
resposta_label.pack()

ensinar_frame = tk.Frame(root)
tk.Label(ensinar_frame, text="Ensine a resposta:").pack()
resposta_entry = tk.Entry(ensinar_frame, width=50)
resposta_entry.pack()
tk.Button(ensinar_frame, text="OK", command=ensinar_resposta).pack(pady=5)

root.mainloop()
