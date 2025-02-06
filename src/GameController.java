import java.util.InputMismatchException;
import java.util.Scanner;

public class GameController {
    private final Scanner scanner;
    private final Player player;
    private final UserHighScoreManager highScoreManager;

    public GameController(Scanner scanner) {
        this.scanner = scanner;
        this.player = new Player(scanner);
        this.highScoreManager = new UserHighScoreManager();
    }

    public void start() {
        while (true) {
            try {
                System.out.println("Welcome to Mastermind!");
                System.out.println("1. Signup");
                System.out.println("2. Login");
                System.out.println("3. View High Scores");
                System.out.println("4. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        signup();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        viewHighScores();
                        break;
                    case 4:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Please choose a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number from the options.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private void signup() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        String password;
        while (true) {
            System.out.println("Enter password:");
            password = scanner.nextLine();
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please enter a valid password.");
            } else {
                break;
            }
        }

        if (User.signup(username, password)) {
            System.out.println("Signup successful!");
        } else {
            System.out.println("Username already exists. Try again.");
        }
    }

    private void login() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        if (User.login(username, password)) {
            System.out.println("Login successful!");
            player.setUser(username);
            player.setHighScoreManager(highScoreManager);
            player.startGame();
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private void viewHighScores() {
        System.out.println("Enter username to view high scores:");
        String username = scanner.nextLine();
        highScoreManager.viewHighScores(username);
    }
}
