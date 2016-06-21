/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.note;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.undo.undoables.measure.UndoableMeasureGeneric;
import org.herac.tuxguitar.song.models.TGBeat;

/**
 * @author julian
 *
 */
public class CleanBeatAction extends Action{
	public static final String NAME = "action.note.general.clean-beat";
	
	public CleanBeatAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | DISABLE_ON_PLAYING | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		TGBeat beat = getEditor().getTablature().getCaret().getSelectedBeat();
		if( beat != null){
			//comienza el undoable
			UndoableMeasureGeneric undoable = UndoableMeasureGeneric.startUndo();
			TuxGuitar.instance().getFileHistory().setUnsavedFile();
			
			//getSongManager().getMeasureManager().removeAllComponentsAt(caret.getMeasure(),caret.getSelectedComponent().getStart());
			getSongManager().getMeasureManager().cleanBeat(beat);
			
			//termia el undoable
			addUndoableEdit(undoable.endUndo());
			updateTablature();
		}
		return 0;
	}
	
	public void updateTablature() {
		fireUpdate(getEditor().getTablature().getCaret().getMeasure().getNumber());
	}
}
