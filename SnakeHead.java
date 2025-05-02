// I accidnetly implmeneted the snakehead using the wrong class... ill fix it later
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class SnakeHead extends Piece {
    private int speed;
    private int frame;
    private int gridAmount;
    private String direction;

    // constructor
    public SnakeHead(int x, int y, int size, int speed, String direction, int frame, int gridAmount) {
        super(x, y, size);
        this.speed = speed;
        this.direction = direction;
        this.frame = frame;
        this.gridAmount = gridAmount;
    }

    // methods for moving
    public void move() {
        switch (direction) {
            case "UP" -> setY(getY() - speed);
            case "DOWN" -> setY(getY() + speed);
            case "LEFT" -> setX(getX() - speed);
            case "RIGHT" -> setX(getX() + speed);
            default -> {}
        }
    }

    // getter for direction
    public String getDirection() {
        return direction;
    }

    // setter for direction
    public void setDirection(String direction) {
        this.direction = direction;
    }



    @Override
    public void draw(Graphics g) {
        ImageIcon image = new ImageIcon("snake_head_" + direction.toLowerCase() + ".png");
        g.drawImage(image.getImage(), getX(), getY(), (this.frame/this.gridAmount), (this.frame/this.gridAmount), null);
    }
}