// MISSÃO 5 — Prensa Hidráulica
// TODO: 1) classe ABSTRATA EstacaoIndustrial
//          com o método ABSTRATO: public abstract void executarCiclo();
//       2) classe Prensa extends EstacaoIndustrial
//          implementando executarCiclo() com @Override e println:
//          "Prensa: 200 toneladas aplicadas!"

// (pode escrever as duas classes aqui no mesmo arquivo)
public abstract class EstacaoIndustrial{
    public abstract void executarCiclo();
}

public class Prensa extends EstacaoIndustrial{
    @Override
    public class executarCiclo(){
        System.out.println("Prensa: 200 toneladas aplicadas!");

    }

}