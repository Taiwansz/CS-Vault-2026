#include <iostream>
using namespace std;

class aluno
{
public:
  int ra;
  string nome;
  float n1, n2;
};

class Hash
{
private:
  int qtd, TABLE_SIZE;
  aluno **itens;

public:
  Hash(int TABLE_SIZE)
  {
    int i;
    this->TABLE_SIZE = TABLE_SIZE;
    this->itens = new aluno *[TABLE_SIZE];
    this->qtd = 0;
    for (i = 0; i < this->TABLE_SIZE; i++)
      this->itens[i] = NULL;
  }

  int sondagemLinear(int pos, int i, int TABLE_SIZE)
  {
    return ((pos * i) & 0x7FFFFFFF) % TABLE_SIZE;
  }

  int valorString(string str)
  {
    // faz com que cama e maca tenha valores diferentes
    int i, valor = 7;
    int tam = str.size();
    for (i = 0; i < tam; i++)
      valor = 31 * valor + (int)str[i];
    return valor;
  }

  int chaveDivisao(int chave, int TABLE_SIZE)
  {
    return (chave & 0x7FFFFFFF) % TABLE_SIZE;
    // a operação de "& bit-a-bit" com 0x7FFFFFFF elimina o bit de sinal
    // se houver overflow o valor fica negativo e com esta ação isto não ocorre.
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

  void liberaHash()
  {
    int i;
    for (i = 0; i < TABLE_SIZE; i++)
    {
      if (itens[i] != NULL)
        delete (itens[i]);
    }
    delete (itens);
    delete (this);
  }

  bool insereHash_semColisao(struct aluno al, int key)
  {
    if (qtd == TABLE_SIZE)
      return false;
    int chave = key;
    int pos = chaveDivisao(chave, TABLE_SIZE);
    aluno *novo;
    novo = new aluno;
    if (novo == NULL)
      return false;
    *novo = al;
    itens[pos] = novo;
    qtd++;
    return true;
  }

  bool insereHash_semColisao(struct aluno al, string key)
  {
    if (qtd == TABLE_SIZE)
      return false;
    int chave = valorString(key);
    int pos = chaveDivisao(chave, TABLE_SIZE);
    aluno *novo;
    novo = new aluno;
    if (novo == NULL)
      return false;
    *novo = al;
    itens[pos] = novo;
    qtd++;
    return true;
  }

  bool buscaHash_semColisao(int key, aluno *al)
  {
    int pos = chaveDivisao(key, TABLE_SIZE);
    if (itens[pos] == nullptr)
      return false;
    *al = *(itens[pos]);
    return true;
  }

  bool buscaHash_semColisao(string key, aluno *al)
  {
    int chave = valorString(key);
    int pos = chaveDivisao(chave, TABLE_SIZE);
    if (itens[pos] == NULL)
      return false;
    *al = *(itens[pos]);
    return true;
  }

  bool insereHash_enderAberto(aluno al)
  {
    if (qtd == TABLE_SIZE)
      return false;
    int chave = al.ra;
    int i, pos, newPos;
    pos = chaveDivisao(chave, TABLE_SIZE);
    for (i = 0; i < TABLE_SIZE; i++)
    {
      newPos = sondagemLinear(pos, i, TABLE_SIZE);
      if (itens[newPos] == NULL)
      {
        aluno *novo;
        novo = new aluno;
        if (novo == NULL)
          return false;
        *novo = al;
        itens[newPos] = novo;
        qtd++;
        return true;
      }
    }
    return false;
  }
  bool buscaHash_enderAberto(int mat, aluno *al)
  {
    int i, pos, newPos;
    pos = chaveDivisao(mat, TABLE_SIZE);
    for (i = 0; i < TABLE_SIZE; i++)
    {
      newPos = sondagemLinear(pos, i, TABLE_SIZE);
      if (itens[newPos] == NULL)
        return false;
      if (itens[newPos]->ra == mat)
      {
        *al = *(itens[newPos]);
        return true;
      }
    }
    return false;
  }
};

int main()
{
  Hash *ha = new Hash(1427);
  int aux, x;
  aluno al;

  al.ra = 2222;
  al.nome = "Clodoaldo";
  al.n1 = 10;
  al.n2 = 8;
  x = ha->insereHash_semColisao(al, al.nome);

  al.ra = 12345;
  al.nome = "Carlos Alexandre Miglinski";
  al.n1 = 10;
  al.n2 = 9.5;
  x = ha->insereHash_semColisao(al, al.ra);

  aux = al.ra;
  al.ra = 0;
  al.nome = "";
  al.n1 = 0;
  al.n2 = 0;

  x = ha->buscaHash_semColisao(aux, &al);
  cout << "Hash sem colisao " << endl;
  if (x)
    cout << "RA: " << al.ra << " Nome: " << al.nome << ", Nota1: " << al.n1
         << " Nota2: " << al.n2 << endl;
  else
    cout << "RA nao localizado" << endl;

  x = ha->buscaHash_semColisao("Clodoaldo", &al);
  cout << "Hash sem colisao " << endl;
  if (x)
    cout << "RA: " << al.ra << " Nome: " << al.nome << ", Nota1: " << al.n1
         << " Nota2: " << al.n2 << endl;
  else
    cout << "RA nao localizado" << endl;

  al.ra = 1500;
  al.nome = "Pedro Alvares Cabral";
  al.n1 = 7;
  al.n2 = 8.5;
  x = ha->insereHash_enderAberto(al);
  aux = al.ra;
  al.ra = 0;
  al.nome = "";
  al.n1 = 0;
  al.n2 = 0;

  x = ha->buscaHash_enderAberto(aux, &al);

  cout << endl
       << endl
       << "Hash endereco aberto " << endl;
  if (x)
    cout << "RA: " << al.ra << " Nome: " << al.nome << ", Nota1: " << al.n1
         << " Nota2: " << al.n2 << endl;
  else
    cout << "RA nao localizado" << endl;
  ha->liberaHash();
  return 0;
}