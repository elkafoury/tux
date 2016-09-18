package org.herac.tuxguitar.gui.rxtx;

public class rxtxSettings {
	
	//public static final String DEFAULT_SERIAL_PORT = "/dev/ttyACM0";
	//public static final String DEFAULT_SERIAL_PORT = "COM4";
	public static final String DEFAULT_SERIAL_PORT ="/dev/rfcomm0";
	private String port;
	
	public rxtxSettings(){
		// por defecto podria ser segun SO
		this.port = DEFAULT_SERIAL_PORT;
		System.out.println("Setting ctor");
	}
	
	public String getPort() {
		return this.port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
}
