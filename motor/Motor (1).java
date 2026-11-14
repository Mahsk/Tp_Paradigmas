package motor;

import cenario.Coordenadas;
import cenario.Setor;
import cenario.Tabuleiro;
import entidades.Inimigo;
import entidades.Jogador;
import entidades.JogadorSimples;
import java.util.Scanner;

public class Motor {

    private static final int MAX_CICLOS = 25;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Tabuleiro tabuleiro = new Tabuleiro();

        Jogador p1 = new JogadorSimples(new Coordenadas(3, 3));
        Jogador p2 = null;

        TurnoP1 turnoP1 = new TurnoP1(p1, tabuleiro, sc);

        // Turno 0
        tabuleiro.imprimirTabuleiro(p1, p2);
        turnoP1.turnoInicial();
        boolean venceu = turnoP1.chegouNaFonte();

        // Ciclos: P1, (P2), inimigos
        for (int ciclo = 1; ciclo <= MAX_CICLOS && !venceu && p1.getDef() > 0; ciclo++) {
            tabuleiro.imprimirTabuleiro(p1, p2);

            venceu = turnoP1.jogar();

            if (!venceu) {
                turnoInimigos(p1, tabuleiro);
            }
        }
    }

    // Cada inimigo vivo do setor ataca o jogador uma vez
   // cada inimigo do setor ataca o P1 uma vez
    private static void turnoInimigos(Jogador p1, Tabuleiro tab) {
        // Setor setor = tab.getSetor(p1.getPos().getX(), p1.getPos().getY());
        Setor setor = tab.getSetor(p1.getPos());
        for (Inimigo e : setor.getInimigos()) {
            e.atacar(p1);
        }
    }
}