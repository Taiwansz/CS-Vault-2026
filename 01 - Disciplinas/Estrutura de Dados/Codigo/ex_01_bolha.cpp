#include <iostream>
using namespace std;

//#define MAX 5

class BubbleSort {
private:
  int MAX;

public:
  BubbleSort(int m) { MAX = m; }
  void impVetor(int *v) {
    cout << "{";
    for (int i = 0; i < MAX; i++)
      cout << v[i] << " ";
    cout << "}\n\n";
  }

  void bubbleSort(int *v) {
    int i, j, temp;
    bool troca;
    for (i = 0; i < MAX - 1;i++){
      troca = false;
      for (j = 0; j < MAX - i - 1; j++){
        if (v[j] > v[j+1]){
          temp = v[j];
          v[j] = v[j + 1];
          v[j + 1] = temp;
          troca = true;
        }
      }
      if (!troca) // se nao trocou para
        break;
    }
  }
};

int main() {
  BubbleSort *bs = new BubbleSort(5);
  int vetor[5] = {1, 9, 2, 4, 0};
  cout << "\nVetor Original\n";
  bs->impVetor(vetor);
  bs->bubbleSort(vetor);
  cout << "\nVetor Ordenado (metodo bolha)\n";
  bs->impVetor(vetor);
  return 0;
}