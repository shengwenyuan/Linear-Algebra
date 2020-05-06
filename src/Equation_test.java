public class Equation_test {
    public static void main(String[] args) {

        Matrix test1 = new Matrix();
        test1.setRow(3);
//        test1.setRow(2);
        test1.setColumn(3);
        double[][] first = { {1, 2, 3}, {2, 2, 1}, {2, 4, 6} };
//        double[][] first = { {1, 2, 3}, {2, 2, 1} };
        test1.setDeck(first);

        Matrix zeroConstant = new Matrix(1, 3);
        double[][] zeros = { {0}, {0}, {0} };
        zeroConstant.setDeck(zeros);

        Equation testing = new Equation(3, 3, test1, zeroConstant);
        testing.operate();
    }
}
