package it.polimi.core;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;

public class Assets {
	private static SoundPool soundPool;
	private static MediaPlayer mediaPlayer;
	public static Bitmap background,welcome,bowl,playUp,playDown,statisticUp,statisticDown,settingsUp,settingsDown,tray;
	// 1. Load Assets, initialize Frames and Animations.
	public static void load() {

        welcome = loadBitmap("welcome.png", false);
        background = loadBitmap("background.png", false);
        playUp = loadBitmap("playUp.png", false);
        playDown = loadBitmap("playDown.png", false);
        settingsUp = loadBitmap("SettingUp.png", false);
        settingsDown = loadBitmap("SettingDown.png", false);
        statisticUp = loadBitmap("statisticUp.png", false);
        statisticDown = loadBitmap("statisticDown.png", false);
        bowl = loadBitmap("bowl.png", false);
        tray = loadBitmap("tray.png", false);
    }
	
	// 2. Load sounds here
	private static void loadSounds() {
		
	}

	public static void onResume() {
		loadSounds();
		// May be a good place to play music.
	}
	
	/*
	 * The Following Methods do NOT need to be changed.
	 */

	public static void onPause() {
		if (soundPool != null) {
			soundPool.release();
			soundPool = null;
		}

		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer = null;
		}
	}

	private static Bitmap loadBitmap(String filename, boolean transparency) {
		InputStream inputStream = null;
		try {
			inputStream = Game.getInstance().getAssets().open(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Options options = new Options();
		if (transparency) {
			options.inPreferredConfig = Config.ARGB_8888;
		} else {
			options.inPreferredConfig = Config.RGB_565;
		}
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
				options);
		return bitmap;
	}

	private static int loadSound(String filename) {
		int soundID = 0;
		if (soundPool == null) {
			soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
		}
		try {
			soundID = soundPool.load(GameMainActivity.assets.openFd(filename),
					1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return soundID;
	}

	public static void playSound(int soundID) {
		if (soundPool != null) {
			soundPool.play(soundID, 1, 1, 1, 0, 1);
		}
	}

	public static void playMusic(String filename, boolean looping) {
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
		}
		try {
			AssetFileDescriptor afd = GameMainActivity.assets.openFd(filename);
			mediaPlayer.setDataSource(afd.getFileDescriptor(),
					afd.getStartOffset(), afd.getLength());
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.prepare();
			mediaPlayer.setLooping(looping);
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}