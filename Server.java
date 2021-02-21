//read the message what users are sending and broadcast the messages
//before sending message we would get the connection request
//using multithread we have to connect the users with server

import java.net.*;
import java.io.*; // import for Buffer
import java.util.*; // import for Vector()


public class Server implements Runnable{
	
	Socket socket;
	
	public static Vector client = new Vector(); //to store multiple client
	
	public Server(Socket socket) {
		try {
			this.socket = socket;
		}catch(Exception e) {}
		
	}
	
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  //to read the message from the socket
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); //to write the message through the socket
			
			client.add(writer);  //to add all the writers in the vector class
			
			while(true) {
				String data = reader.readLine().trim();
				System.out.println("Received " + data);
				
				for(int i = 0; i < client.size(); i = i + 1) {
					try {
						
						BufferedWriter bw = (BufferedWriter)client.get(i);
						bw.write(data);
						bw.write("\r\n");
						bw.flush();
						
						
					}catch(Exception e) {}
				}
				
			}
		}catch(Exception e) {}
	}
	
	public static void main(String[] args) throws Exception{
		ServerSocket s = new ServerSocket(2006);
		while(true) {   										//to response for infinite client users 
			Socket socket = s.accept();  						//to accepting the requests
			Server server = new Server(socket);
			Thread thread = new Thread(server);
			thread.start();
		}
	}

}
