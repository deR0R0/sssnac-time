import java.awt.Graphics;

public abstract class Piece {
    private int x;
    private int y;
    private int size;

    // constructor
    public Piece(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    // getter methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    // setter methods
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public abstract void draw(Graphics g);
}