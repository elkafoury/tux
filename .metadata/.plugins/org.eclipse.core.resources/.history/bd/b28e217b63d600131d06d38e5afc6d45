/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.edit;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.actions.Action;

/**
 * @author julian
 *
 */
public class SetNaturalKeyAction extends Action{
	public static final String NAME = "action.edit.set-natural-key";
	
	public SetNaturalKeyAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | DISABLE_ON_PLAYING | AUTO_UPDATE);
	}
	
	protected int execute(TypedEvent e){
		getEditor().getTablature().getEditorKit().setNatural(!getEditor().getTablature().getEditorKit().isNatural());
		return 0;
	}
}
