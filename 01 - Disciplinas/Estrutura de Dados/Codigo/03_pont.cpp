#include <iostream>
using namespace std;

int main() {
  int num[3] = {2, 8, 9};
  int *numeros;
  numeros = new int[3];
  int i;
  numeros[0] = -1;
  numeros[1] = -2;
  numeros[2] = -3;
  cout << "Listagem do vetor num " << endl;
  for (i = 0; i < 3; i++)
    cout << num[i] << ", ";
  cout << "\b\b " << endl;
  cout << "Listagem do vetor numeros " << endl;
  for (i = 0; i < 3; i++)
    cout << numeros[i] << ", ";
  cout << "\b\b " << endl;
  cout << "Listagem do vetor numeros usando ponteiro*(numeros+1) " << endl;
  for (i = 0; i < 3; i++) {
    cout << *(numeros + i) << ", ";
  }

  cout << endl << "Somando um ao vetor numeros (ponteiro++)" << endl;
  cout << "numeros [0]: " << numeros[0] << endl;
  numeros++;
  cout << "numeros [0]: " << numeros[0] << endl;
  numeros++;
  cout << "numeros [0]: " << numeros[0] << endl;
  return 0;
}