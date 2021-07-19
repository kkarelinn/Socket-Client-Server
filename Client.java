package socket;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

	static String SYMB;
	static Socket clientSocket = null;
	static String quote;

	static OutputStream outbound;
	static BufferedReader inbound;



	private static void connectServer() {
		try {
			clientSocket = new Socket("localhost", 3000);
			System.out.println(" I am  a Client: " + clientSocket);
			outbound = clientSocket.getOutputStream();
			inbound = new  BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		}
		catch(Exception e) {
			System.out.println("Failed to connect the server");
			System.exit(-1);
		}
	}
	
	private static void getSymbolPrice() {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));)
		{	
			System.out.println("Please, put the Stock symbol to get price");
			while(true) {
				SYMB = reader.readLine();
				if(SYMB.equals("q"))
				{	

					receivePrice("exit");
					System.out.println("Exit the programm");
					reader.close();
					System.exit(-1);
				}


				if(SYMB.equals(""))
					System.out.println("You no put any symbols");

				if(!SYMB.equals("")) {
					receivePrice(SYMB);
				}	

			}

		}
		catch (Exception e) {
			System.out.println("You have some problem with inputing symbols");
		}

	}
	
	private static void receivePrice(String sym) {

		try {

			outbound.write(  (sym+"\n").getBytes()   ) ;

			while (true){
				quote = inbound.readLine();
				if (quote.length() == 0) continue;
				if (quote.equals("exit")) {
					System.out.println("Server was Shat down");
					clientSocket.close();
					break;
				}
				System.out.println(quote);
				break;					
			}

		}
		catch (IOException ioe)		{System.out.println("You have some problem with server");}
	}

	public static void main(java.lang.String[] args) {
		connectServer();
		getSymbolPrice();
	}

}

