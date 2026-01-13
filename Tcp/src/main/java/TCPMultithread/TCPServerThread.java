package TCPMultithread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class TCPServerThread extends Thread {

    private Socket socket;
    private ServerThreadTCP server;


    public TCPServerThread(Socket socket, ServerThreadTCP server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            int clientNumber = server.getClientNumber();
            System.out.println("El cliente es " + clientNumber);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream() ) , true);

            output.println("bienvenido, dime una operación");

            String operacion ;
            while((operacion = input.readLine() ) != null){
                if (operacion.equals("bye")){
                    output.println("adios amigo");

                    break;
                }
                System.out.println("Cliente " + clientNumber + " pide: " + operacion);
                String resultado = procesarOperacion(operacion);
                output.println("El calculo es " + resultado);

                System.out.println("Hilo trabajando con el cliente...");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String procesarOperacion(String entrada) {


        if (entrada == null || entrada.trim().isEmpty()) {
            return "Error: Comando vacío.";
        }

        try {

            String[] partes = entrada.trim().split("\\s+");


            if (partes.length != 3) {
                return "Error de formato. Usa: NUMERO OPERADOR NUMERO (Ej: 5 + 5)";
            }


            double num1 = Double.parseDouble(partes[0]);
            String operador = partes[1];
            double num2 = Double.parseDouble(partes[2]);
            double resultado = 0;

            // 4. ELEGIR LA OPERACIÓN
            switch (operador) {
                case "+":
                    resultado = num1 + num2;
                    break;
                case "-":
                    resultado = num1 - num2;
                    break;
                case "*":
                    resultado = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        return "Error: No se puede dividir por cero.";
                    }
                    resultado = num1 / num2;
                    break;
                default:
                    return "Error: Operador '" + operador + "' no reconocido. Usa +, -, *, /";
            }

            return String.valueOf(resultado);

        } catch (NumberFormatException e) {
            return "Error: Uno de los valores no es un número válido.";
        } catch (Exception e) {
            return "Error desconocido: " + e.getMessage();
        }
    }
}