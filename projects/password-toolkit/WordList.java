import java.util.List;

/*
 * a fixed word list used by the passphrase generator.
 *
 * these are common, plain english words chosen only for length and
 * memorability, they carry no special meaning beyond that. keeping the
 * list in code (rather than reading an external dictionary file) keeps
 * the project dependency free and the entropy math predictable, since
 * the list size never changes at runtime.
 */
public final class WordList {

    private WordList() {
    }

    public static final List<String> WORDS = List.of(
        "anchor", "anvil", "apple", "arrow", "autumn", "badge", "banjo", "basil", "beacon", "beaver",
        "bishop", "blanket", "bolt", "bramble", "breeze", "bridge", "bronze", "bucket", "bugle", "cabin",
        "camel", "canyon", "castle", "cedar", "cellar", "chalk", "chapel", "cherry", "chimney", "cider",
        "cinder", "clover", "coast", "cobalt", "comet", "compass", "copper", "coral", "corner", "cotton",
        "cradle", "crane", "crater", "cricket", "crimson", "crown", "cursor", "dagger", "daisy", "dawn",
        "delta", "desert", "diamond", "dolphin", "domino", "dragon", "drift", "eagle", "ember", "engine",
        "falcon", "feather", "fence", "ferry", "fiddle", "field", "finch", "flame", "flannel", "flint",
        "forest", "forge", "fossil", "fountain", "fox", "frost", "garden", "gate", "geyser", "ginger",
        "glacier", "goblet", "granite", "grove", "guitar", "gully", "hammer", "harbor", "harvest", "hazel",
        "heron", "hollow", "honey", "hornet", "hunter", "hyacinth", "iceberg", "indigo", "inlet", "ivory",
        "jacket", "jasmine", "jester", "jetty", "jigsaw", "juniper", "kayak", "kestrel", "kettle", "kingdom",
        "kiosk", "kite", "ladder", "lagoon", "lantern", "larch", "lattice", "laurel", "ledge", "lemon",
        "lentil", "lever", "lighthouse", "lilac", "linen", "lizard", "lobster", "lodge", "lumber", "lynx",
        "magnet", "maple", "marble", "marsh", "meadow", "melody", "mercury", "meteor", "mimosa", "mint",
        "mirror", "mist", "mitten", "moat", "monsoon", "moon", "moose", "mosaic", "moth", "mountain",
        "mustard", "myrtle", "narwhal", "needle", "nectar", "nettle", "nickel", "noodle", "nutmeg", "oak",
        "oasis", "oatmeal", "oboe", "ocean", "olive", "onyx", "opal", "orbit", "orchard", "origami",
        "osprey", "otter", "outpost", "oxygen", "paddle", "pagoda", "palace", "panther", "papaya", "parchment",
        "parsley", "pebble", "pelican", "pepper", "petal", "pewter", "pheasant", "pigeon", "pilgrim", "pillar",
        "pine", "pioneer", "pirate", "plank", "plateau", "plume", "pocket", "poplar", "porcupine", "portal",
        "possum", "pottery", "prairie", "prism", "pumpkin", "puzzle", "quail", "quarry", "quartz", "quill",
        "quilt", "quiver", "rabbit", "raccoon", "rainbow", "raven", "ravine", "reef", "reindeer", "relic",
        "ribbon", "ridge", "rifle", "ripple", "river", "robin", "rocket", "rooster", "rosemary", "rowan",
        "saddle", "saffron", "sailor", "salmon", "sapling", "sapphire", "satchel", "savanna", "scarf", "sesame",
        "shadow", "shamrock", "shelter", "shepherd", "shield", "shovel", "shrimp", "shuttle", "sickle", "silo",
        "sketch", "skylark", "sleigh", "sliver", "sloth", "snail", "sonnet", "sorrel", "sparrow", "spinach",
        "spindle", "spiral", "spruce", "squire", "stallion", "starling", "statue", "stirrup", "stork", "strait",
        "sugar", "sundial", "sunset", "swallow", "swan", "sycamore", "tangerine", "tapestry", "tarragon", "temple",
        "terrace", "thatch", "thicket", "thistle", "thorn", "thunder", "timber", "toffee", "topaz", "torch",
        "tortoise", "toucan", "tractor", "trellis", "tribune", "trinket", "trout", "trumpet", "tulip", "tundra",
        "tunnel", "turquoise", "turtle", "tusk", "twilight", "umbrella", "urchin", "valley", "vantage", "velvet",
        "vessel", "vicar", "village", "vinegar", "violet", "viper", "vista", "voyage", "walnut", "walrus",
        "wandering", "warbler", "warden", "watermill", "weasel", "wharf", "wheat", "whisker", "wicker", "willow",
        "windmill", "winter", "wisteria", "wizard", "wolf", "woodland", "wren", "yarrow", "yeoman", "zeppelin", "zinc"
    );
}
