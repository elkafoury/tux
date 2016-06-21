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
package com.ritolaaudio.javajack.helper;

import java.nio.FloatBuffer;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import com.ritolaaudio.GlobalPointer.LocallyAllocatedGlobalPointer;
import com.ritolaaudio.javajack.JACK;
import com.ritolaaudio.javajack.client.Client;
import com.ritolaaudio.javajack.client.ClientLoadFailureException;
import com.ritolaaudio.javajack.client.ClientZombifiedException;
import com.ritolaaudio.javajack.client.ProcessingKernel;
import com.ritolaaudio.javajack.client.Client.LocalClient;
import com.ritolaaudio.javajack.flags.JACKOption;
import com.ritolaaudio.javajack.flags.NullOption;
import com.ritolaaudio.javajack.port.MIDIInputPort;
import com.ritolaaudio.javajack.port.MIDIOutputPort;
import com.ritolaaudio.javajack.server.BackendException;
import com.ritolaaudio.javajack.server.DummyClientCreationFailureException;
import com.ritolaaudio.javajack.server.GeneralServerErrorException;
import com.ritolaaudio.javajack.server.InitException;
import com.ritolaaudio.javajack.server.JACKFailureException;
import com.ritolaaudio.javajack.server.JACKServer;
import com.ritolaaudio.javajack.server.NoSuchClientException;
import com.ritolaaudio.javajack.server.ServerLoadFailureException;
import com.ritolaaudio.javajack.server.SharedMemoryFailureException;
import com.ritolaaudio.javajack.server.VersionMismatchException;

public abstract class SimpleJACKClient extends LocalClient implements ProcessingKernel
	{
	/**
	 * 
	 */
	private static final long serialVersionUID = -270602465027164999L;
	private Thread keepAliveThread;
	private JACKServer jackServer;
	final ConcurrentHashMap<String,FloatBuffer> 		audioIn 	= new ConcurrentHashMap<String,FloatBuffer>();
	final ConcurrentHashMap<String,FloatBuffer> 		audioOut 	= new ConcurrentHashMap<String,FloatBuffer>();
	final ConcurrentHashMap<String,MIDIInputPort> 	midiIn 		= new ConcurrentHashMap<String,MIDIInputPort>();
	final ConcurrentHashMap<String,MIDIOutputPort> 	midiOut 	= new ConcurrentHashMap<String,MIDIOutputPort>();
	final String []audioInNames=getAudioInputPortNames();
	final String []audioOutNames=getAudioOutputPortNames();
	final String []midiInNames=getMIDIInputPortNames();
	final String []midiOutNames=getMIDIOutputPortNames();
	
	/**
	 * Getter for this Client's JACK server
	 * @return
	 * @author	Chuck Ritola
	 * @date	Sep 16, 2011
	 */
	public JACKServer getJackServer()
		{
		return jackServer;
		}
	/**
	 * Setter for this Client's JACK server
	 * @param jackServer
	 * @author	Chuck Ritola
	 * @date	Sep 16, 2011
	 */
	public void setJackServer(JACKServer jackServer)
		{
		this.jackServer = jackServer;
		}
	
	/**
	 * Automatically creates a local JACK client with the specified name with null options, 
	 * finding and connecting to a local JACK server.
	 * @param clientName
	 * @throws RemoteException
	 * @throws IllegalArgumentException
	 * @throws VersionMismatchException
	 * @throws NoSuchClientException
	 * @throws InitException
	 * @throws SharedMemoryFailureException
	 * @throws ClientZombifiedException
	 * @throws ServerLoadFailureException
	 * @throws GeneralServerErrorException
	 * @throws BackendException
	 * @throws ClientLoadFailureException
	 * @throws JACKFailureException
	 * @throws DummyClientCreationFailureException 
	 * @throws NameAlreadyBoundException 
	 */
	public SimpleJACKClient(String clientName) throws RemoteException, IllegalArgumentException, VersionMismatchException, NoSuchClientException, InitException, SharedMemoryFailureException, ClientZombifiedException, ServerLoadFailureException, GeneralServerErrorException, BackendException, ClientLoadFailureException, JACKFailureException, DummyClientCreationFailureException, NameAlreadyBoundException
		{
		this(clientName,new NullOption());
		}
	
	/**
	 * Automatically creates a local JACK client with the specified name with given options, 
	 * finding and connecting to a local JACK server.
	 * @param clientName
	 * @param options An array or vararg of instantiated JACKOption objects.
	 * @throws RemoteException
	 * @throws IllegalArgumentException
	 * @throws VersionMismatchException
	 * @throws NoSuchClientException
	 * @throws InitException
	 * @throws SharedMemoryFailureException
	 * @throws ClientZombifiedException
	 * @throws ServerLoadFailureException
	 * @throws GeneralServerErrorException
	 * @throws BackendException
	 * @throws ClientLoadFailureException
	 * @throws JACKFailureException
	 * @throws DummyClientCreationFailureException 
	 */
	public SimpleJACKClient(String clientName, JACKOption ... options) throws RemoteException, IllegalArgumentException, VersionMismatchException, NoSuchClientException, InitException, SharedMemoryFailureException, ClientZombifiedException, ServerLoadFailureException, GeneralServerErrorException, BackendException, ClientLoadFailureException, JACKFailureException, DummyClientCreationFailureException,NameAlreadyBoundException
		{
		super(  ((JACKServer.JACKLocalServer)JACK.getServer()).createClientPointer(clientName, options),
				(JACKServer.JACKLocalServer)JACK.getServer());
		setJackServer(JACK.getServer());
		//Register the ports
		final int bufferSize=this.getBufferSize();
		try{
		if(audioInNames!=null){for(String s:audioInNames)
			{this.createAudioInputPort(s, bufferSize);}}
		if(audioOutNames!=null){for(String s:audioOutNames)
			{this.createAudioOutputPort(s, bufferSize);}}
		if(midiInNames!=null){for(String s:midiInNames)
			{this.createMIDIInputPort(s, bufferSize);}}
		if(midiOutNames!=null){for(String s:midiOutNames)
			{this.createMIDIOutputPort(s, bufferSize);}}
		}
		catch(NameAlreadyBoundException e)
			{throw new RuntimeException("Cannot start program: You are using duplicate port names for this Client. Port names must be unique to each other, even across port types. DETAIL BELOW:\n"+e.getExplanation());}
		startKeepAliveThread();
		setProcessingKernel(this);
		activate();
		}//end constructor
	
	/**
	 * Built-in callback for the simple client. Prepares easy maps containing the input and output buffers for read/write operations.
	 */
	final public void process(Client client, int numberOfFrames, long jackTimeOfLastFrame)
		{
		if(client==null){try{setProcessingKernel(null);}catch(RemoteException e){}throw new NullPointerException("I need a non-null client in order to do my job.");}
		
		audioIn.clear();
		audioOut.clear();
		midiIn.clear();
		midiOut.clear();
		
		final String []audioInNames=getAudioInputPortNames();
		final String []audioOutNames=getAudioOutputPortNames();
		final String []midiInNames=getMIDIInputPortNames();
		final String []midiOutNames=getMIDIOutputPortNames();
		try
			{
			if(audioInNames!=null){for(String s:audioInNames)
				{
				audioIn.put(s, ((LocallyAllocatedGlobalPointer)(this.getPortByShortName(s).getBuffer())).getPointer().getByteBuffer(0, numberOfFrames*4).asFloatBuffer());
				}}
			if(audioOutNames!=null){for(String s:audioOutNames)
				{
				audioOut.put(s, ((LocallyAllocatedGlobalPointer)(this.getPortByShortName(s).getBuffer())).getPointer().getByteBuffer(0, numberOfFrames*4).asFloatBuffer());
				}}
			if(midiInNames!=null){for(String s:midiInNames)
				{
				midiIn.put(s, ((MIDIInputPort)this.getPortByShortName(s)));
				}}
			if(midiOutNames!=null){for(String s:midiOutNames)
				{
				midiOut.put(s, ((MIDIOutputPort)this.getPortByShortName(s)));
				}}
			process(audioIn,audioOut,midiIn,midiOut);
			}
		catch(RemoteException e){e.printStackTrace();}
		catch (NameNotFoundException e)
			{
			e.printStackTrace();
			}
		}
	
	public abstract void process(
			Map<String,FloatBuffer>audioIn, 
			Map<String,FloatBuffer>audioOut,
			Map<String,MIDIInputPort>midiIn,
			Map<String,MIDIOutputPort>midiOut);
	
	/**
	 * Implementer-specified short names for this Client's Audio Input Ports.
	 * @return	The desired short-names of this Client's Audio Input Ports.
	 * @author	Chuck Ritola
	 * @date	Sep 16, 2011
	 */
	public abstract String [] getAudioInputPortNames();
	/**
	 * Implementer-specified short names for this Client's Audio Output Ports.
	 * @return The desired short-names of this Client's Audio Output Ports.
	 * @author	Chuck Ritola
	 * @date	Sep 16, 2011
	 */
	public abstract String [] getAudioOutputPortNames();
	/**
	 * Implementer-specified short names for this Client's MIDI Input Ports.
	 * @return The desired short-names of this Client's MIDI Input Ports.
	 * @author	Chuck Ritola
	 * @date	Sep 16, 2011
	 */
	public abstract String [] getMIDIInputPortNames();
	/**
	 * Implementer-specified short names for this Client's MIDI Output Ports.
	 * @return The desired short-names of this Client's MIDI Output Ports.
	 * @author	Chuck Ritola
	 * @date	Sep 16, 2011
	 */
	public abstract String [] getMIDIOutputPortNames();
	
	/**
	 * Close this client, stoppin the keep-alive thread, deregistering this Client from the JACKServer, and its associated ports.
	 */
	public void close()
		{
		stopKeepAliveThread();
		try
			{
			super.close();
			}
		catch (RemoteException e)
			{//This should never happen since we are working with local objects
			e.printStackTrace();
			}
		}//end close()
	
	private void stopKeepAliveThread()
		{
		keepAliveThread.interrupt();
		}
	
	private void startKeepAliveThread()
		{
		keepAliveThread = new Thread(){public void run(){try
			{
			while(true){Thread.sleep(10000);}
			}
		catch (InterruptedException e)
			{
			}}};
		keepAliveThread.start();
		}
	}
