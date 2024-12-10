import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Interfaz extends JFrame {
    private Catalogo catalogo;
    private JTextArea textAreaCatalogo;
    private List<Producto> productosFactura;

    public Interfaz() {
        catalogo = new Catalogo();
        productosFactura = new ArrayList<>();
        
        setTitle("Catálogo de Productos");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        textAreaCatalogo = new JTextArea();
        textAreaCatalogo.setEditable(false); // No editable
        textAreaCatalogo.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(textAreaCatalogo);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(8, 1)); // 8 botones, uno por cada opción
        add(panelBotones, BorderLayout.WEST);

        JButton mostrarCatalogoBtn = new JButton("Mostrar Catálogo");
        JButton agregarProductoBtn = new JButton("Agregar Producto");
        JButton modificarProductoBtn = new JButton("Modificar Producto");
        JButton eliminarProductoBtn = new JButton("Eliminar Producto");
        JButton buscarProductoBtn = new JButton("Buscar Producto por Código");
        JButton agregarAFacturaBtn = new JButton("Agregar a Factura");
        JButton generarFacturaBtn = new JButton("Generar Factura");
        JButton salirBtn = new JButton("Salir");
        
        panelBotones.add(mostrarCatalogoBtn);
        panelBotones.add(agregarProductoBtn);
        panelBotones.add(modificarProductoBtn);
        panelBotones.add(eliminarProductoBtn);
        panelBotones.add(buscarProductoBtn);
        panelBotones.add(agregarAFacturaBtn);
        panelBotones.add(generarFacturaBtn);
        panelBotones.add(salirBtn);
        
        mostrarCatalogoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCatalogo();
            }
        });
        
        agregarProductoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = JOptionPane.showInputDialog("Código del producto:");
                String nombre = JOptionPane.showInputDialog("Nombre del producto:");
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad del producto:"));
                double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio del producto:"));
                
                Producto nuevoProducto = new Producto(codigo, nombre, cantidad, precio);
                catalogo.agregarProducto(nuevoProducto);
                JOptionPane.showMessageDialog(null, "Producto agregado al catálogo.");
            }
        });
        
        modificarProductoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = JOptionPane.showInputDialog("Código del producto para modificar:");
                Producto producto = catalogo.buscarProductoPorCodigo(codigo);
                if (producto != null) {
                    String[] opciones = { "Modificar Nombre", "Modificar Cantidad", "Modificar Precio" };
                    int opcion = JOptionPane.showOptionDialog(null, "¿Qué deseas modificar?", "Modificar Producto",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
                    
                    switch (opcion) {
                        case 0:
                            String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre del producto:");
                            producto.setNombre(nuevoNombre);
                            JOptionPane.showMessageDialog(null, "Nombre actualizado.");
                            break;
                        case 1:
                            int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog("Nueva cantidad del producto:"));
                            producto.setCantidad(nuevaCantidad);
                            JOptionPane.showMessageDialog(null, "Cantidad actualizada.");
                            break;
                        case 2:
                            double porcentaje = Double.parseDouble(JOptionPane.showInputDialog("Porcentaje de incremento:"));
                            double incremento = producto.getPrecio() * (porcentaje / 100);
                            double nuevoPrecio = producto.getPrecio() + incremento;
                            producto.setPrecio(nuevoPrecio);
                            JOptionPane.showMessageDialog(null, "Precio actualizado. Nuevo precio: " + nuevoPrecio);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Operación cancelada.");
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                }
            }
        });
        
        eliminarProductoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = JOptionPane.showInputDialog("Código del producto a eliminar:");
                catalogo.eliminarProducto(codigo);
                JOptionPane.showMessageDialog(null, "Producto eliminado del catálogo.");
            }
        });
        
        buscarProductoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = JOptionPane.showInputDialog("Código del producto a buscar:");
                Producto producto = catalogo.buscarProductoPorCodigo(codigo);
                if (producto != null) {
                    JOptionPane.showMessageDialog(null, "Producto encontrado:\n" + producto.getNombre() + " - " + producto.getPrecio());
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                }
            }
        });
        
        agregarAFacturaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = JOptionPane.showInputDialog("Código del producto para agregar a la factura:");
                Producto producto = catalogo.buscarProductoPorCodigo(codigo);
                if (producto != null) {
                    int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de productos a agregar:"));
                    if (producto.getCantidad() >= cantidad) {
                        productosFactura.add(new Producto(producto.getCodigo(), producto.getNombre(), cantidad, producto.getPrecio()));
                        JOptionPane.showMessageDialog(null, "Producto agregado a la factura.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay suficiente stock.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                }
            }
        });
        
        generarFacturaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double total = 0;
                StringBuilder factura = new StringBuilder();
                factura.append("Código*********Cantidad*************Precio*******Total\n");
                
                for (Producto producto : productosFactura) {
                    int cantidad = producto.getCantidad();
                    double precio = producto.getPrecio();
                    double totalProducto = cantidad * precio;
                    total += totalProducto;
                    factura.append(String.format("%-10s %-15d %-15.2f %-15.2f\n",
                            producto.getCodigo(),
                            cantidad,
                            precio,
                            totalProducto));
                    
                    catalogo.actualizarCantidad(producto.getCodigo(), cantidad);
                }

                double iva = total * 0.19;
                double totalConIva = total + iva;

                factura.append("\nPrecio Total: " + total + "\n");
                factura.append("IVA (19%): " + iva + "\n");
                factura.append("Total con IVA: " + totalConIva + "\n");

                JOptionPane.showMessageDialog(null, factura.toString());
                
                productosFactura.clear();
            }
        });
        
        salirBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void mostrarCatalogo() {
        textAreaCatalogo.setText(""); 
        
        textAreaCatalogo.append(String.format("%-10s %-20s %-15s %-10s\n", "Codigo", "Nombre", "Cantidad", "Precio"));
        for (Producto producto : catalogo.getProductos()) {
            textAreaCatalogo.append(String.format("%-10s %-20s %-10d %-10.2f\n",
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCantidad(),
                    producto.getPrecio()));
        }
    }
    
    public static void main(String[] args) {
        Interfaz ventana = new Interfaz();
        ventana.setVisible(true);
    }
}
