
/*******************************************************************************
 *  * JavaJACK - A JACK bridge for Java.
 *  *     Copyright (C) 2011  Chuck Ritola
 *  * 	chuck@ritolaaudio.com 
 * *	...with parts generated by JNAerator, where noted.
 *  * 
 *  *     This program is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  * 
 *  *     This program is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU General Public License for more details.
 *  * 
 *  *     You should have received a copy of the GNU General Public License
 *  *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  ******************************************************************************/
package com.ritolaaudio.jack;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
/**
 * <i>native declaration : jack/session.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class jack_session_command_t extends Structure<jack_session_command_t, jack_session_command_t.ByValue, jack_session_command_t.ByReference > {
	/// C type : const char*
	public Pointer uuid;
	/// C type : const char*
	public Pointer client_name;
	/// C type : const char*
	public Pointer command;
	/**
	 * @see jack_session_flags_t<br>
	 * C type : jack_session_flags_t
	 */
	public int flags;
	public jack_session_command_t() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"uuid", "client_name", "command", "flags"});
	}
	/**
	 * @param uuid C type : const char*<br>
	 * @param client_name C type : const char*<br>
	 * @param command C type : const char*<br>
	 * @param flags @see jack_session_flags_t<br>
	 * C type : jack_session_flags_t
	 */
	public jack_session_command_t(Pointer uuid, Pointer client_name, Pointer command, int flags) {
		super();
		this.uuid = uuid;
		this.client_name = client_name;
		this.command = command;
		this.flags = flags;
		initFieldOrder();
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected jack_session_command_t newInstance() { return new jack_session_command_t(); }
	public static jack_session_command_t[] newArray(int arrayLength) {
		return Structure.newArray(jack_session_command_t.class, arrayLength);
	}
	public static class ByReference extends jack_session_command_t implements Structure.ByReference {
		
	};
	public static class ByValue extends jack_session_command_t implements Structure.ByValue {
		
	};
}
