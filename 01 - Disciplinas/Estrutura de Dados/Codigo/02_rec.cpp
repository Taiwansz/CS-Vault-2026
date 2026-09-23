#include <iostream>
using namespace std;

int fat(int n) {
  int f;
  if (n <= 0)
    cout << n << "! = 1\n";
  else {
    f = 1;
    for (int i = 1; i <= n; i++)
      f = f * i;
  }
  return f;
}
int main() {
  int n;
  cout << "Fatorial de ";
  cin >> n;

  cout << n << "! = " << fat(n) << "\n";
}
