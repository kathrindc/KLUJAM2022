package ws.toast.lit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.var;
import com.badlogic.gdx.Game;
import ws.toast.lit.audio.Jukebox;
import ws.toast.lit.graphics.ScreenFader;
import ws.toast.lit.scenes.MainTitleScreen;

public class LITGame extends Game {

	public SpriteBatch batch;
	public ShapeRenderer shapes;
	public BitmapFont readableFont;
	public BitmapFont fantasyFont;
	public ScreenFader fader;
	public Jukebox jukebox;
	public int score;
	public int returnAt;

	@Override
	public void create() {
		var screen = new MainTitleScreen(this);
		var adelpheFontFileHandle = Gdx.files.internal("fonts/adelphe/regular.fnt");
		var xidusFontFileHandle = Gdx.files.internal("fonts/xidus/regular.fnt");

		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		readableFont = new BitmapFont(adelpheFontFileHandle);
		fantasyFont = new BitmapFont(xidusFontFileHandle);
		fader = new ScreenFader(this);
		jukebox = new Jukebox(this);

		shapes.setAutoShapeType(true);
		readableFont.setColor(1.0F, 1.0F, 1.0F, 1.0F);

		this.setScreen(screen);
	}

	@Override
	public void resize(int width, int height) {
		Gdx.graphics.setWindowedMode(width, height);
		Gdx.gl.glViewport(0, 0, width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapes.dispose();
		readableFont.dispose();
		fantasyFont.dispose();
		jukebox.dispose();
	}
}
