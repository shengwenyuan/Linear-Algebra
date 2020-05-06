public class Matrix_test {
    public static void main(String[] args) {
        Matrix test1 = new Matrix();
//        test1.setRow(3);
        test1.setRow(2);
        test1.setColumn(3);
//        double[][] first = { {1, 2, 3}, {2, 2, 1}, {2, 4, 6} };
        double[][] first = { {1, 2, 3}, {2, 2, 1} };
        test1.setDeck(first);

//        System.out.printf("Your matrix:\n%s's inversed matrix is:\n", test1);
//
//        System.out.print(test1.rowEchelonMatrix());
//        System.out.printf("The rank of test1 is: %d\n", test1.rank());

        System.out.println(test1.rowJoint(test1));
    }
}
