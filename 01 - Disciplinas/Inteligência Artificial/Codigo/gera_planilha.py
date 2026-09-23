import pandas as pd
import numpy as np

np.random.seed(42)

n = 100

# Gerar dados simulados
investimento = np.random.uniform(5, 20, n)          # entre 5k e 20k
preco = np.random.uniform(10, 50, n)                # entre R$10 e R$50
estacao = np.random.randint(1, 5, n)                 # 1 a 4 para estações

# Criar variável alvo Vendas com alguma relação + ruído
vendas = 30 + 5 * investimento - 0.8 * preco + 3 * estacao + np.random.normal(0, 10, n)

df = pd.DataFrame({
    'Investimento_Propaganda': investimento,
    'Preco_Produto': preco,
    'Estacao': estacao,
    'Vendas': vendas
})

# Salvar CSV
df.to_csv('vendas_simuladas.csv', index=False)

print(df.head())

