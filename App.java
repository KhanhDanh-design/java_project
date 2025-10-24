import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GundamInventory inventory = GundamInventory.getInstance();

        // --- Default sample models (so menu starts with example data) ---
        // If you restart the program these will be re-imported; adjust quantities as needed
        Gundam rx78 = new BasicGundam("RX-78", 500000);
        Gundam rx78Led = new LedDecorator(rx78);
        Gundam unicorn = new BasicGundam("Unicorn", 700000);
        Gundam unicornFull = new WeaponDecorator(new LedDecorator(unicorn));
        Gundam zaku = new BasicGundam("Zaku II", 300000);
        Gundam wing = new BasicGundam("Wing Gundam", 650000);
        Gundam wingWeapon = new WeaponDecorator(wing);

        // Import default sample quantities
        inventory.importGundam(rx78, 10);
        inventory.importGundam(rx78Led, 5);
        inventory.importGundam(unicornFull, 3);
        inventory.importGundam(zaku, 8);
        inventory.importGundam(wingWeapon, 4);

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
                    Gundam selected = selectGundamFromInventory(scanner, inventory);
                    if (selected != null) inventory.removeGundam(selected);
                    break;
                }
                case "edit": {
                    Gundam selected = selectGundamFromInventory(scanner, inventory);
                    if (selected == null) break;
                    int newQty = readInt(scanner, "Enter new quantity (0 to remove): ");
                    inventory.setQuantity(selected, newQty);
                    break;
                }
                case "sell": {
                    Gundam selected = selectGundamFromInventory(scanner, inventory);
                    if (selected == null) break;
                    int q = readInt(scanner, "Quantity to sell: ");
                    inventory.sellGundam(selected, q);
                    break;
                }
                case "show": {
                    inventory.showInventory();
                    break;
                }
                case "list": {
                    // Allow filtering when listing
                    Map<Gundam, Integer> filtered = getFilteredSnapshot(scanner, inventory);
                    if (filtered.isEmpty()) {
                        System.out.println("No products match the filter.");
                    } else {
                        System.out.println("Available products (descriptions):");
                        for (Gundam g : filtered.keySet()) {
                            System.out.println("- " + g.getDescription() + " | Price: " + g.getPrice() + " | Qty: " + filtered.get(g));
                        }
                    }
                    break;
                }
                default:
                    System.out.println("Unknown action: " + action);
            }
        }

        scanner.close();
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

    // Display inventory with optional filters and allow user to select an item by index
    private static Gundam selectGundamFromInventory(Scanner scanner, GundamInventory inventory) {
        Map<Gundam, Integer> filtered = getFilteredSnapshot(scanner, inventory);
        if (filtered.isEmpty()) return null;

        List<Gundam> list = new ArrayList<>(filtered.keySet());
        for (int i = 0; i < list.size(); i++) {
            Gundam g = list.get(i);
            System.out.println((i + 1) + ") " + g.getDescription() + " | Price: " + g.getPrice() + " | Qty: " + filtered.get(g));
        }
        int idx = readInt(scanner, "Choose an item by number (0 to cancel): ");
        if (idx <= 0 || idx > list.size()) {
            System.out.println("Cancelled or invalid selection.");
            return null;
        }
        return list.get(idx - 1);
    }

    // Build a filtered snapshot based on user-specified min/max price or quantity
    private static Map<Gundam, Integer> getFilteredSnapshot(Scanner scanner, GundamInventory inventory) {
        Map<Gundam, Integer> snap = inventory.getStockSnapshot();

        System.out.print("Do you want to apply filters? (y/n): ");
        String apply = scanner.nextLine().trim();
        if (!apply.equalsIgnoreCase("y")) return snap;

        Double minPrice = null, maxPrice = null;
        Integer minQty = null, maxQty = null;

        System.out.print("Filter by price range? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            minPrice = readDouble(scanner, "Min price (or leave blank for none): ");
            maxPrice = readDouble(scanner, "Max price (or leave blank for none): ");
        }

        System.out.print("Filter by quantity range? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            minQty = readInt(scanner, "Min quantity (or 0 for none): ");
            maxQty = readInt(scanner, "Max quantity (or 0 for none): ");
            if (minQty != null && minQty == 0) minQty = null;
            if (maxQty != null && maxQty == 0) maxQty = null;
        }

        Map<Gundam, Integer> out = new java.util.HashMap<>();
        for (Map.Entry<Gundam, Integer> e : snap.entrySet()) {
            Gundam g = e.getKey();
            int q = e.getValue();
            double p = g.getPrice();

            if (minPrice != null && p < minPrice) continue;
            if (maxPrice != null && p > maxPrice) continue;
            if (minQty != null && q < minQty) continue;
            if (maxQty != null && q > maxQty) continue;
            out.put(g, q);
        }
        return out;
    }
}