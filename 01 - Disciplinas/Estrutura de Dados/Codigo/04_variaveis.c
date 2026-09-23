#include <stdio.h>
#include <string.h>

int main()
{
    int idade;
    float salario;
    double altura;
    char tamanho;
    char nome[30];
    idade = 53;
    salario = 1.12345678901234567890;
    altura = 1.12345678901234567890;
    tamanho = 'x';
    strcpy(nome, "Carlos Miglinski");
    printf("\n\nListagem\n");
    printf("Nome...............: %s\n", nome);
    printf("Idade..............: %d\n", idade);
    printf("Altura.............: %.20lf\n", altura);
    printf("Pretencao salarial : %.20f\n", salario);
    printf("Tamanho do uniforme: %c\n", tamanho);
    return 0;
}