import java.util.ArrayList;

public class Game {
    char[][] board;
    char turno;

    public Game(char[][] board, char turno) {
        this.board = board;
        this.turno = turno;
    }

    public Game() {
        this.turno = 'X';
        this.board = new char[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                this.board[i][j] = '-';
            }
        }
    }

    public char getTurno() {
        return turno;
    }

    public char getProximoTurno() {
        if (turno == 'X') return '0';
        else return 'X';
    }

    public boolean equals(char[][] board) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (this.board[i][j] != board[i][j])
                    return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> movimentosPossiveis() {
        ArrayList<Integer> movimentos = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (board[0][i] == '-') {
                movimentos.add(i);
            }
        }
        return movimentos;
    }

    public boolean isInProgress() {
        if (isBoardFull()) {
            return false;
        }
        return Math.abs(utilidade()) != 512;
    }

    public Game sucessor(int coluna) {
        char proximoTurno = this.getProximoTurno();

        char[][] newBoard = new char[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                newBoard[i][j] = this.board[i][j];
            }
        }

        Game novo = new Game(newBoard, proximoTurno);

        if (novo.board[0][coluna] != '-') {
            return novo;
        }

        int i;
        for (i = 5; novo.board[i][coluna] != '-' && i >= 0; i--);
        novo.board[i][coluna] = this.turno;

        return novo;
    }

    /*
    - - - X Y Z W
    - - X Y Z W S
    - X Y Z W S T
    X Y Z W S T -
    Y Z W S T - -
    Z W S T - - -
    */
    public boolean vitoria(char jogador) {
        String sequencia = "" + jogador + "" + jogador + "" + jogador + "" + jogador;

        //verificar as linhas
        for (int i = 0; i < 6; i++)
            if (String.valueOf(this.board[i]).contains(sequencia))
                return true;
        //verificar as colunas
        for (int j = 0; j < 7; j++) {
            String col = "";
            for (int i = 0; i < 6; i++)
                col += this.board[i][j];

            if (col.contains(sequencia))
                return true;
        }
        //Diagonal Direita
        int row = 3, col = 0;
        while (row <= 5) {
            String temp = "";
            int row2 = row, col2 = col;
            while (row2 >= 0 && col2 < 7) {
                temp += board[row2][col2];
                row2--;
                col2++;
            }
            if (temp.contains(sequencia)) return true;
            row++;
        }
        //Cont. Diagonal Direita
        row = 5;
        col = 1;
        while (col <= 3) {
            String temp = "";
            int row2 = row, col2 = col;
            while (row2 >= 0 && col2 < 7) {
                temp += board[row2][col2];
                row2--;
                col2++;
            }
            if (temp.contains(sequencia)) return true;
            col++;
        }

        //Diagonal Esquerda
        row = 3;
        col = 6;
        while (row <= 5) {
            String temp = "";
            int row2 = row, col2 = col;
            while (row2 >= 0 && col2 >= 0) {
                temp += board[row2][col2];
                row2--;
                col2--;
            }
            if (temp.contains(sequencia)) return true;
            row++;
        }
        //Cont. Diagonal Esquerda
        row = 5;
        col = 5;
        while (col >= 3) {
            String temp = "";
            int row2 = row, col2 = col;
            while (row2 >= 0 && col2 >= 0) {
                temp += board[row2][col2];
                row2--;
                col2--;
            }
            if (temp.contains(sequencia)) return true;
            col--;
        }

        return false;
    }

    public void printJogo() {
        for (int i = 0; i < 7; i++) {
            System.out.print(String.valueOf(i) + " ");
        }
        System.out.print("\n");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(this.board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isBoardFull() {
        for (int j = 0; j < 7; j++) { // Checando por empate
            if (board[0][j] == '-') {
                return false;
            }
        }
        return true;
    }

    public int utilidade() {
        // draw
        if (isBoardFull()) {
            return 0;
        }

        // Vertical victory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 7; col++) {
                if (checkVerticalWin(row, col, 'X')) return 512;
                if (checkVerticalWin(row, col, '0')) return -512;
            }
        }

        // Horizontal victory
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (checkHorizontalWin(row, col, 'X')) return 512;
                if (checkHorizontalWin(row, col, '0')) return -512;
            }
        }

        // Descending victory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (checkDescendingWin(row, col, 'X')) return 512;
                if (checkDescendingWin(row, col, '0')) return -512;
            }
        }

        // Ascending victory
        for (int row = 5; row > 2; row--) {
            for (int col = 0; col < 4; col++) {
                if (checkAscendingWin(row, col, 'X')) return 512;
                if (checkAscendingWin(row, col, '0')) return -512;
            }
        }

        // Not a end game

        int sum = 0;
        /*
        if (turno == 'X') sum += 16;
        else sum -= 16;
        */
        // Vertical eval
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                sum += evaluateVertical(row, col);
            }
        }

        // Horizontal eval
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                sum += evaluateHorizontal(row, col);
            }
        }

        // Descending eval
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                sum += evaluateDescending(row, col);
            }
        }

        // Ascending eval
        for (int row = 5; row > 1; row--) {
            for (int col = 0; col < 5; col++) {
                sum += evaluateAscending(row, col);
            }
        }

        return sum;
    }

    private int valueByCounts(int xCount, int zCount) {
        if (zCount == 3 && xCount == 0) {
            return -50;
        } else if (zCount == 2 && xCount == 0) {
            return -10;
        } else if (zCount == 1 && xCount == 0) {
            return -1;
        } else if (zCount == 0 && xCount == 1) {
            return -1;
        } else if (zCount == 0 && xCount == 2) {
            return -10;
        } else if (zCount == 0 && xCount == 3) {
            return -50;
        }

        return 0;
    }

    private int evaluateVertical(int rowStart, int col) {
        int xCount = 0, zCount = 0;
        for (int row = rowStart; row < rowStart + 3; row++) {
            if (board[row][col] == 'X') xCount++;
            if (board[row][col] == '0') zCount++;
        }
        return valueByCounts(xCount, zCount);
    }

    private int evaluateHorizontal(int row, int colStart) {
        int xCount = 0, zCount = 0;
        for (int col = colStart; col < colStart + 3; col++) {
            if (board[row][col] == 'X') xCount++;
            if (board[row][col] == '0') zCount++;
        }
        return valueByCounts(xCount, zCount);
    }

    private int evaluateAscending(int rowStart, int colStart) {
        int xCount = 0, zCount = 0;
        for (int i = 0; i < 3; i++) {
            if (board[rowStart - i][colStart + i] == 'X') xCount++;
            if (board[rowStart - i][colStart + i] == '0') zCount++;
        }
        return valueByCounts(xCount, zCount);
    }

    private int evaluateDescending(int rowStart, int colStart) {
        int xCount = 0, zCount = 0;
        for (int i = 0; i < 3; i++) {
            if (board[rowStart + i][colStart + i] == 'X') xCount++;
            if (board[rowStart + i][colStart + i] == '0') zCount++;
        }
        return valueByCounts(xCount, zCount);
    }

    private boolean checkVerticalWin(int rowStart, int col, char marker) {
        for (int i = rowStart; i < rowStart + 4; i++) {
            if (board[i][col] != marker) {
                return false;
            }
        }
        return true;
    }

    private boolean checkHorizontalWin(int row, int colStart, char marker) {
        for (int j = colStart; j < colStart + 4; j++) {
            if (board[row][j] != marker) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAscendingWin(int rowStart, int colStart, char marker) {
        for (int i = 0; i < 4; i++) {
            if (board[rowStart - i][colStart + i] != marker) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDescendingWin(int rowStart, int colStart, char marker) {
        for (int i = 0; i < 4; i++) {
            if (board[rowStart + i][colStart + i] != marker) {
                return false;
            }
        }
        return true;
    }

    /*
    public int utilidade() {
        if (isBoardFull()) {
            return 0;
        }

        int utilidade = 0;
        int o = 0, oo = 0, ooo = 0, x = 0, xx = 0, xxx = 0;
        if (vitoria('X')) {
            return 512;
        }
        if (vitoria('O')) {
            return -512;
        }
        int count = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {                     //horizontal
                if (board[i][j] != 'O' && board[i][j + 1] != 'O' && board[i][j + 2] != 'O' && board[i][j + 3] != 'O') {
                    if (board[i][j] == 'X') {
                        count++;
                    }
                    if (board[i][j + 1] == 'X') {
                        count++;
                    }
                    if (board[i][j + 2] == 'X') {
                        count++;
                    }
                    if (board[i][j + 3] == 'X') {
                        count++;
                    }


                    if (count == 1) {
                        utilidade += 1;
                        x++;
                    } else if (count == 2) {
                        utilidade += 10;
                        xx++;
                    } else if (count == 3) {
                        utilidade += 50;
                        xxx++;
                    }
                }
                count = 0;

            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {                     //vertical
                if (board[i][j] != 'O' && board[i + 1][j] != 'O' && board[i + 2][j] != 'O' && board[i + 3][j] != 'O') {
                    if (board[i][j] == 'X') {
                        count++;
                    }
                    if (board[i + 1][j] == 'X') {
                        count++;
                    }
                    if (board[i + 2][j] == 'X') {
                        count++;
                    }
                    if (board[i + 3][j] == 'X') {
                        count++;
                    }


                    if (count == 1) {
                        utilidade += 1;
                        x++;
                    } else if (count == 2) {
                        utilidade += 10;
                        xx++;
                    } else if (count == 3) {
                        utilidade += 50;
                        xxx++;
                    }
                }
                count = 0;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {                     //diagonal1
                if (board[i][j] != 'O' && board[i + 1][j + 1] != 'O' && board[i + 2][j + 2] != 'O' && board[i + 3][j + 3] != 'O') {
                    if (board[i][j] == 'X') {
                        count++;
                    }
                    if (board[i + 1][j + 1] == 'X') {
                        count++;
                    }
                    if (board[i + 2][j + 2] == 'X') {
                        count++;
                    }
                    if (board[i + 3][j + 3] == 'X') {
                        count++;
                    }


                    if (count == 1) {
                        utilidade += 1;
                        x++;
                    } else if (count == 2) {
                        utilidade += 10;
                        xx++;
                    } else if (count == 3) {
                        utilidade += 50;
                        xxx++;
                    }
                }
                count = 0;
            }
        }


        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {                     //diagonal2
                if (board[i][j] != 'O' && board[i - 1][j + 1] != 'O' && board[i - 2][j + 2] != 'O' && board[i - 3][j + 3] != 'O') {
                    if (board[i][j] == 'X') {
                        count++;
                    }
                    if (board[i - 1][j + 1] == 'X') {
                        count++;
                    }
                    if (board[i - 2][j + 2] == 'X') {
                        count++;
                    }
                    if (board[i - 3][j + 3] == 'X') {
                        count++;
                    }


                    if (count == 1) {
                        utilidade += 1;
                        x++;
                    } else if (count == 2) {
                        utilidade += 10;
                        xx++;
                    } else if (count == 3) {
                        utilidade += 50;
                        xxx++;
                    }
                }
                count = 0;
            }
        }


        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {                     //horizontal
                if (board[i][j] != 'X' && board[i][j + 1] != 'X' && board[i][j + 2] != 'X' && board[i][j + 3] != 'X') {
                    if (board[i][j] == 'O') {
                        count++;
                    }
                    if (board[i][j + 1] == 'O') {
                        count++;
                    }
                    if (board[i][j + 2] == 'O') {
                        count++;
                    }
                    if (board[i][j + 3] == 'O') {
                        count++;
                    }


                    if (count == 1) {
                        utilidade -= 1;
                    } else if (count == 2) {
                        utilidade -= 10;
                    } else if (count == 3) {
                        utilidade -= 50;
                    }
                }
                count = 0;

            }
        }


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {                     //vertical
                if (board[i][j] != 'X' && board[i + 1][j] != 'X' && board[i + 2][j] != 'X' && board[i + 3][j] != 'X') {
                    if (board[i][j] == 'O') {
                        count++;
                    }
                    if (board[i + 1][j] == 'O') {
                        count++;
                    }
                    if (board[i + 2][j] == 'O') {
                        count++;
                    }
                    if (board[i + 3][j] == 'O') {
                        count++;
                    }


                    if (count == 1) {
                        utilidade -= 1;
                    } else if (count == 2) {
                        utilidade -= 10;
                    } else if (count == 3) {
                        utilidade -= 50;
                    }
                }
                count = 0;
            }
        }


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {                     //diagonal1
                if (board[i][j] != 'X' && board[i + 1][j + 1] != 'X' && board[i + 2][j + 2] != 'X' && board[i + 3][j + 3] != 'X') {
                    if (board[i][j] == 'O') {
                        count++;
                    }
                    if (board[i + 1][j + 1] == 'O') {
                        count++;
                    }
                    if (board[i + 2][j + 2] == 'O') {
                        count++;
                    }
                    if (board[i + 3][j + 3] == 'O') {
                        count++;
                    }


                    if (count == 1) {
                        utilidade -= 1;
                    } else if (count == 2) {
                        utilidade -= 10;
                    } else if (count == 3) {
                        utilidade -= 50;
                    }
                }
                count = 0;
            }
        }


        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {                     //diagonal2
                if (board[i][j] != 'X' && board[i - 1][j + 1] != 'X' && board[i - 2][j + 2] != 'X' && board[i - 3][j + 3] != 'X') {
                    if (board[i][j] == 'O') {
                        count++;
                    }
                    if (board[i - 1][j + 1] == 'O') {
                        count++;
                    }
                    if (board[i - 2][j + 2] == 'O') {
                        count++;
                    }
                    if (board[i - 3][j + 3] == 'O') {
                        count++;
                    }


                    if (count == 1) {
                        utilidade -= 1;
                    } else if (count == 2) {
                        utilidade -= 10;
                    } else if (count == 3) {
                        utilidade -= 50;
                    }
                }
                count = 0;
            }
        }
        return utilidade;
    }

     */

    public ArrayList<Game> getChildren() {
        ArrayList<Game> children = new ArrayList<>();
        for (int move : this.movimentosPossiveis()) {
            children.add(this.sucessor(move));
        }
        return children;
    }

    public Game getRandomChild() {
        ArrayList<Integer> movimentosPossiveis = movimentosPossiveis();
        int nextPlayCol = movimentosPossiveis.get((int) Math.round(Math.random() * (movimentosPossiveis.size() - 1)));
        return sucessor(nextPlayCol);
    }



/*
    public static void main(String[] args) {
        Game a = new Game();
        Game b;
        b = a.sucessor('O', 2);
        b = b.sucessor('X', 3);
        b = b.sucessor('O', 3);
        b = b.sucessor('X', 4);
        b = b.sucessor('X', 4);
        b = b.sucessor('O', 4);
        b = b.sucessor('X', 5);
        b = b.sucessor('X', 5);
        b = b.sucessor('X', 5);
        b = b.sucessor('O', 5);
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                System.out.print(b.board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(b.vitoria('O'));
    }
    */
}

