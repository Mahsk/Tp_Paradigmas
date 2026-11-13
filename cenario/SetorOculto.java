import java.util.Random;

public class SetorOculto extends Setor {
    private Random random = new Random();

    public SetorOculto(Coordenada coordenada, boolean[] portas) {
        super(coordenada, portas);
    }

    // Sorteia aleatoriamente se pode realizar o ataque
    @Override
    public boolean permiteAtaque() {
        return random.nextBoolean();
    }

    // Setor oculto permite a procura
    @Override
    public boolean permiteProcura() {
        return true;
    }
}

