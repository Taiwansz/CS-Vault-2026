#include <iostream>

int main() {
  int num[3] = {1, 2, 3};
  int *numeros =
      new int(3); // Correção: use new int[3] para alocar espaço para 3 inteiros
  numeros[0] = -1;
  numeros[1] = -2;
  numeros[2] = -3;

  std::cout << "Listagem usando x: numeros" << std::endl;
  for (int x : num) { // Correção: use 'numeros' em vez de 'num'
    std::cout << x << std::endl;
  }

  delete[] numeros; // Não se esqueça de liberar a memória alocada

  return 0;
}
