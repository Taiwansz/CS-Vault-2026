import cv2
import face_recognition
import numpy as np
import tkinter as tk
from tkinter import simpledialog, messagebox

# Banco de dados fictício de usuários cadastrados
db = {}

# Função para salvar o banco de dados de faces
def save_db():
    with open("face_db.npy", "wb") as f:
        np.save(f, db)

# Função para carregar o banco de dados de faces
def load_db():
    global db
    try:
        with open("face_db.npy", "rb") as f:
            db = np.load(f, allow_pickle=True).item()
    except FileNotFoundError:
        db = {}

# Função para registrar uma face
def register_face():
    cap = cv2.VideoCapture(0)
    if not cap.isOpened():
        messagebox.showerror("Erro", "Não foi possível acessar a câmera.")
        return

    ret, frame = cap.read()
    if not ret:
        messagebox.showerror("Erro", "Falha ao capturar imagem.")
        cap.release()
        return

    # Detectando a face
    rgb_frame = frame[:, :, ::-1]  # Convertendo a imagem para RGB
    face_locations = face_recognition.face_locations(rgb_frame)
    face_encodings = face_recognition.face_encodings(rgb_frame, face_locations)

    if len(face_encodings) > 0:
        # Solicitar nome do usuário
        name = simpledialog.askstring("Cadastro", "Digite seu nome para cadastro:")
        if name:
            db[name] = face_encodings[0]
            save_db()
            messagebox.showinfo("Cadastro", f"Usuário {name} cadastrado com sucesso!")
        else:
            messagebox.showinfo("Cadastro", "Nome não fornecido. Cadastro não realizado.")
    else:
        messagebox.showinfo("Cadastro", "Nenhuma face detectada. Tente novamente.")

    cap.release()

# Função para reconhecer a face
def recognize_face():
    cap = cv2.VideoCapture(0)
    if not cap.isOpened():
        messagebox.showerror("Erro", "Não foi possível acessar a câmera.")
        return

    while True:
        ret, frame = cap.read()
        if not ret:
            break

        # Detectando rostos
        rgb_frame = frame[:, :, ::-1]  # Convertendo a imagem para RGB
        face_locations = face_recognition.face_locations(rgb_frame)
        face_encodings = face_recognition.face_encodings(rgb_frame, face_locations)

        for (top, right, bottom, left), face_encoding in zip(face_locations, face_encodings):
            cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 0), 2)

            # Comparando com as faces cadastradas
            matches = face_recognition.compare_faces(list(db.values()), face_encoding)
            name = "Desconhecido"

            if True in matches:
                first_match_index = matches.index(True)
                name = list(db.keys())[first_match_index]

            # Exibindo o nome na imagem
            cv2.putText(frame, name, (left, bottom + 10), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)

        # Exibindo a imagem com reconhecimento
        cv2.imshow("Webcam - Reconhecimento", frame)

        # Fechar a câmera com 'ESC' ou 'q'
        if cv2.waitKey(1) & 0xFF == 27:  # ESC
            break

    cap.release()
    cv2.destroyAllWindows()

# Função para inicializar a interface gráfica
def menu():
    load_db()  # Carregar o banco de dados de rostos

    root = tk.Tk()
    root.title("Sistema de Reconhecimento Facial")

    label = tk.Label(root, text="Bem-vindo ao sistema de reconhecimento facial!", font=("Arial", 16))
    label.pack(padx=20, pady=20)

    # Botão para cadastrar face
    cadastrar_button = tk.Button(root, text="Cadastrar Face", font=("Arial", 14), command=register_face)
    cadastrar_button.pack(padx=20, pady=10)

    # Botão para reconhecer face
    reconhecer_button = tk.Button(root, text="Reconhecer Face", font=("Arial", 14), command=recognize_face)
    reconhecer_button.pack(padx=20, pady=10)

    root.mainloop()

if __name__ == "__main__":
    menu()
