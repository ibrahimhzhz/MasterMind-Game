import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserHighScoreManager {
    private final File highScoreFile = new File("user_highscores.txt");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public UserHighScoreManager() {
        try {
            if (!highScoreFile.exists()) {
                highScoreFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recordHighScore(String username, int level) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(highScoreFile, true))) {
            String dateTime = dateFormat.format(new Date());
            writer.write(username + ":" + level + ":" + dateTime);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewHighScores(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(highScoreFile))) {
            String line;
            System.out.println("High Scores for " + username + ":");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username)) {
                    System.out.println("Level " + parts[1] + " on " + parts[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
