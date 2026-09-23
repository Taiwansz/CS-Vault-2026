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
      impVetor(a);
      if (i > 0) {
        i--;
        t = a[i];
        printf("[1] i-- (%d) t = a[i] (%d) \n", i, t);
      } else {
        n--;
        if (n <= 0)
          return;
        t = a[n];
        a[n] = a[0];
        printf("[2] n-- (%d) t =  a[n] (%d), a[n] = a[0] (%d) \n", n, t, a[0]);
      }
      pai = i;
      filho = i * 2 + 1;
      printf("[3] pai (%d) filho (%d)\n", pai, filho);
      while (filho < n) {
        if ((filho + 1 < n) && (a[filho + 1] > a[filho])) {
          filho++;
          printf("[4] Filho ++ %d\n", filho);
        }
        if (a[filho] > t) {
          a[pai] = a[filho];
          pai = filho;
          // filho = pai * 2 + 1;
          printf("[5] a[pai] = a[filho] (%d) pai = filho (%d) filho = pai * 2 "
                 "+1 %d\n",
                 a[filho], filho, pai * 2 + 1);
          filho = pai * 2 + 1;
        } else {
          printf("[5else]\n");
          break;
        }
      }
      a[pai] = t;
      printf("[6] a[pai] = t (%d)\n", t);
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

/*
Vetor Original
1 9 2 4 0
[1] i-- (1) t = a[i] (9)
[3] pai (1) filho (3)
[5else]
[6] a[pai] = t (9)
1 9 2 4 0
[1] i-- (0) t = a[i] (1)
[3] pai (0) filho (1)
[5] a[pai] = a[filho] (9) pai = filho (1) filho = pai * 2 +1 3
[5] a[pai] = a[filho] (4) pai = filho (3) filho = pai * 2 +1 7
[6] a[pai] = t (1)
9 4 2 1 0
[2] n-- (4) t =  a[n] (0), a[n] = a[0] (9)
[3] pai (0) filho (1)
[5] a[pai] = a[filho] (4) pai = filho (1) filho = pai * 2 +1 3
[5] a[pai] = a[filho] (1) pai = filho (3) filho = pai * 2 +1 7
[6] a[pai] = t (0)
4 1 2 0 9
[2] n-- (3) t =  a[n] (0), a[n] = a[0] (4)
[3] pai (0) filho (1)
[4] Filho ++ 2
[5] a[pai] = a[filho] (2) pai = filho (2) filho = pai * 2 +1 5
[6] a[pai] = t (0)
2 1 0 4 9
[2] n-- (2) t =  a[n] (0), a[n] = a[0] (2)
[3] pai (0) filho (1)
[5] a[pai] = a[filho] (1) pai = filho (1) filho = pai * 2 +1 3
[6] a[pai] = t (0)
1 0 2 4 9
[2] n-- (1) t =  a[n] (0), a[n] = a[0] (1)
[3] pai (0) filho (1)
[6] a[pai] = t (0)
0 1 2 4 9

Vetor HeapSort (metodo selecao)
*/