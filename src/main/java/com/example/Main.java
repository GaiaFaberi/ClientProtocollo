package com.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 10.22.9.14
        try (Socket socket = new Socket("192.168.1.149", 3000)) {

            System.out.println("Connesso al server");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            boolean continua = true;
            String usernameInUso = "";
            Scanner scanner = new Scanner(System.in);

            System.out.println("Hai già un account?: ");

            String scelta = scanner.nextLine();

            while (continua) {
                switch (scelta) {

                    case "no":
                        out.writeBytes("su?" + "\n");
                        register(scanner, socket, in, out);

                    case "si":
                        out.writeBytes("si?" + "\n");
                        usernameInUso = login(scanner, socket, in, out, usernameInUso);
                        continua = false;
                        break;

                    default:
                        System.out.println("scelta non disponibile");
                }
            }

            ReaderThread readerThread = new ReaderThread(in);

            String comando;
            System.out.println("Operazioni possibili:");
            System.out.println("1. lista utenti");
            System.out.println("2. chat privata");
            System.out.println("3. chat globale");
            System.out.println("4. uscire dalla chat");

            continua = true;
            readerThread.start();
            while (continua) {

                comando = scanner.nextLine();

                switch (comando) {
                    case "1":
                        out.writeBytes("UserList" + "\n");
                        break;

                    case "2":
                        out.writeBytes("@" + "\n");
                        out.writeBytes(usernameInUso + "\n");
                        out.writeBytes(scanner.nextLine() + "\n");
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

    private static void register(Scanner scanner, Socket socket, BufferedReader in, DataOutputStream out)
            throws IOException {
        boolean continua = true;
        if (in.readLine().equals("suC?")) {

            while (continua) {
                System.out.println("Inserisci il nome utente: ");
                String username = scanner.nextLine();
                System.out.println("Inserisci la password: ");
                String password = scanner.nextLine();

                out.writeBytes(username + "\n");
                out.writeBytes(password + "\n");

                String risposta = in.readLine();
                if ("su!".equals(risposta)) {
                    System.out.println("Errore: username già in uso.");
                } else if ("suS".equals(risposta)) {
                    System.out.println("Registrazione avvenuta con successo.");
                    continua = false;
                }
            }
        }
    }

    private static String login(Scanner scanner, Socket socket, BufferedReader in, DataOutputStream out,
            String usernameInUso) throws IOException {

        boolean continua = true;
        if (in.readLine().equals("siC?")) {
            while (continua) {
                System.out.println("Inserici l'username: ");
                String username = scanner.nextLine();
                System.out.println("Inserisci la password: ");
                String password = scanner.nextLine();

                usernameInUso = username;

                out.writeBytes(username + "\n");
                out.writeBytes(password + "\n");

                String risposta = in.readLine();
                if ("si!".equals(risposta)) {
                    System.out.println("Credenziali errate. Riprova: ");
                } else if ("siS".equals(risposta)) {
                    System.out.println("Accesso effettuato");
                    continua = false;
                }
            }
        }
        return usernameInUso;
    }
}