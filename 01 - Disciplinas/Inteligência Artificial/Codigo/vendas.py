import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression

# Leitura do CSV (substitua com o caminho para seu arquivo CSV)
dados = pd.read_csv('historico_vendas.csv')

# Preparamos os dados, vamos usar as semanas como variável independente e as vendas semanais como dependente
# Supondo que a coluna 'Semana' tenha as semanas e as colunas 'Janeiro', 'Fevereiro', 'Março' tenham as vendas semanais

semanas = dados['Semana'].values.reshape(-1, 1)

# Concatenando os dados de vendas de janeiro, fevereiro e março
vendas_jan = dados['Janeiro'].values
vendas_fev = dados['Fevereiro'].values
vendas_mar = dados['Março'].values

# Para a regressão, vamos considerar a soma das vendas como variável dependente
vendas_totais = vendas_jan + vendas_fev + vendas_mar

# Criando o modelo de regressão linear
modelo = LinearRegression()

# Ajustando o modelo aos dados
modelo.fit(semanas, vendas_totais)

# Prevendo as vendas para a próxima semana de abril
previsoes = modelo.predict(semanas)

# Gerando um gráfico para visualizar a relação
plt.plot(semanas, vendas_totais, label='Vendas Reais', marker='o')
plt.plot(semanas, previsoes, label='Previsão de Vendas', linestyle='--')
plt.title('Previsão de Vendas Semana a Semana')
plt.xlabel('Semana')
plt.ylabel('Vendas Totais')
plt.legend()
plt.grid(True)
plt.show()

# Exibindo os coeficientes da regressão
print(f'Coeficiente Angular: {modelo.coef_[0]}')
print(f'Intercepto: {modelo.intercept_}')

# Prevendo para o próximo mês (por exemplo, semana 13 em diante)
previsao_abril = modelo.predict(np.array([[i] for i in range(13, 17)]))

# Exibindo a previsão para abril (semana 13 a 16)
for semana, venda in zip(range(13, 17), previsao_abril):
    print(f"Semana {semana}: Previsão de Vendas = {venda:.2f}")

# Relatório formatado para melhor visualização
relatorio = pd.DataFrame({
    'Semana': range(1, 17),
    'Vendas Reais': np.concatenate([vendas_totais, np.zeros(4)]),
    'Previsão de Vendas': np.concatenate([previsoes, previsao_abril])
})

print("\nRelatório de Previsão de Vendas:")
print(relatorio)

