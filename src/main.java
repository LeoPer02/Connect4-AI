import java.util.Scanner;
import java.util.Random;
public class main {
    public static void main(String[] args) {
        Game a = new Game();
        System.out.println("Escolha o algoritmo que quer utilizar:\n1 => Min-Max\n2 => Alpha-Beta\n3 => Monte Carlo");
        Scanner in = new Scanner(System.in);
        int alg = in.nextInt();
        if(alg == 1) {
            System.out.println("Introduza a profundidade do algoritmo:");
            int depth = in.nextInt();
            MinMax jogo = new MinMax(depth);
            int jogada = 0;
            System.out.println("Deseja jogar primeiro ou segundo? (1/2)");
            int ordem = in.nextInt();
            if(ordem == 2) {
                while (jogada < 42) {
                    //Computador primeiro
                    int movimento = jogo.gerar(a);
                    System.out.println("Movimento: " + movimento);
                    a = a.sucessor('X', movimento);
                    jogada++;
                    a.printJogo();
                    System.out.println();
                    if (a.vitoria('X')) {
                        System.out.println("X ganhou a partida!!");
                        break;
                    }
                    int playerMov = in.nextInt();
                    a = a.sucessor('O', playerMov);
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
            }else{
                while (jogada < 42) {
                    //Player primeiro
                    a.printJogo();
                    int playerMov = in.nextInt();
                    a = a.sucessor('O', playerMov);
                    jogada++;
                    a.printJogo();
                    System.out.println();
                    if(a.vitoria('O')){
                        System.out.println("O ganhou a partida!!");
                        break;
                    }
                    int movimento = jogo.gerar(a);
                    System.out.println("Movimento: " + movimento);
                    a = a.sucessor('X', movimento);
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
    }
}
