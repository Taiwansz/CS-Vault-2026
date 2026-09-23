#include <iostream>
using namespace std;
//#define MAX 5

class SelectionSort {
private:
  int MAX;

public:
  SelectionSort(int m) { MAX = m; }
  void selection_sort(int num[], int tam) {
    int i, j, min, aux;
    for (i = 0; i < (tam - 1); i++) {
      min = i;
      for (j = (i + 1); j < tam; j++) {
        if (num[j] < num[min])
          min = j;
      }
      if (i != min) {
        aux = num[i];
        num[i] = num[min];
        num[min] = aux;
      }
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
  int vetor[5] = {1, 9, 2, 4, 0};
  SelectionSort *ss = new SelectionSort(5);
  cout << "\nVetor Original\n";
  ss->impVetor(vetor);
  ss->selection_sort(vetor, 5);
  cout << "\nVetor Ordenado (metodo selecao)\n";
  ss->impVetor(vetor);
  return 0;
}
