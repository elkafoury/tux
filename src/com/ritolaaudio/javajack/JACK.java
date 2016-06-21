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

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

import com.ritolaaudio.javajack.server.DummyClientCreationFailureException;
import com.ritolaaudio.javajack.server.JACKServer;

/**
 * Base class for Java-JACK used in obtaining a reference to a local JACK server.
 * @author Chuck Ritola
 *
 */
public class JACK
	{
	private static JACKServer server;
	private static int rmiID=0;
	/**
	 * Obtain a local reference to a JACK server. Creates the reference on first call but then caches it thereon.
	 * @return
	 * @author	Chuck Ritola
	 * @throws DummyClientCreationFailureException 
	 * @throws AlreadyBoundException 
	 * @throws RemoteException 
	 * @date	Sep 17, 2011
	 */
	public static JACKServer getServer() throws DummyClientCreationFailureException, RemoteException
		{
		if(server==null)server=new JACKServer.JACKLocalServer();
		return server;
		}
	
	/**
	 * Helper method for checking return values of JNA calls.
	 * @param code
	 * @author	Chuck Ritola
	 * @date	Sep 17, 2011
	 */
	public static void errCheck(int code)
		{
		if(code==0)return;
		else if(code==17) throw new RuntimeException("Lib call returned 17: EEXIST. The item you attempted to create already exists.");
		else
			throw new RuntimeException("Lib call returned nonzero (error): "+code);
		}
	private static boolean autoExport=false;	
	/**
	 * Specify whether or not java-jack Remote objects should export themselves to the local RMI Registry at port 1099. 
	 * If no Registry is available, it will attempt to create one.
	 * @param stateOfAutoExport
	 * @author	Chuck Ritola
	 * @date	Sep 20, 2011
	 */
	public static void setAutoExport(boolean stateOfAutoExport)
		{autoExport=stateOfAutoExport;}
	
	/**
	 * Returns whether or not the java-jack API should automatically export its Remote objects to the localhost Registry at port 1099.
	 * @return	true if Remote objects in this API automatically export themselves, else false.
	 * @author	Chuck Ritola
	 * @date	Sep 20, 2011
	 */
	public static boolean willAutoExport(){return autoExport;}

	/**
	 * @return
	 * @author	Chuck Ritola
	 * @date	Sep 20, 2011
	 */
	public static synchronized int getRMIUniqueID()
		{
		return rmiID++;
		}
	}//end JACK
