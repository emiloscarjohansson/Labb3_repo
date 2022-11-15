import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Objects;


class RPS extends JFrame implements ActionListener {
    Gameboard myboard, computersboard;
    int counter = 0; // To count ONE ... TWO  and on THREE you play
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    JButton closebutton;


    RPS () {
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	initializeClient();
	closebutton = new JButton("Close");
	myboard = new Gameboard("Myself", this); // Must be changed
	computersboard = new Gameboard("Computer");
	JPanel boards = new JPanel();
	boards.setLayout(new GridLayout(1,2));
	boards.add(myboard);
	boards.add(computersboard);
	add(boards, BorderLayout.CENTER);
	add(closebutton, BorderLayout.SOUTH);
	this.closebutton.addActionListener(e -> System.exit(0));
	setSize(350, 650);
	setVisible(true);

    }

	private void initializeClient(){
		try {
			socket = new Socket("localhost",4713);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream());
		}
		catch (IOException e) {
			System.out.println("Något gick fel: "+e);
		}
	}
	public void actionPerformed(ActionEvent ae) {
		this.counter += 1;
		if (this.counter == 1){
			this.myboard.setLower("1");
			this.myboard.resetColor();
			this.myboard.setUpper("");
			this.computersboard.setLower("1");
			this.computersboard.resetColor();
			this.computersboard.setUpper("");
		}
		else if(this.counter == 2){
			this.myboard.setLower("2");
			this.computersboard.setLower("2");
		}
		else{
			try {
				String my_choice = ae.getActionCommand();
				out.println("");
				String computers_choice = in.readLine();
				this.myboard.markPlayed(my_choice);
				this.computersboard.markPlayed(computers_choice);
				this.myboard.setUpper(my_choice);
				this.computersboard.setUpper(computers_choice);
				determineOutcome(my_choice, computers_choice);
			} catch (IOException e) {
				System.out.println("Något gick fel: "+e);
			}
		}
	}

	private void determineOutcome(String player_choice, String server_choice) {
		HashMap<String, Integer> hashmap = new HashMap<>();
		hashmap.put("ROCK", 0);
		hashmap.put("PAPER", 1);
		hashmap.put("SCISSORS", 2);
		int outcomeDiff = hashmap.get(player_choice) - hashmap.get(server_choice);

		if (outcomeDiff == 0){
			this.myboard.setLower("Draw");
			this.computersboard.setLower("Draw");
		}
		else if ((outcomeDiff == 1 ) || (outcomeDiff == -2)){
			this.myboard.setLower("Won");
			this.computersboard.setLower("Lost");
			this.myboard.wins();
		}
		else{
			this.myboard.setLower("Lost");
			this.computersboard.setLower("Won");
			this.computersboard.wins();
		}

		this.counter = 0;
	}
    public static void main (String[] u) {
	new RPS();
    }
}


