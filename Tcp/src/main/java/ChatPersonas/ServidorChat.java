package ChatPersonas;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServidorChat {
    public static void main(String[] args) {
        int puerto = 6000;
        Scanner scanner = new Scanner(System.in);

        try {
            ServerSocket serverSocket = new ServerSocket(puerto);
            System.out.println("--- SERVIDOR DE CHAT ---");

            // 1. Pedir nombre localmente
            System.out.print("Introduce tu nombre de usuario: ");
            String miNombre = scanner.nextLine();

            System.out.println("Esperando a que alguien se conecte...");
            Socket socket = serverSocket.accept();

            // Configurar flujos
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 2. INTERCAMBIO DE NOMBRES (HANDSHAKE)
            // Envio mi nombre
            out.println(miNombre);
            // Espero el nombre del otro
            String nombreCliente = in.readLine();

            System.out.println(">>> ¡Conectado con " + nombreCliente + "! <<<");

            // 3. INICIAR HILO DE ESCUCHA (Le pasamos el nombre del otro para que lo muestre)
            HiloEscucha hilo = new HiloEscucha(socket, nombreCliente);
            hilo.start();

            // 4. BUCLE DE ENVÍO (Hilo principal)
            while (true) {
                String mensaje = scanner.nextLine();
                // Opcional: Escribir en mi propia pantalla lo que envié para que quede bonito
                // System.out.println("[" + miNombre + "]: " + mensaje); 

                out.println(mensaje);

                if (mensaje.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}