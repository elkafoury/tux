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
/**
 * WARNING: This class is untested.
 */

package com.ritolaaudio.javajack.flags;

import com.ritolaaudio.jack.JackLibrary.JackOptions;

/**
 * Passes the specified session ID such that the SessionManager can find the created Client again.
 * @author chuck
 *
 */
public class SessionID extends JACKOption
	{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3923406843133370580L;
	Object token;
	public SessionID(Object token)
		{this.token=token;}
	@Override
	protected int enumerate()
		{
		return JackOptions.JackSessionID;
		}

	@Override
	protected Object getArg()
		{
		return token;
		}

	}
