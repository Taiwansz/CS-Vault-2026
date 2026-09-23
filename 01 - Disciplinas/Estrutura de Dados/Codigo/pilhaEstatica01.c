/**********************************************
 * Exemplo de Pilha com estrutura estatica     *
 * pilhaEstatica01.c                           *
 ***********************************************/
#include <stdio.h>
#define MAX 3

#define true 1
#define false 0

typedef int bool;
typedef int TIPOCHAVE;

typedef struct
{
    TIPOCHAVE chave;
    // outros campos
} REGISTRO;

typedef struct
{
    REGISTRO A[MAX];
    int topo;
} PILHA;

void inicializaPilha(PILHA *p)
{
    p->topo = -1;
}

int tamanhoPilha(PILHA *p)
{
    return p->topo + 1;
}

void listarPilha(PILHA *p)
{
    printf("Pilha [");
    int i;
    for (i = p->topo; i >= 0; i--)
    {
        printf("%i ", p->A[i].chave);
    }
    printf("]\n");
}

bool pushPilha(PILHA *p, REGISTRO reg)
{
    if (p->topo >= MAX - 1)
        return false;
    // p->topo = p-> topo+1;
    p->A[++p->topo] = reg;
    // printf("push(%d) posicao %d\n",reg.chave,p->topo+1);
    return true;
}

bool popPilha(PILHA *p, REGISTRO *reg)
{
    if (p->topo == -1)
        return false;
    *reg = p->A[p->topo];
    p->topo = p->topo - 1;
    return true;
}

void reinicializaPilha(PILHA *p)
{
    p->topo = -1;
}

int main()
{
    PILHA p1;
    REGISTRO reg1;
    inicializaPilha(&p1);
    printf("tamanho da pilha %d \n", tamanhoPilha(&p1));
    listarPilha(&p1);
    reg1.chave = 10;
    if (pushPilha(&p1, reg1))
        printf("Elemento %d inserido com sucesso\n", reg1.chave);
    else
        printf("erro pilha cheia!!\n");
    reg1.chave = 20;
    if (pushPilha(&p1, reg1))
        printf("Elemento %d inserido com sucesso\n", reg1.chave);
    else
        printf("erro pilha cheia!!\n");
    reg1.chave = 30;
    if (pushPilha(&p1, reg1))
        printf("Elemento %d inserido com sucesso\n", reg1.chave);
    else
        printf("erro pilha cheia!!\n");
    listarPilha(&p1);
    printf("tamanho da pilha %d \n", tamanhoPilha(&p1));
    reg1.chave = 40;
    if (pushPilha(&p1, reg1))
        printf("Elemento %d inserido com sucesso\n", reg1.chave);
    else
        printf("erro pilha cheia!!\n");
    listarPilha(&p1);
    printf("tamanho da pilha %d \n", tamanhoPilha(&p1));
    if (popPilha(&p1, &reg1))
    {
        printf("pop(%d)\n", reg1.chave);
    }
    listarPilha(&p1);
    reinicializaPilha(&p1);
    printf("tamanho da pilha %d \n", tamanhoPilha(&p1));
    listarPilha(&p1);
    return 0;
}