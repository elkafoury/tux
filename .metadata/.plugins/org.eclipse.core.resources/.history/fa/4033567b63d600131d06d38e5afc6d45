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
public class GoFirstTrackAction extends Action{
	public static final String NAME = "action.track.go-first";
	
	public GoFirstTrackAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		Caret caret = getEditor().getTablature().getCaret();
		TGTrack track = getSongManager().getFirstTrack();
		if(track != null){
			caret.update(track.getNumber());
		}
		return 0;
	}
}
