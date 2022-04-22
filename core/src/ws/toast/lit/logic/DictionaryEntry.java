package ws.toast.lit.logic;

public class DictionaryEntry {

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
