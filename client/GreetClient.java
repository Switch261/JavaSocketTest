import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GreetClient {
    private static  Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
	
	private static String client = "";

    public static void main(String[] args) {
        startConnection(args[0], 8082);
		client = args[1];
        //sendMessageWithResponse("Hello");
        //sendMessageWithResponse("blub");
		String message = "";
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Text: ");
		while(scanner.hasNext()) {
			message = scanner.next();
			if(message.equals("EXIT")) {
				stopConnection();
				return;
			}
			sendMessageWithResponse(message);
			System.out.print("Enter Text: ");
		}
		System.out.println();
        stopConnection();
    }

    public static void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void sendMessage(String msg) {
        out.println(msg);
    }
	
	public static void sendMessageWithResponse(String msg) {
        out.println(client + ":" + msg);
        String resp = null;
        try {
            resp = in.readLine();
            if(resp != null && resp.equals("RECEIVED")) {
				System.out.println("Successfull send: " + msg);
			}
			else {
				System.out.println("Faild to send: " + msg);
			}
        } catch (IOException e) {
			System.out.println("Faild to send: " + msg);
            e.printStackTrace();
        }
    }

    public static void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		System.out.println("Closing Connection");
    }
}