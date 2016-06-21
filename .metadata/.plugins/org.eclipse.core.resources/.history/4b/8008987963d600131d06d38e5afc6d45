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

import java.rmi.RemoteException;

import com.sun.jna.Pointer;

/**
 * SessionListeners catch JACK Session Events involved in the starting/stopping/saving of JACK Sessions. It allows applications to 
 * distribute Server-wide save/close operations to alleviate the need for manual opening and closing.
 * @author Chuck Ritola
 *
 */
public interface SessionListener
	{
	public void sessionMessage(
			SessionEventType type, String sessionDirectory, String clientUUID, 
			Pointer commandLine, JACKSessionFlagset sessionFlags, Client client)throws RemoteException;
	}
