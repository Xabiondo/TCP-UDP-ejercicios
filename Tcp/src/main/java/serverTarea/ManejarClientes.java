package serverTarea;

import java.io.*;
import java.net.Socket;

public class ManejarClientes implements Runnable {
    private Socket clientSocket;

    public ManejarClientes(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        // Obtenemos el nombre del hilo actual para ver en los logs quién trabaja
        String threadName = Thread.currentThread().getName();
        System.out.println(">> [" + threadName + "] Atendiendo nuevo cliente: " + clientSocket.getInetAddress());

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {

            out.println("Mensaje del Servidor de Control Multihilo. Hilo asignado: " + threadName);

            String command;
            while ((command = in.readLine()) != null) {
                System.out.println("   [" + threadName + "] Comando recibido: " + command);

                if ("Apagar".equalsIgnoreCase(command)) {
                    out.println("Desconectando tu sesión...");
                    break; // Salimos del bucle SOLO para este cliente
                } else if ("Reiniciar".equalsIgnoreCase(command)) {
                    // Simulamos una tarea pesada
                    try { Thread.sleep(2000); } catch (InterruptedException e) {}
                    out.println("OK. Reiniciando Sistema operativo (Procesado por " + threadName + ")");
                } else {
                    out.println("ERROR. Comando no reconocido.");
                }
            }

        } catch (IOException e) {
            System.err.println("Error con el cliente: " + e.getMessage());
        } finally {
            try {
                System.out.println(">> [" + threadName + "] serverTarea.Cliente desconectado.");
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}