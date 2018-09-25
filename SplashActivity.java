package com.example.gritters;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SplashActivity extends Activity implements OnClickListener{

	Button startButton;
	MediaPlayer player;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		startButton = (Button)findViewById(R.id.startButton);
		startButton.setOnClickListener(this);
		
		player = MediaPlayer.create(getApplicationContext(), R.raw.splashscreen);
		player.setLooping(true);
		player.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
	
	protected void onStart(){
		super.onStart();
		player = MediaPlayer.create(getApplicationContext(), R.raw.splashscreen);
		player.setLooping(true);
		player.start();
	}
	
	protected void onStop(){
		if(player!=null){
			player.stop();
			player.release();
			super.onStop();
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.startButton){
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
		}
		
	}

}
