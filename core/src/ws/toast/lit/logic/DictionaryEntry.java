package ws.toast.lit.logic;

public class DictionaryEntry {

    public static final DictionaryEntry[] ENTRIES = {
            new DictionaryEntry("Hello", "hello"),
            new DictionaryEntry("A word", "how are you"),
            new DictionaryEntry("Where", "where are we"),
            new DictionaryEntry("Acceptable", "good"),
            new DictionaryEntry("Something", "police"),
            new DictionaryEntry("Oh no", "bad"),
            new DictionaryEntry("you did that", "to accuse"),
            new DictionaryEntry("Gamebert", "Gamebert"),
            new DictionaryEntry("AbCdEf", "where is..."),
            new DictionaryEntry("eingesperrt", "arrested"),
            new DictionaryEntry("hefn", "jail"),
            new DictionaryEntry("nacht", "night"),
            new DictionaryEntry("anwalt", "lawyer"),
            new DictionaryEntry("urlaub", "vacation"),
            new DictionaryEntry("strafe", "punishment"),
            new DictionaryEntry("hauptplatz", "main square"),
            new DictionaryEntry("lokal", "bar"),
            new DictionaryEntry("Sonntag", "Sunday"),
            new DictionaryEntry("tourist", "tourist"),
            new DictionaryEntry("tiergarten", "zoo"),
            new DictionaryEntry("eindringen", "trespassing"),
            new DictionaryEntry("gehege", "enclosure"),
            new DictionaryEntry("zeuge", "witness"),
            new DictionaryEntry("tor", "gate"),
            new DictionaryEntry("alibi", "alibi"),
            new DictionaryEntry("lafn", "to run"),
            new DictionaryEntry("Flucht", "escape"),
            new DictionaryEntry("verstehn", "to understand"),
            new DictionaryEntry("thema", "topic"),
            new DictionaryEntry("beweis", "evidence"),
            new DictionaryEntry("ubrzgt", "convinced"),
            new DictionaryEntry("wache", "guard"),
            new DictionaryEntry("drei", "three"),
            new DictionaryEntry("Hilfe", "help"),
    };

    private final String foreignWord;
    private final String translation;

    public DictionaryEntry(String foreignWord, String translation) {
        this.foreignWord = foreignWord;
        this.translation = translation;
    }

    public String getForeignWord() {
        return foreignWord;
    }

    public String getTranslation() {
        return translation;
    }
}
