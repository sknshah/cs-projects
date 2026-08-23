/*
 * a small hand rolled test runner for the analyzer and generator.
 *
 * no external test framework is used, keeping the project dependency
 * free, each test method throws an AssertionError on failure with a
 * message describing what was expected, run with: java Tests.java
 */
public final class Tests {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        run("common password is scored very weak", Tests::testCommonPasswordIsVeryWeak);
        run("short password is flagged", Tests::testShortPasswordIsFlagged);
        run("sequential run is detected", Tests::testSequentialRunDetected);
        run("repeated run is detected", Tests::testRepeatedRunDetected);
        run("keyboard walk is detected", Tests::testKeyboardWalkDetected);
        run("varied random password scores very strong", Tests::testStrongRandomPasswordScoresHigh);
        run("passphrase has the requested word count", Tests::testPassphraseWordCount);
        run("passphrase entropy matches word count", Tests::testPassphraseEntropy);
        run("passphrase capitalize option works", Tests::testPassphraseCapitalize);
        run("passphrase digit option appends a digit", Tests::testPassphraseDigit);

        System.out.println("\n" + passed + " passed, " + failed + " failed");
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void run(String name, Runnable test) {
        try {
            test.run();
            System.out.println("PASS - " + name);
            passed++;
        } catch (AssertionError e) {
            System.out.println("FAIL - " + name + ": " + e.getMessage());
            failed++;
        }
    }

    private static void testCommonPasswordIsVeryWeak() {
        AnalysisResult result = new PasswordAnalyzer().analyze("password");
        expect(result.score() == StrengthScore.VERY_WEAK, "expected VERY_WEAK, got " + result.score());
        expect(!result.warnings().isEmpty(), "expected at least one warning");
    }

    private static void testShortPasswordIsFlagged() {
        AnalysisResult result = new PasswordAnalyzer().analyze("aB1$");
        boolean flagged = result.warnings().stream().anyMatch(w -> w.contains("8 characters"));
        expect(flagged, "expected a warning about minimum length");
    }

    private static void testSequentialRunDetected() {
        AnalysisResult result = new PasswordAnalyzer().analyze("abcdefgh");
        boolean flagged = result.warnings().stream().anyMatch(w -> w.contains("sequential run"));
        expect(flagged, "expected a warning about a sequential run");
    }

    private static void testRepeatedRunDetected() {
        AnalysisResult result = new PasswordAnalyzer().analyze("aaaa1234");
        boolean flagged = result.warnings().stream().anyMatch(w -> w.contains("repeated character run"));
        expect(flagged, "expected a warning about a repeated character run");
    }

    private static void testKeyboardWalkDetected() {
        AnalysisResult result = new PasswordAnalyzer().analyze("qwerty12");
        boolean flagged = result.warnings().stream().anyMatch(w -> w.contains("keyboard walk"));
        expect(flagged, "expected a warning about a keyboard walk");
    }

    private static void testStrongRandomPasswordScoresHigh() {
        AnalysisResult result = new PasswordAnalyzer().analyze("xQ7#mK9!zP2$vL");
        expect(result.score() == StrengthScore.VERY_STRONG,
            "expected VERY_STRONG, got " + result.score() + " (" + result.entropyBits() + " bits)");
    }

    private static void testPassphraseWordCount() {
        PassphraseResult result = new PassphraseGenerator().generate(6, "-", false, false);
        String[] parts = result.passphrase().split("-");
        expect(parts.length == 6, "expected 6 words, got " + parts.length);
        for (String part : parts) {
            expect(WordList.WORDS.contains(part), "unexpected word not in the word list: " + part);
        }
    }

    private static void testPassphraseEntropy() {
        PassphraseResult result = new PassphraseGenerator().generate(5, "-", false, false);
        double expected = 5 * (Math.log(WordList.WORDS.size()) / Math.log(2));
        double diff = Math.abs(result.entropyBits() - expected);
        expect(diff < 0.01, "expected entropy near " + expected + ", got " + result.entropyBits());
    }

    private static void testPassphraseCapitalize() {
        PassphraseResult result = new PassphraseGenerator().generate(4, "-", true, false);
        for (String word : result.passphrase().split("-")) {
            expect(Character.isUpperCase(word.charAt(0)), "expected each word to start uppercase: " + word);
        }
    }

    private static void testPassphraseDigit() {
        PassphraseResult result = new PassphraseGenerator().generate(4, "-", false, true);
        char last = result.passphrase().charAt(result.passphrase().length() - 1);
        expect(Character.isDigit(last), "expected the passphrase to end with a digit, got: " + result.passphrase());
    }

    private static void expect(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
