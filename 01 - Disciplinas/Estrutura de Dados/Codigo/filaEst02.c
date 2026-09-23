/*****************************************************************
* Exemplo de Fila com estrutura estatica e interação com usuário *
* filaEst02.c                                                    *
******************************************************************/
#include <stdio.h>

#define MAX 3

#define true 1
#define false 0

typedef int boolean;
typedef int TIPOCHAVE;

typedef struct {
    TIPOCHAVE chave;
} REGISTRO;

typedef struct {
    REGISTRO A[MAX];
    int inicio;
    int nroElem;
} FILA;

void inicializarFila(FILA *f){
    f->inicio = 0;
    f->nroElem = 0;
}

int tamanhoFila(FILA *f) {
    return f->nroElem;
}

void listarFila (FILA *f){
    printf("\n\nFila: [ ");
    int i = f->inicio;
    int temp;
    for(temp = 0; temp < f->nroElem; temp++){
        printf("%d ",f->A[i].chave);
        i = (i+1) % MAX; //se o i passar do máximo, i volta a zero
    }
    printf("]\n");
}

boolean inserirElemFila(FILA *f, REGISTRO reg) {
   if (f->nroElem >= MAX) return false;
   int posicao = (f->inicio + f->nroElem) % MAX;
   f->A[posicao] = reg;
   f->nroElem++;
   return true; 
}

boolean excluirElementoFila(FILA *f, REGISTRO *reg){
    if (f->nroElem == 0) return false;
    *reg = f->A[f->inicio];
    f->inicio = (f->inicio+1) % MAX;
    f->nroElem--;
    return true;
}

void reinicializaFila(FILA *f){
    inicializarFila(f);
}

int main() {
    FILA f1;
    REGISTRO reg1;
    inicializarFila(&f1);
    int op = 1;
    char cont;
    while (op){
        printf("\n\n");
        printf("1- Inserir\n");
        printf("2- Excluir\n");
        printf("3- Listar\n");
        printf("4- Reinicializar\n");
        printf("5- Tamanho da pilha\n");
        printf("0- Finalizar\n");
        printf("Opcao: ");
        scanf("%d",&op);
        switch (op){
            case 1 :printf("\n\nDigite o elemento a ser inserido : "); 
                    scanf("%d",&reg1.chave);
                    if(inserirElemFila(&f1,reg1)) printf("Elemento %d inserido com sucesso\n",reg1.chave);
                    else printf("Erro, fila cheia\n");
                    break;
            case 2 :if (excluirElementoFila(&f1,&reg1)) {
                        printf("\n\nElemento excluido: (%d)\n",reg1.chave);
                    } else {printf("\n\nErro, fila vazia ");} 
                    break;
            case 3 :listarFila(&f1); getchar(); break;
            case 4 : printf("Todos os dados serão perdidos, continua (y/n) ? ");
                    scanf(" %c",&cont); if (cont=='y' || cont == 'Y') reinicializaFila(&f1);
                    break;
            case 5 :printf("\n\ntamanho da fila %d \n",tamanhoFila(&f1)); 
            break;
            case 0 :printf("\n\nHasta la vista, Baby\n\n");
                    break;
            default :printf("\nOpcao invalida\n");
        }
    }
    return 0;
}

