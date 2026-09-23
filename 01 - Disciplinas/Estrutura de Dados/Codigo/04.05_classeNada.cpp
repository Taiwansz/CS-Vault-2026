#include <iostream>
using namespace std;

class Nada {
private:
  int i;

public:
  ~Nada() { cout << "destroi" << endl; }
  void setI(int i) { this->i = i; }
  int getI() { return i; }
};

int main() {
  Nada *n;
  n = new Nada();
  n->setI(2);
  cout << "O atributo i tem valor " << n->getI() << endl;
  delete (n);
  int x;
  cin >> x;
  return 0;
}