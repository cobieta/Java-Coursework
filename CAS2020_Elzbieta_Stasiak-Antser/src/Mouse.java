// This class inherits from the Product class and is used to define the mouses the store sells.
public class Mouse extends Product {
	
	private int buttonNumber;
	
	public Mouse(int barcode, DeviceType type, String brand, DeviceColour colour, DeviceConnectivity connectivity, int quantity, float cost, float price, int buttons) {
		super(barcode, type, brand, colour, connectivity, quantity, cost, price);
		this.buttonNumber = buttons;
	}
	
	public int get_buttonNumber() {
		return this.buttonNumber;
	}
	
	// Implements the abstract toString method from the super class.
	public String toString() {
		String toReturn = barcode + ", mouse, " + type.toString().toLowerCase() + ", " + brand + ", " + colour.toString().toLowerCase() + ", " + connectivity.toString().toLowerCase() + ", " + quantityInStock + ", " + originalCost + ", " + retailPrice + ", " + this.buttonNumber; 
		return toReturn;
	}
}
