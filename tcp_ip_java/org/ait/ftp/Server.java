package org.ait.ftp;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket providerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    String message;

    Server() {
    }

    void run() {
        try {
            // 1. create a socket server listening to port 8080
            providerSocket = new ServerSocket(8080);
            // 2. waiting for the connection (here we are waiting until next connection)
            connection = providerSocket.accept();
            // 3. create Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
            dataInputStream = new DataInputStream(connection.getInputStream());

            // 4. socket communication
            do {
                try {
                    // send files list
                    sendMessage(listFolder("c:/projects/isi/tcp_ip_java/ftp_server_root").toString());
                    // input user command from System.in
                    String userInput = (String) in.readObject();
                    
                    String[] userCommand = userInput.split(" ");
                    if (userCommand[0].equals("u")) {
                        receiveFile("c:/projects/isi/tcp_ip_java/ftp_server_root/" + userCommand[1]);
                    }
                    message = (String) in.readObject();
                    
                    System.out.println("client>" + message);
                    
                    if (message.equals("bye")) {
                        sendMessage("bye");
                    }
                } catch (ClassNotFoundException classnot) {
                    System.err.println("Data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            // 4: close connection
            try {
                in.close();
                out.close();
                providerSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    void sendMessage(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    StringBuilder listFolder(String folder) {
        StringBuilder buffer = new StringBuilder();
        File[] files = new File(folder).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                buffer.append(file.getName() + "\n");
            }
        }
        return buffer;
    }

    public void receiveFile(String fileName) {
        int bytes = 0;
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            Long size = (Long)in.readObject(); // read file size
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {         
                fileOutputStream.write(buffer, 0, bytes);
                size -= bytes; // read upto file size
            }
            // Here we received file
            System.out.println("File is Received");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Server server = new Server();
        while (true) {
            server.run();
        }
    }
}
