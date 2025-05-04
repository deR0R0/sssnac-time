// HomePanel.java
// Code Written by Robert Zhao and Nihal Gorthi
// Date 5/2/2025
// Made for Computer Science Foundations Project @ TJHSST
// © 2025 Robert Zhao and Nihal Gorthi

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePanel extends JPanel implements ActionListener {
   private JFrame mainFrame;
   private JButton standardBtn;  
   private JButton speedBtn;
   private JButton quakeBtn;     
   private BackgroundGrid bg; 
   
   public HomePanel(JFrame frame) {
      mainFrame = frame;
      
      // Create background grid using driver dimensions (900x900)
      bg = new BackgroundGrid(10, 10, new Color(143, 205, 57), new Color(168, 217, 72), 900, 900);
      
      // Use simple layout manager
      
      // Add empty panel at top for spacing
      add(new JPanel() {
         public void paintComponent(Graphics g) {
            // Make this panel transparent
            
         }
      });
      
      // Add title
      JLabel title = new JLabel("SSSnake Time");
      title.setFont(new Font("Arial", Font.BOLD, 40));
      title.setForeground(new Color(50, this.getWidth(), 10));
      add(title);
      
      // Add buttons
      standardBtn = new JButton("Standard Mode");
      standardBtn.addActionListener(this);
      add(standardBtn);
      
      speedBtn = new JButton("Speed Mode");
      speedBtn.addActionListener(this);
      add(speedBtn);
      
      quakeBtn = new JButton("Earthquake Mode");
      quakeBtn.addActionListener(this);
      add(quakeBtn);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      bg.draw(g);
   }
   
   public void actionPerformed(ActionEvent e) {
     // GamePanel game = new GamePanel();
      
      //if (e.getSource() == speedBtn) {
     //   game.setGameMode("speed");
     // } else if (e.getSource() == quakeBtn) {
        // game.setGameMode("earthquake");
     // } else {
       //  game.setGameMode("standard");
      //}
      
    //  mainFrame.setContentPane(game);
     // mainFrame.revalidate();
     // game.requestFocus();
   }
}
