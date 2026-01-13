package KnockKnock;



import java.io.*;
import java.net.*;

public class KKMultiServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);
        System.out.println("Servidor de Chistes iniciado en puerto 4444...");

        while (true) {
            // Aceptamos cliente y lanzamos un hilo
            new Thread(new KKClientHandler(serverSocket.accept())).start();
        }
    }

    // Hilo para atender a un cliente
    private static class KKClientHandler implements Runnable {
        private Socket clientSocket;

        public KKClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine, outputLine;

                // 1. INSTANCIAMOS EL PROTOCOLO (Cada cliente tiene su propia copia/estado)
                KnockKnockProtocol kkp = new KnockKnockProtocol();

                // 2. Pedimos la primera frase al protocolo (el "Knock Knock" inicial)
                outputLine = kkp.processInput(null);
                out.println(outputLine);

                // 3. Bucle de conversación
                while ((inputLine = in.readLine()) != null) {

                    // Le damos lo que dijo el usuario al protocolo
                    outputLine = kkp.processInput(inputLine);

                    // Enviamos la respuesta del protocolo al usuario
                    out.println(outputLine);

                    // Si el protocolo dice "Bye", cortamos
                    if (outputLine.equals("Bye."))
                        break;
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}