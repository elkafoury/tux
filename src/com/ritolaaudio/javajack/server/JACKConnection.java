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

import java.io.Serializable;

/**
 * @author chuck
 *
 */
public class JACKConnection implements Serializable
	{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1481807174630496892L;
	public String port1,port2,client1,client2,machine1,machine2;//machine1 and machine2 are used in macroJACK
	public JACKConnection(String client1, String port1, String client2, String port2)
		{
		if(client1==null)throw new NullPointerException("client1 is null. Need non-null value.");
		this.client1=client1;
		if(client2==null)throw new NullPointerException("client2 is null. Need non-null value.");
		this.client2=client2;
		if(port1==null)throw new NullPointerException("port1 is null. Need non-null value.");
		this.port1=port1;
		if(port2==null)throw new NullPointerException("port2 is null. Need non-null value.");
		this.port2=port2;
		}
	}
