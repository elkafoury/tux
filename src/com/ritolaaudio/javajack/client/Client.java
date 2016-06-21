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

import com.ritolaaudio.jack.JackLibrary;
import com.ritolaaudio.jack._jack_session_event;
import com.ritolaaudio.jack.jack_position_t;
import com.ritolaaudio.jack.JackLibrary.JackBufferSizeCallback;
import com.ritolaaudio.jack.JackLibrary.JackClientRegistrationCallback;
import com.ritolaaudio.jack.JackLibrary.JackFreewheelCallback;
import com.ritolaaudio.jack.JackLibrary.JackGraphOrderCallback;
import com.ritolaaudio.jack.JackLibrary.JackLatencyCallback;
import com.ritolaaudio.jack.JackLibrary.JackLatencyCallbackMode;
import com.ritolaaudio.jack.JackLibrary.JackPortConnectCallback;
import com.ritolaaudio.jack.JackLibrary.JackPortFlags;
import com.ritolaaudio.jack.JackLibrary.JackPortRegistrationCallback;
import com.ritolaaudio.jack.JackLibrary.JackPortRenameCallback;
import com.ritolaaudio.jack.JackLibrary.JackProcessCallback;
import com.ritolaaudio.jack.JackLibrary.JackSampleRateCallback;
import com.ritolaaudio.jack.JackLibrary.JackSessionCallback;
import com.ritolaaudio.jack.JackLibrary.JackShutdownCallback;
import com.ritolaaudio.jack.JackLibrary.JackSyncCallback;
import com.ritolaaudio.jack.JackLibrary.JackTimebaseCallback;
import com.ritolaaudio.jack.JackLibrary.JackXRunCallback;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import com.ritolaaudio.javajack.AutoExportingJACKRemoteObject;
import com.ritolaaudio.javajack.JACK;
import com.ritolaaudio.javajack.port.AudioInputPort;
import com.ritolaaudio.javajack.port.AudioOutputPort;
import com.ritolaaudio.javajack.port.MIDIInputPort;
import com.ritolaaudio.javajack.port.MIDIOutputPort;
import com.ritolaaudio.javajack.port.Port;
import com.ritolaaudio.javajack.port.PortConnectListener;
import com.ritolaaudio.javajack.port.PortRegistrationListener;
import com.ritolaaudio.javajack.port.PortRenameListener;
import com.ritolaaudio.javajack.port.Port.LocalPort;
import com.ritolaaudio.javajack.server.JACKServer;
import com.ritolaaudio.javajack.server.JACKServer.JACKLocalServer;
import com.ritolaaudio.javajack.transport.JACKPosition;
import com.ritolaaudio.javajack.transport.TransportState;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface Client extends Remote, Serializable
	{
	/**
	 * Returns the current RT priority of this Client.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 16, 2011
	 */
	public int queryCurrentRealtimePriority()throws RemoteException;
	/**
	 * Gets a Port by its short name, automatically appending the name of this client to create its long name.
	 * @param portName
	 * @return
	 * @throws RemoteException
	 * @throws NameNotFoundException
	 * @author	Chuck Ritola
	 * @date	Sep 27, 2011
	 */
	Port getPortByShortName(String portName) throws RemoteException,
			NameNotFoundException;
	/**
	 * Get a port by its long name formatted client:port
	 * @param portName
	 * @return
	 * @throws RemoteException
	 * @throws NameNotFoundException
	 * @author	Chuck Ritola
	 * @date	Sep 27, 2011
	 */
	Port getPortByLongName(String portName) throws RemoteException,
			NameNotFoundException;
	/**
	 * Convert the specified duration into frames at the current sample rate.
	 * @param timeInMicroseconds The duration to convert, as represented in microseconds. (1E-6)
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 16, 2011
	 */
	public long timeToFrames(long timeInMicroseconds)throws RemoteException;
	/**
	 * Convert the specified duration at the current sample rate into microseconds.
	 * Due to a glitch in wrapping the library, this method has been implemented in java rather than made into a native call.
	 * @param numFrames The duration to convert, as represented in frames.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 16, 2011
	 */
	public long framesToTime(int numFrames)throws RemoteException;
	/**
	 * Determines if this Client is active.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 13, 2011
	 */
	public boolean isActive()throws RemoteException;
	/**
	 * Determine if the host's installed JACK library supports the latency callback symbol. Some builds do not yet implement this feature.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 13, 2011
	 */
	public boolean isLatencyListeningSupported() throws RemoteException;
	/**
	 * Deactivates this Client if it is active, then unregisters this Client's Ports and closes this JACK Client.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public void close() throws RemoteException;
	/**
	 * Gets the name of this Client
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public String getName() throws RemoteException;
	/**
	 * Activates this Client.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public void activate() throws RemoteException;
	/**
	 * Deactivates this Client.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public void deactivate() throws RemoteException;
	/**
	 * Requests realtime priority from the JACK Server to this Client, if available.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public void requestRealtimePriority() throws RemoteException;
	/**
	 * Requests the maximum possible realtime priority from the JACK Server to this Client, if available.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public void requestMaxRealtimePriority() throws RemoteException;
	/**
	 * Divulges whether or not this Client has realtime priority.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public boolean isRealtime() throws RemoteException;
	
	/**
	 * Enables or disables freewheel (faster or slower than realtime) mode.
	 * @param enabled
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public void setFreewheel(boolean enabled) throws RemoteException;
	/**
	 * Sets the requested buffer size for this Client.
	 * @param numberOfSamples
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public void setBufferSize(int numberOfSamples) throws RemoteException;
	/**
	 * Gets the suggested sample rate of this Client, in Hertz (audio frames per second)
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public int getSampleRate() throws RemoteException;
	/**
	 * Returns the default buffer size of this Client.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public int getBufferSize() throws RemoteException;
	/**
	 * Gets the total CPU usage of the affiliated JACK server.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 5, 2011
	 */
	public float getCPULoad() throws RemoteException;
	/**
	 * Creates an Audio Input Port from this Client using the supplied name and buffer size.
	 * @param portName
	 * @param bufferSizeInSamples
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @throws AlreadyBoundException 
	 * @date	Sep 5, 2011
	 */
	public AudioInputPort createAudioInputPort(String portName, long bufferSizeInSamples) throws RemoteException;
	/**
	 * Creates an Audio Output Port from this Client using the supplied name and buffer size.
	 * @param portName
	 * @param bufferSizeInSamples
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @throws AlreadyBoundException 
	 * @date	Sep 5, 2011
	 */
	public AudioOutputPort createAudioOutputPort(String portName, long bufferSizeInSamples) throws RemoteException;
	/**
	 * Creates a MIDI Input Port of the specified name from this Client using the given buffer size.
	 * @param portName
	 * @param bufferSizeInSamples
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @throws NameAlreadyBoundException 
	 * @throws AlreadyBoundException 
	 * @date	Sep 5, 2011
	 */
	public MIDIInputPort createMIDIInputPort(String portName, long bufferSizeInSamples) throws RemoteException, NameAlreadyBoundException;
	/**
	 * Creates a MIDI Output Port of the specified name and buffer size from this Client.
	 * @param portName
	 * @param bufferSizeInSamples
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @throws AlreadyBoundException 
	 * @date	Sep 5, 2011
	 */
	public MIDIOutputPort createMIDIOutputPort(String portName, long bufferSizeInSamples) throws RemoteException;
	/**
	 * Returns all ports whose class matches or extends the given class using instanceof for comparison.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public Port [] getPortsOfType(Class<? extends Port> classOfDesiredPort) throws RemoteException;
	/**
	 * Get all ports belonging to this server.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public Port [] getAllPorts()throws RemoteException;
	/**
	 * Get ClientDesicriptions of all Clients on the affiliated Server which contain 
	 * the name of each Client, and arrays of PortDescriptions for that Client's
	 * affiliated Ports.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 19, 2011
	 */
	public String [] getAllClientNames()throws RemoteException;
	/**
	 * Get the names of all ports of all Clients belonging to this Port's server.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 19, 2011
	 */
	public String [] getAllPortNames()throws RemoteException;
	/**
	 * Get the port affiliated with this Client whose ID matches the passed int.
	 * @param portID
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public Port getPortByID(int portID)throws RemoteException;
	/**
	 * Returns the JACKServer which created this Client.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public JACKServer getAffiliatedServer() throws RemoteException;
	/**
	 * Returns an int quantity describing the number of frames since the last cycle started.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	
	public int framesSinceCycleStart()throws RemoteException;
	/**
	 * To be called only by the timebase master, releases the timebase.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void releaseTimebase() throws RemoteException;
	/**
	 * Sets the sync timeout in milliseconds.
	 * @param timeoutInMicroseconds
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void setSyncTimeout(long timeoutInMicroseconds) throws RemoteException;
	/**
	 * Set's the Client's transport location.
	 * @param numberOfFrames
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void transportLocate(int numberOfFrames) throws RemoteException;
	/**
	 * Queries the current transport position of this Client.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public JACKPosition queryTransport() throws RemoteException;
	/**
	 * Returns the frame number for this Client's current transport location.
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public int currentTransportFrame() throws RemoteException;
	/**
	 * 
	 * @param newPosition
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void transportReposition(JACKPosition newPosition) throws RemoteException;
	/**
	 * Starts/Plays the transport for this Client.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void transportStart() throws RemoteException;
	/**
	 * Stops/Pauses the transport for this Client.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void transportStop() throws RemoteException;
	/**
	 * Adds a Timebase Listener to this Client.
	 * @param l
	 * @param conditional
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addTimebaseListener(TimebaseListener l, int conditional) throws RemoteException;
	/**
	 * Removes a Timebase Listener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removeTimebaseListener(TimebaseListener l) throws RemoteException;
	/**
	 * Adds a Sync Listener to this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addSyncListener(SyncListener l) throws RemoteException;
	/**
	 * Removes a Sync Listener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removeSyncListener(SyncListener l) throws RemoteException;
	/**
	 * Adds a  Session Listener to this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addSessionListener(SessionListener l) throws RemoteException;
	/**
	 * Removes a Session Listener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removeSessionListener(SessionListener l) throws RemoteException;
	/**
	 * Adds a Shutdown Listener to this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addShutdownListener(ShutdownListener l) throws RemoteException;
	/**
	 * Adds a Port Rename Listener to this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public void addPortRenameListener(PortRenameListener l)throws RemoteException;
	/**
	 * Removes the specified Shutdown Listener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removeShutdownListener(ShutdownListener l) throws RemoteException;
	/**
	 * Sets the exclusive processing kernel for this Client. This is analogous to the JACK API's set_process_callback() function.
	 * Unlike other listeners, there can only be one ProcessingKernel per Client. As a result there is no removal method. 
	 * Set null and the API will simply skip on to the next Client in the processing loop.
	 * @param k
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void setProcessingKernel(ProcessingKernel k) throws RemoteException;
	/**
	 * Adds a Freewheel Listener to this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addFreewheelListener(FreewheelListener l) throws RemoteException;
	/**
	 * Removes the specified Freewheel Listener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removeFreewheelListener(FreewheelListener l) throws RemoteException;
	/**
	 * Adds a BufferSizeListener to this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addBufferSizeListener(BufferSizeListener l) throws RemoteException;
	/**
	 * Removes the specified BufferSizeListener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removeBufferSizeListener(BufferSizeListener l) throws RemoteException;
	/**
	 * Adds a SampleRateListener to this Client which is invoked when there is a change in the JACKServer's sampling rate.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addSampleRateListener(SampleRateListener l)  throws RemoteException;
	/**
	 * Removes the specified SampleRateListener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removeSampleRateListener(SampleRateListener l) throws RemoteException;
	/**
	 * Adds a Client Registration Listener to this Client to be invoked when a Client is registered on the affiliated JACKServer.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addClientRegistrationListener(ClientRegistrationListener l) throws RemoteException;
	/**
	 * Removes the specified Client Registration Listener.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removeClientRegistrationListener(ClientRegistrationListener l) throws RemoteException;
	/**
	 * Adds the specified Port Registration Listener to this Client to be invoked when a Port is created or destroyed in the affiliated JACKServer.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addPortRegistrationListener(PortRegistrationListener l) throws RemoteException;
	/**
	 * Removes the specified Port Registration Listener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removePortRegistrationListener(PortRegistrationListener l) throws RemoteException;
	/**
	 * Adds the specified Port Connect Listener to be invoked when a connection is made or destroyed in the affiliated JACKServer.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addPortConnectListener(PortConnectListener l) throws RemoteException;
	/**
	 * Removes the specified Port Connect Listener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removePortConnectListener(PortConnectListener l) throws RemoteException;
	/**
	 * Adds the specified Graph Order Listener to be invoked when the JACKServer's graph order is changed.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addGraphOrderListener(GraphOrderListener l) throws RemoteException;
	/**
	 * Removes the specified Graph Order Listener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removeGraphOrderListener(GraphOrderListener l) throws RemoteException;
	/**
	 * Adds the specified xrun (processing didn't complete in time) listener to be invoked in the event of an xrun event anywhere in the Client's affiliated JACKServer
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addXrunListener(XrunListener l) throws RemoteException;
	/**
	 * Removes the specified xrun Listener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removeXrunListener(XrunListener l) throws RemoteException;
	/**
	 * Adds the specified Playback Latency Listener to be invoked when it is necessary to recompute the latency of some or all Ports.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addPlaybackLatencyListener(PlaybackLatencyListener l) throws RemoteException;
	/**
	 * Removes the specified Playback Latency Listener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removePlaybackLatencyListener(PlaybackLatencyListener l) throws RemoteException;
	/**
	 * Adds the specified Capture Latency Listener to be invoked when it is necessary to recompute the latency of some or all Ports.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void addCaptureLatencyListener(CaptureLatencyListener l) throws RemoteException;
	/**
	 * Removes the specified Capture Latency Listener from this Client.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 6, 2011
	 */
	public void removeCaptureLatencyListener(CaptureLatencyListener l) throws RemoteException;
	/**
	 * Removes the specified Port Rename Listener.
	 * @param l
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	public void removePortRenameListener(PortRenameListener l)throws RemoteException;
	/**
	 * Become the timebase master.
	 * @param l
	 * @param failIfMasterAlreadyExists
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 10, 2011
	 */
	void becomeTimebaseMaster(TimebaseListener l,
			boolean failIfMasterAlreadyExists) throws RemoteException;
	/**
	 * Reply to a session event. Typically called from within a SessionListener.
	 * @param eventSentInResponse
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 13, 2011
	 */
	void sessionReply(SessionEvent eventSentInResponse) throws RemoteException;
	/**
	 * Frees memory used by a _session_event_t and its related command_line pointer if it is not NULL.
	 * @param eventToFree
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 13, 2011
	 */
	void freeSessionEvent(_jack_session_event eventToFree) throws RemoteException;
	/**
	 * Indicates whether or not this Client is a JavaJACK-created dummy client.
	 * @return true if this is a dummy client, else false.
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 24, 2011
	 */
	boolean isDummy() throws RemoteException;
	
	/**
	 * Determines whether or not the port under the given short name exists for this Client.
	 * @param portName
	 * @return
	 * @throws RemoteException
	 * @author	Chuck Ritola
	 * @date	Sep 27, 2011
	 */
	public boolean portByShortNameExists(String portName) throws RemoteException;
	
	public static class LocalClient extends AutoExportingJACKRemoteObject implements Client
		{
		/**
		 * Base class for a Client. Applying an instanceof to Client objects against the LocalClient class can determine if the object is 
		 * local (non-RMI).
		 */
		private static final long serialVersionUID = -1235652065414820140L;
		private JACKLocalServer affiliatedServer;
		private Pointer clientPointer;
		private ArrayList<BufferSizeListener>bufferSizeListenerList=new ArrayList<BufferSizeListener>();
		private ArrayList<CaptureLatencyListener>captureLatencyListenerList=new ArrayList<CaptureLatencyListener>();
		private ArrayList<ClientRegistrationListener>clientRegistrationListenerList=new ArrayList<ClientRegistrationListener>();
		private ArrayList<PlaybackLatencyListener>playbackLatencyListenerList=new ArrayList<PlaybackLatencyListener>();
		private ArrayList<GraphOrderListener>graphOrderListenerList=new ArrayList<GraphOrderListener>();
		private ArrayList<PortConnectListener>portConnectListenerList=new ArrayList<PortConnectListener>();
		private ArrayList<SessionListener>sessionListenerList=new ArrayList<SessionListener>();
		private ArrayList<SampleRateListener>sampleRateListenerList=new ArrayList<SampleRateListener>();
		private ArrayList<XrunListener>xrunListenerList=new ArrayList<XrunListener>();
		private ArrayList<PortRegistrationListener>portRegistrationListenerList=new ArrayList<PortRegistrationListener>();
		private ArrayList<PortRenameListener>portRenameListenerList=new ArrayList<PortRenameListener>();
		private ArrayList<FreewheelListener>freewheelListenerList=new ArrayList<FreewheelListener>();
		private ArrayList<ShutdownListener>shutdownListenerList=new ArrayList<ShutdownListener>();
		private ArrayList<TimebaseListener>timebaseListenerList=new ArrayList<TimebaseListener>();
		private ArrayList<SyncListener>syncListenerList=new ArrayList<SyncListener>();
		private ProcessingKernel processingKernel;
		
		private ConcurrentHashMap<String,Port> portNameMap = new ConcurrentHashMap<String,Port>();
		private ConcurrentHashMap<Pointer,Port>portPointerMap = new ConcurrentHashMap<Pointer,Port>();
		
		JackProcessCallback processCallback= new JackProcessCallback()
			{
			@Override
			public int apply(int nframes, Pointer arg)
				{
				if(processingKernel!=null)processingKernel.process(LocalClient.this, nframes, JackLibrary.INSTANCE.jack_last_frame_time(clientPointer));
				return 0;
				}
			};
		
		JackFreewheelCallback freewheelCallback = new JackFreewheelCallback()
			{
			@Override
			public void apply(int starting, Pointer arg)
				{
				//System.out.println("FreewheelCallback.");
				for(FreewheelListener l:freewheelListenerList)
					{l.freewheelEvent(starting!=0?true:false,LocalClient.this);}
				}
			};
			
		JackClientRegistrationCallback clientRegistrationCallback = new JackClientRegistrationCallback()
			{
			@Override
			public void apply(Pointer name, int int1, Pointer arg)
				{
				//System.out.println("RegistrationCallback.");
				String clientName=name.getString(0);
				for(ClientRegistrationListener l:clientRegistrationListenerList)
					{l.clientRegistrationChanged(clientName, int1!=0?true:false,LocalClient.this);}
				}
			};
		
			
		JackGraphOrderCallback graphOrderCallback = new JackGraphOrderCallback()
			{
			@Override
			public int apply(Pointer arg)
				{
				//System.out.println("GraphOrderCallback");
				for(GraphOrderListener l:graphOrderListenerList)
					{l.graphOrderChanged(LocalClient.this);}
				return 0;
				}
			};
		
		JackBufferSizeCallback bufferSizeCallback = new JackBufferSizeCallback()
			{
			@Override
			public int apply(int nframes, Pointer arg)
				{
				//System.out.println("BufferSizeCallback");
				for(BufferSizeListener l:bufferSizeListenerList)
					{l.bufferSizeChangeEvent(nframes,LocalClient.this);}
				return 0;
				}
		
			};
		
		JackLatencyCallback latencyCallback = new JackLatencyCallback()
			{
			@Override
			public void apply(int mode, Pointer arg)
				{
				//System.out.println("LatencyCallback");
				switch(mode)
					{
					case JackLatencyCallbackMode.JackCaptureLatency:
						{
						for(CaptureLatencyListener l:captureLatencyListenerList)
							{l.recalculateCaptureLatency(LocalClient.this);}
						return;
						}
					case JackLatencyCallbackMode.JackPlaybackLatency:
						{
						for(PlaybackLatencyListener l:playbackLatencyListenerList)
							{l.recalculatePlaybackLatency(LocalClient.this);}
						return;
						}
					}//end switch(mode)
				throw new RuntimeException("Invalid mode number passed to latency callback method: "+mode);
				}
			};
			
		JackPortConnectCallback portConnectCallback =  new JackPortConnectCallback()
			{
			@Override
			public void apply(int a, int b, int connect, Pointer arg)
				{
				//System.out.println("PortconnectCallback");
				for(PortConnectListener l:portConnectListenerList)
					{try
						{
						l.portConnectEvent(getPortByID(a), getPortByID(b), connect!=0?true:false, LocalClient.this);
						}
					catch (RemoteException e)
						{
						//consider maybe routing this to JACK's error stream?
						e.printStackTrace();
						}
					}//end for(listener)
				}//end apply()
			};
		
		JackSessionCallback sessionCallback = new JackSessionCallback()
			{
			@Override
			public void apply(_jack_session_event event, Pointer arg)
				{
				//System.out.println("SessionCallback");
				SessionEventType type = SessionEventType.denumerate(event.type);
				for(SessionListener l: sessionListenerList)
					{try
						{
						l.sessionMessage(type, event.session_dir.getString(0), event.client_uuid.getString(0), 
								event.command_line, new JACKSessionFlagset(event.flags),LocalClient.this);
						}
					catch (RemoteException e)
						{
						e.printStackTrace();
						}}
				}//end apply()
			};
			
		JackSampleRateCallback sampleRateCallback = new JackSampleRateCallback()
			{
			@Override
			public int apply(int nframes, Pointer arg)
				{
				//System.out.println("SampleRateCallback");
				for(SampleRateListener l : sampleRateListenerList)
					{l.sampleRateChanged(nframes,LocalClient.this);}
				return 0;
				}//end apply()
			};
			
		JackXRunCallback xrunCallback = new JackXRunCallback()
			{
			@Override
			public int apply(Pointer arg)
				{
				//System.out.println("xruncallback");
				for(XrunListener l:xrunListenerList)
					{l.xRunOccurred(LocalClient.this);}
				return 0;
				}//end apply()
			};
		
		JackPortRegistrationCallback portRegistrationCallback = new JackPortRegistrationCallback()
			{
			@Override
			public void apply(int port, int int1, Pointer arg)
				{
				//System.out.println("PortRegCallback");
				boolean isReg=int1!=0?true:false;
				Port p;
				try
					{
					p = getPortByID(port);
					for(PortRegistrationListener l:portRegistrationListenerList)
						{l.portRegistrationEvent(p, isReg,LocalClient.this);}
					}
				catch (RemoteException e)
					{
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				}//end apply(...)
			};
		
		JackShutdownCallback shutdownCallback = new JackShutdownCallback()
			{
			@Override
			public void apply(Pointer arg)
				{
				//System.out.println("ShutdownCallback");
				for(ShutdownListener l:shutdownListenerList)
					{l.shutdownEvent(LocalClient.this);}
				}
			};
		
		JackSyncCallback syncCallback = new JackSyncCallback()
			{
			@Override
			public int apply(int state, jack_position_t pos, Pointer arg)
				{
				//System.out.println("SyncCallback.");
				TransportState tState = TransportState.parse(state);
				JACKPosition position = new JACKPosition(pos, tState);
				for(SyncListener l:syncListenerList)
					{l.syncEvent(position,LocalClient.this);}
				return 0;
				}//end apply()
			};
			
		JackPortRenameCallback portRenameCallback = new JackPortRenameCallback()
			{
			@Override
			public int apply(int port, Pointer oldName,
					Pointer newName, Pointer arg) throws NameNotFoundException
				{
				//System.out.println("PortRenameCallback");
				Port p;
				try
					{
					String _oldName=oldName.getString(0);
					String _newName=newName.getString(0);
					p = getPortByLongName(_oldName);
					portNameMap.remove(_oldName);
					portNameMap.put(_newName, p);
					for(PortRenameListener l:portRenameListenerList)
						{l.portRenamed(p, _oldName, _newName,LocalClient.this);}
					}
				catch (RemoteException e)
					{
					e.printStackTrace();
					}
				return 0;
				}
			};
			
		private boolean latencySupported=true;
		private boolean isActive=false;
		
		/**
		 * Instantiate this LocalClient with an externally-gathered clientPointer and the supplied local JACK server.
		 * LocalClients are typically to be called from within the API itself. Outside implementers generally do not do this.
		 * @param _clientPointer
		 * @param server
		 */
		public LocalClient(Pointer _clientPointer,JACKLocalServer server) throws RemoteException
			{
			if(_clientPointer==null){throw new NullPointerException("I need a valid clientPointer to do my job.");}
			if(server==null){throw new NullPointerException("I need a valid JACKLocalServer to do my job.");}
			affiliatedServer=server;
			this.clientPointer=_clientPointer;
			
			JackLibrary.INSTANCE.jack_set_process_callback(clientPointer, processCallback,null);
			JackLibrary.INSTANCE.jack_set_freewheel_callback(clientPointer,freewheelCallback,null);
			JackLibrary.INSTANCE.jack_set_client_registration_callback(clientPointer, clientRegistrationCallback,null);
			JackLibrary.INSTANCE.jack_set_graph_order_callback(clientPointer, graphOrderCallback,null);
			JackLibrary.INSTANCE.jack_set_buffer_size_callback(clientPointer, bufferSizeCallback,null);
			try{JackLibrary.INSTANCE.jack_set_latency_callback(clientPointer,latencyCallback,null);}
			catch(UnsatisfiedLinkError e){latencySupported=false;}
			JackLibrary.INSTANCE.jack_set_port_connect_callback(clientPointer, portConnectCallback,null);
			JackLibrary.INSTANCE.jack_set_session_callback(clientPointer, sessionCallback,null);
			JackLibrary.INSTANCE.jack_set_sample_rate_callback(clientPointer, sampleRateCallback,null);
			JackLibrary.INSTANCE.jack_set_xrun_callback(clientPointer, xrunCallback,null);
			JackLibrary.INSTANCE.jack_set_port_registration_callback(clientPointer, portRegistrationCallback,null);
			JackLibrary.INSTANCE.jack_on_shutdown(clientPointer, shutdownCallback,null);
			JackLibrary.INSTANCE.jack_set_sync_callback(clientPointer, syncCallback,null);
			JackLibrary.INSTANCE.jack_set_port_rename_callback(clientPointer, portRenameCallback,null);
			//Ensure we have a nice cleanup if/when this program is terminated. Else JACK makes an unpleasant jitter noise.
			Runtime.getRuntime().addShutdownHook(new Thread()
				{
				public void run()
					{
					try {
						if(isActive)deactivate();
						close();
						}catch(RemoteException e){e.printStackTrace();}}//We're shutting down anyway.
				});
			}//end constructor
		
		@Override
		public void activate() throws RemoteException
			{
			JACK.errCheck(JackLibrary.INSTANCE.jack_activate(clientPointer));
			isActive=true;
			}

		@Override
		public void addBufferSizeListener(BufferSizeListener l)
				throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			bufferSizeListenerList.add(l);
			}

		@Override
		public void addCaptureLatencyListener(CaptureLatencyListener l)
				throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			captureLatencyListenerList.add(l);
			}

		@Override
		public void addClientRegistrationListener(ClientRegistrationListener l)
				throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			clientRegistrationListenerList.add(l);
			}

		@Override
		public void addFreewheelListener(FreewheelListener l)
				throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			freewheelListenerList.add(l);
			}

		@Override
		public void addGraphOrderListener(GraphOrderListener l)
				throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			graphOrderListenerList.add(l);
			}

		@Override
		public void addPlaybackLatencyListener(PlaybackLatencyListener l)
				throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			playbackLatencyListenerList.add(l);
			}

		@Override
		public void addPortConnectListener(PortConnectListener l)
				throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			portConnectListenerList.add(l);
			}

		@Override
		public void addPortRegistrationListener(PortRegistrationListener l)
				throws RemoteException
			{
			portRegistrationListenerList.add(l);
			}

		@Override
		public void addSampleRateListener(SampleRateListener l)
				throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			sampleRateListenerList.add(l);
			}

		@Override
		public void addSessionListener(SessionListener l)
				throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			sessionListenerList.add(l);
			}

		@Override
		public void addShutdownListener(ShutdownListener l)
				throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			shutdownListenerList.add(l);
			}

		@Override
		public void addSyncListener(SyncListener l) throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			syncListenerList.add(l);
			}

		@Override
		public void addTimebaseListener(TimebaseListener l, int conditional)
				throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			timebaseListenerList.add(l);
			}

		@Override
		public void addXrunListener(XrunListener l) throws RemoteException
			{
			if(l==null){throw new NullPointerException("I need a non-null listener argument to do my job.");}
			xrunListenerList.add(l);
			}

		@Override
		public void close() throws RemoteException
			{
			if(isActive())deactivate();
			//Deregister ports.
			for(Port port:portPointerMap.values())
				{port.unregister();}
			JACK.errCheck(JackLibrary.INSTANCE.jack_client_close(clientPointer));
			}

		@Override
		public AudioInputPort createAudioInputPort(String portName,
				long bufferSizeInSamples) throws RemoteException
			{
			Pointer pointer=JackLibrary.INSTANCE.jack_port_register
				(clientPointer, portName, JackLibrary.JACK_DEFAULT_AUDIO_TYPE, new NativeLong(JackPortFlags.JackPortIsInput),new NativeLong(bufferSizeInSamples));
			Port p=new Port.LocalAudioInputPort(this,pointer);
			portNameMap.put(portName, p);
			portPointerMap.put(pointer, p);
			return (AudioInputPort)p;
			}

		@Override
		public AudioOutputPort createAudioOutputPort(String portName,
				long bufferSizeInSamples) throws RemoteException
			{
			Pointer pointer=JackLibrary.INSTANCE.jack_port_register
				(clientPointer, portName, JackLibrary.JACK_DEFAULT_AUDIO_TYPE, new NativeLong(JackPortFlags.JackPortIsOutput),new NativeLong(bufferSizeInSamples));
			AudioOutputPort p=new Port.LocalAudioOutputPort(this,pointer);
			portNameMap.put(portName, p);
			portPointerMap.put(pointer,p);
			return (AudioOutputPort)p;
			}

		@Override
		public MIDIInputPort createMIDIInputPort(String portName,
				long bufferSizeInSamples) throws RemoteException, NameAlreadyBoundException
			{
			if(portByShortNameExists(portName)){throw new NameAlreadyBoundException("Port of name `"+portName+"` already exists.");}
			Pointer pointer=JackLibrary.INSTANCE.jack_port_register
				(clientPointer, portName, JackLibrary.JACK_DEFAULT_MIDI_TYPE, new NativeLong(JackPortFlags.JackPortIsInput),new NativeLong(bufferSizeInSamples));
			MIDIInputPort p=new Port.LocalMIDIInputPort(this,pointer);
			portNameMap.put(portName, p);
			portPointerMap.put(pointer, p);
			return (MIDIInputPort)p;
			}

		@Override
		public MIDIOutputPort createMIDIOutputPort(String portName,
				long bufferSizeInSamples) throws RemoteException
			{
			Pointer pointer=JackLibrary.INSTANCE.jack_port_register
				(clientPointer, portName, JackLibrary.JACK_DEFAULT_MIDI_TYPE, new NativeLong(JackPortFlags.JackPortIsOutput),new NativeLong(bufferSizeInSamples));
			MIDIOutputPort p = new Port.LocalMIDIOutputPort(this,pointer);
			portNameMap.put(portName, p);
			portPointerMap.put(pointer, p);
			return (MIDIOutputPort)p;
			}

		@Override
		public int currentTransportFrame() throws RemoteException
			{
			return JackLibrary.INSTANCE.jack_get_current_transport_frame(clientPointer);
			}

		@Override
		public void deactivate() throws RemoteException
			{
			JACK.errCheck(JackLibrary.INSTANCE.jack_activate(clientPointer));
			isActive=false;
			}

		@Override
		public int framesSinceCycleStart() throws RemoteException
			{
			return JackLibrary.INSTANCE.jack_frames_since_cycle_start(clientPointer);
			}

		@Override
		public JACKServer getAffiliatedServer() throws RemoteException
			{
			return affiliatedServer;
			}

		@Override
		public Port[] getAllPorts() throws RemoteException
			{
			String [] portStrings = getAllPortNames();
			Port [] result = new Port[portStrings.length];
			for(int i=0; i<portStrings.length; i++)
				{
				//System.out.println("Client.getAllPorts() jack_port_by_name() name="+portStrings[i]);
				Pointer _portPointer = JackLibrary.INSTANCE.jack_port_by_name(clientPointer, portStrings[i]);
				//Pointer _portPointer = JackLibrary.INSTANCE.jack_get_ports(clientPointer, portStrings[i], "", new NativeLong(0));
				if(_portPointer==null){throw new RuntimeException("Invalid port pointer obtained for name: "+portStrings[i]);}
				String fmtString = JackLibrary.INSTANCE.jack_port_type(_portPointer);
				boolean isOut=(JackLibrary.INSTANCE.jack_port_flags(_portPointer)&JackPortFlags.JackPortIsOutput)!=0;
				if(fmtString.contentEquals(JackLibrary.JACK_DEFAULT_AUDIO_TYPE))//Audio
					{
					if(isOut){result[i]=new Port.LocalAudioOutputPort(this,JackLibrary.INSTANCE.jack_port_by_name(clientPointer, portStrings[i]));}
					else{result[i]=new Port.LocalAudioInputPort(this,JackLibrary.INSTANCE.jack_port_by_name(clientPointer, portStrings[i]));}
					}
				else if(fmtString.contentEquals(JackLibrary.JACK_DEFAULT_MIDI_TYPE))//MIDI
					{
					if(isOut){new Port.LocalMIDIOutputPort(this,JackLibrary.INSTANCE.jack_port_by_name(clientPointer, portStrings[i]));}
					else{result[i]=new Port.LocalMIDIInputPort(this,JackLibrary.INSTANCE.jack_port_by_name(clientPointer, portStrings[i]));}
					}
				else {throw new RuntimeException("Unrecognized format string: "+fmtString+" aborting Port wrapping of "+JackLibrary.INSTANCE.jack_port_name(_portPointer));}
				}
			return result;
			}
		
		@Override
		public String [] getAllPortNames() throws RemoteException
			{
			PointerByReference pbr = JackLibrary.INSTANCE.jack_get_ports(clientPointer, "", "", new NativeLong(0));
			return pbr.getPointer().getStringArray(0);
			}

		@Override
		public int getBufferSize() throws RemoteException
			{
			return JackLibrary.INSTANCE.jack_get_buffer_size(clientPointer);
			}

		@Override
		public float getCPULoad() throws RemoteException
			{
			return JackLibrary.INSTANCE.jack_cpu_load(clientPointer);
			}

		@Override
		public String getName() throws RemoteException
			{
			return JackLibrary.INSTANCE.jack_get_client_name(clientPointer).getString(0);
			}

		@Override
		public Port getPortByID(int portID) throws RemoteException
			{
			return portPointerMap.get(JackLibrary.INSTANCE.jack_port_by_id(clientPointer, portID));
			}
		
		@Override
		public boolean portByShortNameExists(String portName) throws RemoteException
			{
			return JackLibrary.INSTANCE.jack_port_by_name(clientPointer, getName()+":"+portName)!=null?true:false;
			}
		
		@Override
		public Port getPortByLongName(String portName) throws RemoteException, NameNotFoundException
			{
			Pointer pointer = JackLibrary.INSTANCE.jack_port_by_name(clientPointer, portName);
			if(pointer==null)throw new NameNotFoundException("No port by that name: "+portName);
			if((JackLibrary.INSTANCE.jack_port_flags(pointer)&JackPortFlags.JackPortIsOutput)!=0)
				{
				if(JackLibrary.INSTANCE.jack_port_type(pointer).contentEquals(JackLibrary.JACK_DEFAULT_AUDIO_TYPE))
					{return new Port.LocalAudioOutputPort(this,pointer);}
				else if(JackLibrary.INSTANCE.jack_port_type(pointer).contentEquals(JackLibrary.JACK_DEFAULT_MIDI_TYPE))
					{return new Port.LocalMIDIOutputPort(this,pointer);}
				}//end output
			else//Input
				{
				if(JackLibrary.INSTANCE.jack_port_type(pointer).contentEquals(JackLibrary.JACK_DEFAULT_AUDIO_TYPE))
					{return new Port.LocalAudioInputPort(this,pointer);}
				else if(JackLibrary.INSTANCE.jack_port_type(pointer).contentEquals(JackLibrary.JACK_DEFAULT_MIDI_TYPE))
					{return new Port.LocalMIDIInputPort(this,pointer);}
				}//end input
			throw new RuntimeException("could not identify port: "+portName);
			}
		
		@Override
		public Port getPortByShortName(String portName) throws RemoteException, NameNotFoundException
			{
			Pointer pointer = JackLibrary.INSTANCE.jack_port_by_name(clientPointer, getName()+":"+portName);
			if(pointer==null)throw new NameNotFoundException("No port by that name: "+portName);
			if((JackLibrary.INSTANCE.jack_port_flags(pointer)&JackPortFlags.JackPortIsOutput)!=0)
				{
				if(JackLibrary.INSTANCE.jack_port_type(pointer).contentEquals(JackLibrary.JACK_DEFAULT_AUDIO_TYPE))
					{return new Port.LocalAudioOutputPort(this,pointer);}
				else if(JackLibrary.INSTANCE.jack_port_type(pointer).contentEquals(JackLibrary.JACK_DEFAULT_MIDI_TYPE))
					{return new Port.LocalMIDIOutputPort(this,pointer);}
				}//end output
			else//Input
				{
				if(JackLibrary.INSTANCE.jack_port_type(pointer).contentEquals(JackLibrary.JACK_DEFAULT_AUDIO_TYPE))
					{return new Port.LocalAudioInputPort(this,pointer);}
				else if(JackLibrary.INSTANCE.jack_port_type(pointer).contentEquals(JackLibrary.JACK_DEFAULT_MIDI_TYPE))
					{return new Port.LocalMIDIInputPort(this,pointer);}
				}//end input
			throw new RuntimeException("could not identify port: "+portName);
			}
		
		@Override
		public Port[] getPortsOfType(Class<? extends Port> classOfDesiredPort)
				throws RemoteException
			{
			Port [] rawPorts = getAllPorts();
			ArrayList<Port>rList=new ArrayList<Port>();
			for(Port p:rawPorts){if(classOfDesiredPort.isInstance(p)){rList.add(p);}}
			return rList.toArray(new Port[]{});
			}

		@Override
		public int getSampleRate() throws RemoteException
			{return JackLibrary.INSTANCE.jack_get_sample_rate(clientPointer);}

		@Override
		public boolean isRealtime() throws RemoteException
			{
			return JackLibrary.INSTANCE.jack_client_real_time_priority(clientPointer)!=-1;
			}

		@Override
		public JACKPosition queryTransport() throws RemoteException
			{
			jack_position_t position = new jack_position_t();
			int state = JackLibrary.INSTANCE.jack_transport_query(clientPointer, position);
			return new JACKPosition(position,TransportState.parse(state));
			}

		@Override
		public void releaseTimebase() throws RemoteException
			{
			JACK.errCheck(JackLibrary.INSTANCE.jack_release_timebase(clientPointer));
			}

		@Override
		public void removeBufferSizeListener(BufferSizeListener l)
				throws RemoteException
			{
			bufferSizeListenerList.remove(l);
			}

		@Override
		public void removeCaptureLatencyListener(CaptureLatencyListener l)
				throws RemoteException
			{
			captureLatencyListenerList.remove(l);
			}

		@Override
		public void removeClientRegistrationListener(
				ClientRegistrationListener l) throws RemoteException
			{
			clientRegistrationListenerList.remove(l);
			}

		@Override
		public void removeFreewheelListener(FreewheelListener l)
				throws RemoteException
			{
			freewheelListenerList.remove(l);
			}

		@Override
		public void removeGraphOrderListener(GraphOrderListener l)
				throws RemoteException
			{
			graphOrderListenerList.remove(l);
			}

		@Override
		public void removePlaybackLatencyListener(PlaybackLatencyListener l)
				throws RemoteException
			{
			playbackLatencyListenerList.remove(l);
			}

		@Override
		public void removePortConnectListener(PortConnectListener l)
				throws RemoteException
			{
			portConnectListenerList.remove(l);
			}

		@Override
		public void removePortRegistrationListener(PortRegistrationListener l)
				throws RemoteException
			{
			portRegistrationListenerList.remove(l);
			}

		@Override
		public void removeSampleRateListener(SampleRateListener l)
				throws RemoteException
			{
			sampleRateListenerList.remove(l);
			}

		@Override
		public void removeSessionListener(SessionListener l)
				throws RemoteException
			{
			sessionListenerList.remove(l);
			}

		@Override
		public void removeShutdownListener(ShutdownListener l)
				throws RemoteException
			{
			shutdownListenerList.remove(l);
			}

		@Override
		public void removeSyncListener(SyncListener l) throws RemoteException
			{
			syncListenerList.remove(l);
			}

		@Override
		public void removeTimebaseListener(TimebaseListener l)
				throws RemoteException
			{
			timebaseListenerList.remove(l);
			}

		@Override
		public void removeXrunListener(XrunListener l) throws RemoteException
			{
			xrunListenerList.remove(l);
			}

		@Override
		public void requestMaxRealtimePriority() throws RemoteException
			{
			JACK.errCheck(JackLibrary.INSTANCE.jack_client_max_real_time_priority(clientPointer));
			}

		@Override
		public void requestRealtimePriority() throws RemoteException
			{
			JACK.errCheck(JackLibrary.INSTANCE.jack_client_real_time_priority(clientPointer));
			}

		@Override
		public void setBufferSize(int numberOfSamples) throws RemoteException
			{
			JACK.errCheck(JackLibrary.INSTANCE.jack_set_buffer_size(clientPointer, numberOfSamples));
			}

		@Override
		public void setFreewheel(boolean enabled) throws RemoteException
			{
			final int _enabled=enabled?1:0;
			JACK.errCheck(JackLibrary.INSTANCE.jack_set_freewheel(clientPointer, _enabled));
			}

		@Override
		public void setProcessingKernel(ProcessingKernel k)
				throws RemoteException
			{
			processingKernel=k;
			}

		@Override
		public void setSyncTimeout(long timeoutInMicroseconds)
				throws RemoteException
			{
			JACK.errCheck(JackLibrary.INSTANCE.jack_set_sync_timeout(clientPointer, timeoutInMicroseconds));
			}

		@Override
		public void transportLocate(int numberOfFrames) throws RemoteException
			{
			JACK.errCheck(JackLibrary.INSTANCE.jack_transport_locate(clientPointer, numberOfFrames));
			}

		@Override
		public void transportReposition(JACKPosition newPosition)
				throws RemoteException
			{
			JACK.errCheck(JackLibrary.INSTANCE.jack_transport_reposition(clientPointer, newPosition.toNativeStruct()));
			}

		@Override
		public void transportStart() throws RemoteException
			{
			JackLibrary.INSTANCE.jack_transport_start(clientPointer);
			}

		@Override
		public void transportStop() throws RemoteException
			{
			JackLibrary.INSTANCE.jack_transport_stop(clientPointer);
			}

		@Override
		public void addPortRenameListener(PortRenameListener l)
				throws RemoteException
			{
			portRenameListenerList.add(l);
			}

		@Override
		public void removePortRenameListener(PortRenameListener l)
				throws RemoteException
			{
			portRenameListenerList.remove(l);
			}

		@Override
		public void becomeTimebaseMaster(TimebaseListener l, boolean failIfMasterAlreadyExists)
				throws RemoteException
			{
			JACK.errCheck(JackLibrary.INSTANCE.jack_set_timebase_callback(clientPointer, failIfMasterAlreadyExists?1:0, new JackTimebaseCallback()
				{
					@Override
					public void apply(int state, int nframes,
							jack_position_t pos, int newPos, Pointer arg)
						{
						TransportState ts = TransportState.parse(state);
						JACKPosition position = new JACKPosition(pos,ts);
						for(TimebaseListener l:timebaseListenerList)
							{l.timebaseEvent(ts, nframes, position);}
						}
				}, null));
			}//end becomeTimebaseMaster

		public Pointer getPointer()
			{return clientPointer;}

		public void unregisterPort(LocalPort port)
			{
			try
				{
				portNameMap.remove(port.getShortName());
				}
			catch (RemoteException e)
				{
				e.printStackTrace();
				}
			portPointerMap.remove(port.getPointer());
			JACK.errCheck(JackLibrary.INSTANCE.jack_port_unregister(getPointer(), port.getPointer()));
			}

		@Override
		public void freeSessionEvent(_jack_session_event eventToFree)
				throws RemoteException
			{
			JackLibrary.INSTANCE.jack_session_event_free(eventToFree);
			}

		@Override
		public void sessionReply(SessionEvent eventSentInResponse)
				throws RemoteException
			{
			JACK.errCheck(JackLibrary.INSTANCE.jack_session_reply(clientPointer, eventSentInResponse.toNativeStyle()));
			}

		@Override
		public boolean isLatencyListeningSupported() throws RemoteException
			{
			return latencySupported;
			}

		@Override
		public boolean isActive() throws RemoteException
			{
			return isActive;
			}

		@Override
		public long framesToTime(int numFrames) throws RemoteException
			{
			return JackLibrary.INSTANCE.jack_frames_to_time(clientPointer, numFrames);
			}

		@Override
		public long timeToFrames(long timeInMicroseconds)
				throws RemoteException
			{//JNAerator just isn't working out with this method. Implementing by hand...
			//return JackLibrary.INSTANCE.jack_time_to_frames(clientPointer,timeInMicroseconds);
			return new BigDecimal(timeInMicroseconds).divide(new BigDecimal(1000000)).multiply(new BigDecimal(getSampleRate())).longValue();
			}

		@Override
		public int queryCurrentRealtimePriority() throws RemoteException
			{
			return JackLibrary.INSTANCE.jack_client_real_time_priority(clientPointer);
			}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.client.Client#getAllClientNames()
		 */
		@Override
		public String[] getAllClientNames() throws RemoteException
			{
			String [] ports = getAllPortNames();
			HashMap<String,Object>portNameMap = new HashMap<String,Object>();
			for(String portName:ports)
				{
				int colonIndex=portName.indexOf(':');
				String clientName=portName.substring(0, colonIndex);
				//String shortPortName=portName.substring(colonIndex+1);
				//System.out.println("Client.CLIENTNAME: "+clientName+" PORTNAME: "+shortPortName);
				if(!portNameMap.containsKey(clientName))portNameMap.put(clientName, new Object());
				}
			return portNameMap.keySet().toArray(new String []{});
			}

		/* (non-Javadoc)
		 * @see com.ritolaaudio.javajack.client.Client#isDummy()
		 */
		@Override
		public boolean isDummy() throws RemoteException
			{return this==getAffiliatedServer().getDummyClient();}
		}//end ClientImpl
	}//end Client
