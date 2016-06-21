/*
 * Created on 29-nov-2005
 *
 */
package org.herac.tuxguitar.song.models;
/**
 * @author julian
 *
 */
public class TGDuration {
	/**
	 * tiempo por defecto de la Negra.
	 */
	public static final long QUARTER_TIME = 960;
	/**
	 * Redonda.
	 */
	public static final int WHOLE = 1;
	
	/**
	 * Blanca.
	 */
	public static final int HALF = 2;
	
	/**
	 * Negra.
	 */
	public static final int QUARTER = 4;
	
	/**
	 * Corchea.
	 */
	public static final int EIGHTH = 8;
	
	/**
	 * Semi-Corchea.
	 */
	public static final int SIXTEENTH = 16;
	
	/**
	 * Fusa.
	 */
	public static final int THIRTY_SECOND = 32;
	
	/**
	 * Semi-Fusa.
	 */
	public static final int SIXTY_FOURTH = 64;
	/**
	 * Valor.
	 */
	private int value;
	/**
	 * Puntillo.
	 */
	private boolean dotted;
	/**
	 * Doble Puntillo.
	 */
	private boolean doubleDotted;
	/**
	 * DivisionType.
	 */
	private TGDivisionType divisionType;
	
	public TGDuration(){
		this.value = QUARTER;
		this.dotted = false;
		this.doubleDotted = false;
		this.divisionType = new TGDivisionType();
	}
	
	public TGDuration(TGDuration duration) {
		this.value = duration.value;
		this.dotted = duration.dotted;
		this.doubleDotted = duration.doubleDotted;
		this.divisionType = duration.getDivision();
	}

	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public boolean isDotted() {
		return dotted;
	}
	
	public void setDotted(boolean dotted) {
		this.dotted = dotted;
	}
	
	public boolean isDoubleDotted() {
		return doubleDotted;
	}
	
	public void setDoubleDotted(boolean doubleDotted) {
		this.doubleDotted = doubleDotted;
	}
	
	public TGDivisionType getDivision(){
		return divisionType;
	}
	
	public void setDivision(TGDivisionType newDivision) {
		divisionType = newDivision;
	}
	
	public long getTime(){
		long time = (long)( QUARTER_TIME * ( 4.0f / this.value ) ) ;
		if(dotted){
			time += time / 2;
		}else if(doubleDotted){
			time += ((time / 4) * 3);
		}
		return divisionType.convertTime(time);
	}
	
	public static TGDuration fromTime(long time){
		TGDuration duration = new TGDuration();
		duration.setValue(TGDuration.SIXTY_FOURTH);
		duration.setDotted(false);
		duration.setDoubleDotted(false);
		duration.setDivision(new TGDivisionType (3, 2));
		return fromTime(time,duration);
	}
	
	public static TGDuration fromTime(long time,TGDuration minDuration){
		return fromTime(time, minDuration, 10);
	}
	
	public static TGDuration fromTime(long time,TGDuration minimum, int diff){
		TGDuration duration = minimum.clone();
		TGDuration tmpDuration = new TGDuration();
		tmpDuration.setValue(TGDuration.WHOLE);
		tmpDuration.setDotted(true);
		boolean finish = false;
		while(!finish){
			long tmpTime = tmpDuration.getTime();
			if(tmpTime - diff <= time){
				//if(tmpTime > duration.getTime()){
				if(Math.abs( tmpTime - time ) < Math.abs( duration.getTime() - time ) ){
					duration = tmpDuration.clone();
				}
			}
			if(tmpDuration.isDotted()){
				tmpDuration.setDotted(false);
			}else if(tmpDuration.getDivision().isEqual(TGDivisionType.NORMAL)){
				tmpDuration.setDivision(new TGDivisionType (3, 2));
			}else{
				tmpDuration.setValue(tmpDuration.getValue() * 2);
				tmpDuration.setDotted(true);
				tmpDuration.setDivision(new TGDivisionType (1, 1));
			}
			if(tmpDuration.getValue() > TGDuration.SIXTY_FOURTH){
				finish = true;
			}
		}
		return duration;
	}

	public int getIndex(){
		int index = 0;
		int value = this.value;
		while( ( value = ( value >> 1 ) ) > 0 ){
			index ++;
		}
		return index;
	}
	
	public boolean isEqual(TGDuration d){
		return (getValue() == d.getValue() && isDotted() == d.isDotted() && isDoubleDotted() == d.isDoubleDotted() && getDivision().isEqual(d.getDivision()));
	}
	
	public TGDuration clone(){
		TGDuration duration = new TGDuration(this);
		return duration;
	}
	
}
