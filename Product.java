public class Product {
    private String name;
    private String description;
    private double price;
    private String imagePath; // Optional: Path to the product image

    public Product(String name, String description, double price, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }

    public Product(String name, String description, double price) {
        this(name, description, price, null);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return name + " - $" + price;
    }
}
