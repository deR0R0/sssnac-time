// HomePanel.java
// Code Written by Robert Zhao and Nihal Gorthi
// Date 5/2/2025
// Made for Computer Science Foundations Project @ TJHSST
// © 2025 Robert Zhao and Nihal Gorthi

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePanel extends JPanel {
   private JFrame frame;
   private JButton standardBtn;  
   private JButton speedBtn;
   private JButton quakeBtn;     
   private BackgroundGrid bg; 
   
   public HomePanel() {
      //(900x900)
      bg = new BackgroundGrid(10, 10, new Color(143, 205, 57), new Color(168, 217, 72), 900, 900);
      
      // Add title
      JLabel title = new JLabel("SSSnake Time");
      title.setFont(new Font("Arial", Font.BOLD, 40));
      title.setForeground(new Color(50, this.getWidth(), 10));
      add(title);
      
      // Add buttons
      standardBtn = new JButton("Standard Mode");
      standardBtn.addActionListener(new StandardListener());
      add(standardBtn);
      
      speedBtn = new JButton("Speed Mode");
      speedBtn.addActionListener(new SpeedListener());
      add(speedBtn);
      
      quakeBtn = new JButton("Earthquake Mode");
      quakeBtn.addActionListener(new QuakeListener());
      add(quakeBtn);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      bg.draw(g);
   }

   public class StandardListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         GamePanel game = new GamePanel();
         
      }
   }

   public class SpeedListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         GamePanel game = new GamePanel();
        
      }
   }

   public class QuakeListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         GamePanel game = new GamePanel();
        
      }
   }
}
