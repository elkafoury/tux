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

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import com.ritolaaudio.jack._jack_session_event;

public class SessionEvent implements Serializable
	{
	/**
	 * Contains details regarding a JACK session event. Such objects are typically passed to a SessionListener.
	 */
	private static final long serialVersionUID = 6784150855681322996L;
	private SessionEventType eventType;
	public SessionEventType getEventType()
		{
		return eventType;
		}
	public void setEventType(SessionEventType eventType)
		{
		this.eventType = eventType;
		}
	/**
	 * The session directory path, exclusive to this event's Client. The Client may read/write/delete any file within this path.
	 * @return
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public String getSessionDirectory()
		{
		return sessionDirectory;
		}
	/**
	 * The session directory path, exclusive to this event's Client. The Client may read/write/delete any file within this path.
	 * @param sessionDirectory
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public void setSessionDirectory(String sessionDirectory)
		{
		this.sessionDirectory = sessionDirectory;
		}
	public String getClientUUID()
		{
		return clientUUID;
		}
	public void setClientUUID(String clientUUID)
		{
		this.clientUUID = clientUUID;
		}
	/**
	 * A platform-dependent command (typically a shell command) for restoring the Client.
	 * @return
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public String getCommandLine()
		{
		return commandLine;
		}
	/**
	 * A platform-dependent command (typically a shell command) for restoring the Client.
	 * @param commandLine
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public void setCommandLine(String commandLine)
		{
		this.commandLine = commandLine;
		}
	/**
	 * See javadoc for JACKSessionFlagset
	 * @return
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public JACKSessionFlagset getSessionFlags()
		{
		return sessionFlags;
		}
	/**
	 * See javadoc for JACKSessionFlagset
	 * @param sessionFlags
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public void setSessionFlags(JACKSessionFlagset sessionFlags)
		{
		this.sessionFlags = sessionFlags;
		}
	private String sessionDirectory;
	private String clientUUID;
	private String commandLine;
	private JACKSessionFlagset sessionFlags;
	/**
	 * 
	 * @param eventType
	 * @param sessionDirectory
	 * @param clientUUID
	 * @param commandLine
	 * @param sessionFlags
	 */
	public SessionEvent(SessionEventType eventType, String sessionDirectory, String clientUUID, String commandLine, JACKSessionFlagset sessionFlags)
		{
		this.eventType=eventType;
		this.sessionDirectory=sessionDirectory;
		this.clientUUID=clientUUID;
		this.commandLine=commandLine;
		this.sessionFlags=sessionFlags;
		}
	/**
	 * Returns a raw C/JNAerator-style form of this SessionEvent.
	 * @return	Native style JackSessionEvent
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public _jack_session_event toNativeStyle()
		{
		_jack_session_event result = new _jack_session_event();
		result.client_uuid=pointerFromString(clientUUID);
		result.command_line=pointerFromString(commandLine);
		result.flags=sessionFlags.enumerate();
		result.session_dir=pointerFromString(sessionDirectory);
		result.type=SessionEventType.enumerate(eventType);
		return result;
		}
	
	protected static Pointer pointerFromString(String s)
		{
		Memory mem = new Memory(s.length());
		mem.setString(0, s,false);
		return mem;
		}
	}//end SessionEvent
