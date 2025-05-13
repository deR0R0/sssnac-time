import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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

        getPastPos().addFirst(new int[]{getX(), getY()});
        if (getPastPos().size() > 100) {
            getPastPos().removeLast();
        }
    }

    public BufferedImage getRotatedImage(int angle, ImageIcon imageIcon) {
        // rotate the image by the given angle
        BufferedImage image = new BufferedImage(getSize(), getSize(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.rotate(Math.toRadians(angle), getSize() / 2, getSize() / 2);
        g2d.drawImage(imageIcon.getImage(), 0, 0, getSize(), getSize(), null);
        g2d.dispose();
        return image;
    }

    @Override
    public void draw(Graphics g) {
        // get the default image for snake head :O
        ImageIcon imageIcon = new ImageIcon("snake_head_right.png");

        // draw the image
        g.drawImage(getRotatedImage(0, imageIcon), getX(), getY(), getSize(), getSize(), null);
    }

    public void draw(Graphics g, int angle, boolean dead) {
        // get the default image for snake head :O
        ImageIcon imageIcon;
        if(!dead)
            imageIcon = new ImageIcon("snake_head_right.png");
        else
            imageIcon = new ImageIcon("snake_head_right_dead.png");

        // draw the image
        g.drawImage(getRotatedImage(angle, imageIcon), getX(), getY(), getSize(), getSize(), null);
    }
}