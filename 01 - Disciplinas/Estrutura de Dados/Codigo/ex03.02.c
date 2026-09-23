/***********************************************
 * Calcula x elevado a y
 * ex03.02.c
 * *********************************************/

#include <stdio.h>

int potencia(int base, int expo)
{
    if (expo == 0)
        return 1;
    // if (expo==1) return x;
    return base * potencia(base, expo - 1);
}

int main()
{
    int x, y;
    printf("\nx elevado a y\n\n");
    printf("Digite x: ");
    scanf("%d", &x);
    printf("Digite y: ");
    scanf("%d", &y);
    printf("\n%d elevado %d = %d\n", x, y, potencia(x, y));
    return 0;
}