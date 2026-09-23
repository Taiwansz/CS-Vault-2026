/********************************************
* Exemplo de Fila com estrutura estatica    *
* filaEst.c                                 *
*********************************************/
#include <iostream>
using namespace std;
#define MAX 50

typedef int TIPOCHAVE;

struct REGISTRO{
    TIPOCHAVE chave;
};

struct FILA{
    REGISTRO A[MAX];
    int inicio;
    int nroElem;
};

void inicializarFila(FILA *f){
    f->inicio = 0;
    f->nroElem = 0;
}

int tamanhoFila(FILA *f) {
    return f->nroElem;
}

void listarFila (FILA *f){
    cout << "Fila: [ ";
    int i = f->inicio;
    int temp;
    for(temp = 0; temp < f->nroElem; temp++){
        cout << f->A[i].chave << " ";
        i = (i + 1) % MAX; // se o i passar do máximo, i volta a zero
    }
    cout << "]" << endl;
}

bool inserirElemFila(FILA *f, REGISTRO reg) {
   if (f->nroElem >= MAX) return false;
   int posicao = (f->inicio + f->nroElem) % MAX;
   f->A[posicao] = reg;
   f->nroElem++;
   return true; 
}

bool excluirElementoFila(FILA *f, REGISTRO *reg){
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
    return 0;
}
