#include <iostream>
using namespace std;

void achaMaior(int v1, int v2, int *maior) {
  if (v1 > v2) {
    *maior = v1;
  } else {
    *maior = v2;
  }
}
int main() {
  int z1, z2, m;
  z1 = 9;
  z2 = 10;
  achaMaior(z1, z2, &m);
  cout << "Maior " << m << endl;
  achaMaior(8, 7, &m);
  cout << "Maior " << m << endl;
  return 0;
}