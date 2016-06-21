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
 * Requests that the created Client affiliate with the server whose name is the specified name-String.
 * @author chuck
 *
 */
public class ServerName extends JACKOption
	{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2399311890942102703L;
	String name;
	public ServerName(String name){this.name=name;}
	@Override
	protected int enumerate()
		{
		return JackOptions.JackServerName;
		}

	@Override
	protected Object getArg()
		{
		return name;
		}

	}