package br.com.cristianfava.pongserver.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ConnectionAcceptor extends Thread {
    private final ServerSocket serverSocket;
    private final List<Client> clients;

    public ConnectionAcceptor(String port, List<Client> clients) throws Exception {
        if (port == null || port.trim().isEmpty()) {
            throw new Exception("Port is missing");
        }
        if (clients == null) {
            throw new Exception("Clients list is missing");
        }

        try {
            int portNumber = Integer.parseInt(port);
            this.serverSocket = new ServerSocket(portNumber);
        } catch (Exception e) {
            throw new Exception("Invalid port number or port already in use");
        }

        this.clients = clients;
    }

    public ConnectionAcceptor(int port, List<Client> clients) throws Exception {
        if (clients == null) {
            throw new Exception("Clients list is missing");
        }

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            throw new Exception("Invalid port number or port already in use");
        }

        this.clients = clients;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = this.serverSocket.accept();

                ConnectionSupervisor supervisor = new ConnectionSupervisor(socket, this.clients);
                supervisor.start();
            } catch (Exception e) {
                break;
            }
        }
    }
}