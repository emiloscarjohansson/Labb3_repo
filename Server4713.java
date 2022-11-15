/*******************************************
OBS! No swedish letters in this program.
STEN, SAX and PASE is played.
STEN = ROCK, SAX = SCISSORS, PASE = PAPER
*******************************************/
import java.net.*;
import java.io.*;
import java.util.*;
public class Server4713 {
    public static void main( String[] args) {
	try {
	    ServerSocket sock = new ServerSocket(4713,100);
	    while (true) {
			new ClientHandler(sock.accept()).start();
		}
	}
	catch(IOException e){
	    System.err.println(e);
	}
    }
} 
// Hello
class ClientHandler extends Thread {
    static int numberOfPls=0;
    BufferedReader in;
    PrintWriter out;
    public ClientHandler(Socket socket){
	try {
	    in = new BufferedReader(new InputStreamReader
				    (socket.getInputStream()));
	    out= new PrintWriter(socket.getOutputStream());
	}
	catch(IOException e) {System.err.println(e);
	}
    }
	@SuppressWarnings("InfiniteLoopStatement")
    public void run() {
	Random random=new Random();
	String[] hand={"ROCK","PAPER","SCISSORS"};
	try {
	    System.out.println((++numberOfPls)+ ": RPS client connected");
	    while(true) {
			//String input = in.readLine();
			out.println(hand[random.nextInt(3)]);
			out.flush();
	    }
	    //System.out.println("RPS client disconnected");
	    //numberOfPls--;
		//System.exit(0);
	}
        catch(Exception e) {
	    System.err.println(e);
	}
    }
}
