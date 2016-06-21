/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.settings;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.system.config.TGConfigEditor;

/**
 * @author julian
 *
 */
public class EditConfigAction extends Action{
	public static final String NAME = "action.settings.configure";
	
	public EditConfigAction() {
		super(NAME, AUTO_LOCK | AUTO_UPDATE | KEY_BINDING_AVAILABLE );
	}
	
	protected int execute(TypedEvent e){
		new TGConfigEditor().showDialog(TuxGuitar.instance().getShell());
		return 0;
	}
}
