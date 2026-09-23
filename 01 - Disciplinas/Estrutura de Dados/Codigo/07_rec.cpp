#include <iostream>
using namespace std;

bool ehPalindromo(string str, int inicio, int fim) {
  bool pali = true;
  int f = fim;
  for (int i = 0; i < f; i++) {
    if (str[inicio] != str[fim])
      pali = false;
    inicio++;
    fim--;
  }
  return pali;
}

int main() {
  string txt;
  txt = "socorram me subino onibus em marrocos";
  cout << txt << endl;
  for (int i = txt.length(); i >= 0; i--)
    cout << txt[i];

  if (ehPalindromo(txt, 0, txt.length() - 1))
    cout << " eh palindromo" << endl;
  else
    cout << " nao eh palindromo" << endl;
  getline(cin, txt);
  cout << txt;
  if (ehPalindromo(txt, 0, txt.length() - 1))
    cout << " eh palindromo" << endl;
  else
    cout << " nao eh palindromo" << endl;
}