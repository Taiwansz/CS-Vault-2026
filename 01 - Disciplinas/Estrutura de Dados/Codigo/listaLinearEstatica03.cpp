/*******************************************************************************************************
* Lista Linear Sequencial - caso de uso com interação com o usuario e novos campos: nome nota1 e nota2 *
* listaLinearEstatica03.cpp                                                                            *
********************************************************************************************************/
#include <iostream>
#include <iomanip>
#include <string>
using namespace std;
#define MAX 5

typedef int TIPOCHAVE;

struct REGISTRO {
    TIPOCHAVE chave;
    string nome;
    float nota1;
    float nota2;
};

struct LISTA {
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
    cout <<"\n\nLista  \n";
    cout <<"|-----------|------------------------------------------------------------|----|-----|\n";
    cout <<"|    RA     |                     Nome                                   | P1 |  P2 |\n";
    cout <<"|-----------|------------------------------------------------------------|----|-----|\n";
    for (i=0; i< l->nroElem; i++)
        cout << "| " << setw(10) << l->A[i].chave << "|" << setw(60) << left << l->A[i].nome << "|" <<right << setw(4) << fixed << setprecision(1) << l->A[i].nota1 << "|" << setw(4) << fixed << setprecision(1) << l->A[i].nota2 <<" |" << endl;
    cout <<"|-----------|------------------------------------------------------------|----|-----|\n";
    cout <<"\n";
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
    if ((l->nroElem == MAX) || (i < 0) || (i > l->nroElem)){
        cout <<"Faio"<<endl;
        return false;
    }
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

string leStr(){
    string txt;
    getline(cin,txt);
    if (txt[0]=='\0')
        getline(cin, txt);
    return txt;
}

int main() {
    LISTA l1;
    REGISTRO reg1;
    inicializaLista(&l1);
    int op = 1;
    int pos;
    char cont;
    while (op){
        cout <<"\n\n";
        cout <<"1- Inserir no final\n";
        cout <<"2- Inserir na posição\n";
        cout <<"3- Excluir\n";
        cout <<"4- Listar\n";
        cout <<"5- Tamanho da lista\n";
        cout <<"6- Consulta\n";
        cout <<"7- Reinicializar\n";
        cout <<"0- Finalizar\n";
        cout <<"Opcao: ";
        cin >> op;
        switch (op){
            case 1 :cont = 'y';
                    while (cont == 'y' || cont == 'Y'){
                        cout <<"\n\nDigite o RA: "; 
                        cin >>reg1.chave;
                        cout <<"Nome: ";
                        reg1.nome = leStr();
                        cout << "Nota 1 prova: ";
                        cin >> reg1.nota1;
                        cout <<"Nota 2 prova: ";
                        cin >> reg1.nota2;
                        if(!inserirFinalLista(&l1,reg1)) cout <<"\n\nErro, fila cheia\n\n";
                        cout <<"\n\nContinuar cadastrando (y/n)? ";
                        cin >> cont;
                    }
                    break;
            case 2 :cout <<"\n\nDigite o RA: ";
                    cin >> reg1.chave;
                    cout <<"\nQual posição: ";
                    cin >> pos;
                    cout <<"Nome: ";
                    reg1.nome = leStr();
                    cout <<"Nota 1 prova: ";
                    cin >> reg1.nota1;
                    cout <<"Nota 2 prova: ";
                    cin >> reg1.nota2;
                    if(!inserirPosicaoLista(&l1,reg1,pos))
                     cout <<"\n\nErro, fila cheia ou posicao invalida\n\n";
                    break;
            case 3 :cout <<"\n\nDigite a chave a ser excluida: ";
                    cin >> reg1.chave;
                    if (excluiElementoLista(reg1.chave,&l1)) {
                        cout <<"\n\nElemento "<< reg1.chave << " excluido"<<endl;
                    } else {cout <<"\n\nErro, lista vazia ou elemento não localizado ";}
                    getchar();
                    getchar();
                    break;
            case 4 :imprimirLista(&l1);
                getchar();
                getchar();
                break;
            case 5 :cout <<"\n\ntamanho da lista "<<tamanho(&l1)<<endl; 
            getchar();getchar();
            break;
            case 6 :cout <<"\n\nDigite a chave: ";
                    cin >> reg1.chave;
                    pos= buscaSequencial(&l1,reg1.chave);
                    if (pos==-1) cout <<"\nElemento não localizado\n";
                    else cout <<"Elemento "<< reg1.chave<<" encontra-se na posição "<<pos<<endl<<endl;
                    getchar();
                    getchar();
                    break;
            case 7 : cout <<"\n\nTodos os dados serão perdidos, continua (y/n) ? ";
                    cin >> cont; if (cont=='y' || cont == 'Y') reinicializaLista(&l1);
                    break;
            case 0 :cout <<"\n\nHasta la vista, Baby\n\n";
                    break;
            default :cout <<"\nOpcao invalida\n";
        }
    }
    return 0;
}