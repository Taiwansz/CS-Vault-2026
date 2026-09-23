# leitura2.py — modelo + análise + relatório .docx

import os
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier

# ====== tentar importar python-docx (para o relatório) ======
try:
    from docx import Document
    from docx.shared import Inches
    DOCX_OK = True
except Exception as e:
    print("⚠️ python-docx não está disponível. Instale com: pip install python-docx")
    print("Relatório .docx será pulado nesta execução.")
    DOCX_OK = False

# ====== caminho da planilha ======
# se o arquivo estiver no mesmo diretório do script, deixe só o nome:
caminho_arquivo = r'comportamento_clientes_anime.xlsx'

# ====== leitura ======
df = pd.read_excel(caminho_arquivo)
print("Planilha carregada com sucesso.\n")
print("Primeiras linhas da planilha:")
print(df.head(), "\n")

# ====== normalização do alvo ('Produto Comprado') => 0/1 ======
alvo_raw = df['Produto Comprado'].astype(str).str.strip().str.lower()
positivos = {'1', 'sim', 'yes', 'comprou', 'true'}
negativos = {'0', 'não', 'nao', 'no', 'none', 'nan', '', 'false'}

def map_alvo(x):
    if x in positivos: return 1
    if x in negativos: return 0
    return np.nan

df['Produto Comprado'] = alvo_raw.apply(map_alvo)

print("Distribuição de 'Produto Comprado' (após normalização):")
print(df['Produto Comprado'].value_counts(dropna=False), "\n")

# validar 2 classes
classes = df['Produto Comprado'].dropna().unique()
if len(classes) < 2:
    raise ValueError(
        "A coluna 'Produto Comprado' precisa ter pelo menos duas classes (0 e 1). "
        f"No arquivo atual, só encontrei: {classes}.\n"
        "➡️ Adicione linhas com casos de NÃO compra (0)."
    )

# remover linhas inválidas do alvo
df = df.dropna(subset=['Produto Comprado'])
df['Produto Comprado'] = df['Produto Comprado'].astype(int)

# ====== campanha em 0/1 ======
camp_raw = df['Campanha'].astype(str).str.strip().str.lower()
df['Campanha'] = camp_raw.isin({'1', 'sim', 'yes', 'true'}).astype(int)

# ====== features ======
col_numericas = []
if 'Dias desde a última compra' in df.columns:
    col_numericas.append('Dias desde a última compra')

col_binarias = ['Campanha']

col_categoricas = []
for col in ['Produto Visualizado', 'Histórico de Navegação']:
    if col in df.columns and df[col].dtype == object:
        col_categoricas.append(col)

# montar X
X = pd.DataFrame(index=df.index)
if col_numericas:
    X[col_numericas] = df[col_numericas].apply(pd.to_numeric, errors='coerce')
X[col_binarias] = df[col_binarias]
if col_categoricas:
    dummies = pd.get_dummies(df[col_categoricas], drop_first=True)
    X = pd.concat([X, dummies], axis=1)

# imputar NaNs numéricos se houver
X = X.fillna(X.median(numeric_only=True))
y = df['Produto Comprado']

# ====== split ======
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=42, stratify=y
)

# ====== modelo ======
modelo = RandomForestClassifier(
    n_estimators=200,
    random_state=42,
    class_weight='balanced_subsample',
)
modelo.fit(X_train, y_train)

# ====== probabilidades ======
idx_pos = list(modelo.classes_).index(1)
y_prob = modelo.predict_proba(X)[:, idx_pos]
df['prob_compra'] = y_prob
df['Probabilidade de Compra'] = (df['prob_compra']*100).map(lambda v: f"{v:.1f}% chance de compra")

# segmentos
alta = df[df['prob_compra'] >= 0.70].copy()
baixa = df[df['prob_compra'] <= 0.30].copy()

print("\nClientes com alta probabilidade de compra (>=70%):")
cols_show = [c for c in ['ID Cliente', 'Probabilidade de Compra'] if c in df.columns]
print(alta[cols_show].head(20))
print("\nClientes com baixa probabilidade de compra (<=30%):")
print(baixa[cols_show].head(20))

# ====== importância das variáveis ======
importancias = modelo.feature_importances_
plt.figure(figsize=(9, 6))
plt.barh(X.columns, importancias)
plt.title("Importância das Variáveis no Modelo Random Forest")
plt.xlabel("Importância")
plt.ylabel("Variáveis")
plt.tight_layout()
plt.savefig("importancia_variaveis.png", dpi=140)
plt.close()

# ====== exportações csv/xlsx ======
alta.drop(columns=['prob_compra'], errors='ignore').to_csv('clientes_high_probabilidade.csv', index=False)
baixa.drop(columns=['prob_compra'], errors='ignore').to_csv('clientes_low_probabilidade.csv', index=False)
planilha_formatada = 'clientes_formatados.xlsx'
df.drop(columns=['prob_compra'], errors='ignore').to_excel(planilha_formatada, index=False)

# ====== métricas ======
acuracia = modelo.score(X_test, y_test)
print(f"\nAcurácia do modelo: {acuracia:.2f}")
print(f"Planilha formatada salva em: {os.path.abspath(planilha_formatada)}")

# =============================================================================
# =========================  ANÁLISE & RELATÓRIO  =============================
# =============================================================================

# --- força por produto (taxa de compra) ---
conv_por_produto = (
    df.groupby('Produto Visualizado')['Produto Comprado']
      .mean()
      .sort_values(ascending=False)
)

# contagem de compras por produto (útil para desempate/volume)
compras_por_produto = (
    df.groupby('Produto Visualizado')['Produto Comprado']
      .sum()
      .sort_values(ascending=False)
)

produto_mais_forte = conv_por_produto.idxmax()
taxa_mais_forte = conv_por_produto.max()

produto_mais_fraco = conv_por_produto.idxmin()
taxa_mais_fraco = conv_por_produto.min()

# --- perfil por cliente ---
perfil_clientes = df.groupby("ID Cliente").agg(
    compras=("Produto Comprado", "sum"),
    tentativas=("Produto Comprado", "count"),
    taxa=("Produto Comprado", "mean"),
    produto_mais_comprado=("Produto Visualizado",
                           lambda x: x.mode()[0] if not x.mode().empty else None)
).reset_index()

# ordenar por taxa para uma visão rápida (top 10)
top_clientes = (
    perfil_clientes.sort_values(by=["taxa","compras","tentativas"], ascending=[False, False, True])
    .head(10)
)

# ====== gráficos para o relatório ======
# gráfico: conversão por produto
plt.figure(figsize=(9, 6))
conv_por_produto.plot(kind="bar")
plt.title("Taxa de Conversão por Produto")
plt.ylabel("Taxa de compra")
plt.xlabel("Produto")
plt.tight_layout()
plt.savefig("graf_conv_produto.png", dpi=140)
plt.close()

# gráfico: top clientes por taxa
plt.figure(figsize=(9, 6))
plt.bar(top_clientes["ID Cliente"], top_clientes["taxa"])
plt.title("Top 10 Clientes por Taxa de Compra")
plt.ylabel("Taxa de compra")
plt.xlabel("Cliente")
plt.xticks(rotation=45, ha='right')
plt.tight_layout()
plt.savefig("graf_top_clientes.png", dpi=140)
plt.close()

# ====== salvar tabelas auxiliares para auditoria (opcional) ======
conv_por_produto.to_frame("taxa_compra").to_csv("taxa_conversao_por_produto.csv")
perfil_clientes.to_csv("perfil_clientes.csv", index=False)

# ====== gerar relatório .docx ======
if DOCX_OK:
    doc = Document()
    doc.add_heading("Relatório de Análise de Compras", level=0)

    # sumário curto do modelo
    doc.add_paragraph(f"Acurácia (holdout): {acuracia:.2f}")
    doc.add_paragraph(f"Total de registros: {len(df)}")

    # seção 1: força dos produtos
    doc.add_heading("1) Força dos Produtos", level=1)
    doc.add_paragraph(f"Produto mais forte: {produto_mais_forte} (taxa {taxa_mais_forte:.2%})")
    doc.add_paragraph(f"Produto mais fraco: {produto_mais_fraco} (taxa {taxa_mais_fraco:.2%})")

    doc.add_heading("Taxa de Conversão por Produto", level=2)
    for produto, taxa in conv_por_produto.items():
        doc.add_paragraph(f"{produto}: {taxa:.2%}")

    # inserir gráfico de conversão por produto
    if os.path.exists("graf_conv_produto.png"):
        doc.add_picture("graf_conv_produto.png", width=Inches(6))

    # seção 2: perfil por cliente
    doc.add_heading("2) Perfil de Compra por Cliente", level=1)
    for _, row in perfil_clientes.iterrows():
        doc.add_paragraph(
            f"Cliente: {row['ID Cliente']} | "
            f"Compras: {row['compras']}/{row['tentativas']} "
            f"({row['taxa']:.2%}) | "
            f"Produto mais comprado: {row['produto_mais_comprado']}"
        )

    # gráfico top clientes
    doc.add_heading("Top 10 Clientes por Taxa de Compra", level=2)
    if os.path.exists("graf_top_clientes.png"):
        doc.add_picture("graf_top_clientes.png", width=Inches(6))

    # importância das variáveis
    doc.add_heading("3) Importância das Variáveis (Modelo)", level=1)
    if os.path.exists("importancia_variaveis.png"):
        doc.add_picture("importancia_variaveis.png", width=Inches(6))

    # salvar
    nome_relatorio = "relatorio_compras.docx"
    doc.save(nome_relatorio)
    print(f"Relatório gerado: {os.path.abspath(nome_relatorio)}")
else:
    print("Relatório .docx NÃO gerado (instale python-docx para habilitar).")
