/**********************************************
 * Exemplo de Pilha com estrutura estatica     *
 * pilhaEstatica01.c                           *
 ***********************************************/
#include <iostream>
using namespace std;
#define MAX 3

typedef int TIPOCHAVE;

struct REGISTRO
{
    TIPOCHAVE chave;
    string nome;
    float n1;
    float n2;
    // outros campos
};

struct PILHA
{
    REGISTRO A[MAX];
    int topo;
};

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
    cout << "Pilha [\n";
    int i;
    for (i = p->topo; i >= 0; i--)
    {
        cout << "chave " << p->A[i].chave
             << " Nome " << p->A[i].nome
             << " Nota 1 = " << p->A[i].n1
             << " Nota 2 = " << p->A[i].n1 << endl;
    }
    cout << "]\n";
}

bool pushPilha(PILHA *p, REGISTRO reg)
{
    if (p->topo >= MAX - 1)
        return false;
    p->topo = p->topo + 1;
    p->A[p->topo] = reg;
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
    cout << "tamanho da pilha " << tamanhoPilha(&p1) << endl;
    listarPilha(&p1);
    reg1.chave = 10;
    reg1.nome = "Xuxa Meneguel";
    reg1.n1 = 1.5;
    reg1.n2 = 10.0;
    if (pushPilha(&p1, reg1))
        cout << "Elemento " << reg1.chave << " inserido com sucesso\n";
    else
        cout << "erro pilha cheia!!\n";
    reg1.chave = 20;
    reg1.nome = "Sergio Malandro";
    reg1.n1 = 7.5;
    reg1.n2 = 9.0;
    if (pushPilha(&p1, reg1))
        cout << "Elemento " << reg1.chave << "%d inserido com sucesso\n";
    else
        cout << "erro pilha cheia!!\n";
    reg1.chave = 30;
    reg1.nome = "Silvio Santos";
    reg1.n1 = 10.0;
    reg1.n2 = 10.0;
    if (pushPilha(&p1, reg1))
        cout << "Elemento " << reg1.chave << " inserido com sucesso\n";
    else
        cout << "erro pilha cheia!!\n";
    reg1.chave = 40;
    reg1.nome = "Cremilda Cremosa";
    reg1.n1 = 2.5;
    reg1.n2 = 1.0;
    if (pushPilha(&p1, reg1))
        cout << "Elemento " << reg1.chave << " inserido com sucesso\n";
    else
        cout << "erro pilha cheia!!\n";

    listarPilha(&p1);
    cout << "tamanho da pilha " << tamanhoPilha(&p1) << endl;
    if (popPilha(&p1, &reg1))
        cout << "pop(" << reg1.chave << "," << reg1.nome << ")\n";

    listarPilha(&p1);
    reinicializaPilha(&p1);
    cout << "tamanho da pilha " << tamanhoPilha(&p1) << endl;
    listarPilha(&p1);
    return 0;
}