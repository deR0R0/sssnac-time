
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Apple extends Piece {
    private int grid;

    // constructor
    public Apple(int x, int y, int size, int grid) {
        super(x, y, size);
        this.grid = grid;
    }

    // method for jumping
    public void jump() {
        setX(((int) (Math.random() * (grid))) * getSize());
        setY(((int) (Math.random() * (grid))) * getSize());
    }

    // drawing
    public void draw(Graphics g) {
        ImageIcon image = new ImageIcon("apple.png");
        g.drawImage(image.getImage(), getX(), getY(), getSize(), getSize(), null);
    }
}