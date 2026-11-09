public class SetorNormal extends Setor {

    public SetorNormal(Coordenada coordenada, boolean[] portas) {
        super(coordenada, portas);
    }

    // Setor normal realiza o ataque normalmente
    @Override
    public boolean receberAtaque(Inimigo inimigo, int dano) {
        inimigo.setDef(inimigo.getDef() - dano);
        return true; // Retorna true indicando que o ataque acertou
    }

    // Setor normal permite a procura 
    @Override
    public boolean permiteProcura() {
        return true; 
    }
}

