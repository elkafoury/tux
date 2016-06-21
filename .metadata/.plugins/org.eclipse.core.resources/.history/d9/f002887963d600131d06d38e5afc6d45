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
 * Invoked when a JACK Client experiences a change in buffer size.
 * @author chuck
 *
 */
public interface BufferSizeListener extends Serializable
	{
	/**
	 * Invokes when a buffer size change has occurred, passing the new size and the Client who invoked this Listener.
	 * @param newNumberOfFrames
	 * @param localClient
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public void bufferSizeChangeEvent(int newNumberOfFrames, Client localClient);
	}
