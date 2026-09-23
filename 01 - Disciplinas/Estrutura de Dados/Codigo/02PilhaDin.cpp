#include <iostream>
using namespace std;
typedef int TIPOCHAVE;

struct REGISTRO{
	TIPOCHAVE chave;
	// outros campos aqui
};

struct ELEMENTO {
	REGISTRO reg;
	ELEMENTO *prox;
};

typedef ELEMENTO *PONT;

struct PILHA {
	PONT topo;
};

void inicializaPilha (PILHA *p){
	p->topo = nullptr;
}

int tamanhoPilha(PILHA *p){
	PONT ender = p->topo;
	int tam = 0;
	while (ender != nullptr){
		tam++;
		ender = ender->prox;
	}
	return tam;
}

bool estaVazia(PILHA *p){
	if (p->topo == nullptr) return true;
	else                    return false;
}

void listaPilha(PILHA *p){
	PONT ender = p->topo;
	cout << "\nPilha [";
	while (ender != nullptr){
		cout << ender->reg.chave<<" ";
		ender = ender->prox;
	}
	cout << "]\n";
}

bool pushElemPilha(PILHA *p, REGISTRO reg){
	PONT novo = new ELEMENTO(); //(PONT)malloc(sizeof(ELEMENTO));
	cout << "Posicao "<<novo<<endl;
	novo->reg = reg;
	novo->prox = p->topo;
	p->topo = novo;
	return true;
}


bool popElemPilha(PILHA *p, REGISTRO *reg){
	if (p->topo  == nullptr) return false;
	*reg = p->topo->reg;
	PONT apagar = p->topo;
	p->topo = p->topo->prox;
	delete (apagar);
	return true;
}

void reinicializaPilha(PILHA *p){
	PONT apagar;
	PONT posicao = p->topo;
	while (posicao != nullptr){
		apagar = posicao;
		posicao = posicao->prox;
		delete (apagar);
	}
	p->topo = nullptr;
}

int main() {
	PILHA p;
	REGISTRO r;
	inicializaPilha(&p);
	r.chave = 0;
	pushElemPilha(&p, r);
	r.chave = 20;
	pushElemPilha(&p, r);
	r.chave = 30;
	pushElemPilha(&p, r);
	listaPilha(&p);
	if (popElemPilha(&p,&r)) cout <<"removido"<<r.chave<<endl;
	cout <<"Tamanho da pilha "<<tamanhoPilha(&p)<<endl;
	listaPilha(&p);
	reinicializaPilha(&p);
	listaPilha(&p);
	return 0;
}