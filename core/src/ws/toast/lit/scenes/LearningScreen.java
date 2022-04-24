package ws.toast.lit.scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.var;
import ws.toast.lit.LITGame;
import ws.toast.lit.logic.DictionaryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ws.toast.lit.logic.DictionaryEntry.ENTRIES;

public class LearningScreen extends ScreenAdapter {

    private static class CardElement {
        public float x, y;
        public float w, h;
        public String text;
        public DictionaryEntry entry;
        public boolean readable;
        public boolean open = false;
        public boolean matched = false;
    }

    private static class LearningScreenInputAdapter extends InputAdapter {

        private final LearningScreen parent;

        LearningScreenInputAdapter(LearningScreen parent) {
            this.parent = parent;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (! parent.game.fader.isFading()) {
                var screenHeight = Gdx.graphics.getHeight();
                var y = screenHeight - screenY;

                if (button == 0) {
                    for (var card : parent.cards) {
                        if (screenX > card.x && screenX < (card.x + card.w) && y > card.y && y < (card.y + card.h)) {
                            parent.openCard(card);
                        }
                    }
                }
            }

            return true;
        }
    }

    private static final int NUM_CARDS = 12;
    private static final int NUM_COLS = 3;
    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 68;
    private static final float CARD_PADDING = 2.F;

    private final LITGame game;
    private final List<CardElement> cards;
    private int score;
    private CardElement selected = null;
    private boolean resetting = false;
    private float resetTimer = 0.F;
    private float textTimer = 0.F;
    private boolean showMatch = false;
    private boolean showNoMatch = false;

    public LearningScreen(LITGame game) {
        this.game = game;
        this.cards = new ArrayList<>();
    }

    private void openCard(CardElement target) {
        if (! resetting && ! target.open && ! target.matched) {
            target.open = true;

            if (selected == null) {
                selected = target;
            } else if (selected.entry == target.entry && selected.readable != target.readable) {
                var complete = true;

                selected.matched = true;
                target.matched = true;
                selected = null;
                score = score + 15;
                textTimer = 1.5F;
                showMatch = true;

                for (var card : cards) {
                    if (! card.matched) {
                        complete = false;

                        break;
                    }
                }

                if (complete) {
                    game.score = score;

                    if (game.inIntermission) {
                        var screen = new IntermissionScreen(game, game.returnFromIntermission + (score > 55 ? 1 : 0));

                        if (score > 55) {

                        }

                        game.fader.fade(screen);
                    } else {
                        var screen = new ConversationScreen(game, game.returnAt);

                        game.fader.fade(screen);
                    }
                }
            } else {
                resetTimer = 1.5F;
                resetting = true;
                textTimer = 1.5F;
                showNoMatch = true;
                score = Math.max(score - 10, 0);
            }
        }
    }

    private void resetCards() {
        resetting = false;
        selected = null;

        for (var card : cards) {
            if (! card.matched) {
                card.open = false;
            }
        }
    }

    private void drawCards() {
        game.shapes.begin(ShapeRenderer.ShapeType.Filled);

        for (var card : cards) {
            if (! card.matched) {
                if (card.open) {
                    game.shapes.setColor(0.8F, 0.8F, 0.8F, 1.0F);
                } else {
                    game.shapes.setColor(0.7F, 0.7F, 0.7F, 1.0F);
                }

                game.shapes.rect(card.x, card.y, card.w, card.h);
            }
        }

        game.shapes.end();
        game.batch.begin();

        game.readableFont.setColor(0.F, 0.F, 0.F, 1.F);
        game.fantasyFont.setColor(0.F, 0.F, 0.F, 1.F);

        for (var card : cards) {
            if (! card.matched) {
                var y = card.y + (card.h / 2.F);

                if (card.readable) {
                    game.readableFont.draw(game.batch, card.text, card.x, y, card.w, 1, true);
                } else {
                    game.fantasyFont.draw(game.batch, card.text, card.x, y, card.w, 1, true);
                }
            }
        }

        game.readableFont.setColor(1.F, 1.F, 1.F, 1.F);
        game.fantasyFont.setColor(1.F, 1.F, 1.F, 1.F);

        game.batch.end();
    }

    public void drawScoreAndInfo() {
        var height = Gdx.graphics.getHeight() - 12.F;
        var width = Gdx.graphics.getWidth();

        game.batch.begin();

        height -= game.readableFont.draw(game.batch, "Score: " + score, 0, height, width, 1, false).height + 4.F;

        if (showNoMatch) {
            game.readableFont.setColor(0.6F, 0.1F, 0.1F, 1.F);
            game.readableFont.draw(game.batch, "No Match", 0, height, width, 1, false);
            game.readableFont.setColor(1.F, 1.F, 1.F, 1.F);
        } else if (showMatch) {
            game.readableFont.setColor(0.1F, 0.6F, 0.1F, 1.F);
            game.readableFont.draw(game.batch, "Match!", 0, height, width, 1, false);
            game.readableFont.setColor(1.F, 1.F, 1.F, 1.F);
        }

        game.batch.end();
    }

    @Override
    public void show() {
        var inputAdapter = new LearningScreenInputAdapter(this);
        var yBaseline = (Gdx.graphics.getHeight() / 2.F) - (((NUM_CARDS / NUM_COLS) / 2.F) * (CARD_HEIGHT + CARD_PADDING));
        var xBaseline = (Gdx.graphics.getWidth() / 2.F) - ((NUM_COLS / 2.F) * (CARD_WIDTH + CARD_PADDING));

        score = 0;

        for (int i = 0; i < NUM_CARDS; ++i) {
            var index = (int) Math.floor(Math.random() * ENTRIES.length);
            var entry = ENTRIES[index];
            var a = new CardElement();
            var b = new CardElement();

            a.w = CARD_WIDTH;
            a.h = CARD_HEIGHT;
            a.entry = entry;
            a.text = entry.getForeignWord();
            a.readable = false;

            i += 1;

            b.w = CARD_WIDTH;
            b.h = CARD_HEIGHT;
            b.entry = entry;
            b.text = entry.getTranslation();
            b.readable = true;

            cards.add(a);
            cards.add(b);
        }

        Collections.shuffle(cards);

        for (int i = 0; i < cards.size(); ++i) {
            var card = cards.get(i);

            card.x = xBaseline + ((i % NUM_COLS) * (CARD_WIDTH + CARD_PADDING));
            card.y = yBaseline + (((int) (i / (float) NUM_COLS)) * (CARD_HEIGHT + CARD_PADDING));
        }

        Gdx.input.setInputProcessor(inputAdapter);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15F, 0.15F, 0.15F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (resetting && ! game.fader.isFading()) {
            resetTimer = Math.max(resetTimer - delta, 0.F);

            if (resetTimer <= 0.01F) {
                resetCards();
            }
        }

        if (showMatch || showNoMatch) {
            textTimer = Math.max(textTimer - delta, 0.F);

            if (textTimer <= 0.01F) {
                showMatch = false;
                showNoMatch = false;
            }
        }

        drawCards();
        drawScoreAndInfo();

        game.fader.render(delta);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
