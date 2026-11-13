package cenario;

public class SetorPrivado extends Setor {

    public SetorPrivado(Coordenadas coordenada, boolean[] portas) {
        super(coordenada, portas);
    }

    // Setor privado permite o ataque normalmente
    @Override
    public boolean permiteAtaque() {
        return true;
    }

    // Setor privado nao permite a procura
    @Override
    public boolean permiteProcura() {
        return false; 
    }
}
