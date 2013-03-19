package com.fumuquan.util;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {

	private static SoundPlayer soundPlayer = null;

	private Context context;
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	private float streamVolume;

	private SoundPlayer(Context context) {
		this.context = context.getApplicationContext();
		initSounds();
	}

	public static SoundPlayer getInstance(Context context) {
		if (soundPlayer == null) {
			soundPlayer = new SoundPlayer(context);
		}
		return soundPlayer;
	}

	public void initSounds() {
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap<Integer, Integer>();
		AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
//		soundPoolMap.put(1, soundPool.load(context, R.raw.btn_click, 1));
//		soundPoolMap.put(3, soundPool.load(context, R.raw.money, 1));
//		soundPoolMap.put(6, soundPool.load(context, R.raw.popupwindow, 1));
//		soundPoolMap.put(7, soundPool.load(context, R.raw.warning, 1));
	}

	public void play(int sound, int uLoop) {
		soundPool.play(soundPoolMap.get(sound), streamVolume, streamVolume, 1, uLoop, 1f);
	}
}
