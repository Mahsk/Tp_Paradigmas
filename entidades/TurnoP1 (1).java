package motor;

import cenario.Coordenadas;
import cenario.Setor;
import cenario.Tabuleiro;
import entidades.Inimigo;
import entidades.Jogador;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TurnoP1 {
    private static final Random rand = new Random();

    private final Jogador p1;
    private final Tabuleiro tabuleiro;
    private final Scanner sc;

    public TurnoP1(Jogador p1, Tabuleiro tabuleiro, Scanner sc) {
        this.p1 = p1;
        this.tabuleiro = tabuleiro;
        this.sc = sc;
    }

    public void turnoInicial() {
        System.out.println("P1 está no centro (3,3). Escolha uma porta para sair.");
        while (!executarMovimento()) { }
    }

    public boolean chegouNaFonte() {
        return tabuleiro.ehFonte(p1.getPos());
    }

    public boolean jogar() {
        System.out.println("\n--- Turno do Jogador 1 (P1) ---");
        System.out.println("Posição atual: " + p1.getPos() + "  ATK/DEF: " + p1.getAtk() + "/" + p1.getDef());

        int acoes = 0;
        while (acoes < 2 && p1.getDef() > 0) {
            Setor setorAtual = tabuleiro.getSetor(p1.getPos());

            System.out.println("\nAção " + (acoes + 1) + "/2 - escolha:");
            System.out.println("1 - Movimentar (só com o setor limpo; encerra o turno)");
            System.out.println("2 - Atacar inimigos no setor");
            System.out.println("3 - Procurar itens/vantagens");
            System.out.print("Opção: ");

            switch (lerInt()) {
                case 1 -> {
                    if (executarMovimento()) {
                        return chegouNaFonte();
                    }
                }
                case 2 -> {
                    if (executarAtaque(setorAtual)) acoes++;
                }
                case 3 -> {
                    if (executarProcura(setorAtual)) acoes++;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
        return false;
    }

    // P1 moveu -> true
    private boolean executarMovimento() {
        Setor atual = tabuleiro.getSetor(p1.getPos());
        if (!atual.estaLimpo()) {
            System.out.println("Ainda há inimigos no setor! Elimine todos antes de se mover.");
            return false;
        }

        System.out.println("Digite as coordenadas de destino (X Y): ");
        int destX = lerInt();
        int destY = lerInt();

        Coordenadas dest = new Coordenadas(destX, destY);

        if (!tabuleiro.temPassagem(p1.getPos(), dest)) {
            System.out.println("Movimento inválido ou caminho bloqueado!");
            return false;
        }

        p1.movimentar(new Coordenadas(destX, destY));
        System.out.println("P1 moveu-se para (" + destX + ", " + destY + ")");

        Setor destino = tabuleiro.getSetor(dest);
        // if setor novo gera de 1 a 3 inimigos 
        if (!tabuleiro.ehFonte(p1.getPos()) && !destino.isVisitado()) {
            gerarInimigos(destino);
        }
        destino.setVisitado(true);
        return true;
    }

    // açao realizada -> true
    private boolean executarAtaque(Setor setor) {
        if (setor.estaLimpo()) {
            System.out.println("Não há inimigos neste setor para atacar.");
            return false;
        }

        System.out.println("Inimigos no setor:");
        for (int i = 0; i < setor.getInimigos().size(); i++) {
            Inimigo e = setor.getInimigos().get(i);
            System.out.println("[" + i + "] Inimigo ATK/DEF: " + e.getAtk() + "/" + e.getDef());
        }

        System.out.print("Escolha o índice do inimigo para atacar: ");
        int idx = lerInt();

        if (idx < 0 || idx >= setor.getInimigos().size()) {
            System.out.println("Índice inválido.");
            return false;
        }

        Inimigo alvo = setor.getInimigos().get(idx);
        // verifica o ataque se acertou -> dano
        // boolean acertou = setor.receberAtaque(alvo, p1.getAtk());
        boolean acertou = setor.permiteAtaque();
        //boolean acertou = alvo.

        if (!acertou) {
            System.out.println("O ataque não atingiu o vírus.");
        } else if (alvo.getDef() <= 0) {
            setor.removerInimigo(alvo);
            System.out.println("Inimigo derrotado e removido do setor!");
        } else {
            alvo.setDef(alvo.getDef() - p1.getAtk());
            System.out.println("Defesa restante dele: " + alvo.getDef());
        }
        return true;
    }

    // açap realizada -> true
    private boolean executarProcura(Setor setor) {
        if (!setor.permiteProcura()) {
            System.out.println("Setor privado: não é possível procurar aqui.");
            return false;
        }

        int res = p1.procurar();
        System.out.println("P1 procurou no setor e obteve o resultado: " + res);

        if (res <= 3) {
            System.out.println("Nada encontrado.");
        } else if (res == 4) {
            System.out.println("+1 de DEF! DEF atual: " + p1.getDef());
        } else if (res == 5) {
            System.out.println("+2 de DEF! DEF atual: " + p1.getDef());
        } else { 
            System.out.println("Todos os inimigos do setor perdem 1 de DEF!");
            for (Inimigo e : new ArrayList<>(setor.getInimigos())) {
                e.setDef(e.getDef() - 1);
                if (e.getDef() <= 0) setor.removerInimigo(e);
            }
        }
        return true;
    }

    private void gerarInimigos(Setor setor) {
        int qtd = rand.nextInt(3) + 1;                 // 1 a 3 inimigos
        for (int i = 0; i < qtd; i++) {
            int valor = rand.nextInt(3) + 1;           // ATK = DEF -> 1 a 3
            setor.adicionarInimigo(new Inimigo(valor, setor.getCoordenada()));
        }
        System.out.println("Surgiram " + qtd + " inimigo(s) no setor!");
    }

    private int lerInt() {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Digite um número: ");
        }
        return sc.nextInt();
    }
}