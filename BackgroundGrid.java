import java.awt.Color;
import java.awt.Graphics;

public class BackgroundGrid {
    private int gridX;
    private int gridY;
    private Color color1;
    private Color color2;
    private int width;
    private int height;

    // default constructor
    public BackgroundGrid() {
        this.gridX = 10;
        this.gridY = 10;
        this.color1 = new Color(143, 205, 57); // green
        this.color2 = new Color(168, 217, 72); // lighter green
        this.width = 900;
        this.height = 900;
    }

    // custom constructor
    public BackgroundGrid(int gridX, int gridY, Color color1, Color color2, int width, int height) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.color1 = color1;
        this.color2 = color2;
        this.width = width;
        this.height = height;
    }

    // getter methods
    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public Color getColor1() {
        return color1;
    }

    public Color getColor2() {
        return color2;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // setter methods

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    // draw the grid

    public void draw(Graphics myBuffer) {
        for(int i = 0; i < width; i += width / gridX) {
            for(int j = 0; j < height; j += height / gridY) {
                if((i / (width / gridX)) % 2 == (j / (height / gridY)) % 2) {
                    myBuffer.setColor(color1);
                } else {
                    myBuffer.setColor(color2);
                }
                myBuffer.fillRect(i, j, width / gridX, height / gridY);
            }
        }
    }
    
}