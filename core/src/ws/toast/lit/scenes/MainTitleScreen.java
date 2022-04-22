package ws.toast.lit.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import lombok.var;
import ws.toast.lit.LITGame;

public class MainTitleScreen extends ScreenAdapter {

    private static final String[] MENU_ENTRIES = {
            "Start Game",
            "Credits",
            "Quit"
    };
    private static final float CURSOR_SIZE = 4.F;

    private static class MainTitleScreenInputAdapter extends InputAdapter {

        private final MainTitleScreen parent;

        MainTitleScreenInputAdapter(MainTitleScreen parent) {
            this.parent = parent;
        }

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.UP: {
                    parent.moveCursor(-1);
                } break;

                case Input.Keys.DOWN: {
                    parent.moveCursor(1);
                } break;

                case Input.Keys.ENTER: {
                    parent.activateMenuEntry();
                } break;

                default: break;
            }

            return true;
        }
    }

    private final LITGame game;
    private int cursor = 0;
    private float cursorSpread = 0, cursorY = 0;

    public MainTitleScreen(LITGame game) {
        this.game = game;
    }

    public void moveCursor(int offset) {
        int selected = cursor + offset;

        if (selected < 0) {
            selected = MENU_ENTRIES.length - 1;
        } else if (selected >= MENU_ENTRIES.length) {
            selected = 0;
        }

        cursor = selected;
    }

    public void activateMenuEntry() {
        var value = MENU_ENTRIES[cursor];

        switch (value) {
            case "Start Game": {
                var screen = new ConversationScreen(game);

                game.setScreen(screen);
            } break;

            case "Credits": {
                var screen = new CreditsScreen(game);

                game.setScreen(screen);
            } break;

            case "Quit": {
                Gdx.app.exit();
            }
        }
    }

    private void drawTitle() {
        game.readableFont.draw(
                game.batch,
                "Lost in Translation",
                0,
                Gdx.graphics.getHeight() * 0.8F,
                Gdx.graphics.getWidth(),
                1,
                false);
    }

    private void drawMenuEntries() {
        float yOffset = Gdx.graphics.getHeight() * 0.55F;
        float lineHeight = game.readableFont.getLineHeight();
        float step = lineHeight + 4;
        float width = Gdx.graphics.getWidth();

        for (int i = 0; i < MENU_ENTRIES.length; ++i) {
            var value = MENU_ENTRIES[i];
            var layout = game.readableFont.draw(game.batch, value, 0, yOffset, width, 1, false);

            if (i == cursor) {
                cursorSpread = (layout.width / 2.F) + 20;
                cursorY = yOffset - (lineHeight / 2.6F);
            }

            yOffset -= step;
        }
    }

    private void drawCursor() {
        float center = Gdx.graphics.getWidth() / 2.F;

        game.shapes.circle(center - cursorSpread, cursorY, CURSOR_SIZE);
        game.shapes.circle(center + cursorSpread, cursorY, CURSOR_SIZE);
    }

    @Override
    public void show() {
        var inputAdapter = new MainTitleScreenInputAdapter(this);

        Gdx.input.setInputProcessor(inputAdapter);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15F, 0.15F, 0.15F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        drawTitle();
        drawMenuEntries();
        game.batch.end();
        game.shapes.begin();
        drawCursor();
        game.shapes.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
