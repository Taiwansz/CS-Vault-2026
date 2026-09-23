/*
Exercício Ordenação
Matheus Sousa dos Santos	52319400
*/


#include <iostream>
#include <ctime>   
#include <cstdlib> 
#include <chrono>  


using namespace std;
using namespace std::chrono;


class SortingAlgorithms {
    private:
        int MAX;
    public:
        SortingAlgorithms(int m) : MAX(m) {}

        void impVetor(int *v) {
            cout << "{";
            for (int i = 0; i < MAX; i++) {
                cout << v[i] << " ";
            }
            cout << "}\n\n";
        }

        void bubbleSort(int *v) {
            for (int i = 0; i < MAX - 1; i++) {
                bool troca = false;
                for (int j = 0; j < MAX - i - 1; j++) {
                    if (v[j] > v[j + 1]) {
                        swap(v[j], v[j + 1]);
                        troca = true;
                    }
                }
                if (!troca) break;
            }
        }

        void selectionSort(int *v) {
            for (int i = 0; i < MAX - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < MAX; j++) {
                    if (v[j] < v[minIdx]) {
                        minIdx = j;
                    }
                }
                if (minIdx != i) {
                    swap(v[i], v[minIdx]);
                }
            }
        }

        void insertionSort(int *v) {
            for (int i = 1; i < MAX; i++) {
                int key = v[i];
                int j = i - 1;
                while (j >= 0 && v[j] > key) {
                    v[j + 1] = v[j];
                    j--;
                }
                v[j + 1] = key;
            }
        }
};


int main() {
    const int m = 100000;
    int vet[m];
    SortingAlgorithms sa(m);

    srand(time(nullptr));
    for (int i = 0; i < m; i++) {
        vet[i] = rand();
    }

    int *vetCopy = new int[m];

    auto start = high_resolution_clock::now();
    memcpy(vetCopy, vet, m * sizeof(int));
    sa.bubbleSort(vetCopy);
    auto bubbleSortDuration = duration_cast<milliseconds>(high_resolution_clock::now() - start).count();
    cout << "Tempo de execucao do Bubble Sort: " << bubbleSortDuration << " ms\n";

    start = high_resolution_clock::now();
    memcpy(vetCopy, vet, m * sizeof(int));
    sa.selectionSort(vetCopy);
    auto selectionSortDuration = duration_cast<milliseconds>(high_resolution_clock::now() - start).count();
    cout << "Tempo de execucao do Selection Sort: " << selectionSortDuration << " ms\n";

    start = high_resolution_clock::now();
    memcpy(vetCopy, vet, m * sizeof(int));
    sa.insertionSort(vetCopy);
    auto insertionSortDuration = duration_cast<milliseconds>(high_resolution_clock::now() - start).count();
    cout << "Tempo de execucao do Insertion Sort: " << insertionSortDuration << " ms\n";

    cout << "\nRanking de desempenho:\n";
    if (bubbleSortDuration <= selectionSortDuration && bubbleSortDuration <= insertionSortDuration) {
        cout << "1: Bubble Sort (" << bubbleSortDuration << " ms)\n";
        cout << (selectionSortDuration <= insertionSortDuration ? "2: Selection Sort (" : "2: Insertion Sort (") 
             << (selectionSortDuration <= insertionSortDuration ? selectionSortDuration : insertionSortDuration) 
             << " ms)\n";
        cout << "3: " << (selectionSortDuration <= insertionSortDuration ? "Insertion Sort" : "Selection Sort") 
             << " (" << (selectionSortDuration <= insertionSortDuration ? insertionSortDuration : selectionSortDuration) << " ms)\n";
    } else if (selectionSortDuration <= bubbleSortDuration && selectionSortDuration <= insertionSortDuration) {
        cout << "1: Selection Sort (" << selectionSortDuration << " ms)\n";
        cout << (bubbleSortDuration <= insertionSortDuration ? "2: Bubble Sort (" : "2: Insertion Sort (") 
             << (bubbleSortDuration <= insertionSortDuration ? bubbleSortDuration : insertionSortDuration) 
             << " ms)\n";
        cout << "3: " << (bubbleSortDuration <= insertionSortDuration ? "Insertion Sort" : "Bubble Sort") 
             << " (" << (bubbleSortDuration <= insertionSortDuration ? insertionSortDuration : bubbleSortDuration) << " ms)\n";
    } else {
        cout << "1: Insertion Sort (" << insertionSortDuration << " ms)\n";
        cout << (bubbleSortDuration <= selectionSortDuration ? "2: Bubble Sort (" : "2: Selection Sort (") 
             << (bubbleSortDuration <= selectionSortDuration ? bubbleSortDuration : selectionSortDuration) 
             << " ms)\n";
        cout << "3: " << (bubbleSortDuration <= selectionSortDuration ? "Selection Sort" : "Bubble Sort") 
             << " (" << (bubbleSortDuration <= selectionSortDuration ? selectionSortDuration : bubbleSortDuration) << " ms)\n";
    }


    delete[] vetCopy;
    return 0;
}


