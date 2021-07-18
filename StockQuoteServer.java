package socket;

import java.io.*;
import java.net.*;
public class StockQuoteServer {
 public static void main(java.lang.String[] args) {
	 ServerSocket serverSocket=null;
	 Socket client = null;

	 try {
		 serverSocket = new ServerSocket(3000);
		 client = serverSocket.accept();
		 System.out.println("Waiting for a quote request...");

	 }
	 catch(Exception e) {e.printStackTrace();}

	 
	 try(  BufferedReader inbound =new BufferedReader(new  InputStreamReader(client.getInputStream()));	   
			 OutputStream outbound = client.getOutputStream(); )
	 {
		 while (true)
		 {	 String symbol = inbound.readLine();
		 if(symbol.equals("exit")) {
			 outbound.write(("exit").getBytes());

			 System.out.println("Shut Down the server");
			 System.exit(-1);
		 }

		 //Generate a random stock price
		 String price= (new Double(Math.random()*100)).toString();
		 outbound.write(("\n The price of "+symbol+ " is " + price + "\n").getBytes());

		 System.out.println("Request for " + symbol + " has been processed - the price of " + symbol+
				 " is " + price + "\n" );
		 }

	 }
	 catch (Exception ioe) {
		 System.out.println("Error in Server: " + ioe);
	 }  
 }
}

