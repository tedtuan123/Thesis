package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;
import java.io.IOException;
import java.io.FileOutputStream;
import java.math.BigInteger;
public  class SerialTest implements SerialPortEventListener {
	
SerialPort serialPort;
    /** The port we're normally going to use. */
private static final String PORT_NAMES[] = {"COM3", // Mac OS X
        									"/dev/ttyUSB0", // Linux
        									"COM7", // Windows
        									};

public BufferedReader input;
 OutputStream output;
private static final int TIME_OUT = 2000;
private static final int DATA_RATE = 9600;

public static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
    }
    return data;
}
public void initialize() {
    
    CommPortIdentifier portId = null;
    Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
    //First, Find an instance of serial port as set in PORT_NAMES.
    while (portEnum.hasMoreElements()) {
        CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
        for (String portName : PORT_NAMES) {
            if (currPortId.getName().equals(portName)) {
                portId = currPortId;
                
                break;
                
            }
        }
    }
    if (portId == null) {
        System.out.println("Could not find COM port.");
        return;
    }

    try {
        serialPort = (SerialPort) portId.open(this.getClass().getName(),
                TIME_OUT);
        serialPort.setSerialPortParams(DATA_RATE,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        // open the streams
        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        output = serialPort.getOutputStream();
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
    } catch (Exception e) {
        System.err.println(e.toString());
    }
}


public synchronized void close() {
    if (serialPort != null) {
        serialPort.removeEventListener();
        serialPort.close();
    }
}

public synchronized void serialEvent(SerialPortEvent oEvent) {
    if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
        try {
            String inputLine=null;
            if (input.ready()) {
                inputLine = input.readLine();
                String [] chunks = inputLine.split(",");
                System.out.println(inputLine);
                System.out.println(chunks[0] + "\t" + chunks[1] + "\t" + chunks[2] + "\t");
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
        synchronized (this) {
            this.notify();
        }
    }
    // Ignore all the other eventTypes, but you should consider the other ones.
}
public synchronized void sendSerial( byte[] data) throws IOException 
{
        try {
          output.write(data);  
        }
    catch (Exception e) {
            System.err.println(e.toString());
        }
}
public synchronized void getSerial( String data) throws IOException
{try{
     String inputLine=null;
            if (input.ready()) {
                inputLine = input.readLine();
                
                String [] chunks = inputLine.split(",");
                
                System.out.println(inputLine);
                System.out.println(chunks[0] + "\t" + chunks[1] + "\t" + chunks[2] + "\t");
}
}
catch (Exception e) {
            System.err.println(e.toString());
        }
}
public String toHex(String arg) {
    return String.format("%x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    
}
}
