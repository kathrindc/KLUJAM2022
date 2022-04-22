package ws.toast.lit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.var;
import com.badlogic.gdx.Game;
import ws.toast.lit.scenes.MainTitleScreen;

public class LITGame extends Game {

	public SpriteBatch batch;
	public ShapeRenderer shapes;
	public BitmapFont readableFont;
	public BitmapFont fantasyFont;

	@Override
	public void create() {
		var screen = new MainTitleScreen(this);
		var adelpheFontFileHandle = Gdx.files.internal("fonts/adelphe/regular.fnt");

		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		readableFont = new BitmapFont(adelpheFontFileHandle);
		fantasyFont = new BitmapFont();

		shapes.setAutoShapeType(true);
		readableFont.setColor(1.0F, 1.0F, 1.0F, 1.0F);

		this.setScreen(screen);
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapes.dispose();
		readableFont.dispose();
		fantasyFont.dispose();
	}
}
