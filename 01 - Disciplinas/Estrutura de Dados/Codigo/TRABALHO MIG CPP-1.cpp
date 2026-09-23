/* 
		INTEGRANTES
	Matheus Sousa dos Santos
	Felipe Guarnieri Pinete
	Ághata Victória de Oliveira Lobato
	Juan Pedro Souza de Oliveira 
*/ 

#include <iostream>
using namespace std;

typedef struct{
int cod, cpf, rg;
string nome, rua, bairro, cidade , estado, celular, email;
}type;

int main(){
  type ts;
  cout << "Cadastro. "<<endl<<endl;
  cout << "Digite o codigo: "<<endl;
  cin >> ts.cod;
  cout << "Digite seu nome: "<<endl;
  cin >> ts.nome;
  cout << "Digite a rua: "<<endl;
  cin >> ts.rua;
  cout << "Digite o bairro: "<<endl;
  cin >> ts.bairro;
  cout << "Digite a cidade: "<<endl;
  cin >> ts.cidade;
  cout << "Digite o estado: "<<endl;
  cin >> ts.estado;
  cout << "Digite seu numero de celular: "<<endl;
  cin >> ts.celular;
  cout << "Digite seu email: "<<endl;
  cin >> ts.email;
  cout << "Digite seu CPF: "<<endl;
  cin >> ts.cpf;
  cout << "Difite seu RG: "<<endl<<endl;
  cin >> ts.rg;

  cout << "Cadastro completo. Segue informações: "<<endl;
  cout << "Codigo: "<<ts.cod<<endl;
  cout << "Nome: "<<ts.nome<<endl;
  cout << "Rua: "<<ts.rua<<endl;
  cout << "Bairro: "<<ts.bairro<<endl;
  cout << "Cidade: "<<ts.cidade<<endl;
  cout << "Estado: "<<ts.estado<<endl;
  cout << "Celular: "<< ts.celular<<endl;
  cout << "Email: "<<ts.email<<endl; 
  cout << "CPF: "<<ts.cpf<<endl;
  cout << "RG: "<<ts.rg<<endl;
  
  return 0;
}
