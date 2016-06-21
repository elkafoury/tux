/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.track;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.editors.tab.Caret;
import org.herac.tuxguitar.song.models.TGTrack;

/**
 * @author julian
 *
 */
public class GoPreviousTrackAction extends Action{
	public static final String NAME = "action.track.go-previous";
	
	public GoPreviousTrackAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		Caret caret = getEditor().getTablature().getCaret();
		TGTrack track = getSongManager().getTrack(caret.getTrack().getNumber() - 1);
		if(track != null){
			caret.update(track.getNumber());
		}
		return 0;
	}
}
