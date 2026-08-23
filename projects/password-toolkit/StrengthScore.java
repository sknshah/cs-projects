/*
 * overall rating for an analyzed password, ordered from weakest to
 * strongest so callers can compare scores with plain enum ordering.
 */
public enum StrengthScore {
    VERY_WEAK("Very Weak"),
    WEAK("Weak"),
    FAIR("Fair"),
    STRONG("Strong"),
    VERY_STRONG("Very Strong");

    private final String label;

    StrengthScore(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
