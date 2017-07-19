import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadServer implements Runnable{

	Socket csocket;
	static HashMap<integer,Socket>hm=new HashMap<integer,Socket>();
	static int k = 1;
	MultiThreadServer(Socket csocket){
		this.csocket = csocket;
	}

	public static void main(String args[]) throws Exception{
		ServerSocket ssock = new ServerSocket(5000);
		System.out.println("Listening");
		while(true){
			Socket sock = ssock.accept();
			MultiThreadServer ser = new MultiThreadServer(sock);
			new Thread(ser).start();
			hm.put(k,sock);
			System.out.println("Connected to client" + k);
			k++;
		}
	}

	public void run(){
		try{
			PrintWriter out;
			BufferedReader in = new BufferedReader(new inputStreamReader(csocket.getinputStream()));
			String inputline;
			String j = "sending";
			String l = "to";
			String t = "sendtoall";

			while ((inputLine = in.readLine())!= null){
				String a[] = inputLine.split("");
				if(a[0].equals(j) && a[2].equals(l)){
					int id = integer.parseInt(a[3]);
					if(hm.containsKey(id)){
						Socket ser1 = hm.get(id);
						out = new PrintWriter(ser1.getOutputStream(),true);
						out.pprintln(a[1]);
						out.flush();

					}
					else{
						out = new PrintWriter(csocket.getOutputStream(),true);
						out.println("User Offline");
						out.flush();
					}
				}
				else if (a[0].equals(t)) {
					for(int h =1;h<=hm.size();h++){
						Socket ser1 = hm.get(h);
						out = new PrintWriter(ser1.getOutputStream(),true);
						out.println(a[1]);
						out.flash();
					}
				}
				else{
					out = new PrintWriter(csocket.getOutputStream(),true);
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
