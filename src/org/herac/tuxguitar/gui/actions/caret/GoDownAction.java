/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.caret;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.actions.Action;

/**
 * @author julian
 *
 */
public class GoDownAction extends Action{
	public static final String NAME = "action.caret.go-down";
	
	public GoDownAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | DISABLE_ON_PLAYING | AUTO_UPDATE );
	}
	
	protected int execute(TypedEvent e){
		getEditor().getTablature().getCaret().moveDown();
		return 0;
	}
}
