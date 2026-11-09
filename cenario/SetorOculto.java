import java.util.Random;

public class SetorOculto extends Setor {
    private Random random = new Random();

    public SetorOculto(Coordenada coordenada, boolean[] portas) {
        super(coordenada, portas);
    }

    // Sorteia aleatoriamente se pode realizar o ataque
    @Override
    public boolean receberAtaque(Inimigo inimigo, int dano) {
        if (random.nextBoolean()) {
            inimigo.setDef(inimigo.getDef() - dano);
            return true; 
        } else {
            return false; 
        }
    }

    // Setor oculto permite a procura
    @Override
    public boolean executarProcura() {
        return true;
    }
}

