import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private List<Producto> productos;

    public Catalogo() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public Producto buscarProductoPorCodigo(String codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        return null;
    }

    public void eliminarProducto(String codigo) {
        Producto producto = buscarProductoPorCodigo(codigo);
        if (producto != null) {
            productos.remove(producto);
        }
    }

    public void mostrarCatalogo() {
        System.out.printf("%-10s %-20s %-15s %-10s\n", "Codigo", "Nombre", "Cantidad_Disponible", "Precio");
        for (Producto producto : productos) {
            System.out.printf("%-10s %-20s %-10d %-10.2f\n",
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCantidad(),
                    producto.getPrecio());
        }
    }

    public boolean actualizarCantidad(String codigo, int cantidad) {
        Producto producto = buscarProductoPorCodigo(codigo);
        if (producto != null) {
            if (producto.getCantidad() >= cantidad) {
                producto.setCantidad(producto.getCantidad() - cantidad);
                return true;
            } else {
                System.out.println("No hay suficiente cantidad disponible del producto.");
                return false;
            }
        }
        System.out.println("Producto no encontrado.");
        return false;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}
