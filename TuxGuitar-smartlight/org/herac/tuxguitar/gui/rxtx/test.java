package org.herac.tuxguitar.gui.rxtx;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

//import org.herac.tuxguitar.gui.util.MessageDialog;

import gnu.io.CommPortIdentifier;
import gnu.io.RXTXCommDriver;
public class test {
/**
* @param args
*/
	
	//for containing the ports that will be found
    private static List xports = new ArrayList();
    //map the port names to CommPortIdentifiers
    private static HashMap portMap = new HashMap();
	private static rxtxSerialCommunicator serial;
    
public static void main(String[] args) {
System.out.println("Starting Test..");
try{

	//searchForPorts();
	
	
	
	 serial = new rxtxSerialCommunicator();
	 xports=serial.getPorts();
	 serial.connect();
	
Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();
while(ports.hasMoreElements()){
System.out.println("found port:"+ports.nextElement().getName());
}


}catch(Exception ex){
ex.printStackTrace();
}catch(Error er){
er.printStackTrace();
}
System.exit(0);
}


//public static void fillPortList(){
//	Enumeration portsIds = CommPortIdentifier.getPortIdentifiers();
//
//    while (portsIds.hasMoreElements())
//    {
//        CommPortIdentifier curPort = (CommPortIdentifier)portsIds.nextElement();
//        if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
//        	ports.add(curPort.getName());
//        	portMap.put(curPort.getName(), curPort);
//        	System.out.println("filling port :"+curPort.getName());
//        }
//    }
//}
//
//public static void searchForPorts()
//{
//	String fp = File.pathSeparator;
//	String tmpl = "/dev/ttyACM0";
//	tmpl += fp + "/dev/ttyUSB0";
//	tmpl += fp + "/dev/ttyUSB1";
//	tmpl += fp + "/dev/ttyACM1";
//	// podria hacerse segun SO
//	// Acordarse de poner los puertos de windows y mac COM1..5 etc 
//	//XXX acordarse de sacar los pts! 
//	tmpl += fp + "/dev/pts/1";
//	tmpl += fp + "/dev/pts/6";
//	tmpl += fp + "/dev/pts/7";
//	tmpl += fp + "/dev/pts/8";
//	System.out.println("tmpl :"+tmpl);
//	System.clearProperty("gnu.io.rxtx.SerialPorts");
//	fillPortList();
//	//Ignora los puertos del SO si hacemos esto antes:
//	System.setProperty("gnu.io.rxtx.SerialPorts", tmpl);
//	System.out.println("got system property: "+System.getProperty("gnu.io.rxtx.SerialPorts"));	 
//	fillPortList();
//	
//}



}