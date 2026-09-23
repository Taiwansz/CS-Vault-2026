#include <iostream>
using namespace std;

class Nada {
  int i;
};

int main() {
  Nada n;
  n.i = 2; // erro pois a variável i eh privada
  return 0;
}