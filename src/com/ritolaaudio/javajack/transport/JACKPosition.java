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

import com.ritolaaudio.jack.jack_position_t;

import java.io.Serializable;

public class JACKPosition implements Serializable
	{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8618168344894189318L;
	//Set only by creator.
	private long uID,uID2;
	private long timeInMicroseconds;
	private int frameRate;
	private int currentFrame;
	private TransportState state;
	
	public JACKPosition(long uid, long timeInMicroseconds, int frameRate, long uid2)
		{
		this.uID=uid;
		this.uID2=uid2;
		this.timeInMicroseconds=timeInMicroseconds;
		this.frameRate=frameRate;
		}
	
	public JACKPosition(jack_position_t position, TransportState _state)
		{
		this.uID=position.unique_1;
		this.uID2=position.unique_2;
		this.timeInMicroseconds=position.usecs;
		this.frameRate=position.frame_rate;
		this.currentFrame=position.frame;
		this.state=_state;
		}

	public long getUniqueID(){return uID;}
	public long getUniqueID2(){return uID2;}
	public long getTimeInMicroseconds(){return timeInMicroseconds;}
	public int getFrameRate(){return frameRate;}
	
	public int getCurrentFrame(){return currentFrame;}
	public TransportState getState(){return state;}

	public jack_position_t toNativeStruct()
		{
		jack_position_t result = new jack_position_t();
		result.unique_1=uID;
		result.unique_2=uID2;
		result.usecs=timeInMicroseconds;
		result.frame_rate=frameRate;
		result.frame=currentFrame;
		return result;
		}
	}//end JACKPosition
