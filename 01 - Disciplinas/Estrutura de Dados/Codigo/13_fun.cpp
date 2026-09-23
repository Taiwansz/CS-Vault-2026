#include <iostream>
using namespace std;

void achaMaior(int v1, int v2) {
  int maior;

  if (v1 > v2) {
    maior = v1;
  } else {
    maior = v2;
  }
  cout << "O maior numero " << maior << endl;
}
int main() {
  int z1, z2;
  z1 = 9;
  z2 = 10;
  achaMaior(z1, z2);
  achaMaior(8, 7);
  return 0;
}