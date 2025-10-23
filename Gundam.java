public abstract class Gundam {
    protected String name;
    protected double basePrice;

    public Gundam(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public abstract String getDescription();
    public abstract double getPrice();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gundam)) return false;
        Gundam other = (Gundam) o;
     
        if (Double.compare(other.getPrice(), this.getPrice()) != 0) return false;
        String d1 = this.getDescription();
        String d2 = other.getDescription();
        return d1 == null ? d2 == null : d1.equals(d2);
    }

    @Override
    public int hashCode() {
        String desc = getDescription();
        long temp = Double.doubleToLongBits(getPrice());
        int result = (desc == null) ? 0 : desc.hashCode();
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return getDescription() + " (" + getPrice() + ")";
    }
}