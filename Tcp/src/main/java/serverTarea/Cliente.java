package serverTarea;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        System.out.println("serverTarea.Cliente TCP intenta conectarse a" + host + ":" + port);

        try (Socket socket = new Socket(host, port)) {

            System.out.println("serverTarea.Cliente conectado.");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Canal para leer datos del servidor
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            // Canal para leer datos del usuario que escribe en la consola!
            BufferedReader usuarioSysIN = new BufferedReader(new InputStreamReader(System.in));
            String comando_entrada;
            System.out.println("Escribe un comando (REINICIAR, APAGAR):");
            // Leemos de la consola del usuario
            while ((comando_entrada = usuarioSysIN.readLine()) != null) {
                out.println(comando_entrada);
                String respuesta = in.readLine();
                System.out.println("Respuesta del servidor: " + respuesta);
                if (comando_entrada.equalsIgnoreCase("APAGAR")) {
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("No se conoce el host: " + host);
        } catch (IOException e) {
            System.err.println("No se pudo conectar: " + e.getMessage());
        }
    }
}