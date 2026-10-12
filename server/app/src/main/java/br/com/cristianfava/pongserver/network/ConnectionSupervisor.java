package br.com.cristianfava.pongserver.network;

import br.com.cristianfava.pongserver.protocol.Message;

import java.net.Socket;
import java.util.List;

public class ConnectionSupervisor extends Thread {
    private final Socket socket;
    private final List<Client> clients;
    private Client client;

    public ConnectionSupervisor(Socket socket, List<Client> clients) throws Exception {
        if (socket == null) {
            throw new Exception("Socket is missing");
        }
        if (clients == null) {
            throw new Exception("Clients list is missing");
        }

        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            this.client = new Client(this.socket);
        } catch (Exception e) {
            return;
        }

        synchronized (this.clients) {
            this.clients.add(this.client);
        }

        try {
            while (true) {
                Message message = this.client.receive();

                if (message == null) {
                    break;
                }

                // TODO: Adicionar o tratamento das mensagens do seu protocolo (ex.: Pong / Game State)
                // Exemplo de verificação de comando de saída:
                /*
                if (message instanceof ExitMessage) {
                    break;
                }
                */

                // Exemplo de envio de resposta ao cliente:
                // this.client.send(responseMessage);
            }
        } catch (Exception ignored) {
        } finally {
            synchronized (this.clients) {
                this.clients.remove(this.client);
            }
            try {
                this.client.close();
            } catch (Exception ignored) {
            }
        }
    }
}