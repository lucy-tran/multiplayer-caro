import edu.macalester.graphics.Rectangle;

public class BoardSquare extends Rectangle {
    private int row;
    private int column;

    public BoardSquare(double x, double y, double width, double height, int row, int column) {
        super(x, y, width, height);
        this.row = row;
        this.column = column;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

}
