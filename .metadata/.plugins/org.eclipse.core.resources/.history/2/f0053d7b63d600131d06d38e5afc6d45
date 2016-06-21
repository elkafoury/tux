/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.marker;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.editors.tab.Caret;
import org.herac.tuxguitar.gui.marker.MarkerNavigator;

/**
 * @author julian
 *
 */
public class GoPreviousMarkerAction extends Action{
	public static final String NAME = "action.marker.go-previous";
	
	public GoPreviousMarkerAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		Caret caret = getEditor().getTablature().getCaret();
		
		new MarkerNavigator().goToSelectedMarker(getSongManager().getPreviousMarker(caret.getMeasure().getNumber()));
		
		return 0;
	}
}
