package entidades;

import cenario.Coordenadas;

public class JogadorSuporte extends Jogador implements AcaoPlayerSuporte {

    public JogadorSuporte(Coordenadas pos) {
        super(1, 7, pos);
    }

    @Override
    public void recuperar(Jogador alvo) {
        if (alvo != null && this.getPos() != null && this.getPos().equals(alvo.getPos())) {
            alvo.setDef(alvo.getDef() + 2);
        }
    }
}