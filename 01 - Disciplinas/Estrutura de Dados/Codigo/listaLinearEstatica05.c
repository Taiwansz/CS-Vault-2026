/*******************************************************************************************************
* Lista Linear Sequencial - caso de uso com interação com o usuario e novos campos: nome cpf e idade   *
* a funcao cpfValido() verifica se o cpf é valido ou não, deve ser usado com a mascara xxx.xxx.xxx-xx  *
* listaLinearEstatica05.c                                                                              *
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
    int idade;
    char cpf[15];
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
    printf("|-----------|------------------------------------------------------------|--- -|---------------|\n");
    printf("|    RA     |                     Nome                                   |idade|      CPF      |\n");
    printf("|-----------|------------------------------------------------------------|---- |---------------|\n");
    for (i=0; i< l->nroElem; i++)
        printf("| %10i|%-60s|%5d|%15s|\n", 
        l->A[i].chave,l->A[i].nome,l->A[i].idade,l->A[i].cpf);
    printf("|-----------|------------------------------------------------------------|---- |---------------|\n");
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

boolean cpfValido(char c[15]){
    int tam = strlen(c);
    if (tam != 14) return false;
    int t;
    int resto1,resto2,soma=0,i,j=-1;
    char p1[10];
    int r1,r2;
    for (i=0;i<10;i++){
        if (i== 3) j++;
        if (i== 6) j++;
        if (i==9) j++;
        j++;
        p1[i] = c[j];
    }
    r1 = (c[12]-'0') *10;
    r1 = r1 + (c[13]-'0');
    soma = 0;
    for (i=0;i<9;i++){
        t=p1[i]-'0'; // subtrair o valor asc de '0' para retornar o numero
        j = i + 1;
        soma = soma + (t*j);
    }
    resto1 = soma % 11;
    if (resto1 == 10) resto1 = 0;
    soma = 0;
    for (i=1;i<10;i++){
        t=p1[i]-'0';
        soma = soma + (t*i);
    }
    resto2 = soma % 11;
    if (resto2 == 10) resto2 = 0;
    r2 = resto1*10+resto2;
    if(r1==r2) return true;
    else return false;
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
                        printf("Idade: ");
                        scanf("%d",&reg1.idade);
                        printf("CPF: ");
                        leStr(reg1.cpf,15);
                        while(!cpfValido(reg1.cpf)) {
                            printf("CPF invalida\n");
                            printf("CPF: ");
                            leStr(reg1.cpf,15);
                        }    
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
                    printf("Idade: ");
                    scanf("%d",&reg1.idade);
                    printf("CPF: ");
                    leStr(reg1.cpf,15);
                    while(!cpfValido(reg1.cpf)) {
                            printf("CPF invalida\n");
                            printf("CPF: ");
                            leStr(reg1.cpf,15);
                        }
                    if(!inserirPosicaoLista(&l1,reg1,pos)) printf("\n\nErro, fila cheia ou posicao invalida\n\n");
                    break;
            case 3 :printf("\n\nDigite a chave a ser excluida: ");
                    scanf("%d",&reg1.chave);
                    if (excluiElementoLista(reg1.chave,&l1)) {
                        printf("\n\nElemento excluido: %d\n",reg1.chave);
                    } else {printf("\n\nErro, lista vazia ou elemento não localizado ");} 
                    break;
            case 4 :imprimirLista(&l1);  break;
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