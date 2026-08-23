/*
 * a generated passphrase along with the entropy it was built with, so
 * callers do not have to recompute the math themselves.
 */
public final class PassphraseResult {

    private final String passphrase;
    private final double entropyBits;

    public PassphraseResult(String passphrase, double entropyBits) {
        this.passphrase = passphrase;
        this.entropyBits = entropyBits;
    }

    public String passphrase() {
        return passphrase;
    }

    public double entropyBits() {
        return entropyBits;
    }
}
