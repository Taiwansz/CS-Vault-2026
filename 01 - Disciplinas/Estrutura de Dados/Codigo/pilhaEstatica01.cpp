/**********************************************
 * Exemplo de Pilha com estrutura estatica     *
 * pilhaEstatica01.cpp                         *
 ***********************************************/
#include <iostream>
using namespace std;
#define MAX 3
typedef int TIPOCHAVE;

struct REGISTRO{
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

int main()
{
    PILHA p1;
    REGISTRO reg1;
    inicializaPilha(&p1);
    cout << "tamanho da pilha " << tamanhoPilha(&p1) << endl;
    listarPilha(&p1);
    reg1.chave = 10;
    if (pushPilha(&p1, reg1))
        cout << "Elemento %d inserido com sucesso " << reg1.chave << endl;
    else
        cout << "erro pilha cheia!!\n";
    reg1.chave = 20;
    if (pushPilha(&p1, reg1))
        cout << "Elemento " << reg1.chave << " inserido com sucesso\n";
    else
        cout << "erro pilha cheia!!\n";
    reg1.chave = 30;
    if (pushPilha(&p1, reg1))
        cout << "Elemento "<<reg1.chave<<" inserido com sucesso\n" ;
    else
        cout << "erro pilha cheia!!\n";
    listarPilha(&p1);
    cout << "tamanho da pilha " << tamanhoPilha(&p1) << endl;
    reg1.chave = 40;
    if (pushPilha(&p1, reg1))
        cout << "Elemento " << reg1.chave << " inserido com sucesso\n";
    else
        cout << "erro pilha cheia!!\n";
    listarPilha(&p1);
    cout << "tamanho da pilha " << tamanhoPilha(&p1) << endl;
    if (popPilha(&p1, &reg1))
    {
        cout << "pop(" << reg1.chave << ")" << endl;
    }
    listarPilha(&p1);
    reinicializaPilha(&p1);
    cout << "tamanho da pilha " << tamanhoPilha(&p1) << endl;
    listarPilha(&p1);
    return 0;
}