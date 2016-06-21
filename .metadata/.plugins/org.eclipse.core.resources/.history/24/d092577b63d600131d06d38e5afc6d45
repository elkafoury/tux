/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.track;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.editors.tab.Caret;
import org.herac.tuxguitar.gui.undo.undoables.track.UndoableMoveTrackDown;
import org.herac.tuxguitar.song.models.TGTrack;

/**
 * @author julian
 *
 */
public class MoveTrackDownAction extends Action{
	public static final String NAME = "action.track.move-down";
	
	public MoveTrackDownAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | DISABLE_ON_PLAYING | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		//comienza el undoable
		UndoableMoveTrackDown undoable = UndoableMoveTrackDown.startUndo();
		
		Caret caret = getEditor().getTablature().getCaret();
		TGTrack track = caret.getTrack();
		
		if(getSongManager().moveTrackDown(track)){
			updateTablature();
			TuxGuitar.instance().getMixer().update();
			
			//termia el undoable
			addUndoableEdit(undoable.endUndo(track));
			TuxGuitar.instance().getFileHistory().setUnsavedFile();
		}
		return 0;
	}
}
