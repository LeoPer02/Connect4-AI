import java.util.ArrayList;

public class MCTSGame extends Game {

    public MCTSGame(char[][] board, char turno) {
        super(board, turno);
    }

    public MCTSGame() {
        super();
    }

    public ArrayList<MCTSGame> getChildren() {
        ArrayList<MCTSGame> children = new ArrayList<>();
        for (int move : this.movimentosPossiveis()) {
            children.add((MCTSGame) this.sucessor(move + 1));
        }
        return children;
    }

    public MCTSGame getRandomChild() {
        ArrayList<Integer> movimentosPossiveis = movimentosPossiveis();
        int nextPlayCol = movimentosPossiveis.get((int) Math.round(Math.random() * movimentosPossiveis.size()));
        return (MCTSGame) sucessor(nextPlayCol);
    }

}
