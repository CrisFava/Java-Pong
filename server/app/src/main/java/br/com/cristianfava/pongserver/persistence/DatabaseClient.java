package br.com.cristianfava.pongserver.persistence;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public final class DatabaseClient implements AutoCloseable {
    private final MongoClient client;
    private final MongoDatabase database;

    private DatabaseClient(MongoClient client, MongoDatabase database) {
        this.client = client;
        this.database = database;
    }

    public static DatabaseClient connect() {
        String uri = requiredEnv("MONGODB_URI");
        String databaseName = requiredEnv("MONGODB_DATABASE");

        MongoClient client = MongoClients.create(uri);
        try {
            MongoDatabase database = client.getDatabase(databaseName);
            database.runCommand(new Document("ping", 1));

            return new DatabaseClient(client, database);
        } catch (RuntimeException exception) {
            try {
                client.close();
            } catch (RuntimeException closeException) {
                exception.addSuppressed(closeException);
            }
            throw exception;
        }
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoCollection<Document> getCollection(String name) {
        return database.getCollection(name);
    }

    @Override
    public void close() {
        client.close();
    }

    private static String requiredEnv(String name) {
        String value = System.getenv(name);

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Variavel de ambiente obrigatoria: " + name);
        }

        return value.trim();
    }
}
