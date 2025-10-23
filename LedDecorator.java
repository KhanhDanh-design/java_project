public class LedDecorator extends GundamDecorator {
    public LedDecorator(Gundam gundam) {
        super(gundam);
    }

    @Override
    public String getDescription() {
        return decoratedGundam.getDescription() + " + LED";
    }

    @Override
    public double getPrice() {
        return decoratedGundam.getPrice() + 100000;
    }
}