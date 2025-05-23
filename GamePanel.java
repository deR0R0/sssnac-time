// Code Written by Robert Zhao and Nihal Gorthi
// Date 5/2/2025
// Made for Computer Science Foundations Project @ TJHSST
// © 2025 Robert Zhao and Nihal Gorthi

/*
   TODO:
   ✔️ Implement "snapping" to the grid (through the turning)
   ✔️ Fix the latency of the snake turning, potential solution: queue the inputs in an array, then process them when the input unlocks
   ✔️ Implement the apple spawning. OPTIONAL: make the apple float up and down
   ✔️ Implement apple spawning in random locations when eaten
   ✔️ Implement the snake growing when it eats the apple through the body use of leadership and follwership ideology
   - Implement the snake dying when it runs into itself or the wall (the wall is the edge of the screen)

   - Implement other modes (eat apple, go faster)
   - Crazy Mode or Dual Mode

   OPTIONAL:
   - Make movement smoother?
*/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.util.LinkedList;
import javax.swing.*;

public class GamePanel extends JPanel {
   private JFrame frame = new JFrame("Game Frame - Standard");

   private static final int FRAME = 900;
   private static final int GRID = 15; // gridsize x gridsize
   private static final int loopDelay = 5; // delay in milliseconds
   private final BackgroundGrid BACKGROUND;
   private final Scoreboard SCOREBOARD;
   private int scoreBoardTransparency = 200;
   private BufferedImage myImage;
   private Graphics myBuffer;
   private Timer t;
   private boolean gameOver = false;

   // stuff initiazliated after the game starts
   private SnakeHead player;
   private int playerSpeed;
   private int playerRotation = 0;
   private int playerRotateTo = 0;
   private int playerRotationSpeed = 1; // dynamic, based on early and late turns

   private Apple apple;

   private LinkedList<SnakePiece> snakeBody = new LinkedList<>();

   private int score = 0;

   // time based fields
   private int tick = 0;
   private int tickUntilBackHome = 0;
   private KeyEvent queuedInput = null;
   private int tickTillResetSpeed = 0;
   private boolean lockInput = false;

   private Font whaleITried;
   private String causeOfDeath = "Not Dead Yet";

   // constructors, or basically the setup for the game
   public GamePanel(int mode, JFrame frame) {
      this.frame = frame;

      try {
         whaleITried = Font.createFont(Font.TRUETYPE_FONT, new File("whale-i-tried.ttf")).deriveFont(100f);
      } catch (Exception e) {
         e.printStackTrace();
      }
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(whaleITried);
      // create the buffered image for a smoother game
      myImage =  new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();      // create the background grid and scoreboard
      BACKGROUND = new BackgroundGrid(GRID, GRID, new Color(143, 205, 57), new Color(168, 217, 72), FRAME, FRAME);
      SCOREBOARD = new Scoreboard(5, 5, 12345, GRID); // Moved away from the edge for better visibility

      // create the player
      switch(mode) {
         case 1 -> {playerSpeed = 1;}
         case 2 -> {playerSpeed = 2;}
         case 3 -> {playerSpeed = 3;}
      }
      player = new SnakeHead(FRAME/GRID*3, FRAME/GRID*7, FRAME/GRID, playerSpeed, "RIGHT", null, null);

      // create apple yummy
      apple = new Apple(FRAME/GRID*10, FRAME/GRID*7, FRAME/GRID, GRID);

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
         if(keycode == KeyEvent.VK_UP || keycode == KeyEvent.VK_DOWN || keycode == KeyEvent.VK_LEFT || keycode == KeyEvent.VK_RIGHT || keycode == KeyEvent.VK_W || keycode == KeyEvent.VK_A || keycode == KeyEvent.VK_S || keycode == KeyEvent.VK_D) {
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
         playerRotationSpeed = 5;
      } else { // if player is in the right half of the grid, snap to next grid
         player.setX(player.getX() + (FRAME / GRID) - (player.getX() % (FRAME / GRID)));
         if(playerSpeed == 1)
            player.setSpeed(2);
         playerRotationSpeed = 3;
      }

      tickTillResetSpeed = tick + ((FRAME / GRID) / 5);
   }

   public void snapToGridY() {
      // same as snapToGridX();
      if(player.getY() % (FRAME / GRID) <= (FRAME / GRID / 2 + 10)) {
         player.setY(player.getY() - (player.getY() % (FRAME / GRID)));
         playerRotationSpeed = 5;
      } else {
         player.setY(player.getY() + (FRAME / GRID) - (player.getY() % (FRAME / GRID)));
         if(playerSpeed == 1)
            player.setSpeed(2);
         playerRotationSpeed = 3;
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
            case KeyEvent.VK_UP, KeyEvent.VK_W -> {
               if(!player.getDirection().equals("DOWN")) {
                  player.setDirection("UP");
                  snapToGridX();
               }
            }
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
               if(!player.getDirection().equals("UP")) {
                  player.setDirection("DOWN");
                  snapToGridX();
               }
            }
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
               if(!player.getDirection().equals("RIGHT")) {
                  player.setDirection("LEFT");
                  snapToGridY();
               }
            }
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
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

   public void handleRotation() {
      // get the angles of the player to rotate to
      switch(player.getDirection()) {
         case "UP" -> playerRotateTo = 270;
         case "DOWN" -> playerRotateTo = 90;
         case "LEFT" -> playerRotateTo = 180;
         case "RIGHT" -> playerRotateTo = 0;
      }

      if(playerRotation != playerRotateTo) {
         // check whether or not the player is within the range,
         // if player is, just set to the angle
         if(Math.abs(playerRotation - playerRotateTo) <= 5) {
            playerRotation = playerRotateTo;
         }
         // actual rotation if not in range
         if(playerRotation < playerRotateTo) {
            playerRotation += playerRotationSpeed;
         } else if(playerRotation > playerRotateTo) {
            playerRotation -= playerRotationSpeed;
         }
      }
   }

   public void handlePlayerActions() {
      // unlock input after set amount of time
      if(tick >= tickTillResetSpeed + (((FRAME / GRID) / 2) / (player.getSpeed()*2))) {
         lockInput = false;
         tickTillResetSpeed = 0;
      }

      // reset player speed when tick has been reached
      if(tick >= tickTillResetSpeed && playerSpeed == 1) {
         player.setSpeed(playerSpeed);
      }

      // move the player and draw it
      if(!gameOver) {
         handlePlayerTurning();
         handleRotation();
         player.move();
      }
      player.draw(myBuffer, playerRotation, gameOver);
   }

   public int getDistance(Piece a, Piece b) {
      // get the distance between two pieces
      return (int) Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
   }

   public void addSnakePiece() {
      // determine which end to add the snake piece to)
      if(!snakeBody.isEmpty()) {
         SnakePiece last = snakeBody.getLast();
         // offset the snake piece
         int newSnakeX = last.getX();
         int newSnakeY = last.getY();
         
         switch(player.getDirection()) {
            case "UP" -> newSnakeY += FRAME/GRID;
            case "DOWN" -> newSnakeY -= FRAME/GRID;
            case "LEFT" -> newSnakeX += FRAME/GRID;
            case "RIGHT" -> newSnakeX -= FRAME/GRID;
         }

         SnakeBody newBody = new SnakeBody(newSnakeX, newSnakeY, FRAME/GRID, playerSpeed, player.getDirection(), last, null, GRID);
         
         // set the follower to the last piece
         snakeBody.getLast().setFollower(newBody);
         snakeBody.add(newBody);
      } else {
         // offset the snake piece
         int newSnakeX = player.getX();
         int newSnakeY = player.getY();
         
         switch(player.getDirection()) {
            case "UP" -> newSnakeY -= FRAME/GRID;
            case "DOWN" -> newSnakeY += FRAME/GRID;
            case "LEFT" -> newSnakeX -= FRAME/GRID;
            case "RIGHT" -> newSnakeX += FRAME/GRID;
         }

         SnakeBody newBody = new SnakeBody(newSnakeX, newSnakeY, FRAME/GRID, playerSpeed, player.getDirection(), player, null, GRID);
         
         player.setFollower(player);
         snakeBody.add(newBody);
      }
   }

   public void detectAppleCollision() {
      // check if the player has eaten the apple
      if(getDistance(player, apple) <= (FRAME / GRID) - 5) {
         boolean inSnake = true;
          while (inSnake) { 
            inSnake = false;
            if (getDistance(player, apple) <= (FRAME / GRID) - 5) {
               inSnake = true;
            } else {
               for (SnakePiece piece : snakeBody) {
                 if (getDistance(piece, apple) <= (FRAME / GRID) - 5) {
                   inSnake = true;
                   break;
                 }
               }
            }
            if (inSnake) {
               apple.jump();
            }
          }
         SCOREBOARD.increaseScore();
         addSnakePiece();
         if(playerSpeed != 1) {
            player.setSpeed(player.getSpeed() + 1);
            for(SnakePiece piece : snakeBody) {
               piece.setSpeed(player.getSpeed());
            }
         }
      }
   }

   public void checkWallCollision() {
      String[] possibleDeathMessages = new String[] {
         "Rammed head into wall",
         "Hit head on wall",
         "Wall was tougher than you thought, huh?",
         "Wall: 1, Head: 0",
         "Transferred iq into the wall",
         "Permanent brain damage",
         "Achievement Unlocked: Wall Enthusiast",
         "Speedrunners hate bug patches for walls...",
      };
      // check if the player has hit the wall
      if(player.getX() < 0 - (FRAME/GRID/2) || player.getX() > FRAME - (FRAME / GRID / 2) || player.getY() < 0 - (FRAME / GRID / 2) || player.getY() > FRAME - (FRAME / GRID / 2)) {
         // end game
         gameOver = true;
         tickUntilBackHome = tick + 450;
         // randomly select a death message
         int randomIndex = (int) (Math.random() * possibleDeathMessages.length);
         causeOfDeath = possibleDeathMessages[randomIndex];
      }
   }

   public void checkSnakeCollision() {
      String[] possibleDeathMessages = new String[] {
         "You are your own worst enemy",
         "Can't eat yourself",
         "Got caught in a tailspin?",
         "Bit off more tail than they could chew",
         "Self-awareness level: deadly",
         "Simple python loop?"
      };
      // check if the player has hit the snake
      for(SnakePiece piece : snakeBody) {
         if(getDistance(player, piece) <= (FRAME / GRID) - (piece.getSize() / 1.25)) {
            // end game
            gameOver = true;
            tickUntilBackHome = tick + 450;
            // randomly select a death message
            int randomIndex = (int) (Math.random() * possibleDeathMessages.length);
            causeOfDeath = possibleDeathMessages[randomIndex];
         }
      }
   }

   public void checkEndGame() {
      // check if the player has hit the wall
      checkWallCollision();

      // check if the player has hit the snake
      checkSnakeCollision();
   }

   // game loop
   private class GameLoop implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         tick++;

         // erase previous frame by overlaying the background
         BACKGROUND.draw(myBuffer);

         // draw the apple yumy
         apple.draw(myBuffer);

         // player actions
         handlePlayerActions();

         if(gameOver) {
            // if has reached the end, set home panel
            if(tick >= tickUntilBackHome) {
               try {
                  String a = JOptionPane.showInputDialog("Do you want to save your score? (Y/N)");
                  if(a.equalsIgnoreCase("y")) {
                     String name = JOptionPane.showInputDialog("Enter your name:");
                     String mode = "Standard";
                     switch(playerSpeed) {
                        case 1 -> mode = "Standard";
                        case 2 -> mode = "Speed";
                        case 3 -> mode = "Quake";
                     }
                     LeaderboardHandler.addScore(name, SCOREBOARD.getScore(), mode);
                     JOptionPane.showMessageDialog(null, "✅ Score saved!");
                  }


                  HomePanel home = new HomePanel(frame);
                  frame.setContentPane(home);
                  frame.revalidate();
                  frame.repaint();
                  home.requestFocus();
                  t.stop();
               } catch (Exception err) {
                  err.printStackTrace();
               }
            }

            for(SnakePiece piece : snakeBody) {
               piece.draw(myBuffer);
            } 

            // game over animation stuff
            myBuffer.setColor(Color.WHITE);
            myBuffer.setFont(whaleITried.deriveFont(100f));
            myBuffer.drawString("GAME OVER", FRAME/2 - 300, FRAME/2 - 50);
            myBuffer.setFont(whaleITried.deriveFont(50f));
            myBuffer.drawString("Score: " + SCOREBOARD.getScore(), FRAME/2 - 300, FRAME/2);
            myBuffer.setFont(whaleITried.deriveFont(25f));
            myBuffer.drawString(causeOfDeath, FRAME/2 - 300, FRAME/2 + 50);
            


            repaint();
            return;
         }

         // check if the player has hit the wall or snake
         checkEndGame();

         // draw the snake body
         for(SnakePiece piece : snakeBody) {
            piece.move();
            piece.draw(myBuffer);
         }         // detect apple collision
         detectAppleCollision();

         if(getDistance(player, SCOREBOARD) <= (FRAME / GRID)*2) {
            if(scoreBoardTransparency > 50) {
               scoreBoardTransparency -= 5;
            } else {
               scoreBoardTransparency = 50;
            }
         } else {
            if(scoreBoardTransparency < 255) {
               scoreBoardTransparency += 5;
            } else {
               scoreBoardTransparency = 255;
            }
         }

         SCOREBOARD.draw(myBuffer, scoreBoardTransparency);

         repaint();
      }
   }
}