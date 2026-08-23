import java.util.List;

/*
 * the outcome of analyzing a single password: an estimated entropy in
 * bits, an overall score, and a list of human readable warnings
 * explaining what pulled the score down (or an empty list if nothing did).
 */
public final class AnalysisResult {

    private final double entropyBits;
    private final StrengthScore score;
    private final List<String> warnings;

    public AnalysisResult(double entropyBits, StrengthScore score, List<String> warnings) {
        this.entropyBits = entropyBits;
        this.score = score;
        this.warnings = List.copyOf(warnings);
    }

    public double entropyBits() {
        return entropyBits;
    }

    public StrengthScore score() {
        return score;
    }

    public List<String> warnings() {
        return warnings;
    }
}
