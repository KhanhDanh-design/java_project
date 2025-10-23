public abstract class GundamDecorator extends Gundam {
    protected Gundam decoratedGundam;

    public GundamDecorator(Gundam gundam) {
        super(gundam.name, gundam.basePrice);
        this.decoratedGundam = gundam;
    }

    public abstract String getDescription();
    public abstract double getPrice();
}