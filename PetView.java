package com.example.gritters;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;



public class PetView extends ImageView{
	
	private AnimationDrawable standingAnim;
	private AnimationDrawable standingAnim2;
	private AnimationDrawable flingRight;
	private AnimationDrawable flingLeft;
	private AnimationDrawable eating;
	private AnimationDrawable dizzy;
	private AnimationDrawable paperHit;
	private AnimationDrawable flingUp;
	private AnimationDrawable buttDance;
	private AnimationDrawable walkingRight;
	private AnimationDrawable walkingLeft;
	private AnimationDrawable pissing;
	private AnimationDrawable pooping;
	
	private AnimationDrawable currAnim;
	private LoadProps loadProps;
	
	
	private Pet pet;
	private Context context;
	private SoundPlayer soundPlayer;
	private int sdk = android.os.Build.VERSION.SDK_INT;
	public boolean isPooping = false;
	
	private boolean posted1 =false;
	private boolean posted2 =false;
	private boolean posted3 = false;
	private boolean posted4 = false;
	private boolean posted5 = false;
	private boolean posted6 = false;
	private boolean posted7 = false;
	private boolean posted8 = false;
	private boolean posted9 = false;
	private boolean posted10 = false;
	private boolean posted11 = false;
	private boolean posted12 = false;
	private boolean posted13 = false;
	
	FrameLayout.LayoutParams par;
	
	@SuppressWarnings("deprecation")
	public PetView(Context context){
		super(context);
		pet = new Pet("Gritty");
		
		soundPlayer = new SoundPlayer();
		this.context = context;
		
		
		standingAnim2 = (AnimationDrawable)getResources().getDrawable(R.drawable.nstanding);
		loadProps = new LoadProps();
		
		setVisibility(PetView.INVISIBLE);
		loadProps.execute();
		
		
		//setting default animation
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN){
			setBackgroundDrawable(standingAnim2);
		}
		else{
			setBackground(standingAnim2);
		}
		
	}
	
	//starting default animation
	public void startAnimationF(){
		standingAnim2.start();
	}
	
	//sets the animation drawable based the states
	public void onDraw(Canvas canvas){
		switch(pet.getState()){
		
			case STANDING:
				currAnim = standingAnim;
				if(!posted1){
					setCurrentAnimation();
					posted1 = true;
					posted2 =false;
					posted3 = false;
					posted4 = false;
					posted5 = false;
					posted6 = false;
					posted7 = false;
					posted8 = false;
					posted9 = false;
					posted10 = false;
					posted11 = false;
					posted12 = false;
					posted13 = false;
					this.setEnabled(false);
					soundPlayer.playScratch(context);
				}
				checkIfAnimationDone(currAnim);
				break;
				
			case NSTANDING:
				currAnim = standingAnim2;
				if(!posted2){
					setCurrentAnimation();
					posted2 = true;
					posted1 = false;
					posted3 = false;
					posted4 = false;
					posted5 = false;
					posted6 = false;
					posted7 = false;
					posted8 = false;
					posted9 = false;
					posted10 = false;
					posted11 = false;
					posted12 = false;
					posted13 = false;
					
					
					par = (FrameLayout.LayoutParams)getLayoutParams();
					par.topMargin = 250;
					setLayoutParams(par);
				}
				break;
				
			case FLINGRIGHT:
				currAnim = flingRight;
				if(!posted3){
					setCurrentAnimation();
					increaseBars(5, 2);
					posted3 = true;
					posted1 = false;
					posted2 = false;
					posted4 = false;
					posted5 = false;
					posted6 = false;
					posted7 = false;
					posted8 = false;
					posted9 = false;
					posted10 = false;
					posted11 = false;
					posted12 = false;
					posted13 = false;
					soundPlayer.playFling(context);
				}
				break;
				
			case FLINGLEFT:
				currAnim = flingLeft;
				if(!posted4){
					setCurrentAnimation();
					increaseBars(5, 2);
					posted4 = true;
					posted3 = false;
					posted1 = false;
					posted2 = false;
					posted5 = false;
					posted6 = false;
					posted7 = false;
					posted8 = false;
					posted9 = false;
					posted10 = false;
					posted11 = false;
					posted12 = false;
					posted13 = false;
					soundPlayer.playFling(context);
				}
				break;
				
			case EATING:
				currAnim = eating;
				if(!posted5){
					setCurrentAnimation();
					increaseBars(10,1);
					increaseBars(5,2);
					posted5 = true;
					posted4 = false;
					posted3 = false;
					posted1 = false;
					posted2 = false;
					posted6 = false;
					posted7 = false;
					posted8 = false;
					posted9 = false;
					posted10 = false;
					posted11 = false;
					posted12 = false;
					posted13 = false;
					this.setEnabled(false);
					soundPlayer.playEat(context);
				}
				checkIfAnimationDone(currAnim);
				break;
			case DIZZY:
				currAnim = dizzy;
				if(!posted6){
					setCurrentAnimation();
					posted6 = true;
					posted5 = false;
					posted4 = false;
					posted3 = false;
					posted1 = false;
					posted2 = false;
					posted7 = false;
					posted8 = false;
					posted9 = false;
					posted10 = false;
					posted11 = false;
					posted12 = false;
					posted13 = false;
					soundPlayer.playDizzy(context);
					randomDizzy();
				}
				break;
			case PAPERHIT:
				currAnim = paperHit;
				if(!posted7){
					setCurrentAnimation();
					posted7 = true;
					posted6 = false;
					posted5 = false;
					posted4 = false;
					posted3 = false;
					posted1 = false;
					posted2 = false;
					posted8 = false;
					posted9 = false;
					posted10 = false;
					posted11 = false;
					posted12 = false;
					posted13 = false;
					this.setEnabled(false);
					soundPlayer.playPaperHit(context);
					
					par = (FrameLayout.LayoutParams)this.getLayoutParams();
					par.topMargin -= 23;
					this.setLayoutParams(par);
					
					pet.reduceBars(20, 2);
					
				}
				checkIfAnimationDone(currAnim);
				break;
			case FLINGUP:
				currAnim = flingUp;
				if(!posted8){
					setCurrentAnimation();
					increaseBars(5, 2);
					posted8 = true;
					posted7 = false;
					posted6 = false;
					posted5 = false;
					posted4 = false;
					posted3 = false;
					posted1 = false;
					posted2 = false;
					posted9 = false;
					posted10 = false;
					posted11 = false;
					posted12 = false;
					posted13 = false;
					soundPlayer.playFling(context);
				}
				break;
			case BUTTDANCE:
				currAnim = buttDance;
				if(!posted9){
					setCurrentAnimation();
					posted9 = true;
					posted8 = false;
					posted7 = false;
					posted6 = false;
					posted5 = false;
					posted4 = false;
					posted3 = false;
					posted1 = false;
					posted2 = false;
					posted10 = false;
					posted11 = false;
					posted12 = false;
					posted13 = false;
					this.setEnabled(false);
					soundPlayer.playButtDance(context);
				}
				checkIfAnimationDone(currAnim);
				break;
			case WALKINGRIGHT:
				currAnim = walkingRight;
				if(!posted10){
					setCurrentAnimation();
					posted10 = true;
					posted9 = false;
					posted8 = false;
					posted7 = false;
					posted6 = false;
					posted5 = false;
					posted4 = false;
					posted3 = false;
					posted1 = false;
					posted2 = false;
					posted11 = false;
					posted12 = false;
					posted13 = false;
					soundPlayer.playWalk(context);
				}
				break;
			case WALKINGLEFT:
				currAnim = walkingLeft;
				if(!posted11){
					setCurrentAnimation();
					posted11 = true;
					posted10 = false;
					posted9 = false;
					posted8 = false;
					posted7 = false;
					posted6 = false;
					posted5 = false;
					posted4 = false;
					posted3 = false;
					posted1 = false;
					posted2 = false;
					posted12 = false;
					posted13 = false;
					soundPlayer.playWalk(context);
				}
				break;
			case PISSING:
				currAnim = pissing;
				if(!posted12){
					setCurrentAnimation();
					posted12 = true;
					posted11 = false;
					posted10 = false;
					posted9 = false;
					posted8 = false;
					posted7 = false;
					posted6 = false;
					posted5 = false;
					posted4 = false;
					posted3 = false;
					posted1 = false;
					posted2 = false;
					posted13 = false;
					this.setEnabled(false);
					soundPlayer.playPee(context);
				}
				checkIfAnimationDone(currAnim);
				break;
			case POOPING:
				currAnim = pooping;
				if(!posted13){
					setCurrentAnimation();
					posted13 = true;
					posted12 = false;
					posted11 = false;
					posted10 = false;
					posted9 = false;
					posted8 = false;
					posted7 = false;
					posted6 = false;
					posted5 = false;
					posted4 = false;
					posted3 = false;
					posted1 = false;
					posted2 = false;
					this.setEnabled(false);
					soundPlayer.playShitingSound(context);
				}
				checkIfAnimationDone(currAnim);
				break;
				
			default:
				break;
		}
		invalidate();
		super.onDraw(canvas);
	}
	
	//timer events
	public void timeChange(){
		pet.timeChange();
	}
	public void increaseBars(int rate, int flag){
		pet.increaseBars(rate, flag);
	}
	public void releaseSounds(){
		soundPlayer.releaseSounds();
	}
	
	private void randomDizzy(){
		int rand = (int)Math.random() * 3;
		if(rand == 0){
			pet.reduceBars(20,2);
		}
		else{
			pet.increaseBars(20,2);
		}
	}
	
	//sets the current animation drawable
	private void setCurrentAnimation(){
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN){
			setBackgroundDrawable(currAnim);
		}
		else{
			setBackground(currAnim);
		}
		post(currAnim);
		post(runnable);
	}
	private Runnable runnable = new Runnable() {
		   @Override
		   public void run() {
			   currAnim.start();
		   }
	};
	
	//getting info from status bars
	public int[] getStatus(){
		int[] status = new int[2];
		status[0] = pet.food;
		status[1] = pet.happy;
		return status;
	}
	
	public PetStates getState(){
		return pet.getState();
	}
	
	//method that sets state based on particular flag
	public void setState(int flag){
		if(flag==2){
			pet.setState(PetStates.NSTANDING);
		}
		if(flag==3){
			pet.setState(PetStates.FLINGRIGHT);
		}
		if(flag==4){
			pet.setState(PetStates.FLINGLEFT);
		}
		if(flag==5){
			pet.setState(PetStates.EATING);
		}
		if(flag==6){
			pet.setState(PetStates.DIZZY);
		}
		if(flag==7){
			pet.setState(PetStates.PAPERHIT);
		}
		if(flag==8){
			pet.setState(PetStates.FLINGUP);
		}
		if(flag==9){
			pet.setState(PetStates.BUTTDANCE);
		}
		if(flag==10){
			pet.setState(PetStates.WALKINGRIGHT);
		}
		if(flag==11){
			pet.setState(PetStates.WALKINGLEFT);
		}
	}
	
	//checks if current animation has stopped.
	private void checkIfAnimationDone(AnimationDrawable anim){
        final AnimationDrawable a = anim; //frame animation to be checked
        int timeBetweenChecks = 100; //specified checking time
        Handler h = new Handler(); //UI handler
        h.postDelayed(new Runnable(){
            public void run(){
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)){ //if we are not at the last frame
                	if(pet.getState() == PetStates.POOPING){
                		if(a.getCurrent() == a.getFrame(a.getNumberOfFrames() - 3)){
                			isPooping = true;
                		}
                		if(a.getCurrent() == a.getFrame(a.getNumberOfFrames() - 2)){
                			isPooping = true;
                		}
                		if(a.getCurrent() == a.getFrame(a.getNumberOfFrames() - 1)){
                			isPooping = true;
                		}
                	}
                    checkIfAnimationDone(a); //recursive call
                } else{
                	switch(pet.getState()){//switch the state
                	 	case STANDING:
                	 		setEnabled(true);
                	 		setState(2);
                	 		break;
                	 	case EATING:
                	 		setEnabled(true);
                	 		setState(2);
                	 		break;
                	 	case PAPERHIT:
                	 		setEnabled(true);
                	 		setState(2);
                	 		break;
                	 	case BUTTDANCE:
                	 		setEnabled(true);
                	 		setState(2);
                	 		break;
                	 	case PISSING:
                	 		setEnabled(true);
                	 		setState(2);
                	 		break;
                	 	case POOPING:
                	 		setEnabled(true);
                	 		setState(2);
                	 		break;
                	 	default:
                	 		break;
                	}
                }
            }
        }, timeBetweenChecks);
    };
    
    private class LoadProps extends AsyncTask<Void,Void,Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//getting frames from resource
			standingAnim = (AnimationDrawable)getResources().getDrawable(R.drawable.standing);
			flingRight = (AnimationDrawable)getResources().getDrawable(R.drawable.flingright);
			flingLeft = (AnimationDrawable)getResources().getDrawable(R.drawable.flingleft);
			eating = (AnimationDrawable)getResources().getDrawable(R.drawable.eating);
			dizzy = (AnimationDrawable)getResources().getDrawable(R.drawable.dizzy);
			paperHit = (AnimationDrawable)getResources().getDrawable(R.drawable.paperhit);
			flingUp = (AnimationDrawable)getResources().getDrawable(R.drawable.flingup);
			buttDance = (AnimationDrawable)getResources().getDrawable(R.drawable.buttdance);
			walkingRight = (AnimationDrawable)getResources().getDrawable(R.drawable.walkingright);
			walkingLeft = (AnimationDrawable)getResources().getDrawable(R.drawable.walkingleft);
			pissing = (AnimationDrawable)getResources().getDrawable(R.drawable.pissing);
			pooping = (AnimationDrawable)getResources().getDrawable(R.drawable.pooping);
			return null;
		}
		
		protected void onPostExecute(Void result){
			setVisibility(PetView.VISIBLE);
		}
		
	}

}
