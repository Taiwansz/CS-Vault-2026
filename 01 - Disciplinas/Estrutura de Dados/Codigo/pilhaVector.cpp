//#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int main() {
  vector<int> pilha;
  cout << "inserindo 1" << endl;
  pilha.push_back(1);
  cout << "inserindo 2" << endl;
  pilha.push_back(2);
  cout << "inserindo 3" << endl;
  pilha.push_back(3);

  cout << "Pilha após as inserções:" << endl;
  cout << "Pilha [ ";
  for (auto elemento = pilha.rbegin(); elemento != pilha.rend(); elemento++) {
    cout << *elemento << " ";
  }
  cout << "] " << endl;
  cout << endl;
  cout << "inserindo 5" << endl;
  pilha.push_back(5);
  cout << "Pilha após a inserção:" << endl;
  cout << "Pilha [ ";

  for (int i = pilha.size() - 1; i >= 0; i--) {
    cout << pilha[i] << " ";
  }
  cout << "] " << endl;
  cout << endl;

  cout << "Pilha após a exclusão do número " << pilha.back() << endl;
  pilha.pop_back();
  cout << "pilha [ ";
  // for (const auto &elemento : pilha) {

  for (int i = pilha.size() - 1; i >= 0; i--) {
    cout << pilha[i] << " ";
  }
  cout << "] " << endl;
  cout << endl;

  pilha.clear();
  cout << "Pilha após a clear() :" << endl;
  cout << "pilha [ ";
  for (const auto &elemento : pilha) {
    cout << elemento << " ";
  }
  cout << "] " << endl;
  cout << endl;

  return 0;
}
