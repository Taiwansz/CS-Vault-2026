using namespace std;
#include <algorithm>
#include <iostream>
#include <vector>

int main() {
  vector<int> lista;
  lista.push_back(21);
  lista.push_back(9);
  lista.push_back(55);
  lista.insert(lista.begin() + 1, 3);
  cout << "Vetor após as inserções:" << endl;
  cout << "Lista [ ";
  for (const auto &elemento : lista) {
    cout << elemento << " ";
  }
  cout << "] " << endl;
  cout << endl;
  lista.erase(find(lista.begin(), lista.end(), 3));
  cout << "Vetor após a exclusão do número 3:" << endl;
  cout << "Lista [ ";
  for (const auto &elemento : lista) {
    cout << elemento << " ";
  }
  cout << "] " << endl;
  cout << endl;

  lista.clear();
  cout << "Vetor após a clear() :" << endl;
  cout << "Lista [ ";
  for (const auto &elemento : lista) {
    cout << elemento << " ";
  }
  cout << "] " << endl;
  cout << endl;

  return 0;
}
