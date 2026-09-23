#include <iostream>
using namespace std;

void imprimeFor(int n) {
  for (int i = n; i > 0; i--)
    cout << i << " ";
}

int main() {
  cout << "\nUsando for: ";
  imprimeFor(5);
  return 0;
}