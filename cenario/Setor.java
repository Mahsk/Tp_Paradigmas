import java.util.ArrayList;
import java.util.List;

public abstract class Setor {
    // atributos 
    private Coordenada coordenada;
    private boolean[] portas; // [0]=cima, [1]=baixo, [2]=direita, [3]=esquerda (true = porta, false = parede)
    private List<Inimigo> inimigos; 
    private boolean visitado;

    // Construtor
    public Setor(Coordenada coordenada, boolean[] portas) {
        this.coordenada = coordenada;
        this.portas = portas;
        this.inimigos = new ArrayList<>(); 
        this.visitado = false;
    }

    // metodo para o tabuleiro colocar os virus gerados dentro do setor
    public void adicionarInimigo(Inimigo inimigo) {
        this.inimigos.add(inimigo);
    }

    // metodo para remover o virus do setor quando a DEF dele chegar a 0 
    public void removerInimigo(Inimigo inimigo) {
        this.inimigos.remove(inimigo);
    }

    // metodo para o jogo saber se o setor esta limpo (sem virus)
    public boolean estaLimpo() {
        return this.inimigos.isEmpty();
    }

    // Metodos Abstratos 
    // Cada setor vai ditar como reage a um ataque e se permite a procura
    public abstract boolean receberAtaque(Inimigo inimigo, int dano); 
    public abstract boolean executarProcura();

    // Getters e Setters 
    public Coordenada getCoordenada() { // Getter para a Coordenada inteira
        return coordenada; 
    }

    public boolean[] getPortas(){
        return portas; 
    }

    public List<Inimigo> getInimigos(){
        return inimigos; 
    }

    public boolean isVisitado(){
        return visitado; 
    }

    public void setVisitado(boolean visitado){ 
        this.visitado = visitado; 
    }
}
