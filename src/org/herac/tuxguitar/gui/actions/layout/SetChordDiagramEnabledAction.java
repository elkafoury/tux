/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.layout;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.editors.tab.layout.ViewLayout;

/**
 * @author julian
 *
 */
public class SetChordDiagramEnabledAction extends Action{
	public static final String NAME = "action.view.layout-set-chord-diagram-enabled";
	
	public SetChordDiagramEnabledAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		ViewLayout layout = getEditor().getTablature().getViewLayout();
		layout.setStyle( ( layout.getStyle() ^ ViewLayout.DISPLAY_CHORD_DIAGRAM) );
		updateTablature();
		return 0;
	}
}
