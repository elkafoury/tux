/*
 * Created on 17-dic-2005
 *
 */
package org.herac.tuxguitar.gui.actions.file;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TypedEvent;
import org.herac.tuxguitar.gui.TuxGuitar;
import org.herac.tuxguitar.gui.actions.Action;
import org.herac.tuxguitar.gui.actions.ActionLock;

/**
 * @author julian
 * 
 */
public class SaveAsFileAction extends Action {
	public static final String NAME = "action.file.save-as";
	
	public SaveAsFileAction() {
		super(NAME, AUTO_LOCK | AUTO_UPDATE | KEY_BINDING_AVAILABLE );
	}
	
	protected int execute(TypedEvent e){
		final String fileName = FileActionUtils.chooseFileName();
		if(fileName == null){
			return AUTO_UNLOCK;
		}
		TuxGuitar.instance().loadCursor(SWT.CURSOR_WAIT);
		new Thread(new Runnable() {
			public void run() {
				if(!TuxGuitar.isDisposed()){
					FileActionUtils.save(fileName);
					TuxGuitar.instance().loadCursor(SWT.CURSOR_ARROW);
					ActionLock.unlock();
				}
			}
		}).start();
		
		return 0;
	}
}
