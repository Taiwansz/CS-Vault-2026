---
tipo: aula
disciplina: Técnicas Avançadas de Jogos Digitais
professor: Fabricio
tema: "Shaders, Physically Based Rendering (PBR) e Otimizacao de Draw Calls"
status: concluido
---

# Shaders PBR e Otimizacao de Renderizacao em Tempo Real

> [!INFO] **Visao Geral do Modulo**
> A computacao grafica em tempo real para jogos exige fidelidade optica combinada a uma taxa estavel de quadros por segundo (60 a 120 FPS). O modelo **PBR (Physically Based Rendering)** simula o comportamento fisico real da luz ao interagir com superficies materiais.

---

## 1. O Modelo PBR e a BRDF Cook-Torrance

A funcao de distribuicao de refletancia bidirecional (BRDF) divide a luz refletida em duas componentes:
$$f_r = k_d f_{\text{lambert}} + k_s f_{\text{cook-torrance}}$$

### Mapas de Textura Fundamentais do PBR:
1. **Albedo / Base Color:** A cor difusa pura da superficie, sem informacoes de sombra ou luz pre-computada.
2. **Metallic:** Define se a superficie e dieletrica (isolante: plastico, madeira, pedra - valor 0.0) ou condutora (metal puro: ouro, ferro, cobre - valor 1.0).
3. **Roughness (Rugosidade):** Controla a microgeometria da superficie. Valores baixos geram reflexos especulares nitidos como espelho; valores altos dispersam a luz em reflexos borrados.
4. **Normal Map:** Vetores de perturbacao de normal gravados nos canais RGB que simulam relevo e detalhes geometricos sem adicionar novos poligonos a malha.
5. **Ambient Occlusion (AO):** Escala de cinza que simula a oclusao da luz ambiente em frestas, vincos e cantos de geometria.

---

## 2. Gargalos de Desempenho na GPU: Draw Calls e Overdraw

### O que e uma Draw Call?
E o comando emitido pela CPU para a GPU instruindo a renderizacao de uma malha geometrica com um determinado material. O excesso de Draw Calls sobrecarrega a CPU (*CPU Bound*).

### Estrategias de Otimizacao:
- **Static Batching:** Agrupa objetos estaticos que compartilham o mesmo material em uma unica malha unificada na memoria, reduzindo dezenas de chamadas para apenas uma.
- **Dynamic Batching / GPU Instancing:** Renderiza multiplas copias identicas do mesmo objeto (como arvores, grama ou soldados) utilizando uma unica Draw Call e passando transformacoes de posicao via buffers da GPU.
- **LOD (Level of Detail):** Reduz a contagem poligonal da malha conforme o objeto se distancia da camera.
- **Occlusion Culling:** Descarta objetos que estao totalmente ocultos atras de paredes ou montanhas antes que eles sejam enviados para o pipeline de rasterizacao da GPU.
