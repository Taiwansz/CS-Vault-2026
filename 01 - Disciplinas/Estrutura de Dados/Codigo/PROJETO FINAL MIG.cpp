#include <iostream>
#include <string>
using namespace std;

typedef string TIPOCHAVE;

class REGISTRO {
public:
    TIPOCHAVE placa;
};

class ELEMENTO {
public:
    REGISTRO reg;
    ELEMENTO *prox;
};

class PilhaDin {
private:
    ELEMENTO *topo;
public:
    PilhaDin() { topo = NULL; }

    bool estaVazia() { return topo == NULL; }

    void listaPilha() {
        ELEMENTO *ender = topo;
        cout << "\nPilha [";
        while (ender != nullptr) {
            cout << ender->reg.placa << " ";
            ender = ender->prox;
        }
        cout << "]\n";
    }

    bool pushElemPilha(REGISTRO reg) {
        ELEMENTO *novo = new ELEMENTO();
        novo->reg = reg;
        novo->prox = topo;
        topo = novo;
        return true;
    }

    bool popElemPilha(REGISTRO &reg) {
        if (topo == nullptr) return false;
        reg = topo->reg;
        ELEMENTO *apagar = topo;
        topo = topo->prox;
        delete apagar;
        return true;
    }

    void reinicializaPilha() {
        ELEMENTO *apagar;
        ELEMENTO *posicao = topo;
        while (posicao != nullptr) {
            apagar = posicao;
            posicao = posicao->prox;
            delete apagar;
        }
        topo = nullptr;
    }
};

class Estacionamento {
private:
    PilhaDin estacionamento;
    PilhaDin patio;
public:
    void entradaVeiculo(const TIPOCHAVE &placa) {
        REGISTRO reg;
        reg.placa = placa;
        estacionamento.pushElemPilha(reg);
        cout << "Veiculo " << placa << " entrou no estacionamento.\n";
    }

    void saidaVeiculo(const TIPOCHAVE &placa) {
        REGISTRO reg;
        bool encontrado = false;

        while (!estacionamento.estaVazia()) {
            estacionamento.popElemPilha(reg);
            if (reg.placa == placa) {
                encontrado = true;
                cout << "Veiculo " << placa << " saiu do estacionamento.\n";
                break;
            } else {
                patio.pushElemPilha(reg);
            }
        }

        while (!patio.estaVazia()) {
            patio.popElemPilha(reg);
            estacionamento.pushElemPilha(reg);
        }

        if (!encontrado) {
            cout << "Veiculo " << placa << " nao encontrado no estacionamento.\n";
        }
    }

    void mostrarEstadoAtual() {
        cout << "Estado atual do estacionamento:\n";
        estacionamento.listaPilha();
        cout << "Estado atual do patio:\n";
        patio.listaPilha();
    }

    void exibirBonus() {
        cout << "\n--- Explicacao do Codigo ---\n";
        cout << "Este texto tem como objetivo detalhar as partes do codigo que vao alem do exemplo dado pelo professor, explicando cada modificacao realizada para atender aos requisitos do projeto de simulacao de estacionamento com uso de pilhas. Reconheco que, por nao ter dedicado a atencao necessaria ao projeto desde o inicio, precisei de um esforco extra para adaptar o codigo de forma adequada. Pecos desculpas por essa falta de atencao inicial e espero que as explicacoes a seguir esclarecam cada ajuste e o motivo de suas implementacoes.\n\n";
        
        cout << "--Classe Estacionamento--\n";
        cout << "Criamos a classe `Estacionamento` para centralizar as operacoes de entrada e saida de veiculos, alem de manter o estado atual do estacionamento. A classe e composta por duas pilhas: `estacionamento` e `patio`.\n";
        cout << "- Motivo da criacao: Essa organizacao ajuda a representar o conceito de estacionamento de forma mais clara, englobando tanto as operacoes de controle de entrada e saida quanto os componentes principais do nosso sistema (`estacionamento` e `patio`), tudo em um so lugar.\n";
        cout << "- Outros metodos possiveis: Poderiamos usar apenas uma pilha e realizar todas as manipulacoes diretamente fora de uma classe, mas isso resultaria em um codigo mais confuso e dificil de manter, com operacoes de pilha espalhadas.\n\n";

        cout << "--Metodo entradaVeiculo--\n";
        cout << "O metodo `entradaVeiculo` recebe a placa de um veiculo (como uma `string`), cria um registro para ele, e o insere na pilha `estacionamento`.\n";
        cout << "- Funcionamento: Primeiro, o metodo cria um objeto `REGISTRO` e configura a `placa` com o valor recebido. Em seguida, ele usa o metodo `pushElemPilha` da classe `PilhaDin` para adicionar o veiculo ao topo da pilha `estacionamento`.\n";
        cout << "- Motivo de implementacao: Esse metodo simula a chegada de veiculos ao estacionamento e facilita sua inclusao na pilha de forma organizada.\n\n";

        cout << "--Metodo saidaVeiculo--\n";
        cout << "O metodo `saidaVeiculo` gerencia a remocao de um veiculo especifico. Para isso, ele retira os veiculos do topo da pilha `estacionamento` ate encontrar o veiculo desejado, e os armazena temporariamente no `patio`.\n";
        cout << "- Motivo de implementacao: Como a pilha e uma estrutura de dados do tipo LIFO, so temos acesso direto ao ultimo veiculo inserido. O `patio` serve como uma pilha auxiliar, garantindo que a ordem original dos veiculos seja mantida ao retornar.\n\n";

        cout << "--Interface no main--\n";
        cout << "A funcao `main` contem um menu de opcoes para interagir com o sistema. O menu inclui agora a opcao bonus, que exibe esta explicacao detalhada.\n\n";
        
        cout << "--Cosideracoes Finais--\n";
        cout << "Considere olhar o codigo gerado pela IA, confesso sim que utilizei ela mas garanto que tem um toque humanizado ali. No dia eu havia estudado ele mas confesso que nao teria um resultado satisfatorio, por isso nao me coloquei a disposicao para apresentar para a turma.\n";
        cout << "\n--OBS--";
		cout << "\nTexto gerado por IA.";
        cout << "\nXD \n\n";
        
    }
};

int main() {
    Estacionamento est;
    int opcao;
    string placa;

    do {
        cout << "\n1. Entrada de Veiculo\n2. Saida de Veiculo\n3. Mostrar Estado Atual\n4. Bonus\n0. Sair\nEscolha uma opcao: ";
        cin >> opcao;

        switch (opcao) {
        case 1:
            cout << "Digite a placa do veiculo: ";
            cin >> placa;
            est.entradaVeiculo(placa);
            break;
        case 2:
            cout << "Digite a placa do veiculo para saida: ";
            cin >> placa;
            est.saidaVeiculo(placa);
            break;
        case 3:
            est.mostrarEstadoAtual();
            break;
        case 4:
            est.exibirBonus();
            break;
        case 0:
            cout << "Encerrando programa.\n";
            break;
        default:
            cout << "Opcao invalida.\n";
        }
    } while (opcao != 0);

    return 0;
}
