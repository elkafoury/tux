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
package com.ritolaaudio.javajack;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Map;

import com.ritolaaudio.GlobalPointer.LocallyAllocatedGlobalPointer;
import com.ritolaaudio.javajack.client.Client;
import com.ritolaaudio.javajack.client.ProcessingKernel;
import com.ritolaaudio.javajack.client.Client.LocalClient;
import com.ritolaaudio.javajack.flags.NullOption;
import com.ritolaaudio.javajack.helper.SimpleJACKClient;
import com.ritolaaudio.javajack.port.AudioInputPort;
import com.ritolaaudio.javajack.port.AudioOutputPort;
import com.ritolaaudio.javajack.port.MIDIInputPort;
import com.ritolaaudio.javajack.port.MIDIOutputPort;
import com.ritolaaudio.javajack.server.JACKServer;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import com.ritolaaudio.jack.JackLibrary;
import com.ritolaaudio.jack.JackLibrary.*;


public class TestJavaJack
	{

	/**
	 * @param args
	 */
	public static void main(String[] args)
		{
		//cStyle2();
		easyStyle();
		}
	
	protected static void easyStyle()
		{
		try
			{
			new SimpleJACKClient("TestClient")
				{
					/**
					 * 
					 */
					private static final long serialVersionUID = -1334335759893101268L;

					@Override
					public String[] getAudioInputPortNames()
						{return new String [] {"testIn1","testIn2"};}

					@Override
					public String[] getAudioOutputPortNames()
						{return new String[]{"testOut1","testOut2"};}

					@Override
					public String[] getMIDIInputPortNames()
						{return null;}

					@Override
					public String[] getMIDIOutputPortNames()
						{return null;}

					@Override
					public void process(Map<String, FloatBuffer> audioIn,
							Map<String, FloatBuffer> audioOut,
							Map<String, MIDIInputPort> midiIn,
							Map<String, MIDIOutputPort> midiOut)
						{
						audioOut.get("testOut1").put(audioIn.get("testIn1"));
						audioOut.get("testOut2").put(audioIn.get("testIn2"));
						}
				};
			}
		catch (Exception e)
			{e.printStackTrace();}
		}
	
	static class TestCallback implements JackProcessCallback
		{
		@Override
		public int apply(int nframes, Pointer arg)
			{
			return 0;
			}
		}//end TestCallback
	
	protected static void javaStyle()
		{
		try
			{
			JACKServer server = JACK.getServer();
			LocalClient client = (Client.LocalClient)server.createClient("fooClient",new NullOption());
			final AudioInputPort p = client.createAudioInputPort("fooIn", 1024);
			final AudioOutputPort _p = client.createAudioOutputPort("fooOut",1024);
			new Thread(){public void run(){try
				{while(true){Thread.sleep(1000);}}
			catch (InterruptedException e)
				{e.printStackTrace();}}}.start();
			
			client.setProcessingKernel(new ProcessingKernel()
				{
				/**
					 * 
					 */
					private static final long serialVersionUID = 6283399300728326290L;

				@Override
				public void process(Client client, int numberOfFrames,
						long jackTimeOfLastFrame)
					{
					Pointer in,out;
					try
						{
						in = ((LocallyAllocatedGlobalPointer)p.getBuffer()).getPointer();
						out = ((LocallyAllocatedGlobalPointer)_p.getBuffer()).getPointer();
						out.getByteBuffer(0, numberOfFrames*4).put(in.getByteBuffer(0, numberOfFrames*4));
						}
					catch(Exception e){e.printStackTrace();}
					}
				});
			
			client.activate();
			try
				{
				Thread.sleep(50000);
				}
			catch (InterruptedException e)
				{
				e.printStackTrace();
				}
			//client.close();//automatically deactivates, unregisters ports
			}
		catch (Exception e)
			{e.printStackTrace();}
		}
	
	protected static void cStyle()
		{
		JackLibrary lib=JackLibrary.INSTANCE;
		IntBuffer status = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
		status.put(0);
		int options=JackOptions.JackNullOption;
		Pointer client = lib.jack_client_open("fooClient", options, status, new Object [] {});
		TestCallback cb=new TestCallback();
		lib.jack_set_process_callback(client, cb, null);
		lib.jack_port_register(client, "Port 1 in", JackLibrary.JACK_DEFAULT_AUDIO_TYPE, new NativeLong(JackPortFlags.JackPortIsInput), new NativeLong(1024));
		lib.jack_port_register(client, "Port 1 out", JackLibrary.JACK_DEFAULT_AUDIO_TYPE, new NativeLong(JackPortFlags.JackPortIsOutput), new NativeLong(1024));
		
		lib.jack_activate(client);
		try
			{
			Thread.sleep(50000);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		
		lib.jack_deactivate(client);
		System.out.println("Closing...");
		lib.jack_client_close(client);
		}
	
	protected static void cStyle2()
		{
		try{
		JackLibrary lib=JackLibrary.INSTANCE;
		//IntBuffer status = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
		//status.put(0);
		JACKServer server = JACK.getServer();
		LocalClient _client = (Client.LocalClient)server.createClient("fooClient", new NullOption());
		Pointer client=_client.getPointer();
		//Pointer client = lib.jack_client_open("fooClient", JackOptions.JackNullOption, null, new Object [] {});
		//TestCallback cb=new TestCallback();
		//lib.jack_set_process_callback(client, cb, null);
		lib.jack_port_register(client, "Port 1 in", JackLibrary.JACK_DEFAULT_AUDIO_TYPE, new NativeLong(JackPortFlags.JackPortIsInput), new NativeLong(1024));
		lib.jack_port_register(client, "Port 1 out", JackLibrary.JACK_DEFAULT_AUDIO_TYPE, new NativeLong(JackPortFlags.JackPortIsOutput), new NativeLong(1024));
		
		lib.jack_activate(client);
		try
			{
			Thread.sleep(50000);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		
		lib.jack_deactivate(client);
		System.out.println("Closing...");
		lib.jack_client_close(client);
		}catch(Exception e){e.printStackTrace();}
		}
	}//end Test
