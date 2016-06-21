/*
 * Created on 26-dic-2005
 *
 */
package org.herac.tuxguitar.song.models.effects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author julian
 *
 */
public class TGEffectBend {
	public static final int SEMITONE_LENGTH = 1;
	public static final int MAX_POSITION_LENGTH = 12;
	public static final int MAX_VALUE_LENGTH = (SEMITONE_LENGTH * 12);
	
	private List<BendPoint> points;
	
	public TGEffectBend(){
		this.points = new ArrayList<BendPoint>();
	}
	
	public void addPoint(int position,int value){
		this.points.add(new BendPoint(position,value));
	}
	
	public List<BendPoint> getPoints(){
		return this.points;
	}

	public TGEffectBend clone(){
		TGEffectBend effect = new TGEffectBend() {};
		Iterator<BendPoint> it = getPoints().iterator();
		while(it.hasNext()){
			BendPoint point = (BendPoint)it.next();
			effect.addPoint(point.getPosition(),point.getValue());
		}
		return effect;
	}
	
	public class BendPoint{
		private int position;
		private int value;
		
		public BendPoint(int position,int value){
			this.position = position;
			this.value = value;
		}
		
		public int getPosition() {
			return this.position;
		}
		
		public int getValue() {
			return this.value;
		}
		
		public long getTime(long duration){
			return (duration * getPosition() / MAX_POSITION_LENGTH);
		}
		
		public Object clone(){
			return new BendPoint(getPosition(),getValue());
		}
	}
	
}
