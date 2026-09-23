//Trabalho de Estrutura de Dados: Simulação de Estacionamento Usando Pilhas
//Aluno:	Matheus Sousa dos Santos | 52319400
//Docente: 	Carlos Alexandre Miglinski 


#include <stdio.h>
#include <malloc.h>

typedef struct {
    char placa[8]; 
} REGISTRO;

typedef struct aux {
    REGISTRO reg;
    struct aux *prox;
} ELEMENTO;

typedef ELEMENTO* PONT;

typedef struct {
    PONT topo;
} PILHA;

void inicializaPilha(PILHA *p) {
    p->topo = NULL;
}

void entradaVeiculo(PILHA *p, char placa[]) {
    PONT novo = (PONT)malloc(sizeof(ELEMENTO));
    // Copia a placa manualmente
    for (int i = 0; i < 8; i++) {
        novo->reg.placa[i] = placa[i];
    }
    novo->prox = p->topo;
    p->topo = novo;
    printf("Veiculo %s entrou no estacionamento.\n", placa);
}

void saidaVeiculo(PILHA *p) {
    if (p->topo != NULL) {
        PONT apagar = p->topo;
        p->topo = p->topo->prox;
        free(apagar);
    }
}

void mostrarPilha(PILHA *p, char nome[]) {
    PONT atual = p->topo;
    printf("\n%s: ", nome);
    while (atual != NULL) {
        printf("%s ", atual->reg.placa);
        atual = atual->prox;
    }
    printf("\n");
}

void removerVeiculo(PILHA *estacionamento, PILHA *patio, char placa[]) {
    printf("\n--- Remocao de Veiculo %s ---\n", placa);
    
    while (estacionamento->topo != NULL && (estacionamento->topo->reg.placa[0] != placa[0] || 
           estacionamento->topo->reg.placa[1] != placa[1] || 
           estacionamento->topo->reg.placa[2] != placa[2] || 
           estacionamento->topo->reg.placa[3] != placa[3] || 
           estacionamento->topo->reg.placa[4] != placa[4] || 
           estacionamento->topo->reg.placa[5] != placa[5] || 
           estacionamento->topo->reg.placa[6] != placa[6] || 
           estacionamento->topo->reg.placa[7] != placa[7])) {
        printf("Movendo veiculo %s para o patio temporariamente.\n", estacionamento->topo->reg.placa);
        PONT novo = (PONT)malloc(sizeof(ELEMENTO));
      
        for (int i = 0; i < 8; i++) {
            novo->reg.placa[i] = estacionamento->topo->reg.placa[i];
        }
        novo->prox = patio->topo;
        patio->topo = novo;
        saidaVeiculo(estacionamento); 
    }

    if (estacionamento->topo != NULL) {
        printf("Veiculo %s foi removido do estacionamento.\n", placa);
        saidaVeiculo(estacionamento);
    } else {
        printf("Veiculo %s nao encontrado no estacionamento.\n", placa);
    }

    printf("\n--- Devolucao dos Veiculos do Patio ---\n");
    while (patio->topo != NULL) {
        printf("Devolvendo veiculo %s para o estacionamento.\n", patio->topo->reg.placa);
        PONT novo = (PONT)malloc(sizeof(ELEMENTO));
        for (int i = 0; i < 8; i++) {
            novo->reg.placa[i] = patio->topo->reg.placa[i];
        }
        novo->prox = estacionamento->topo;
        estacionamento->topo = novo;
        saidaVeiculo(patio);
    }

    printf("\nRemocao concluida.\n");
}

int main() {
    PILHA estacionamento, patio;
    inicializaPilha(&estacionamento);
    inicializaPilha(&patio);

    printf("--- Entrada de Veiculos ---\n");

	entradaVeiculo(&estacionamento, "MIG1234");
    entradaVeiculo(&estacionamento, "MIG5678");
    entradaVeiculo(&estacionamento, "MIG1227");
    entradaVeiculo(&estacionamento, "MIG7894");
    entradaVeiculo(&estacionamento, "MIG4325");

    printf("\n--- Estado Atual do Estacionamento ---");
    mostrarPilha(&estacionamento, "Estacionamento");


    removerVeiculo(&estacionamento, &patio, "MIG1234");
    removerVeiculo(&estacionamento, &patio, "MIG7894");

    printf("\n--- Estado Atual do Estacionamento ---");
    mostrarPilha(&estacionamento, "Estacionamento");

    return 0;
}
