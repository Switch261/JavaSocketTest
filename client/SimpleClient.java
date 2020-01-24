import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    private static  Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
	
	private static String client = "";
	private static String address = "";

    public static void main(String[] args) {
        address = args[0];
		client = args[1];
		String message = "";
		Scanner scanner = new Scanner(System.in);

		while(true) {
		    System.out.println("Wait for Chat Begin...");
            startConnection(address, 8082);
            sendMessageWithResponse(client);
            try {
                String resp = in.readLine();
                if(resp != null && resp.equals("CHAT START")) {
                    System.out.println("Chat started...");
                    System.out.print("Enter Text: ");
                    while(scanner.hasNextLine()) {
                        message = scanner.nextLine();
                        if(message.equals("EXIT")) {
                            stopConnection();
                            break;
                        }
                        else if(message.equals("EXIT COMMUNICATION")) {
                            stopConnection();
                            return;
                        }
                        else {
                            sendMessageWithResponse(message);
                            System.out.print("Enter Text: ");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void startConnection(String ip, int port) {
        System.out.print("Establishing Communication ... ");
        boolean connectionEstablished = false;
        while(!connectionEstablished) {
            try {
                clientSocket = new Socket(ip, port);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                connectionEstablished = true;
            } catch (IOException e) {
                // Connection refused: wait 1 second and retry
                //e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                //
                connectionEstablished = false;
            }
        }
        System.out.println(" Communication established");

    }

    public static void sendMessage(String msg) {
        out.println(msg);
    }
	
	public static void sendMessageWithResponse(String msg) {
        boolean sendSuccessful = false;
        while(!sendSuccessful) {
            out.println(msg);
            String resp = null;
            try {
                resp = in.readLine();
                if(resp != null && (resp.equals("RECEIVED") || resp.equals("CHAT START"))) {
                    System.out.println("Successfull send: " + msg);
                    sendSuccessful = true;
                }
                else {
                    System.out.println("Faild to send: " + msg);
                    sendSuccessful = false;
                }
            } catch (IOException e) {
                System.out.println("Faild to send caoused by exception: " + msg);
                e.printStackTrace();
                sendSuccessful = false;
                stopConnection();
                startConnection(address, 8082);
                sendMessageWithResponse(client);
                try {
                    String chatInit = in.readLine();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
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