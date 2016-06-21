/*******************************************************************************
 * JavaJACK - A JACK bridge for Java.
 *     Copyright (C) 2011  Chuck Ritola
 * 	chuck@ritolaaudio.com 
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.ritolaaudio.javajack.transport;

public class JACKBBTPosition extends JACKPosition
	{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2685204021487924857L;

	public int getBar()
		{
		return bar;
		}

	public void setBar(int bar)
		{
		this.bar = bar;
		}

	public int getBeat()
		{
		return beat;
		}

	public void setBeat(int beat)
		{
		this.beat = beat;
		}

	public int getTick()
		{
		return tick;
		}

	public void setTick(int tick)
		{
		this.tick = tick;
		}

	public double getBarStartTick()
		{
		return barStartTick;
		}

	public void setBarStartTick(double barStartTick)
		{
		this.barStartTick = barStartTick;
		}

	public double getTicksPerBeat()
		{
		return ticksPerBeat;
		}

	public void setTicksPerBeat(double ticksPerBeat)
		{
		this.ticksPerBeat = ticksPerBeat;
		}

	public double getBeatsPerMinute()
		{
		return beatsPerMinute;
		}

	public void setBeatsPerMinute(double beatsPerMinute)
		{
		this.beatsPerMinute = beatsPerMinute;
		}

	public float getBeatsPerBar()
		{
		return beatsPerBar;
		}

	public void setBeatsPerBar(float beatsPerBar)
		{
		this.beatsPerBar = beatsPerBar;
		}

	public float getBeatType()
		{
		return beatType;
		}

	public void setBeatType(float beatType)
		{
		this.beatType = beatType;
		}

	private int bar,beat,tick;
	private double barStartTick,ticksPerBeat,beatsPerMinute;
	private float beatsPerBar, beatType;
	
	public JACKBBTPosition(long uid, long timeInMicroseconds, int frameRate, long uid2,
			int bar, int beat, int tick, double barStartTick, double ticksPerBeat, double beatsPerMinute,
			float beatsPerBar, float beatType)
		{
		super(uid, timeInMicroseconds, frameRate, uid2);
		this.bar=bar; this.beat=beat; this.tick=tick; 
		this.barStartTick=barStartTick; this.ticksPerBeat=ticksPerBeat; 
		this.beatsPerMinute=beatsPerMinute; this.beatsPerBar=beatsPerBar;
		this.beatType=beatType;
		}//end constructor
	}
