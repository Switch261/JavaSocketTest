package de.socketTest;

import de.socketTest.database.repository.ClientRepository;
import de.socketTest.socket.SocketService;
import de.socketTest.socket.SocketThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

@SpringBootApplication
public class SocketTestApplication {

	static int socketPort = 8082;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new
				SpringApplicationBuilder(SocketTestApplication.class).run();
		printIP();
		runSocketServer(ctx);
	}

	public static void printIP() {
		// Print IP Address
		System.out.println("/////////Web-Server///////");
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			System.out.println("IP Adresse: " + localHost.getHostAddress());
			System.out.println("Port: 8081 for Webserver");
			System.out.println("Port: " + socketPort + " for Webserver");
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}
		System.out.println("//////////////////////////");
	}

	// waits for incoming connections and starts new SocketThread for each
	public static void runSocketServer(ConfigurableApplicationContext ctx) {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;

		try {
			serverSocket = new ServerSocket(socketPort);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("I/O error: " + e);
			}
			// new thread for a client
			new SocketThread(clientSocket, ctx.getBean(SocketService.class)).start();
		}
	}

}
