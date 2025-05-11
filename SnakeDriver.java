// Code Written by Robert Zhao and Nihal Gorthi
// Date 5/2/2025
// Made for Computer Science Foundations Project @ TJHSST
// © 2025 Robert Zhao and Nihal Gorthi

import javax.swing.JFrame;
public class SnakeDriver {
   public static void main(String[] args) { 
      JFrame frame = new JFrame("SSSnack Time");
      frame.setSize(900, 900);
      frame.setLocation(0, 0);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      GamePanel p = new GamePanel();
      frame.setContentPane(p);
      p.requestFocus();
      frame.setVisible(true);
   }
}
