import java.io.IOException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner lectura = new Scanner(System.in);
        ConsultaMoneda consulta = new ConsultaMoneda();
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Menú de Conversor de Monedas ===");
            System.out.println("1. Convertir moneda");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = lectura.nextInt();
            lectura.nextLine(); // Limpiar buffer de entrada

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese la moneda base (ejemplo: USD, EUR): ");
                    String monedaBase = lectura.next().toUpperCase();

                    try {
                        // Obtener tasas de cambio
                        Moneda tasas = consulta.obtenerTasas(monedaBase);
                        System.out.println("Tasas de cambio para: " + tasas.base_code());
                        tasas.conversion_rates().forEach((moneda, tasa) ->
                                System.out.println(moneda + ": " + tasa));

                        // Pedir moneda objetivo
                        System.out.print("Ingrese la moneda a la que desea convertir (ejemplo: EUR, JPY): ");
                        String monedaObjetivo = lectura.next().toUpperCase();

                        if (!tasas.conversion_rates().containsKey(monedaObjetivo)) {
                            System.out.println("La moneda objetivo no es válida o no está disponible.");
                            continue;
                        }

                        // Pedir cantidad a convertir
                        System.out.print("Ingrese la cantidad que desea convertir: ");
                        double cantidad = lectura.nextDouble();

                        // Calcular el valor convertido
                        double tasaCambio = tasas.conversion_rates().get(monedaObjetivo);
                        double resultado = cantidad * tasaCambio;

                        // Mostrar resultado
                        System.out.printf("%.2f %s equivale a %.2f %s\n", cantidad, monedaBase, resultado, monedaObjetivo);

                        // Guardar tasas en un archivo JSON
                        GeneradorDeArchivo generador = new GeneradorDeArchivo();
                        generador.guardarJson(tasas);
                        System.out.println("Tasas de cambio guardadas en: " + tasas.base_code() + "-exchange-rates.json");

                    } catch (RuntimeException | IOException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Error al procesar la solicitud. Intente nuevamente.");
                    }
                }
                case 2 -> {
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    continuar = false;
                }
                default -> System.out.println("Opción no válida. Por favor, seleccione 1 o 2.");
            }
        }

        lectura.close();
    }
}