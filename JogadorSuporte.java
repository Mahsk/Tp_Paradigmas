public class JogadorSuporte extends Jogador implements AcaoPlayerSuporte{
    //construtor
    public JogadorSuporte(Coordenadas pos) {
        super(1, 7, pos) ;
    }

    //metodos
    public void recuperar(Jogador alvo){


        alvo.setDef(alvo.getDef() + 2) ;

    }
}
