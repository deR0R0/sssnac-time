// Code Written by Robert Zhao and Nihal Gorthi
// Date 5/2/2025
// Made for Computer Science Foundations Project @ TJHSST
// © 2025 Robert Zhao and Nihal Gorthi

/*
   TODO:
   - Implement "snapping" to the grid (through the turning)
   - Implement the apple spawning. OPTIONAL: make the apple float up and down
   - Implement the snake growing when it eats the apple through the body use of leadership and follwership ideology
   - Implement the snake dying when it runs into itself or the wall (the wall is the edge of the screen)

   - Implement other modes (eat apple, go faster)
   - Crazy Mode or Dual Mode
*/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
public class GamePanel extends JPanel {
   private static final int FRAME = 900;
   private final BackgroundGrid BACKGROUND;
   private BufferedImage myImage;
   private Graphics myBuffer;
   private Timer t;

   private SnakeHead player;

   // constructors, or basically the setup for the game
   public GamePanel() {
      // create the buffered image for a smoother game
      myImage =  new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();
      // create the background grid
      BACKGROUND = new BackgroundGrid(15, 15, new Color(143, 205, 57), new Color(168, 217, 72), FRAME, FRAME);
      // create the player
      player = new SnakeHead(0, 0, 60, 1, "RIGHT", 900, 15);
      // start the game loop without blocking the gui threads
      t = new Timer(5, new GameLoop());
      t.start();
      // allow mouse clicks and stuff
      setFocusable(true);

      // create lsiteners
      addKeyListener(new Keyboard());
   }

   public class Keyboard extends KeyAdapter {
      @Override
      public void keyPressed(KeyEvent e) {
         // check for key presses and change the direction of the player
         switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
               if(!player.getDirection().equals("DOWN"))
                  player.setDirection("UP");
               break;
            case KeyEvent.VK_DOWN:
               if(!player.getDirection().equals("UP"))
                  player.setDirection("DOWN");
               break;
            case KeyEvent.VK_LEFT:
               if(!player.getDirection().equals("RIGHT"))
                  player.setDirection("LEFT");
               break;
            case KeyEvent.VK_RIGHT:
               if(!player.getDirection().equals("LEFT"))
                  player.setDirection("RIGHT");
               break;
         }
      }
   }
   

   // override the paintComponent method to draw the buffered image
   public void paintComponent(Graphics g) {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }

   // game loop
   private class GameLoop implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         // Erase previous frame by overlaying the background
         BACKGROUND.draw(myBuffer);
         player.move();
         player.draw(myBuffer);
         // Paint
         repaint();
      }
   }
}