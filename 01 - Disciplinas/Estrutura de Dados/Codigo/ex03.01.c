/**************************************************************************
 * Calcular fatorial usando recursão
 * ex03.01.c
 * 5! = 5 * 4 * 3 * 2 * 1
 * 0! = 1
 * ex03.01.c
 * ************************************************************************/

#include <stdio.h>

int fat(int n)
{
  if (n <= 0)
    return 1;
  else
    return (n * fat(n - 1));
}

int main()
{
  printf("\nFatorial de 0 = %d\n\n", fat(0));
  printf("\nFatorial de 1 = %d\n\n", fat(1));
  printf("\nFatorial de 3 = %d\n\n", fat(3));
  return 0;
}

/******************************************************
 * empilhamento da funcao                             *
 ******************************************************
 int fat (3) {
    if (3 == 0)
      return 1;
    else
      return (3 * fat (2));
}

 int fat (2) {
    if (2 == 0)
      return 1;
    else
      return (2 * fat (1));
}

 int fat (1) {
    if (1 == 0)
      return 1;
    else
      return (1 * fat (0));
}

 int fat (0) {
    if (0 == 0)
      return 1;
}

************************************************************/
