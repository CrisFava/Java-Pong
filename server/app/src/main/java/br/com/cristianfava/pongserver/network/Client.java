package br.com.cristianfava.pongserver.network;

import br.com.cristianfava.pongserver.protocol.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Client {
    private final Socket socket;
    private final Semaphore mutex = new Semaphore(1);
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private Message nextMessage;

    public Client(Socket socket) throws Exception {
        if (socket == null) {
            throw new NullPointerException("Socket is null");
        }

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        this.socket = socket;
    }

    public void send(Message message) throws Exception {
        this.out.writeObject(message);
        this.out.flush();
    }

    public Message receive() throws Exception {
        try {
            mutex.acquire();

            Message message;

            if (this.nextMessage == null) {
                message = (Message) this.in.readObject();
            } else {
                message = this.nextMessage;
                this.nextMessage = null;
            }

            return message;
        } finally {
            this.mutex.release();
        }
    }

    public Message peek() throws Exception {
        try {
            this.mutex.acquire();

            if (this.nextMessage == null) {
                this.nextMessage = (Message) this.in.readObject();
            }
        } finally {
             this.mutex.release();
        }

        return this.nextMessage;
    }

    public void close() throws Exception {
        this.in.close();
        this.out.close();
        this.socket.close();
    }
}
