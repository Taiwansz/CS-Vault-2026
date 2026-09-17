"""
=============================================================================
Gerador de Figuras Gráficas Científicas — TCC II (Ciência da Computação)
Autor: Matheus Sousa dos Santos (RA: 52319400)
Orientador: Prof. Luiz Claudio Chiavini Oliveira Junior
Centro Universitário Max Planck (UniMAX) - Indaiatuba, 2026
=============================================================================
Gera os 4 gráficos científicos de bancada em alta resolução (300 DPI):
- Figura 10.1: Volume Médio de Tokens de Entrada por Técnica e Caso
- Figura 10.2: Latência e Redução de Time-to-First-Token (TTFT em ms)
- Figura 10.3: Curva de Retenção Semântica (BERTScore F1) vs Razão de Compressão
- Figura 10.4: Projeção de Custo Financeiro e Redução de FLOPs O(n²)
=============================================================================
"""

import os
from pathlib import Path
import matplotlib.pyplot as plt
import numpy as np

# Configuração de estilo sóbrio para publicações científicas ABNT
plt.rcParams['font.family'] = 'sans-serif'
plt.rcParams['font.sans-serif'] = ['Arial', 'DejaVu Sans', 'Helvetica']
plt.rcParams['axes.edgecolor'] = '#333333'
plt.rcParams['axes.linewidth'] = 0.8
plt.rcParams['grid.color'] = '#E0E0E0'
plt.rcParams['grid.linestyle'] = '--'
plt.rcParams['grid.linewidth'] = 0.5

out_dir = Path(__file__).parent.parent / "04 - Figuras"
out_dir.mkdir(parents=True, exist_ok=True)

# -------------------------------------------------------------
# FIGURA 10.1: Volume Médio de Tokens de Entrada por Técnica
# -------------------------------------------------------------
fig, ax = plt.subplots(figsize=(7.5, 4.2), dpi=300)
labels = ['Caso 1: Sist. Distribuídos', 'Caso 2: SQL sob DDL', 'Caso 3: Auditoria LGPD']
few_shot = [2240, 2650, 3180]
zero_shot = [1385, 1792, 2148]
llmlingua_2x = [682, 890, 1064]
llmlingua_4x = [348, 452, 542]

x = np.arange(len(labels))
width = 0.18

rects1 = ax.bar(x - width*1.5, few_shot, width, label='Few-shot (Baseline)', color='#4A5568', edgecolor='#2D3748')
rects2 = ax.bar(x - width*0.5, zero_shot, width, label='Zero-shot', color='#718096', edgecolor='#4A5568')
rects3 = ax.bar(x + width*0.5, llmlingua_2x, width, label='LLMLingua (2x)', color='#2B6CB0', edgecolor='#1A365D')
rects4 = ax.bar(x + width*1.5, llmlingua_4x, width, label='LLMLingua (4x)', color='#319795', edgecolor='#234E52')

ax.set_ylabel('Tokens de Entrada (Prompt)', fontsize=10, fontweight='bold')
ax.set_title('Consumo de Tokens de Entrada por Técnica de Prompting', fontsize=11, fontweight='bold', pad=12)
ax.set_xticks(x)
ax.set_xticklabels(labels, fontsize=9.5)
ax.legend(frameon=True, facecolor='white', edgecolor='#E2E8F0', fontsize=8.5)
ax.grid(axis='y', alpha=0.7)
ax.set_axisbelow(True)

def autolabel(rects):
    for rect in rects:
        height = rect.get_height()
        ax.annotate(f'{height}',
                    xy=(rect.get_x() + rect.get_width() / 2, height),
                    xytext=(0, 2),
                    textcoords="offset points",
                    ha='center', va='bottom', fontsize=7.5)

autolabel(rects1)
autolabel(rects2)
autolabel(rects3)
autolabel(rects4)

fig.tight_layout()
fig1_path = out_dir / "figura_10_1_volume_tokens.png"
fig.savefig(fig1_path, dpi=300)
plt.close(fig)
print(f"[OK] Gerada Figura 10.1: {fig1_path}")

# -------------------------------------------------------------
# FIGURA 10.2: Latência e Time-to-First-Token (TTFT em ms)
# -------------------------------------------------------------
fig, ax = plt.subplots(figsize=(7.5, 4.2), dpi=300)
ttft_few = [1420, 1680, 2150]
ttft_zero = [890, 1120, 1410]
ttft_2x = [460, 580, 710]
ttft_4x = [280, 320, 390]

rects1 = ax.bar(x - width*1.5, ttft_few, width, label='Few-shot (Baseline)', color='#4A5568')
rects2 = ax.bar(x - width*0.5, ttft_zero, width, label='Zero-shot', color='#718096')
rects3 = ax.bar(x + width*0.5, ttft_2x, width, label='LLMLingua (2x)', color='#C53030')
rects4 = ax.bar(x + width*1.5, ttft_4x, width, label='LLMLingua (4x)', color='#DD6B20')

ax.set_ylabel('Time-to-First-Token - TTFT (ms)', fontsize=10, fontweight='bold')
ax.set_title('Impacto da Compressão na Latência Inicial (Prefill Time)', fontsize=11, fontweight='bold', pad=12)
ax.set_xticks(x)
ax.set_xticklabels(labels, fontsize=9.5)
ax.legend(frameon=True, facecolor='white', edgecolor='#E2E8F0', fontsize=8.5)
ax.grid(axis='y', alpha=0.7)
ax.set_axisbelow(True)

autolabel(rects1)
autolabel(rects2)
autolabel(rects3)
autolabel(rects4)

fig.tight_layout()
fig2_path = out_dir / "figura_10_2_latencia_ttft.png"
fig.savefig(fig2_path, dpi=300)
plt.close(fig)
print(f"[OK] Gerada Figura 10.2: {fig2_path}")

# -------------------------------------------------------------
# FIGURA 10.3: Curva de Retenção Semântica (BERTScore F1)
# -------------------------------------------------------------
fig, ax = plt.subplots(figsize=(7.5, 4.2), dpi=300)
ratios = [1.0, 2.0, 4.0, 6.0]
case1_f1 = [1.000, 0.893, 0.873, 0.821]
case2_f1 = [1.000, 0.914, 0.863, 0.795]
case3_f1 = [1.000, 0.883, 0.856, 0.812]

ax.plot(ratios, case1_f1, marker='o', linewidth=2, label='Caso 1: Sist. Distribuídos', color='#2B6CB0')
ax.plot(ratios, case2_f1, marker='s', linewidth=2, label='Caso 2: SQL sob DDL', color='#2C7A7B')
ax.plot(ratios, case3_f1, marker='^', linewidth=2, label='Caso 3: Auditoria LGPD', color='#805AD5')

ax.axhline(0.85, color='#E53E3E', linestyle=':', linewidth=1.5, label='Limiar Crítico de Aceitação (F1 = 0,85)')

ax.set_xlabel('Razão de Compressão (Taxa)', fontsize=10, fontweight='bold')
ax.set_ylabel('BERTScore F1', fontsize=10, fontweight='bold')
ax.set_title('Retenção Semântica (BERTScore F1) em Função da Razão de Compressão', fontsize=11, fontweight='bold', pad=12)
ax.set_ylim(0.75, 1.02)
ax.set_xticks(ratios)
ax.set_xticklabels(['1x (Integral)', '2x (50%)', '4x (75%)', '6x (83%)'], fontsize=9.5)
ax.legend(frameon=True, facecolor='white', edgecolor='#E2E8F0', fontsize=8.5, loc='lower left')
ax.grid(True, alpha=0.6)

fig.tight_layout()
fig3_path = out_dir / "figura_10_3_bertscore_f1.png"
fig.savefig(fig3_path, dpi=300)
plt.close(fig)
print(f"[OK] Gerada Figura 10.3: {fig3_path}")

# -------------------------------------------------------------
# FIGURA 10.4: Projeção de Custo Financeiro e Redução de FLOPs
# -------------------------------------------------------------
fig, ax1 = plt.subplots(figsize=(7.5, 4.2), dpi=300)

scenarios = ['Few-shot', 'Zero-shot', 'LLMLingua 2x', 'LLMLingua 4x']
cost_usd = [1221.00, 926.10, 656.70, 518.40]
flops_reduction = [0.0, 56.4, 74.9, 93.8]

x_pos = np.arange(len(scenarios))
w_bar = 0.35

rects_cost = ax1.bar(x_pos - w_bar/2, cost_usd, w_bar, label='Custo Total 100k Ch. (US$)', color='#2B6CB0')
ax1.set_ylabel('Custo Financeiro Total (US$)', color='#2B6CB0', fontsize=10, fontweight='bold')
ax1.tick_params(axis='y', labelcolor='#2B6CB0')
ax1.set_ylim(0, 1450)

ax2 = ax1.twinx()
rects_flops = ax2.bar(x_pos + w_bar/2, flops_reduction, w_bar, label='Redução FLOPs Atenção (%)', color='#38A169')
ax2.set_ylabel('Redução de FLOPs no Prefill O(n²) (%)', color='#38A169', fontsize=10, fontweight='bold')
ax2.tick_params(axis='y', labelcolor='#38A169')
ax2.set_ylim(0, 110)

ax1.set_xticks(x_pos)
ax1.set_xticklabels(scenarios, fontsize=9.5)
ax1.set_title('Projeção Financeira e Redução de Complexidade Computacional (100k Requisições)', fontsize=11, fontweight='bold', pad=12)

for rect in rects_cost:
    h = rect.get_height()
    ax1.annotate(f'US${h:.0f}',
                xy=(rect.get_x() + rect.get_width() / 2, h),
                xytext=(0, 2),
                textcoords="offset points",
                ha='center', va='bottom', fontsize=8, color='#1A365D')

for rect in rects_flops:
    h = rect.get_height()
    ax2.annotate(f'{h:.1f}%',
                xy=(rect.get_x() + rect.get_width() / 2, h),
                xytext=(0, 2),
                textcoords="offset points",
                ha='center', va='bottom', fontsize=8, color='#22543D')

fig.tight_layout()
fig4_path = out_dir / "figura_10_4_custo_flops.png"
fig.savefig(fig4_path, dpi=300)
plt.close(fig)
print(f"[OK] Gerada Figura 10.4: {fig4_path}")
