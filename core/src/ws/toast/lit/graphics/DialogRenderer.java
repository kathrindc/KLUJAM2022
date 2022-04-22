package ws.toast.lit.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import lombok.var;
import ws.toast.lit.LITGame;
import ws.toast.lit.logic.DialogTree;

public class DialogRenderer {

    private static final int CHARACTER_SPEED = 2; // per 16 millis

    private final LITGame game;
    private final DialogTree tree;
    private String text;
    private String speaker;
    private int lastNode;
    private boolean done;
    private int printedChars;
    private int charsPerTick;
    private boolean obfuscated;

    public DialogRenderer(LITGame game, DialogTree tree) {
        this.game = game;
        this.tree = tree;
        this.lastNode = -1;
        this.done = false;
    }

    public void render(float delta) {
        var visibleText = getVisibleText(delta);
        var width = Gdx.graphics.getWidth();
        var y = 100.F;

        game.batch.begin();
        game.readableFont.draw(game.batch, speaker, 0, y + 20, width, -1, true);

        if (obfuscated) {
            game.fantasyFont.draw(game.batch, visibleText, 0, y, width, -1, true);
        } else {
            game.readableFont.draw(game.batch, visibleText, 0, y, width, -1, true);
        }

        game.batch.end();
    }

    public void skip() {
        if (done) {
            tree.follow();
        } else {
            done = true;
        }
    }

    public boolean handleClick(int x, int y) {
        skip();

        return true;
    }

    public boolean handleKey(int keycode) {
        if (keycode == Input.Keys.ENTER) {
            skip();

            return true;
        }

        return false;
    }

    private String getVisibleText(float delta) {
        if (lastNode != tree.current) {
            done = false;
            lastNode = tree.current;
            speaker = tree.nodes[tree.current].speaker;
            text = tree.nodes[tree.current].text;
            obfuscated = tree.nodes[tree.current].obfuscated;
            printedChars = 0;
            charsPerTick = (int) ((delta / 0.016) * CHARACTER_SPEED);
        } else if (done) {
            return text;
        }

        printedChars += charsPerTick;

        if (printedChars >= text.length()) {
            done = true;

            return text;
        }

        return text.substring(0, printedChars);
    }
}
