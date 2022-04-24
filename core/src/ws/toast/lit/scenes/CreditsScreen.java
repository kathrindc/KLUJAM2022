package ws.toast.lit.scenes;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import ws.toast.lit.LITGame;

public class CreditsScreen extends ScreenAdapter {

    private static class CreditsScreenInputAdapter extends InputAdapter {

        private final CreditsScreen parent;

        public CreditsScreenInputAdapter(CreditsScreen parent) {
            this.parent = parent;
        }

        @Override
        public boolean keyDown(int keycode) {
            if (! parent.game.fader.isFading() && (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.ENTER || keycode == Input.Keys.SPACE)) {
                Screen screen = new MainTitleScreen(parent.game);

                parent.game.fader.fade(screen);
            }

            return true;
        }
    }

    private final LITGame game;

    public CreditsScreen(LITGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        InputAdapter inputAdapter = new CreditsScreenInputAdapter(this);

        Gdx.input.setInputProcessor(inputAdapter);
    }

    @Override
    public void render(float delta) {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Gdx.gl.glClearColor(0.15F, 0.15F, 0.15F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();
        game.readableFont.draw(game.batch, "Lost in Translation\nSubmission for KLUJAM 2022\n\nGame by:\nFlorian Krainz\nKathrin De Cecco\n\nFree Assets by:\nEugenie Bidaut\nLJ Design Studios\nJason Shaw (audionautix)", 3.F, height - 23.F, width, 1, true);
        game.batch.end();
        game.fader.render(delta);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
