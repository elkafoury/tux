/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.tools;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.tools.scale.ScaleEditor;

/**
 * @author julian
 *
 */
public class ScaleAction extends Action{
	public static final String NAME = "action.tools.scale";
	
	public ScaleAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE);
	}
	
	protected int execute(TypedEvent e){
		new ScaleEditor().show();
		return 0;
	}
}
