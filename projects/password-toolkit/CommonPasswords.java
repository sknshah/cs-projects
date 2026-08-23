import java.util.Set;

/*
 * a set of widely known, frequently reused passwords.
 *
 * these are the kinds of values that show up at the top of nearly every
 * public breach analysis year after year, matching one of these is one
 * of the strongest possible signals that a password is unsafe, no
 * matter what its raw character-pool entropy looks like.
 */
public final class CommonPasswords {

    private CommonPasswords() {
    }

    public static final Set<String> VALUES = Set.of(
        "password", "123456", "123456789", "12345678", "12345", "1234567", "qwerty", "abc123",
        "password1", "111111", "123123", "letmein", "welcome", "monkey", "login", "admin",
        "iloveyou", "starwars", "dragon", "sunshine", "master", "hello", "freedom", "whatever",
        "qazwsx", "trustno1", "superman", "shadow", "football", "baseball", "princess", "flower",
        "michael", "jennifer", "jordan", "hunter", "ranger", "buster", "soccer", "hockey",
        "killer", "george", "asshole", "computer", "michelle", "jessica", "pepper", "zxcvbn",
        "121212", "654321", "000000", "1q2w3e4r", "changeme", "letmein1", "passw0rd", "p@ssword",
        "admin123", "root", "toor", "guest", "test", "temp", "default", "welcome1"
    );

    public static boolean contains(String candidate) {
        return VALUES.contains(candidate.toLowerCase());
    }
}
