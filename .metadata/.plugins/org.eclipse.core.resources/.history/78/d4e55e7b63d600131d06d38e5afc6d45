/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.view;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.actions.Action;

/**
 * @author julian
 *
 */
public class ShowTransportAction extends Action{
	public static final String NAME = "action.view.show-transport";
	
	public ShowTransportAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK |  AUTO_UPDATE | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		if(TuxGuitar.instance().getTransport().isDisposed()){
			TuxGuitar.instance().getTransport().show();
		}else{
			TuxGuitar.instance().getTransport().dispose();
		}
		return 0;
	}
}
