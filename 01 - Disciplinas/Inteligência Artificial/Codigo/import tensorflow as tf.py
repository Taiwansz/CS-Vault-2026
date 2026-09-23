import tensorflow as tf
import numpy as np
from tensorflow.keras.applications.mobilenet_v2 import MobileNetV2, preprocess_input, decode_predictions
from tensorflow.keras.preprocessing import image
import tkinter as tk
from tkinter import filedialog

# Carrega o modelo MobileNetV2 pré-treinado no ImageNet
model = MobileNetV2(weights='imagenet')

def reconhecer_imagem(caminho_imagem):
    img = image.load_img(caminho_imagem, target_size=(224, 224))
    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=0)
    x = preprocess_input(x)

    preds = model.predict(x)
    resultados = decode_predictions(preds, top=3)[0]
    for _, nome, prob in resultados:
        print(f'{nome}: {prob*100:.2f}%')

def selecionar_imagem():
    # Cria uma janela de diálogo para selecionar o arquivo
    root = tk.Tk()
    root.withdraw()  # Esconde a janela principal
    caminho_imagem = filedialog.askopenfilename(title="Selecione uma imagem", filetypes=[("Arquivos de Imagem", "*.jpg;*.jpeg;*.png")])

    if caminho_imagem:
        reconhecer_imagem(caminho_imagem)
    else:
        print("Nenhuma imagem selecionada.")

# Exemplo de uso
selecionar_imagem()
