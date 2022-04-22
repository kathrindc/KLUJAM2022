package ws.toast.lit;

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
	public void create () {
		var screen = new MainTitleScreen(this);

		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		readableFont = new BitmapFont();
		fantasyFont = new BitmapFont();

		shapes.setAutoShapeType(true);

		this.setScreen(screen);
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapes.dispose();
		readableFont.dispose();
		fantasyFont.dispose();
	}
}
