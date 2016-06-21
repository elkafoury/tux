package org.herac.tuxguitar.song.models.effects;

import org.herac.tuxguitar.song.models.TGDuration;
import org.herac.tuxguitar.song.models.TGVelocities;

public class TGEffectGrace {
	public enum Transition {
		NONE (0),
		SLIDE (1),
		BEND (2),
		HAMMER (3);
		
		Transition (int value) {
			this.value = value;
		}
		
		public static Transition fromInt (int value) {
			switch (value) {
			case 0:
				return NONE;
			case 1:
				return SLIDE;
			case 2:
				return BEND;
			case 3:
				return HAMMER;
			default:
				return NONE;
			}
		}
		
		public int getValue () {
			return value;
		}
		
		private int value;
	}
	
	private int fret;
	private int duration;
	// TODO replace int with enum
	private int dynamic;
	private Transition transition;
	private boolean onBeat;
	private boolean dead;
	
	public TGEffectGrace() {
		this.fret = 0;
		this.duration = 1;
		this.dynamic = TGVelocities.DEFAULT;
		this.transition = Transition.NONE;
		this.onBeat = false;
		this.dead = false;
	}
	
	public boolean isDead() {
		return this.dead;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getDynamic() {
		return this.dynamic;
	}
	
	public void setDynamic(int dynamic) {
		this.dynamic = dynamic;
	}
	
	public int getFret() {
		return this.fret;
	}
	
	public void setFret(int fret) {
		this.fret = fret;
	}
	
	public boolean isOnBeat() {
		return this.onBeat;
	}
	
	public void setOnBeat(boolean onBeat) {
		this.onBeat = onBeat;
	}
	
	public Transition getTransition() {
		return this.transition;
	}
	
	public void setTransition(Transition transition) {
		this.transition = transition;
	}
	
	public int getDurationTime(){
		return (int)((TGDuration.QUARTER_TIME / 16.00 ) * getDuration());
	}
	
	public TGEffectGrace clone() {
		TGEffectGrace effect = new TGEffectGrace();
		effect.setFret(getFret());
		effect.setDuration(getDuration());
		effect.setDynamic(getDynamic());
		effect.setTransition(getTransition());
		effect.setOnBeat(isOnBeat());
		effect.setDead(isDead());
		return effect;
	}
	
}
