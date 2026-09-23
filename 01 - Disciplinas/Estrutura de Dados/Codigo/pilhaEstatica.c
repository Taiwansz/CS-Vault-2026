/********************************************
 * Exemplo de Pilha com estrutura estatica   *
 * pilhaEstatica.c
 **********************************************/
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
    p->topo = p->topo + 1;
    p->A[p->topo] = reg;
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
    return 0;
}