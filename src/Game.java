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
        int o = 0,oo = 0,ooo = 0, x = 0, xx= 0, xxx= 0;
        if(vitoria('X')){
            return 512;
        }
        if(vitoria('O')){
            return -512;
        }
        int count=0;
        for(int i=0; i<6; i++){
            for(int j=0; j<4; j++){                     //horizontal
                if(board[i][j]!='O' && board[i][j+1]!='O' && board[i][j+2]!='O' && board[i][j+3]!='O'){
                    if(board[i][j]=='X'){
                        count++;
                    }
                    if(board[i][j+1]=='X'){
                        count++;
                    }
                    if(board[i][j+2]=='X'){
                        count++;
                    }
                    if(board[i][j+3]=='X'){
                        count++;
                    }


                    if(count==1){
                        utilidade+=1;
                        x++;
                    }
                    else if(count==2){
                        utilidade+=10;
                        xx++;
                    }
                    else if(count==3){
                        utilidade+=50;
                        xxx++;
                    }
                }
                count=0;

            }
        }

        for(int i=0; i<3; i++){
            for(int j=0; j<7; j++){                     //vertical
                if(board[i][j]!='O' && board[i+1][j]!='O' && board[i+2][j]!='O' && board[i+3][j]!='O'){
                    if(board[i][j]=='X'){
                        count++;
                    }
                    if(board[i+1][j]=='X'){
                        count++;
                    }
                    if(board[i+2][j]=='X'){
                        count++;
                    }
                    if(board[i+3][j]=='X'){
                        count++;
                    }


                    if(count==1){
                        utilidade+=1;
                        x++;
                    }
                    else if(count==2){
                        utilidade+=10;
                        xx++;
                    }
                    else if(count==3){
                        utilidade+=50;
                        xxx++;
                    }
                }
                count=0;
            }
        }

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){                     //diagonal1
                if(board[i][j]!='O' && board[i+1][j+1]!='O' && board[i+2][j+2]!='O' && board[i+3][j+3]!='O'){
                    if(board[i][j]=='X'){
                        count++;
                    }
                    if(board[i+1][j+1]=='X'){
                        count++;
                    }
                    if(board[i+2][j+2]=='X'){
                        count++;
                    }
                    if(board[i+3][j+3]=='X'){
                        count++;
                    }


                    if(count==1){
                        utilidade+=1;
                        x++;
                    }
                    else if(count==2){
                        utilidade+=10;
                        xx++;
                    }
                    else if(count==3){
                        utilidade+=50;
                        xxx++;
                    }
                }
                count=0;
            }
        }


        for(int i=3; i<6; i++){
            for(int j=0; j<4; j++){                     //diagonal2
                if(board[i][j]!='O' && board[i-1][j+1]!='O' && board[i-2][j+2]!='O' && board[i-3][j+3]!='O'){
                    if(board[i][j]=='X'){
                        count++;
                    }
                    if(board[i-1][j+1]=='X'){
                        count++;
                    }
                    if(board[i-2][j+2]=='X'){
                        count++;
                    }
                    if(board[i-3][j+3]=='X'){
                        count++;
                    }


                    if(count==1){
                        utilidade+=1;
                        x++;
                    }
                    else if(count==2){
                        utilidade+=10;
                        xx++;
                    }
                    else if(count==3){
                        utilidade+=50;
                        xxx++;
                    }
                }
                count=0;
            }
        }



        for(int i=0; i<6; i++){
            for(int j=0; j<4; j++){                     //horizontal
                if(board[i][j]!='X' && board[i][j+1]!='X' && board[i][j+2]!='X' && board[i][j+3]!='X'){
                    if(board[i][j]=='O'){
                        count++;
                    }
                    if(board[i][j+1]=='O'){
                        count++;
                    }
                    if(board[i][j+2]=='O'){
                        count++;
                    }
                    if(board[i][j+3]=='O'){
                        count++;
                    }


                    if(count==1){
                        utilidade-=1;
                    }
                    else if(count==2){
                        utilidade-=10;
                    }
                    else if(count==3){
                        utilidade-=50;
                    }
                }
                count=0;

            }
        }



        for(int i=0; i<3; i++){
            for(int j=0; j<7; j++){                     //vertical
                if(board[i][j]!='X' && board[i+1][j]!='X' && board[i+2][j]!='X' && board[i+3][j]!='X'){
                    if(board[i][j]=='O'){
                        count++;
                    }
                    if(board[i+1][j]=='O'){
                        count++;
                    }
                    if(board[i+2][j]=='O'){
                        count++;
                    }
                    if(board[i+3][j]=='O'){
                        count++;
                    }


                    if(count==1){
                        utilidade-=1;
                    }
                    else if(count==2){
                        utilidade-=10;
                    }
                    else if(count==3){
                        utilidade-=50;
                    }
                }
                count=0;
            }
        }



        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){                     //diagonal1
                if(board[i][j]!='X' && board[i+1][j+1]!='X' && board[i+2][j+2]!='X' && board[i+3][j+3]!='X'){
                    if(board[i][j]=='O'){
                        count++;
                    }
                    if(board[i+1][j+1]=='O'){
                        count++;
                    }
                    if(board[i+2][j+2]=='O'){
                        count++;
                    }
                    if(board[i+3][j+3]=='O'){
                        count++;
                    }


                    if(count==1){
                        utilidade-=1;
                    }
                    else if(count==2){
                        utilidade-=10;
                    }
                    else if(count==3){
                        utilidade-=50;
                    }
                }
                count=0;
            }
        }


        for(int i=3; i<6; i++){
            for(int j=0; j<4; j++){                     //diagonal2
                if(board[i][j]!='X' && board[i-1][j+1]!='X' && board[i-2][j+2]!='X' && board[i-3][j+3]!='X'){
                    if(board[i][j]=='O'){
                        count++;
                    }
                    if(board[i-1][j+1]=='O'){
                        count++;
                    }
                    if(board[i-2][j+2]=='O'){
                        count++;
                    }
                    if(board[i-3][j+3]=='O'){
                        count++;
                    }


                    if(count==1){
                        utilidade-=1;
                    }
                    else if(count==2){
                        utilidade-=10;
                    }
                    else if(count==3){
                        utilidade-=50;
                    }
                }
                count=0;
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

