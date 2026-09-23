/**********************************************
* Exemplo de Pilha com estrutura estatica     *
* pilhaEstatica01.c                           *
***********************************************/
#include <stdio.h>
#include <string.h>
#define MAX 3

#define true 1
#define false 0

typedef int boolean; 
typedef int TIPOCHAVE;

typedef struct {
    TIPOCHAVE chave;
    char nome[60];
    float n1;
    float n2;
    // outros campos 
} REGISTRO;

typedef struct {
    REGISTRO A[MAX];
    int topo;
} PILHA;

void inicializaPilha (PILHA *p){
    p->topo = -1;
}

int tamanhoPilha(PILHA *p) {
    return p->topo+1;
}

void listarPilha(PILHA *p){
    printf("Pilha [\n");
    int i;
    for (i= p->topo;i>=0;i--){
        printf("chave %i Nome %s Nota 1 = %4.1f Nota 2 = %4.1f\n", 
            p->A[i].chave,p->A[i].nome,p->A[i].n1,p->A[i].n2);
    }
    printf("]\n");
}

boolean pushPilha(PILHA *p, REGISTRO reg){
    if (p->topo >=  MAX -1) return false;
    p->topo = p-> topo+1;
    p->A[p->topo] = reg;
    //printf("push(%d) posicao %d\n",reg.chave,p->topo+1);
    return true;
}

boolean popPilha (PILHA *p, REGISTRO *reg){
    if (p->topo == -1) return false;
    *reg = p->A[p->topo];
    p->topo = p-> topo -1;
    return true;
}

void reinicializaPilha(PILHA *p) {
    p->topo = -1;
}

int main() {
    PILHA p1;
    REGISTRO reg1;
    inicializaPilha (&p1);
    printf("tamanho da pilha %d \n",tamanhoPilha(&p1));
    listarPilha(&p1);
    reg1.chave = 10;
    strcpy(reg1.nome,"Xuxa Meneguel"); 
    reg1.n1 = 1.5; reg1.n2 = 10.0;
    if (pushPilha(&p1,reg1)) printf("Elemento %d inserido com sucesso\n",reg1.chave);
    else printf("erro pilha cheia!!\n");
    reg1.chave = 20;
    strcpy(reg1.nome,"Sergio Malandro"); 
    reg1.n1 = 7.5; reg1.n2 = 9.0;
    if (pushPilha(&p1,reg1)) printf("Elemento %d inserido com sucesso\n",reg1.chave);
    else printf("erro pilha cheia!!\n");
    reg1.chave = 30;
    strcpy(reg1.nome,"Silvio Santos"); 
    reg1.n1 = 10.0; reg1.n2 = 10.0;
    if (pushPilha(&p1,reg1)) printf("Elemento %d inserido com sucesso\n",reg1.chave);
    else printf("erro pilha cheia!!\n");
    reg1.chave = 40;
    strcpy(reg1.nome,"Cremilda Cremosa"); 
    reg1.n1 = 2.5; reg1.n2 = 1.0;
    if (pushPilha(&p1,reg1)) printf("Elemento %d inserido com sucesso\n",reg1.chave);
    else printf("erro pilha cheia!!\n");
    
    
    
    listarPilha(&p1);
    printf("tamanho da pilha %d \n",tamanhoPilha(&p1));
    if (popPilha(&p1,&reg1)) {
        printf("pop(%d,%s)\n",reg1.chave,reg1.nome);
    }
    listarPilha(&p1);
    reinicializaPilha(&p1);
    printf("tamanho da pilha %d \n",tamanhoPilha(&p1));
    listarPilha(&p1);
    return 0;
}