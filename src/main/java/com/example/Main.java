package com.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 10.22.9.14
        try (Socket socket = new Socket("localhost", 3000)) {

            System.out.println("Connesso al server");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            boolean continua = true;
            String usernameInUso = "";
            Scanner scanner = new Scanner(System.in);
            usernameInUso = login(scanner, socket, in, out, usernameInUso);
            ReaderThread readerThread = new ReaderThread(in);
            String comando;
            System.out.println("Operazioni possibili:");
            System.out.println("1. lista utenti");
            System.out.println("2. chat privata");
            System.out.println("3. chat globale");
            System.out.println("4. uscire dalla chat");

            readerThread.start();
            while (continua) {

                comando = scanner.nextLine();
                String message = "";

                switch (comando) {
                    case "1":
                        out.writeBytes("UserList" + "\n");
                        break;

                    case "2":
                        message = scanner.nextLine();
                        if (!message.contains("-")) {
                            System.out.println("Inserire '-' tra l'utente a cui vuoi mandare il messaggio e il messaggio.");
                            break;
                        }
                        out.writeBytes("@" + "\n");
                        out.writeBytes(usernameInUso + "\n");
                        out.writeBytes(message + "\n");
                        break;

                    case "3":
                        out.writeBytes("GLOBAL" + "\n");
                        out.writeBytes(usernameInUso + "\n");
                        out.writeBytes(scanner.nextLine() + "\n");
                        break;

                    case "4":
                        out.writeBytes("exit" + "\n");
                        out.writeBytes(usernameInUso + "\n");
                        continua = false;
                        socket.close();
                        break;

                    default:
                        System.out.println("opzione non valida");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    private static String login(Scanner scanner, Socket socket, BufferedReader in, DataOutputStream out,
            String usernameInUso) throws IOException {

        boolean continua = true;

        while (continua) {
            System.out.println("Inserici l'username: ");
            String username = scanner.nextLine();
            out.writeBytes(username + "\n");
            String risposta = in.readLine();
            if (risposta.equals("si!")) {
                System.out.println("Nome gia' in uso. Riprova: ");
            } else if (risposta.equals("siS")) {
                System.out.println("Accesso effettuato");
                usernameInUso = username;
                continua = false;
            }
        }
        
        return usernameInUso;
    }
}