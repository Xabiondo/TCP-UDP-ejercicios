package ChatPersonas;

import java.io.*;
import java.net.Socket;

public class HiloEscucha extends Thread {
    private Socket socket;
    private String nombreDelOtro; // Guardamos el nombre de quien nos habla

    public HiloEscucha(Socket socket, String nombreDelOtro) {
        this.socket = socket;
        this.nombreDelOtro = nombreDelOtro;
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mensaje;

            while ((mensaje = entrada.readLine()) != null) {
                // Aquí usamos el nombre del otro para decorar el mensaje
                System.out.println("[" + nombreDelOtro + "]: " + mensaje);
            }
        } catch (IOException e) {
            System.out.println("La conexión con " + nombreDelOtro + " ha terminado.");
        }
    }
}