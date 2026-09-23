#include <iostream>
using namespace std;

int achaMaior(int v1, int v2) {
  int maior;

  if (v1 > v2) {
    maior = v1;
  } else {
    maior = v2;
  }
  return maior;
}
int main() {
  int z1, z2;
  z1 = 9;
  z2 = 10;
  cout << "Maior " << achaMaior(z1, z2) << endl;
  cout << "Maior " << achaMaior(8, 7) << endl;
  return 0;
}