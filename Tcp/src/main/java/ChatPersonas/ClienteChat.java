package ChatPersonas;

import ChatPersonas.HiloEscucha;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteChat {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 6000;
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("--- CLIENTE DE CHAT ---");

            // 1. Pedir nombre localmente
            System.out.print("Introduce tu nombre de usuario: ");
            String miNombre = scanner.nextLine();

            System.out.println("Conectando al servidor...");
            Socket socket = new Socket(host, puerto);

            // Configurar flujos
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 2. INTERCAMBIO DE NOMBRES
            // Envio mi nombre
            out.println(miNombre);
            // Espero el nombre del servidor
            String nombreServidor = in.readLine();

            System.out.println(">>> ¡Conectado con " + nombreServidor + "! <<<");

            // 3. INICIAR HILO DE ESCUCHA
            HiloEscucha hilo = new HiloEscucha(socket, nombreServidor);
            hilo.start();

            // 4. BUCLE DE ENVÍO
            while (true) {
                String mensaje = scanner.nextLine();
                out.println(mensaje);

                if (mensaje.equalsIgnoreCase("bye")) {
                    break;
                }
            }
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}