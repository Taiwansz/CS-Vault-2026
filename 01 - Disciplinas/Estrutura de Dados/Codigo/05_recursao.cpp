/*******************************************
 * Calcula o tamanho de uma String
 ********************************************/
#include <iostream>
using namespace std;

int tamStr(char *s) {
  if ((s[0] == '\0') || (s[0] == '\n'))
    return 0;
  return 1 + tamStr(&s[1]);
}

int main() {
  string texto;
  cout << "Digite uma texto: ";
  getline(cin, texto);
  cout << "\ntamanho da string = " << tamStr((char *)texto.c_str()) << endl;
  return 0;
}