import edu.macalester.graphics.Image;

public class Mark {
    private int column;
    private int row;
    private Image symbol;

    public Mark(String imagePath, int row, int column) {
        symbol = new Image(0, 0);
        symbol.setImagePath(imagePath);
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setMaxSize(double size) {
        symbol.setMaxHeight(size);
        symbol.setMaxWidth(size);
    }

    public void setCenter(double x, double y) {
        symbol.setCenter(x, y);
    }

    public Image getSymbol() {
        return symbol;
    }
}
