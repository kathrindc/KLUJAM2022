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

import static ws.toast.lit.logic.DictionaryEntry.ENTRIES;

public class DictionaryScreen extends ScreenAdapter {

    private static final float BOOK_WIDTH = 640;
    private static final float BOOK_HEIGHT = 480;
    private static final int ENTRIES_PER_PAGE = 4;
    private static final float TIME_LIMIT = 30f;    //in seconds
    private final LITGame game;
    private Texture bookTexture;
    private int page = 0;
    private float timeSeconds;

    private static class DictionaryScreenInputAdapter extends InputAdapter {

        private final DictionaryScreen parent;

        DictionaryScreenInputAdapter(DictionaryScreen parent) {
            this.parent = parent;
        }

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.LEFT: {
                    if (parent.page > 0) {
                        parent.page -= 2;
                    }
                }
                break;

                case Input.Keys.RIGHT: {
                    if ((parent.page + 1) * ENTRIES_PER_PAGE < ENTRIES.length) {
                        parent.page += 2;
                    }
                }
                break;

                default:
                    break;
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
        int limit = Math.min(start + ENTRIES_PER_PAGE, ENTRIES.length);
        int yOffset = 0;

        game.readableFont.setColor(0.F, 0.F, 0.F, 1.F);
        game.fantasyFont.setColor(0.F, 0.F, 0.F, 1.F);

        for (int i = start; i < limit; i++) {
            DictionaryEntry entry = ENTRIES[i];
            GlyphLayout layout = game.fantasyFont.draw(game.batch, entry.getForeignWord(), BOOK_WIDTH * 0.16F, (BOOK_HEIGHT * 0.94F) - yOffset, (BOOK_WIDTH / 2.1F), -1, true);

            yOffset += layout.height + 6;
            layout = game.readableFont.draw(game.batch, entry.getTranslation(), BOOK_WIDTH * 0.17F, (BOOK_HEIGHT * 0.94F) - yOffset, (BOOK_WIDTH / 2.1F), -1, true);
            yOffset += layout.height + 20;
        }

        start = (page + 1) * ENTRIES_PER_PAGE;
        limit = Math.min(start + ENTRIES_PER_PAGE, ENTRIES.length);
        yOffset = 0;

        for (int i = start; i < limit; i++) {
            DictionaryEntry entry = ENTRIES[i];
            GlyphLayout layout = game.fantasyFont.draw(game.batch, entry.getForeignWord(), BOOK_WIDTH * 0.53F, (BOOK_HEIGHT * 0.94F) - yOffset, (BOOK_WIDTH / 2.1F), -1, true);

            yOffset += layout.height + 6;
            layout = game.readableFont.draw(game.batch, entry.getTranslation(), BOOK_WIDTH * 0.54F, (BOOK_HEIGHT * 0.94F) - yOffset, (BOOK_WIDTH / 2.1F), -1, true);
            yOffset += layout.height + 20;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15F, 0.15F, 0.15F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!game.fader.isFading()) {
            timeSeconds -= Gdx.graphics.getDeltaTime();

            if (timeSeconds < 0.01F) {
                var scene = new LearningScreen(game);

                game.fader.fade(scene);

                return;
            }
        }

        game.batch.begin();
        drawBook();
        drawEntries();
        game.readableFont.draw(game.batch, "" + (page + 1), 100, 50, 1, 10, false);
        game.readableFont.draw(game.batch, "" + (page + 2), BOOK_WIDTH - 100, 50, 1, 10, false);
        game.readableFont.draw(game.batch, "" + (int) Math.ceil(timeSeconds), BOOK_WIDTH * 0.5F, 100, 1, 1, false);
        game.batch.end();

        game.fader.render(delta);
    }

    @Override
    public void show() {
        var inputAdapter = new DictionaryScreen.DictionaryScreenInputAdapter(this);

        Gdx.input.setInputProcessor(inputAdapter);

        game.jukebox.load("atlantis", "sounds/audionautix/atlantis/Atlantis.mp3");
        game.jukebox.play("atlantis");

        bookTexture = new Texture(Gdx.files.internal("sprites/book_open.png"));
        timeSeconds = TIME_LIMIT;
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        bookTexture.dispose();
        game.readableFont.setColor(1.F, 1.F, 1.F, 1.F);
        game.fantasyFont.setColor(1.F, 1.F, 1.F, 1.F);
    }
}
