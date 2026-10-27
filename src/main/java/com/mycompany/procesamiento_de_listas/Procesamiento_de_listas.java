package com.mycompany.procesamiento_de_listas;

/**
 *
 * @author sebas
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Procesamiento_de_listas {

    static class Producto {

        String codigo;
        String nombre;
        String categoria;
        double precio;
        int stock;

        Producto(String codigo, String nombre, String categoria,
                double precio, int stock) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.categoria = categoria;
            this.precio = precio;
            this.stock = stock;
        }

        double valorInventario() {
            return precio * stock;
        }

        @Override
        public String toString() {
            return codigo + "\t" + nombre + "\t" + categoria
                    + "\t$" + precio + "\t" + stock;
        }
    }

    static String[] CATEGORIAS = {"Tecnologia", "Papeleria", "Muebles", "Electrodomesticos"};

    static ArrayList<Producto> productos = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    static void cargarDatos() {
        productos.add(new Producto("P001", "Portatil", "Tecnologia", 2500, 8));
        productos.add(new Producto("P002", "Mouse", "Tecnologia", 45, 30));
        productos.add(new Producto("P003", "Teclado", "Tecnologia", 120, 5));
        productos.add(new Producto("P004", "Cuaderno", "Papeleria", 8, 100));
        productos.add(new Producto("P005", "Resma de papel", "Papeleria", 25, 9));
        productos.add(new Producto("P006", "Escritorio", "Muebles", 350, 12));
        productos.add(new Producto("P007", "Silla de oficina", "Muebles", 180, 7));
        productos.add(new Producto("P008", "Licuadora", "Electrodomesticos", 150, 15));
        productos.add(new Producto("P009", "Nevera", "Electrodomesticos", 1800, 4));
    }

    static void mostrarEstadisticas() {
        System.out.println("\n===== ESTADISTICAS DEL INVENTARIO =====");

        if (productos.size() == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }

        double suma = 0;
        int stockBajo = 0;
        Producto masCostoso = productos.get(0);
        Producto menorPrecio = productos.get(0);

        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);

            suma += p.precio;

            if (p.precio > masCostoso.precio) {
                masCostoso = p;
            }

            if (p.precio < menorPrecio.precio) {
                menorPrecio = p;
            }

            if (p.stock < 10) {
                stockBajo++;
            }
        }

        System.out.println("Cantidad total de productos: " + productos.size());
        System.out.printf("Valor promedio de los precios: $%.2f%n",
                suma / productos.size());
        System.out.println("Producto mas costoso: " + masCostoso.nombre
                + " ($" + masCostoso.precio + ")");
        System.out.println("Producto con menor precio: " + menorPrecio.nombre
                + " ($" + menorPrecio.precio + ")");
        System.out.println("Productos con stock menor a 10 unidades: " + stockBajo);
    }

    static void mostrarLista(String titulo, ArrayList<Producto> lista) {
        System.out.println("\n--- " + titulo + " ---");
        System.out.println("Codigo\tNombre\tCategoria\tPrecio\tStock");

        if (lista.isEmpty()) {
            System.out.println("(lista vacia)");
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }
    }

    static double valorInventario(ArrayList<Producto> lista) {
        double total = 0;

        for (int i = 0; i < lista.size(); i++) {
            total += lista.get(i).valorInventario();
        }

        return total;
    }

    static void generarListasFiltradas() {
        System.out.println("\n===== LISTAS FILTRADAS =====");

        ArrayList<Producto> productosTecnologia = new ArrayList<>();
        ArrayList<Producto> productosEconomicos = new ArrayList<>();

        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);

            if (p.categoria.equalsIgnoreCase("Tecnologia")) {
                productosTecnologia.add(p);
            }

            if (p.precio < 200) {
                productosEconomicos.add(p);
            }
        }

        mostrarLista("productosTecnologia", productosTecnologia);
        System.out.printf("Valor del inventario (precio x stock): $%.2f%n",
                valorInventario(productosTecnologia));

        mostrarLista("productosEconomicos (precio menor a $200)", productosEconomicos);
        System.out.printf("Valor del inventario (precio x stock): $%.2f%n",
                valorInventario(productosEconomicos));
    }

    static int leerEntero(String mensaje) {
        System.out.print(mensaje);

        while (!sc.hasNextInt()) {
            System.out.print("Entrada invalida. Ingrese un entero: ");
            sc.next();
        }

        int n = sc.nextInt();
        sc.nextLine();
        return n;
    }

    static double leerDecimal(String mensaje) {
        System.out.print(mensaje);

        while (!sc.hasNextDouble()) {
            System.out.print("Entrada invalida. Ingrese un numero: ");
            sc.next();
        }

        double n = sc.nextDouble();
        sc.nextLine();
        return n;
    }

    static int buscarPorCodigo(String codigo) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).codigo.equalsIgnoreCase(codigo)) {
                return i;
            }
        }

        return -1;
    }

    static void opcionBuscar() {
        System.out.print("Codigo del producto: ");
        int pos = buscarPorCodigo(sc.nextLine().trim());

        if (pos == -1) {
            System.out.println("Producto no encontrado.");
        } else {
            System.out.println("Codigo\tNombre\tCategoria\tPrecio\tStock");
            System.out.println(productos.get(pos));
        }
    }

    static void opcionActualizarPrecio() {
        System.out.print("Codigo del producto: ");
        int pos = buscarPorCodigo(sc.nextLine().trim());

        if (pos == -1) {
            System.out.println("Producto no encontrado.");
            return;
        }

        double nuevo;

        do {
            nuevo = leerDecimal("Nuevo precio (mayor a 0): ");

            if (nuevo <= 0) {
                System.out.println("El precio debe ser mayor a 0.");
            }
        } while (nuevo <= 0);

        productos.get(pos).precio = nuevo;
        System.out.println("Precio actualizado correctamente.");
    }

    static void opcionActualizarStock() {
        System.out.print("Codigo del producto: ");
        int pos = buscarPorCodigo(sc.nextLine().trim());

        if (pos == -1) {
            System.out.println("Producto no encontrado.");
            return;
        }

        int nuevo;

        do {
            nuevo = leerEntero("Nuevo stock (mayor o igual a 0): ");

            if (nuevo < 0) {
                System.out.println("El stock no puede ser negativo.");
            }
        } while (nuevo < 0);

        productos.get(pos).stock = nuevo;
        System.out.println("Stock actualizado correctamente.");
    }

    static void opcionEliminar() {
        System.out.print("Codigo del producto: ");
        int pos = buscarPorCodigo(sc.nextLine().trim());

        if (pos == -1) {
            System.out.println("Producto no encontrado.");
            return;
        }

        productos.remove(pos);
        System.out.println("Producto eliminado correctamente.");
    }

    static void opcionMostrarInventario() {
        mostrarLista("INVENTARIO", productos);
        System.out.printf("Valor total del inventario: $%.2f%n",
                valorInventario(productos));
    }

    static void opcionPorCategoria() {
        int op;

        do {
            System.out.println("\nCategoria:");

            for (int i = 0; i < CATEGORIAS.length; i++) {
                System.out.println((i + 1) + ". " + CATEGORIAS[i]);
            }

            op = leerEntero("Elija una opcion: ");

            if (op < 1 || op > CATEGORIAS.length) {
                System.out.println("Opcion no disponible.");
            }
        } while (op < 1 || op > CATEGORIAS.length);

        String categoria = CATEGORIAS[op - 1];
        ArrayList<Producto> lista = new ArrayList<>();

        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).categoria.equalsIgnoreCase(categoria)) {
                lista.add(productos.get(i));
            }
        }

        mostrarLista("Productos de " + categoria, lista);
    }

    static void Menu() {
        int opcion;

        do {
            System.out.println("\nTienda sixseven ");
            System.out.println("1. Buscar producto por codigo");
            System.out.println("2. Actualizar precio");
            System.out.println("3. Actualizar stock");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Mostrar inventario");
            System.out.println("6. Mostrar productos por categoria");
            System.out.println("0. Salir");

            System.out.print("Opcion: ");

            while (!sc.hasNextInt()) {
                System.out.print("No existente. Ingrese la opcion correta: ");
                sc.next();
            }

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    opcionBuscar();
                    break;

                case 2:
                    opcionActualizarPrecio();
                    break;

                case 3:
                    opcionActualizarStock();
                    break;

                case 4:
                    opcionEliminar();
                    break;

                case 5:
                    opcionMostrarInventario();
                    break;

                case 6:
                    opcionPorCategoria();
                    break;

                case 7:
                    System.out.println("Gracias Usuario");
                    break;

                default:
                    System.out.println("no valida.");
            }

        } while (opcion != 0);
    }

    public static void main(String[] args) {
        cargarDatos();
        mostrarEstadisticas();
        generarListasFiltradas();
        Menu();
    }
}
