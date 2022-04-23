package ws.toast.lit.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.var;
import ws.toast.lit.LITGame;
import ws.toast.lit.logic.DialogTree;

import java.util.ArrayList;
import java.util.List;

public class DialogRenderer {

    private static final int CHARACTER_SPEED = 2; // per 16 millis

    @AllArgsConstructor
    @NoArgsConstructor
    public static class DialogButton {

        public float x, y;
        public float w, h;
        public String text;
        public int choice;
    }

    private final LITGame game;
    private final DialogTree tree;
    private final List<DialogButton> buttons;
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
        this.buttons = new ArrayList<>();
    }

    public void render(float delta) {
        var visibleText = getVisibleText(delta);
        var width = Gdx.graphics.getWidth();
        var y = 100.F + ((float) Math.ceil(buttons.size() / 2.F) * 60.F);

        game.shapes.begin();

        for (int i = 0; i < buttons.size(); ++i) {
            var button = buttons.get(i);

            if (game.score >= tree.nodes[tree.current].choices[i].requiredScore) {
                game.shapes.setColor(Color.WHITE);
                game.shapes.rect(button.x, button.y, button.w, button.h);
            }
        }

        game.shapes.end();
        game.batch.begin();

        game.readableFont.draw(game.batch, speaker, 0, y + 24, width, -1, true);

        if (obfuscated) {
            game.fantasyFont.draw(game.batch, visibleText, 0, y, width, -1, true);
        } else {
            game.readableFont.draw(game.batch, visibleText, 0, y, width, -1, true);
        }

        for (int i = 0; i < buttons.size(); ++i) {
            var button = buttons.get(i);

            if (game.score >= tree.nodes[tree.current].choices[i].requiredScore) {
                game.readableFont.draw(game.batch, button.text, button.x, button.y + (button.h / 1.6F), button.w, 1, true);
            }
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

    public boolean handleClick(int screenX, int screenY) {
        if (tree.nodes[tree.current].type == DialogTree.DialogTreeNode.NodeType.TEXT_ONLY) {
            skip();

            return true;
        }

        var y = Gdx.graphics.getHeight() - screenY;

        for (int i = 0; i < buttons.size(); ++i) {
            var button = buttons.get(i);
            var r = button.x + button.w;
            var t = button.y + button.h;

            if (screenX > button.x && screenX < r && y > button.y && y < t) {
                tree.follow(button.choice);

                return true;
            }
        }

        return false;
    }

    public boolean handleKey(int keycode) {
        if (keycode == Input.Keys.ENTER && tree.nodes[tree.current].type == DialogTree.DialogTreeNode.NodeType.TEXT_ONLY) {
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

            buttons.clear();

            if (tree.nodes[tree.current].type == DialogTree.DialogTreeNode.NodeType.CHOICE) {
                var screenW = Gdx.graphics.getWidth();
                var width = 280.F;
                var height = 40.F;
                var visibleChoices = 0;
                var lastVisible = 0;

                for (int i = 0; i < tree.nodes[tree.current].choices.length; ++i) {
                    if (game.score >= tree.nodes[tree.current].choices[i].requiredScore) {
                        visibleChoices += 1;
                        lastVisible = i;
                    }
                }

                if (visibleChoices > 0) {
                    if (visibleChoices == 1) {
                        var button = new DialogButton(
                                (screenW / 2.F) - (width / 2.F),
                                50.F,
                                width, height,
                                tree.nodes[tree.current].choices[lastVisible].text,
                                lastVisible
                        );

                        buttons.add(button);
                    } else {
                        var rows = (float) Math.ceil(tree.nodes[tree.current].choices.length / 2.F);
                        var rowHeight = height + 10.F;
                        var yOffset = (rowHeight * rows) + 10.F;

                        for (int i = 0; i < tree.nodes[tree.current].choices.length; ++i) {
                            var button = new DialogButton(
                                    (screenW / 2.F) + ((i % 2 == 0 ? (-10.F - width) : 10.F)),
                                    yOffset,
                                    width, height,
                                    tree.nodes[tree.current].choices[i].text,
                                    i
                            );

                            if ((i % 2) == 1) {
                                yOffset -= rowHeight;
                            }

                            buttons.add(button);
                        }
                    }
                }
            }
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
