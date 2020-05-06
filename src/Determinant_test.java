import java.util.Scanner;

public class Determinant_test {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the size of square and the numbers by line:");
        Determinant determinant = new Determinant(input.nextInt());
        determinant.showout();
    }

}
