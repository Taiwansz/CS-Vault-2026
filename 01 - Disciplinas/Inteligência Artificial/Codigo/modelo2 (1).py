import tkinter as tk
from tkinter import scrolledtext, simpledialog, messagebox, Toplevel, Label, Entry, Button
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
    # --- ALTERAÇÃO PREVENTIVA: VAMOS LIMITAR OS RECURSOS ---
    # n_threads limita o número de núcleos de CPU que o modelo pode usar.
    # Isso pode evitar que ele use 100% da CPU e trave o sistema.
    # Se você tiver um processador com 8 núcleos, 4 é um bom valor.
    llm = Llama(model_path=MODEL_PATH, n_ctx=2048, n_threads=4, verbose=False)
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

# --- FUNÇÕES DE ANÁLISE E BUSCA (iguais) ---
def analisar_pergunta_com_spacy(pergunta):
    if not nlp: return []
    doc = nlp(pergunta.lower())
    entidades = [ent.text for ent in doc.ents]
    for token in doc:
        if token.pos_ in ['PROPN', 'NOUN'] and token.text not in entidades:
            entidades.append(token.text)
    print(f"[Analisador spaCy] Entidades encontradas: {entidades}")
    return entidades

def buscar_fatos_no_json(entidades):
    fatos_encontrados = set()
    base_conhecimento = dados.get("conhecimento", {})
    if not base_conhecimento: return []
    entidades_conhecidas = list(base_conhecimento.keys())
    for entidade in entidades:
        melhor_match = process.extractOne(entidade, entidades_conhecidas)
        if melhor_match and melhor_match[1] > 75:
            chave_encontrada = melhor_match[0]
            print(f"Entidade '{entidade}' correspondeu à chave '{chave_encontrada}' (Pontuação: {melhor_match[1]})")
            info_entidade = base_conhecimento[chave_encontrada]
            for categoria, lista_fatos in info_entidade.items():
                for fato in lista_fatos:
                    fatos_encontrados.add(fato)
    print(f"[Base de Conhecimento] Fatos encontrados: {list(fatos_encontrados)}")
    return list(fatos_encontrados)

# --- O "CÉREBRO SINTETIZADOR" (igual) ---
def sintetizar_resposta_com_modelo_local(pergunta, fatos):
    if not llm: return "Erro: O modelo de IA local não foi carregado corretamente."
    if not fatos:
        return "Desculpe, não encontrei nenhuma informação sobre isso para elaborar uma resposta."
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

# --- FUNÇÃO DE APRENDIZADO (igual) ---
def abrir_janela_de_aprendizado():
    janela = Toplevel(root)
    janela.title("Adicionar Novo Conhecimento")
    janela.geometry("400x250")
    Label(janela, text="Entidade (Tópico principal):", font=("Arial", 10, "bold")).pack(pady=(10, 2))
    entry_entidade = Entry(janela, width=50)
    entry_entidade.pack()
    Label(janela, text="Categoria (Contexto da informação):", font=("Arial", 10, "bold")).pack(pady=(10, 2))
    entry_categoria = Entry(janela, width=50)
    entry_categoria.pack()
    Label(janela, text="Informação (O fato a ser aprendido):", font=("Arial", 10, "bold")).pack(pady=(10, 2))
    entry_informacao = Entry(janela, width=50)
    entry_informacao.pack()
    def salvar_e_fechar():
        entidade = entry_entidade.get().strip().lower()
        categoria = entry_categoria.get().strip().lower()
        informacao = entry_informacao.get().strip()
        if not entidade or not categoria or not informacao:
            messagebox.showwarning("Atenção", "Todos os campos devem ser preenchidos.", parent=janela)
            return
        if entidade not in dados["conhecimento"]:
            dados["conhecimento"][entidade] = {}
        if categoria not in dados["conhecimento"][entidade]:
            dados["conhecimento"][entidade][categoria] = []
        dados["conhecimento"][entidade][categoria].append(informacao)
        salvar_dados()
        messagebox.showinfo("Sucesso!", f"Obrigado! Aprendi um novo fato sobre '{entidade}'.", parent=janela)
        janela.destroy()
    Button(janela, text="Salvar e Fechar", command=salvar_e_fechar, font=("Arial", 10, "bold")).pack(pady=20)

# --- NOVA FUNÇÃO DE TESTE SIMPLES ---
def teste_simples_modelo():
    """Função de diagnóstico que envia um prompt simples direto para o modelo."""
    resposta_ia.config(state=tk.NORMAL)
    resposta_ia.delete("1.0", tk.END)
    resposta_ia.insert(tk.END, "Iniciando teste de estresse do modelo...\nIsso pode congelar o programa por um momento.\n\n---\n")
    root.update_idletasks()

    def run_test():
        try:
            prompt_teste = "<|user|>\nQual é a capital do Brasil?<|end|>\n<|assistant|>"
            output = llm(prompt_teste, max_tokens=50)
            resposta = output["choices"][0]["text"]
            messagebox.showinfo("Teste Concluído!", f"O modelo respondeu com sucesso:\n\n{resposta.strip()}")
        except Exception as e:
            messagebox.showerror("Teste Falhou!", f"Ocorreu um erro durante o teste do modelo:\n\n{e}")
        finally:
            # Garante que os botões sejam reativados
            botao_perguntar.config(state=tk.NORMAL)
            botao_teste.config(state=tk.NORMAL)
            
    # Desabilita botões e roda o teste em uma thread para não travar
    botao_perguntar.config(state=tk.DISABLED)
    botao_teste.config(state=tk.DISABLED)
    thread = threading.Thread(target=run_test)
    thread.start()


# --- FUNÇÃO PRINCIPAL E INTERFACE GRÁFICA ---
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
    botao_teste.config(state=tk.NORMAL)

def iniciar_processamento():
    botao_perguntar.config(state=tk.DISABLED)
    botao_teste.config(state=tk.DISABLED)
    thread = threading.Thread(target=processar_pergunta_thread)
    thread.start()

# --- INTERFACE GRÁFICA ATUALIZADA ---
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
botao_aprender = tk.Button(button_frame, text="Adicionar Conhecimento", command=abrir_janela_de_aprendizado, font=("Arial", 12, "bold"), bg="#4CAF50", fg="white")
botao_aprender.pack(side=tk.LEFT, padx=10, ipadx=10)

# NOVO BOTÃO DE TESTE
botao_teste = tk.Button(button_frame, text="Testar Modelo", command=teste_simples_modelo, font=("Arial", 12, "bold"), bg="#FFC107", fg="black")
botao_teste.pack(side=tk.LEFT, padx=10, ipadx=10)

tk.Label(main_frame, text="Resposta da IA:", font=("Arial", 14)).pack(pady=5)
resposta_ia = scrolledtext.ScrolledText(main_frame, height=15, font=("Arial", 11), state=tk.DISABLED, wrap=tk.WORD)
resposta_ia.pack(fill=tk.BOTH, expand=True)
root.mainloop()