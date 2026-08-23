import java.security.SecureRandom;

/*
 * generates random passphrases from a fixed word list.
 *
 * a passphrase of several random common words is easier for a person to
 * remember and type than a short string of mixed symbols, while
 * carrying more real entropy, this is the diceware-style approach:
 * pick words uniformly at random from a list using a cryptographically
 * secure source of randomness, and let the word count do the work.
 */
public final class PassphraseGenerator {

    private final SecureRandom random = new SecureRandom();

    public PassphraseResult generate(int wordCount, String separator, boolean capitalize, boolean includeDigit) {
        if (wordCount < 1) {
            throw new IllegalArgumentException("word count must be at least 1");
        }

        int listSize = WordList.WORDS.size();
        String[] chosen = new String[wordCount];
        for (int i = 0; i < wordCount; i++) {
            String word = WordList.WORDS.get(random.nextInt(listSize));
            chosen[i] = capitalize ? capitalize(word) : word;
        }

        double entropyBits = wordCount * (Math.log(listSize) / Math.log(2));

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            result.append(chosen[i]);
            if (i < wordCount - 1) {
                result.append(separator);
            }
        }

        if (includeDigit) {
            int digit = random.nextInt(10);
            result.append(digit);
            entropyBits += Math.log(10.0 * wordCount) / Math.log(2);
        }

        return new PassphraseResult(result.toString(), entropyBits);
    }

    private String capitalize(String word) {
        if (word.isEmpty()) {
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}
