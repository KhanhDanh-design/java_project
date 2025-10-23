public class WeaponDecorator extends GundamDecorator {
    public WeaponDecorator(Gundam gundam) {
        super(gundam);
    }

    @Override
    public String getDescription() {
        return decoratedGundam.getDescription() + " + Vu khi dac biet";
    }

    @Override
    public double getPrice() {
        return decoratedGundam.getPrice() + 150000;
    }
}