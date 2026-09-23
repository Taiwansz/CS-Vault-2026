#include <iostream>
using namespace std;

//#define MAX 5

class InsertionSort {
private:
  int MAX;

public:
  InsertionSort(int m) { MAX = m; }
  void insertionSort(int v[]) {
    int i, j, x;
    for (i = 1; i < MAX; i++) {
      x = v[i];
      j = i - 1;
      while ((j >= 0) && (x < v[j])) {
        v[j + 1] = v[j];
        j = j - 1;
      }
      v[j + 1] = x;
    }
  }

  void impVetor(int *v) {
    cout << "{ ";
    for (int i = 0; i < MAX; i++) {
      cout << v[i] << " ";
    }
    cout << "}\n\n";
  }
};
int main() {
  InsertionSort *is = new InsertionSort(5);
  int vetor[5] = {1, 9, 2, 4, 0};
  cout << "\nVetor Original\n";
  is->impVetor(vetor);
  is->insertionSort(vetor);
  cout << "\nVetor Ordenado (insercao)\n";
  is->impVetor(vetor);
  return 0;
}
