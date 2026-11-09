

public abstract class Personagem implements Acao{
    //atributos
    protected int atk ;
    protected int def ; 
    protected Coordenadas pos ;

    //Construtor
    public Personagem(int atk, int def, Coordenadas pos) {
        this.atk = atk ;
        this.def = def ;
        this.pos = pos ;
    }

    //Metodos
    public int getAtk(){
        return this.atk  ;
    }
    public int getDef(){
        return this.def ;
    }

    public void setDef(int def) {
        if (def < 0)
            this.def = 0 ;
        else    
            this.def = def ;
    }
}
