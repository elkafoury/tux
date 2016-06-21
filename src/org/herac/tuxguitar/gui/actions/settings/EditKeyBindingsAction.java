/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.settings;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.system.keybindings.editor.KeyBindingEditor;

/**
 * @author julian
 *
 */
public class EditKeyBindingsAction extends Action{
	public static final String NAME = "action.settings.key-bindings";
	
	public EditKeyBindingsAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE);
	}
	
	protected int execute(TypedEvent e){
		new KeyBindingEditor().show(TuxGuitar.instance().getShell());
		return 0;
	}
}
