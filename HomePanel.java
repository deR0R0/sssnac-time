// HomePanel.java
// Code Written by Robert Zhao and Nihal Gorthi
// Date 5/2/2025
// Made for Computer Science Foundations Project @ TJHSST
// © 2025 Robert Zhao and Nihal Gorthi

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class HomePanel extends JPanel {
   private JFrame frame;
   private JButton standardBtn;  
   private JButton speedBtn;
   private JButton quakeBtn;
   private JButton leaderboardBtn;
   private ImageIcon imageIcon = new ImageIcon(new ImageIcon("snakehomepanel2.png").getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT));
   private BackgroundGrid bg; 
   private Font whaleITried;
   
   public HomePanel() throws Exception {
      // get the font from the true type file
      whaleITried = Font.createFont(Font.TRUETYPE_FONT, new File("whale-i-tried.ttf")).deriveFont(100f);
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(whaleITried);

      setLayout(new BorderLayout());
      bg = new BackgroundGrid(10, 10, new Color(143, 205, 57), new Color(168, 217, 72), 900, 900);

      JLabel title = new JLabel(" SSSnack Time!", SwingConstants.CENTER);
      title.setFont(whaleITried);
      title.setForeground(Color.WHITE);

      JLabel names = new JLabel("© 2025 Robert Zhao and Nihal Gorthi", SwingConstants.CENTER);
      names.setFont(new Font("Monospaced", Font.BOLD, 20));
      names.setForeground(Color.WHITE);

      //this
      JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      subPanel.add(new JLabel(imageIcon));
      subPanel.setBackground(new Color(0, 0, 0, 0));
      subPanel.add(title);
      add(subPanel, BorderLayout.NORTH);

      //names for the thing
      JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      namePanel.setBackground(new Color(0, 0, 0, 0));
      namePanel.add(names);
      add(namePanel, BorderLayout.SOUTH);


      // Button panel (CENTER)
      JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 60, 40)); // 3 rows, 1 column, vertical gap for this
      buttonPanel.setBackground(new Color(220,20,60, 200));

      standardBtn = new JButton("Standard Mode");
      standardBtn.setFont(new Font("Wawati SC", Font.BOLD, 18));
      standardBtn.addActionListener(new StandardListener());
      
      buttonPanel.add(standardBtn);

      speedBtn = new JButton("⏩ Speed Mode");
      speedBtn.setFont(new Font("Wawati SC", Font.BOLD, 20));
      speedBtn.addActionListener(new SpeedListener());
      buttonPanel.add(speedBtn);

      quakeBtn = new JButton(" E A R T H Q U A K E ");
      quakeBtn.setFont(new Font("Wawati SC", Font.BOLD, 22));
      quakeBtn.addActionListener(new QuakeListener());
      buttonPanel.add(quakeBtn);

      leaderboardBtn = new JButton("🏆 Leaderboard");
      leaderboardBtn.setFont(new Font("Wawati SC", Font.BOLD, 20));
      leaderboardBtn.addActionListener(new LeaderboardListener());
      buttonPanel.add(leaderboardBtn);

      JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));
      mainPanel.add(buttonPanel);
      mainPanel.setBackground(new Color(0, 0, 0, 0));
      add(mainPanel, BorderLayout.CENTER);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      bg.draw(g);
   }

   public class StandardListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         GamePanel game = new GamePanel(1);
         frame = new JFrame("SSSnack Time - STANDARD MODE");
         frame.setSize(900, 900);
         frame.setLocation(0, 0);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(game); //learned this roberto r u proud of me?
         frame.setVisible(true);
         game.requestFocus(); //learned this roberto r u proud of me?


      }
   }

   public class SpeedListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         GamePanel game = new GamePanel(2);
         frame = new JFrame("SSSnack Time - SPEED MODE 🔥");
         frame.setSize(900, 900);
         frame.setLocation(0, 0);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(game); //learned this roberto r u proud of me?
         frame.setVisible(true);
         game.requestFocus(); //learned this roberto r u proud of me?

      }
   }

   public class QuakeListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         GamePanel game = new GamePanel(3);
         frame = new JFrame("SSSnack Time - E A R T H Q U A K E  UR COOKED!!");
         frame.setSize(900, 900);
         frame.setLocation(0, 0);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(game); //learned this roberto r u proud of me?
         frame.setVisible(true);
         game.requestFocus(); //learned this roberto r u proud of me?

      }
   }

   public class LeaderboardListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         LeaderboardPanel leaderboard = new LeaderboardPanel();
         frame = new JFrame("SSSnack Time - Leaderboard");
         frame.setSize(900, 900);
         frame.setLocation(0, 0);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(leaderboard);
         frame.setVisible(true);
         leaderboard.requestFocus();
      }
   }
}
