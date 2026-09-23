/*******************************************
 * Calcula o tamanho de uma String
 ********************************************/
#include <iostream>
using namespace std;

int tamStr(char *s) {
  int i = 0;
  while (s[i] != '\0')
    i++;
  return i;
}

int main() {
  string texto;
  cout << "Digite uma texto: ";
  getline(cin, texto);
  cout << "\ntamanho da string = " << tamStr((char *)texto.c_str()) << endl;
  return 0;
}