public abstract class SnakePiece extends Piece {
    private int speed;
    private String direction;
    private SnakePiece leader;
    private SnakePiece follower;

    // constructor
    public SnakePiece(int x, int y, int size, int speed, String direction, SnakePiece leader, SnakePiece follower) {
        super(x, y, size);
        this.speed = speed;
        this.direction = direction;
        this.leader = leader;
        this.follower = follower;
    }

    // getter methods
    public int getSpeed() {
        return speed;
    }

    public String getDirection() {
        return direction;
    }

    public SnakePiece getLeader() {
        return leader;
    }

    public SnakePiece getFollower() {
        return follower;
    }

    // setter methods
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setLeader(SnakePiece leader) {
        this.leader = leader;
    }

    public void setFollower(SnakePiece follower) {
        this.follower = follower;
    }

    // methods for moving
    public abstract void move();
}