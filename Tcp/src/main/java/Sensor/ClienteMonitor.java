package Sensor;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteMonitor {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 2020);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in);

            // Leer bienvenida
            System.out.println(in.readLine());

            while (true) {
                System.out.print("Escribe comando ('ver' o 'bye'): ");
                String comando = sc.nextLine();

                out.println(comando);

                if (comando.equalsIgnoreCase("bye")) break;

                System.out.println("Servidor dice: " + in.readLine());
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}