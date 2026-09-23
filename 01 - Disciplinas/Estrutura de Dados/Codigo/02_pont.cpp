#include <iostream>
using namespace std;

void calculos(float n1 = 0, float n2 = 0, float *media = nullptr,
              float *maior = nullptr) {
  *media = (n1 + n2) / 2.0;
  if (n1 > n2)
    *maior = n1;
  else
    *maior = n2;
}

int main() {
  int nota1 = 9, nota2 = 2;
  float med, maiornota;
  calculos(nota1, nota2, &med, &maiornota);
  cout << "Media " << med << " Nota da sub sera somada com " << maiornota
       << endl;
  return 0;
}