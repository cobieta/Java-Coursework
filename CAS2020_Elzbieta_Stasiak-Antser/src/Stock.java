import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

// This class is used to store the details of all products in the stock and contains methods for interacting with the whole stock. 
public class Stock {
	
	private ArrayList<Product> stockList;
	
	public Stock() {
		this.stockList = new ArrayList<Product>();
		read_stockFile();
		Collections.sort(this.stockList, Collections.reverseOrder());
	}
	
	public ArrayList<Product> get_stockList() {
		return this.stockList;
	}
	
	// Get a version of the stockList with filters applied.
	public ArrayList<Product> getFiltered_stockList(KeyboardLayout layout, String brand) {
		ArrayList<Product> filteredStockList = new ArrayList<Product>();
		if ((layout != null) && (brand != null)) {
			for (Product p: this.stockList) {
				if (p instanceof Keyboard) {
					Keyboard k = (Keyboard)p;
					if ((k.get_layout() == layout) && (k.get_brand().equals(brand))) {
						filteredStockList.add(k);
					}
				}
			}
		} else if (layout != null) {
			for (Product p: this.stockList) {
				if (p instanceof Keyboard) {
					Keyboard k = (Keyboard)p;
					if (k.get_layout() == layout) {
						filteredStockList.add(k);
					}
				}
			}
		} else if (brand != null) {
			for (Product p: this.stockList) {
				if (p.get_brand().equals(brand)) {
					filteredStockList.add(p);
				}
			}
		}
		return filteredStockList;
	}
	
	// Add a new product to the end of the stock list.
	public void append_stockList(Product newProduct) {
		this.stockList.add(newProduct);
		sort_stockList();
	}
	
	// Insert a new product into the product list at a given position overwriting the existing list entry.
	public void insert_stockList(Product newProduct, int position) {
		this.stockList.set(position, newProduct);
		sort_stockList();
	}
	
	// Search for the product in the stock list with the specified barcode and return its index otherwise return -1 if not found. 
	public int search_stockList(int barcode) {
		for (Product p: this.stockList) {
			if (p.get_barcode() == barcode) {
				return this.stockList.indexOf(p);
			}
		}
		return -1;
	}
	
	// Sort the stocklist in order of descending stock quantity and save to file.
	public void sort_stockList() {
		write_stockFile();
		Collections.sort(this.stockList, Collections.reverseOrder());
	}
	
	// Write the current contents of the stockFile into the Stock.txt file.
	private void write_stockFile() {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("Stock.txt"));
			for (int i = 0; i < this.stockList.size(); i++) {
				bw.write(this.stockList.get(i).toString() + " \n");
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	// Read the contents of the Stock.txt file, creating Mouse and Keyboard objects from each line of data and adding them to ArrayList of Products.
	private void read_stockFile() {
		Scanner fileScanner = null;
		try {
			File inputFile = new File("Stock.txt");
			fileScanner = new Scanner(inputFile);
			while (fileScanner.hasNextLine()) {
				String[] lineArray = fileScanner.nextLine().split(",");
				DeviceType type;
				if (lineArray[2].trim().equals("standard")) {
					type = DeviceType.STANDARD;
				} else if (lineArray[2].trim().equals("gaming")) {
					type = DeviceType.GAMING;
				} else if (lineArray[2].trim().equals("internet")) {
					type = DeviceType.INTERNET;
				} else {
					type = DeviceType.FLEXIBLE;
				}
				DeviceConnectivity connectivity;
				if (lineArray[5].trim().equals("wired")) {
					connectivity = DeviceConnectivity.WIRED;
				} else {
					connectivity = DeviceConnectivity.WIRELESS;
				}
				DeviceColour colour;
				if (lineArray[4].trim().equals("black")) {
					colour = DeviceColour.BLACK;
				} else if (lineArray[4].trim().equals("white")) {
					colour = DeviceColour.WHITE;
				} else if (lineArray[4].trim().equals("grey")) {
					colour = DeviceColour.GREY;
				} else if (lineArray[4].trim().equals("blue")) {
					colour = DeviceColour.BLUE;
				} else if (lineArray[4].trim().equals("red")) {
					colour = DeviceColour.RED;
				} else if (lineArray[4].trim().equals("yellow")) {
					colour = DeviceColour.YELLOW;
				} else if (lineArray[4].trim().equals("purple")) {
					colour = DeviceColour.PURPLE;
				} else if (lineArray[4].trim().equals("green")) {
					colour = DeviceColour.GREEN;
				} else {
					colour = DeviceColour.ORANGE;
				} 
				if (lineArray[1].trim().equals("mouse")) {
					Mouse newMouse = new Mouse(Integer.parseInt(lineArray[0].trim()), type, lineArray[3].trim(), colour, connectivity, Integer.parseInt(lineArray[6].trim()), Float.parseFloat(lineArray[7].trim()), Float.parseFloat(lineArray[8].trim()), Integer.parseInt(lineArray[9].trim()));
					this.stockList.add(newMouse);
				} else {
					KeyboardLayout layout;
					if (lineArray[9].trim().equals("UK")) {
						layout = KeyboardLayout.UK;
					} else {
						layout = KeyboardLayout.US;
					}
					Keyboard newKeyboard = new Keyboard(Integer.parseInt(lineArray[0].trim()), type, lineArray[3].trim(), colour, connectivity, Integer.parseInt(lineArray[6].trim()), Float.parseFloat(lineArray[7].trim()), Float.parseFloat(lineArray[8].trim()), layout);
					this.stockList.add(newKeyboard);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			fileScanner.close();
		}
	}

}
