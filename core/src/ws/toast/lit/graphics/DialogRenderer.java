package ws.toast.lit.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ws.toast.lit.LITGame;
import ws.toast.lit.logic.DialogTree;

import java.util.ArrayList;
import java.util.List;

public class DialogRenderer {

    private static final int CHARACTER_SPEED = 2; // per 16 millis

    public static class DialogButton {

        public float x, y;
        public float w, h;
        public String text;
        public int choice;

        public DialogButton() {}

        public DialogButton(float x, float y, float w, float h, String text, int choice) {
            this.x = x; this.y = y;
            this.w = w; this.h = h;
            this.text = text;
            this.choice = choice;
        }
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
        String visibleText = getVisibleText(delta);
        float width = Gdx.graphics.getWidth();
        float y = 100.F + ((float) Math.ceil(buttons.size() / 2.F) * 60.F);

        game.shapes.begin();

        for (int i = 0; i < buttons.size(); ++i) {
            DialogButton button = buttons.get(i);

            if (game.score >= tree.nodes[tree.current].choices[i].requiredScore) {
                game.shapes.setColor(Color.WHITE);
                game.shapes.rect(button.x, button.y, button.w, button.h);
            }
        }

        game.shapes.end();
        game.batch.begin();

        y = Gdx.graphics.getHeight() - 4.F;
        y -= game.readableFont.draw(game.batch, speaker, 0, y, width, -1, true).height + 10.F;

        if (obfuscated) {
            game.fantasyFont.draw(game.batch, visibleText, 3.F, y, width - 6.F, -1, true);
        } else {
            game.readableFont.draw(game.batch, visibleText, 3.F, y, width - 6.F, -1, true);
        }

        for (int i = 0; i < buttons.size(); ++i) {
            DialogButton button = buttons.get(i);

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
        if (tree.nodes[tree.current].type == DialogTree.Node.NodeType.TEXT_ONLY) {
            skip();

            return true;
        }

        float y = Gdx.graphics.getHeight() - screenY;

        for (int i = 0; i < buttons.size(); ++i) {
            DialogButton button = buttons.get(i);
            float r = button.x + button.w;
            float t = button.y + button.h;

            if (screenX > button.x && screenX < r && y > button.y && y < t) {
                tree.follow(button.choice);

                return true;
            }
        }

        return false;
    }

    public boolean handleKey(int keycode) {
        if (keycode == Input.Keys.ENTER && tree.nodes[tree.current].type == DialogTree.Node.NodeType.TEXT_ONLY) {
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

            if (tree.nodes[tree.current].type == DialogTree.Node.NodeType.CHOICE) {
                float screenW = Gdx.graphics.getWidth();
                float width = 280.F;
                float height = 40.F;
                int visibleChoices = 0;
                int lastVisible = 0;

                for (int i = 0; i < tree.nodes[tree.current].choices.length; ++i) {
                    if (game.score >= tree.nodes[tree.current].choices[i].requiredScore && (! tree.nodes[tree.current].choices[i].requiredBonus || game.bonusUnlocked)) {
                        visibleChoices += 1;
                        lastVisible = i;
                    }
                }

                if (visibleChoices > 0) {
                    if (visibleChoices == 1) {
                        DialogButton button = new DialogButton(
                                (screenW / 2.F) - (width / 2.F),
                                50.F,
                                width, height,
                                tree.nodes[tree.current].choices[lastVisible].text,
                                lastVisible
                        );

                        buttons.add(button);
                    } else {
                        float rows = (float) Math.ceil(tree.nodes[tree.current].choices.length / 2.F);
                        float rowHeight = height + 10.F;
                        float yOffset = (rowHeight * rows) + 10.F;

                        for (int i = 0; i < tree.nodes[tree.current].choices.length; ++i) {
                            DialogButton button = new DialogButton(
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
