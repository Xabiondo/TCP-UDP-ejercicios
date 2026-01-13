package TCPMultithread;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteCalculadora {

    ClienteCalculadora() throws IOException {
        Socket puertoServer = new Socket("localhost" , 2020);

        BufferedReader input = new BufferedReader(new InputStreamReader(puertoServer.getInputStream()));
        PrintWriter output = new PrintWriter(new OutputStreamWriter(puertoServer.getOutputStream()),true);
        Scanner sc = new Scanner(System.in);

        String mensaje = input.readLine();
        System.out.println(mensaje);

        while(true) {
            System.out.println("Escribe...");
            String peticionAlServidor = sc.nextLine() ;
            output.println(peticionAlServidor);

            if (peticionAlServidor.equalsIgnoreCase("bye")) {
                String despedida = input.readLine();
                System.out.println("Servidor: " + despedida);
                break;
            }
            String respuestaServer = input.readLine();
            System.out.println("Servidor: " + respuestaServer);

        }
        sc.close();
        puertoServer.close();
    }
    public static void main(String[] args) throws IOException {
        new ClienteCalculadora();
    }


}
