package ws.toast.lit.scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import lombok.var;
import ws.toast.lit.LITGame;
import ws.toast.lit.logic.DictionaryEntry;

import java.util.ArrayList;
import java.util.List;

public class DictionaryScreen extends ScreenAdapter {

    private static final DictionaryEntry[] DICTIONARY_ENTRIES = {
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
    private static final float BOOK_WIDTH = 640;
    private static final float BOOK_HEIGHT = 480;
    private static final int ENTRIES_PER_PAGE = 10;
    private static final float TIME_LIMIT = 60f;    //in seconds
    private final LITGame game;
    private Texture bookTexture;
    private int page = 0;
    private float timeSeconds = TIME_LIMIT;

    private static class DictionaryScreenInputAdapter extends InputAdapter {

        private final DictionaryScreen parent;

        DictionaryScreenInputAdapter(DictionaryScreen parent) { this.parent = parent; }

        @Override
        public boolean keyDown(int keycode) {
            switch(keycode){
                case Input.Keys.LEFT: {
                    if (parent.page > 0) {
                        parent.page -= 2;
                    }
                } break;

                case Input.Keys.RIGHT: {
                    if((parent.page + 1) * ENTRIES_PER_PAGE < DICTIONARY_ENTRIES.length) {
                        parent.page += 2;
                    }
                } break;

                default: break;
            }

            return true;
        }
    }

    public DictionaryScreen(LITGame game) {
        this.game = game;
    }

    private void drawBook() {
        game.batch.draw(bookTexture, 0, 0, BOOK_WIDTH, BOOK_HEIGHT);
    }

    private void drawEntries() {
        int start = page * ENTRIES_PER_PAGE;
        int limit = Math.min(start + ENTRIES_PER_PAGE, DICTIONARY_ENTRIES.length);
        int yOffset = 0;

        game.readableFont.setColor(0.F, 0.F, 0.F, 1.F);

        for (int i = start; i < limit; i++) {
            DictionaryEntry entry = DICTIONARY_ENTRIES[i];
            GlyphLayout layout = game.readableFont.draw(game.batch, entry.getForeignWord() + ": " + entry.getTranslation(), BOOK_WIDTH * 0.17F, (BOOK_HEIGHT * 0.94F) - yOffset, (BOOK_WIDTH / 2.1F), -1, true);

            yOffset += layout.height + 6;
        }

        start = (page + 1) * ENTRIES_PER_PAGE;
        limit = Math.min(start + ENTRIES_PER_PAGE, DICTIONARY_ENTRIES.length);
        yOffset = 0;

        for (int i = start; i < limit; i++) {
            DictionaryEntry entry = DICTIONARY_ENTRIES[i];
            GlyphLayout layout = game.readableFont.draw(game.batch, entry.getForeignWord() + ": " + entry.getTranslation(), BOOK_WIDTH * 0.53F, (BOOK_HEIGHT * 0.94F) - yOffset, (BOOK_WIDTH / 2.1F), -1, true);

            yOffset += layout.height + 6;
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15F, 0.15F, 0.15F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timeSeconds -= Gdx.graphics.getDeltaTime();

        game.batch.begin();
        drawBook();
        drawEntries();
        game.readableFont.draw(game.batch, ""+page, 100, 50, 1, 10, false);
        game.readableFont.draw(game.batch, ""+(page + 1), BOOK_WIDTH - 100, 50, 1, 10, false);
        game.readableFont.draw(game.batch, ""+(int)Math.ceil(timeSeconds), BOOK_WIDTH * 0.5F, 100, 1, 1, false);
        game.batch.end();

    }

    @Override
    public void show() {
        var inputAdapter = new DictionaryScreen.DictionaryScreenInputAdapter(this);

        Gdx.input.setInputProcessor(inputAdapter);

        bookTexture = new Texture(Gdx.files.internal("sprites/book_open.png"));
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        bookTexture.dispose();
    }
}
