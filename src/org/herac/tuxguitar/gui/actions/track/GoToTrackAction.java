/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.track;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.editors.tab.TGTrackImpl;

/**
 * @author julian
 *
 */
public class GoToTrackAction extends Action{
	public static final String NAME = "action.track.goto";
	
	public GoToTrackAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE);
	}
	
	protected int execute(TypedEvent e){
		Object data = e.widget.getData();
		if(data instanceof TGTrackImpl){
			TGTrackImpl track = (TGTrackImpl)data;
			getEditor().getTablature().getCaret().update(track.getNumber());
		}
		return 0;
	}
}
