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
public class TGEffectTremoloBar {
	public static final int MAX_POSITION_LENGTH = 12;
	public static final int MAX_VALUE_LENGTH = 12;
	
	private List<TremoloBarPoint> points;
	
	public TGEffectTremoloBar(){
		this.points = new ArrayList<TremoloBarPoint>();
	}
	
	public void addPoint(int position,int value){
		this.points.add(new TremoloBarPoint(position,value));
	}
	
	public List<TremoloBarPoint> getPoints(){
		return this.points;
	}
	
	public TGEffectTremoloBar clone(){
		TGEffectTremoloBar effect = new TGEffectTremoloBar();
		Iterator<TremoloBarPoint> it = getPoints().iterator();
		while(it.hasNext()){
			TremoloBarPoint point = (TremoloBarPoint)it.next();
			effect.addPoint(point.getPosition(),point.getValue());
		}
		
		return effect;
	}
	
	public class TremoloBarPoint{
		private int position;
		private int value;
		
		public TremoloBarPoint(int position,int value){
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
	}
	
}
