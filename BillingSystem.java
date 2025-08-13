import java.util.*;

class Product {
    String name;
    int id;
    double price;

    Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class CartItem {
    Product product;
    int quantity;

    CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    double getTotal() {
        return product.price * quantity;
    }
}

public class BillingSystem {
    static List<Product> products = new ArrayList<>();
    static List<CartItem> cart = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedProducts();
        System.out.println("=== Welcome to Billing System ===");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Show Products\n2. Add to Cart\n3. View Cart\n4. Checkout\n5. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> showProducts();
                case 2 -> addToCart();
                case 3 -> viewCart();
                case 4 -> checkout();
                case 5 -> {
                    System.out.println("Thank you! Exiting.");
                    running = false;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void seedProducts() {
        products.add(new Product(1, "Apple", 0.99));
        products.add(new Product(2, "Milk", 1.49));
        products.add(new Product(3, "Bread", 1.99));
        products.add(new Product(4, "Soap", 0.79));
    }

    static void showProducts() {
        System.out.println("\n--- Product List ---");
        for (Product p : products) {
            System.out.printf("%d. %s - $%.2f\n", p.id, p.name, p.price);
        }
    }

    static void addToCart() {
        showProducts();
        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();
        Product selected = null;
        for (Product p : products) {
            if (p.id == id) {
                selected = p;
                break;
            }
        }
        if (selected == null) {
            System.out.println("Product not found!");
            return;
        }
        System.out.print("Enter quantity: ");
        int qty = scanner.nextInt();
        cart.add(new CartItem(selected, qty));
        System.out.println("Added to cart.");
    }

    static void viewCart() {
        System.out.println("\n--- Cart ---");
        double total = 0;
        for (CartItem item : cart) {
            System.out.printf("%s x%d = $%.2f\n", item.product.name, item.quantity, item.getTotal());
            total += item.getTotal();
        }
        System.out.printf("Total: $%.2f\n", total);
    }

    static void checkout() {
        viewCart();
        System.out.print("Enter payment amount: $");
        double payment = scanner.nextDouble();
        double total = cart.stream().mapToDouble(CartItem::getTotal).sum();
        if (payment < total) {
            System.out.println("Insufficient payment.");
        } else {
            double change = payment - total;
            System.out.printf("Payment successful! Change: $%.2f\n", change);
            cart.clear();
        }
    }
}
