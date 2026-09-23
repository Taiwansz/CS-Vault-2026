#include <iostream>
using namespace std;

class Nada {
private:
  int i;

public:
  void setI(int ii) { i = ii; }
  int getI() { return i; }
};

int main() {
  Nada *n;
  n = new Nada();
  n->setI(2);
  cout << "O atributo i tem valor " << n->getI() << endl;
  return 0;
}