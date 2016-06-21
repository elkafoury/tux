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
import org.herac.tuxguitar.song.models.TGDuration;

/**
 * @author julian
 *
 */
public class ChangeDottedDurationAction extends Action{
	public static final String NAME = "action.note.duration.change-dotted";
	
	public ChangeDottedDurationAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | DISABLE_ON_PLAYING | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		//comienza el undoable
		UndoableMeasureGeneric undoable = UndoableMeasureGeneric.startUndo();
		
		getSelectedDuration().setDotted(!getSelectedDuration().isDotted());
		getSelectedDuration().setDoubleDotted(false);
		
		setDurations();
		
		//termia el undoable
		addUndoableEdit(undoable.endUndo());
		
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
