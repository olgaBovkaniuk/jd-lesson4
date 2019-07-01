package by.pvt;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static Logger log = Logger.getLogger("by.pvt.Client");

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 3036)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DataInputStream dataInputStream =
                    new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream =
                    new DataOutputStream(socket.getOutputStream());
            log.info("Please, enter a message: ");
            String message = "";
            while (!"END".equalsIgnoreCase(message)) {
                message = reader.readLine();
                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();
                String serverMessage = dataInputStream.readUTF();
                log.info(serverMessage);
            }
            log.info("Client has been closed");
            dataInputStream.close();
            dataOutputStream.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
