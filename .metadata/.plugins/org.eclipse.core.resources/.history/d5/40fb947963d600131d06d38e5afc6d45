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
package com.ritolaaudio.javajack.client;

import java.io.Serializable;

/**
 * Invoked when there is a reported change in a JACK Client's playback latency. 
 * Playback latency is the latency between a Server's output (terminal) port and this Client's output port, 
 * including the latencies incurred by intermediate Clients inserted along the way.
 * @author chuck
 *
 */

public interface PlaybackLatencyListener extends Serializable
	{
	/**
	 * Typically invoked when another Client has changed its latency value. 
	 * When this happens, all other Clients may have to compensate for this change in latency.
	 * @param localClient
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public void recalculatePlaybackLatency(Client localClient);
	}
