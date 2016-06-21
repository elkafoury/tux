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
package com.ritolaaudio.javajack.examples;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.ritolaaudio.GPL;
import com.ritolaaudio.jack.JackLibrary;
import com.ritolaaudio.jack.JackLibrary.JackOptions;
import com.ritolaaudio.jack.JackLibrary.JackPortFlags;
import com.ritolaaudio.jack.JackLibrary.JackProcessCallback;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

/**
 * A demonstration of a simple pass-through JACK Audio client using the raw JNAerator-produced wrapper classes.
 * Use of the raw classes is discouraged in favor of the Java-jack classes which wrap these raw classes. See SimpleDemo.
 * @author chuck
 *
 */
public class RawClientDemo
	{
	public static final JackLibrary lib			=JackLibrary.INSTANCE;
	public static Pointer 						client;
	public static JackProcessCallback callback 	= new JackProcessCallback()
		{//Strange static declaration of this callback has to be done because else the VM will think it is not being used anymore and GC it.
		@Override
		public int apply(int nframes, Pointer arg)
			{
			final Pointer inPortBufferPtr=lib.jack_port_get_buffer(lib.jack_port_by_name(client, "RawClientDemo:in"),nframes);
			final Pointer outPortBufferPtr=lib.jack_port_get_buffer(lib.jack_port_by_name(client, "RawClientDemo:out"),nframes);
			outPortBufferPtr.getByteBuffer(0, nframes*4).put(inPortBufferPtr.getByteBuffer(0, nframes*4));
			return 0;
			}
		};
	
	/**
	 * @param args
	 * @author	Chuck Ritola
	 * @date	Sep 27, 2011
	 */
	public static void main(String[] args)
		{
		GPL.printDisclaimer3("RawClientDemo", "2011","Chuck Ritola");
		
		final IntBuffer status = ByteBuffer.allocateDirect(4).asIntBuffer();
		client = lib.jack_client_open("RawClientDemo", JackOptions.JackNullOption, status, new Object[]{});
		final int bufferSize = lib.jack_get_buffer_size(client);
		lib.jack_set_process_callback(client, callback,null);
		lib.jack_port_register(client, "in", JackLibrary.JACK_DEFAULT_AUDIO_TYPE, new NativeLong(JackPortFlags.JackPortIsInput), new NativeLong(bufferSize));
		lib.jack_port_register(client, "out", JackLibrary.JACK_DEFAULT_AUDIO_TYPE, new NativeLong(JackPortFlags.JackPortIsOutput), new NativeLong(bufferSize));
		new Thread(){public void run(){while(true){try//Keep-alive thread.
			{Thread.sleep(5000);}catch (InterruptedException e){e.printStackTrace();}}}}.start();
		lib.jack_activate(client);
		}//end main(...)

	}
