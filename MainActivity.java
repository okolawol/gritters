package com.example.gritters;

import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity implements OnTouchListener,SensorEventListener{

	private TextView loadingText;
	private ImageView coinView;
	private AnimationDrawable coinSpin;
	private TextView coins;
	private int numOfCoins = 0;
	private FrameLayout frameLayout;
	private PetView petView;
	private Handler handler = new Handler();
	volatile boolean gameRunning = true;
	private ProgressBar foodBar;
	private ProgressBar happyBar;
	private GestureDetectorCompat mDetector;
	
	private TranslateAnimation flingRight;
	private TranslateAnimation flingLeft;
	private TranslateAnimation flingUp;
	private TranslateAnimation walk;
	private TranslateAnimation walkBack;
	
	private float deltaX = 200;
	private float mdeltaX = -200;
	
	private float walkDeltaX = 200;
	private float mWalkDeltaX = -200;
	
	private float deltaY = -250;
	
	private Thread gLoop;
	
	private int screenWidth;
	private int screenHeight;
	
	private boolean isDragged = true;
	private boolean isDragged2 = true;
	private boolean isDragged3 = true;
	
	private boolean isPorkReleased = false;
	private boolean isPork2Released = false;
	private boolean isPaperReleased = false;
	
	private boolean isPorkOnGround = false;
	private boolean isPorkOnGround2 = false;
	private boolean isNewsPaperOnGround = false;
	
	private ImageView porkButt;
	private ImageView porkButt2;
	private ImageView newsPaper;
	
	private LoadProps loadProps;
	
	
	private SensorManager mySensorManager = null;
	private Sensor accelSensor = null;
	private float noise = (float)10.0;
	private float lastX;
	private float lastY;
	private float lastZ;
	private boolean initialized;
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frame);
		
		loadingText = (TextView)findViewById(R.id.loadingText);
		coins = (TextView)findViewById(R.id.coinTextView);
		coinView = new ImageView(this);
		
		//Pet view object
		petView = new PetView(this);
		petView.setId(1);
		petView.setOnTouchListener(this);
		
		//preping fling right animation
		float toX = petView.getLeft()+deltaX;
		flingRight = new TranslateAnimation(petView.getLeft(),toX,0,0);
		flingRight.setDuration(500);
		flingRight.setInterpolator(new DecelerateInterpolator());
		flingRight.setFillAfter(true);
		flingRight.setFillBefore(true);
		flingRight.setFillEnabled(true);
		flingRight.setAnimationListener(new AnimationListener());
		
		//preping fling left animation
		toX = petView.getLeft()+mdeltaX;
		flingLeft = new TranslateAnimation(petView.getLeft(),toX,0,0);
		flingLeft.setDuration(500);
		flingLeft.setInterpolator(new DecelerateInterpolator());
		flingLeft.setFillAfter(true);
		flingLeft.setFillBefore(true);
		flingLeft.setFillEnabled(true);
		flingLeft.setAnimationListener(new AnimationListener());
		
		//preping fling up animation
		float toY = petView.getTop()+deltaY;
		flingUp = new TranslateAnimation(0,0,petView.getTop(),toY);
		flingUp.setDuration(800);
		flingUp.setInterpolator(new DecelerateInterpolator());
		flingUp.setFillAfter(true);
		flingUp.setFillBefore(true);
		flingUp.setFillEnabled(true);
		flingUp.setRepeatMode(TranslateAnimation.REVERSE);
		flingUp.setRepeatCount(1);
		flingUp.setAnimationListener(new AnimationListener());
		
		toX = petView.getLeft()+walkDeltaX;
		walk = new TranslateAnimation(petView.getLeft(),toX,0,0);
		walk.setDuration(1000);
		walk.setInterpolator(new DecelerateInterpolator());
		walk.setFillAfter(true);
		walk.setFillBefore(true);
		walk.setFillEnabled(true);
		walk.setAnimationListener(new AnimationListener());
		
		toX = petView.getLeft()+mWalkDeltaX;
		walkBack = new TranslateAnimation(petView.getLeft(),toX,0,0);
		walkBack.setDuration(1000);
		walkBack.setInterpolator(new DecelerateInterpolator());
		walkBack.setFillAfter(true);
		walkBack.setFillBefore(true);
		walkBack.setFillEnabled(true);
		walkBack.setAnimationListener(new AnimationListener());
		
		//adding pet view at specific position
		frameLayout = (FrameLayout)findViewById(R.id.frameLayout);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT);
		params.leftMargin = 150;
		params.topMargin = 250;
		params.gravity = Gravity.TOP;
		frameLayout.addView(petView, params);
		
		params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT);
		frameLayout.addView(coinView, params);
		
		//getting onscreen objects and adding touch listeners
		porkButt = (ImageView)findViewById(R.id.porkImageView);
		porkButt.setOnTouchListener(this);
		porkButt2 = (ImageView)findViewById(R.id.porkImageView2);
		porkButt2.setOnTouchListener(this);
		newsPaper = (ImageView)findViewById(R.id.newsPaperImageView);
		newsPaper.setOnTouchListener(this);
		
		//gesture detector
		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
		
		//progress bars
		foodBar = (ProgressBar)findViewById(R.id.foodBar);
		happyBar = (ProgressBar)findViewById(R.id.happyBar);
		
		//sensors
		mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		accelSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		//game loop stuff
		gameRunning  = true;
		gLoop = new Thread(gameLoop);
		gLoop.start();
		
		loadProps = new LoadProps();
		loadProps.execute();
		
	}
	
	protected void onResume(){
		super.onResume();
		
		//starting  timer thread
		handler = new Handler();
		handler.postDelayed(runnable, 5000);
		
		gameRunning  = true;
		mySensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	protected void onPause(){
		mySensorManager.unregisterListener(this);
		//removing timer thread
		handler.removeCallbacks(runnable);
		super.onPause();
	}
	protected void onStop(){
		gameRunning  = false;
		petView.releaseSounds();
		super.onStop();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			petView.startAnimationF();
		}
	}
	
	//timer thread
	private Runnable runnable = new Runnable() {
		   @Override
		   public void run() {
		      /* do what you need to do */
			   
			   if(loadingText.getVisibility() == TextView.INVISIBLE){
				   
				   int rand = (int) (Math.random() * 3);
				   if(rand == 1){
					   if(petView.getState() == PetStates.NSTANDING){
						   if(petView.getLeft() >= screenWidth/2){
							   petView.startAnimation(walkBack);
						   }
						   else if(petView.getLeft() <= screenWidth/2){
							   petView.startAnimation(walk);
						   }
					   }
				   }
				   else{
					   petView.timeChange();
					   
				   }
			   }
			   if(petView.getStatus()[0] > 60 && petView.getStatus()[1] > 60){
					numOfCoins+=50;
					coins.setText(""+numOfCoins);
				}
		      
		      
		      
		      /* and here comes the "trick" */
		      handler.postDelayed(this, 8000);
		   }
	};


	//on touch listener
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(v.getId()==petView.getId()){
			//petView.setState(9);
			return mDetector.onTouchEvent(event);
		}
		else{
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				isDragged = true;
				isDragged2 = true;
				isDragged3 = true;
				return true;
			}
			else if(event.getAction()==MotionEvent.ACTION_MOVE){
				
				
				if(v.getId()==R.id.porkImageView){
					isPorkOnGround = false;
					isPorkReleased = false;
				}
				if(v.getId()==R.id.porkImageView2){
					isPorkOnGround2 = false;
					isPork2Released = false;
				}
				if(v.getId()==R.id.newsPaperImageView){
					isNewsPaperOnGround = false;
					isPaperReleased = false;
				}
				
				FrameLayout.LayoutParams par = (FrameLayout.LayoutParams)v.getLayoutParams();
				par.topMargin = (int)event.getRawY() - (v.getHeight());
				par.leftMargin = (int)event.getRawX() - (v.getWidth()/2);
				v.setLayoutParams(par);
				return true;
			}
			else if(event.getAction()==MotionEvent.ACTION_UP){
				
				
				if(v.getId()==R.id.porkImageView){
					isDragged = false;
					isPorkReleased = true;
				}
				if(v.getId()==R.id.porkImageView2){
					isDragged2 = false;
					isPork2Released = true;
				}
				if(v.getId()==R.id.newsPaperImageView){
					isDragged3 = false;
					isPaperReleased = true;
				}
				
			}
			return false;
		}
	}
	
	//temporary game loop will change to thread class
	private Runnable gameLoop = new Runnable(){
		int[] status;
		Handler loopHandler = new Handler();
		FrameLayout.LayoutParams par;
		
		public void run(){
			
			while(gameRunning){
				status = petView.getStatus();
				
				loopHandler.post(new Runnable(){
					public void run(){
						foodBar.setProgress(status[0]);
						happyBar.setProgress(status[1]);
						
						if(petView.getVisibility() == ImageView.VISIBLE){
							loadingText.setVisibility(TextView.INVISIBLE);
						}
						if(petView.getState() == PetStates.POOPING){
							if(petView.isPooping){
								Shit shit = new Shit(MainActivity.this);
								par = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
										LayoutParams.WRAP_CONTENT);
								par.leftMargin = petView.getLeft()+petView.getWidth()-50;
								par.topMargin = petView.getTop()+petView.getHeight()-50;
								par.gravity = Gravity.TOP;
								frameLayout.addView(shit,par);
								petView.isPooping = false;
							}
						}
						
					}
				});
				
				objectsGravity();
				if(petView.isEnabled() == true){
					objectsCollision();
				}
				
			}
		}
		private void objectsCollision(){
			int petX = petView.getLeft()/2;
			int petY = petView.getTop()/2;
			int objX = porkButt.getLeft()/2;
			int objY = porkButt.getTop()/2;
			
			int distance = (int)Math.sqrt(Math.pow((objX - petX), 2) + 
					Math.pow((objY - petY), 2));
			
			if(isPorkReleased){
				//PORKBUTT COLLISION-----------------------------------------------------------------------------------
				if(distance < (petView.getWidth()/4 + porkButt.getWidth()/4) && !isPorkOnGround){
					par = (FrameLayout.LayoutParams)porkButt.getLayoutParams();
					par.topMargin = 50;
					par.leftMargin = 600;
					
					loopHandler.post(new Runnable(){
						public void run(){
							porkButt.setOnTouchListener(null);
							porkButt.setLayoutParams(par);
							isDragged = false;
							isPorkReleased = false;
						}
					});
					petView.setState(5);
				}
				//-----------------------------------------------------------------------------------
			}
			
			objX = porkButt2.getLeft()/2;
			objY = porkButt2.getTop()/2;
			
			distance = (int)Math.sqrt(Math.pow((objX - petX), 2) + 
					Math.pow((objY - petY), 2));
			
			if(isPork2Released){
				//PORKBUTT 2 COLLISION-----------------------------------------------------------------------------------
				if(distance < (petView.getWidth()/4 + porkButt2.getWidth()/4) && !isPorkOnGround2){
					par = (FrameLayout.LayoutParams)porkButt2.getLayoutParams();
					par.topMargin = 50;
					par.leftMargin = 500;
					
					loopHandler.post(new Runnable(){
						public void run(){
							porkButt2.setOnTouchListener(null);
							porkButt2.setLayoutParams(par);
							isDragged2 = false;
							isPork2Released = false;
						}
					});
					petView.setState(5);
				}
				//-----------------------------------------------------------------------------------
			}
			
			objX = newsPaper.getLeft()/2;
			objY = newsPaper.getTop()/2;
			
			distance = (int)Math.sqrt(Math.pow((objX - petX), 2) + 
					Math.pow((objY - petY), 2));
			
			if(isPaperReleased){
				//NEWSPAPER COLISION---------------------------------------------------------------------
				if(distance < (petView.getWidth()/4 + newsPaper.getWidth()/4) && !isNewsPaperOnGround){
					par = (FrameLayout.LayoutParams)newsPaper.getLayoutParams();
					par.topMargin = 50;
					par.leftMargin = 300;
					
					loopHandler.post(new Runnable(){
						public void run(){
							newsPaper.setOnTouchListener(null);
							newsPaper.setLayoutParams(par);
							isDragged3 = false;
							isPaperReleased = false;
						}
					});
					petView.setState(7);
				}
				//-----------------------------------------------------------------------------------------
			}
		}
		private void objectsGravity(){
			if(isDragged == false){
				par = (FrameLayout.LayoutParams)porkButt.getLayoutParams();
				par.topMargin += 1;
				
				if(par.topMargin >= screenHeight-200){
					par.topMargin = screenHeight-200;
					isDragged = true;
					isPorkOnGround = true;
					loopHandler.post(new Runnable(){
						public void run(){
							porkButt.setOnTouchListener(MainActivity.this);
						}
					});
				}
				
				loopHandler.post(new Runnable(){
					public void run(){
						porkButt.setLayoutParams(par);
					}
				});
			}
			if(isDragged2 == false){
				par = (FrameLayout.LayoutParams)porkButt2.getLayoutParams();
				par.topMargin += 1;
				if(par.topMargin >= screenHeight-180){
					par.topMargin = screenHeight-180;
					isDragged2 = true;
					isPorkOnGround2 = true;
					loopHandler.post(new Runnable(){
						public void run(){
							porkButt2.setOnTouchListener(MainActivity.this);
						}
					});
				}
				loopHandler.post(new Runnable(){
					public void run(){
						porkButt2.setLayoutParams(par);
					}
				});
			}
			if(isDragged3 == false){
				par = (FrameLayout.LayoutParams)newsPaper.getLayoutParams();
				par.topMargin += 1;
				if(par.topMargin >= screenHeight-170){
					par.topMargin = screenHeight-170;
					isDragged3 = true;
					isNewsPaperOnGround = true;
					loopHandler.post(new Runnable(){
						public void run(){
							newsPaper.setOnTouchListener(MainActivity.this);
						}
					});
				}
				loopHandler.post(new Runnable(){
					public void run(){
						newsPaper.setLayoutParams(par);
					}
				});
			}
		}
	};
	
	//Gesture listener for pet flings
	class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
		public boolean onDown(MotionEvent event){
			return true;
		}
		
		public boolean onFling(MotionEvent event1, MotionEvent event2,
				float velocityX, float velocityY){
			int largeMove = 60;
			
			if(event2.getX()-event1.getX()>largeMove){
				//Fling right
				petView.startAnimation(flingRight);
				return false;
			}
			else if(event1.getX()-event2.getX()>largeMove){
				//fling left
				petView.startAnimation(flingLeft);
				return false;
			}
			else if(event1.getY()-event2.getY()>largeMove){
				petView.startAnimation(flingUp);
				return false;
			}
			else{
				return false;
			}
		}
	}
	
	//animation listener for animation
	class AnimationListener implements Animation.AnimationListener{

		@Override
		public void onAnimationEnd(Animation animation) {
			if(animation.equals(flingRight)){
				FrameLayout.LayoutParams par = (FrameLayout.LayoutParams)petView.getLayoutParams();
				par.leftMargin += deltaX;
				petView.setLayoutParams(par);
				petView.clearAnimation();
			}
			else if(animation.equals(flingLeft)){
				FrameLayout.LayoutParams par = (FrameLayout.LayoutParams)petView.getLayoutParams();
				par.leftMargin += mdeltaX;
				petView.setLayoutParams(par);
				petView.clearAnimation();
			}
			else if(animation.equals(flingUp)){
				
			}
			else if(animation.equals(walk)){
				FrameLayout.LayoutParams par = (FrameLayout.LayoutParams)petView.getLayoutParams();
				par.leftMargin += walkDeltaX;
				petView.setLayoutParams(par);
				petView.clearAnimation();
			}
			else if(animation.equals(walkBack)){
				FrameLayout.LayoutParams par = (FrameLayout.LayoutParams)petView.getLayoutParams();
				par.leftMargin += mWalkDeltaX;
				petView.setLayoutParams(par);
				petView.clearAnimation();
			}
			
			petView.setState(2);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
			if(animation.equals(flingRight)){
				petView.setState(3);
			}
			else if(animation.equals(flingLeft)){
				petView.setState(4);
			}
			else if(animation.equals(flingUp)){
				petView.setState(8);
			}
			else if(animation.equals(walk)){
				petView.setState(10);
			}
			else if(animation.equals(walkBack)){
				petView.setState(11);
			}
		}
		
	}
	
	private class LoadProps extends AsyncTask<Void,Void,Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			//getting screen dimensions TODO: deprecation check
			Display display = getWindowManager().getDefaultDisplay();
			screenWidth = display.getWidth();
			screenHeight = display.getHeight();
			
			coinSpin = (AnimationDrawable)getResources().getDrawable(R.drawable.coinspin);
			return null;
		}
		
		protected void onPostExecute(Void result){
			int sdk = android.os.Build.VERSION.SDK_INT;
			
			if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN){
				coinView.setBackgroundDrawable(coinSpin);
			}
			else{
				coinView.setBackground(coinSpin);
			}
			coinView.post(coinSpin);
			coinView.post(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					coinSpin.start();
				}
				
			});
		}
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			//get xyz values from the sensor
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			
			//if previous values have not been initialized
			if(initialized == false){
				//initialize them
				lastX = x;
				lastY = y;
				lastZ = z;
				initialized = true;
			}
			else{
				//get change in x y and z axis respectively
				float deltaX = Math.abs(lastX - x);
				float deltaY = Math.abs(lastY - y);
				float deltaZ = Math.abs(lastZ - z);
				
				//if the change is less than 2.0(noise)
				//i.e small movements should not be read
				if(deltaX < noise){
					deltaX = (float)0.0;
				}
				if(deltaY < noise){
					deltaY = (float)0.0;
				}
				if(deltaZ < noise){
					deltaZ = (float)0.0;
				}
				
				//update previous coordinates with current one
				lastX = x;
				lastY = y;
				lastZ = z;
				
				//if change in x is greater than change in y
				if(deltaX > deltaY){
					//motionView.setText("Horizontal Movement!");
					Log.i("OUTPUT", "dizzy");
					petView.setState(6);
				}
				//if change in y is greater than change in x
				else if(deltaY > deltaX){
					//motionView.setText("Vertical Movement!");
					Log.i("OUTPUT", "dizzy");
					petView.setState(6);
				}
				//if change in z is greater than change in y & x
				else if(deltaZ > deltaX || deltaZ > deltaY){
					//motionView.setText("Depth Movement!");
					Log.i("OUTPUT", "dizzy");
					petView.setState(6);
				}
			}
		}
		
	}
}
