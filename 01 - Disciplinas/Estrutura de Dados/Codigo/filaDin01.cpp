/* Fila Dinamica
 */
#include <iostream>
using namespace std;

typedef int TIPOCHAVE;

struct REGISTRO
{
    TIPOCHAVE chave;
    // outros campos ...
};

struct ELEMENTO
{
    REGISTRO reg;
    ELEMENTO *prox;
};

typedef ELEMENTO *PONT;

struct FILA
{
    PONT inicio;
    PONT fim;
};

void inicializarFila(FILA *f)
{
    f->inicio = NULL;
    f->fim = NULL;
}

int tamanhoFila(FILA *f)
{
    PONT ender = f->inicio;
    int tam = 0;
    while (ender != NULL)
    {
        tam++;
        ender = ender->prox;
    }
    return tam;
}

void listaFila(FILA *f)
{
    PONT ender = f->inicio;
    cout << "Fila [ ";
    while (ender != NULL)
    {
        cout << ender->reg.chave << " ";
        ender = ender->prox;
    }
    cout << "]\n";
}

bool inserirFila(FILA *f, REGISTRO reg)
{
    PONT novo = new ELEMENTO; // malloc(sizeof(ELEMENTO));
    novo->reg = reg;
    novo->prox = NULL;
    if (f->inicio == NULL)
        f->inicio = novo;
    else
        f->fim->prox = novo;
    f->fim = novo;
    return true;
}

bool excluirFila(FILA *f, REGISTRO *reg)
{
    if (f->inicio == NULL)
        return false;
    *reg = f->inicio->reg;
    PONT apagar = f->inicio;
    f->inicio = f->inicio->prox;
    delete (apagar);
    if (f->inicio == NULL)
        f->fim = NULL;
    return true;
}

void reinicializaFila(FILA *f)
{
    PONT ender = f->inicio;
    while (ender != NULL)
    {
        PONT apagar = ender;
        ender = ender->prox;
        delete (apagar);
    }
    f->inicio = NULL;
    f->fim = NULL;
}

int main()
{
    FILA f1;
    REGISTRO r1;
    inicializarFila(&f1);
    r1.chave = 10;
    inserirFila(&f1, r1);
    r1.chave = 20;
    inserirFila(&f1, r1);
    r1.chave = 30;
    inserirFila(&f1, r1);
    cout << "Tamamnho da fila " << tamanhoFila(&f1) << endl;
    listaFila(&f1);
    excluirFila(&f1, &r1);
    cout << "elemento (" << r1.chave << ") foi excluido\n";
    listaFila(&f1);
    reinicializaFila(&f1);
    listaFila(&f1);
    cout << "Tamamnho da fila " << tamanhoFila(&f1) << endl;
    return 0;
}
