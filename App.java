import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GundamInventory inventory = GundamInventory.getInstance();

        System.out.println("Welcome to Gundam Inventory (menu):");
        while (true) {
            System.out.println("\nChoose an action: add | remove | edit | sell | show | list | exit");
            System.out.print("> ");
            String action = scanner.nextLine().trim().toLowerCase();

            if (action.equals("exit")) {
                System.out.println("Exiting...");
                break;
            }

            switch (action) {
                case "add": {
                    System.out.print("Enter base name: ");
                    String name = scanner.nextLine().trim();
                    double price = readDouble(scanner, "Enter base price (number): ");
                    int qty = readInt(scanner, "Quantity to import: ");
                    System.out.print("Add LED? (y/n): ");
                    boolean led = scanner.nextLine().trim().equalsIgnoreCase("y");
                    System.out.print("Add Weapon? (y/n): ");
                    boolean weapon = scanner.nextLine().trim().equalsIgnoreCase("y");

                    Gundam g = new BasicGundam(name, price);
                    if (led) g = new LedDecorator(g);
                    if (weapon) g = new WeaponDecorator(g);

                    inventory.importGundam(g, qty);
                    break;
                }
                case "remove": {
                    System.out.print("Enter exact description of product to remove: ");
                    String desc = scanner.nextLine().trim();
                    Gundam found = findByDescription(inventory, desc);
                    if (found != null) inventory.removeGundam(found);
                    else System.out.println("Product not found: " + desc);
                    break;
                }
                case "edit": {
                    System.out.print("Enter exact description of product to edit: ");
                    String desc = scanner.nextLine().trim();
                    Gundam found = findByDescription(inventory, desc);
                    if (found == null) {
                        System.out.println("Product not found: " + desc);
                        break;
                    }
                    int newQty = readInt(scanner, "Enter new quantity (0 to remove): ");
                    inventory.setQuantity(found, newQty);
                    break;
                }
                case "sell": {
                    System.out.print("Enter exact description of product to sell: ");
                    String desc = scanner.nextLine().trim();
                    Gundam found = findByDescription(inventory, desc);
                    if (found == null) {
                        System.out.println("Product not found: " + desc);
                        break;
                    }
                    int q = readInt(scanner, "Quantity to sell: ");
                    inventory.sellGundam(found, q);
                    break;
                }
                case "show": {
                    inventory.showInventory();
                    break;
                }
                case "list": {
                    Map<Gundam, Integer> snap = inventory.getStockSnapshot();
                    System.out.println("Available products (descriptions):");
                    for (Gundam g : snap.keySet()) {
                        System.out.println("- " + g.getDescription() + " | Price: " + g.getPrice() + " | Qty: " + snap.get(g));
                    }
                    break;
                }
                default:
                    System.out.println("Unknown action: " + action);
            }
        }

        scanner.close();
    }

    private static Gundam findByDescription(GundamInventory inventory, String description) {
        for (Map.Entry<Gundam, Integer> e : inventory.getStockSnapshot().entrySet()) {
            if (e.getKey().getDescription().equals(description)) return e.getKey();
        }
        return null;
    }

    // Read an integer from scanner with prompt, repeat until valid
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Please enter an integer.");
            }
        }
    }

    // Read a double from scanner with prompt, repeat until valid
    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Please enter a valid decimal number.");
            }
        }
    }
}