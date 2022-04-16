import java.util.ArrayList;

public class MCTS {

    private static class Node {
        private MCTSGame game;
        private Node parent;
        private ArrayList<Node> children;

        private int visitCount;
        private int score;

        public Node(MCTSGame game, Node parent) {
            this.game = game;
            this.parent = parent;
            this.children = new ArrayList<>();

            this.visitCount = 0;
            this.score = 0;
        }

        public Node(MCTSGame game) {
            this.game = game;
            this.parent = null;
            this.children = new ArrayList<>();

            this.visitCount = 0;
            this.score = 0;
        }

        public boolean isLeaf() {
            return children.size() == 0;
        }

        public MCTSGame getGame() {
            return game;
        }

        public Node getParent() {
            return parent;
        }

        public int getVisitCount() {
            return visitCount;
        }

        public void incrementVisitCount() {
            visitCount++;
        }

        public void addToScore(int value) {
            score += value;
        }

        public int getScore() {
            return score;
        }

        public void expandNode() {
            for (MCTSGame c: game.getChildren()) {
                children.add(new Node(c));
            }
        }

        private float getUCB() {
            if (visitCount == 0) return Float.POSITIVE_INFINITY;
            return (float) ((score/visitCount) + 2 * Math.sqrt(Math.log(parent.getVisitCount()) / visitCount));
        }

        public Node getChildMaxUCB() {
            if (children.size() == 0) System.out.println("Deu pau");

            Node selected = children.get(0);
            if (children.size() == 1) return selected;

            float tmpUcb, highestUcb = selected.getUCB();
            for (int i = 1; i < children.size(); i++) {
                tmpUcb = children.get(i).getUCB();
                if (tmpUcb == Float.POSITIVE_INFINITY) {
                    return children.get(i);
                } else if (tmpUcb > highestUcb) {
                    selected = children.get(i);
                    highestUcb = selected.getUCB();
                }
            }
            return selected;
        }

        public Node getFirstChild() {
            return children.get(0);
        }

    }

    private char player;
    private int playerMultiplier;
    private long timeLimit;
    private int rolloutLimit;

    public MCTS(char player, long timeLimitInSecs, int rolloutLimit) {
        this.player = player;
        this.timeLimit = timeLimitInSecs * 1000;
        this.rolloutLimit = rolloutLimit;

        if (player == 'X') {
            playerMultiplier = 1;
        } else {
            playerMultiplier = -1;
        }
    }

    public Game findNextMove(Game rootGame) {
        Node root = new Node((MCTSGame) rootGame);
        root.expandNode();

        Node current = selection(root);
        int rolloutValue;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeLimit) {
            if (current.getVisitCount() != 0) {
                current.expandNode();
                current = current.getFirstChild();
            }
            rolloutValue = rollout(current);
            backpropagate(current, rolloutValue);

            current = selection(root);
        }

        return current.getChildMaxUCB().getGame();
    }

    private Node selection(Node node) {
        while (!node.isLeaf()) {
            node = node.getChildMaxUCB();
        }
        return node;
    }

    private int rollout(Node node) {
        MCTSGame game = node.getGame();
        for (int i = 0; i < rolloutLimit; i++) {
            if (!game.isInProgress()) {
                return game.utilidade() * playerMultiplier;
            }
            game = game.getRandomChild();
        }
        return game.utilidade() * playerMultiplier;
    }

    private void backpropagate(Node node, int rolloutValue) {
        while (node != null) {
            node.addToScore(rolloutValue);
            node.incrementVisitCount();
            node = node.getParent();
        }
    }

}