package ws.toast.lit.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import lombok.var;
import ws.toast.lit.LITGame;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Jukebox {

    public static final float TARGET_VOLUME = 0.5F;

    private final LITGame game;
    private final HashMap<String, Music> tracks;
    private Music activeTrack;

    public Jukebox(LITGame game) {
        this.game = game;
        this.tracks = new HashMap<>();
    }

    public void load(String name, String path) {
        if (! tracks.containsKey(name)) {
            var fileHandle = Gdx.files.internal(path);
            var music = Gdx.audio.newMusic(fileHandle);

            music.setVolume(TARGET_VOLUME);
            music.setLooping(true);

            tracks.put(name, music);
        }
    }

    public void prepare(String name) {
        if (activeTrack != null && activeTrack.isPlaying()) {
            activeTrack.stop();
        }

        activeTrack = tracks.get(name);
    }

    public void play(String name) {
        prepare(name);

        activeTrack.play();
    }

    public void play() {
        activeTrack.play();
    }

    public void pause() {
        activeTrack.pause();
    }

    public void volume(float volume) {
        if (activeTrack != null) {
            activeTrack.setVolume(volume);
        }
    }

    public void fadeOut(float delta) {
        if (activeTrack != null) {
            var volume = activeTrack.getVolume();

            if (volume > 0.01F) {
                volume = Math.max(volume - delta, 0.F);

                activeTrack.setVolume(volume);
            }
        }
    }

    public void fadeIn(float delta) {
        if (activeTrack != null) {
            var volume = activeTrack.getVolume();

            if (volume <= TARGET_VOLUME) {
                activeTrack.setVolume(volume + delta);
            }
        }
    }

    public void dispose() {
        for (var entry : tracks.entrySet()) {
            entry.getValue().dispose();
        }

        tracks.clear();
    }
}
