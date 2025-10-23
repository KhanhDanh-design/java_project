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
                    System.out.print("Enter base price (number): ");
                    double price = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Quantity to import: ");
                    int qty = Integer.parseInt(scanner.nextLine().trim());
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
                    System.out.print("Enter new quantity (0 to remove): ");
                    int newQty = Integer.parseInt(scanner.nextLine().trim());
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
                    System.out.print("Quantity to sell: ");
                    int q = Integer.parseInt(scanner.nextLine().trim());
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
}