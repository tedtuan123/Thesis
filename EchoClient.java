package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import com.example.SerialTest;
import java.io.InputStream;
import java.io.DataOutputStream; 
import java.math.BigInteger;
import java.io.ByteArrayInputStream;
import com.example.SerialTest;
public class EchoClient extends SerialTest{
  
    public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.err.println("Usage: java EchoClient <host name> <port number>");
      System.exit(1);
    }
    String hostName = args[0];
    int portNumber = Integer.parseInt(args[1]);
     SerialTest main = new SerialTest();
      main.initialize();
    try (Socket echoSocket = new Socket(hostName, portNumber);
            
      PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()))){
      String inputLine;
	  while ((inputLine = in.readLine()) != null) {
            System.out.println("Received message: " + inputLine );
            byte[] data=main.hexStringToByteArray(inputLine);
            main.sendSerial(data);
	    out.println(data);            
	  }
    }catch (UnknownHostException e) {
       System.err.println("Don't know about host " + hostName);
       System.exit(1);
  
    } 
  }
}

/*in.readline : read stream from server*
  out.println : write stream to s
*/
