/********************************************
* Exemplo de Fila com estrutura estatica    *
* filaEst01.c                               *
*********************************************/
#include <iostream>
using namespace std;
#define MAX 3

typedef int TIPOCHAVE;

struct REGISTRO {
    TIPOCHAVE chave;
};

struct FILA {
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
    FILA f1;
    REGISTRO r1;
    inicializarFila(&f1);
    cout << "Tamanho da fila " << tamanhoFila(&f1) << endl;
    r1.chave = 10;
    if(inserirElemFila(&f1,r1))
        cout << "Elemento " << r1.chave << " inserido com sucesso\n";
    else
        cout << "Erro, fila cheia\n";
    r1.chave = 20;
    if(inserirElemFila(&f1,r1))
        cout << "Elemento " << r1.chave << " inserido com sucesso\n";
    else
        cout << "Erro, fila cheia\n";
    
    r1.chave = 30;
    if(inserirElemFila(&f1,r1)) 
        cout << "Elemento " << r1.chave << " inserido com sucesso\n";
    else
        cout << "Erro, fila cheia\n";
    r1.chave = 40;
    if(inserirElemFila(&f1,r1)) 
        cout << "Elemento " << r1.chave << " inserido com sucesso\n";
    else
        cout << "Erro, fila cheia\n";
    listarFila(&f1);
    cout << "Tamanho da fila " << tamanhoFila(&f1) << endl;
    excluirElementoFila(&f1, &r1);
    listarFila(&f1);


    reinicializaFila(&f1);
    listarFila(&f1);
    return 0;
}