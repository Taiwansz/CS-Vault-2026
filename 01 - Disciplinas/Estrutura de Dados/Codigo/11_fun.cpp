#include <iostream>
using namespace std;

void achaMaior() {
  int v1, v2, maior;
  cout << "Digite um numero: ";
  cin >> v1;
  cout << "Digite outro numero: ";
  cin >> v2;
  if (v1 > v2) {
    maior = v1;
  } else {
    maior = v2;
  }
  cout << "O maior numero " << maior << endl;
}
int main() {
  achaMaior();
  return 0;
}