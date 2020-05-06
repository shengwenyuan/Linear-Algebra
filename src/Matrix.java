import java.util.Scanner;

public class Matrix {
    private int column;
    private int row;
    private double [][] deck = new double[row][column];

    public Matrix() {
    }

    public Matrix(int column, int row, double[][] deck) {
        this(column, row);
        this.deck = deck;
    }

    public Matrix(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public double[][] getDeck() {
        return deck;
    }

    public void setDeck(double[][] deck) {
        this.deck = deck;
    }

    public double[][] writeDeck(){
        Scanner input = new Scanner(System.in);

        for(int i = 0; i < getRow(); i++){
            for(int j = 0; j < getColumn(); j++){
                deck[i][j] = input.nextDouble();
            }
        }

        return deck;
    }


    public Matrix lambdaMultiply(double lambdaNum){
        Matrix matrixReturn = this;
        double[][] newDeck = new double[this.row][this.column];

        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.column; j++){
                newDeck[i][j] = this.deck[i][j] * lambdaNum;
            }
        }

        matrixReturn.setDeck(newDeck);

        return matrixReturn;
    }

    public Matrix inverseMatrix(){

        try {
            if(isSquare(this)) {
                Matrix inverse = adjointMatrix();

                double value = 1 / AlgebraCompMinor(this.getDeck());
                inverse.lambdaMultiply(value);

                return inverse;
            }
            else throw new isSquareException("This matrix is not square.");
        }
        catch (isSquareException e){
            System.out.println(e);
            return this;
        }
    }

    public Matrix adjointMatrix(){
        double[][] tempDeck = new double[this.row - 1][this.column - 1];
        double[][] adjDeck = new double[this.row][this.column];

        Matrix adjoint = new Matrix();
        adjoint.setRow(this.getRow());
        adjoint.setColumn(this.getColumn());

        for(int aRow = 0; aRow < this.getRow(); aRow++){
            for(int aColumn = 0; aColumn < this.getColumn(); aColumn++){

                int x = 0;
                int y = 0;
                for(int i = 0; i < this.row; i++){
                    for (int j = 0; j < this.row; j++){
                        if(i != aRow && j != aColumn) {
                            tempDeck[x][y] = this.getDeck()[i][j];
                            if(y == tempDeck.length - 1){
                                y = 0;
                                x++;
                            }
                            else y++;
                        }

                        adjDeck[aColumn][aRow] = AlgebraCompMinor(tempDeck) * (int) Math.pow((-1), aColumn+aRow);
                    }
                }
            }
        }

        adjoint.setDeck(adjDeck);

        return adjoint;
    }

    private double AlgebraCompMinor(double[][] higherDeck){
        double temp = 0;

        if(higherDeck.length == 2){
            return higherDeck[0][0] * higherDeck[1][1] - higherDeck[1][0] * higherDeck[0][1];
        }
        else {
            for(int aColumn = 0; aColumn < higherDeck.length; aColumn++){
                double[][] newDeck = new double[higherDeck.length - 1][higherDeck.length - 1];
                int x = 0;
                int y = 0;

                for(int i = 1; i < higherDeck.length; i++){
                    for (int j = 0; j < higherDeck.length; j++){
                        if(j != aColumn){
                            newDeck[x][y] = higherDeck[i][j];
                            if(y == newDeck.length - 1){
                                y = 0;
                                x++;
                            }
                            else y++;
                        }

                    }
                }

                temp += AlgebraCompMinor(newDeck) * (int) Math.pow((-1), aColumn) * higherDeck[0][aColumn];
            }
        }

        return temp;
    }

    public Matrix multiply(Matrix newcome){
        double[][] temp = new double[this.column][newcome.row];
        Matrix matrixResult = new Matrix(this.column, newcome.row);

        if(this.column == newcome.row){
            for(int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.column; j++) {
                    for (int ergodicY = 0; ergodicY < this.column; ergodicY++) {
                        temp[i][j] += this.getDeck()[i][ergodicY] * newcome.getDeck()[ergodicY][j];
                    }
                }
            }
            matrixResult.setDeck(temp);

            return matrixResult;
        }

        else {
            System.out.println("The matrix's size can not match.\nThe original matrix is:");
            return this;
        }


    }

    public Matrix plus(Matrix newcome){
        Matrix matrixResult = this;
        if(this.row == newcome.getRow() && this.column == newcome.getColumn()){
            double[][] temp = new double[this.row][this.column];

            for(int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.column; j++) {
                    temp[i][j] = this.getDeck()[i][j] + newcome.getDeck()[i][j];
                }
            }
            matrixResult.setDeck(temp);

            return matrixResult;
        }

        else {
            System.out.println("The size is not matched, cant operate.");
            return this;
        }
    }

    public int rank(){  //秩
        Matrix matrixResult = this.rowEchelonMatrix();
        int rankNum = matrixResult.row;

        for(int i = 0; i < matrixResult.row; i++){
            int count = 0;
            for(int j = 0; j < matrixResult.column;j++){
                if(matrixResult.getDeck()[i][j] == 0)
                    count++;
                else break;
            }
            if(count == matrixResult.row)
                rankNum--;
        }

        return rankNum;
    }

    public Matrix rowSimplestMatrix(){
        Matrix matrixResult = this.rowEchelonMatrix();
        double[][] temp = matrixResult.getDeck();
        double ratio = 1;

        for(int i = 0; i < matrixResult.row; i++){
            int firstNZeroColumn = 0;
            for(; firstNZeroColumn < matrixResult.column;firstNZeroColumn++){
                if(matrixResult.getDeck()[i][firstNZeroColumn] != 0){
                    ratio = 1 / matrixResult.getDeck()[i][firstNZeroColumn];
                    break;
                }
            }

            for(int j = firstNZeroColumn; j < matrixResult.column; j++) {
                temp[i][j] *= ratio;
            }
        }

        out:for (int nowRow = 1; nowRow < matrixResult.row; nowRow++){
            int firstNZeroColumn = 0;
            for (; firstNZeroColumn < matrixResult.column; firstNZeroColumn++){
                if(temp[nowRow][firstNZeroColumn] == 1.0) break;
                else if(temp[nowRow][firstNZeroColumn] == 0 && firstNZeroColumn == matrixResult.column - 1)
                    break out;
            }
            for (int aboveRow = 0; aboveRow < nowRow; aboveRow++){
                ratio = (-1) * temp[aboveRow][firstNZeroColumn];
                for (int j = firstNZeroColumn; j < matrixResult.column; j++){
                    temp[aboveRow][j] += ratio * temp[nowRow][j];
                }
            }
        }
        matrixResult.setDeck(temp);

        return matrixResult;
    }

    public Matrix rowEchelonMatrix(){
        Matrix matrixResult = this;
        double[][] temp = this.getDeck();
        double ratio = 1;

        for(int nowRow = 0; nowRow < temp.length; nowRow++){
            int startColumn = nowRow;
            int startRow = nowRow;

            mid: for(; startColumn < temp[0].length; startColumn++){
                for(; startRow < temp.length; startRow++){
                    if(temp[startRow][startColumn] == 0){
                        swapLine(temp, nowRow, startColumn);
                    }
                    else break mid;
                }
            }

            for(int i = startRow + 1; i < temp.length; i++){
                ratio = (-1) * temp[i][startColumn] / temp[startRow][startColumn];
                for(int j = startColumn; j < temp[0].length;j++){
                    temp[i][j] += temp[startRow][j] * ratio;
                }
            }
        }

        matrixResult.setDeck(temp);

        return matrixResult;
    }

    private void swapLine(double[][] deal, int first, int second){
        double[][] temp = deal;

        for(int j = 0; j < deal[0].length;j++){
            temp[first][j] = deal[second][j];
            deal[second][j] = deal[first][j];
            deal[first][j] = temp[first][j];
        }

//        return deal;
    }

    public Matrix rowJoint(Matrix newcome){    //result matrix = (first, second)
        if(this.row == newcome.row){
            Matrix matrixResult = new Matrix( (this.column + newcome.column), this.row);
            double[][] temp = new double[matrixResult.row][matrixResult.column];

            for(int i = 0; i < matrixResult.row; i++){
                int this_j = 0;
                int newcome_j = 0;
                for(int j = 0; j < matrixResult.column;j++){
                    if(this_j < this.column) {
                        temp[i][j] = this.getDeck()[i][this_j++];
                    }
                    else {
                        temp[i][j] = newcome.getDeck()[i][newcome_j++];
                    }
                }
            }
            matrixResult.setDeck(temp);

            return matrixResult;
        }
        else {
            System.out.println("The size of matrices is unmatched, can not joint in row");
            return this;
        }
    }

    @Override
    public String toString() {
        String result = "";
        for(int i = 0; i < row; i++){
            result += '|';
            for (int j = 0; j < column; j++) {
                result += deck[i][j];
                if(j != column - 1)
                    result += ' ';
                else {
                    result += "|\n";
                }
            }
        }

        return result;
    }

    public Boolean isSquare(Matrix test){
        if(test.getRow() == test.getColumn())
            return true;
        else
            return false;
    }
}

class isSquareException extends Exception{
    public isSquareException(String message) {
        super(message);
    }
}

class denominatorZeroException extends Exception{
    public denominatorZeroException(String message) {
        super(message);
    }
}