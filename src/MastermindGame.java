import java.util.Random;

public class MastermindGame {
    public enum Color {
        VIOLET, INDIGO, BLUE, GREEN, YELLOW, ORANGE, RED, PINK, BROWN, GRAY;
    }

    private Color[] secretCode;
    private final int attempts = 5;
    private final int codeLength = 4;
    private Color[] colors;

    public MastermindGame(int level) {
        setDifficultyLevel(level);
        generateSecretCode();
    }

    private void setDifficultyLevel(int level) {
        if (level == 1) {
            colors = new Color[]{Color.VIOLET, Color.INDIGO, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
        } else if (level == 2) {
            colors = Color.values();
        }
    }

    private void generateSecretCode() {
        secretCode = new Color[codeLength];
        Random random = new Random();
        boolean[] usedColors = new boolean[colors.length];

        for (int i = 0; i < codeLength; i++) {
            int index;
            do {
                index = random.nextInt(colors.length);
            } while (usedColors[index]);
            usedColors[index] = true;
            secretCode[i] = colors[index];
        }
    }

    public String evaluateGuess(Color[] guess) {
        String result = "";
        for (int i = 0; i < codeLength; i++) {
            if (guess[i].equals(secretCode[i])) {
                result += "⬜ ";
            } else if (containsColor(secretCode, guess[i])) {
                result += "⬛ ";
            } else {
                result += "X ";
            }
        }
        return result.trim();
    }

    private boolean containsColor(Color[] array, Color color) {
        for (Color c : array) {
            if (c.equals(color)) {
                return true;
            }
        }
        return false;
    }

    public Color[] getSecretCode() {
        return secretCode;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public Color[] getColors() {
        return colors;
    }
}
