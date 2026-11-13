public class SetorNormal extends Setor {

    public SetorNormal(Coordenada coordenada, boolean[] portas) {
        super(coordenada, portas);
    }

    // Setor normal permite o ataque normalmente
    @Override
    public boolean permiteAtaque() {
        return true; 
    }

    // Setor normal permite a procura 
    @Override
    public boolean permiteProcura() {
        return true; 
    }
}

