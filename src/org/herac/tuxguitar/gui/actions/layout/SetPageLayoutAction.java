/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.layout;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.editors.tab.Tablature;
import org.herac.tuxguitar.gui.editors.tab.layout.PageViewLayout;

/**
 * @author julian
 *
 */
public class SetPageLayoutAction extends Action{
	public static final String NAME = "action.view.layout-set-page";
	
	public SetPageLayoutAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		Tablature tablature = getEditor().getTablature();
		tablature.setViewLayout(new PageViewLayout(tablature,tablature.getViewLayout().getStyle()));
		updateTablature();
		return 0;
	}
}
