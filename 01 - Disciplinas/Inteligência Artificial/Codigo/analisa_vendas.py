import pandas as pd
import matplotlib.pyplot as plt

# Importar planilha
df = pd.read_csv('vendas_detalhadas_semestre.csv')

# Converter coluna Data para datetime
df['Data'] = pd.to_datetime(df['Data'])

# Extrair dia da semana numérico (0=segunda, 6=domingo)
df['Dia_Semana_Num'] = df['Data'].dt.dayofweek

# Codificar produto
df['Produto_Cod'] = df['Produto'].astype('category').cat.codes

# Mostrar algumas linhas dos dados
print("Dados amostra:")
print(df[['Data', 'Dia_da_Semana', 'Produto', 'Preco', 'Quantidade']].head(10))

# Análise 1: total de vendas por dia
vendas_por_dia = df.groupby('Data')['Quantidade'].sum()
print("\nVendas totais por dia:")
print(vendas_por_dia.head(10))

# Gráfico vendas por dia
plt.figure(figsize=(12, 5))
vendas_por_dia.plot()
plt.title("Vendas Totais por Dia")
plt.xlabel("Data")
plt.ylabel("Quantidade Vendida")
plt.grid(True)
plt.show()

# Análise 2: total de vendas por produto
vendas_por_produto = df.groupby('Produto')['Quantidade'].sum()
print("\nVendas totais por produto:")
print(vendas_por_produto)

# Gráfico vendas por produto
plt.figure(figsize=(8, 5))
vendas_por_produto.plot(kind='bar')
plt.title("Vendas Totais por Produto")
plt.xlabel("Produto")
plt.ylabel("Quantidade Vendida")
plt.grid(axis='y')
plt.show()

# Análise 3: vendas por dia da semana
vendas_por_dia_semana = df.groupby('Dia_da_Semana')['Quantidade'].sum()
print("\nVendas totais por dia da semana:")
print(vendas_por_dia_semana)

# Gráfico vendas por dia da semana
plt.figure(figsize=(8, 5))
vendas_por_dia_semana.reindex(['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']).plot(kind='bar')
plt.title("Vendas Totais por Dia da Semana")
plt.xlabel("Dia da Semana")
plt.ylabel("Quantidade Vendida")
plt.grid(axis='y')
plt.show()
