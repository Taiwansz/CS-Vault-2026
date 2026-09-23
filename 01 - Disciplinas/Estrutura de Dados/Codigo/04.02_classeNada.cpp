#include <iostream>
using namespace std;

class Nada {
public:
  int i;
};

int main() {
  Nada n;
  n.i = 2;
  cout << "O atributo i tem valor " << n.i << endl;
  return 0;
}