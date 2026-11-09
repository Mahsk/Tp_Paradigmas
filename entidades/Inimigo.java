package entidades;
import java.util.Random ;


public class Inimigo extends Personagem implements Acao  {

    //construtor
    public Inimigo(int valor, Coordenadas pos) {
        super(valor, valor, pos) ;
    }
    
    //metodo
    public void atacar(Personagem alvo) {
        int num ; 
        Random aleatorio = new Random();

        num = aleatorio.nextInt(6) + 1;
    
        if ((num % 2) == 0) {

            System.out.println("acertou");
            alvo.setDef(alvo.getDef() - getAtk()) ;
        }
        else 
            System.out.println("errou");
    }
    
}