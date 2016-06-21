/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.transport;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.actions.Action;

/**
 * @author julian
 * 
 */
public class TransportPlayAction extends Action {
	public static final String NAME = "action.transport.play";
	
	public TransportPlayAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		TuxGuitar.instance().getTransport().play();
		return 0;
	}
}