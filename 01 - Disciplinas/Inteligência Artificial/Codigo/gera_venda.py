import pandas as pd
import numpy as np

np.random.seed(42)

# Definir período (ex: 1º jan a 30 jun)
datas = pd.date_range(start='2025-01-01', end='2025-06-30')

# Produtos disponíveis
produtos = ['Produto A', 'Produto B', 'Produto C', 'Produto D']

# Preços médios para cada produto
precos = {
    'Produto A': 20.0,
    'Produto B': 35.0,
    'Produto C': 50.0,
    'Produto D': 15.0,
}

dados = []

for data in datas:
    dia_semana = data.day_name()  # Nome do dia da semana
    
    # Para cada produto, gerar vendas do dia
    for produto in produtos:
        preco = precos[produto]
        
        # Simular quantidade vendida com variação por dia da semana
        # Exemplo: fim de semana vende mais
        if dia_semana in ['Saturday', 'Sunday']:
            qtd_vendida = np.random.poisson(10)
        else:
            qtd_vendida = np.random.poisson(5)
        
        dados.append({
            'Data': data.strftime('%Y-%m-%d'),
            'Dia_da_Semana': dia_semana,
            'Produto': produto,
            'Preco': preco,
            'Quantidade': qtd_vendida
        })

# Criar DataFrame
df = pd.DataFrame(dados)

# Salvar CSV
df.to_csv('vendas_detalhadas_semestre.csv', index=False)

print("Planilha 'vendas_detalhadas_semestre.csv' criada com sucesso!")
print(df.head(10))
