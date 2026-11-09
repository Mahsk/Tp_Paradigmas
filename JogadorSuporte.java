public class JogadorSuporte extends Jogador implements AcaoPlayerSuporte{
    //construtor
    public JogadorSuporte(Coordenadas pos) {
        super(1, 7, pos) ;
    }

    //metodos
    public void recuperar(Jogador alvo){

        if (this.pos.getX() == alvo.pos.getX() && this.pos.getY() == alvo.pos.getY()) {
            alvo.setDef(alvo.getDef() + 2) ;
        }
        else {
            System.out.println("Não dá para recuperar - Diferente setor") ; 
        }
        
        
    }
}
