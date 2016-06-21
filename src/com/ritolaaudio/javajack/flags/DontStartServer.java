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
package com.ritolaaudio.javajack.flags;

import com.ritolaaudio.jack.JackLibrary.JackOptions;

/**
 * Requests that a JACK server not be started upon creating a JACK Client, preferring to find an already running server.
 * "This option is always selected if $JACK_NO_START_SERVER is defined in the calling process environment. " - JACK API
 * @author chuck
 *
 */
public class DontStartServer extends JACKOption
	{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5306473619809154198L;

	@Override
	protected int enumerate()
		{
		return JackOptions.JackNoStartServer;
		}

	@Override
	protected Object getArg()
		{
		return null;
		}

	}
