import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression

# Dados de exemplo
X = np.array([[30], [40], [50], [60], [70], [80], [90]])
y = np.array([200, 250, 300, 320, 360, 400, 420])

# Valores para o eixo x para desenhar as linhas
x_line = np.linspace(20, 100, 100).reshape(-1, 1)

# Modelo real treinado
modelo = LinearRegression()
modelo.fit(X, y)

# Coeficientes reais
coef_real = modelo.coef_[0]
intercept_real = modelo.intercept_

# Linha inicial: coef = 0, intercept = média dos y (para começar mais perto)
coef_inicial = 0
intercept_inicial = np.mean(y)

# Criar passos interpolados entre chute inicial e ajuste real para animar
num_passos = 7
coefs = np.linspace(coef_inicial, coef_real, num_passos)
intercepts = np.linspace(intercept_inicial, intercept_real, num_passos)

# Mensagens explicativas para cada passo
messages = [
    "Passo 1: Linha inicial com coeficiente 0 e intercepto na média dos dados.",
    "Passo 2: Ajustando coeficiente e intercepto para melhor encaixe.",
    "Passo 3: Refinando o ajuste da linha.",
    "Passo 4: Ajuste intermediário.",
    "Passo 5: Ajuste quase final.",
    "Passo 6: Pequenos ajustes finais.",
    "Passo 7: Linha final ajustada pelo modelo treinado."
]

plt.figure(figsize=(12, 8))

for i in range(num_passos):
    plt.clf()
    plt.scatter(X, y, color='blue', label='Dados reais')
    y_line = coefs[i] * x_line + intercepts[i]
    plt.plot(x_line, y_line, color='red', label=f'Linha ajustada (passo {i+1})')
    plt.xlabel('Tamanho da casa (m²)')
    plt.ylabel('Preço (milhares)')
    plt.title('Evolução do ajuste da regressão linear')
    plt.legend(loc='upper left')

    # Texto explicativo
    plt.text(25, 450, messages[i], fontsize=12, bbox=dict(facecolor='yellow', alpha=0.5))

    plt.pause(1)

plt.show()
