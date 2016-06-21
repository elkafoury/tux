/**
    JavaJACK - A JACK bridge for Java.
    Copyright (C) 2011  Chuck Ritola

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.ritolaaudio.javajack;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author chuck
 *
 */
public class AutoExportingJACKRemoteObject implements Remote
	{
	public AutoExportingJACKRemoteObject() throws RemoteException
		{
		if(JACK.willAutoExport())
			{
			try{LocateRegistry.createRegistry(1099);}
			catch(RemoteException e){}
			bindAuto(0,this);//Recursive call to attempt a bind with a unique name.
			}
		}//end constructor
	
	private void bindAuto(int iterationCount,Remote objectToExport) throws RemoteException
		{
		if(iterationCount>1000)
			{//What the heck is going on here?! Give up.
			throw new RuntimeException("Couldn't find an unused RMI name for this JACK object, even after 1000 tries. Something's wrong.");
			}
		try{LocateRegistry.getRegistry().bind("java-jack-autoExported-"+JACK.getRMIUniqueID(), UnicastRemoteObject.exportObject(objectToExport,0));}
		catch(AlreadyBoundException e){bindAuto(++iterationCount,objectToExport);}
		}
	}//end class
