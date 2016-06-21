
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
import com.ochafik.lang.jnaerator.runtime.NativeSize;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
/**
 * <i>native declaration : jack/ringbuffer.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class jack_ringbuffer_data_t extends Structure<jack_ringbuffer_data_t, jack_ringbuffer_data_t.ByValue, jack_ringbuffer_data_t.ByReference > {
	/// C type : char*
	public Pointer buf;
	public NativeSize len;
	public jack_ringbuffer_data_t() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"buf", "len"});
	}
	/// @param buf C type : char*
	public jack_ringbuffer_data_t(Pointer buf, NativeSize len) {
		super();
		this.buf = buf;
		this.len = len;
		initFieldOrder();
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected jack_ringbuffer_data_t newInstance() { return new jack_ringbuffer_data_t(); }
	public static jack_ringbuffer_data_t[] newArray(int arrayLength) {
		return Structure.newArray(jack_ringbuffer_data_t.class, arrayLength);
	}
	public static class ByReference extends jack_ringbuffer_data_t implements Structure.ByReference {
		
	};
	public static class ByValue extends jack_ringbuffer_data_t implements Structure.ByValue {
		
	};
}
