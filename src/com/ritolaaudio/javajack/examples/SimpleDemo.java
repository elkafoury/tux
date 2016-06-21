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

import java.nio.FloatBuffer;
import java.util.Map;

import com.ritolaaudio.GPL;
import com.ritolaaudio.javajack.helper.SimpleJACKClient;
import com.ritolaaudio.javajack.port.MIDIInputPort;
import com.ritolaaudio.javajack.port.MIDIOutputPort;

/**
 * A simple stereo pass-through JACK Client. 
 * All SimpleJACKClients extend the Client class and attempt to set themselves up, activate, and create a keep-alive thread in the background.
 * @author chuck
 *
 */
public class SimpleDemo
	{
	/**
	 * @param args
	 * @author	Chuck Ritola
	 * @date	Oct 6, 2011
	 */
	public static void main(String[] args)
		{
		GPL.printDisclaimer3("SimpleDemo", "2011", "Chuck Ritola");
		try{new SimpleJACKClient("SimpleDemo")
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 8312905434657214802L;

				@Override
				public String[] getAudioInputPortNames()
					{return new String []{"In1","In2"};}

				@Override
				public String[] getAudioOutputPortNames()
					{return new String []{"Out1","Out2"};}

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
					//Copy the input FloatBuffers to the output FloatBuffers.
					audioOut.get("Out1").put(audioIn.get("In1"));
					audioOut.get("Out2").put(audioIn.get("In2"));
					}//end process()
			};}
		catch(Exception e){}
		}//end main()
	}//end SimpleDemo
