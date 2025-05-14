
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class LeaderboardPanel extends JPanel {
    private JFrame frame;
    private BackgroundGrid bg;
    private Font whaleITried;

    // Constructor
    public LeaderboardPanel(JFrame frame) {
        bg = new BackgroundGrid(10, 10, new Color(143, 205, 57), new Color(168, 217, 72), 900, 900);
        // get the font from the true type file
        try {
            whaleITried = Font.createFont(Font.TRUETYPE_FONT, new File("whale-i-tried.ttf")).deriveFont(100f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(whaleITried);

        setLayout(new BorderLayout());

        // title
        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(whaleITried);
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        // leaderboard
        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new GridLayout(5, 1));
        leaderboardPanel.setOpaque(false);

        String[][] scores = LeaderboardHandler.getTop5Scores();

        for (int i = 0; i < scores.length; i++) {
            String name = scores[i][0];
            String score = scores[i][1];
            String date = scores[i][2];
            String mode = scores[i][3];
            
            JLabel scoreLabel = new JLabel((i + 1) + ". " + name + ": " + score + " Points  Mode: " +  mode + "  @" + date, SwingConstants.CENTER);
            scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
            scoreLabel.setForeground(Color.WHITE);
            leaderboardPanel.add(scoreLabel);
        }

        // add components to the main panel
        add(leaderboardPanel, BorderLayout.CENTER);

        // back button
        JButton backButton = new JButton("🔙");
        backButton.setFont(new Font("Wawati SC", Font.BOLD, 18));
        backButton.addActionListener(new BackListener());

        add(backButton, BorderLayout.SOUTH);

        this.frame = frame;
    }
    
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      bg.draw(g);
    }

    public class BackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                HomePanel home = new HomePanel(frame);
                frame.setContentPane(home);
                frame.revalidate();
                frame.repaint();
                home.requestFocus();
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    
}