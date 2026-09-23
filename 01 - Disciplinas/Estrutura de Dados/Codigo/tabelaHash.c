#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "tabelaHash.h"

int sondagemLinear(int pos, int i, int TABLE_SIZE)
{
    return ((pos * i) & 0x7FFFFFFF) % TABLE_SIZE;
}

int valorString(char *str)
{
    int i, valor = 7;
    int tam = strlen(str);
    for (i = 0; i < tam; i++)
        valor = 31 * valor + (int)str[i];
    return valor;
}

int chaveDivisao(int chave, int TABLE_SIZE)
{
    return (chave & 0x7FFFFFFF) % TABLE_SIZE;
    // a operação de "& bit-a-bit" com 0x7FFFFFFF elimina o bit de sinal
    // se houver valor negativo, com esta ação vira positivo.
}

int chaveMultiplicacao(int chave, int TABLE_SIZE)
{
    float A = 0.6180339887; // constante 0 < A < 1
    float val = A * chave;
    val = val - (int)val;
    return (int)(TABLE_SIZE * val);
}

int chaveDobra(int chave, int TABLE_SIZE)
{
    int num_bits = 10;
    int part1 = chave >> num_bits;
    int part2 = chave & (TABLE_SIZE - 1);
    return part1 ^ part2;
}

Hash *criaHash(int TABLE_SIZE)
{
    Hash *ha = (Hash *)malloc(sizeof(Hash));
    if (ha != NULL)
    {
        int i;
        ha->TABLE_SIZE = TABLE_SIZE;
        ha->itens = (struct aluno **)malloc(TABLE_SIZE * sizeof(struct aluno *));
        if (ha->itens == NULL)
        {
            free(ha);
            return NULL;
        }
        ha->qtd = 0;
        for (i = 0; i < ha->TABLE_SIZE; i++)
            ha->itens[i] = NULL;
    }
    return ha;
}

void liberaHash(Hash *ha)
{
    if (ha != NULL)
    {
        int i;
        for (i = 0; i < ha->TABLE_SIZE; i++)
        {
            if (ha->itens[i] != NULL)
                free(ha->itens[i]);
        }
        free(ha->itens);
        free(ha);
    }
}

bool insereHash_semColisao(Hash *ha, struct aluno al)
{
    if (ha == NULL || ha->qtd == ha->TABLE_SIZE)
    {
        return false;
    }
    int chave = al.ra;
    // int chave = valorString(al.nome);
    int pos = chaveDivisao(chave, ha->TABLE_SIZE);
    struct aluno *novo;
    novo = (struct aluno *)malloc(sizeof(struct aluno));
    if (novo == NULL)
        return false;
    *novo = al;
    ha->itens[pos] = novo;
    ha->qtd++;
    return true;
}
bool buscaHash_semColisao(Hash *ha, int mat, struct aluno *al)
{
    if (ha == NULL)
        return false;
    int pos = chaveDivisao(mat, ha->TABLE_SIZE);
    if (ha->itens[pos] == NULL)
        return false;
    *al = *(ha->itens[pos]);
    return true;
}

bool insereHash_enderAberto(Hash *ha, struct aluno al)
{
    if (ha == NULL || ha->qtd == ha->TABLE_SIZE)
        return false;
    int chave = al.ra;
    int i, pos, newPos;
    pos = chaveDivisao(chave, ha->TABLE_SIZE);
    for (i = 0; i < ha->TABLE_SIZE; i++)
    {
        newPos = sondagemLinear(pos, i, ha->TABLE_SIZE);
        if (ha->itens[newPos] == NULL)
        {
            struct aluno *novo;
            novo = (struct aluno *)malloc(sizeof(struct aluno));
            if (novo == NULL)
                return false;
            *novo = al;
            ha->itens[newPos] = novo;
            ha->qtd++;
            return true;
        }
    }
    return false;
}
bool buscaHash_enderAberto(Hash *ha, int mat, struct aluno *al)
{
    if (ha == NULL)
        return false;
    int i, pos, newPos;
    pos = chaveDivisao(mat, ha->TABLE_SIZE);
    for (i = 0; i < ha->TABLE_SIZE; i++)
    {
        newPos = sondagemLinear(pos, i, ha->TABLE_SIZE);
        if (ha->itens[newPos] == NULL)
            return false;
        if (ha->itens[newPos]->ra == mat)
        {
            *al = *(ha->itens[newPos]);
            return true;
        }
    }
    return false;
}

int main()
{
    Hash *ha = criaHash(1427);
    struct aluno al;
    al.ra = 12345;
    strcpy(al.nome, "Carlos ALexandre Miglinski");
    al.n1 = 10;
    al.n2 = 9.5;
    int x = insereHash_semColisao(ha, al);
    int mat = al.ra;
    al.ra = 0;
    strcpy(al.nome, " ");
    al.n1 = 0;
    al.n2 = 0;
    x = buscaHash_semColisao(ha, mat, &al);
    printf("Hash sem colisao\n");
    if (x)
        printf("RA: %d Nome %s Nota1: %4.1f Nota1: %4.1f\n",
               al.ra, al.nome, al.n1, al.n2);
    else
        printf("RA nao localizado\n");

    al.ra = 1500;
    strcpy(al.nome, "Pedro Alvares Cabral");
    al.n1 = 1;
    al.n2 = 5.5;
    x = insereHash_enderAberto(ha, al);
    mat = al.ra;
    al.ra = 0;
    strcpy(al.nome, " ");
    al.n1 = 0;
    al.n2 = 0;
    x = buscaHash_enderAberto(ha, mat, &al);
    printf("Hash endereco aberto\n");
    if (x)
        printf("RA: %d Nome %s Nota1: %4.1f Nota1: %4.1f\n",
               al.ra, al.nome, al.n1, al.n2);
    else
        printf("RA nao localizado\n");
    liberaHash(ha);
    return 0;
}
