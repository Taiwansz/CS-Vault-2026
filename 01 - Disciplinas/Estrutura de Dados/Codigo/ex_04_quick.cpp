#include <iostream>
using namespace std;

//#define MAX 10

class QuickSort {
private:
  int max;

public:
  QuickSort(int m) { max = m; }
  void quicksort(int values[], int began, int end) {
    int i, j, pivo, aux;
    i = began;
    j = end - 1;
    pivo = values[(began + end) / 2];
    while (i <= j) {
      while (values[i] < pivo && i < end) {
        i++;
      }
      while (values[j] > pivo && j > began) {
        j--;
      }
      if (i <= j) {
        aux = values[i];
        values[i] = values[j];
        values[j] = aux;
        i++;
        j--;
      }
    }
    if (j > began)
      quicksort(values, began, j + 1);
    if (i < end)
      quicksort(values, i, end);
  }

  void impVetor(int *v) {
    cout << "{ ";
    for (int i = 0; i < max; i++) {
      cout << v[i] << " ";
    }
    cout << "}\n\n";
  }
};

int main() {
  int vetor[] = {5, 8, 1, 2, 7, 3, 6, 9, 4, 10};
  QuickSort *qs = new QuickSort(10);
  cout << endl << endl << "Vetor Original" << endl;
  qs->impVetor(vetor);
  cout << "\n\nVetor Ordenado (quicksort)\n";
  qs->quicksort(vetor, 0, 10);
  qs->impVetor(vetor);
  return 0;
}