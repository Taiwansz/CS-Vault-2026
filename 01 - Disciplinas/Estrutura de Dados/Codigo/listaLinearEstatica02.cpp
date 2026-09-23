/********************************************************************
* Lista Linear Sequencial - caso de uso com interacao com o usuario *
* listaLinearEstatica02.c                                           *
*********************************************************************/

#include <iostream>
using namespace std;
#define MAX 5

typedef int TIPOCHAVE;

struct REGISTRO {
    TIPOCHAVE chave;
    // outros campos
};

struct LISTA{
    REGISTRO A[MAX];
    int nroElem;
};

void inicializaLista (LISTA *l){
    l->nroElem = 0;
}

int tamanho (LISTA *l){
    return l->nroElem;
}

void imprimirLista(LISTA *l){
    int i;
    cout<<"Lista [ ";
    for (i=0; i< l->nroElem; i++)
        cout << l->A[i].chave<< " ";
    cout << " ]"<<endl;
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
    if ((l->nroElem == MAX) || (i < 0) || (i > l->nroElem))
        return false;
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
    REGISTRO reg1;
    inicializaLista(&l1);
    int op = 1;
    int pos;
    char cont;
    while (op){
        system("clear");
        cout <<"\n\n";
        cout <<"1- Inserir no final\n";
        cout <<"2- Inserir na posicao\n";
        cout <<"3- Excluir\n";
        cout <<"4- Listar\n";
        cout <<"5- Tamanho da lista\n";
        cout <<"6- Consulta\n";
        cout <<"7- Reinicializar\n";
        cout <<"0- Finalizar\n";
        cout <<"Opcao: ";
        cin >>op;
        switch (op){
            case 1 :cout <<"\n\nDigite o elemento a ser inserido : "; 
                    cin >>reg1.chave; //reg1.chave = 22;
                    if (!inserirFinalLista(&l1,reg1)) {
                        cout <<"\n\nErro lista cheia\n\n";
                        getchar(); getchar();
                    }
                    break;
            case 2 :cout <<"\n\nDigite o elemento a ser inserido : ";
                    cin >>reg1.chave;
                    cout <<"\nQual posicao: ";
                    cin >> pos;
                    if(!inserirPosicaoLista(&l1,reg1,pos)){
                        cout <<"\n\nErro, lista cheia ou posicao invalida\n\n";
                        getchar(); getchar();
                    }
                    break;
            case 3 :cout <<"Digite a chave a ser excluida: ";
                    cin >> reg1.chave;
                    if (excluiElementoLista(reg1.chave,&l1)) {
                        cout <<"\n\nElemento excluido: "<<reg1.chave<<endl;
                        getchar(); getchar();
                    } 
                    else {cout <<"\n\nErro, lista vazia"; 
					        cout <<" ou elemento nao localizado ";
                            getchar(); getchar();
                    } 
                    break;
            case 4 :imprimirLista(&l1); 
                    getchar(); getchar();
                    break;
            case 5 :cout <<"\n\ntamanho da lista "<<tamanho(&l1)<<endl; 
                    getchar(); getchar();
                    break;
            case 6 :cout <<"\n\nDigite a chave: ";
                    cin >> reg1.chave;
                    pos= buscaSequencial(&l1,reg1.chave);
                    if (pos==-1) {
                        cout <<"\nElemento nao localizado\n";
                        getchar(); getchar();
                    }
                    else {
                        cout <<"Elemento "<< reg1.chave<<" encontra-se na posicao "<<
                        pos<<endl<<endl;
                        getchar(); getchar();
                    }
                    break;
            case 7 : cout <<"Todos os dados serao perdidos, continua (y/n) ? ";
                    cin >> cont;
					if (cont=='y' || cont == 'Y') reinicializaLista(&l1);
                    break;
            case 0 :cout <<"\n\nHasta la vista, Baby\n\n";
                    break;
            default :cout <<"\nOpcao invalida\n"; 
        }
    }
    return 0;
}
