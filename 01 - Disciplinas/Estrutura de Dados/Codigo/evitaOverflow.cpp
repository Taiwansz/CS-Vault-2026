#include <iostream>
#include <limits.h>
#include <bitset>
using namespace std;

int main()
{
  // 0111 1111 1111 1111 1111 1111 1111 1111
  int valor = 0x7FFFFFFF; // 7 vezes F
  bitset<32> binario(valor);
  cout << "0x7FFFFFFF em binário é    : " << binario << endl;
  bitset<32> binario2(valor + 1);
  cout << "0x7FFFFFFF + 1 em binário é: " << binario2 << endl;

  cout << "0x7FFFFFFF     = " << 0x7FFFFFFF << endl;
  cout << "INT_MAX        = " << INT_MAX << endl;
  cout << "0x7FFFFFFF + 1 = " << 0x7FFFFFFF + 1 << endl;
  cout << "0x7FFFFFFF - 1 = " << 0x7FFFFFFF - 1 << endl;
  cout << "-------------------------------------------" << endl;
  int i = -1;
  int ii;
  ii = (i & 0x7FFFFFFF);
  cout << "0x7FFFFFFF = " << 0x7FFFFFFF << endl;
  cout << "Valor de i " << i << " valor de ii " << ii << endl;
  cout << "Maximo inteiro " << INT_MAX << endl;
  ii += 10;
  cout << "Valor de ii + 10 " << ii << endl;
  ii = (ii & 0x7FFFFFFF);
  cout << "Valor de ii depois do bit a bit " << ii << endl;
  ii = INT_MAX + 10;
  cout << "Valor de ii max + 1 " << ii << endl;
  ii = ii % INT_MAX;
  cout << "Valor de ii depois ii % INT_MAX " << ii << endl;

  int chave = 12345;
  bitset<32> bin1(chave);
  int num_bits = 2;
  int part1 = chave >> num_bits;
  bitset<32> bin2(part1);
  int part2 = chave & (1024 - 1);
  bitset<32> bin3(1024 - 1);
  bitset<32> bin4(part2);
  int retorno = part1 ^ part2;
  bitset<32> bin5(retorno);

  cout << "Chave          " << chave << "[" << bin1 << "]" << endl;
  cout << "Chave >> 10    " << part1 << "[" << bin2 << "]" << endl;
  cout << "1024 - 1       " << chave-1 << "[" << bin3 << "]" << endl;
  cout << "Chave & 1024-1 " << part2 << "[" << bin4 << "]" << endl;
  cout << "part1 ^ part2  " << retorno << "[" << bin5 << "]" << endl;
  return 0;
}