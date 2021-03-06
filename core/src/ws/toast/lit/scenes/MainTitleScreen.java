package ws.toast.lit.scenes;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import ws.toast.lit.LITGame;
import ws.toast.lit.audio.Jukebox;

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
            if (parent.game.fader.isFading()) {
                return true;
            }

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
    private Texture logoTexture;

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
        String value = MENU_ENTRIES[cursor];

        switch (value) {
            case "Start Game": {
                Screen screen = new ConversationScreen(game, 0);

                game.score = 0;

                game.fader.fade(screen);
            } break;

            case "Credits": {
                Screen screen = new CreditsScreen(game);

                game.fader.fade(screen);
            } break;

            case "Quit": {
                Gdx.app.exit();
            }
        }
    }

    private void drawTitle() {
        GlyphLayout layout = game.readableFont.draw(
                game.batch,
                "Lost in Translation",
                40.F,
                Gdx.graphics.getHeight() * 0.8F,
                Gdx.graphics.getWidth(),
                1,
                false);

        game.batch.draw(logoTexture, 150.F, Gdx.graphics.getHeight() * 0.7F, 80.F, 80.F);
    }

    private void drawMenuEntries() {
        float yOffset = Gdx.graphics.getHeight() * 0.55F;
        float lineHeight = game.readableFont.getLineHeight();
        float step = lineHeight + 4;
        float width = Gdx.graphics.getWidth();

        for (int i = 0; i < MENU_ENTRIES.length; ++i) {
            String value = MENU_ENTRIES[i];
            GlyphLayout layout = game.readableFont.draw(game.batch, value, 0, yOffset, width, 1, false);

            if (i == cursor) {
                cursorSpread = (layout.width / 2.F) + 20;
                cursorY = yOffset - (lineHeight / 2.6F);
            }

            yOffset -= step;
        }
    }

    private void drawCursor() {
        float center = Gdx.graphics.getWidth() / 2.F;

        game.shapes.setColor(1.F, 1.F, 1.F, 1.F);
        game.shapes.circle(center - cursorSpread, cursorY, CURSOR_SIZE);
        game.shapes.circle(center + cursorSpread, cursorY, CURSOR_SIZE);
    }

    @Override
    public void show() {
        InputAdapter inputAdapter = new MainTitleScreenInputAdapter(this);
        FileHandle logoFileHandle = Gdx.files.internal("sprites/logo.png");

        logoTexture = new Texture(logoFileHandle);

        game.jukebox.load("quiet", "sounds/audionautix/quiet/Quiet.mp3");
        game.jukebox.play("quiet");
        game.jukebox.volume(Jukebox.TARGET_VOLUME);

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

        game.fader.render(delta);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        logoTexture.dispose();
    }
}
