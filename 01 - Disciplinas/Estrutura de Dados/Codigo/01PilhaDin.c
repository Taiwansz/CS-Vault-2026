#include <stdio.h>
#include <malloc.h>

typedef int TIPOCHAVE;
typedef int bool;

#define true 1
#define false 0

typedef struct {
	TIPOCHAVE chave;
	// outros campos aqui
}REGISTRO;

typedef struct aux {
	REGISTRO reg;
	struct aux *prox;
}ELEMENTO;

typedef ELEMENTO *PONT;

typedef struct {
	PONT topo;
}PILHA;

void inicializaPilha (PILHA *p){
	p->topo = NULL;
}

int tamanhoPilha(PILHA *p){
	PONT ender = p->topo;
	int tam = 0;
	while (ender != NULL){
		tam++;
		ender = ender->prox;
	}
	return tam;
}

bool estaVazia(PILHA *p){
	if (p->topo == NULL) return true;
	else                 return false;
}

void listaPilha(PILHA *p){
	PONT ender = p->topo;
	printf("\nPilha [");
	while (ender != NULL){
		printf("%d ", ender->reg.chave);
		ender = ender->prox;
	}
	printf("]\n");
}

bool pushElemPilha(PILHA *p, REGISTRO reg){
	PONT novo = (PONT)malloc(sizeof(ELEMENTO));
	novo->reg = reg;
	novo->prox = p->topo;
	p->topo = novo;
	return true;
}


bool popElemPilha(PILHA *p, REGISTRO *reg){
	if (p->topo  == NULL) return false;
	*reg = p->topo->reg;
	PONT apagar = p->topo;
	p->topo = p->topo->prox;
	free (apagar);
	return true;
}

void reinicializaPilha(PILHA *p){
	PONT apagar;
	PONT posicao = p->topo;
	while (posicao != NULL){
		apagar = posicao;
		posicao = posicao->prox;
		free (apagar);
	}
	p->topo = NULL;
}

int main() {
	PILHA p;
	REGISTRO r;
	inicializaPilha(&p);
	r.chave = 10;
	pushElemPilha(&p, r);
	r.chave = 20;
	pushElemPilha(&p, r);
	r.chave = 30;
	pushElemPilha(&p, r);
	listaPilha(&p);
	if (popElemPilha(&p,&r)) printf("Elemento %d removido\n",r.chave);
	printf("Tamanho da pilha %d \n",tamanhoPilha(&p));
	listaPilha(&p);
	reinicializaPilha(&p);
	listaPilha(&p);
	return 0;
}