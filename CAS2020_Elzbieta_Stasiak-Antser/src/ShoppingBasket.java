import java.util.ArrayList;

// This class stores the list of items in the customer's shopping basket.
public class ShoppingBasket {

	private ArrayList<BasketItem> basketList;
	
	public ShoppingBasket() {
		this.basketList = new ArrayList<BasketItem>();
	}
	
	public ArrayList<BasketItem> get_basketList() {
		return this.basketList;
	}
	
	// Add a new item to the customer's basket.
	public void append_basketList(BasketItem newItem) {
		this.basketList.add(newItem);
	}
	
	// Search for the product in the shopping basket with the specified barcode and return its index otherwise return -1 if not found. 
	public int search_basketList(int barcode) {
		for (BasketItem i: this.basketList) {
			if (i.get_item().get_barcode() == barcode) {
				return this.basketList.indexOf(i);
			}
		}
		return -1;
	}
	
	// Calculates and returns the total cost of all items in the shopping basket.
	public float calculate_totalCost() {
		float totalCost = 0.00f;
		for (BasketItem i: this.basketList) {
			totalCost += (i.get_item().get_retailPrice() * i.get_amount());
		}
		return totalCost;
	}
	
}
