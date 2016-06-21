/*
 * Created on 05-dic-2005
 *
 */
package org.herac.tuxguitar.song.models;

/**
 * @author julian
 *
 */
public class TGDivisionType {
	public static final TGDivisionType NORMAL = new TGDivisionType(1,1);
	
	/**
	 * Number of durations that fall in the time
	 */
	private int enters;

	private int times;
	
	public TGDivisionType(){
		this.enters = 1;
		this.times = 1;
	}
	
	public TGDivisionType(int enters, int times) {
		this.enters = enters;
		this.times = times;
	}

	public int getEnters() {
		return this.enters;
	}
	
	public void setEnters(int enters) {
		this.enters = enters;
	}
	
	public int getTimes() {
		return this.times;
	}
	
	public void setTimes(int times) {
		this.times = times;
	}
	
	public long convertTime(long time){
		return time * this.times / this.enters;
	}
	
	public boolean isEqual(TGDivisionType divisionType){
		return (divisionType.getEnters() == getEnters() && divisionType.getTimes() == getTimes());
	}
	
	public TGDivisionType clone(){
		TGDivisionType divisionType = new TGDivisionType();
		copy(divisionType);
		return divisionType;
	}
	
	// TODO remove or rename
	public void copy(TGDivisionType divisionType){
		divisionType.setEnters(this.enters);
		divisionType.setTimes(this.times);
	}
	
}
