/***********************************************
 * Calcula x elevado a y
 * *********************************************/

#include <iostream>
using namespace std;

int potencia(int base, int expo) {
  if (expo == 0)
    return 1;
  /*if (expo == 1)
    return base;*/
  return base * potencia(base, expo - 1);
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