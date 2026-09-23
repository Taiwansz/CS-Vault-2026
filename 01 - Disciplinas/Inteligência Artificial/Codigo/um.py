# Importar bibliotecas necessárias
import numpy as np
from sklearn.linear_model import LinearRegression
import matplotlib.pyplot as plt

# Criar dados de exemplo (tamanho da casa em m² vs preço em milhares)
X = np.array([[30], [40], [50], [60], [70], [80], [90]])  # variável independente (tamanho)
y = np.array([200, 250, 300, 320, 360, 400, 420])       # variável dependente (preço)

# Criar e treinar o modelo
modelo = LinearRegression()
modelo.fit(X, y)

# Fazer previsões
X_novo = np.array([[55], [85]])
y_previsto = modelo.predict(X_novo)

print("Preços previstos para casas de 55m² e 85m²:", y_previsto)

# Visualizar dados e linha de regressão
plt.scatter(X, y, color='blue', label='Dados reais')
plt.plot(X, modelo.predict(X), color='red', label='Regressão Linear')
plt.scatter(X_novo, y_previsto, color='green', label='Previsões')
plt.xlabel("Tamanho da casa (m²)")
plt.ylabel("Preço (milhares)")
plt.legend()
plt.show()
