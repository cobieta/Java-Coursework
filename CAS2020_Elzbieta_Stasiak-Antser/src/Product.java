// The abstract class used to define the devices the store sells.
public abstract class Product implements Comparable<Product> {
	
	protected int barcode;
	protected DeviceType type;
	protected String brand;
	protected DeviceColour colour;
	protected DeviceConnectivity connectivity;
	protected int quantityInStock;
	protected float originalCost;
	protected float retailPrice;
	
	public Product(int barcode, DeviceType type, String brand, DeviceColour colour, DeviceConnectivity connectivity, int quantity, float cost, float price) {
		this.barcode = barcode;
		this.type = type;
		this.brand = brand;
		this.colour = colour;
		this.connectivity = connectivity;
		this.quantityInStock = quantity;
		this.originalCost = cost;
		this.retailPrice = price;
	}
	
	// Overides the compareTo method from the Comparable interface to allow products to be copmared by their quantity.
	public int compareTo(Product p) {
		return this.quantityInStock - p.quantityInStock;
	}
	
	public int get_barcode() {
		return this.barcode;
	}
	
	public DeviceType get_type() {
		return this.type;
	}
	
	public String get_type_toString() {
		return this.type.toString().toLowerCase();
	}
	
	public String get_brand() {
		return this.brand;
	}
	
	public DeviceColour get_colour() {
		return this.colour;
	}
	
	public String get_colour_toString() {
		return this.colour.toString().toLowerCase();
	}
	
	public DeviceConnectivity get_connectivty() {
		return this.connectivity;
	}
	
	public String get_connectivity_toString() {
		return this.connectivity.toString().toLowerCase();
	}
	
	public int get_quantityInStock() {
		return this.quantityInStock;
	}
	
	public float get_originalCost() {
		return this.originalCost;
	}
	
	public float get_retailPrice() {
		return this.retailPrice;
	}
	
	// Removes the specified quantity of a product from the stock.
	public void remove_stockQuantity(int quantityToRemove) {
		if (quantityToRemove <= this.quantityInStock) {
			this.quantityInStock -= quantityToRemove;
		} else {
			System.out.println("Error, Quantity to remove is greater than the amount in stock available.");
		}
	}
	
	// Overides Object.toString method.
	public abstract String toString();
	
}
