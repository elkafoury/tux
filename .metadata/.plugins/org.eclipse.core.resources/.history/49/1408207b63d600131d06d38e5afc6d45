/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.edit;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.undo.CannotRedoException;

/**
 * @author julian
 *
 */
public class RedoAction extends Action{
	public static final String NAME = "action.edit.redo";
	
	public RedoAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | DISABLE_ON_PLAYING | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		try {
			if(TuxGuitar.instance().getUndoableManager().canRedo()){
				TuxGuitar.instance().getUndoableManager().redo();
			}
		} catch (CannotRedoException e1) {
			e1.printStackTrace();
		}
		return 0;
	}
}
