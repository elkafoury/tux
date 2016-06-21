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
public class SetVoice1Action extends Action{
	public static final String NAME = "action.edit.voice-1";
	
	public SetVoice1Action() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		getEditor().getTablature().getCaret().setVoice(0);
		return 0;
	}
}
