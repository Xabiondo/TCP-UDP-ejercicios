package Ejercicio3URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerURL {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce la URL: ");

        String urlUsuario = sc.nextLine();

        try {
            URL url = new URL(urlUsuario);
            URLConnection conexion = url.openConnection();
            conexion.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader lector = new BufferedReader(
                    new InputStreamReader(conexion.getInputStream(), StandardCharsets.UTF_8));

            String linea;
            StringBuilder contenidoCompleto = new StringBuilder();


            while ((linea = lector.readLine()) != null) {
                contenidoCompleto.append(linea).append("\n"); // Añadimos salto de línea
            }
            lector.close();


            Pattern patron = Pattern.compile("(?i)<title>(.*?)</title>", Pattern.DOTALL);
            Matcher matcher = patron.matcher(contenidoCompleto.toString());

            if (matcher.find()) {
                System.out.println("\n--------------------------------------------------");

                System.out.println("TÍTULO ENCONTRADO: " + matcher.group(1).trim());
                System.out.println("--------------------------------------------------");
            } else {
                System.out.println("No se encontró la etiqueta <title>.");
            }

        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}