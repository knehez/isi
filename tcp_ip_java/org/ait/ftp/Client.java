package org.ait.ftp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataOutputStream dataOutputStream;
    String message;

    Client() {
    }

    void run() {
        try {
            // 1. try to connect to the socket: localhost:8080
            requestSocket = new Socket("localhost", 8080);
            // 2. Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            dataOutputStream = new DataOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());
            Scanner systemInput = new Scanner(System.in);
            // 3: communications
            do {
                try {
                    // get files list
                    message = (String) in.readObject();
                    System.out.println("server>" + message);

                    String userInput = systemInput.nextLine();
                    String[] userCommand = userInput.split(" ");

                    if (userCommand[0].equals("u")) {
                        sendMessage("u " + userCommand[1]);
                        sendFile("c:/projects/isi/tcp_ip_java/ftp_client_root/" + userCommand[1]);
                    }

                    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

                    // Reading data using readLine
                    String command = r.readLine();

                    sendMessage(command);

                } catch (Exception e) {
                    System.err.println("data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            // 4: close connection
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void sendFile(String path) {
        int bytes = 0;
        
        File file = new File(path);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            out.writeObject(file.length());
            out.flush();

            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytes);
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Client client = new Client();
        client.run();
    }
}