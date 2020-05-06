import java.util.Scanner;

public class Determinant
{
    private int size;
    private double [][] deck;

    public Determinant(Matrix himatrix) {
        this.size = himatrix.getColumn();
        this.deck = himatrix.getDeck();
    }

    public Determinant(int n) {
        size = n;

        Scanner input = new Scanner(System.in);

        System.out.println("");
        deck = new double[size][size];
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
                deck[i][j] = input.nextDouble();
        }


//        deck = new int[][]{{1, 2, -4}, {-2, 2, 1}, {-3, 4, -2}};
//        deck = new int[][]{{3, 1, 1, 1}, {1, 3, 1, 1}, {1, 1, 3, 1}, {1, 1, 1, 3} };
    }

    public double run()
    {
        return calculate(deck);
    }
    public void showout()   //special for determinant_test
    {
        resultOutPut(calculate(deck), deck);
    }

    private double calculate(double[][]deck)
    {
        int temp = 0;
        if(deck.length == 2){
            return deck[0][0] * deck[1][1] - deck[1][0] * deck[0][1];
        }
        else {
            for (int j = 0; j < deck.length; j++)
                temp += reduceOrder(deck, j) * (int) Math.pow((-1), j) * deck[0][j];    //一个元与其代数余子式的乘积
            return temp;
        }
    }

    private double reduceOrder(double[][] highOrderDeck, int jOfa)   //余子式展开(降阶)
    {
        if (highOrderDeck.length == 2){
            return highOrderDeck[0][0] * highOrderDeck[1][1] - highOrderDeck[1][0] * highOrderDeck[0][1];
        }

        else {
            int size = highOrderDeck.length;
            double[][] deck = new double[size - 1][size - 1];
            int x = 0, y = 0;

            for (int i = 1; i < highOrderDeck.length; i++) {
                for (int j = 0; j < highOrderDeck.length; j++) {
                    if (j != jOfa) {
                        deck[x][y] = highOrderDeck[i][j];
                        if (y == deck.length - 1) {
                            x++;
                            y = 0;
                        }
                        else
                            y++;
                    }
                }
            }

            return calculate(deck);
        }
    }

    private void resultOutPut(double result, double[][] deck)
    {
        for(int i = 0; i < size; i++)
        {
            System.out.print("\n|");
            for(int j = 0; j < size; j++)
            {
                System.out.printf("%d", deck[i][j]);
                if(j == size - 1)
                    System.out.print("|");
                else
                    System.out.print(" ");
            }
        }

        System.out.printf("\n=%d", result);

    }

    public int getSize() {
        return size;
    }


}
