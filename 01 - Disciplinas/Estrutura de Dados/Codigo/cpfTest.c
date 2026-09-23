#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "/home/mig/c/libs/myLib.c"
/*
#define false 0
#define true 1

typedef int boolean;


int num(char c){
    return c - '0';
}

boolean cpfValido(char c[15]){
    int tam = strlen(c);
    if (tam != 14) return false;
    int t;
    int resto1,resto2,soma=0,i,j=-1;
    char p1[10];
    int r1,r2;
    for (i=0;i<10;i++){
        if (i== 3) j++;
        if (i== 6) j++;
        if (i==9) j++;
        j++;
        p1[i] = c[j];
    }
    r1 = num(c[12]) *10;
    r1 = r1 + num(c[13]);
    soma = 0;
    for (i=0;i<9;i++){
        t=num(p1[i]); // subtrair o valor asc de '0' para retornar o numero
        j = i + 1;
        soma = soma + (t*j);
    }
    resto1 = soma % 11;
    if (resto1 == 10) resto1 = 0;
    soma = 0;
    for (i=1;i<10;i++){
        t=num(p1[i]);
        soma = soma + (t*i);
    }
    resto2 = soma % 11;
    if (resto2 == 10) resto2 = 0;
    r2 = resto1*10+resto2;
    if(r1==r2) return true;
    else return false;
}
*/
int main() {
    char cpf[15] = "123.456.789.09";
    if (cpfValido(cpf)) { printf("ok\n");}
    else {printf("Faio\n");}
    return 0;
}
