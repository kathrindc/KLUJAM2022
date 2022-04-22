package ws.toast.lit.scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import lombok.var;
import ws.toast.lit.LITGame;
import ws.toast.lit.logic.DictionaryEntry;

import java.util.ArrayList;
import java.util.List;

public class DictionaryScreen extends ScreenAdapter {

    private static final DictionaryEntry[] DICTIONARY_ENTRIES = {
            new DictionaryEntry("Hello", "Hallo"),
            new DictionaryEntry("Egg", "Ei"),
            new DictionaryEntry("Owl", "Eule")
    };
    private static final float BOOK_WIDTH = 640;
    private static final float BOOK_HEIGHT = 480;
    private final LITGame game;
    private Texture bookTexture;
    private int page = 0;

    private static class DictionaryScreenInputAdapter extends InputAdapter {

        private final DictionaryScreen parent;

        DictionaryScreenInputAdapter(DictionaryScreen parent) { this.parent = parent; }

        @Override
        public boolean keyDown(int keycode) {
            switch(keycode){
                case Input.Keys.LEFT: {
                    if (parent.page > 0) {
                        parent.page--;
                    }
                } break;

                case Input.Keys.RIGHT: {
                    parent.page++;
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15F, 0.15F, 0.15F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        drawBook();
        game.readableFont.draw(game.batch, ""+page, 100, 100, 1, 10, false);
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
    }
}
