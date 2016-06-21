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
package com.ritolaaudio.javajack.server;

/**
 * Thrown when Server is instantiated but fails to launch its dummy client. 
 * It also encapsulates the specific exception which caused this failure.
 * @author chuck
 *
 */
public class DummyClientCreationFailureException extends Exception
	{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7312662280947112115L;
	Exception cause;
	public DummyClientCreationFailureException(Exception cause)
		{
		super();
		this.cause=cause;
		}
	
	public Exception getCause(){return cause;}
	}//
