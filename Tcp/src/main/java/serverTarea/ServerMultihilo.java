package serverTarea;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMultihilo {
    private int port;
    private ExecutorService threadPool;
    public ServerMultihilo(int port) {
        this.port = port;
        this.threadPool = Executors.newCachedThreadPool();
    }
    public void start() {
        System.out.println(">> [SERVER] Servidor - Multihilo iniciado en puerto " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(">> [SERVER] Conexión aceptada. Delegando a un hilo...");
                threadPool.execute(new ManejarClientes(clientSocket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public static void main(String[] args) {
        new ServerMultihilo(5000).start();
    }
}