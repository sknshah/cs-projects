import java.util.ArrayList;
import java.util.List;

/*
 * estimates how hard a password would be to guess and explains why.
 *
 * the estimate starts from a naive character-pool entropy calculation
 * (how many bits of randomness the length and character variety imply),
 * then applies penalties for the patterns that actually make passwords
 * easy to guess in practice: known breached passwords, keyboard walks,
 * sequential runs, and repeated characters. a password can have high
 * naive entropy and still be guessed in seconds if it is a well known
 * pattern, so the penalties matter more than the raw math.
 */
public final class PasswordAnalyzer {

    private static final String[] KEYBOARD_ROWS = {
        "qwertyuiop", "asdfghjkl", "zxcvbnm", "1234567890"
    };

    private static final int RUN_LENGTH_TO_FLAG = 3;

    public AnalysisResult analyze(String password) {
        List<String> warnings = new ArrayList<>();

        if (password == null || password.isEmpty()) {
            warnings.add("the password is empty");
            return new AnalysisResult(0.0, StrengthScore.VERY_WEAK, warnings);
        }

        if (CommonPasswords.contains(password)) {
            warnings.add("this is one of the most commonly breached passwords, it would be tried first by any attacker");
            return new AnalysisResult(0.0, StrengthScore.VERY_WEAK, warnings);
        }

        int poolSize = characterPoolSize(password);
        double entropyBits = poolSize > 0
            ? password.length() * (Math.log(poolSize) / Math.log(2))
            : 0.0;

        if (password.length() < 8) {
            warnings.add("shorter than the commonly recommended minimum of 8 characters");
        }

        int sequentialRuns = countSequentialRuns(password);
        if (sequentialRuns > 0) {
            warnings.add("contains " + sequentialRuns + " sequential run" + (sequentialRuns > 1 ? "s" : "")
                + " of " + RUN_LENGTH_TO_FLAG + " or more characters, such as \"abc\" or \"321\"");
            entropyBits -= sequentialRuns * 6.0;
        }

        int repeatedRuns = countRepeatedRuns(password);
        if (repeatedRuns > 0) {
            warnings.add("contains " + repeatedRuns + " repeated character run" + (repeatedRuns > 1 ? "s" : "")
                + " of " + RUN_LENGTH_TO_FLAG + " or more, such as \"aaa\" or \"111\"");
            entropyBits -= repeatedRuns * 6.0;
        }

        int keyboardWalks = countKeyboardWalks(password);
        if (keyboardWalks > 0) {
            warnings.add("contains " + keyboardWalks + " keyboard walk" + (keyboardWalks > 1 ? "s" : "")
                + ", such as \"qwerty\" or \"asdf\"");
            entropyBits -= keyboardWalks * 8.0;
        }

        entropyBits = Math.max(entropyBits, 0.0);

        StrengthScore score = scoreFor(entropyBits);
        return new AnalysisResult(entropyBits, score, warnings);
    }

    private int characterPoolSize(String password) {
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSymbol = true;
            }
        }

        int pool = 0;
        if (hasLower) {
            pool += 26;
        }
        if (hasUpper) {
            pool += 26;
        }
        if (hasDigit) {
            pool += 10;
        }
        if (hasSymbol) {
            pool += 33;
        }
        return pool;
    }

    private int countSequentialRuns(String password) {
        String lower = password.toLowerCase();
        int count = 0;
        int runLength = 1;

        for (int i = 1; i < lower.length(); i++) {
            int prev = lower.charAt(i - 1);
            int curr = lower.charAt(i);
            boolean ascending = curr - prev == 1;
            boolean descending = prev - curr == 1;

            if (ascending || descending) {
                runLength++;
            } else {
                if (runLength >= RUN_LENGTH_TO_FLAG) {
                    count++;
                }
                runLength = 1;
            }
        }
        if (runLength >= RUN_LENGTH_TO_FLAG) {
            count++;
        }
        return count;
    }

    private int countRepeatedRuns(String password) {
        int count = 0;
        int runLength = 1;

        for (int i = 1; i < password.length(); i++) {
            if (password.charAt(i) == password.charAt(i - 1)) {
                runLength++;
            } else {
                if (runLength >= RUN_LENGTH_TO_FLAG) {
                    count++;
                }
                runLength = 1;
            }
        }
        if (runLength >= RUN_LENGTH_TO_FLAG) {
            count++;
        }
        return count;
    }

    private int countKeyboardWalks(String password) {
        String lower = password.toLowerCase();
        int count = 0;

        for (String row : KEYBOARD_ROWS) {
            String reversed = new StringBuilder(row).reverse().toString();
            for (int i = 0; i + 4 <= lower.length(); i++) {
                String window = lower.substring(i, i + 4);
                if (row.contains(window) || reversed.contains(window)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    private StrengthScore scoreFor(double entropyBits) {
        if (entropyBits < 28) {
            return StrengthScore.VERY_WEAK;
        } else if (entropyBits < 36) {
            return StrengthScore.WEAK;
        } else if (entropyBits < 60) {
            return StrengthScore.FAIR;
        } else if (entropyBits < 80) {
            return StrengthScore.STRONG;
        } else {
            return StrengthScore.VERY_STRONG;
        }
    }
}
