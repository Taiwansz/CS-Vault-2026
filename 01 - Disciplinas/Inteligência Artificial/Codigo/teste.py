import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression

# Dados fictícios: horas de exercício por semana e pressão arterial correspondente
horas_exercicio = np.array([0, 1, 2, 3, 4, 5, 6, 7, 8]).reshape(-1, 1)  # Quantidade de exercício (horas/semana)
pressao_arterial = np.array([140, 135, 130, 125, 120, 115, 110, 105, 100])  # Pressão arterial (mmHg)

# Criando o modelo de regressão linear
modelo = LinearRegression()

# Ajustando o modelo aos dados
modelo.fit(horas_exercicio, pressao_arterial)

# Previsões do modelo
pressao_prevista = modelo.predict(horas_exercicio)

# Plotando os dados e a linha de regressão
plt.scatter(horas_exercicio, pressao_arterial, color='blue', label='Dados reais')  # Dados reais
plt.plot(horas_exercicio, pressao_prevista, color='red', label='Linha de regressão')  # Linha de regressão
plt.title('Impacto do Exercício na Pressão Arterial')
plt.xlabel('Horas de Exercício por Semana')
plt.ylabel('Pressão Arterial (mmHg)')
plt.legend()
plt.grid(True)
plt.show()

# Exibindo os coeficientes da regressão
print(f'Coeficiente angular (inclinação): {modelo.coef_[0]}')
print(f'Intercepto: {modelo.intercept_}')
