import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Game a = new Game();
        MinMax jogo = new MinMax(6);
        Scanner in = new Scanner(System.in);
        while(true){
            //Computador primeiro
            int movimento = jogo.gerar(a);
            System.out.println("Movimento: " + movimento);
            a = a.sucessor('X', movimento);
            a.printJogo();
            System.out.println();
            if(a.vitoria('X')){
                System.out.println("X ganhou a partida!!");
                break;
            }
            int playerMov = in.nextInt();
            a = a.sucessor('O', playerMov);
            a.printJogo();
            System.out.println();
            if(a.vitoria('O')){
                System.out.println("O ganhou a partida!!");
                break;
            }
        }
    }
}
