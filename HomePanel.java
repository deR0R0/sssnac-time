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
      
      // TODO: maybe add more grid patterns? Current one is kinda boring
      bg = new BackgroundGrid();
      
      setLayout(null);   // absolute positioning - took forever to figure this out!
      setOpaque(false);  // makes panel transparent - took forever to figure this out!
      
      JLabel title = new JLabel("SSSnake Time");
      title.setBounds(300, 100, 300, 70);
      title.setFont(new Font("Arial", Font.BOLD, 40));
      title.setForeground(new Color(50, 20, 10));      // brownish color
      title.setHorizontalAlignment(JLabel.CENTER);
      add(title);
      
      // init all buttons - should probably make these constants but whatever
      standardBtn = makeButton("Standard Mode", 300);
      speedBtn = makeButton("Speed Mode 🔥", 400);
      quakeBtn = makeButton("E A R T H Q U A K E", 500);  // space between letters looks cool
   }
   
   // quick button maker - saves typing the same stuff over and over
   private JButton makeButton(String txt, int yPos) {
      JButton btn = new JButton(txt);
      
      // standard button setup
      btn.setBounds(300, yPos, 300, 60);
      btn.setFont(new Font("Arial", Font.BOLD, 20));
      
      // colors - might need adjusting
      Color btnGreen = new Color(100, 180, 40);  // my favorite shade of green
      btn.setBackground(btnGreen);
      btn.setForeground(Color.WHITE);
      
      // make it pretty
      btn.setFocusPainted(false);  // removes ugly focus border
      btn.setBorder(BorderFactory.createRaisedBevelBorder());
      
      // wire up the listener
      btn.addActionListener(this);
      add(btn);
      return btn;
   }
   
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      // draw background first
      bg.draw(g);
      
      /* adding semi-transparent overlay for better readability
         tried different alpha values:
         - 40 too light
         - 80 too dark
         - 60 looks just right
      */
      g.setColor(new Color(0, 0, 0, 60));
      g.fillRoundRect(250, 50, 400, 600, 30, 30);
   }
   
   // TODO fix this please i just made something up
   public void actionPerformed(ActionEvent e) {
      // default to standard mode
      String mode = "standard";
      
      // figure out which button was clicked
      if (e.getSource() == speedBtn) {
         mode = "speed";
      } else if (e.getSource() == quakeBtn) {
         mode = "earthquake";
      }
      
      // start new game
      GamePanel game = new GamePanel();
      //game.setGameMode(mode);  // commented out till I implement this
      
      // switch panels
      mainFrame.setContentPane(game);
      mainFrame.revalidate();
      game.requestFocus();  // make sure keyboard input works
   }
}
