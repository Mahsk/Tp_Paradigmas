package cenario;

import entidades.Inimigo;
import entidades.Jogador;
import java.util.Random;

public class Tabuleiro {
    /* Atributos */
    private static final int TAM = 5;
    private final Coordenadas fonteInfeccao;
    private final Setor[][] setores;
    private final boolean[][][] portasTabuleiro = new boolean[TAM + 1][TAM + 1][4];

    /* Construtor */
    public Tabuleiro() {
        Random random = new Random();
        setores = new Setor[TAM][TAM];

        // Preenchendo as portas
        for (int x = 1; x <= TAM; x++) {
            for (int y = 1; y <= TAM; y++) {
                boolean[] portas = portasTabuleiro[x][y];

                // Cima: herda a porta de baixo [1] do setor de cima (y - 1)
                portas[0] = ((y > 1) && (portasTabuleiro[x][y - 1][1]));

                // Baixo: aberta, menos na borda
                portas[1] = (y < TAM);

                // Direita: aberta no meio e em posições fixas
                portas[2] = (x < TAM) && ((y == 3) || ((y == 1) && (x == 2)) || ((y == 5) && (x == 4)));

                // Esquerda: herda a porta à direita [2] de antes
                portas[3] = ((x > 1) && (portasTabuleiro[x - 1][y][2]));

                int tipo = random.nextInt(3);

                Coordenadas posicao = new Coordenadas(x, y);

                if ((x == 3) && (y == 3)) {
                    setores[x - 1][y - 1] = new SetorNormal(posicao, portas);
                }
                else {
                    switch (tipo) {
                        case 0:
                            setores[x - 1][y - 1] = new SetorNormal(posicao, portas);
                            break;

                        case 1:
                            setores[x - 1][y - 1] = new SetorOculto(posicao, portas);
                            break;
                        
                        case 2:
                            setores[x - 1][y - 1] = new SetorPrivado(posicao, portas);
                            break;
                    }                
                }
            }
        }
        
        setores[2][2].setVisitado(true);

        int x, y;

        // Fonte da infecção aleatória sem ser o centro
        do {
            x = random.nextInt(TAM) + 1;
            y = random.nextInt(TAM) + 1;
        } while (x == 3 && y == 3);

        fonteInfeccao = new Coordenadas(x, y);
    }

    /* Getters */
    // Retorna o setor de uma posição
    public Setor getSetor(Coordenadas pos) {
        if ((pos == null) || (!posValida(pos))) {
            return null;
        }

        return setores[(pos.getX()) - 1][(pos.getY()) - 1];
    }

    /* Métodos */
    // Verifica se uma posição está dentro do tabuleiro
    // Retorna true se sim, false se não
    public boolean posValida(Coordenadas pos) {
        return ((pos.getX() >= 1) && (pos.getX() <= TAM) && (pos.getY() >= 1) && (pos.getY() <= TAM));
    }

    // Verifica se a posição tem a fonte da infecção
    // Retorna true se sim, false se não
    public boolean ehFonte(Coordenadas pos) {
        return ((pos != null) && (pos.getX() == fonteInfeccao.getX()) && (pos.getY() == fonteInfeccao.getY()));
    }

    // Verifica se há porta de um setor para o outro
    // Retorna true se sim, false se não
    public boolean temPassagem (Coordenadas origem, Coordenadas destino) {
        if ((origem == null) || (destino == null) || (!posValida(origem)) || (!posValida(destino))) {
            return false;
        }

        int dx = destino.getX() - origem.getX();
        int dy = destino.getY() - origem.getY();

        // Não deixa se movimentar na diagonal
        // 1 = movimento na vertical / 1 = movimento na vertical | se for 2, se movimentou na diagonal
        if (Math.abs(dx) + Math.abs(dy) != 1) {
            return false;        
        }

        boolean[] portas = getSetor(origem).getPortas();

        if (dy == -1) {
            return portas[0];
        }

        if (dy == 1) {
            return portas[1];
        }

        if (dx == 1) {
            return portas[2];
        }

        return portas[3];
    }

    // Imprime o tabuleiro no terminal
    public void imprimirTabuleiro(Jogador p1, Jogador p2) {
        Coordenadas posP1 = (p1 != null) ? p1.getPos() : null;
        Coordenadas posP2 = (p2 != null) ? p2.getPos() : null;

        System.out.print("   ");

        // Coordenadas X no cabeçalho
        for (int x = 1; x <= TAM; x++) {
            System.out.print("  " + x + " ");
        }

        System.out.println();

        for (int y = 1; y <= TAM; y++) {
            // Desenha as paredes e as portas de cima
            System.out.print("   ");

            for (int x = 1; x <= TAM; x++) {
                Coordenadas pos = new Coordenadas(x, y);

                Setor setor = getSetor(pos);

                boolean[] portas = setor.getPortas();
                boolean visivel = setor.isVisitado();

                System.out.print("+");

                if (y > 1) {
                    Coordenadas posAbaixo = new Coordenadas(x, y - 1);
                    visivel = (visivel || getSetor(posAbaixo).isVisitado());
                }

                // Imprime porta se houver e tiver visível
                if (visivel && portas[0]) {
                    System.out.print("-*-");
                }
                else {
                    System.out.print("---");
                }
            }

            System.out.println("+");

            // Coordenadas Y na lateral
            System.out.print(y + "  ");

            // Desenha as paredes dos lados e conteúdo
            for (int x = 1; x <= TAM; x++) {
                Coordenadas pos = new Coordenadas(x, y);

                Setor setor = getSetor(pos);

                boolean[] portas = setor.getPortas();
                boolean visivel = setor.isVisitado();

                if (x > 1) {
                    Coordenadas posEsquerda = new Coordenadas(x, y);
                    
                    visivel = (visivel || getSetor(posEsquerda).isVisitado());
                }

                // Imprime porta se houver e tiver visível
                if (visivel && portas[3]) {
                    System.out.print("*");
                }
                else {
                    System.out.print("|");
                }

                // Verifica a posição dos jogadores
                // null caso tenha morrido
                boolean p1Aqui = ((posP1 != null) && (posP1.getX() == x) && (posP1.getY() == y));
                boolean p2Aqui = ((posP2 != null) && (posP2.getX() == x) && (posP2.getY() == y));

                // Verifica a fonte de infecção
                boolean fonteAqui = ((fonteInfeccao.getX() == x) && (fonteInfeccao.getY() == y));

                if (p1Aqui && p2Aqui) {
                    System.out.print(" P ");
                }
                else if (p1Aqui) {
                    System.out.print(" P1");
                }
                else if (p2Aqui) {
                    System.out.print(" P2");
                }
                else if (fonteAqui) {
                    System.out.print(" X ");
                }
                else if (x == 3 && y == 3) {
                    System.out.print(" C ");
                }
                else {
                    System.out.print("   ");
                }
            }

            System.out.println("|");
        }

        // Desenha a borda inferior
        System.out.print("   ");

        for (int x = 1; x <= TAM; x++) {
            System.out.print("+---");
        }

        System.out.println("+");

        // Imprime os detalhes dos setores abaixo
        // Se estiverem no mesmo setor
        if ((posP1 != null) && (posP2 != null) && (posP1.equals(posP2))) {
            imprimirSetor(posP1, p1, p2);
        }
        // Se estiverem em setores diferentes
        else {
            if (posP1 != null) {
                imprimirSetor(posP1, p1, null);
            }

            if (posP2 != null) {
                imprimirSetor(posP2, null, p2);
            }
        }
    }

    // Imprime os detalhes do setor ocupado
    private void imprimirSetor(Coordenadas pos, Jogador p1, Jogador p2) {
        int x = pos.getX();
        int y = pos.getY();

        if (!posValida(pos)) {
            return;
        }

        Setor setor = getSetor(pos);

        boolean[] portas = setor.getPortas();

        System.out.println();
        System.out.println("Setor [" + x + "," + y + "]");

        // Cima: parede ou porta
        System.out.println("+" + (portas[0] ? "---------*---------" : "-------------------") + "+");

        // Atributos dos inimigos
        String inimigos = "";
        for (Inimigo inimigo : setor.getInimigos()) {
            if (inimigo.getDef() > 0) {
                if (!inimigos.isEmpty()) {
                    inimigos += " ";
                }

                inimigos += inimigo.getAtk() + "/" + inimigo.getDef();
            }
        }

        // Atributos dos jogadores
        String jogadores = "";
        String atributos = "";

        if (p1 != null) {
            jogadores += "P1";
            atributos += p1.getAtk() + "/" + p1.getDef();
        }

        if (p2 != null) {
            if (p1 != null) {
                jogadores += " ";
                atributos += " ";
            }

            jogadores += "P2";
            atributos += p2.getAtk() + "/" + p2.getDef();
        }

        // Imprimindo as linhas
        imprimirLinhaSetor(inimigos, false, false);
        imprimirLinhaSetor("", false, false);
        imprimirLinhaSetor("", portas[3], portas[2]);
        imprimirLinhaSetor(jogadores, false, false);
        imprimirLinhaSetor(atributos, false, false);

        // Parede ou porta de baixo
        System.out.println("+" + (portas[1] ? "---------*---------" : "-------------------") + "+");
    }

    // Imprime uma linha do setor com as paredes ou portas laterais
    private void imprimirLinhaSetor(String texto, boolean esquerda, boolean direita) {
        System.out.printf("%c %-17.17s %c\n", (esquerda ? '*' : '|'), texto, (direita ? '*' : '|'));
    }
}
