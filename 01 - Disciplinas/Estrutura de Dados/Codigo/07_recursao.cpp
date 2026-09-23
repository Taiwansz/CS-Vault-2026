#include <iostream>
using namespace std;

bool ehPalindromo(string str, int inicio, int fim) {
  if (inicio >= fim)
    return true;
  if (str[inicio] != str[fim])
    return false;
  return ehPalindromo(str, inicio + 1, fim - 1);
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
}