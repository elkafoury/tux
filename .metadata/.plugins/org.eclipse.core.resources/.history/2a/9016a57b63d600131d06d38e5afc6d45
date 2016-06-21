/*
 * Created on 30-nov-2005
 *
 */
package org.herac.tuxguitar.gui.editors.tab;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.herac.tuxguitar.gui.editors.TGPainter;

/**
 * @author julian
 *
 */
public class TablaturePaintListener implements PaintListener{
	private Tablature tablature;
	
	public TablaturePaintListener(Tablature tablature){
		this.tablature = tablature;
	}
	
	public void paintControl(PaintEvent e) {
		this.tablature.paintTablature( new TGPainter(e.gc) );
	}
}
