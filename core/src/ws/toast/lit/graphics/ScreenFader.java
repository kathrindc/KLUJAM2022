package ws.toast.lit.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.var;
import ws.toast.lit.LITGame;

public class ScreenFader {

    private final LITGame game;
    private Screen nextScreen;
    private float opacity;
    private boolean fading;
    private int phase;

    public ScreenFader(LITGame game) {
        this.game = game;
        this.nextScreen = null;
        this.opacity = 0.F;
        this.fading = false;
        this.phase = 0;
    }

    public void render(float delta) {
        if (fading) {
            var width = Gdx.graphics.getWidth();
            var height = Gdx.graphics.getHeight();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            game.shapes.begin(ShapeRenderer.ShapeType.Filled);
            game.shapes.setColor(0.0F, 0.0F, 0.0F, opacity);
            game.shapes.rect(0, 0, width, height);
            game.shapes.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            if (phase == 0) {
                opacity = Math.min(opacity + delta, 1.F);

                game.jukebox.fadeOut(delta);

                if (opacity >= 1.F) {
                    game.setScreen(nextScreen);

                    phase = 1;
                    nextScreen = null;
                }
            } else if (phase == 1) {
                opacity = Math.max(opacity - delta, 0.01F);

                game.jukebox.fadeIn(delta);

                if (opacity <= 0.01F) {
                    fading = false;
                }
            }
        }
    }

    public void fade(Screen next) {
        fading = true;
        nextScreen = next;
        opacity = 0.F;
        phase = 0;
    }

    public boolean isFading() {
        return fading;
    }
}
