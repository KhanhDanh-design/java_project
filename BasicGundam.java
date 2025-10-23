public class BasicGundam extends Gundam {
    public BasicGundam(String name, double basePrice) {
        super(name, basePrice);
    }

    @Override
    public String getDescription() {
        return name;
    }

    @Override
    public double getPrice() {
        return basePrice;
    }
}