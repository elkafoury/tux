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
package com.ritolaaudio.javajack.port;

import com.ritolaaudio.jack.JackLibrary;
import com.ritolaaudio.jack._jack_latency_range;
import com.ritolaaudio.jack.jack_midi_event_t;
import com.ritolaaudio.jack.JackLibrary.JackLatencyCallbackMode;
import com.ritolaaudio.jack.JackLibrary.JackPortFlags;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.naming.NameNotFoundException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;

import com.ochafik.lang.jnaerator.runtime.NativeSize;
import com.ritolaaudio.javajack.AutoExportingJACKRemoteObject;
import com.ritolaaudio.javajack.JACK;
import com.ritolaaudio.javajack.client.Client;
import com.ritolaaudio.javajack.client.Client.LocalClient;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface Port extends Remote, Serializable
	{
	/**
	 * Returns this Port's JACK type string.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 16, 2011
	 */
	public String getTypeString()throws RemoteException;
	/**
	 * Gets the full-length name of this Port.
	 * @return	A String representing this Port in the format ClientName:PortShortName
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public String getLongName() throws RemoteException;
	/**
	 * Gets the short name of this Port.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public String getShortName() throws RemoteException;
	/**
	 * Sets the short name of this Port. The long name is simply this short name appended to the end of the client name and a colon.
	 * ex: longName: myClient:myPort  shortName: myPort
	 * @param name
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public void setName(String name) throws RemoteException;
	/**
	 * Gets the affiliated Client which created this Port.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public Client getAffiliatedClient() throws RemoteException;
	/**
	 * Sets an alias of this port. A port may have up to two aliases.
	 * @param alias
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public void setAlias(String alias) throws RemoteException;
	/**
	 * Unsets the supplied alias of this Port
	 * @param alias
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public void unsetAlias(String alias) throws RemoteException;
	/**
	 * Get all aliases of this Port
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public String [] getAliases() throws RemoteException;
	/**
	 * Returns whether or not this Port is monitoring.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public boolean isMonitoring() throws RemoteException;
	/**
	 * Requests (conditionally) that this Port support monitoring (or not)
	 * @param yesNo
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public void requestMonitor(boolean yesNo) throws RemoteException;
	/**
	 * Unregister this Port
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public void unregister() throws RemoteException;
	/**
	 * Disconnect this Port from the specified Port
	 * @param otherPort
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public void disconnectFrom(Port otherPort) throws RemoteException, ConnectionNotLocalException;
	/**
	 * Return whether this Port is connected to anything.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public boolean isConnected() throws RemoteException;
	/**
	 * Return whether this Port is connected to the supplied Port.
	 * @param otherPort
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public boolean isConnectedTo(Port otherPort) throws RemoteException;
	/**
	 * Get all Ports which are connected to this Port, regardless if they belong to this Port's Client. 
	 * This may not be called in response to callback methods.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 20, 2011
	 */
	public Port[] getAllConnections() throws RemoteException;
	/**
	 * Helper method for obtaining the name of the Client affiliated with this Port.
	 * @return String returning the first half of this Port's long name, which would be the name of this Port's client. No colon included.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 20, 2011
	 */
	public String getClientName() throws RemoteException;
	/**
	 * Get all Ports which are connected to this Port.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public Port [] getConnections() throws RemoteException;
	/**
	 * Get the buffer for this Port, in the form of a GlobalBuffer which may be local or remote.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public com.ritolaaudio.GlobalPointer getBuffer() throws RemoteException;
	/**
	 * Get the minimum possible playback latency in frames.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public int getMinPlaybackLatencyInFrames()throws RemoteException;
	/**
	 * Get the maximum possible playback latency in frames.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public int getMaxPlaybackLatencyInFrames()throws RemoteException;
	/**
	 * Get the minimum capture possible latency in frames.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public int getMinCaptureLatencyInFrames()throws RemoteException;
	/**
	 * Get the maximum possible capture latency in frames.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public int getMaxCaptureLatencyInFrames()throws RemoteException;
	/**
	 * Set the total minimum/maximum playback latency range, in frames.
	 * @param min
	 * @param max
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public void setPlaybackLatencyRangeInFrames(int min, int max)throws RemoteException;
	/**
	 * Set the total minimum/maximum capture latency range, in frames.
	 * @param min
	 * @param max
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public void setCaptureLatencyRangeInFrames(int min, int max)throws RemoteException;
	
	/**
	 * Gets all connections to this port in the form of a string in client:port format 
	 * (mentioning only the OTHER end of the connection)
	 * @return	Array of Strings, each representing an opposing end of a connection to this Port.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 19, 2011
	 */
	public String [] getConnectionsAsStrings()throws RemoteException;
	/**
	 * Get the type of this port. (ex: AudioInput, AudioOutput, MIDIInput,MIDIOutput, Other)
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 22, 2011
	 */
	public PortFormat getPortFormat()throws RemoteException;
	/**
	 * Connect this Port to another Port on the same server. Order is not important as it will be corrected by this method.
	 * @param otherPort
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @throws ConnectionAlreadyExistsException 
	 * @throws GenderClashException 
	 * @throws PortTypeClashException 
	 * @date	Sep 22, 2011
	 */
	public void connectTo(Port otherPort)throws RemoteException, ConnectionAlreadyExistsException, GenderClashException, PortTypeClashException;
	/**
	 * Determines if this Port is outgoing (output).
	 * @return	true if this port is outgoing, false if it is incoming.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 22, 2011
	 */
	public boolean isOutgoing()throws RemoteException;
	/**
	 * Returns whether or not this Port is a physical I/O port, typically a sound card.
	 * @return true if physical, false if not.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 23, 2011
	 */
	public boolean isPhysical() throws RemoteException;
	/**
	 * Returns whether or not this Port does not pass audio on beyond this point. A terminal connection such as an I/O input/output, a synth.
	 * @return true if terminal. false if not.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 23, 2011
	 */
	public boolean isTerminal() throws RemoteException;
	/**
	 * Returns whether or not this port is capable of monitoring.
	 * @return true if capable of monitoring.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 23, 2011
	 */
	public boolean canMonitor() throws RemoteException;
	public static abstract class LocalPort extends AutoExportingJACKRemoteObject implements Port
		{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8506677993222881273L;
		LocalClient client;
		Pointer portPointer,bufferPointer;
		public LocalPort(Pointer portPointer,LocalClient client) throws RemoteException
			{
			if(client==null)throw new NullPointerException("The constructor was passed a null pointer for Client. I need a non-null pointer to do my job.");
			if(portPointer==null)throw new NullPointerException("The constructor was passed a null pointer for Port. I need a non-null pointer to do my job.");
			this.client=client;
			this.portPointer=portPointer;
			}//end constructor()
		@Override
		public boolean isOutgoing()throws RemoteException
			{
			final int flags = JackLibrary.INSTANCE.jack_port_flags(portPointer);
			return (flags&JackPortFlags.JackPortIsInput)!=0;
			}
		@Override
		public String getTypeString()throws RemoteException
			{return JackLibrary.INSTANCE.jack_port_type(portPointer);}
		@Override
		public boolean isMonitoring() throws RemoteException
			{return JackLibrary.INSTANCE.jack_port_monitoring_input(portPointer)!=0;}
		@Override
		public void disconnectFrom(Port otherPort) throws RemoteException, ConnectionNotLocalException
			{
			if(otherPort instanceof LocalPort)
				{//Local
				JACK.errCheck(JackLibrary.INSTANCE.jack_port_disconnect(client.getPointer(), portPointer));
				}
			else
				{throw new ConnectionNotLocalException();}
			}
		@Override
		public Client getAffiliatedClient() throws RemoteException
			{return client;}
		@Override
		public String[] getAliases() throws RemoteException
			{
			ByteBuffer [] buffers = new ByteBuffer[2];//"ports can have up to 2 aliases"
			JACK.errCheck(JackLibrary.INSTANCE.jack_port_get_aliases(portPointer, buffers));
			String [] results = new String[buffers.length];
			for(int i=0; i<buffers.length;i++)
				{results[i]=buffers[i].asCharBuffer().toString();}
			return results;
			}
		@Override
		public com.ritolaaudio.GlobalPointer getBuffer() throws RemoteException
			{
			com.ritolaaudio.GlobalPointer pointer = 
				new com.ritolaaudio.GlobalPointer.LocallyAllocatedGlobalPointer(JackLibrary.INSTANCE.jack_port_get_buffer(portPointer,client.getBufferSize()));
			if(JACK.willAutoExport())
				{
				try{LocateRegistry.createRegistry(1099);}
				catch(RemoteException e){}
				bindAuto(0,pointer);//Recursive call to attempt a bind with a unique name.
				}
			return pointer;
			}
		private void bindAuto(int iterationCount,Remote objectToExport) throws RemoteException
			{
			if(iterationCount>1000)
				{//What the heck is going on here?! Give up.
				throw new RuntimeException("Couldn't find an unused RMI name for this JACK object, even after 1000 tries. Something's wrong.");
				}
			try{LocateRegistry.getRegistry().bind("java-jack-autoExported-"+JACK.getRMIUniqueID(), UnicastRemoteObject.exportObject(objectToExport,0));}
			catch(AlreadyBoundException e){bindAuto(++iterationCount,objectToExport);}
			}
		@Override
		public Port[] getConnections() throws RemoteException
			{
			PointerByReference pbr = JackLibrary.INSTANCE.jack_port_get_connections(portPointer);
			String []pNames= pbr.getPointer().getStringArray(0);
			Port [] results = new Port[pNames.length];
			for(int i=0; i<results.length; i++)
				{
				try{results[i]=client.getPortByLongName(pNames[i]);}
				catch(NameNotFoundException e)
					{System.err.println("JACK API failed to find port named "+pNames[i]+" yet JACK API itself reported it. Bizarre.");e.printStackTrace();}
				}
			return results;
			}
		@Override
		public Port[] getAllConnections() throws RemoteException
			{
			PointerByReference pbr = JackLibrary.INSTANCE.jack_port_get_all_connections(((LocalClient)getAffiliatedClient()).getPointer(),portPointer);
			if(pbr==null){return new Port[]{};}
			String 	[]pNames= pbr.getPointer().getStringArray(0);
			Port 	[]results = new Port[pNames.length];
			for(int i=0; i<results.length; i++)
				{
				try{results[i]=client.getPortByLongName(pNames[i]);}
				catch(NameNotFoundException e)
					{System.err.println("JACK API failed to find port named "+pNames[i]+" yet JACK API itself reported it. Bizarre.");e.printStackTrace();}

				}
			return results;
			}
		@Override
		public String getClientName() throws RemoteException
			{
			String pName=getLongName();
			int cIndex=pName.indexOf(":");
			String cName=pName.substring(0, cIndex);
			return cName;
			}
		@Override
		public String [] getConnectionsAsStrings()
			{
			PointerByReference pbr = JackLibrary.INSTANCE.jack_port_get_connections(portPointer);
			return pbr.getPointer().getStringArray(0);
			}
		@Override
		public String getLongName() throws RemoteException
			{return JackLibrary.INSTANCE.jack_port_name(portPointer);}
		@Override
		public String getShortName() throws RemoteException
			{return JackLibrary.INSTANCE.jack_port_short_name(portPointer);}
		@Override
		public boolean isConnected() throws RemoteException
			{return JackLibrary.INSTANCE.jack_port_connected(portPointer)!=0;}
		@Override
		public boolean isConnectedTo(Port otherPort) throws RemoteException
			{return JackLibrary.INSTANCE.jack_port_connected_to(portPointer, otherPort.getLongName())!=0;}
		@Override
		public void requestMonitor(boolean yesNo) throws RemoteException
			{JackLibrary.INSTANCE.jack_port_request_monitor(portPointer, yesNo?1:0);}
		@Override
		public void setAlias(String alias) throws RemoteException
			{JackLibrary.INSTANCE.jack_port_set_alias(portPointer, alias);}
		@Override
		public void setPlaybackLatencyRangeInFrames(int min, int max)
				throws RemoteException
			{
			_jack_latency_range range=new _jack_latency_range();
			range.min=min;range.max=max;
			JackLibrary.INSTANCE.jack_port_set_latency_range(portPointer, JackLatencyCallbackMode.JackPlaybackLatency, range);
			}
		@Override
		public void setCaptureLatencyRangeInFrames(int min, int max)
				throws RemoteException
			{
			_jack_latency_range range=new _jack_latency_range();
			range.min=min;range.max=max;
			JackLibrary.INSTANCE.jack_port_set_latency_range(portPointer, JackLatencyCallbackMode.JackCaptureLatency, range);
			}
		@Override
		public void setName(String name) throws RemoteException
			{JackLibrary.INSTANCE.jack_port_set_name(portPointer, name);}
		@Override
		public void unregister() throws RemoteException
			{client.unregisterPort(this);}
		@Override
		public void unsetAlias(String alias) throws RemoteException
			{JACK.errCheck(JackLibrary.INSTANCE.jack_port_unset_alias(portPointer, alias));}
		@Override
		public int getMaxCaptureLatencyInFrames() throws RemoteException
			{
			_jack_latency_range range=new _jack_latency_range();
			JackLibrary.INSTANCE.jack_port_get_latency_range(portPointer, JackLibrary.JackLatencyCallbackMode.JackCaptureLatency, range);
			return range.max;
			}
		@Override
		public int getMaxPlaybackLatencyInFrames() throws RemoteException
			{
			_jack_latency_range range=new _jack_latency_range();
			JackLibrary.INSTANCE.jack_port_get_latency_range(portPointer, JackLibrary.JackLatencyCallbackMode.JackPlaybackLatency, range);
			return range.max;
			}
		@Override
		public int getMinCaptureLatencyInFrames() throws RemoteException
			{
			_jack_latency_range range=new _jack_latency_range();
			JackLibrary.INSTANCE.jack_port_get_latency_range(portPointer, JackLibrary.JackLatencyCallbackMode.JackCaptureLatency, range);
			return range.min;
			}
		@Override
		public int getMinPlaybackLatencyInFrames() throws RemoteException
			{
			_jack_latency_range range=new _jack_latency_range();
			JackLibrary.INSTANCE.jack_port_get_latency_range(portPointer, JackLibrary.JackLatencyCallbackMode.JackPlaybackLatency, range);
			return range.min;
			}
		
		public Pointer getPointer(){return portPointer;}
		
		@Override
		public void connectTo(Port otherPort) throws RemoteException, ConnectionAlreadyExistsException, GenderClashException, PortTypeClashException
			{
			if(this.getPortFormat()!=otherPort.getPortFormat())throw new PortTypeClashException("this="+getPortFormat()+", otherport="+otherPort.getPortFormat());
			//Look for gender clash
			if(isOutgoing()&&otherPort.isOutgoing())//We ask.
				{throw new GenderClashException();}//And we tell.
			int err;
			//this is source
			if(isOutgoing()){err=JackLibrary.INSTANCE.jack_connect(client.getPointer(), getLongName(), otherPort.getLongName());}
			//this is dest
			else{err=JackLibrary.INSTANCE.jack_connect(client.getPointer(), otherPort.getLongName(),getLongName());}
			if(err==17)throw new ConnectionAlreadyExistsException();
			else JACK.errCheck(err);
			}
		
		@Override
		public boolean isPhysical()
			{
			return (JackLibrary.INSTANCE.jack_port_flags(portPointer)&JackPortFlags.JackPortIsPhysical)!=0;
			}
		@Override
		public boolean isTerminal()
			{
			return (JackLibrary.INSTANCE.jack_port_flags(portPointer)&JackPortFlags.JackPortIsTerminal)!=0;
			}
		@Override
		public boolean canMonitor()
			{
			return (JackLibrary.INSTANCE.jack_port_flags(portPointer)&JackPortFlags.JackPortCanMonitor)!=0;
			}
		}//end LocalPort
	
	public static abstract class LocalAudioPort extends LocalPort
		{
		/**
		 * 
		 */
		private static final long serialVersionUID = 7242060870785201972L;

		/**
		 * @param portPointer
		 * @param client
		 * @throws RemoteException
		 */
		public LocalAudioPort(Pointer portPointer, LocalClient client)
				throws RemoteException
			{
			super(portPointer, client);
			}

		@Override
		public PortFormat getPortFormat()
			{
			return PortFormat.Audio;
			}
		}//end LocalAudioPort
	
	public static class LocalAudioInputPort extends LocalAudioPort implements AudioInputPort
		{
		/**
		 * 
		 */
		private static final long serialVersionUID = -6330664686099809023L;

		public LocalAudioInputPort(LocalClient client, Pointer portPointer) throws RemoteException
			{
			super(portPointer, client);
			}

		@Override
		public void connectTo(AudioOutputPort otherPort) throws RemoteException
			{
			if(otherPort instanceof LocalAudioOutputPort)
				{
				JACK.errCheck(JackLibrary.INSTANCE.jack_connect(client.getPointer(), getLongName(), otherPort.getLongName()));
				}
			}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.port.Port#isOutgoing()
		 */
		@Override
		public boolean isOutgoing() throws RemoteException
			{
			return false;
			}
		}//end LocalAudioInputPort
	
	public static class LocalAudioOutputPort extends LocalAudioPort implements AudioOutputPort
		{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8132104804910054815L;

		public LocalAudioOutputPort(LocalClient client, Pointer portPointer) throws RemoteException
			{super(portPointer, client);}

		@Override
		public void connectTo(AudioInputPort otherPort) throws RemoteException
			{
			if(otherPort instanceof LocalAudioInputPort)
				{
				JACK.errCheck(JackLibrary.INSTANCE.jack_connect(client.getPointer(), getLongName(), otherPort.getLongName()));
				}
			}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.port.Port#isOutgoing()
		 */
		@Override
		public boolean isOutgoing() throws RemoteException
			{
			return true;
			}
		}//end AudioOutputPort
	
	public static abstract class LocalMIDIPort extends LocalPort implements MIDIPort
		{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2545285790140463281L;

		public LocalMIDIPort(Pointer portPointer, LocalClient client) throws RemoteException
			{
			super(portPointer, client);
			}
		
		@Override
		public PortFormat getPortFormat()
			{
			return PortFormat.MIDI;
			}

		@Override
		public void clearBuffer() throws RemoteException
			{JackLibrary.INSTANCE.jack_midi_clear_buffer(((com.ritolaaudio.GlobalPointer.LocallyAllocatedGlobalPointer)getBuffer()).getPointer());}
		
		@Override
		public int getLostEventCount() throws RemoteException
			{return JackLibrary.INSTANCE.jack_midi_get_lost_event_count(((com.ritolaaudio.GlobalPointer.LocallyAllocatedGlobalPointer)getBuffer()).getPointer());}

		@Override
		public int getMIDIEventCount() throws RemoteException
			{return JackLibrary.INSTANCE.jack_midi_get_event_count(((com.ritolaaudio.GlobalPointer.LocallyAllocatedGlobalPointer)getBuffer()).getPointer());}
		
		@Override
		public int maxEventSizeInBytes() throws RemoteException
			{return JackLibrary.INSTANCE.jack_midi_max_event_size(((com.ritolaaudio.GlobalPointer.LocallyAllocatedGlobalPointer)getBuffer()).getPointer()).intValue();}
		}//end class MIDIPortImpl
	
	public static class LocalMIDIInputPort extends LocalMIDIPort implements MIDIInputPort
		{

		/**
		 * 
		 */
		private static final long serialVersionUID = 85972403424268143L;

		public LocalMIDIInputPort(LocalClient client, Pointer portPointer) throws RemoteException
			{super(portPointer, client);}

		@Override
		public MidiEvent getMIDIEvent(int eventIndex) throws RemoteException
			{
			jack_midi_event_t event = new jack_midi_event_t();
			//TODO: What is the buffer size of a midi port really supposed to be?
			JACK.errCheck(JackLibrary.INSTANCE.jack_midi_event_get(event, JackLibrary.INSTANCE.jack_port_get_buffer(portPointer,client.getBufferSize()), eventIndex));
			CustomMIDIMessage msg = new CustomMIDIMessage(event);
			//TODO: Check the tick/sample relations between JACK's sample-based timing and Java API's tick-based timing.
			return new MidiEvent(msg, event.time);
			}
		
		class CustomMIDIMessage extends MidiMessage
			{
			jack_midi_event_t evt;
			public CustomMIDIMessage(jack_midi_event_t event)
				{
				super(event.buffer.getByteArray(0, event.size.intValue()));
				evt=event;
				}

			@Override
			public Object clone()
				{return new CustomMIDIMessage(evt);}
			}//end CustomMIDIEvent
		
		@Override
		public MidiEvent[] getMIDIEvents() throws RemoteException
			{
			MidiEvent [] result = new MidiEvent[getMIDIEventCount()];
			for(int i=0; i<result.length; i++)
				{result[i]=getMIDIEvent(i);}
			return result;
			}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.port.Port#isOutgoing()
		 */
		@Override
		public boolean isOutgoing() throws RemoteException
			{
			return false;
			}
		}//end MIDIInputPort
	
	public static class LocalMIDIOutputPort extends LocalMIDIPort implements MIDIOutputPort
		{
		/**
		 * 
		 */
		private static final long serialVersionUID = 5280004669660856591L;

		public LocalMIDIOutputPort(LocalClient client, Pointer portPointer) throws RemoteException
			{super(portPointer, client);}

		@Override
		public void writeMIDIMessage(MidiEvent eventToWrite)
				throws RemoteException
			{//TODO: Check the tick/sample relations between JACK's sample-based timing and Java API's tick-based timing.
			byte [] messageData = eventToWrite.getMessage().getMessage();
			ByteBuffer midiData = ByteBuffer.allocateDirect(messageData.length);
			midiData.put(messageData);
			JACK.errCheck(JackLibrary.INSTANCE.jack_midi_event_write(portPointer, (int)eventToWrite.getTick(), midiData, new NativeSize(midiData.capacity())));
			}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.port.Port#isOutgoing()
		 */
		@Override
		public boolean isOutgoing() throws RemoteException
			{
			return true;
			}
		}//end MIDIOutputPort
	}//end Port
