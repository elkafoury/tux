/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.duration;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.editors.tab.Caret;
import org.herac.tuxguitar.gui.undo.undoables.measure.UndoableMeasureGeneric;
import org.herac.tuxguitar.song.models.TGBeat;
import org.herac.tuxguitar.song.models.TGDuration;
import org.herac.tuxguitar.song.models.TGVoice;

/**
 * @author julian
 *
 */
public class SetSixteenthDurationAction extends Action{
	public static final String NAME = "action.note.duration.set-sixteenth";
	public static final int VALUE = TGDuration.SIXTEENTH;
	
	public SetSixteenthDurationAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | DISABLE_ON_PLAYING | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		Caret caret = getEditor().getTablature().getCaret();
		TGBeat beat = caret.getSelectedBeat();
		if(beat != null){
			TGVoice voice = beat.getVoice( caret.getVoice() );
			TGDuration duration = getSelectedDuration();
			if(duration.getValue() != VALUE || (!voice.isEmpty() && voice.getDuration().getValue() != VALUE)){
				//comienza el undoable
				UndoableMeasureGeneric undoable = UndoableMeasureGeneric.startUndo();
				
				getSelectedDuration().setValue(VALUE);
				getSelectedDuration().setDotted(false);
				getSelectedDuration().setDoubleDotted(false);
				setDurations();
				
				//termia el undoable
				addUndoableEdit(undoable.endUndo());
			}
		}
		return 0;
	}
	
	private void setDurations() {
		Caret caret = getEditor().getTablature().getCaret();
		caret.changeDuration(getSelectedDuration().clone());
		TuxGuitar.instance().getFileHistory().setUnsavedFile();
		fireUpdate(getEditor().getTablature().getCaret().getMeasure().getNumber());
	}
	
	public TGDuration getSelectedDuration(){
		return getEditor().getTablature().getCaret().getDuration();
	}
}
