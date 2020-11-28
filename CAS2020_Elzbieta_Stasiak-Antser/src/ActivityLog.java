import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat; 

// This class creates the log strings from customer activities and saves them to the ActivityLog.txt file.
public class ActivityLog {
	
	private String currentDate;
	
	public ActivityLog() {
		Date today = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		this.currentDate = dateFormatter.format(today);
	}
	
	// Create the activity log string for a cancled basket item.
	public void create_cancelLog(SystemUser user, BasketItem item) {
		String cancelLog = "";
		cancelLog += user.get_userID();
		cancelLog += (", " + user.get_postcode());
		cancelLog += (", " + item.get_item().get_barcode());
		cancelLog += (", " + item.get_item().get_retailPrice());
		cancelLog += (", " + item.get_amount());
		cancelLog += ", cancelled, ";
		cancelLog += (", " + this.currentDate);
		append_newLog(cancelLog);
	}
	
	// Create the activity log string for a saved basket item.
	public void create_saveLog(SystemUser user, BasketItem item) {
		String saveLog = "";
		saveLog += user.get_userID();
		saveLog += (", " + user.get_postcode());
		saveLog += (", " + item.get_item().get_barcode());
		saveLog += (", " + item.get_item().get_retailPrice());
		saveLog += (", " + item.get_amount());
		saveLog += ", saved, ";
		saveLog += (", " + this.currentDate);
		append_newLog(saveLog);
	}
	
	// Create the activity log string for a purchased basket item.
	public void create_purchaseLog(SystemUser user, BasketItem item, boolean payPal) {
		String purchaseLog = "";
		purchaseLog += user.get_userID();
		purchaseLog += (", " + user.get_postcode());
		purchaseLog += (", " + item.get_item().get_barcode());
		purchaseLog += (", " + item.get_item().get_retailPrice());
		purchaseLog += (", " + item.get_amount());
		purchaseLog += ", purchased";
		if (payPal) {
			purchaseLog += ", PayPal";
		} else {
			purchaseLog += ", Credit Card";
		}
		purchaseLog += (", " + this.currentDate);
		append_newLog(purchaseLog);
	}
	
	// Save a new log line to the ActivityLog file.
	private void append_newLog(String newLog) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("ActivityLog.txt", true));
			bw.write(newLog + " \n");
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

}
