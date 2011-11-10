package com.freshwreck.drummage;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

public class DrummageActivity extends Activity {
	
	private SoundPool soundPool;
	private long currentTime; 
	private int stepCount = 0;
	private int[] sounds = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private float[][] seq = 
		{
			{1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0.5F,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0.5F,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0.5F,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0.5F,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};
	
    class Player extends Thread {
    	@Override
    	public void run() {
    		while(!isInterrupted()) {
    			currentTime = SystemClock.uptimeMillis();
    			playsound();
    			try {
					Thread.sleep(SystemClock.uptimeMillis() - currentTime + 150);
				} catch (InterruptedException e) {
					return;
				}
    		}
    	}
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try {
			loadSounds();
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    public void loadSounds() throws IOException {
    	soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 10); 
        AssetFileDescriptor afd;
        
        afd = getAssets().openFd("bassdrum25.wav");
        sounds[0] = soundPool.load(afd, 1);
        
        afd = getAssets().openFd("snaredrum63.wav");
        sounds[1] = soundPool.load(afd, 1);
        
        afd = getAssets().openFd("hihat6.wav");
        sounds[2] = soundPool.load(afd, 1);
    }
    
    Player p;
    
    public void startplay(View view) {
    	p = new Player();
    	p.start();
    }
    
    public void stopplay(View view) {
    	p.interrupt();
    }
    
    public void playsound() {
    	for(int i=0; i < 16; i++) {
    		float freq = seq[stepCount][i];
    		if(freq != 0)
    			soundPool.play(sounds[i], 1, 1, 1, 0, freq);    		
    	}
    	stepCount++;
    	if(stepCount == 16) stepCount = 0;
    }
}