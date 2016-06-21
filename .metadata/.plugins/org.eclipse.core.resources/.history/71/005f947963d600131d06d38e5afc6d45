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

import com.ritolaaudio.jack.JackLibrary.JackSessionFlags;


public class JACKSessionFlagset implements Serializable
	{
	/**
	 * Flags for reporting the auxilliary details of a SessionEvent.
	 * @author Chuck Ritola
	 */
	private static final long serialVersionUID = 3039230320581288682L;
	private boolean inNeedOfTerminal,saveError;
	/**
	 * Query whether or not the Client in question needs to be run in a terminal.
	 * @return
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public boolean isInNeedOfTerminal()
		{
		return inNeedOfTerminal;
		}
	public void setInNeedOfTerminal(boolean inNeedOfTerminal)
		{
		this.inNeedOfTerminal = inNeedOfTerminal;
		}
	/**
	 * Returns true if an error had occurred in the process of saving.
	 * @return
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public boolean isSaveError()
		{
		return saveError;
		}
	public void setSaveError(boolean saveError)
		{
		this.saveError = saveError;
		}
	/**
	 * Construct a flagset based on standard java boolean values.
	 * @param inNeedOfTerminal
	 * @param saveError
	 */
	public JACKSessionFlagset(boolean inNeedOfTerminal, boolean saveError)
		{
		this.inNeedOfTerminal=inNeedOfTerminal;
		this.saveError=saveError;
		}
	
	/**
	 * Construct a flagset based on a C-style enumeration of a JackSessionFlags object.
	 * @param flags
	 */
	public JACKSessionFlagset(int flags)
		{
		inNeedOfTerminal=(flags&JackSessionFlags.JackSessionNeedTerminal)!=0;
		saveError=(flags&JackSessionFlags.JackSessionSaveError)!=0;
		}
	/**
	 * Obtain a C-style integer representation of this flagset.
	 * @return
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public int enumerate()
		{
		int result=0;
		if(inNeedOfTerminal)result|=JackSessionFlags.JackSessionNeedTerminal;
		if(saveError)result|=JackSessionFlags.JackSessionSaveError;
		return result;
		}
	}//end JACKSEssionFLagset
