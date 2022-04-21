import java.util.Scanner;
import java.util.Random;

public class main {
    public static void main(String[] args) {
        System.out.println("Escolha o algoritmo que quer utilizar:\n1 => Min-Max\n2 => Alpha-Beta\n3 => Monte Carlo");

        Scanner in = new Scanner(System.in);
        int alg = in.nextInt();
        switch (alg) {
            case 1:
                //runMinMax();
                break;
            case 3:
                runMonteCarlo();
                break;
        }
    }

    private static void runMonteCarlo() {
        Game game = new Game();
        Scanner in = new Scanner(System.in);
        int move;

        System.out.println("Deseja jogar primeiro ou segundo? (1/2)");

        char jogador, oponente;
        if (in.nextInt() == 1) {
            jogador = 'X';
            oponente = 'O';
        } else {
            jogador = 'O';
            oponente = 'X';
        }

        MCTS mcts = new MCTS(oponente, 1);
        int jogadas = 0, aux = 0;

        if (jogador == 'X'){
            System.out.println("You:\n");
            game.printJogo();
            System.out.print("Move: ");
            move = in.nextInt();
            game = game.sucessor(move);

            aux = 1;
        }

        while (jogadas < 42 - aux) {
            if (jogadas % 2 == 0) {
                System.out.println("MCTS:\n");
                game.printJogo();
                game = mcts.findNextMove(game);

                if (game.vitoria(oponente)) {
                    System.out.println("----------------------------------");
                    game.printJogo();
                    System.out.println(oponente + " ganhou!!");
                    break;
                }

                System.out.println("----------------------------------");
            } else {
                System.out.println("You:\n");
                game.printJogo();
                System.out.print("Move: ");
                move = in.nextInt();
                game = game.sucessor(move);

                if (game.vitoria(jogador)) {
                    System.out.println("----------------------------------");
                    game.printJogo();
                    System.out.println(jogador + " ganhou!!");
                    break;
                }

                System.out.println("----------------------------------");
            }

            jogadas++;
        }

    }

    /*
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
            while (jogada < 42) {
                //Computador primeiro
                int movimento = jogo.gerar(a, 1);
                System.out.println("Movimento: " + movimento);
                a = a.sucessor(movimento);
                jogada++;
                a.printJogo();
                System.out.println();
                if (a.vitoria('X')) {
                    System.out.println("X ganhou a partida!!");
                    break;
                }
                int playerMov = in.nextInt();
                a = a.sucessor(playerMov);
                jogada++;
                a.printJogo();
                System.out.println();
                if (a.vitoria('O')) {
                    System.out.println("O ganhou a partida!!");
                    break;
                }
            }
            if (jogada == 42) {
                System.out.println("Empate!!");
                return;
            }
        } else {
            a.printJogo();
            while (jogada < 42) {
                //Player primeiro
                int playerMov = in.nextInt();
                a = a.sucessor(playerMov);
                jogada++;
                a.printJogo();
                System.out.println();
                if (a.vitoria('O')) {
                    System.out.println("O ganhou a partida!!");
                    break;
                }
                int movimento = jogo.gerar(a, 2);
                System.out.println("Movimento: " + movimento);
                a = a.sucessor(movimento);
                jogada++;
                a.printJogo();
                System.out.println();
                if (a.vitoria('X')) {
                    System.out.println("X ganhou a partida!!");
                    break;
                }
            }
            if (jogada == 42) {
                System.out.println("Empate!!");
                return;
            }
        }
    }
    */
}
