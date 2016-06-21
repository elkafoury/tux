/*
 * Created on 09-dic-2005
 *
 */
package org.herac.tuxguitar.gui.clipboard;

/**
 * @author julian
 *
 */
public class ClipBoard {
	private Transferable transferable;
	
	public ClipBoard(){
		this.transferable = null;
	}
	
	public void addTransferable(Transferable transferable){
		this.transferable = transferable;
	}
	
	public Transferable getTransferable(){
		return this.transferable;
	}
	
	public void insertTransfer() throws CannotInsertTransferException{
		if(this.isEmpty()){
			throw new CannotInsertTransferException();
		}
		this.transferable.insertTransfer();
	}
	
	public boolean isEmpty(){
		return (this.transferable == null);
	}
}