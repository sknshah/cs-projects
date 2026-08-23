# Password Strength Checker & Passphrase Generator

A command line toolkit for two related problems: telling you how strong a password actually is, and generating a strong one for you in the first place.

## Why this exists

Skill goal: build a small security tool that goes beyond a checklist of character-type rules, estimating real entropy, checking against known breached passwords, and catching the specific patterns (keyboard walks, repeated characters, sequential runs) that make a password guessable no matter how "complex" its rules-based score looks.

## Stack

Plain Java, standard library only (`java.security.SecureRandom`, collections, `java.io.Console`). No frameworks, no external dependencies, no build tool.

## How the strength checker works

Every password gets an entropy estimate in bits, computed from its length and the character pool it draws from (lowercase, uppercase, digits, symbols), then adjusted downward for the patterns that make a password easier to guess than its raw math suggests:

- **Known breached passwords.** If the password matches a widely reused password from public breach data, it is immediately scored at the bottom regardless of length or complexity.
- **Sequential runs**, such as `abc` or `321`.
- **Repeated character runs**, such as `aaa` or `111`.
- **Keyboard walks**, such as `qwerty` or `asdf`.

The adjusted entropy maps to one of five ratings: Very Weak, Weak, Fair, Strong, or Very Strong. Each check also produces a plain-language note explaining what pulled the score down.

## How the passphrase generator works

Rather than producing a short string of mixed symbols, the generator picks several random words from a fixed word list, diceware style, using a cryptographically secure random source. A passphrase of unrelated words is both easier for a person to remember and type, and, because each word is chosen from a reasonably large pool, competitive in raw entropy with a much less memorable string of symbols. Word count, the separator between words, whether to capitalize each word, and whether to append a random digit are all configurable, and the tool reports the resulting entropy for whatever combination you choose.

## Structure

```
password-toolkit/
├── PasswordAnalyzer.java     <- entropy estimate and pattern based warnings
├── AnalysisResult.java       <- result of one password analysis
├── StrengthScore.java        <- five level strength rating
├── CommonPasswords.java      <- known, widely reused passwords
├── PassphraseGenerator.java  <- diceware style random passphrase builder
├── PassphraseResult.java     <- result of one passphrase generation
├── WordList.java             <- word list used by the generator
├── Cli.java                  <- menu driven demo interface
└── Tests.java                <- hand rolled test suite
```

## Run it

```bash
javac *.java
java Cli
java Tests
```

## Known limitations

This is a portfolio demo, not a production security tool: the breached-password check is a small embedded list rather than a live, multi-million-entry breach database, the pattern detection is heuristic rather than exhaustive, and the word list (a few hundred words) is smaller than a full diceware list, so per-word entropy is lower; using more words in a generated passphrase makes up the difference.
