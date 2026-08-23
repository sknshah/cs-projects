import java.io.Console;
import java.util.List;
import java.util.Scanner;

/*
 * a small menu driven command line interface for the password toolkit.
 *
 * this exists to demo both the analyzer and the generator end to end
 * without needing any kind of server or ui framework.
 */
public final class Cli {

    private final PasswordAnalyzer analyzer = new PasswordAnalyzer();
    private final PassphraseGenerator generator = new PassphraseGenerator();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Cli().run();
    }

    private void run() {
        while (true) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("  1. Check a password's strength");
            System.out.println("  2. Generate a passphrase");
            System.out.println("  3. Exit");
            System.out.print("> ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    checkPassword();
                    break;
                case "2":
                    generatePassphrase();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Not a valid choice.");
            }
        }
    }

    private void checkPassword() {
        String password = readPassword("Enter a password to analyze: ");
        AnalysisResult result = analyzer.analyze(password);

        System.out.println("\nScore: " + result.score().label());
        System.out.printf("Estimated entropy: %.1f bits%n", result.entropyBits());

        List<String> warnings = result.warnings();
        if (warnings.isEmpty()) {
            System.out.println("No obvious weaknesses detected.");
        } else {
            System.out.println("Notes:");
            for (String warning : warnings) {
                System.out.println("  - " + warning);
            }
        }
    }

    private void generatePassphrase() {
        int wordCount = readInt("Number of words (default 5): ", 5);
        String separator = readLine("Separator between words (default -): ", "-");
        boolean capitalize = readYesNo("Capitalize each word? (y/n, default n): ", false);
        boolean includeDigit = readYesNo("Append a random digit? (y/n, default y): ", true);

        PassphraseResult result = generator.generate(wordCount, separator, capitalize, includeDigit);

        System.out.println("\nGenerated passphrase: " + result.passphrase());
        System.out.printf("Estimated entropy: %.1f bits%n", result.entropyBits());
    }

    private String readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] chars = console.readPassword(prompt);
            return new String(chars);
        }
        System.out.print(prompt + "(input will be visible, no console attached) ");
        return scanner.nextLine();
    }

    private String readLine(String prompt, String defaultValue) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        return line.isEmpty() ? defaultValue : line;
    }

    private int readInt(String prompt, int defaultValue) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            return defaultValue;
        }
        try {
            int value = Integer.parseInt(line);
            return value > 0 ? value : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private boolean readYesNo(String prompt, boolean defaultValue) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim().toLowerCase();
        if (line.isEmpty()) {
            return defaultValue;
        }
        return line.startsWith("y");
    }
}
