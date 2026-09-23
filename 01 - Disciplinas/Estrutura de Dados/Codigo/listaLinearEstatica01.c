/******************************************************************
* Lista Linear Sequencial caso de uso sem interação com o usuario *
* listaLinearEstatica01.c                                         *
*******************************************************************/
#include <stdio.h>
#define MAX 5
#define false 0
#define true 1

typedef int bool;
typedef int TIPOCHAVE;

typedef struct {
    TIPOCHAVE chave;
    // outros campos
} REGISTRO;

typedef struct {
    REGISTRO A[MAX];
    int nroElem;
} LISTA;

void inicializaLista (LISTA *l){
    l->nroElem = 0;
}

int tamanho (LISTA *l){
    return l->nroElem;
}

void imprimirLista(LISTA *l){
    int i;
    printf("Lista [ ");
    for (i=0; i< l->nroElem; i++)
        printf("%d ", l->A[i].chave);
    printf(" ]\n");
}

int buscaSequencial (LISTA *l, TIPOCHAVE ch) {
    int i = 0;
    while (i < l-> nroElem) {
        if (ch == l->A[i].chave) return i;
        else i++;
    }
    return -1; 
}

bool inserirPosicaoLista(LISTA *l, REGISTRO reg, int i){
    int j;
    if ((l->nroElem == MAX) || (i < 0) || (i > l->nroElem)){
        printf("Faio");
        return false;
    }
    for (j=l->nroElem; j > i; j--) l->A[j] = l->A[j-1];
    l->A[i]=reg;
    l->nroElem++;
    return true;
}

bool inserirFinalLista(LISTA *l, REGISTRO reg){
    if(l->nroElem == MAX) return false;
    l->A[l->nroElem]=reg;
    l->nroElem=l->nroElem+1;
    return true;
}

bool excluiElementoLista (TIPOCHAVE ch, LISTA *l ){
    int pos,j;
    pos = buscaSequencial(l,ch);
    if (pos == -1) return false;
    for (j = pos; j < l->nroElem; j++ ) l->A[j] = l->A[j+1];
    l->nroElem--;
    return true;
}

void reinicializaLista(LISTA *l){
    l->nroElem = 0;
}

int main() {
    LISTA l1;
    REGISTRO r1;
    inicializaLista(&l1);
    r1.chave = 21;
    inserirFinalLista(&l1,r1);
    r1.chave = 9;
    inserirFinalLista(&l1,r1);
    r1.chave = 55;
    inserirFinalLista(&l1,r1);
    imprimirLista(&l1);
    r1.chave=3;
    inserirPosicaoLista(&l1,r1,2);
    imprimirLista(&l1);
    excluiElementoLista(3,&l1);
    imprimirLista(&l1);   
    reinicializaLista(&l1);
    imprimirLista(&l1);
    return 0;
}