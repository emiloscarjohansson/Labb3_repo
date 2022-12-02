package Labb3;

public class Main {
    try {
        // localhost är alias för IP-adress för den lokala datorn d.v.s. den datorn 
        // du kör detta program (vilket i detta fall är samma dator som serverprogrammet körs på)
        Socket socket=new Socket("localhost",4713);
        BufferedReader in=new BufferedReader
            (new InputStreamReader(socket.getInputStream()));
        PrintWriter ut=new PrintWriter(socket.getOutputStream());
        ut.println("Charlotta");
        ut.flush();
        System.out.println(in.readLine());
    }
    // Om något går fel så skrivs ett felmeddelande ut
    catch (Exception e) {
        System.out.println("Något gick fel: "+e);
    }
}
