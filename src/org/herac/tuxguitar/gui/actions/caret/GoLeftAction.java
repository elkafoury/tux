/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.caret;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.actions.Action;

/**
 * @author julian
 *
 */
public class GoLeftAction extends Action{
	public static final String NAME = "action.caret.go-left";
	
	public GoLeftAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE);
	}
	
	protected int execute(TypedEvent e){
		if(TuxGuitar.instance().getPlayer().isRunning()){
			TuxGuitar.instance().getTransport().gotoPrevious();
		}
		else{
			getEditor().getTablature().getCaret().moveLeft();
		}
		return 0;
	}
}
