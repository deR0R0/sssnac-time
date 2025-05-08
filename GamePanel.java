// Code Written by Robert Zhao and Nihal Gorthi
// Date 5/2/2025
// Made for Computer Science Foundations Project @ TJHSST
// © 2025 Robert Zhao and Nihal Gorthi

/*
   TODO:
   ✔️ Implement "snapping" to the grid (through the turning)
   - Fix the latency of the snake turning, potential solution: queue the inputs in an array, then process them when the input unlocks
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
   private static final int GRID = 15; // gridsize x gridsize
   private static final int loopDelay = 5; // delay in milliseconds
   private final BackgroundGrid BACKGROUND;
   private BufferedImage myImage;
   private Graphics myBuffer;
   private Timer t;

   // stuff initiazliated after the game starts
   private SnakeHead player;
   private Apple apple;

   // time based fields
   private int tick = 0;

   // constructors, or basically the setup for the game
   public GamePanel() {
      // create the buffered image for a smoother game
      myImage =  new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();

      // create the background grid
      BACKGROUND = new BackgroundGrid(GRID, GRID, new Color(143, 205, 57), new Color(168, 217, 72), FRAME, FRAME);

      // create the player
      player = new SnakeHead(0, 0, FRAME/GRID, 1, "RIGHT", null, null);

      // create apple yummy
      apple = new Apple(0, 0, FRAME/GRID, GRID);
      apple.jump();

      // start the game loop without blocking the gui threads
      t = new Timer(loopDelay, new GameLoop()); // 840 / the integer = fps. 840 = 
      t.start();

      // allow mouse clicks and stuff
      setFocusable(true);

      // create lsiteners
      addKeyListener(new Keyboard());
   }

   public class Keyboard extends KeyAdapter {
      public void snapToGridX(String direction) {
         if(player.getX() % (FRAME / GRID) > (FRAME / GRID)/2) {
            player.setX(player.getX() + (FRAME / GRID) - (player.getX() % (FRAME / GRID)));
         } else {
            player.setX(player.getX() - (player.getX() % (FRAME / GRID)));
         }
      }

      public void snapToGridY() {
         if(player.getY() % (FRAME / GRID) > (FRAME / GRID)/2) {
            player.setY(player.getY() + (FRAME / GRID) - (player.getY() % (FRAME / GRID)));
         } else {
            player.setY(player.getY() - (player.getY() % (FRAME / GRID)));
         }
      }

      @Override
      public void keyPressed(KeyEvent e) {
         // TODO: reimplememt moving :O
         switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
               
            }
            case KeyEvent.VK_DOWN -> {
               
            }
            case KeyEvent.VK_LEFT -> {
               
            }
            case KeyEvent.VK_RIGHT -> {
               
            }
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

         apple.draw(myBuffer);

         // move the player and draw it
         player.move();
         player.draw(myBuffer);

         // Paint
         repaint();

         // ticking system to handle the speed of the game
         tick++;
      }
   }
}