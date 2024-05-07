public class Destination {
    private String name;
    private double priceThere;
    private double priceBack;

    public Destination(String name, double priceThere, double priceBack) {
        this.name = name;
        this.priceThere = priceThere;
        this.priceBack = priceBack;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPriceThere() {
        return priceThere;
    }

    public double getPriceBack() {
        return priceBack;
    }

    // Calculate total price based on class type
    public double calculateTotalPrice(String flightClass) {
        double basePrice = priceThere + priceBack;
        switch (flightClass.toLowerCase()) {
            case "first":
                return basePrice + (basePrice * 1.0/3);
            case "business":
                return basePrice + (basePrice * 1.0/4);
            default:  // Economy class has no additional charge
                return basePrice;
        }
    }
}
