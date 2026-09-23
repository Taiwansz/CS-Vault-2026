#include <iostream>
using namespace std;

void somaDoisInt(int v1, int v2,
	int *soma, float *media) {
	*soma = v1 + v2;
	*media = (float)*soma/2;
	//return soma;
}
int main() {
	int ss;
	float med;
	somaDoisInt(2,3,&ss,&med);
	cout << "Resultado "<<ss<<" media "
	<<med<<endl;
	somaDoisInt(5,-1,&ss,&med);	
	cout << "Resultado "<<ss<<" media "
	<<med<<endl;
	//somaDoisInt();
	return 0;
	//somaDoisInt();
}



