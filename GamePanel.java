// Code Written by Robert Zhao and Nihal Gorthi
// Date 5/2/2025
// Made for Computer Science Foundations Project @ TJHSST
// © 2025 Robert Zhao and Nihal Gorthi

/*
   TODO:
   ✔️ Implement "snapping" to the grid (through the turning)
   ✔️ Fix the latency of the snake turning, potential solution: queue the inputs in an array, then process them when the input unlocks
   ✔️ Implement the apple spawning. OPTIONAL: make the apple float up and down
   - Implement apple spawning in random locations when eaten
   - Implement the snake growing when it eats the apple through the body use of leadership and follwership ideology
   - Implement the snake dying when it runs into itself or the wall (the wall is the edge of the screen)

   - Implement other modes (eat apple, go faster)
   - Crazy Mode or Dual Mode

   OPTIONAL:
   - Make movement smoother?
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
   private int playerRotation = 0;
   private Apple apple;

   // time based fields
   private int tick = 0;
   private KeyEvent queuedInput = null;
   private int tickTillResetSpeed = 0;
   private boolean lockInput = false;

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
      @Override
      public void keyPressed(KeyEvent e) {
         int keycode = e.getKeyCode();
         if(keycode == KeyEvent.VK_UP || keycode == KeyEvent.VK_DOWN || keycode == KeyEvent.VK_LEFT || keycode == KeyEvent.VK_RIGHT) {
            queuedInput = e;
         }
      }
   }
   
   // override the paintComponent method to draw the buffered image
   public void paintComponent(Graphics g) {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }

   public void snapToGridX() {
      // if player is in the left half of the grid, snap to the grid
      if(player.getX() % (FRAME / GRID) <= (FRAME / GRID / 2)) {
         player.setX(player.getX() - (player.getX() % (FRAME / GRID)));
      } else { // if player is in the right half of the grid, snap to next grid
         player.setX(player.getX() + (FRAME / GRID) - (player.getX() % (FRAME / GRID)));
         player.setSpeed(2);
      }

      tickTillResetSpeed = tick + ((FRAME / GRID) / 5);
   }

   public void snapToGridY() {
      // same as snapToGridX();
      if(player.getY() % (FRAME / GRID) <= (FRAME / GRID / 2)) {
         player.setY(player.getY() - (player.getY() % (FRAME / GRID)));
      } else {
         player.setY(player.getY() + (FRAME / GRID) - (player.getY() % (FRAME / GRID)));
         player.setSpeed(2);
      }

      tickTillResetSpeed = tick + ((FRAME / GRID) / 5);
   }

   public void handlePlayerTurning() {
      // handle player turning
      if(queuedInput != null && !lockInput) {
         int key = queuedInput.getKeyCode();
         String direction = "";

         // check if the key pressed is a valid direction
         switch(key) {
            case KeyEvent.VK_UP -> direction = "UP";
            case KeyEvent.VK_DOWN -> direction = "DOWN";
            case KeyEvent.VK_LEFT -> direction = "LEFT";
            case KeyEvent.VK_RIGHT -> direction = "RIGHT";
         }

         // check whether or not the player is already moving in that direction
         // if player is, remove the input from the queue
         if(direction.equals(player.getDirection())) {
            queuedInput = null;
            return;
         }

         // actually handle the turning
         switch(key) {
            case KeyEvent.VK_UP -> {
               if(!player.getDirection().equals("DOWN")) {
                  player.setDirection("UP");
                  snapToGridX();
               }
            }
            case KeyEvent.VK_DOWN -> {
               if(!player.getDirection().equals("UP")) {
                  player.setDirection("DOWN");
                  snapToGridX();
               }
            }
            case KeyEvent.VK_LEFT -> {
               if(!player.getDirection().equals("RIGHT")) {
                  player.setDirection("LEFT");
                  snapToGridY();
               }
            }
            case KeyEvent.VK_RIGHT -> {
               if(!player.getDirection().equals("LEFT")) {
                  player.setDirection("RIGHT");
                  snapToGridY();
               }
            }
         }
         queuedInput = null;
         lockInput = true;
      }
   }

   public void handlePlayerActions() {
      // unlock input after set amount of time
      if(tick >= tickTillResetSpeed + ((FRAME / GRID) / 2)) {
         lockInput = false;
         tickTillResetSpeed = 0;
      }

      // reset player speed when tick has been reached
      if(tick >= tickTillResetSpeed) {
         player.setSpeed(1);
      }

      // move the player and draw it
      handlePlayerTurning();
      player.move();
      player.draw(myBuffer, playerRotation);
   }

   // game loop
   private class GameLoop implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         // erase previous frame by overlaying the background
         BACKGROUND.draw(myBuffer);

         // draw the apple yumy
         apple.draw(myBuffer);

         // player actions
         handlePlayerActions();

         // Paint
         repaint();

         // ticking system to handle the speed of the game
         tick++;
      }
   }
}