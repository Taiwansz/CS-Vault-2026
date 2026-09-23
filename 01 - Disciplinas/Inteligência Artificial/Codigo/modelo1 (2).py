import tkinter as tk
from tkinter import messagebox
import cv2
import sys

# Para diagnosticar ambiente Python
print("Python executável:", sys.executable)
print("Caminhos dos módulos:", sys.path)

# Tentativa de importar PIL, com mensagem clara se não estiver instalado
try:
    from PIL import Image, ImageTk
except ModuleNotFoundError:
    messagebox.showerror("Erro", "Biblioteca Pillow (PIL) não encontrada. Instale com:\npip install Pillow")
    raise

import tensorflow as tf
import numpy as np


# Carrega modelo pré-treinado MobileNetV2 para classificação de imagens
model = tf.keras.applications.MobileNetV2(weights='imagenet')

# Função para pré-processar imagem para o MobileNetV2
def preprocess_image(img):
    img = cv2.resize(img, (224, 224))
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    img = tf.keras.applications.mobilenet_v2.preprocess_input(img)
    img = np.expand_dims(img, axis=0)
    return img

# Decodifica a predição em texto
def decode_predictions(preds):
    decoded = tf.keras.applications.mobilenet_v2.decode_predictions(preds, top=1)
    return decoded[0][0][1], decoded[0][0][2]  # nome e probabilidade

class App:
    def __init__(self, window, window_title):
        self.window = window
        self.window.title(window_title)

        self.video_capture = cv2.VideoCapture(0)

        self.panel = tk.Label(window)
        self.panel.pack()

        self.btn_analyze = tk.Button(window, text="Analisar", command=self.analyze_frame)
        self.btn_analyze.pack()

        self.result_label = tk.Label(window, text="", font=("Arial", 16))
        self.result_label.pack()

        self.update_video()
        self.window.protocol("WM_DELETE_WINDOW", self.on_closing)
        self.window.mainloop()

    def update_video(self):
        ret, frame = self.video_capture.read()
        if ret:
            self.current_frame = frame.copy()
            frame_rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            img = Image.fromarray(frame_rgb)
            imgtk = ImageTk.PhotoImage(image=img)
            self.panel.imgtk = imgtk
            self.panel.config(image=imgtk)
        self.window.after(30, self.update_video)

    def analyze_frame(self):
        if hasattr(self, 'current_frame'):
            img = preprocess_image(self.current_frame)
            preds = model.predict(img)
            name, prob = decode_predictions(preds)
            self.result_label.config(text=f"Reconhecido: {name} ({prob*100:.2f}%)")
        else:
            messagebox.showerror("Erro", "Nenhum frame capturado da câmera.")

    def on_closing(self):
        self.video_capture.release()
        self.window.destroy()

if __name__ == "__main__":
    App(tk.Tk(), "Reconhecimento com Câmera e IA")
