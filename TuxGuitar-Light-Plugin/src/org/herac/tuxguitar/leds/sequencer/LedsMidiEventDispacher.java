package org.herac.tuxguitar.leds.sequencer;

import org.herac.tuxguitar.player.base.MidiPlayerException;
import org.herac.tuxguitar.player.impl.sequencer.MidiEvent;
import org.herac.tuxguitar.gui.TuxGuitar;

public class LedsMidiEventDispacher{
	private LedsMidiSequencerImpl sequencer;
	
	public LedsMidiEventDispacher(LedsMidiSequencerImpl sequencer){
		this.sequencer = sequencer;
	}
	
	public void dispatch(MidiEvent event) throws MidiPlayerException{
		if(event.getType() == MidiEvent.MIDI_EVENT_NOTEON){
			if (event.getTrack() == TuxGuitar.instance().getTablatureEditor().getTablature().getCaret().getTrack().getNumber()) {
			  sequencer.pause();
			} else {
			  this.sequencer.getTransmitter().sendNoteOn(event.getData()[0],event.getData()[1],event.getData()[2]);
			}
		}
		else if(event.getType() == MidiEvent.MIDI_EVENT_NOTEOFF){
			this.sequencer.getTransmitter().sendNoteOff(event.getData()[0],event.getData()[1],event.getData()[2]);
		}
		else if(event.getType() == MidiEvent.MIDI_EVENT_CONTROL_CHANGE){
			this.sequencer.getTransmitter().sendControlChange(event.getData()[0],event.getData()[1],event.getData()[2]);
		}
		else if(event.getType() == MidiEvent.MIDI_EVENT_PROGRAM_CHANGE){
			this.sequencer.getTransmitter().sendProgramChange(event.getData()[0],event.getData()[1]);
		}
		else if(event.getType() == MidiEvent.MIDI_EVENT_PITCH_BEND){
			this.sequencer.getTransmitter().sendPitchBend(event.getData()[0],event.getData()[1]);
		}
		else if(event.getType() == MidiEvent.MIDI_SYSTEM_EVENT){
			if(event.getData()[0] == 0x51){
				int usq = ((event.getData()[1] & 0xff) | ((event.getData()[2] & 0xff) << 8) | ((event.getData()[3] & 0xff) << 16));
				this.sequencer.setTempo( (int)((60.00 * 1000.00) / (usq / 1000.00)) );
			}
		}
	}
}
