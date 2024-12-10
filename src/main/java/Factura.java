import java.util.List;

public class Factura {
    private List<Producto> productos;

    public Factura(List<Producto> productos) {
        this.productos = productos;
    }

    public void imprimirFactura() {
        double totalSinIVA = 0;
        System.out.println("\nFactura:");
        System.out.println("Codigo\t\tNombre\t\tCantidad\tPrecio\t\tTotal");
        for (Producto producto : productos) {
            double total = producto.getCantidad() * producto.getPrecio();
            System.out.printf("%s\t\t%s\t\t%d\t\t%.2f\t\t%.2f\n",
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCantidad(),
                    producto.getPrecio(),
                    total);
            totalSinIVA += total;
        }
        double iva = totalSinIVA * 0.19;
        double totalConIVA = totalSinIVA + iva;
        System.out.printf("Total sin IVA: %.2f\n", totalSinIVA);
        System.out.printf("IVA (19%%): %.2f\n", iva);
        System.out.printf("Total con IVA: %.2f\n", totalConIVA);
    }
}
