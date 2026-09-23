import numpy as np
from sklearn.linear_model import LinearRegression
import joblib

# Dados de treinamento (tamanho da casa e preço)
X_train = np.array([[35], [45], [55], [65], [75]])
y_train = np.array([210, 260, 310, 350, 390])

# Criar e treinar o modelo
modelo = LinearRegression()
modelo.fit(X_train, y_train)

# Salvar o modelo treinado em arquivo
joblib.dump(modelo, 'modelo_regressao_salvo.pkl')

print("Modelo treinado e salvo com sucesso.")

# --- Em outro momento ou programa ---

# Carregar o modelo salvo
modelo_carregado = joblib.load('modelo_regressao_salvo.pkl')

# Novos dados para previsão
X_novos = np.array([[50], [70]])

# Fazer previsões usando o modelo carregado
y_previsto = modelo_carregado.predict(X_novos)

print("Previsões para novos dados:", y_previsto)
