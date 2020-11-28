// This class stores the product and its amount which are added to the customer's basket.
public class BasketItem {
	
	private Product item;
	private int amount;
	
	public BasketItem(Product item, int amount) {
		this.item = item;
		this.amount = amount;
	}
	
	public Product get_item() {
		return this.item;
	}
	
	public int get_amount() {
		return this.amount;
	}
	
	public void set_amount(int newAmount) {
		this.amount = newAmount;
	}

}
