import java.util.ArrayList;

public class Equation {
    private int row;
    private int column;
    private Matrix coefficient;
    private Matrix constant;    //inhomogeneous items (right of =)

    public Equation() {
    }

    public Equation(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Equation(int row, int column, Matrix coefficient, Matrix constant) {
        this(row, column);
        this.coefficient = coefficient;
        this.constant = constant;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Matrix getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Matrix coefficient) {
        this.coefficient = coefficient;
    }

    public Matrix getConstant() {
        return constant;
    }

    public void setConstant(Matrix constant) {
        this.constant = constant;
    }

    private Boolean hasRoot(){
        Matrix matrixResult = this.coefficient.rowJoint(this.constant);

        if(matrixResult.rank() > this.coefficient.rank()) return false;
        else return true;
    }

    private Boolean isHomogeneous(){
        for(double[] test : this.constant.getDeck()){
            for(double testagain : test) {
                if (testagain != 0) return false;
            }
        }

        return true;
    }
    public void operate(){
        if(hasRoot()) {
            if(isHomogeneous()){
                homogeneousEquation();
            }
            else inhomogeneousEquation();
        }
        else {
            System.out.println("The equations do not have real roots");
        }
    }

    public void homogeneousEquation(){  //齐次方程
        Matrix matrixResult = this.coefficient.rowSimplestMatrix();
        int rank = matrixResult.rank();

        ArrayList<ArrayList<Double>> roots = new ArrayList<ArrayList<Double>>();
        for (int j = rank; j < matrixResult.getColumn(); j++) {
            ArrayList<Double> Xj_withinRank = new ArrayList<Double>();
            for (int i = 0; i < rank; i++) {
                Xj_withinRank.add(matrixResult.getDeck()[i][j] * (-1));
            }
            for(int i = 0; i < matrixResult.getColumn() - rank; i++){
                if(i == j) Xj_withinRank.add(1.0);
                else Xj_withinRank.add(0.0);
            }
            roots.add(Xj_withinRank);
        }

        outputRoots(roots);
    }

    public void inhomogeneousEquation(){

    }

    private void outputRoots(ArrayList<ArrayList<Double>> roots){
        for(int i = 0; i < roots.get(0).size(); i++){

            for(int j = 0; j < roots.size();j++){
                if(i == roots.get(0).size() / 2) System.out.printf("+c_%d", j);
                System.out.printf("|%f| ", roots.get(j).get(i));
            }

            System.out.println();
        }
    }
}
