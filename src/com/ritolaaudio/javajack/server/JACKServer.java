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
package com.ritolaaudio.javajack.server;

import com.ritolaaudio.jack.JackLibrary;
import com.ritolaaudio.jack.JackLibrary.JackStatus;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.naming.NameNotFoundException;

import com.ritolaaudio.javajack.AutoExportingJACKRemoteObject;
import com.ritolaaudio.javajack.client.Client;
import com.ritolaaudio.javajack.client.ClientLoadFailureException;
import com.ritolaaudio.javajack.client.ClientZombifiedException;
import com.ritolaaudio.javajack.client.Client.LocalClient;
import com.ritolaaudio.javajack.flags.JACKOption;
import com.ritolaaudio.javajack.flags.NullOption;
import com.ritolaaudio.javajack.port.ConnectionAlreadyExistsException;
import com.ritolaaudio.javajack.port.GenderClashException;
import com.ritolaaudio.javajack.port.Port;
import com.ritolaaudio.javajack.port.PortTypeClashException;
import com.sun.jna.Pointer;

public interface JACKServer extends Remote, Serializable
	{
	/**
	 * Opens a new deactivated JACK client using the given client name. In the event of failure or problem, will throw one of the appropriate exceptions.
	 * In the event of multiple problems, the most specific exception will be thrown and all others ignored due to the method's escape. 
	 * Note that in order to function the Client must then be activated.
	 * @param clientName	Name of the Client to open (create).
	 * @throws RemoteException
	 * @throws JACKFailureException 
	 * @throws ClientLoadFailureException 
	 * @throws BackendException 
	 * @throws GeneralServerErrorException 
	 * @throws ServerLoadFailureException 
	 * @throws ClientZombifiedException 
	 * @throws SharedMemoryFailureException 
	 * @throws InitException 
	 * @throws NoSuchClientException 
	 * @throws IllegalArgumentException 
	 * @throws VersionMismatchException 
	 */
	public Client createClient(String clientName, JACKOption ... jackOptions) throws RemoteException, VersionMismatchException, IllegalArgumentException, NoSuchClientException, InitException, SharedMemoryFailureException, ClientZombifiedException, ServerLoadFailureException, GeneralServerErrorException, BackendException, ClientLoadFailureException, JACKFailureException;
	/**
	 * Get a human-readable representation of the JACK server's version.
	 * @return	String containing a human-readable representation of the JACK server's version.
	 * @throws RemoteException
	 */
	public String getVersionString() throws RemoteException;
	/**
	 * Get the maximum allowable length for a JACK Client's name.
	 * @return	int representing the maximum allowable length for a JACK Client's name.
	 * @throws RemoteException
	 */
	public int maxClientNameLength() throws RemoteException;
	/**
	 * Get this JACK Server's global time. This time may or may not be independent of other time sources.
	 * @return	JACK Server's time in microseconds. (1E-6 second units)
	 * @throws RemoteException
	 */
	public long jackTimeInMicroseconds() throws RemoteException;
	/**
	 * Adds an error listener. There may be multiple error listeners.
	 * @param l	A JACK Message Listener to handle the human-readable error messages.
	 * @throws RemoteException
	 */
	public void addErrorListener(JACKMessageListener l) throws RemoteException;
	/**
	 * Removes an error listener.
	 * @param l	Reference of the JACKMessageListener to remove.
	 * @throws RemoteException
	 */
	public void removeErrorListener(JACKMessageListener l) throws RemoteException;
	/**
	 * Adds an info listener. There may be multiple info listeners.
	 * @param l A JACK Message Listener to handle the human-readable info messages.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 4, 2011
	 */
	public void addInfoListener(JACKMessageListener l) throws RemoteException;
	/**
	 * Removes an info listener.
	 * @param l Reference of the JACKMessageListener to remove.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 4, 2011
	 */
	public void removeInfoListener(JACKMessageListener l) throws RemoteException;
	
	/**
	 * Get the absolute maximum allowable number of frames for this JACK Server. (compiled into the driver)
	 * @return 
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public long getMaxAllowableFrames() throws RemoteException;
	/**
	 * Returns the names of all Clients on this Server
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 20, 2011
	 */
	public String [] getAllClientNames() throws RemoteException;
	/**
	 * java-jack uses a dummy Client as a workaround in obtaining information about JACK's Client and Port topology
	 *  without the implementer creating a Client. java-jack may use ports or other features of this dummy
	 * Client. With this in mind, implementer-use of this Client should be avoided.
	 * @return
	 * @author	Chuck Ritola
	 * @date	Sep 20, 2011
	 */
	public Client getDummyClient() throws RemoteException;
	/**
	 * Gets all connections between all Ports on this Server.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 20, 2011
	 */
	public JACKConnection [] getAllConnections() throws RemoteException;
	/**
	 * Gets all Ports on this Server.
	 * @return An array of all Ports on this Server.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 20, 2011
	 */
	public Port [] getAllPorts() throws RemoteException;
	/**
	 * Get a Port reference based on its long name.
	 * @param portLongName	Long names areformatted:   clientName:portName
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @throws NameNotFoundException 
	 * @date	Sep 22, 2011
	 */
	public Port getPortByLongName(String portLongName) throws RemoteException, NameNotFoundException;
	/**
	 * Connect two ports by their long names.
	 * @param port1LongName
	 * @param port2LongName
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @throws PortTypeClashException 
	 * @throws GenderClashException 
	 * @throws ConnectionAlreadyExistsException 
	 * @throws NameNotFoundException 
	 * @date	Sep 22, 2011
	 */
	public void connectPorts(String port1LongName, String port2LongName) throws RemoteException, ConnectionAlreadyExistsException, GenderClashException, PortTypeClashException, NameNotFoundException;
	
	/**
	 * Determines whether or not the port by the specified long name exists under this entire JACKServer.
	 * @param portLongName
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 27, 2011
	 */
	public boolean portByLongNameExists(String portLongName) throws RemoteException;
	
	//////////////////////////////////////////
	////////// I M P L E M E N T A T I O N 
	//////////////////////////////////////////
	public static class JACKLocalServer extends AutoExportingJACKRemoteObject implements JACKServer
		{
		/**
		 * 
		 */
		private static final long serialVersionUID = 721909513102875016L;
		private transient ArrayList<JACKMessageListener>errorListenerList = new ArrayList<JACKMessageListener>();
		private transient LocalClient dummyClient;
		
		public ArrayList<JACKMessageListener> getErrorListenerList()
			{return errorListenerList;}
		
		public void setErrorListenerList(
				ArrayList<JACKMessageListener> errorListenerList)
			{this.errorListenerList = errorListenerList;}
		
		public ArrayList<JACKMessageListener> getInfoListenerList()
			{return infoListenerList;}
		
		public void setInfoListenerList(ArrayList<JACKMessageListener> infoListenerList)
			{this.infoListenerList = infoListenerList;}
	
		private ArrayList<JACKMessageListener>infoListenerList = new ArrayList<JACKMessageListener>();
		
		public JACKLocalServer() throws DummyClientCreationFailureException, RemoteException
			{
			try	{
				//Apply a random number to ensure we don't get name clashes if there are multi-instances using java-jack
				dummyClient=createClient("JavaJackDummyClient-"+(int)(65000.0*Math.random()),new NullOption());
				dummyClient.activate();
				}
			catch(Exception e){throw new DummyClientCreationFailureException(e);}
			}
		
		@Override
		public String getVersionString() throws RemoteException
			{return JackLibrary.INSTANCE.jack_get_version_string();}
		
		public Pointer createClientPointer(String clientName, JACKOption ... options)throws 
		RemoteException,VersionMismatchException,IllegalArgumentException,NoSuchClientException,
		InitException,SharedMemoryFailureException,ClientZombifiedException,ServerLoadFailureException,
		GeneralServerErrorException,BackendException,ClientLoadFailureException,JACKFailureException
			{
			final int optionFlags=JACKOption.enumerate(options);
			final Object [] optionArgs=JACKOption.arguments(options);
			IntBuffer _status = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
			Pointer result= JackLibrary.INSTANCE.jack_client_open(clientName, optionFlags, _status, optionArgs);
			int status=_status.get();
			final boolean jackFailure = (status&JackStatus.JackFailure)!=0;
			final boolean invalidOption = (status&JackStatus.JackInvalidOption)!=0;
			//final boolean serverStartedAsResult =
			//	(status&JackStatus.JackServerStarted)!=0;
			final boolean serverFailedToStart = (status&JackStatus.JackServerFailed)!=0;
			final boolean serverError = (status&JackStatus.JackServerError)!=0;
			final boolean noSuchClient = (status&JackStatus.JackNoSuchClient)!=0;
			final boolean loadFailure = (status&JackStatus.JackLoadFailure)!=0;
			final boolean initFailure = (status&JackStatus.JackInitFailure)!=0;
			final boolean sharedMemFail = (status&JackStatus.JackShmFailure)!=0;
			final boolean versionErr = (status&JackStatus.JackVersionError)!=0;
			final boolean backendErr = (status&JackStatus.JackBackendError)!=0;
			final boolean clientZomb = (status&JackStatus.JackClientZombie)!=0;
			
			//throw in order of most specific exceptions to least
			if(versionErr)throw new VersionMismatchException();
			if(invalidOption)throw new IllegalArgumentException("option flag mismatched to passed args. If you used a Flagset, his might be a bug in the wrapper.");
			if(noSuchClient)throw new NoSuchClientException();
			if(initFailure)throw new InitException();
			if(sharedMemFail)throw new SharedMemoryFailureException();
			if(clientZomb)throw new ClientZombifiedException();
			if(serverFailedToStart)throw new ServerLoadFailureException();
			if(serverError)throw new GeneralServerErrorException();
			if(backendErr)throw new BackendException();
			if(loadFailure)throw new ClientLoadFailureException("'"+clientName+"'");
			if(jackFailure)throw new JACKFailureException("The overall operation failed.");
			return result;
			}
		
		@Override
		public LocalClient createClient(String clientName, JACKOption ... options) throws 
				RemoteException,VersionMismatchException,IllegalArgumentException,NoSuchClientException,
				InitException,SharedMemoryFailureException,ClientZombifiedException,ServerLoadFailureException,
				GeneralServerErrorException,BackendException,ClientLoadFailureException,JACKFailureException
			{
			Pointer clientPointer = createClientPointer(clientName,options);
			return new Client.LocalClient(clientPointer,this);
			}//end createClient
	
		@Override
		public void addErrorListener(JACKMessageListener l) throws RemoteException
			{errorListenerList.add(l);}
	
		@Override
		public void addInfoListener(JACKMessageListener l) throws RemoteException
			{infoListenerList.add(l);}
	
		@Override
		public long jackTimeInMicroseconds() throws RemoteException
			{return JackLibrary.INSTANCE.jack_get_time();}
	
		@Override
		public int maxClientNameLength() throws RemoteException
			{return JackLibrary.INSTANCE.jack_client_name_size();}
	
		@Override
		public void removeErrorListener(JACKMessageListener l)
				throws RemoteException
			{errorListenerList.remove(l);}
	
		@Override
		public void removeInfoListener(JACKMessageListener l)
				throws RemoteException
			{infoListenerList.remove(l);}

		@Override
		public long getMaxAllowableFrames() throws RemoteException
			{return JackLibrary.JACK_MAX_FRAMES;}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.server.JACKServer#getAllClientNames()
		 */
		@Override
		public String[] getAllClientNames() throws RemoteException
			{return dummyClient.getAllClientNames();}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.server.JACKServer#getDummyClient()
		 */
		@Override
		public Client getDummyClient() throws RemoteException
			{if(dummyClient==null){throw new NullPointerException("dummy Client was never initialized.");};return dummyClient;}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.server.JACKServer#getAllConnections()
		 */
		@Override
		public JACKConnection[] getAllConnections() throws RemoteException
			{
			ArrayList<JACKConnection>result = new ArrayList<JACKConnection>();
			Port [] ports = getAllPorts();
			for(Port p:ports)
				{
				//System.err.println("raw port name: "+p.getLongName());
				Port[] destPorts = p.getAllConnections();
				String srcPortName = p.getShortName();
				String srcPortClientName = p.getClientName();
				for(Port dp:destPorts)
					{
					String destPortName=dp.getShortName();
					String destPortClientName=dp.getClientName();
					result.add(new JACKConnection(srcPortClientName,srcPortName,destPortClientName,destPortName));
					}//end for(dest Ports)
				}//end for(source Ports)
			return result.toArray(new JACKConnection[]{});
			}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.server.JACKServer#getAllPorts()
		 */
		@Override
		public Port[] getAllPorts() throws RemoteException
			{
			return dummyClient.getAllPorts();
			}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.server.JACKServer#getPortByLongName(java.lang.String)
		 */
		@Override
		public Port getPortByLongName(String portLongName)
				throws RemoteException, NameNotFoundException
			{
			return dummyClient.getPortByLongName(portLongName);
			}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.server.JACKServer#connectPorts(java.lang.String, java.lang.String)
		 */
		@Override
		public void connectPorts(String port1LongName, String port2LongName)
				throws RemoteException, ConnectionAlreadyExistsException, GenderClashException, PortTypeClashException, NameNotFoundException
			{
			Port port1 = dummyClient.getPortByLongName(port1LongName);
			Port port2 = dummyClient.getPortByLongName(port2LongName);
			port1.connectTo(port2);
			}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.server.JACKServer#portByLongNameExists(java.lang.String)
		 */
		@Override
		public boolean portByLongNameExists(String portLongName)
				throws RemoteException
			{
			return JackLibrary.INSTANCE.jack_port_by_name(dummyClient.getPointer(),portLongName)!=null?true:false;
			}
		}//end JACKServerImpl
	}//end JACKServer
