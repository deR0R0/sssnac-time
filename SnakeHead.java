import java.awt.Graphics;
import javax.swing.ImageIcon;

public class SnakeHead extends SnakePiece {
    // constructor
    public SnakeHead(int x, int y, int size, int speed, String direction, SnakePiece leader, SnakePiece follower) {
        super(x, y, size, speed, direction, leader, follower);
    }

    // methods for moving
    @Override
    public void move() {
        switch (getDirection()) {
            case "UP" -> setY(getY() - getSpeed());
            case "DOWN" -> setY(getY() + getSpeed());
            case "LEFT" -> setX(getX() - getSpeed());
            case "RIGHT" -> setX(getX() + getSpeed());
            default -> {}
        }
    }

    @Override
    public void draw(Graphics g) {
        ImageIcon image = new ImageIcon("snake_head_" + getDirection().toLowerCase() + ".png");
        g.drawImage(image.getImage(), getX(), getY(), getSize(), getSize(), null);
    }
}