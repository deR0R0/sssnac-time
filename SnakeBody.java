
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class SnakeBody extends SnakePiece {
    private int grid;
    private String sprite = "snake_body_right.png";
    
    // constructor
    public SnakeBody(int x, int y, int size, int speed, String direction, SnakePiece leader, SnakePiece follower, int grid) {
        super(x, y, size, speed, direction, leader, follower);
        this.grid = grid;
    }

    public void handleSnakeSprite(int[] pastPos) {
        // direction of snake is based off of the leader's past position
        if (getLeader().getY() == getY()) {
            sprite = "snake_body_right.png";
        } else if (getLeader().getX() == getX()) {
            sprite = "snake_body_up.png";
        } else if (getLeader().getX() > getX() && getLeader().getY() > getY()) {
            sprite = "snake_body_1.png";
        } else if (getLeader().getX() < getX() && getLeader().getY() > getY()) {
            sprite = "snake_body_4.png";
        } else if (getLeader().getX() > getX() && getLeader().getY() < getY()) {
            sprite = "snake_body_2.png";
        } else if (getLeader().getX() < getX() && getLeader().getY() < getY()) {
            sprite = "snake_body_3.png";
        }
    }

    // methods for moving
    @Override
    public void move() {
        // update current old position
        getPastPos().addFirst(new int[]{getX(), getY()});
        if (getPastPos().size() > 100) {
            getPastPos().removeLast();
        }

        // move the snake body based on leader's past position
        if (getLeader() != null && getLeader().getPastPos().size() > (getSize() / getSpeed()) - 1) {
            // get the leader's past position
            int[] pastPos = getLeader().getPastPos().get((getSize() / getSpeed()) - 1);
            setX(pastPos[0]);
            setY(pastPos[1]);
            handleSnakeSprite(pastPos);
        }
    }

    // method for drawing
    @Override
    public void draw(Graphics g) {
        // get the default image for snake body
        ImageIcon imageIcon = new ImageIcon(sprite);

        // draw the image
        g.drawImage(imageIcon.getImage(), getX(), getY(), getSize(), getSize(), null);
    }
}
