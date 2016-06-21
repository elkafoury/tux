/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.measure;

import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.editors.tab.Caret;
import org.herac.tuxguitar.gui.editors.tab.TGMeasureImpl;
import org.herac.tuxguitar.gui.editors.tab.TGTrackImpl;

/**
 * @author julian
 *
 */
public class GoPreviousMeasureAction extends Action{
	public static final String NAME = "action.measure.go-previous";
	
	public GoPreviousMeasureAction() {
		super(NAME, AUTO_LOCK | AUTO_UNLOCK | AUTO_UPDATE | KEY_BINDING_AVAILABLE);
	}
	
	protected int execute(TypedEvent e){
		if(TuxGuitar.instance().getPlayer().isRunning()){
			TuxGuitar.instance().getTransport().gotoPrevious();
		}
		else{
			Caret caret = getEditor().getTablature().getCaret();
			TGTrackImpl track = caret.getTrack();
			TGMeasureImpl measure = (TGMeasureImpl)getSongManager().getTrackManager().getPrevMeasure(caret.getMeasure());
			if(track != null && measure != null){
				caret.update(track.getNumber(),measure.getStart(),caret.getSelectedString().getNumber());
			}
		}
		return 0;
	}
}
