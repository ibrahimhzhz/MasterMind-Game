import java.util.Scanner;

public class Player {
    private MastermindGame game;
    private final Scanner scanner;
    private User user;
    private UserHighScoreManager highScoreManager;

    public Player(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setUser(String username) {
        user = new User();
        user.setUsername(username);
    }

    public void setHighScoreManager(UserHighScoreManager highScoreManager) {
        this.highScoreManager = highScoreManager;
    }

    public void startGame() {
        while (true) {
            System.out.println("Choose difficulty level: 1 for Easy, 2 for Hard, 0 for Demo");
            int level = scanner.nextInt();
            if (level == 0) {
                runDemo();
            } else {
                game = new MastermindGame(level);
                playGame(level);
            }

            System.out.println("Do you want to play again? (yes/no)");
            String playAgain = scanner.next();
            if (!playAgain.equalsIgnoreCase("yes")) {
                System.out.println("Goodbye, " + user.getUsername() + "!");
                break;
            }
        }
    }

    private void runDemo() {
        System.out.println("Welcome to the Mastermind Demo, " + user.getUsername() + "!");
        System.out.println("In Mastermind, you need to guess a secret code composed of 4 colors.");
        System.out.println("You'll have 5 attempts to guess the code.");
        System.out.println("Possible colors are: VIOLET, INDIGO, BLUE, GREEN, YELLOW, ORANGE, RED.");
        System.out.println("If you guess a correct color in the correct position, you'll see a white block '⬜'.");
        System.out.println("If you guess a correct color in the wrong position, you'll see a black block '⬛'.");
        System.out.println("If you guess an incorrect color, you'll see an 'X'.");
        System.out.println("Let's try a sample round.");

        // Predefined secret code for demo
        MastermindGame.Color[] demoSecretCode = {
                MastermindGame.Color.RED,
                MastermindGame.Color.GREEN,
                MastermindGame.Color.BLUE,
                MastermindGame.Color.YELLOW
        };

        // Predefined guess
        MastermindGame.Color[] demoGuess = {
                MastermindGame.Color.VIOLET,
                MastermindGame.Color.GREEN,
                MastermindGame.Color.BLUE,
                MastermindGame.Color.ORANGE
        };

        String demoResult = "⬛ ⬜ ⬜ X";  // Predefined result for the predefined guess

        System.out.println("Secret Code: RED, GREEN, BLUE, YELLOW (for demo purposes)");
        System.out.println("Guess: VIOLET, GREEN, BLUE, ORANGE");
        System.out.println("Result: " + demoResult);
        System.out.println("You can see that GREEN and BLUE are correct colors and in the correct positions.");
        System.out.println("ORANGE is not in the secret code, hence the 'X'.");
        System.out.println("VIOLET is in the secret code but in the wrong position, hence the '⬛'.");
        System.out.println("Now you are ready to play the real game!");

        System.out.println("Choose difficulty level: 1 for Easy, 2 for Hard");
        int level = scanner.nextInt();
        game = new MastermindGame(level);
        playGame(level);
    }

    public void playGame(int level) {
        System.out.println("Welcome to Mastermind, " + user.getUsername() + "!");
        System.out.print("Possible colors: ");
        for (int i = 0; i < game.getColors().length; i++) {
            System.out.print(i + "-" + game.getColors()[i] + " ");
        }
        System.out.println();

        for (int attempt = 1; attempt <= game.getAttempts(); attempt++) {
            System.out.println("Attempt " + attempt + ": Enter your guess (4 numbers, any numbers after 4 will be ignored):");
            MastermindGame.Color[] guess = new MastermindGame.Color[game.getCodeLength()];

            for (int i = 0; i < game.getCodeLength(); i++) {
                try {
                    int colorIndex = Integer.parseInt(scanner.next());
                    guess[i] = MastermindGame.Color.values()[colorIndex];
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid number. Try again.");
                    i--;
                }
            }
            scanner.nextLine();

            String result = game.evaluateGuess(guess);
            System.out.println("Result: " + result);

            if (result.equals("⬜ ⬜ ⬜ ⬜")) {
                System.out.println("Congratulations! You've guessed the secret code.");
                highScoreManager.recordHighScore(user.getUsername(), level);
                return;
            }
        }

        System.out.println("Sorry, you've used all attempts. The secret code was:");
        for (MastermindGame.Color color : game.getSecretCode()) {
            System.out.print(color + " ");
        }
        System.out.println();
    }
}
