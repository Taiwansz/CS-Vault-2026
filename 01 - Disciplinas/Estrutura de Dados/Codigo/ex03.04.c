/*******************************************
 * Calcula o tamanho de uma String
 * ex03.04.c
 ********************************************/
#include <stdio.h>
#include "/home/mig/c/libs/myLib.c"
#define MAX 20

int tamStr(char s[])
{
    if (s[0] == '\0') // || s[0] == '\n')
        return 0;
    return 1 + tamStr(&s[1]);
}

int main()
{
    char texto[MAX];
    printf("Digite uma texto (máximo %d caracteres): ", MAX);
    // fgets(texto, MAX, stdin);
    leStr(texto, MAX);
    printf("\ntamanho da string = %d\n", tamStr(texto));
    return 0;
}