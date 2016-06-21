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

public class JACKTimecodePosition extends JACKPosition
	{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1791984870091535906L;
	public JACKTimecodePosition(long uid, long timeInMicroseconds, int frameRate, long uid2,
			double frameTime, double nextTime, int bbtOffset, int videoOffset,
			float audioFramesPerVideoFrame)
		{
		super(uid, timeInMicroseconds, frameRate, uid2);
		this.frameTime=frameTime;
		this.nextTime=nextTime;
		this.bbtOffset=bbtOffset;
		this.videoOffset=videoOffset;
		this.audioFramesPerVideoFrame=audioFramesPerVideoFrame;
		}
	private double frameTime, nextTime;
	public double getFrameTime()
		{
		return frameTime;
		}
	public void setFrameTime(double frameTime)
		{
		this.frameTime = frameTime;
		}
	public double getNextTime()
		{
		return nextTime;
		}
	public void setNextTime(double nextTime)
		{
		this.nextTime = nextTime;
		}
	public int getBbtOffset()
		{
		return bbtOffset;
		}
	public void setBbtOffset(int bbtOffset)
		{
		this.bbtOffset = bbtOffset;
		}
	public int getVideoOffset()
		{
		return videoOffset;
		}
	public void setVideoOffset(int videoOffset)
		{
		this.videoOffset = videoOffset;
		}
	public float getAudioFramesPerVideoFrame()
		{
		return audioFramesPerVideoFrame;
		}
	public void setAudioFramesPerVideoFrame(float audioFramesPerVideoFrame)
		{
		this.audioFramesPerVideoFrame = audioFramesPerVideoFrame;
		}
	private int bbtOffset,videoOffset;
	private float audioFramesPerVideoFrame;
	}
