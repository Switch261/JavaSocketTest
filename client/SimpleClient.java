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

    // Client Name
	private static String client = "";
	// Server IP Address
	private static String address = "";

    public static void main(String[] args) {
        address = args[0];
		client = args[1];
		String message = "";
		// Console input
		Scanner scanner = new Scanner(System.in);

		while(true) {
		    // Establish Connumication with Server on Port 8082
            startConnection(address, 8082);
            // Sends the Server the name
            sendMessageWithResponse(client);
            System.out.println("Wait for Chat Begin...");
            try {
                // waits for Server to Start the Chat
                String resp = in.readLine();
                if(resp != null && resp.equals("CHAT START")) {
                    System.out.println("Chat started...");
                    System.out.print("Enter Text: ");
                    // waits for User input
                    while(scanner.hasNextLine()) {
                        message = scanner.nextLine();
                        if(message.equals("EXIT")) {
                            // Stops recent Connection to the Server
                            stopConnection();
                            break;
                        }
                        else if(message.equals("EXIT COMMUNICATION")) {
                            // Stops the whole client
                            stopConnection();
                            return;
                        }
                        else {
                            // Sends input as message
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
        // Tries to Connect to the Server as long as it has not accepted the connection
        while(!connectionEstablished) {
            try {
                clientSocket = new Socket(ip, port);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                connectionEstablished = true;
            } catch (IOException e) {
                // Connection refused: wait 1 second and retry
                // e.printStackTrace();
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

    // Send message and waits for client response thet the message was receive correctly
	public static void sendMessageWithResponse(String msg) {
        boolean sendSuccessful = false;
        // tries to send message as long as the client has not send a RECEIVED message
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
                // Some kind of Exception occured, restarting the connection
                // Messages read over the console will be send after Communication started again
                System.out.println("Faild to send " + msg + " caused by exception:");
                e.printStackTrace();
                sendSuccessful = false;
                stopConnection();
                startConnection(address, 8082);
                sendMessageWithResponse(client);
            }
        }

    }

    // Closing connection to the server
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