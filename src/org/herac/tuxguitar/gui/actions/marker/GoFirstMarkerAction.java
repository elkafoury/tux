/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.marker;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.marker.MarkerNavigator;

/**
 * @author julian
 *
 */
public class GoFirstMarkerAction extends Action{
	public static final String NAME = "action.marker.go-first";
	
	public GoFirstMarkerAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE);
	}
	
	protected int execute(TypedEvent e){
		new MarkerNavigator().goToSelectedMarker(getSongManager().getFirstMarker());
		
		return 0;
	}
}
