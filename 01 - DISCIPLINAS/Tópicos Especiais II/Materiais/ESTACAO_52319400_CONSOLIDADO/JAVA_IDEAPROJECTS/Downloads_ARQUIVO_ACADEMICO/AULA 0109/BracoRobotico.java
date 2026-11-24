// MISSÃO 4 — Braço Robótico BR-01
// Na classe Maquina já existe:
//   public void operar() { System.out.println("Operando em modo padrão"); }
// TODO: 1) BracoRobotico herda de Maquina
//       2) construtor (String nome, int potencia) chamando super
//       3) SOBRESCREVA operar() com @Override imprimindo:
//          "BR-01 soldando com precisão!"
public class BracoRobotico extends Maquina {
    public BracoRobotico(String nome, int potencia) {
        super(nome, potencia);
    }
    @Override
    public void operar() {
        System.out.println("BR-01 soldando com precisão!");
    }
}