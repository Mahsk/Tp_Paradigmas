public class SetorPrivado extends Setor {

    public SetorPrivado(Coordenada coordenada, boolean[] portas) {
        super(coordenada, portas);
    }

    // Setor privado realiza o ataque normalmente
    @Override
    public boolean receberAtaque(Inimigo inimigo, int dano) {
        inimigo.setDef(inimigo.getDef() - dano);
        return true;
    }

    // Setor privado nao permite a procura
    @Override
    public boolean permiteProcura() {
        return false; 
    }
}
