package ws.toast.lit.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import ws.toast.lit.LITGame;

public class EndingScreen extends ScreenAdapter {

    private final LITGame game;
    private int ending;
    private String endingText = "";

    public EndingScreen(LITGame game, int ending) {
        this.game = game;
        this.ending = ending;
    }

    @Override
    public void show() {
        switch (ending) {
            case 0: {
                game.readableFont.setColor(1.F, 0.F, 0.F, 1.F);
                endingText = "JAIL!";
            } break;
            case 1: {
                game.readableFont.setColor(1.F, 1.F, 0.F, 1.F);
                endingText = "FINE!";
            } break;
            case 2: {
                game.readableFont.setColor(0.F, 1.F, 0.F, 1.F);
                endingText = "FREE!";
            } break;
            default: break;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15F, 0.15F, 0.15F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.readableFont.draw(game.batch, endingText, (float)Gdx.graphics.getWidth()/2, (float)Gdx.graphics.getHeight()/2, 1, 1, false);
        game.batch.end();
    }
}
