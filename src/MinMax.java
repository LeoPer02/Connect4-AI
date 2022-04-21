import java.util.ArrayList;
import java.util.Random;


public class MinMax {
    int depth;
    int best = 0;
    long nodes;

    public MinMax(int depth){
        this.depth = depth;
    }

    public int max(int[] a){
        int val = -999999;
        int j = 0, i = 0;
        int count = 0;
        for(int move : a){
            if(move > val){
                val = move;
                j = i;
                count = 1;
            }else if(move == val){
                count++;
            }
            i++;
        }
        System.out.println();
        int[] maxs = new int[count];
        Random r = new Random();
        int k = 0, f = 0;
        for(int move: a){
            if(move == val) {
                maxs[k] = f;
                k++;
            }
            f++;
            }
        return maxs[r.nextInt(count)];
    }
    /*public int min(int[] a){
        int val = 99999;
        int j = 0;
        int count = 0;
        for(int i = 1; i < a.length; i++){
            System.out.print(a[i] + " ");
            if(a[i] < val){
                val = a[i];
                j = i;
                count = 1;
            }else if(a[i] == val){
                count++;
            }
        }
        System.out.println();
        int[] mins = new int[count];
        Random r = new Random();
        int k = 0;
        for(int i = 0; i < a.length; i++){
            if(a[i] == val) {
                mins[k] = i;
                k++;
            }
        }
        return mins[r.nextInt(count)];
    }*/

    public int gerar(Game atual, char ordem) {
        this.nodes = 0;
        if(ordem == 'O') {
            atual.turno = 'O';
            ArrayList<Integer> poss = atual.movimentosPossiveis();
            int[] vals = new int[7];

            for (int i = 0; i < 7; i++) {
                vals[i] = -9999999;
            }
            for (int move : poss) {
                vals[move] = MIN_VALUE(atual.sucessor(move), depth - 1, -1);
            }
            return max(vals);
        }
        ArrayList<Integer> poss = atual.movimentosPossiveis();
        int[] vals = new int[7];

        for (int i = 0; i < 7; i++) {
            vals[i] = -9999999;
        }
        for (int move : poss) {
            vals[move] = MIN_VALUE(atual.sucessor(move), depth - 1, 1);
        }
        return max(vals);
    }

    public int MAX_VALUE(Game atual, int depth, int ordem){
        nodes++;
        if(depth == 0){
            return (atual.utilidade() * ordem);
        }
        int v = -99999;
        int k;
        ArrayList<Integer> poss = atual.movimentosPossiveis();
        for(int move : poss){
                k = MIN_VALUE(atual.sucessor(move), depth-1, ordem) ;
                if(k > v){
                    v = k;
                }
            }
        return v;
    }


    public int MIN_VALUE(Game atual, int depth, int ordem) {
        nodes++;
        if (depth == 0) {
            return atual.utilidade() * ordem;
        }
        int v = 999999;
        int k;
        ArrayList<Integer> poss = atual.movimentosPossiveis();
        for (int move : poss) {
            k = MAX_VALUE(atual.sucessor(move), depth - 1, ordem);
            if (k <= v) {
                v = k;
            }
        }
        return v;
    }
}