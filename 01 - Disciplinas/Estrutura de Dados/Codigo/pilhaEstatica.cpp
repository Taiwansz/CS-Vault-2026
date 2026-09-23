/********************************************
* Exemplo de Pilha com estrutura estatica   *
* pilhaEstatica.cpp
**********************************************/
#include <iostream>
using namespace std;
#define MAX 3
typedef int TIPOCHAVE;

struct  REGISTRO {
    TIPOCHAVE chave;
    // outros campos 
};

struct PILHA{
    REGISTRO A[MAX];
    int topo;
};

void inicializaPilha (PILHA *p){
    p->topo = -1;
}

int tamanhoPilha(PILHA *p) {
    return p->topo+1;
}

void listarPilha(PILHA *p){
    cout << "Pilha [";
    int i;
    for (i= p->topo;i>=0;i--){
        cout << p->A[i].chave<<" ";
    }
    cout <<"]\n";
}

bool pushPilha(PILHA *p, REGISTRO reg){
    if (p->topo >=  MAX -1) return false;
    p->topo = p-> topo+1;
    p->A[p->topo] = reg;
    return true;
}

bool popPilha (PILHA *p, REGISTRO *reg){
    if (p->topo == -1) return false;
    *reg = p->A[p->topo];
    p->topo = p-> topo -1;
    return true;
}

void reinicializaPilha(PILHA *p) {
    p->topo = -1;
}

int main() {
    return 0;
}