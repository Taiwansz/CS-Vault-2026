#include <iostream>
using namespace std;
#define MAXX 5

class HeapSort {
private:
  int max;

public:
  HeapSort(int m) { max = m; }

  void impVetor(int *v) {
    cout << "{ ";
    for (int i = 0; i < max; i++) {
      cout << v[i] << " ";
    }
    cout << "}\n\n";
  }

  void heapsort(int a[], int n) {
    int i = n / 2, pai, filho, t;
    while (true) {
      if (i > 0) {
        i--;
        t = a[i];
      } else {
        n--;
        if (n <= 0)
          return;
        t = a[n];
        a[n] = a[0];
      }
      pai = i;
      filho = i * 2 + 1;
      while (filho < n) {
        if ((filho + 1 < n) && (a[filho + 1] > a[filho])) {
          filho++;
        }
        if (a[filho] > t) {
          a[pai] = a[filho];
          pai = filho;
          filho = pai * 2 + 1;
        } else {
          break;
        }
      }
      a[pai] = t;
    }
  }
};
int main() {
  int vetor[MAXX] = {1, 9, 2, 4, 0};
  HeapSort *hs = new HeapSort(MAXX);
  cout << "\nVetor Original\n";
  hs->impVetor(vetor);
  hs->heapsort(vetor, MAXX);
  cout << "\nVetor HeapSort (metodo selecao)\n";
  hs->impVetor(vetor);
  return 0;
}