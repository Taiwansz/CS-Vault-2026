import tkinter as tk
from tkinter import scrolledtext, simpledialog, messagebox
import spacy
import os
import json
from llama_cpp import Llama
from huggingface_hub import hf_hub_download
import threading
from thefuzz import process

# --- 1. CONFIGURAÇÃO (continua igual) ---
MODEL_ID = "microsoft/Phi-3-mini-4k-instruct-gguf"
MODEL_FILE = "Phi-3-mini-4k-instruct-q4.gguf"
MODEL_PATH = os.path.join(os.getcwd(), "models", MODEL_FILE)
os.makedirs(os.path.join(os.getcwd(), "models"), exist_ok=True)

if not os.path.exists(MODEL_PATH):
    print(f"Baixando o modelo '{MODEL_FILE}'...")
    hf_hub_download(repo_id=MODEL_ID, filename=MODEL_FILE, local_dir=os.path.join(os.getcwd(), "models"))
    print("Download concluído.")

print("Carregando o modelo local na memória (usando Llama.cpp)...")
try:
    llm = Llama(model_path=MODEL_PATH, n_ctx=4096, verbose=False)
    print("Modelo carregado com sucesso.")
except Exception as e:
    print(f"Erro ao carregar o modelo local com Llama.cpp: {e}")
    llm = None

try:
    nlp = spacy.load("pt_core_news_sm")
except IOError:
    print("Modelo do spaCy 'pt_core_news_sm' não encontrado.")
    nlp = None

DADOS_PATH = 'dados.json'
if os.path.exists(DADOS_PATH):
    with open(DADOS_PATH, 'r', encoding='utf-8') as f:
        dados = json.load(f)
else:
    dados = {"conhecimento": {}}

# --- FUNÇÃO DE SALVAMENTO ---
def salvar_dados():
    with open(DADOS_PATH, 'w', encoding='utf-8') as f:
        json.dump(dados, f, ensure_ascii=False, indent=4)

# --- FUNÇÕES DE ANÁLISE E BUSCA ---
def analisar_pergunta_com_spacy(pergunta):
    if not nlp: return []
    doc = nlp(pergunta.lower())
    entidades = [ent.text for ent in doc.ents]
    for token in doc:
        if token.pos_ in ['PROPN', 'NOUN'] and token.text not in entidades:
            entidades.append(token.text)
    print(f"[Analisador spaCy] Entidades encontradas: {entidades}")
    return entidades

# --- FUNÇÃO DE BUSCA CORRIGIDA COM O NÍVEL DE EXIGÊNCIA AJUSTADO ---
def buscar_fatos_no_json(entidades):
    fatos_encontrados = set()
    base_conhecimento = dados.get("conhecimento", {})
    
    if not base_conhecimento:
        return []

    entidades_conhecidas = list(base_conhecimento.keys())

    for entidade in entidades:
        melhor_match = process.extractOne(entidade, entidades_conhecidas)
        
        # --- CORREÇÃO APLICADA AQUI ---
        # Abaixamos o nível de exigência de 85 para 75.
        if melhor_match and melhor_match[1] > 75:
            chave_encontrada = melhor_match[0]
            print(f"Entidade '{entidade}' correspondeu à chave '{chave_encontrada}' (Pontuação: {melhor_match[1]})")
            
            info_entidade = base_conhecimento[chave_encontrada]
            for categoria, lista_fatos in info_entidade.items():
                for fato in lista_fatos:
                    fatos_encontrados.add(fato)

    print(f"[Base de Conhecimento] Fatos encontrados: {list(fatos_encontrados)}")
    return list(fatos_encontrados)

# --- O "CÉREBRO SINTETIZADOR" (continua igual) ---
def sintetizar_resposta_com_modelo_local(pergunta, fatos):
    if not llm: return "Erro: O modelo de IA local não foi carregado corretamente."
    if not fatos:
        return "Desculpe, não encontrei nenhuma informação sobre isso em minha base de conhecimento para elaborar uma resposta."

    fatos_formatados = "- " + "\n- ".join(fatos)
    prompt = f"""<|user|>
Com base nos seguintes fatos, escreva uma resposta completa e amigável para a pergunta do usuário.
Crie um texto coeso e natural em português.

FATOS DISPONÍVEIS:
{fatos_formatados}

PERGUNTA DO USUÁRIO:
"{pergunta}"<|end|>
<|assistant|>
"""
    try:
        output = llm(prompt, max_tokens=256, temperature=0.7, stop=["<|end|>"])
        response = output["choices"][0]["text"]
        return response.strip()
    except Exception as e:
        return f"Ocorreu um erro ao gerar a resposta com o modelo local: {e}"

# --- NOVA FUNÇÃO DE APRENDIZADO (continua igual) ---
def adicionar_conhecimento():
    entidade = simpledialog.askstring("Passo 1/3: Entidade", "Qual é o tópico principal do conhecimento?\n(ex: dengue, paracetamol, Lara Croft)")
    if not entidade: return
    categoria = simpledialog.askstring("Passo 2/3: Categoria", f"Qual é a categoria da informação sobre '{entidade}'?\n(ex: sintomas, tratamento, biografia, jogos)")
    if not categoria: return
    informacao = simpledialog.askstring("Passo 3/3: Informação", f"Digite o fato que você quer me ensinar sobre '{categoria}' de '{entidade}':")
    if not informacao: return

    entidade = entidade.lower()
    categoria = categoria.lower()
    if entidade not in dados["conhecimento"]:
        dados["conhecimento"][entidade] = {}
    if categoria not in dados["conhecimento"][entidade]:
        dados["conhecimento"][entidade][categoria] = []
    
    dados["conhecimento"][entidade][categoria].append(informacao)
    salvar_dados()
    messagebox.showinfo("Sucesso!", f"Obrigado! Aprendi um novo fato sobre '{entidade}'.")

# --- FUNÇÃO PRINCIPAL E INTERFACE GRÁFICA (continuam iguais) ---
def processar_pergunta_thread():
    pergunta = entrada_usuario.get("1.0", tk.END).strip()
    if not pergunta:
        botao_perguntar.config(state=tk.NORMAL)
        return
    resposta_ia.config(state=tk.NORMAL)
    resposta_ia.delete("1.0", tk.END)
    
    def set_text(text):
        resposta_ia.insert(tk.END, text)
        root.update_idletasks()

    set_text("Analisando a sua pergunta...\n")
    entidades = analisar_pergunta_com_spacy(pergunta)
    set_text("Buscando fatos relevantes em minha base de conhecimento...\n")
    fatos = buscar_fatos_no_json(entidades)
    set_text("Elaborando uma nova resposta com o modelo local... (Isso pode levar um momento)\n\n---\n")
    resposta_final = sintetizar_resposta_com_modelo_local(pergunta, fatos)
    set_text(resposta_final)
    resposta_ia.config(state=tk.DISABLED)
    botao_perguntar.config(state=tk.NORMAL)

def iniciar_processamento():
    botao_perguntar.config(state=tk.DISABLED)
    thread = threading.Thread(target=processar_pergunta_thread)
    thread.start()

# --- INTERFACE GRÁFICA ATUALIZADA (continua igual) ---
root = tk.Tk()
root.title("Assistente de IA com Aprendizado")
root.geometry("700x550")
main_frame = tk.Frame(root, padx=10, pady=10)
main_frame.pack(fill=tk.BOTH, expand=True)
tk.Label(main_frame, text="Faça sua pergunta:", font=("Arial", 14)).pack(pady=5)
entrada_usuario = scrolledtext.ScrolledText(main_frame, height=5, font=("Arial", 11))
entrada_usuario.pack(fill=tk.X)
button_frame = tk.Frame(main_frame)
button_frame.pack(pady=10)
botao_perguntar = tk.Button(button_frame, text="Perguntar", command=iniciar_processamento, font=("Arial", 12, "bold"), bg="#007ACC", fg="white")
botao_perguntar.pack(side=tk.LEFT, padx=10, ipadx=10)
botao_aprender = tk.Button(button_frame, text="Adicionar Conhecimento", command=adicionar_conhecimento, font=("Arial", 12, "bold"), bg="#4CAF50", fg="white")
botao_aprender.pack(side=tk.LEFT, padx=10, ipadx=10)
tk.Label(main_frame, text="Resposta da IA:", font=("Arial", 14)).pack(pady=5)
resposta_ia = scrolledtext.ScrolledText(main_frame, height=15, font=("Arial", 11), state=tk.DISABLED, wrap=tk.WORD)
resposta_ia.pack(fill=tk.BOTH, expand=True)

root.mainloop()