package de.socketTest;

import de.socketTest.socket.SocketThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

@SpringBootApplication
public class SocketTestApplication {

	static int socketPort = 8082;

	public static void main(String[] args) {
		SpringApplication.run(SocketTestApplication.class, args);
		printIP();
		runSocketServer();
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

	public static void runSocketServer() {
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
			new SocketThread(clientSocket).start();
		}
	}

}
