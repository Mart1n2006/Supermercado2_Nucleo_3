import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Catalogo catalogo = new Catalogo();
        
        
        catalogo.agregarProducto(new Producto("001", "Leche", 10, 1500));
        catalogo.agregarProducto(new Producto("002", "Pan", 20, 500));
        catalogo.agregarProducto(new Producto("003", "Huevos", 30, 3000));

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Mostrar catalogo");
            System.out.println("2. Agregar producto al catalogo");
            System.out.println("3. Modificar precio de producto");
            System.out.println("4. Eliminar producto del catalogo");
            System.out.println("5. Buscar producto por codigo");
            System.out.println("6. Generar factura");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opcion: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    catalogo.mostrarCatalogo();
                    break;
                case 2:
                    System.out.print("Ingrese el codigo del producto: ");
                    String codigo = scanner.nextLine();
                    System.out.print("Ingrese el nombre del producto: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese la cantidad disponible: ");
                    int cantidad = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ingrese el precio por unidad: ");
                    double precio = Double.parseDouble(scanner.nextLine());
                    catalogo.agregarProducto(new Producto(codigo, nombre, cantidad, precio));
                    break;
                case 3:
                    System.out.print("Ingrese el codigo del producto a modificar: ");
                    String codigoModificar = scanner.nextLine();
                    System.out.print("Ingrese el porcentaje de incremento: ");
                    double porcentaje = Double.parseDouble(scanner.nextLine());
                    Producto productoModificar = catalogo.buscarProductoPorCodigo(codigoModificar);
                    if (productoModificar != null) {
                        productoModificar.incrementarPrecio(porcentaje);
                        System.out.println("Precio del producto actualizado.");
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el codigo del producto a eliminar: ");
                    String codigoEliminar = scanner.nextLine();
                    catalogo.eliminarProducto(codigoEliminar);
                    System.out.println("Producto eliminado.");
                    break;
                case 5:
                    System.out.print("Ingrese el codigo del producto a buscar: ");
                    String codigoBuscar = scanner.nextLine();
                    Producto productoBuscar = catalogo.buscarProductoPorCodigo(codigoBuscar);
                    if (productoBuscar != null) {
                        System.out.println("Producto encontrado:");
                        System.out.printf("Codigo: %s\nNombre: %s\nCantidad Disponible: %d\nPrecio: %.2f\n",
                                productoBuscar.getCodigo(),
                                productoBuscar.getNombre(),
                                productoBuscar.getCantidad(),
                                productoBuscar.getPrecio());
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;
                case 6:
                    List<Producto> productosFactura = new ArrayList<>();
                    while (true) {
                        System.out.print("Ingrese el codigo del producto para la factura (o 'fin' para terminar): ");
                        String codigoFactura = scanner.nextLine();
                        if (codigoFactura.equalsIgnoreCase("fin")) {
                            break;
                        }
                        Producto productoFactura = catalogo.buscarProductoPorCodigo(codigoFactura);
                        if (productoFactura != null) {
                            System.out.print("Ingrese la cantidad comprada: ");
                            int cantidadFactura = Integer.parseInt(scanner.nextLine());
                            if (catalogo.actualizarCantidad(codigoFactura, cantidadFactura)) {
                                Producto productoFacturaConCantidad = new Producto(productoFactura.getCodigo(), productoFactura.getNombre(), cantidadFactura, productoFactura.getPrecio());
                                productosFactura.add(productoFacturaConCantidad);
                            }
                        } else {
                            System.out.println("Producto no encontrado.");
                        }
                    }
                    Factura factura = new Factura(productosFactura);
                    factura.imprimirFactura();
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }
}
