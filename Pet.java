/**
 * 
 */
package com.example.gritters;

import android.util.Log;

/**
 * @author tobakolawole
 *
 */
enum PetStates {
	HAPPY,HUNGRY,STANDING,SAD,WALKINGLEFT,WALKINGRIGHT
	,DIZZY,NSTANDING,FLINGRIGHT,FLINGLEFT,EATING
	,PAPERHIT,FLINGUP,BUTTDANCE,PISSING,POOPING
}

public class Pet {
	private String name;
	int food;
	int happy;
	private final int MAX_UNIT = 100;
	private PetStates state;
	
	private int madCount;
	private int badBehaviour;
	private int defaultReduceRate;
	
	public Pet(String name){
		this.name = name;
		happy = 40;
		food = 60;
		state = PetStates.NSTANDING;
		madCount = 0;
		badBehaviour = 100;
		defaultReduceRate = 5;
	}
	
	public void doSomething(){
		
	}
	
	public void setState(PetStates state){
		this.state = state;
	}
	public PetStates getState(){
		return state;
	}
	public void timeChange(){
		//Log.d("ONDRAW", "TIME: "+calendar.getTimeInMillis());
		int rand = (int)(Math.random() * 4);
		//int rand = 2;
		
		switch(state){
			case NSTANDING:
				if(food < 70 && happy < 70){
					normalActs(rand);
				}
				else if(food > 70 && happy < 70){
					foodActs(rand);
				}
				else if(happy > 70 && food < 70){
					happyActs(rand);
				}
				else{
					happyActs(rand);
				}
				
				break;
			case DIZZY:
				state = PetStates.NSTANDING;
				break;
			default:
				//state = PetStates.NSTANDING;
				break;
		}
		
		reduceBars(defaultReduceRate,0);
	}
	private void normalActs(int rand){
		if(rand == 0){
			state = PetStates.STANDING;
		}
		else if(rand == 1){
			state = PetStates.PISSING;
		}
		else if(rand == 2){
			state = PetStates.POOPING;
		}
		else if(rand == 3){
			state = PetStates.BUTTDANCE;
		}
	}
	private void foodActs(int rand){
		if(rand == 0){
			state = PetStates.STANDING;
		}
		else if(rand == 1){
			state = PetStates.PISSING;
		}
		else if(rand == 2){
			state = PetStates.POOPING;
		}
		else if(rand == 3){
			state = PetStates.POOPING;
		}
	}
	private void happyActs(int rand){
		if(rand == 0){
			state = PetStates.BUTTDANCE;
		}
		else if(rand == 1){
			state = PetStates.BUTTDANCE;
		}
		else if(rand == 2){
			state = PetStates.BUTTDANCE;
		}
		else if(rand == 3){
			state = PetStates.PISSING;
		}
	}
	
	public void increaseBars(int rate, int flag){
		Log.i("ONDRAW","INCING");
		if(flag == 0){
			food = food + rate;
			if(food >= 100){
				food = 100;
			}
			happy = happy + rate;
			if(happy >= 100){
				happy = 100;
			}
		}
		else if(flag == 1){
			food = food + rate;
			if(food >= 100){
				food = 100;
			}
		}
		else if(flag == 2){
			happy = happy + rate;
			if(happy >= 100){
				happy = 100;
			}
		}
		else{
			//do nothing
		}
		
	}
	public void reduceBars(int rate, int flag){
		
		if(flag == 0){
			food = food - rate;
			if(food <= 0){
				food = 0;
			}
			happy = happy - rate;
			if(happy <= 0){
				happy = 0;
			}
		}
		else if(flag == 1){
			food = food - rate;
			if(food <= 0){
				food = 0;
			}
		}
		else if(flag == 2){
			happy = happy - rate;
			if(happy <= 0){
				happy = 0;
			}
		}
		else{
			//do nothing
		}
	}
}
