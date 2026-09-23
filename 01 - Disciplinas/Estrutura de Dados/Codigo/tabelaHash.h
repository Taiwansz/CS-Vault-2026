#ifndef _TABELAHASH_H
#define _TABELAHASH_H
#define true 1
#define false 0

typedef int bool;

struct aluno
{
    int ra;
    char nome[40];
    float n1, n2;
};

struct hash
{
    int qtd, TABLE_SIZE;
    struct aluno **itens;
};

typedef struct hash Hash;

Hash *criaHash(int);
void liberaHash(Hash *);
int valorString(char *);
int insereHash_semColisao(Hash *, struct aluno);
int buscaHash_semColisao(Hash *, int, struct aluno *);
int insereHash_enderAberto(Hash *, struct aluno);
int buscaHash_enderAberto(Hash *, int, struct aluno *);

#endif // !_TABELAHASH_H_
