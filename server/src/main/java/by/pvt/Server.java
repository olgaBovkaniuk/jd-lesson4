package by.pvt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static Logger log = Logger.getLogger("by.pvt.Server");

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(3036)) {
            ExecutorService executorService = Executors.newCachedThreadPool();
            log.info("Waiting for the client request");
            do {
                Socket socket = serverSocket.accept();
                DataInputStream dataInputStream =
                        new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream =
                        new DataOutputStream(socket.getOutputStream());
                executorService.submit(() -> {
                    try {
                        String message = "";
                        while (!"END".equalsIgnoreCase(message)) {
                            message = dataInputStream.readUTF();
                            log.info("Message received: " + message);
                            dataOutputStream.writeUTF("Hi client, server received your message: " + message);
                            dataOutputStream.flush();
                            log.info("Waiting for the client request");
                        }
                        log.info("Server has been closed");
                        socket.close();
                        dataInputStream.close();
                        dataOutputStream.close();
                    } catch (IOException e) {
                        log.log(Level.SEVERE, e.getMessage(), e);
                    }
                });
            } while (true);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
