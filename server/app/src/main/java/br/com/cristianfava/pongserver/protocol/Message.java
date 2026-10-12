package br.com.cristianfava.pongserver.protocol;

import java.io.Serializable;

public class Message implements Serializable {
    private MessageType type;

    public MessageType getType() {
        return type;
    }
}
