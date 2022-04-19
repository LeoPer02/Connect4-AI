import java.util.Scanner;
import java.util.Random;

public class main {
    public static void main(String[] args) {
        System.out.println("Escolha o algoritmo que quer utilizar:\n1 => Min-Max\n2 => Alpha-Beta\n3 => Monte Carlo");

        Scanner in = new Scanner(System.in);
        int alg = in.nextInt();
        switch (alg) {
            case 1:
                runMinMax();
                break;
            case 3:
                runMonteCarlo();
                break;
        }
    }

    private static void runMonteCarlo() {
        Game a = new Game();
        Scanner in = new Scanner(System.in);

        System.out.println("Introduza a profundidade do algoritmo:");
        int depth = in.nextInt();
        int jogadas = 0;
        System.out.println("Deseja jogar primeiro ou segundo? (1/2)");
        int ordem = in.nextInt();

        char jogador, oponente;
        if (ordem == 1) {
            jogador = 'X';
            oponente = 'O';
        } else {
            jogador = 'O';
            oponente = 'X';
        }

        MCTS mcts = new MCTS(oponente, 1, depth);
        int aux = 0;
        a.printJogo();
        if (jogador == 'X') {
            int playerMov = in.nextInt();
            a = a.sucessor(playerMov);
            a.printJogo();
            jogadas++;
            aux = 1;
        }
        while (jogadas < 42 - aux) {
            a = mcts.findNextMove(a);
            jogadas++;
            System.out.println("\n------------------------------------------\nMCTS:\n");
            a.printJogo();
            System.out.println();
            if (Math.abs(a.utilidade()) == 512) {
                System.out.println(oponente + " ganhou a partida!!");
                break;
            }

            int playerMov = in.nextInt();
            a = a.sucessor(playerMov);
            jogadas++;
            System.out.println("\n-------------------------\nYou:\n");
            a.printJogo();
            System.out.println();
            if (Math.abs(a.utilidade()) == 512) {
                System.out.println(jogador + " ganhou a partida!!");
                break;
            }
        }

    }

    private static void runMinMax() {
        Game a = new Game();
        Scanner in = new Scanner(System.in);

        System.out.println("Introduza a profundidade do algoritmo:");
        int depth = in.nextInt();
        MinMax jogo = new MinMax(depth);
        int jogada = 0;
        System.out.println("Deseja jogar primeiro ou segundo? (1/2)");
        int ordem = in.nextInt();
        if (ordem == 2) {
            char jogador = 'O', oponente = 'X';
            while (jogada < 42) {
                //Computador primeiro
                int movimento = jogo.gerar(a, 'X');
                System.out.println("Movimento: " + movimento);
                a = a.sucessor(movimento);
                jogada++;
                a.printJogo();
                System.out.println();
                if (a.vitoria(oponente)) {
                    System.out.println(oponente + " ganhou a partida!!");
                    break;
                }
                int playerMov = in.nextInt();
                a = a.sucessor(playerMov);
                jogada++;
                a.printJogo();
                System.out.println();
                if (a.vitoria(jogador)) {
                    System.out.println(jogador + " ganhou a partida!!");
                    break;
                }
            }
            if (jogada == 42) {
                System.out.println("Empate!!");
                return;
            }
        } else {
            char jogador = 'X', oponente = 'O';
            a.printJogo();
            while (jogada < 42) {
                //Player primeiro
                int playerMov = in.nextInt();
                a = a.sucessor(playerMov);
                jogada++;
                a.printJogo();
                System.out.println();
                if (a.vitoria(jogador)) {
                    System.out.println(jogador + " ganhou a partida!!");
                    break;
                }
                int movimento = jogo.gerar(a, 'O');
                System.out.println("Min-Max jogou: " + movimento);
                a = a.sucessor(movimento);
                jogada++;
                a.printJogo();
                System.out.println();
                if (a.vitoria('0')) {
                    System.out.println(oponente + " ganhou a partida");
                    break;
                }
            }
            if (jogada == 42) {
                System.out.println("Empate!!");
                return;
            }
        }
    }
}
