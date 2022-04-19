import java.util.ArrayList;

public class MCTS {

    private static class Node {
        private final float MAX_UCB = 999999999;
        private final Game game;
        private final Node parent;
        private final ArrayList<Node> children;

        private int visitCount;
        private int score;

        public Node(Game game, Node parent) {
            this.game = game;
            this.parent = parent;
            this.children = new ArrayList<>();

            this.visitCount = 0;
            this.score = 0;
        }

        public Node(Game game) {
            this.game = game;
            this.parent = null;
            this.children = new ArrayList<>();

            this.visitCount = 0;
            this.score = 0;
        }

        public boolean isLeaf() {
            return children.size() == 0 || game.isBoardFull();
        }

        public Game getGame() {
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
            ArrayList<Game> childGames = game.getChildren();
            if (childGames.size() == 0) {
                System.out.println("eita");
            }
            for (Game c: childGames) {
                children.add(new Node(c, this));
            }
        }

        private float getUCB() {
            if (visitCount == 0) return MAX_UCB;
            assert parent != null;
            return (float) ((score/visitCount) + 2 * Math.sqrt(Math.log(parent.getVisitCount()) / visitCount));
        }

        public Node getChildMaxUCB() {
            if (children.size() == 0) System.out.println("Deu pau");

            Node selected = children.get(0);
            if (children.size() == 1) return selected;

            float tmpUcb, highestUcb = selected.getUCB();
            for (int i = 1; i < children.size(); i++) {
                tmpUcb = children.get(i).getUCB();
                if (tmpUcb == MAX_UCB) {
                    return children.get(i);
                } else if (tmpUcb > highestUcb) {
                    selected = children.get(i);
                    highestUcb = tmpUcb;
                }
            }
            return selected;
        }

        public Node getFirstChild() {
            return children.get(0);
        }

    }
    private final int playerMultiplier;
    private final double timeLimit;
    private final int rolloutLimit;

    public MCTS(char aiMarker, double timeLimitInSecs, int rolloutLimit) {
        this.timeLimit = timeLimitInSecs * 1000;
        this.rolloutLimit = rolloutLimit;

        if (aiMarker == 'X') {
            playerMultiplier = 1;
        } else {
            playerMultiplier = -1;
        }
    }

    public Game findNextMove(Game rootGame) {
        Node root = new Node(rootGame);
        root.expandNode();

        Node current;
        int rolloutValue;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeLimit) {
            current = root;

            // left part
            while (!current.isLeaf()) {
                current = current.getChildMaxUCB();
            }

            // right part
            if (current.getVisitCount() != 0 && current.game.isInProgress()) {
                current.expandNode();
                current = current.getFirstChild();
            }
            rolloutValue = rollout(current);
            backpropagation(current, rolloutValue);
        }

        return root.getChildMaxUCB().getGame();
    }

    private int rollout(Node node) {
        Game game = node.getGame();
        for (int i = 0; i < rolloutLimit; i++) {
            if (!game.isInProgress()) {
                return game.utilidade() * playerMultiplier;
            }
            game = game.getRandomChild();
        }
        return game.utilidade() * playerMultiplier;
    }

    private void backpropagation(Node node, int rolloutValue) {
        while (node != null) {
            node.addToScore(rolloutValue);
            node.incrementVisitCount();
            node = node.getParent();
        }
    }

}