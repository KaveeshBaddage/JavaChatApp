import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiThreadChatClient implements Runnable{

	private static Socket clientSocket = null; //clientSocket
	private static PrintStream os = null;      //outputStream
	private static DataInputStream is = null;  //inputStream
	private static BufferedReader inputLine = null;
	private static boolean closed = false;

	public static void main(String[] args) {

		int portNumber = 5000;
		String host = "localhost";

		if(args.length < 2){
			System.out.println("Usage:java MultiThreadChatClient <host> <portNumber>\n" + "Now using host=" + host + ",port numbers =" + portNumber);
		}else{
			host = args[0];
			portNumber = Integer.valueOf(args[1]).intValue(); 
		}

		try{
			clientSocket = new Socket(host,portNumber); //intialize client socket
			inputLine = new BufferedReader(new InputStreamReader(System.in)); //read user input
			os = new PrintStream (clientSocket.getOutputStream());  //write Output stream to socket
			is = new DataInputStream(clientSocket.getInputStream()); //Read from Socket
		}catch(UnknownHostException e){
			System.err.println("Don't know about host " + host);
		}catch(IOException e){
			System.err.println("Couldn't get I/O for the connection to the host"+ host);
		}

		if(clientSocket != null && os != null && is != null){
			try{
				new Thread(new MultiThreadChatClient()).start(); // Create a thread to read from server
				while(!closed){
				os.println(inputLine.readLine());
				}
				os.close();
				is.close();
				clientSocket.close();
			}catch(IOException e){
				System.err.println("IOException: " + e);
			}
		}


		
	}

	public void run(){
		String responseLine;  //Used to read from socket
		try{
			while((responseLine =  is.readLine())!=null){ //"is"-object of data input stream
				System.out.println(responseLine); // print recieved message
			}
			closed = true;
		}catch(IOException e){
			System.err.println("IOException: " + e);
		}
	}

}