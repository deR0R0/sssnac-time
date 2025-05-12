
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class SnakeBody extends SnakePiece {
    private int targetedX;
    private int targetedY;
    private int grid;
    
    // constructor
    public SnakeBody(int x, int y, int size, int speed, String direction, SnakePiece leader, SnakePiece follower, int grid) {
        super(x, y, size, speed, direction, leader, follower);
        // These will store the positions we're currently moving toward
        targetedX = leader.getX();
        targetedY = leader.getY();
        this.grid = grid;
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
        if (getLeader() != null) {
            // get the leader's past position
            int[] pastPos = getLeader().getPastPos().get(getSize() - 1);
            setX(pastPos[0]);
            setY(pastPos[1]);
        }
    }

    // method for drawing
    @Override
    public void draw(Graphics g) {
        // get the default image for snake body
        ImageIcon imageIcon = new ImageIcon("snake_body_right.png");

        // draw the image
        g.drawImage(imageIcon.getImage(), getX(), getY(), getSize(), getSize(), null);
    }
}
