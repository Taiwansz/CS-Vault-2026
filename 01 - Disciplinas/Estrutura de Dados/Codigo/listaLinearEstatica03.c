/*******************************************************************************************************
* Lista Linear Sequencial - caso de uso com interação com o usuario e novos campos: nome nota1 e nota2 *
* listaLinearEstatica03.c                                                                              *
********************************************************************************************************/
#include <stdio.h>
#include <string.h>
#define MAX 5
#define false 0
#define true 1

typedef int boolean;
typedef int TIPOCHAVE;

typedef struct {
    TIPOCHAVE chave;
    char nome[60];
    float nota1;
    float nota2;
} REGISTRO;

typedef struct {
    REGISTRO A[MAX];
    int nroElem;
} LISTA;

void inicializaLista (LISTA *l){
    l->nroElem = 0;
}

int tamanho (LISTA *l){
    return l->nroElem;
}

void imprimirLista(LISTA *l){
    int i;
    printf("\n\nLista  \n");
    printf("|-----------|------------------------------------------------------------|----|-----|\n");
    printf("|    RA     |                     Nome                                   | P1 |  P2 |\n");
    printf("|-----------|------------------------------------------------------------|----|-----|\n");
    for (i=0; i< l->nroElem; i++)
        printf("| %10i|%-60s|%4.1f|%4.1f |\n", 
        l->A[i].chave,l->A[i].nome,l->A[i].nota1,l->A[i].nota2);
    printf("|-----------|------------------------------------------------------------|----|-----|\n");
    printf("\n");
}

int buscaSequencial (LISTA *l, TIPOCHAVE ch) {
    int i = 0;
    while (i < l-> nroElem) {
        if (ch == l->A[i].chave) return i;
        else i++;
    }
    return -1; 
}

boolean inserirPosicaoLista(LISTA *l, REGISTRO reg, int i){
    int j;
    if ((l->nroElem == MAX) || (i < 0) || (i > l->nroElem)){
        printf("Faio");
        return false;
    }
    for (j=l->nroElem; j > i; j--) l->A[j] = l->A[j-1];
    l->A[i]=reg;
    l->nroElem++;
    return true;
}

boolean inserirFinalLista(LISTA *l, REGISTRO reg){
    if(l->nroElem == MAX) return false;
    l->A[l->nroElem]=reg;
    l->nroElem=l->nroElem+1;
    return true;
}

boolean excluiElementoLista (TIPOCHAVE ch, LISTA *l ){
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

void leStr(char *texto, int max ){
    fgets(texto,max,stdin);
    if (texto[0]=='\n')
        fgets(texto,max,stdin);
    int tam = strlen(texto)-1;
    if (texto[tam]=='\n')
        texto[tam] = '\0';
}

int main() {
    LISTA l1;
    REGISTRO reg1;
    inicializaLista(&l1);
    int op = 1;
    int pos;
    char cont;
    while (op){
        printf("\n\n");
        printf("1- Inserir no final\n");
        printf("2- Inserir na posição\n");
        printf("3- Excluir\n");
        printf("4- Listar\n");
        printf("5- Tamanho da lista\n");
        printf("6- Consulta\n");
        printf("7- Reinicializar\n");
        printf("0- Finalizar\n");
        printf("Opcao: ");
        scanf("%d",&op);
        switch (op){
            case 1 :cont = 'y';
                    while (cont == 'y' || cont == 'Y'){
                        printf("\n\nDigite o RA: "); 
                        scanf("%d",&reg1.chave);
                        printf("Nome: ");
                        leStr(reg1.nome,60);
                        printf("Nota 1 prova: ");
                        scanf("%f",&reg1.nota1);
                        printf("Nota 2 prova: ");
                        scanf("%f",&reg1.nota2);
                        if(!inserirFinalLista(&l1,reg1)) printf("\n\nErro, fila cheia\n\n");
                        printf("\n\nContinuar cadastrando (y/n)? ");
                        scanf(" %c",&cont);
                    }
                    break;
            case 2 :printf("\n\nDigite o RA: ");
                    scanf("%d",&reg1.chave);
                    printf("\nQual posição: ");
                    scanf("%d",&pos);
                    printf("Nome: ");
                    leStr(reg1.nome,60);
                    printf("Nota 1 prova: ");
                    scanf("%f",&reg1.nota1);
                    printf("Nota 2 prova: ");
                    scanf("%f",&reg1.nota2);
                    if(!inserirPosicaoLista(&l1,reg1,pos)) printf("\n\nErro, fila cheia ou posicao invalida\n\n");
                    break;
            case 3 :printf("\n\nDigite a chave a ser excluida: ");
                    scanf("%d",&reg1.chave);
                    if (excluiElementoLista(reg1.chave,&l1)) {
                        printf("\n\nElemento excluido: %d\n",reg1.chave);
                    } else {printf("\n\nErro, lista vazia ou elemento não localizado ");} 
                    break;
            case 4 :imprimirLista(&l1); getchar(); break;
            case 5 :printf("\n\ntamanho da lista %d \n",tamanho(&l1)); 
            break;
            case 6 :printf("\n\nDigite a chave: ");
                    scanf("%d",&reg1.chave);
                    pos= buscaSequencial(&l1,reg1.chave);
                    if (pos==-1) printf("\nElemento não localizado\n");
                    else printf("Elemento %d encontra-se na posição %d\n\n",reg1.chave,pos);
                    break;
            case 7 : printf("\n\nTodos os dados serão perdidos, continua (y/n) ? ");
                    scanf(" %c",&cont); if (cont=='y' || cont == 'Y') reinicializaLista(&l1);
                    break;
            case 0 :printf("\n\nHasta la vista, Baby\n\n");
                    break;
            default :printf("\nOpcao invalida\n");
        }
    }
    return 0;
}