from sklearn.linear_model import LinearRegression
import pandas as pd


# Importar dados
df = pd.read_csv('vendas_simuladas.csv')

# Separar features e alvo
X = df[['Investimento_Propaganda', 'Preco_Produto', 'Estacao']]
y = df['Vendas']

# Criar e treinar modelo
modelo = LinearRegression()
modelo.fit(X, y)

# Mostrar coeficientes
print("Coeficientes:", modelo.coef_)
print("Intercepto:", modelo.intercept_)

# Prever usando o próprio conjunto
y_pred = modelo.predict(X)

# Exemplo de previsão para novos dados
novo = [[10, 30, 2]]  # investimento 10k, preço 30, estação 2
print("Previsão para novo dado:", modelo.predict(novo)[0])
