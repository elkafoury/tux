/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.edit;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.editors.tab.edit.EditorKit;

/**
 * @author julian
 *
 */
public class SetMouseModeEditionAction extends Action{
	public static final String NAME = "action.edit.set-mouse-mode-edition";
	
	public SetMouseModeEditionAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | DISABLE_ON_PLAYING | AUTO_UPDATE);
	}
	
	protected int execute(TypedEvent e){
		getEditor().getTablature().getEditorKit().setMouseMode(EditorKit.MOUSE_MODE_EDITION);
		return 0;
	}
}
