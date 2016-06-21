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
import java.rmi.RemoteException;
import java.util.Map;

import javax.sound.midi.MidiEvent;

import com.ritolaaudio.GPL;
import com.ritolaaudio.javajack.helper.SimpleJACKClient;
import com.ritolaaudio.javajack.port.MIDIInputPort;
import com.ritolaaudio.javajack.port.MIDIOutputPort;

/**
 * @author chuck
 *
 */
public class SimpleMIDIFlanger
	{
	@SuppressWarnings("unused")
	/**
	 * A simple demo which applies a one-channel flanger. Trippy! The user must manually patch this program to an audio source and destination.
	 * Without patching this application will do nothing. Consider qJACKctl. Sending MIDI keypresses to this program will allow for manual changing
	 * of the flanger delay.
	 * @param args
	 * @author	Chuck Ritola
	 * @date	Sep 27, 2011
	 */
	public static void main(String[] args)
		{
		GPL.printDisclaimer3("SimpleMIDIFlanger", "2011","Chuck Ritola");
		
		System.out.println("\n\nINSTRUCTIONS:");
		System.out.println("1. Run this program (guess you can skip this)");
		System.out.println("2. Connect a bright audio source to SimpleMIDIFlanger's input using a JACK router like qJACKctl.");
		System.out.println("3. Connect SimpleMIDIFlanger's output to your speakers, typically System:playback_1 or something like that.");
		System.out.println("4. You should hear flanging. Connect a MIDI note source like a keyboard to SimpleMIDIFlanger's MIDI input.");
		System.out.println("5. Play notes in your MIDI source. It should affect the flanger's frequency in a not-very-musical way.");
		System.out.println("6. Press ENTER (or RETURN) to quit.");
		try{
		new SimpleJACKClient("SimpleMIDIFlanger")
			{
			final int 		INITIAL_DELAY=100;//100 samples
			final double 	MOD_AMPLITUDE=0.5;//Modulation amplitude is 50% of the initial delay
			final double 	MOD_FREQ=.2; //Modulation is 0.2 Hertz
			final float		FEEDBACK=.5f; //Feedback after flanging has a gain of 0.3
			RingBufferFIFO 	ringBuffer = new RingBufferFIFO(INITIAL_DELAY);//The delay buffer which reads/writes in an endless circle.
			SineGenerator	lfo = new SineGenerator(getSampleRate()/MOD_FREQ);//The sine generator for our Low Frequency Oscillator which modulates the flanger delay.
			boolean keyMode=false;
			float			keyFlange;
			MIDIParser midiParser = new MIDIParser()
				{

					@Override
					public void channelAftertouch(int channel, int pressure)
						{
						}

					@Override
					public void controllerChange(int channel,
							int controlNumber, int controlValue)
						{
						}

					@Override
					public void noteOff(int channel, int pitch, int velocity)
						{
						keyMode=false;
						}

					@Override
					public void noteOn(int channel, int pitch, int velocity)
						{//Do a very rough pitch approximation in flanger form. (this is not at all tone-correct,by the way)
						keyFlange=(float)((double)pitch/127.0*(double)INITIAL_DELAY)+1.0f;//The +1 is so we don't have zero delay.
						keyMode=true;
						}

					@Override
					public void pitchBend(int channel, int bendValue)
						{
						}

					@Override
					public void polyphonicAftertouch(int channel, int pitch,
							int pressure)
						{
						}

					@Override
					public void programChange(int channel, int programNumber)
						{
						}
				
				};
				/**
				 * 
				 */
				private static final long serialVersionUID = 276923966235057418L;

				@Override
				public String[] getAudioInputPortNames()
					{
					return new String []{"In"};
					}

				@Override
				public String[] getAudioOutputPortNames()
					{
					return new String[]{"Out"};
					}

				@Override
				public String[] getMIDIInputPortNames()
					{
					// TODO Auto-generated method stub
					return new String[]{"mIn"};
					}

				@Override
				public String[] getMIDIOutputPortNames()
					{
					// TODO Auto-generated method stub
					return new String[]{"mOut"};
					}

				@Override
				public void process(
						Map<String, FloatBuffer> audioIn,
						Map<String, FloatBuffer> audioOut,
						Map<String, MIDIInputPort> midiIn,
						Map<String, MIDIOutputPort> midiOut)
					{
					final FloatBuffer aIn = audioIn.get("In");
					final FloatBuffer aOut = audioOut.get("Out");
					final MIDIInputPort mIn = midiIn.get("mIn");
					final MIDIOutputPort mOut = midiOut.get("mOut"); //commented out because it is not used.
					final int nFrames=aOut.capacity();
					//MIDI events
					try{midiParser.parseEvents(mIn.getMIDIEvents());}
					catch(RemoteException e){e.printStackTrace();}//We're probably local anyway.
					//Process the audio. No fast buffer copies since we need to process each sample.
					for(int i=0; i<nFrames; i++)
						{
						//Read the input sample
						final float inFloat=aIn.get(i);
						//Create our initial flanged sample
						final float outFloat=inFloat+ringBuffer.pop();
						//Write our sample to the output buffer
						aOut.put(i, outFloat);
						//Write our sample plus feedback to our ringbuffer (delay)
						ringBuffer.push(inFloat+outFloat*FEEDBACK);
						//Apply the LFO or MIDI key.
						if(!keyMode){ringBuffer.adjustDelay(INITIAL_DELAY+(int)(lfo.pop()*(INITIAL_DELAY*MOD_AMPLITUDE)));}	//LFO
						else 		{ringBuffer.adjustDelay((int)keyFlange);}												//MIDI key
						}//end for(nFrames)
					}//end process()
			
			
			abstract class MIDIParser
				{
				public MIDIParser(){}
				public void parseEvents(MidiEvent [] events)
					{for(MidiEvent e:events){parseEvent(e);}}
				public void parseEvent(MidiEvent event)
					{
					byte [] message = event.getMessage().getMessage();
					int status = event.getMessage().getStatus();
					int channel = status&0x0F;
					//System.out.println("Channel "+channel+" message length: "+message.length);
					switch (status|0x0F)
						{// This is a very crude implementation because it does not account for the event's tick value in the buffer. It's on or off for the whole buffer.
						case 0x8F: //Note on
							{
							noteOff(channel, message[1],message[2]);
							break;
							}
						case 0x9F: //Note off
							{
							noteOn(channel, message[1],message[2]);
							break;
							}
						case 0xAF: //Polyphonic Aftertouch
							{
							polyphonicAftertouch(channel, message[1],message[2]);
							break;
							}
						case 0xBF: //Controller change
							{
							controllerChange(channel, message[1],message[2]);
							break;
							}
						case 0xCF: //Program change
							{
							programChange(channel, message[1]);
							break;
							}
						case 0xDF: //Channel Aftertouch
							{
							channelAftertouch(channel, message[1]);
							break;
							}
						case 0xEF: //Pitch bend
							{
							pitchBend(channel, message[1]+message[2]*127);
							break;
							}
						}//end switch()
					}//end parseEvent()
				public abstract void noteOn(int channel, int pitch, int velocity);
				public abstract void noteOff(int channel, int pitch, int velocity);
				public abstract void polyphonicAftertouch(int channel, int pitch, int pressure);
				public abstract void controllerChange(int channel, int controlNumber, int controlValue);
				public abstract void programChange(int channel, int programNumber);
				public abstract void channelAftertouch(int channel, int pressure);
				public abstract void pitchBend(int channel,int bendValue);
				}//end class MIDIParser
				
			/**
			 * A FIFO queue for float values which is used as an audio delay.
			 * @author chuck
			 *
			 */
			class RingBufferFIFO
				{
				int inIndex;
				int outIndex;
				int currentDelay;
				final int CAPACITY=10000;
				float [] ringBuffer = new float[CAPACITY];
				
				public RingBufferFIFO(int delayInFrames)
					{
					outIndex=CAPACITY-delayInFrames;
					currentDelay=delayInFrames;
					}
				public void adjustDelay(int newDelay)
					{
					final int adjustment=newDelay-currentDelay;
					outIndex+=adjustment;
					if(outIndex<0)outIndex+=CAPACITY;
					outIndex%=CAPACITY;
					currentDelay=newDelay;
					}
				public void push(float value)
					{
					ringBuffer[inIndex]=value;
					inIndex++;
					inIndex%=CAPACITY;
					}
				/**
				 * Obtain the current delayed value while progressing its index.
				 * @return
				 * @author	Chuck Ritola
				 * @date	Sep 27, 2011
				 */
				public float pop()
					{
					float result=ringBuffer[outIndex];
					outIndex++;
					outIndex%=CAPACITY;
					return result;
					}
				/**
				 * Obtain the current delayed value without progressing its index.
				 * @return
				 * @author	Chuck Ritola
				 * @date	Sep 27, 2011
				 */
				public float peek()
					{
					return ringBuffer[outIndex];
					}
				}//end RingBufferFIFO
			
			/**
			 * A sinewave generator, using pop() to obtain each consecutive sine value at the given period.
			 * @author chuck
			 *
			 */
			class SineGenerator
				{
				double phase=0;
				double phaseIncrement;
				public SineGenerator(double periodInFrames)
					{
					phaseIncrement=Math.PI*2/(periodInFrames);
					}
				/**
				 * Obtain the current sine value and progress its phase.
				 * @return
				 * @author	Chuck Ritola
				 * @date	Sep 27, 2011
				 */
				public double pop()
					{
					phase+=phaseIncrement;
					return Math.sin(phase);
					}
				/**
				 * Obtain the current sine value without progressing its phase.
				 * @return
				 * @author	Chuck Ritola
				 * @date	Sep 27, 2011
				 */
				public double peek()
					{
					return Math.sin(phase);
					}
				}//end SineGenerator
			};
		System.in.read();//Wait for carriage-return-linefeed
		}
		catch(Exception e){e.printStackTrace();}
		System.exit(0);
		}//end main()
	}//end SimpleClientDemo
