import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error, r2_score

# Gerar dados simulados
np.random.seed(42)
n = 100

temperatura = np.random.normal(75, 5, n)    # média 75°C, desvio 5
vibracao = np.random.normal(3.5, 0.5, n)    # média 3.5 mm/s, desvio 0.5
carga = np.random.normal(130, 10, n)        # média 130 kg, desvio 10

# Tempo até falha depende negativamente das variáveis + ruído
tempo_falha = 1000 - (temperatura * 5) - (vibracao * 50) - (carga * 2) + np.random.normal(0, 30, n)

# Criar DataFrame
df = pd.DataFrame({
    'Temperatura': temperatura,
    'Vibracao': vibracao,
    'Carga': carga,
    'Tempo_Falha': tempo_falha
})

# Visualização rápida
print(df.head())

# Dividir em features e alvo
X = df[['Temperatura', 'Vibracao', 'Carga']]
y = df['Tempo_Falha']

# Dividir em treino e teste
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Criar e treinar o modelo
modelo = LinearRegression()
modelo.fit(X_train, y_train)

# Fazer previsões
y_pred = modelo.predict(X_test)

# Avaliar modelo
mse = mean_squared_error(y_test, y_pred)
r2 = r2_score(y_test, y_pred)

print(f"Erro Quadrático Médio (MSE): {mse:.2f}")
print(f"R² (Coeficiente de Determinação): {r2:.2f}")

# Visualizar comparação real x previsto
plt.scatter(y_test, y_pred)
plt.xlabel('Tempo real até falha')
plt.ylabel('Tempo previsto até falha')
plt.title('Regressão Linear: Real x Previsto')
plt.plot([min(y_test), max(y_test)], [min(y_test), max(y_test)], color='red')
plt.show()
