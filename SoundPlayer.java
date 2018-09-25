package com.example.gritters;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

public class SoundPlayer implements OnCompletionListener{
	MediaPlayer sound;
	boolean isReleased = false;
	
	public SoundPlayer(){
		sound = new MediaPlayer();
		Log.i("OUTPUT", "BUGGED OUT");
	}
	
	public void playShitingSound(Context context){
		if(isReleased){
			sound = MediaPlayer.create(context, R.raw.shit);
			sound.setOnCompletionListener(this);
			sound.start();
			isReleased = false;
		}
		else if(!sound.isPlaying()){
			sound = MediaPlayer.create(context, R.raw.shit);
			sound.setOnCompletionListener(this);
			sound.start();
		}
		else if(sound.isPlaying()){
			sound.stop();
			sound.reset();
			sound.release();
			sound = MediaPlayer.create(context, R.raw.shit);
			sound.setOnCompletionListener(this);
			sound.start();
		}
	}
	
	public void playFling(Context context){
		if(isReleased){
			sound = MediaPlayer.create(context, R.raw.flingup);
			sound.setOnCompletionListener(this);
			sound.start();
			isReleased = false;
		}
		else if(!sound.isPlaying()){
			sound = MediaPlayer.create(context, R.raw.flingup);
			sound.setOnCompletionListener(this);
			sound.start();
		}
		else if(sound.isPlaying()){
			sound.stop();
			sound.reset();
			sound.release();
			sound = MediaPlayer.create(context, R.raw.flingup);
			sound.setOnCompletionListener(this);
			sound.start();
		}
	}
	
	public void playPee(Context context){
		if(isReleased){
			sound = MediaPlayer.create(context, R.raw.pee);
			sound.setOnCompletionListener(this);
			sound.start();
			isReleased = false;
		}
		else if(!sound.isPlaying()){
			sound = MediaPlayer.create(context, R.raw.pee);
			sound.setOnCompletionListener(this);
			sound.start();
		}
		else if(sound.isPlaying()){
			sound.stop();
			sound.reset();
			sound.release();
			sound = MediaPlayer.create(context, R.raw.pee);
			sound.setOnCompletionListener(this);
			sound.start();
		}
		
	}
	
	public void playButtDance(Context context){
		if(isReleased){
			sound = MediaPlayer.create(context, R.raw.bootydance);
			sound.setOnCompletionListener(this);
			sound.start();
			isReleased = false;
		}
		else if(!sound.isPlaying()){
			sound = MediaPlayer.create(context, R.raw.bootydance);
			sound.setOnCompletionListener(this);
			sound.start();
		}
		else if(sound.isPlaying()){
			sound.stop();
			sound.reset();
			sound.release();
			sound = MediaPlayer.create(context, R.raw.bootydance);
			sound.setOnCompletionListener(this);
			sound.start();
		}
	}
	
	public void playDizzy(Context context){
		if(isReleased){
			sound = MediaPlayer.create(context, R.raw.dizzy);
			sound.setOnCompletionListener(this);
			sound.start();
			isReleased = false;
		}
		else if(!sound.isPlaying()){
			sound = MediaPlayer.create(context, R.raw.dizzy);
			sound.setOnCompletionListener(this);
			sound.start();
		}
		else if(sound.isPlaying()){
			sound.stop();
			sound.reset();
			sound.release();
			sound = MediaPlayer.create(context, R.raw.dizzy);
			sound.setOnCompletionListener(this);
			sound.start();
		}
	}
	
	public void playEat(Context context){
		if(isReleased){
			sound = MediaPlayer.create(context, R.raw.eat);
			sound.setOnCompletionListener(this);
			sound.start();
			isReleased = false;
		}
		else if(!sound.isPlaying()){
			sound = MediaPlayer.create(context, R.raw.eat);
			sound.setOnCompletionListener(this);
			sound.start();
		}
		else if(sound.isPlaying()){
			sound.stop();
			sound.reset();
			sound.release();
			sound = MediaPlayer.create(context, R.raw.eat);
			sound.setOnCompletionListener(this);
			sound.start();
		}
	}
	
	public void playScratch(Context context){
		if(isReleased){
			sound = MediaPlayer.create(context, R.raw.scratch);
			sound.setOnCompletionListener(this);
			sound.start();
			isReleased = false;
		}
		else if(!sound.isPlaying()){
			sound = MediaPlayer.create(context, R.raw.scratch);
			sound.setOnCompletionListener(this);
			sound.start();
		}
		else if(sound.isPlaying()){
			sound.stop();
			sound.reset();
			sound.release();
			sound = MediaPlayer.create(context, R.raw.scratch);
			sound.setOnCompletionListener(this);
			sound.start();
		}
	}
	
	public void playWalk(Context context){
		if(isReleased){
			sound = MediaPlayer.create(context, R.raw.walk);
			sound.setOnCompletionListener(this);
			sound.start();
			isReleased = false;
		}
		else if(!sound.isPlaying()){
			sound = MediaPlayer.create(context, R.raw.walk);
			sound.setOnCompletionListener(this);
			sound.start();
		}
		else if(sound.isPlaying()){
			sound.stop();
			sound.reset();
			sound.release();
			sound = MediaPlayer.create(context, R.raw.walk);
			sound.setOnCompletionListener(this);
			sound.start();
		}
	}
	
	public void playPaperHit(Context context){
		if(isReleased){
			sound = MediaPlayer.create(context, R.raw.hitsquish);
			sound.setOnCompletionListener(this);
			sound.start();
			isReleased = false;
		}
		else if(!sound.isPlaying()){
			sound = MediaPlayer.create(context, R.raw.hitsquish);
			sound.setOnCompletionListener(this);
			sound.start();
		}
		else if(sound.isPlaying()){
			sound.stop();
			sound.reset();
			sound.release();
			sound = MediaPlayer.create(context, R.raw.hitsquish);
			sound.setOnCompletionListener(this);
			sound.start();
		}
	}
	
	public void releaseSounds(){
		if(!isReleased){
			sound.release();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mp.release();
		isReleased = true;
	}

}
