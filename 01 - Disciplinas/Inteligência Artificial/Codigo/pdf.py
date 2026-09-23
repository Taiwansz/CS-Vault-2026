pdf = FPDF()
pdf.add_page()
pdf.set_auto_page_break(auto=True, margin=15)

pdf.set_font("Arial", "B", 16)
pdf.cell(0, 10, "Teoria sobre Regressão Linear", 0, 1, "C")
pdf.ln(10)

pdf.set_font("Arial", "B", 14)
pdf.cell(0, 10, "O que é Regressão Linear?", 0, 1)
pdf.set_font("Arial", "", 12)
pdf.multi_cell(0, 8,
    "A regressão linear é uma técnica estatística e de aprendizado de máquina usada para modelar a relação entre uma variável dependente contínua "
    "(que queremos prever) e uma ou mais variáveis independentes (também chamadas de preditoras ou features). O objetivo da regressão linear é encontrar "
    "uma função linear que melhor explique como as variáveis independentes influenciam a variável dependente."
)
pdf.ln(5)

pdf.set_font("Arial", "B", 14)
pdf.cell(0, 10, "Regressão Linear Simples", 0, 1)
pdf.set_font("Arial", "", 12)
pdf.multi_cell(0, 8,
    "No caso mais simples, com apenas uma variável independente x, a regressão linear tenta ajustar uma linha reta:\n\n"
    "y = β₀ + β₁x + ε\n\n"
    "- y: variável dependente (resposta ou alvo)\n"
    "- x: variável independente (entrada)\n"
    "- β₀: intercepto da reta (valor de y quando x = 0)\n"
    "- β₁: coeficiente angular (quanto y varia para cada unidade de variação em x)\n"
    "- ε: erro (diferença entre o valor real e o previsto)\n"
)
pdf.ln(5)

pdf.set_font("Arial", "B", 14)
pdf.cell(0, 10, "Como funciona o aprendizado?", 0, 1)
pdf.set_font("Arial", "", 12)
pdf.multi_cell(0, 8,
    "O algoritmo de regressão linear busca os melhores valores para β₀ e β₁ que minimizam o erro total entre as previsões do modelo e os valores reais do conjunto de dados.\n"
    "Uma forma comum de medir esse erro é o Erro Quadrático Médio (MSE):\n\n"
    "MSE = (1/n) ∑ (yᵢ - ŷᵢ)²\n\n"
    "onde yᵢ são os valores reais e ŷᵢ são as previsões do modelo.\n"
    "O método dos mínimos quadrados é usado para encontrar os coeficientes β₀ e β₁ que minimizam esse MSE."
)
pdf.ln(5)

pdf.set_font("Arial", "B", 14)
pdf.cell(0, 10, "Regressão Linear Múltipla", 0, 1)
pdf.set_font("Arial", "", 12)
pdf.multi_cell(0, 8,
    "Quando há mais de uma variável independente, a regressão linear assume a forma:\n\n"
    "y = β₀ + β₁x₁ + β₂x₂ + ... + βₚxₚ + ε\n\n"
    "onde p é o número de variáveis independentes.\n"
    "O modelo tenta encontrar os coeficientes βⱼ que melhor ajustam a relação linear entre as variáveis preditoras e a variável alvo."
)
pdf.ln(5)

pdf.set_font("Arial", "B", 14)
pdf.cell(0, 10, "Aplicações Práticas", 0, 1)
pdf.set_font("Arial", "", 12)
pdf.multi_cell(0, 8,
    "A regressão linear é muito usada para prever valores contínuos em várias áreas, como:\n"
    "- Prever preços de imóveis baseado em características (tamanho, localização, número de quartos).\n"
    "- Estimar o consumo de energia conforme a temperatura ambiente.\n"
    "- Prever vendas futuras baseando-se em dados históricos.\n"
    "- Análise econômica e financeira."
)
pdf.ln(5)

pdf.set_font("Arial", "B", 14)
pdf.cell(0, 10, "Vantagens e Limitações", 0, 1)
pdf.set_font("Arial", "", 12)
pdf.multi_cell(0, 8,
    "Vantagens:\n"
    "- Fácil de entender e interpretar.\n"
    "- Rápido para treinar e prever.\n"
    "- Funciona bem quando a relação entre as variáveis é realmente linear.\n\n"
    "Limitações:\n"
    "- Só captura relações lineares.\n"
    "- Sensível a valores extremos (outliers).\n"
    "- Pode sofrer com multicolinearidade quando variáveis preditoras são muito correlacionadas."
)

pdf_output_path = "/mnt/data/Teoria_Regrassao_Linear.pdf"
pdf.output(pdf_output_path)
