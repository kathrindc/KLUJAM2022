package ws.toast.lit.logic;

public class DictionaryEntry {

    public static final DictionaryEntry[] ENTRIES = {
            new DictionaryEntry("Hello", "Hallo"),
            new DictionaryEntry("A word", "How are you"),
            new DictionaryEntry("Where", "Where are we"),
            new DictionaryEntry("Acceptable", "Good"),
            new DictionaryEntry("Something", "Police"),
            new DictionaryEntry("Oh no", "Bad"),
            new DictionaryEntry("you did that", "to accuse"),
            new DictionaryEntry("Gamebert", "Gamebert"),
            new DictionaryEntry("Animal Place", "Zoo"),
            new DictionaryEntry("AbCdEf", "Where is..."),
            new DictionaryEntry("illegal", "Crime"),
            new DictionaryEntry("Despacito", "Punishment"),
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
