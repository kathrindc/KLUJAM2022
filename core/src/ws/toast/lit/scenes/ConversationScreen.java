package ws.toast.lit.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import lombok.var;
import ws.toast.lit.LITGame;
import ws.toast.lit.graphics.DialogRenderer;
import ws.toast.lit.logic.DialogTree;

public class ConversationScreen extends ScreenAdapter {

    private static class ConversationScreenInputAdapter extends InputAdapter {

        private final ConversationScreen parent;

        ConversationScreenInputAdapter(ConversationScreen parent) {
            this.parent = parent;
        }

        @Override
        public boolean keyDown(int keycode) {
            if (! parent.game.fader.isFading()) {
                parent.dialog.handleKey(keycode);
            }

            return true;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (! parent.game.fader.isFading() && button == 0) {
                parent.dialog.handleClick(screenX, screenY);
            }

            return true;
        }
    }

    private final LITGame game;
    private final DialogRenderer dialog;

    public ConversationScreen(LITGame game, int startFrom) {
        var tree = new DialogTree(game, startFrom);

        this.game = game;
        this.dialog = new DialogRenderer(game, tree);
    }

    @Override
    public void show() {
        var inputAdapter = new ConversationScreenInputAdapter(this);

        Gdx.input.setInputProcessor(inputAdapter);

        game.readableFont.setColor(1.F, 1.F, 1.F, 1.F);
        game.jukebox.load("the-visitors", "sounds/audionautix/the-visitors/TheVisitors.mp3");
        game.jukebox.play("the-visitors");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15F, 0.15F, 0.15F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (! game.fader.isFading()) {
            dialog.render(delta);
        }

        game.fader.render(delta);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
