
import java.awt.Color;
import java.awt.Graphics;

public class Scoreboard extends Piece {
    private int score;
    private int gridSize;

    public Scoreboard(int x, int y, int size, int gridSize) {
        super(x, y, size);
        this.score = 0;
        this.gridSize = gridSize;
    }

    // getters
    public int getScore() {
        return score;
    }

    // setters
    public void increaseScore() {
        score++;
    }

    public void increaseScore(int amount) {
        score += amount;
    }

    // method to draw
    public void draw(Graphics g) {
        System.out.println("Wrong method");
    }

    // method to draw
    public void draw(Graphics g, int transparency) {
        // Set font to make text larger and more visible
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        
        // Draw with a more visible color (white with transparency)
        g.setColor(new Color(255, 255, 255, transparency));
        
        // Position the score text in a more visible location (padding from edge)
        g.drawString("Score: " + score, getX() + 15, getY() + 30);
    }
}