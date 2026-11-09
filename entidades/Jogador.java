package entidades;

import cenario.Coordenadas;
import java.util.Random;

public abstract class Jogador extends Personagem {
    //construtor
    public Jogador(int atk, int def, Coordenadas pos) {
        super(atk,def,pos) ;
    }

    //metodos

    @Override
    public void atacar(Personagem alvo) {
        alvo.setDef(alvo.getDef() - this.getAtk());
    }

    public void movimentar (Coordenadas destino) {
        this.setPos(destino);
    }

    public int procurar() {
        Random rand = new Random();
        int resultado = rand.nextInt(6) + 1;
        if (resultado == 4) {
            this.setDef(this.getDef() + 1);
        } else if (resultado == 5) {
            this.setDef(this.getDef() + 2);
        }

        return resultado;
    }
    
}
