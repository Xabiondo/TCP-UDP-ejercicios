package Sensor;

import java.io.*;
import java.net.*;

public class ServidorGateway {

    private static volatile String ultimoDatoSensor = "Sin datos aún...";

    public static void main(String[] args) {

        Thread hiloSensor = new Thread(() -> {
            try {
                DatagramSocket socketUDP = new DatagramSocket(9876); // Puerto UDP
                byte[] buffer = new byte[1024];
                System.out.println("[UDP] Esperando sensores en puerto 9876...");

                while (true) {
                    DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                    socketUDP.receive(paquete);

                    // Actualizamos la variable compartida
                    String mensaje = new String(paquete.getData(), 0, paquete.getLength());
                    ultimoDatoSensor = mensaje;

                    System.out.println("[UDP] Dato actualizado: " + ultimoDatoSensor);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        hiloSensor.start(); // ¡Arrancamos el hilo UDP!


        // ---------------------------------------------------------
        // PARTE 2: EL SERVIDOR TCP PRINCIPAL (Para Clientes)
        // ---------------------------------------------------------
        int puertoTCP = 2020;
        try (ServerSocket serverSocket = new ServerSocket(puertoTCP)) {
            System.out.println("[TCP] Servidor listo para clientes en puerto 2020...");

            while (true) {
                Socket socketCliente = serverSocket.accept();

                // Creamos un hilo para atender a este cliente TCP
                new Thread(new ManejadorClienteTCP(socketCliente)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- CLASE INTERNA PARA ATENDER CLIENTES TCP ---
    static class ManejadorClienteTCP implements Runnable {
        private Socket socket;

        public ManejadorClienteTCP(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println("Bienvenido al Monitor. Escribe 'ver' para ver el sensor.");

                String linea;
                while ((linea = in.readLine()) != null) {

                    if (linea.equalsIgnoreCase("ver")) {
                        // LEEMOS LA VARIABLE QUE EL HILO UDP ESTÁ ACTUALIZANDO
                        out.println("Sensor actual: " + ultimoDatoSensor);
                    }
                    else if (linea.equalsIgnoreCase("bye")) {
                        break;
                    }
                    else {
                        out.println("Comando desconocido. Usa 'ver'.");
                    }
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}