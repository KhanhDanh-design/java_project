import java.util.HashMap;
import java.util.Map;

public class GundamInventory {
    private static GundamInventory instance;
    private Map<Gundam, Integer> stock;

    private GundamInventory() {
        stock = new HashMap<>();
    }

    public static GundamInventory getInstance() {
        if (instance == null) {
            instance = new GundamInventory();
        }
        return instance;
    }

    public void importGundam(Gundam gundam, int quantity) {
        stock.put(gundam, stock.getOrDefault(gundam, 0) + quantity);
        System.out.println("Da nhap " + quantity + " mo hinh: " + gundam.getDescription());
    }

    public void sellGundam(Gundam gundam, int quantity) {
        int currentStock = stock.getOrDefault(gundam, 0);
        if (currentStock >= quantity) {
            stock.put(gundam, currentStock - quantity);
            System.out.println("Da ban " + quantity + " mo hinh: " + gundam.getDescription());
        } else {
            System.out.println("Khong du hang de ban: " + gundam.getDescription());
        }
    }

    public void showInventory() {
        System.out.println("\nTon kho Gundam:");
        for (Map.Entry<Gundam, Integer> entry : stock.entrySet()) {
            Gundam g = entry.getKey();
            int qty = entry.getValue();
            System.out.println("- " + g.getDescription() + " | Gia: " + g.getPrice() + " | So luong: " + qty);
        }
    }

    // Return a snapshot copy of current stock so callers can iterate safely
    public Map<Gundam, Integer> getStockSnapshot() {
        return new HashMap<>(stock);
    }

    // Remove a product entirely from inventory
    public void removeGundam(Gundam gundam) {
        if (stock.containsKey(gundam)) {
            stock.remove(gundam);
            System.out.println("Da xoa mo hinh: " + gundam.getDescription());
        } else {
            System.out.println("Khong tim thay mo hinh: " + gundam.getDescription());
        }
    }

    // Set exact quantity for a product (if qty <= 0, remove it)
    public void setQuantity(Gundam gundam, int quantity) {
        if (quantity <= 0) {
            removeGundam(gundam);
        } else {
            stock.put(gundam, quantity);
            System.out.println("Da dat so luong " + quantity + " cho: " + gundam.getDescription());
        }
    }
}