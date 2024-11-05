package com.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeBytes("s" + "\n");
            String rispostaServer = in.readLine();
            if ("l?".equals(rispostaServer)) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Hai già un account?: ");
                String scelta = scanner.nextLine();

                if (scelta.equalsIgnoreCase("no")) {
                    out.writeBytes("su?" + "\n");
                    register(scanner, socket);
                } else {
                    out.writeBytes("si?" + "\n");
                    login(scanner, socket);
                }
                chat(scanner, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void register(Scanner scanner, Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeBytes("suC?" + "\n");
        System.out.println("Inserisci il nome utente: ");
        String username = scanner.nextLine();
        System.out.println("Inserisci la password: ");
        String password = scanner.nextLine();

        out.writeBytes(username + "\n");
        out.writeBytes(password + "\n");

        String risposta = in.readLine();
        if ("Su!".equals(risposta)) {
            System.out.println("Errore: username già in uso.");
            register(scanner, socket);
        } else if ("suS".equals(risposta)) {
            System.out.println("Registrazione avvenuta con successo.");
        }
    }

    private static void login(Scanner scanner, Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        out.writeBytes("siC?" + "\n");

        System.out.println("Inserici l'username: ");
        String username = scanner.nextLine();
        System.out.println("Inserisci la password: ");

        String password = scanner.nextLine();

        out.writeBytes(username + "\n");
        out.writeBytes(password + "\n");

        String risposta = in.readLine();
        if ("si!".equals(risposta)) {
            System.out.println("Credenziali errate. Riprova: ");
            login(scanner, socket);

        } else if ("siS".equals(risposta)) {
            System.out.println("Accesso effettuato");
        }
    }

    private static void chat(Scanner scanner, Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        Thread readerThread = new Thread(() -> {
            try {
                while (true) {
                    String messaggio = in.readLine();
                    System.out.println(messaggio);
                }
            } catch (IOException e) {
                System.out.println("Connessione chiusa");
            }
        });
        readerThread.start();

        while (true) {
            String messaggio = scanner.nextLine();
            if (messaggio.equalsIgnoreCase("UserList")) {
                out.writeBytes("UserList" + "\n");
            } else if (messaggio.startsWith("@")) {
                out.writeBytes(messaggio);
            } else {
                out.writeBytes("--GLOBAL" + messaggio);
            }
        }
    }
}