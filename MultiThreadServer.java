import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadServer implements Runnable{

	Socket csocket;
	static HashMap<Integer,Socket> hm = new HashMap<Integer,Socket>(); //Store each client socket in hashMap
	static int k = 1;
	MultiThreadServer(Socket csocket){
		this.csocket = csocket;
	}

	public static void main(String args[]) throws Exception{
		ServerSocket ssock = new ServerSocket(5000);
		System.out.println("Listening to the clients");
		while(true){
			PrintWriter out;
			Socket sock = ssock.accept(); //Accept client socket
			MultiThreadServer ser = new MultiThreadServer(sock); //Pass sock to constructor of MultiThreadServer class
 			new Thread(ser).start(); //start the thread
			hm.put(k,sock);  //add sockets into hash map
			System.out.println("Server is connected with client" + k); // print number of connected clients
			Socket ser1 = hm.get(k);     // get the reciever's socket from hashMap
			out = new PrintWriter(ser1.getOutputStream(),true); //Write the message on reciever's socket 
			out.println("This Client's ID is " + k); 
			out.flush();
			k++;
		}
	}

	public void run(){
		try{
			PrintWriter out;
			BufferedReader in = new BufferedReader(new InputStreamReader(csocket.getInputStream())); //to read message from socket
			String inputLine;
			String j = "sendmsg"; // to Send a message
			String l = "to";
			String t = "sendtoall"; // to send messages to everyone
			String m = "calculate"; // to do the calculation

			while ((inputLine = in.readLine())!= null){
				String a[] = inputLine.split(" ");     //Split client input using spaces and check a[0]='sendmsg' and a[2]='to'
				if(a[0].equals(j) && a[2].equals(l)){
					int id = Integer.parseInt(a[3]);   // identify the receiver from client's input
					if(hm.containsKey(id)){           //Check hashMap is that reciever in ashMap 
						Socket ser1 = hm.get(id);     // get the reciever's socket from hashMap
						out = new PrintWriter(ser1.getOutputStream(),true); //Write the message on reciever's socket 
						out.println(a[1]); 
						out.flush();
						System.out.println("Connected two clients and message is sent");

					}
					else{
						out = new PrintWriter(csocket.getOutputStream(),true); //If specified reciever is not hashMap it says client is offline
						out.println("Specified client is Offline");
						out.flush();
						System.out.println("Couldn't connect clients");
					}
				}
				else if (a[0].equals(m)) { //If clint needs to do a clculation
					int operand1 = Integer.parseInt(a[1]);   // Get the operand1
					int operand2 = Integer.parseInt(a[3]);   // Get the operand2
					int id = Integer.parseInt(a[5]);   // identify the receiver from client's input
					float answer = 0;
					System.out.println("calculated " + operand1 + a[2] + operand2 + " for client" + a[5] + " and sent answer to "+ " client" + a[5]);
					if(a[2].equals("+") && a[4].equals(l)){ // Call addition service on server
						 answer = operand1 + operand2;    
					}
					else if(a[2].equals("-") && a[4].equals(l)){ // Call substraction service on server
						answer = operand1 - operand2;

					}
					else if(a[2].equals("*") && a[4].equals(l)){ // Call multiplication service on server
						answer = operand1 * operand2;
					}
					else if(a[2].equals("/") && a[4].equals(l)){ // Call divition service on server
						answer = operand1 / operand2;
					}
						if(hm.containsKey(id)){           //Check hashMap is that reciever in ashMap 
							Socket ser1 = hm.get(id);     // get the reciever's socket from hashMap
							out = new PrintWriter(ser1.getOutputStream(),true); //Write the message on reciever's socket 
							out.println(operand1 + a[2] + operand2 + "="+ answer); 
							out.flush();

						}
						else{
							out = new PrintWriter(csocket.getOutputStream(),true); //If specified reciever is not hashMap it says client is offline
							out.println("Specified client is Offline");
							out.flush();
							System.out.println("Couldn't connect clients");
						}
				}
				else if (a[0].equals(t)) { //If clint needs to send a message to everyone
					System.out.println("Connected all clients and meassages are sent to every client ");
					for(int h =1;h<=hm.size();h++){
						Socket ser1 = hm.get(h);
						out = new PrintWriter(ser1.getOutputStream(),true);
						out.println(a[1]);
						out.flush();
					}
				}
				else{
					out = new PrintWriter(csocket.getOutputStream(),true); //If message if differ from defined message format
					out.println("Message is invalid. Please enter message in correct format");
					out.flush();
				}
			}

		}
		catch(IOException e){
			System.out.println(e);
		}

	}
}
