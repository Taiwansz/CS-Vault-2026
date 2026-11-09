---
tipo: aula
disciplina: Computação Gráfica - Modelagem 3D
tema: "Pipeline Grafico Programavel, Espacos de Coordenadas e Shaders"
status: concluido
---

> [!NOTE] Navegacao no Cofre
> **Painel Geral:** [[00 - Dashboard]] | **Indice de Aulas:** [[Índice Geral de Aulas]]
> **Disciplina:** [[Computação Gráfica - Modelagem 3D]] | **Nucleo ENADE:** [[01 - Algoritmos, Estruturas de Dados e Paradigmas (Do Basico ao Avancado)]]

# Pipeline Grafico Programavel e Shading na Pratica

> [!INFO] **Visao Geral do Modulo**
> O Pipeline Grafico e a sequencia de estagios pela qual uma representacao tridimensional de uma cena (vertices, texturas e iluminacao) e transformada em uma imagem bidimensional de pixels exibida no monitor.

---

## 1. As Etapas do Pipeline Grafico Moderno

```text
Vertices (3D)
 │
 ▼
[Vertex Shader] --------> Transforma coordenadas (Model -> World -> View -> Clip)
 │
 ▼
[Tessellation / Geometry] -> (Opcional) Subdivisao e geracao geometrica na GPU
 │
 ▼
[Clipping & Rasterization] -> Descarta o que esta fora da camera e converte primitivas em fragmentos (pixels potenciais)
 │
 ▼
[Fragment / Pixel Shader] -> Calcula a cor final de cada pixel (PBR, texturas, sombras)
 │
 ▼
[Blending & Output Merging] -> Teste de profundidade (Z-Buffer), transparencia e exibicao no Framebuffer
```

---

## 2. A Cadeia de Transformacoes de Coordenadas (Espacos MVP)

Para posicionar um vertice 3D na tela 2D, aplicam-se matrizes $4 \times 4$ homogeneas:
$$\mathbf{v}_{\text{clip}} = \mathbf{M}_{\text{Projection}} \times \mathbf{M}_{\text{View}} \times \mathbf{M}_{\text{Model}} \times \mathbf{v}_{\text{local}}$$

1. **Local Space (Object Space):** Coordenadas relativas ao centro de criacao do proprio modelo no Blender/Maya.
2. **World Space:** Coordenadas posicionadas no mundo do jogo (escala, rotacao e translacao aplicadas pela matriz Model).
3. **View Space (Camera Space):** Coordenadas relativas a posicao e angulo da camera (matriz View).
4. **Clip Space:** Projecao perspectiva com coordenada homogenea $W$ calculada. Primitivas fora do frustum da camera sao descartadas.
5. **Screen Space:** Divisao perspectiva ($\text{NDC} = [X/W, Y/W, Z/W]$) mapeada para a resolucao em pixels do monitor.
