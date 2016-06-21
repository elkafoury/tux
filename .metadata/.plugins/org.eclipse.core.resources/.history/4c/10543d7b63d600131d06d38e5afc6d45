/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.marker;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.marker.MarkerList;

/**
 * @author julian
 *
 */
public class ListMarkersAction extends Action{
	public static final String NAME = "action.marker.list";
	
	public ListMarkersAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE);
	}
	
	protected int execute(TypedEvent e){
		if(MarkerList.instance().isDisposed()){
			MarkerList.instance().show();
		}
		else{
			MarkerList.instance().dispose();
		}
		return 0;
	}
}
