/***************************************
 * Imprimir de 5 ate 1 usando recursão *
 * ex03.00.c                           *
 * *************************************/

#include <stdio.h>

void imprime (int n){
    if (n!=0){
        printf("%d ",n);
        imprime(n-1);
    }
}


int main() {
    printf("\nImprimir de 5 até 1 usando reursao:\n");
    imprime(5);
    return 0;
}