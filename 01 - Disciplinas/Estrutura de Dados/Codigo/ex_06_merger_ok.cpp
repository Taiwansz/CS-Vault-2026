#include <iostream>
using namespace std;

#define MAXX 5

// primeiro subvetor é vetor[begin..mid]
// Segundo subveor é vetor[mid+1..end]

class MergeSort {
private:
  int max;

public:
  MergeSort(int m) { max = m; }
  void merge(int array[], int const left, int const mid, int const right) {
    int const subArrayOne = mid - left + 1;
    int const subArrayTwo = right - mid;

    auto *leftArray = new int[subArrayOne], *rightArray = new int[subArrayTwo];

    for (auto i = 0; i < subArrayOne; i++)
      leftArray[i] = array[left + i];
    for (auto j = 0; j < subArrayTwo; j++)
      rightArray[j] = array[mid + 1 + j];

    auto indexOfSubArrayOne = 0, indexOfSubArrayTwo = 0;
    int indexOfMergedArray = left;

    while (indexOfSubArrayOne < subArrayOne &&
           indexOfSubArrayTwo < subArrayTwo) {
      if (leftArray[indexOfSubArrayOne] <= rightArray[indexOfSubArrayTwo]) {
        array[indexOfMergedArray] = leftArray[indexOfSubArrayOne];
        indexOfSubArrayOne++;
      } else {
        array[indexOfMergedArray] = rightArray[indexOfSubArrayTwo];
        indexOfSubArrayTwo++;
      }
      indexOfMergedArray++;
    }

    while (indexOfSubArrayOne < subArrayOne) {
      array[indexOfMergedArray] = leftArray[indexOfSubArrayOne];
      indexOfSubArrayOne++;
      indexOfMergedArray++;
    }

    while (indexOfSubArrayTwo < subArrayTwo) {
      array[indexOfMergedArray] = rightArray[indexOfSubArrayTwo];
      indexOfSubArrayTwo++;
      indexOfMergedArray++;
    }
    delete[] leftArray;
    delete[] rightArray;
  }

  void mergeSort(int array[], int const begin, int const end) {
    if (begin >= end)
      return;

    int mid = begin + (end - begin) / 2;
    mergeSort(array, begin, mid);
    mergeSort(array, mid + 1, end);
    merge(array, begin, mid, end);
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
  int vetor[] = {12, 11, 13, 5, 6};
  MergeSort *ms = new MergeSort(MAXX);
  // int tam_vetor = sizeof(vetor) / sizeof(vetor[0]);

  cout << "Vetor original \n";
  ms->impVetor(vetor);
  ms->mergeSort(vetor, 0, MAXX - 1);

  cout << "\nVetor ordenado Merge Sort \n";
  ms->impVetor(vetor);
  return 0;
}
