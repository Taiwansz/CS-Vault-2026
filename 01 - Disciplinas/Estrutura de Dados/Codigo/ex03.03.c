/***********************************************************************
 * Programa para calcular a somatória dos N primeiros números inteiros *
 * ex03.03.c
 * *********************************************************************/
#include <stdio.h>

int somaInteiro (int n) {
    if (n ==1) return (1);
    else return (n + somaInteiro(n-1));
}

int main() {
    int i;
    printf("\nPrograma para calcular a somatória dos \"N\" primeiros números inteiros\n\n");
    printf("Digigte um número: ");
    scanf("%d",&i);
    printf("somatória %d = %d\n",i,somaInteiro(i));
    return 0;
}