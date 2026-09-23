#include <iostream>
using namespace std;

int potencia(int base, int expo) {
  int res = 1;
  for (int i = 0; i < expo; i++)
    res *= base;
  return res;
}

int main() {
  int x, y;
  cout << "\nx elevado a y\n\n";
  cout << "Digite x: ";
  cin >> x;
  cout << "Digite y: ";
  cin >> y;
  cout << "\n" << x << " elevado " << y << " = " << potencia(x, y) << "\n";
}