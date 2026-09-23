/**********************************************
 * Exemplo de Pilha com estrutura estatica     *
 * pilhaEstatica01.c                           *
 ***********************************************/
#include <iostream>
#include <iomanip>
using namespace std;
#define MAX 5
typedef int TIPOCHAVE;
struct  REGISTRO
{
    TIPOCHAVE chave;
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
    cout << endl
         << endl
         << "|    |"<<endl;

    int i;
    for (i = p->topo; i >= 0; i--)
    {
        cout << "| "<<setw(2)<<p->A[i].chave <<" |"<< endl;
    }
    cout << "+----+\n";
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

void initGame(PILHA *pA, PILHA *pB, PILHA *pC)
{
    REGISTRO r1, r2;
    inicializaPilha(pA);
    inicializaPilha(pB);
    inicializaPilha(pC);

    r1.chave = 1;
    r2.chave = 2;

    // pote A
    pushPilha(pA, r1);
    pushPilha(pA, r1);

    // pote B
    pushPilha(pB, r2);
    pushPilha(pB, r2);
    pushPilha(pB, r2);

    // pote C
    pushPilha(pC, r1);
    pushPilha(pC, r1);
    pushPilha(pC, r2);
}

bool move(PILHA *p1, PILHA *p2){
    REGISTRO r1;
    bool mov1, mov2;
    mov1 = popPilha(p1, &r1);
    mov2 = pushPilha(p2, r1);
    if (!(mov1 && mov2))
    {
        cout << "\n\n-----------------------\n";
        cout << "Erro! pilha cheia     ";
        cout << "\n-----------------------\n";
    }
    else
        cout << "\n\n-----------------------\n";
}

void impGame(PILHA *pA, PILHA *pB, PILHA *pC)
{
    cout << "\nPilha A\n";
    listarPilha(pA);
    cout << "\nPilha B\n";
    listarPilha(pB);
    cout << "\nPilha C\n";
    listarPilha(pC);
}

void pausa(){
   cout << endl<<"Pressione ENTER tecla para continuar";
   getchar();
}

int main()
{
    PILHA potA, potB, potC;
    initGame(&potA, &potB, &potC);
    impGame(&potA, &potB, &potC);
    pausa();

    move(&potC, &potB);
    impGame(&potA, &potB, &potC);
    pausa();

    move(&potC, &potA);
    impGame(&potA, &potB, &potC);
    pausa();

    move(&potC, &potA);
    impGame(&potA, &potB, &potC);

    return 0;
}