/* Fila Dinamica
*/

#include <stdio.h>
#include <malloc.h>

#define true 1
#define false 0;

typedef int TIPOCHAVE;
typedef int boolean;

typedef struct {
    TIPOCHAVE chave; 
    // outros campos ...
} REGISTRO;

typedef struct aux {
    REGISTRO reg;
    struct aux *prox;
} ELEMENTO, *PONT;

typedef struct {
    PONT inicio;
    PONT fim;
} FILA;


void inicializarFila(FILA *f) {
    f->inicio=NULL;
    f->fim=NULL;
}

int tamanhoFila(FILA *f){
    PONT ender = f->inicio;
    int tam = 0;
    while (ender != NULL) {
        tam++;
        ender = ender -> prox;
    }
    return tam;
}

void listaFila(FILA *f) {
    PONT ender = f->inicio;
    printf("Fila [ ");
    while (ender != NULL) {
        printf("%i ",ender->reg.chave);
        ender = ender->prox;
    }
    printf("]\n");
}

boolean inserirFila(FILA *f, REGISTRO reg) {
    PONT novo = (PONT) malloc(sizeof(ELEMENTO));
    novo->reg = reg;
    novo->prox = NULL;
    if (f->inicio==NULL) f->inicio=novo;
    else f->fim->prox = novo;
    f->fim = novo;
    return true;
}

boolean excluirFila( FILA *f, REGISTRO *reg){
    if (f->inicio== NULL) return false;
    *reg = f->inicio->reg;
    PONT apagar = f->inicio;
    f->inicio = f->inicio->prox;
    free(apagar);
    if (f->inicio==NULL) f->fim=NULL;
    return true;
}

void reinicializaFila (FILA *f) {
    PONT ender = f->inicio;
    while (ender != NULL){
        PONT apagar = ender;
        ender = ender->prox;
        free(apagar);
    }
    f->inicio=NULL;
    f->fim=NULL;
}

int main() {
    FILA f1;
    REGISTRO r1;
    inicializarFila(&f1);
    r1.chave = 10;
    inserirFila(&f1,r1);
    r1.chave = 20;
    inserirFila(&f1,r1);
    r1.chave = 30;
    inserirFila(&f1,r1);
    printf("Tamamnho da fila %i\n",tamanhoFila(&f1));
    listaFila(&f1);
    excluirFila(&f1,&r1);
    printf("elemento ( %d) foi excluido\n",r1.chave);
    listaFila(&f1);
    reinicializaFila(&f1);
    listaFila(&f1);
    printf("Tamamnho da fila %i\n",tamanhoFila(&f1));
    return 0;
}

/* Saida tela 
Tamamnho da fila 3
Fila [ 10 20 30 ]
elemento ( 10) foi excluido
Fila [ 20 30 ]
Fila [ ]
Tamamnho da fila 0
*/