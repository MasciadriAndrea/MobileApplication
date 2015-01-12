package it.polimi.core;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.widget.ImageView;

import it.polimi.framework.animation.Animation;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;

public class Assets {
	private static SoundPool soundPool;
	private static MediaPlayer mediaPlayer;
	public static Bitmap bowl,bowl_1,bowl_2,tray_1,tray_2,bee,seed, menu,win;
    public static int winID, stealID, seedID;

	// 1. Load Assets, initialize Frames and Animations.
	public static void load() {

        bowl = loadBitmap("bowl.png", false);
        tray_1 = loadBitmap("tray_1.png", false);
        tray_2 = loadBitmap("tray_2.png", false);
        bowl_1 = loadBitmap("bowl_1.png", false);
        bowl_2 = loadBitmap("bowl_2.png", false);
        bee = loadBitmap("bee.png", false);
        bee = loadBitmap("bee.png", false);
        seed = loadBitmap("seed.png", false);
        win = loadBitmap("win_screen.png", false);
        menu = loadBitmap("menu.png", false);
    }
	
	// 2. Load sounds here
	private static void loadSounds() {
        winID = loadSound("clap.wav");
        seedID = loadSound("fall.wav");
        stealID = loadSound("laugh.wav");
	}

	public static void onResume() {
        loadSounds();
        if(Game.getInstance().getMusic()){
            if(mediaPlayer==null){
                playMusic("music.wav",true);
            }
        }
	}


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
			soundID = soundPool.load(Game.getInstance().getAssets().openFd(filename),1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return soundID;
	}

	public static void playSound(int soundID) {
		if (soundPool != null) {
            if(Game.getInstance().getSound()){
			    soundPool.play(soundID, 1, 1, 1, 0, 1);
            }
		}
	}

	public static void playMusic(String filename, boolean looping) {
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
		}
		try {
			AssetFileDescriptor afd = Game.getInstance().getAssets().openFd(filename);
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

    public static Bitmap rotate(Bitmap b,float x)
    {   int width = b.getWidth();
        int height = b.getHeight();
        int newWidth=300;
        int newHeight=300;

        //float scaleWidth=((float) newWidth)/width;
        //float scaleHeight=((float) newHeight)/height;
        Matrix matrix = new Matrix();
        matrix.postRotate(x);
        //matrix.postScale(scaleWidth,scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(b, 0, 0,width, height, matrix, true);
        return  resizedBitmap;
    }

}