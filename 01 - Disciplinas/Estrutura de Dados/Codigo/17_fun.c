#include <stdio.h>
#include <string.h>

void leStr(char *txt, int tam)
{
    fgets(txt, tam, stdin);
    if (txt[0] == '\n')
        fgets(txt, tam, stdin);
    int t = strlen(txt) - 1;
    if (txt[t] == '\n')
        txt[t] = '\0';
}

int main()
{
    int idade;
    float salario;
    double altura;
    char tamanho;
    char nome[30];
    printf("Qual a sua idade: ");
    scanf("%d", &idade);
    printf("Qual o seu nome: ");
    leStr(nome, 29);
    // scanf("%s", nome); // retirar o & pois ja eh endereco de memoria
    printf("Qual o tamanho do uniforme (p,m,g,x): ");
    // colocar um espaco antes do %c
    scanf(" %c", &tamanho);
    printf("Qual a pretencao salarial: ");
    scanf("%f", &salario);
    printf("Qual a sua altura: ");
    scanf("%lf", &altura);

    printf("\n\nListagem\n");
    printf("Nome...............: %s\n", nome);
    printf("Idade..............: %d\n", idade);
    printf("Altura.............: %.10lf\n", altura);
    printf("Pretencao salarial : %.10f\n", salario);
    printf("Tamanho do uniforme: %c\n", tamanho);
    return 0;
}