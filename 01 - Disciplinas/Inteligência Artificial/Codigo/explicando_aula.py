from sklearn.datasets import load_iris
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score

# Carregar o dataset Iris
iris = load_iris()
X = iris.data  # Características das flores
y = iris.target  # Espécies das flores

# Dividir o dataset em treino e teste
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Criar e treinar a Árvore de Decisão
dt_model = DecisionTreeClassifier(random_state=42)
dt_model.fit(X_train, y_train)

# Fazer previsões com a Árvore de Decisão
y_pred = dt_model.predict(X_test)

# Avaliar a acurácia da Árvore de Decisão
accuracy = accuracy_score(y_test, y_pred)
print(f'Acurácia da Árvore de Decisão: {accuracy}')

# Criar e treinar o Random Forest
rf_model = RandomForestClassifier(n_estimators=100, random_state=42)
rf_model.fit(X_train, y_train)

# Fazer previsões com o Random Forest
y_pred_rf = rf_model.predict(X_test)

# Avaliar a acurácia do Random Forest
accuracy_rf = accuracy_score(y_test, y_pred_rf)
print(f'Acurácia do Random Forest: {accuracy_rf}')
