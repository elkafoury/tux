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
package com.ritolaaudio.javajack;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Originally intended to be a wrapper for JACK's threads, this may never be implemented unless there is a clear rationale
 * for doing so. Nonetheless, here it is in case you would like to do so.
 * @author Chuck Ritola
 *
 */
public interface JACKThread extends Remote
	{
	public void start()throws RemoteException;
	public void run()throws RemoteException;
	public void dropRealtimeScheduling()throws RemoteException;
	}
