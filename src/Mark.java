import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;

public class Mark {
    private int column;
    private int row;
    private Image symbol;

    public Mark(String imagePath, int column, int row){
        symbol = new Image(0,0);
        symbol.setImagePath(imagePath);
        this.column = column;
        this.row = row;
    }

    public int getColumn(){
        return column;
    }

    public int getRow(){
        return row;

    }

    public void setMaxSize(double size){
        symbol.setMaxHeight(size);
        symbol.setMaxWidth(size);
    }

    public void setCenter(Point location){
        symbol.setCenter(location.getX(), location.getY());
    }

    public Image getSymbol(){
        return symbol;
    }
}
