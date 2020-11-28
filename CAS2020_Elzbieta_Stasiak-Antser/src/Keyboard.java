// This class inherits from the Product class and is used to define the keyboards the store sells.
public class Keyboard extends Product {
	
	private KeyboardLayout layout;
	
	public Keyboard(int barcode, DeviceType type, String brand, DeviceColour colour, DeviceConnectivity connectivity, int quantity, float cost, float price, KeyboardLayout layout) {
		super(barcode, type, brand, colour, connectivity, quantity, cost, price);
		this.layout = layout;
	}
	
	public KeyboardLayout get_layout() {
		return this.layout;
	}
	
	// Implements the abstract toString method from the super class.
	public String toString() {
		String toReturn = barcode + ", keyboard, " + type.toString().toLowerCase() + ", " + brand + ", " + colour.toString().toLowerCase() + ", " + connectivity.toString().toLowerCase() + ", " + quantityInStock + ", " + originalCost + ", " + retailPrice + ", " + this.layout.toString(); 
		return toReturn;
	}
	
}
