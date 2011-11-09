package com.freshwreck.drummage;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class DrummageActivity extends Activity {
	
	MediaPlayer player; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        player = new MediaPlayer();
    }
    
    
    public void playsound(View view) {
    	try {
			reallyplaysound();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void reallyplaysound() throws IOException {
    	AssetFileDescriptor afd = getAssets().openFd("bassdrum25.wav");
    	player.reset();
    	player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
    	afd.close();
    	player.prepare();
    	player.start();
    }
}