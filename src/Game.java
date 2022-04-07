public class Game {
    char[][] board;

    public Game() {
        this.board = new char[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                this.board[i][j] = '-';
            }
        }
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

    public boolean[] movimentosPossiveis() {
        boolean[] movimentos = new boolean[8];
        for (int i = 1; i < 8; i++) {
            if (board[0][i-1] == '-') {
                movimentos[i] = true;
            } else {
                movimentos[i] = false;
            }

        }
        return movimentos;
    }

    public Game sucessor(char jogador, int coluna) {
        coluna--;
        Game novo = new Game();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                novo.board[i][j] = this.board[i][j];
            }
        }
        if(novo.board[0][coluna] != '-'){
            return novo;
        }
        if (novo.board[5][coluna] == '-') {
            novo.board[5][coluna] = jogador;
            return novo;
        } else {
            int i = 0;
            while (novo.board[i + 1][coluna] == '-') {
                i++;
            }
            novo.board[i][coluna] = jogador;
            return novo;
        }
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

    public void printJogo(){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                System.out.print(this.board[i][j] + " ");
            }
            System.out.println();
        }
    }

        public int utilidade(){
            int utilidade=0;

            int xxx=0;
            int xx=0;
            int x=0;
            int oo=0;
            int ooo=0;
            int o=0;
            for(int i=0; i<6; i++){
                for(int j=0; j<7; j++){
                    if(board[i][j]=='X' && board[i][j]=='X'){
                        utilidade+=1;
                        x++;                                  //numero de x
                    }

                    if(j<6){
                        if(board[i][j]=='X' && board[i][j+1]=='X'){
                            utilidade+=10;
                            xx++;
                        }                                                           //numero de sequencias de 2 e 3 X na horizontal
                    }
                    if(j<5){
                        if(board[i][j]=='X' && board[i][j+1]=='X' && board[i][j+2]=='X'){
                            utilidade+=50;
                            xxx++;
                        }
                    }

                    if(i<5){
                        if(board[i][j]=='X' && board[i+1][j]=='X'){
                            utilidade+=10;
                            xx++;
                        }                                                              //numero de sequencias de 2 e 3 X na vertical
                    }
                    if(i<4){
                        if(board[i][j]=='X' && board[i+1][j]=='X' && board[i+2][j]=='X'){
                            utilidade+=50;
                            xxx++;
                        }
                    }

                    if(i<5 && j<6){
                        if(board[i][j]=='X' && board[i+1][j+1]=='X'){
                            utilidade+=10;
                            xx++;
                        }
                    }
                    if(i<4 && j<5){
                        if(board[i][j]=='X' && board[i+1][j+1]=='X' && board[i+2][j+2]=='X'){
                            utilidade+=50;
                            xxx++;
                        }
                    }

                    if(i<5 && j>0){
                        if(board[i][j]=='X' && board[i+1][j-1]=='X'){
                            utilidade+=10;
                            xx++;
                        }
                    }
                    if(i<4 && j>1){
                        if(board[i][j]=='X' && board[i+1][j-1]=='X' && board[i+2][j-2]=='X'){
                            utilidade+=50;
                            xxx++;
                        }
                    }






                    if(board[i][j]=='O' && board[i][j]=='O'){
                        utilidade-=1;
                        o++;                                 //numero de O
                    }

                    if(j<6){
                        if(board[i][j]=='O' && board[i][j+1]=='O'){
                            utilidade-=10;
                            oo++;
                        }                                                           //numero de sequencias de 2 e 3 O na horizontal
                    }
                    if(j<5){
                        if(board[i][j]=='O' && board[i][j+1]=='O' && board[i][j+2]=='O'){
                            utilidade-=50;
                            ooo++;
                        }
                    }

                    if(i<5){
                        if(board[i][j]=='O' && board[i+1][j]=='O'){
                            utilidade-=10;
                            oo++;
                        }                                                              //numero de sequencias de 2 e 3 O na vertical
                    }
                    if(i<4){
                        if(board[i][j]=='O' && board[i+1][j]=='O' && board[i+2][j]=='O'){
                            utilidade-=50;
                            ooo++;
                        }
                    }

                    if(i<5 && j<6){
                        if(board[i][j]=='O' && board[i+1][j+1]=='O'){
                            utilidade-=10;
                            oo++;
                        }
                    }
                    if(i<4 && j<5){
                        if(board[i][j]=='O' && board[i+1][j+1]=='O' && board[i+2][j+2]=='O'){
                            utilidade-=50;
                            ooo++;
                        }
                    }

                    if(i<5 && j>0){
                        if(board[i][j]=='O' && board[i+1][j-1]=='O'){
                            utilidade-=10;
                            oo++;
                        }
                    }
                    if(i<4 && j>1){
                        if(board[i][j]=='O' && board[i+1][j-1]=='O' && board[i+2][j-2]=='O'){
                            utilidade-=50;
                            ooo++;
                        }
                    }
                }
            }
            return utilidade;
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

