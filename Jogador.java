public class Jogador extends Personagem implements Acao {
    //construtor
    public Jogador(int atk, int def, Coordenadas pos) {
        super(atk,def,pos) ;
    }

    public JogadorSuporte(int atk, int def, Coordenadas pos) {
        super(atk, def, pos) ;
    }
    //Metodos
    public void atacar(Personagem alvo) {
        alvo.setDef(alvo.getDef() - getAtk()) ;
    }
    
}
