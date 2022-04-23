package ws.toast.lit.logic;

public class DictionaryEntry {

    public static final DictionaryEntry[] ENTRIES = {
            new DictionaryEntry("Hello", "Hallo"),
            new DictionaryEntry("Egg", "Ei"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule"),
            new DictionaryEntry("Owl", "Eule")
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
