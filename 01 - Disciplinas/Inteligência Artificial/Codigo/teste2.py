import tkinter as tk
from tkinter import simpledialog, messagebox
import cv2
import face_recognition
import os
import numpy as np
from threading import Thread

cadastro_dir = 'rostos_cadastrados'
if not os.path.exists(cadastro_dir):
    os.makedirs(cadastro_dir)

def carregar_rostos():
    codigos = []
    nomes = []
    for arquivo in os.listdir(cadastro_dir):
        img = face_recognition.load_image_file(os.path.join(cadastro_dir, arquivo))
        codif = face_recognition.face_encodings(img)
        if codif:
            codigos.append(codif[0])
            nomes.append(os.path.splitext(arquivo)[0])
    return codigos, nomes

class FaceApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Reconhecimento Facial")
        self.cap = None
        self.running = False
        self.codigos, self.nomes = carregar_rostos()
        
        self.btn_cadastrar = tk.Button(root, text="Cadastrar", command=self.cadastrar)
        self.btn_cadastrar.pack(padx=10, pady=10)

        self.btn_reconhecer = tk.Button(root, text="Reconhecer", command=self.reconhecer)
        self.btn_reconhecer.pack(padx=10, pady=10)

    def abrir_camera(self):
        if self.cap is None:
            self.cap = cv2.VideoCapture(0)
        self.running = True

    def fechar_camera(self):
        self.running = False
        if self.cap:
            self.cap.release()
            self.cap = None
        cv2.destroyAllWindows()

    def cadastrar(self):
        self.abrir_camera()
        nome = simpledialog.askstring("Nome", "Digite o nome para cadastro:")
        if not nome:
            messagebox.showinfo("Aviso", "Cadastro cancelado")
            self.fechar_camera()
            return

        while self.running:
            ret, frame = self.cap.read()
            if not ret:
                break
            
            rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            faces = face_recognition.face_locations(rgb)
            for (top, right, bottom, left) in faces:
                cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 0), 2)

            cv2.putText(frame, "Pressione 'k' para capturar a imagem e cadastrar", (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 0.6, (30, 30, 255), 2)
            cv2.imshow("Cadastrando - Pressione k para OK", frame)

            key = cv2.waitKey(1) & 0xFF
            if key == ord('k') and faces:
                # Salvar imagem do rosto
                top, right, bottom, left = faces[0]
                face_img = frame[top:bottom, left:right]
                arquivo = os.path.join(cadastro_dir, f"{nome}.jpg")
                cv2.imwrite(arquivo, face_img)
                messagebox.showinfo("Sucesso", f"Usuário '{nome}' cadastrado com sucesso!")
                self.codigos, self.nomes = carregar_rostos()
                self.fechar_camera()
                return

            elif key == ord('q'):
                self.fechar_camera()
                return

        self.fechar_camera()

    def reconhecer(self):
        self.abrir_camera()
        messagebox.showinfo("Info", "Posicione o rosto na câmera e pressione 'k' para reconhecer. Pressione 'q' para sair.")
        while self.running:
            ret, frame = self.cap.read()
            if not ret:
                break

            rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            faces = face_recognition.face_locations(rgb)
            codif_rosto = face_recognition.face_encodings(rgb, known_face_locations=faces)

            for i, (top, right, bottom, left) in enumerate(faces):
                cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 0), 2)
                nome = "Desconhecido"
                if codif_rosto:
                    resultados = face_recognition.compare_faces(self.codigos, codif_rosto[i])
                    distancias = face_recognition.face_distance(self.codigos, codif_rosto[i])
                    if True in resultados:
                        idx = np.argmin(distancias)
                        nome = self.nomes[idx]
                cv2.putText(frame, nome, (left, top - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.75, (0, 255, 0), 2)

            cv2.imshow("Reconhecendo - Pressione 'k' para identificar, 'q' para sair", frame)

            key = cv2.waitKey(1) & 0xFF
            if key == ord('k'):
                if faces:
                    messagebox.showinfo("Reconhecimento", f"Pessoa(s) identificada(s): {', '.join([self.nomes[np.argmin(face_recognition.face_distance(self.codigos, enc))] if True in face_recognition.compare_faces(self.codigos, enc) else 'Desconhecido' for enc in codif_rosto])}")
                else:
                    messagebox.showinfo("Reconhecimento", "Nenhum rosto detectado.")
            elif key == ord('q'):
                self.fechar_camera()
                return
            
        self.fechar_camera()

if __name__ == "__main__":
    root = tk.Tk()
    app = FaceApp(root)
    root.mainloop()
