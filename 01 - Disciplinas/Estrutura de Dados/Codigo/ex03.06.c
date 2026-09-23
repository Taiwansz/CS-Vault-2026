#include <stdio.h>
#include <string.h>
#define bool int
#define true 1
#define false 0

bool ehPalindromo(char *str, int inicio, int fim)
{
    if (inicio >= fim)
        return true;
    if (str[inicio] != str[fim])
        return false;
    return ehPalindromo(str, inicio + 1, fim - 1);
}

int main()
{
    char txt[] = "socorram me subino onibus em marrocos";
    int tam, i;
    tam = strlen(txt) - 1;
    printf("%s\n", txt);
    for (i = tam; i >= 0; i--)
        printf("%c", txt[i]);
    if (ehPalindromo(txt, 0, strlen(txt) - 1))
        printf("\n%s eh palimdromo\n", txt);
    else
        printf("\n%s nao eh palimdromo\n", txt);
}