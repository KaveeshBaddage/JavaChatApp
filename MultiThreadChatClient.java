import java.io.DataInputStream;
import java.io.PrintSteram;
import java.io.BufferReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.java.net.Socket;
import java.net.UnknownHostException;

public class MultiThreadChatClient implements Runnable{

	private static Socket clientSocket = null;
	private static PrintStream os = null;
	private static DataInputStream is = null;
	private static BufferdReader inputLine = null;
	private static boolean closed = false;

	public static void public static void main(String[] args) {

		int portNumber = 5000;
		String host = "localhost";

		if(args.length < 2){
			System.out.println("Usage:java MultiThreadChatClient <host> <portNumber>\n" + "Now using host=" + host + ",port numbers =" + portNumber);
		}else{
			host = arg[0];
			portNumber = Integer.valueOf(args[1]).IntValue(); 
		}

		try{
			clientSocket = new Socket(host,portNumber);
			inputLine = new Bufferedreader(new inputStreamReader(System.in));
			os = new PrintStream (clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
		}catch(UnknownHostException e){
			System.err.println("Don't know about host " + host);
		}catch(IOException e){
			System.err.println("Couldn't get I/O for the connection to the host"+ host);
		}

		if(clientSocket != null && os != null && is != null){
			try{
				new Thread(new MultiThreadChatClient()).start();
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
		String responseLine;
		try{
			while((responseLine =  is.readLine())!=null){
				System.out.println(responseLine);
			}
			closed = true;
		}catch(IOException e){
			System.err.println("IOException: " + e);
		}
	}

}