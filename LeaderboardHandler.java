
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LeaderboardHandler {
    private static String[][] getAllScores() {
        Scanner infile = null;
        String[][] leaderboard = new String[0][4];

        try {
            // attempt to read the leaderboard file
            infile = new Scanner(new File("leaderboard.txt"));
        } catch (FileNotFoundException e) {
            // create the file if it doesn't exist
            try {
                File file = new File("leaderboard.txt");
                if(!file.exists()) {
                    file.createNewFile();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // file doesn't exist
        if(infile == null) {
            return new String[0][0];
        }

        // read the file
        while(infile.hasNextLine()) {
            String line = infile.nextLine();
            String[] stuff = line.split(",");
            String[][] newLeaderboard = new String[leaderboard.length + 1][4];
            for(int i = 0; i < leaderboard.length; i++) {
                newLeaderboard[i] = leaderboard[i];
            }
            newLeaderboard[leaderboard.length] = stuff;
            leaderboard = newLeaderboard;
        }

        // close the file
        infile.close();

        // return the leaderboard
        return leaderboard;
    }

    // need to sort the leaderboard
    private static String[][] sortScores(String[][] leaderboard) {
        // sort the leaderboard by score
        for(int i = 0; i < leaderboard.length; i++) {
            for(int j = i + 1; j < leaderboard.length; j++) {
                if(Integer.parseInt(leaderboard[i][1]) < Integer.parseInt(leaderboard[j][1])) {
                    String[] temp = leaderboard[i];
                    leaderboard[i] = leaderboard[j];
                    leaderboard[j] = temp;
                }
            }
        }

        return leaderboard;
    }

    public static void addScore(String name, int score, String mode) {
        // open the file
        try {
            // create a new file
            File file = new File("leaderboard.txt");
            if(!file.exists()) {
                file.createNewFile();
            }

            // write to the file
            PrintWriter writer = new PrintWriter(new FileWriter(file, true));
            writer.println(name + "," + score + "," + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + "," + mode);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get top 5 scores
    public static String[][] getTop5Scores() {
        String[][] leaderboard = getAllScores();
        leaderboard = sortScores(leaderboard);

        // get the top 5 scores
        String[][] top5 = new String[5][4];
        for(int i = 0; i < 5; i++) {
            if(i < leaderboard.length) {
                top5[i] = leaderboard[i];
            } else {
                top5[i] = new String[]{"No One Here", "0", "No Date", "No Mode"};
            }
        }

        return top5;
    }
}