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
		System.out.println("Listening");
		while(true){
			Socket sock = ssock.accept(); //Accept client socket
			MultiThreadServer ser = new MultiThreadServer(sock); //Pass sock to constructor of MultiThreadServer class
 			new Thread(ser).start(); //start the thread
			hm.put(k,sock);  //add sockets into hash map
			System.out.println("Connected to client" + k); // print number of connected clients
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

			while ((inputLine = in.readLine())!= null){
				String a[] = inputLine.split(" ");     //Split client input using spaces and check a[0]='sendmsg' and a[2]='to'
				if(a[0].equals(j) && a[2].equals(l)){
					int id = Integer.parseInt(a[3]);   // identify the receiver from client's input
					if(hm.containsKey(id)){           //Check hashMap is that reciever in ashMap 
						Socket ser1 = hm.get(id);     // get the reciever's socket from hashMap
						out = new PrintWriter(ser1.getOutputStream(),true); //Write the message on reciever's socket 
						out.println(a[1]); 
						out.flush();

					}
					else{
						out = new PrintWriter(csocket.getOutputStream(),true); //If specified reciever is not hashMap it says client is offline
						out.println("User Offline");
						out.flush();
					}
				}
				else if (a[0].equals(t)) { //If clint needs to send a message to everyone
					for(int h =1;h<=hm.size();h++){
						Socket ser1 = hm.get(h);
						out = new PrintWriter(ser1.getOutputStream(),true);
						out.println(a[1]);
						out.flush();
					}
				}
				else{
					out = new PrintWriter(csocket.getOutputStream(),true); //If message if differ from defined message format
					out.println("Invalid Message");
					out.flush();
				}
			}

		}
		catch(IOException e){
			System.out.println(e);
		}

	}
}
