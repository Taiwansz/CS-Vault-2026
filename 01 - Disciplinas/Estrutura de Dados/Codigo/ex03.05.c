#include <stdio.h>

void imprimeFor(int n){
    for (int i=n;i>0;i--)
    printf("%d ",i);
}
void imprime (int n){
    if (n!=0){
        printf("%d ",n);
        imprime(n-1);
    }
}

void imprime2(int n){
    if (n!=0){
        imprime2(n-1);
        printf("%d ",n);
    }
}

int main() {
    printf("\nUsando for: ");
    imprimeFor(5);
    printf("\n\nUsando reursao: ");
    imprime(5);
    printf("\n\nUsando reursao(2): ");
    imprime2(5);
    return 0;
}