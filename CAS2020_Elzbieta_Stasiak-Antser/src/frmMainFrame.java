import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;

import java.util.regex.Pattern;



public class frmMainFrame extends JFrame {

	private JPanel contentPane;
	private JTable tblAllStockAdmin, tblAllStockCustomer, tblShoppingBasket;
	private DefaultTableModel dtmShoppingBasket, dtmAllStock, dtmAllStockCustomer;
	private JTextField txfBrand, txfBrandSearch, txfTotalCost, txfTotalCostPayment, txfPayPalEmail;
	private NumberFormat currencyFormat, integerFormat;
	private Stock stock;
	private UserAccounts accounts;
	private ShoppingBasket basket;
	private ActivityLog activityLog;

	// Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmMainFrame frame = new frmMainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create the frame.
	public frmMainFrame() {
		// Initialise the program classes:
		accounts = new UserAccounts();
		stock = new Stock();
		basket = new ShoppingBasket();
		activityLog = new ActivityLog();
		
		// Create a format for entering currency amounts.
		currencyFormat = NumberFormat.getNumberInstance();
		currencyFormat.setMinimumFractionDigits(2);
		currencyFormat.setMaximumFractionDigits(2);
		
		// Create a format for entering integer amounts.
		integerFormat = NumberFormat.getNumberInstance();
		integerFormat.setMaximumFractionDigits(0);
		
		
		// GUI code:
		// Main frame and panel.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 852, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// The container for all panels that are part of this window.
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 847, 529);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
			// Welcome panel that is shown first.
			JPanel pnlWelcome = new JPanel();
			layeredPane.add(pnlWelcome, "name_2383021300428500");
			pnlWelcome.setLayout(null);
			JLabel lblHeading = new JLabel("<html><div style='text-align: center;'>Welcome to the Computer Accessories Shop System</div></html>");
			lblHeading.setHorizontalAlignment(SwingConstants.CENTER);
			lblHeading.setBounds(71, 92, 710, 92);
			lblHeading.setFont(new Font("Tahoma", Font.BOLD, 35));
			pnlWelcome.add(lblHeading);
			
				// GUI components that are part of the welcome panel.
				JLabel lblUsername = new JLabel("Select a username:");
				lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 22));
				lblUsername.setBounds(71, 276, 235, 46);
				pnlWelcome.add(lblUsername);
				
				JComboBox cbxUsername = new JComboBox();
				cbxUsername.setModel(new DefaultComboBoxModel(accounts.create_usernameArray()));
				cbxUsername.setMaximumRowCount(4);
				cbxUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
				cbxUsername.setBounds(71, 322, 235, 34);
				pnlWelcome.add(cbxUsername);
				
				JButton btnLogin = new JButton("Login");
				btnLogin.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Check whether the user is an admin or a customer.
						pnlWelcome.setVisible(false);
						String role = accounts.get_userArray()[cbxUsername.getSelectedIndex()].get_role();
						accounts.set_loggedInUser(cbxUsername.getSelectedIndex());
						if (role.equals("admin")) {
							// Update the table data shown and switch to showing the panel: pnlAdminAccess
							layeredPane.getComponent(1).setVisible(true);
							fillTableWithData(dtmAllStock, stock.get_stockList(), true);
						} else {
							//  Update the table data shown and switch to showing the panel: pnlCustomerAccess
							layeredPane.getComponent(2).setVisible(true);
							fillTableWithData(dtmAllStockCustomer, stock.get_stockList(), false);
						}
					}
				});
				btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
				btnLogin.setBounds(316, 322, 235, 34);
				pnlWelcome.add(btnLogin);
			
			// Panel for showing the admin accessable tabs.
			JPanel pnlAdminAccess = new JPanel();
			layeredPane.add(pnlAdminAccess, "name_2383021312994300");
			pnlAdminAccess.setLayout(null);
			pnlAdminAccess.setVisible(false);
			
				// Container for the tabs accessable to the admin.
				JTabbedPane tpnAdminTabs = new JTabbedPane(JTabbedPane.TOP);
				tpnAdminTabs.setFont(new Font("Tahoma", Font.PLAIN, 17));
				tpnAdminTabs.setBounds(0, 0, 847, 529);
				pnlAdminAccess.add(tpnAdminTabs);
				
					// Panel showing the admin's view of the shop stock.
					JPanel pnlAdminStockView = new JPanel();
					tpnAdminTabs.addTab("View all Products", null, pnlAdminStockView, null);
					pnlAdminStockView.setLayout(null);
						
						JScrollPane scpAdminStockScroll = new JScrollPane();
						scpAdminStockScroll.setBounds(10, 10, 822, 416);
						pnlAdminStockView.add(scpAdminStockScroll);

							// Table showing the details of all the stock.
							tblAllStockAdmin = new JTable();
							tblAllStockAdmin.setFont(new Font("Tahoma", Font.PLAIN, 16));
							tblAllStockAdmin.setRowHeight(25);
							dtmAllStock = new DefaultTableModel();
							tblAllStockAdmin.setModel(dtmAllStock);
							dtmAllStock.setColumnIdentifiers(new Object[] {"Barcode", "Name", "Type", "Brand", "Colour", "Connectivity", "Quantity in Stock", "Original Cost (£)", "Retail Price (£)", "Number of Buttons / Keyboard Layout"});
							scpAdminStockScroll.setViewportView(tblAllStockAdmin);
							tblAllStockAdmin.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
							tblAllStockAdmin.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							tblAllStockAdmin.getColumnModel().getColumn(1).setPreferredWidth(90);
							tblAllStockAdmin.getColumnModel().getColumn(2).setPreferredWidth(90);
							tblAllStockAdmin.getColumnModel().getColumn(3).setPreferredWidth(90);
							tblAllStockAdmin.getColumnModel().getColumn(5).setPreferredWidth(110);
							tblAllStockAdmin.getColumnModel().getColumn(6).setPreferredWidth(140);
							tblAllStockAdmin.getColumnModel().getColumn(7).setPreferredWidth(130);
							tblAllStockAdmin.getColumnModel().getColumn(8).setPreferredWidth(120);
							tblAllStockAdmin.getColumnModel().getColumn(9).setPreferredWidth(290);
							// Add data from the Stock class using a private method.
							fillTableWithData(dtmAllStock, stock.get_stockList(), true);
							
					// The logout button takes the user back to the welcome screen.
					JButton btnLogout = new JButton("Logout");
					btnLogout.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// Switch to showing the panel: pnlWelcome
							pnlAdminAccess.setVisible(false);
							pnlWelcome.setVisible(true);
						}
					});
					btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 20));
					btnLogout.setBounds(10, 434, 235, 34);
					pnlAdminStockView.add(btnLogout);
					
				// Panel containing the form for the admin to enter a new product into the system.
				JPanel pnlAdminAddProduct = new JPanel();
				tpnAdminTabs.addTab("Add a Product", null, pnlAdminAddProduct, null);
				pnlAdminAddProduct.setLayout(null);
					
					JLabel lblBarcode = new JLabel("Barcode:");
					lblBarcode.setFont(new Font("Tahoma", Font.PLAIN, 22));
					lblBarcode.setBounds(36, 4, 116, 44);
					pnlAdminAddProduct.add(lblBarcode);
						
					JLabel lblName = new JLabel("Name:");
					lblName.setFont(new Font("Tahoma", Font.PLAIN, 22));
					lblName.setBounds(36, 52, 116, 44);
					pnlAdminAddProduct.add(lblName);
						
					JLabel lblType = new JLabel("Type:");
					lblType.setFont(new Font("Tahoma", Font.PLAIN, 22));
					lblType.setBounds(36, 100, 116, 44);
					pnlAdminAddProduct.add(lblType);
						
					JLabel lblBrand = new JLabel("Brand:");
					lblBrand.setFont(new Font("Tahoma", Font.PLAIN, 22));
					lblBrand.setBounds(36, 148, 116, 44);
					pnlAdminAddProduct.add(lblBrand);
					
					JLabel lblColour = new JLabel("Colour:");
					lblColour.setFont(new Font("Tahoma", Font.PLAIN, 22));
					lblColour.setBounds(36, 196, 116, 44);
					pnlAdminAddProduct.add(lblColour);
					
					JLabel lblQuantityOfStock = new JLabel("Quantity of Stock:");
					lblQuantityOfStock.setFont(new Font("Tahoma", Font.PLAIN, 22));
					lblQuantityOfStock.setBounds(36, 292, 190, 44);
					pnlAdminAddProduct.add(lblQuantityOfStock);
					
					JLabel lblOriginalCost = new JLabel("Original Cost (£):");
					lblOriginalCost.setFont(new Font("Tahoma", Font.PLAIN, 22));
					lblOriginalCost.setBounds(36, 340, 176, 44);
					pnlAdminAddProduct.add(lblOriginalCost);
					
					JLabel lblRetailPrice = new JLabel("Retail Price (£):");
					lblRetailPrice.setFont(new Font("Tahoma", Font.PLAIN, 22));
					lblRetailPrice.setBounds(36, 388, 176, 44);
					pnlAdminAddProduct.add(lblRetailPrice);
					
					JLabel lblConnectivity = new JLabel("Connectivity:");
					lblConnectivity.setFont(new Font("Tahoma", Font.PLAIN, 22));
					lblConnectivity.setBounds(36, 244, 142, 44);
					pnlAdminAddProduct.add(lblConnectivity);
					
					JLabel lblNumberOfButtons = new JLabel("Number of Buttons:");
					lblNumberOfButtons.setFont(new Font("Tahoma", Font.PLAIN, 22));
					lblNumberOfButtons.setBounds(36, 436, 210, 44);
					pnlAdminAddProduct.add(lblNumberOfButtons);
					
					JLabel lblKeyboardLayout = new JLabel("Keyboard Layout:");
					lblKeyboardLayout.setFont(new Font("Tahoma", Font.PLAIN, 22));
					lblKeyboardLayout.setBounds(36, 436, 210, 44);
					pnlAdminAddProduct.add(lblKeyboardLayout);
					lblKeyboardLayout.setVisible(false);
					
					txfBrand = new JTextField();
					txfBrand.setFont(new Font("Tahoma", Font.PLAIN, 20));
					txfBrand.setColumns(10);
					txfBrand.setBounds(251, 155, 235, 31);
					pnlAdminAddProduct.add(txfBrand);
					
					JFormattedTextField ftfOriginalCost = new JFormattedTextField(currencyFormat);
					ftfOriginalCost.setFont(new Font("Tahoma", Font.PLAIN, 20));
					ftfOriginalCost.setBounds(346, 347, 140, 31);
					pnlAdminAddProduct.add(ftfOriginalCost);
					
					JFormattedTextField ftfQuantityOfStock = new JFormattedTextField(integerFormat);
					ftfQuantityOfStock.setFont(new Font("Tahoma", Font.PLAIN, 20));
					ftfQuantityOfStock.setBounds(346, 299, 140, 31);
					pnlAdminAddProduct.add(ftfQuantityOfStock);
					
					JFormattedTextField ftfBarcode = new JFormattedTextField(useFormatter("######"));
					ftfBarcode.setFont(new Font("Tahoma", Font.PLAIN, 20));
					ftfBarcode.setBounds(251, 10, 235, 32);
					pnlAdminAddProduct.add(ftfBarcode);
					
					JFormattedTextField ftfRetailPrice = new JFormattedTextField(currencyFormat);
					ftfRetailPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
					ftfRetailPrice.setBounds(346, 395, 140, 31);
					pnlAdminAddProduct.add(ftfRetailPrice);
					
					JFormattedTextField ftfNumberOfButtons = new JFormattedTextField(integerFormat);
					ftfNumberOfButtons.setFont(new Font("Tahoma", Font.PLAIN, 20));
					ftfNumberOfButtons.setBounds(346, 443, 140, 31);
					pnlAdminAddProduct.add(ftfNumberOfButtons);
					
					JComboBox cbxType = new JComboBox();
					cbxType.setModel(new DefaultComboBoxModel(new DeviceType[] {DeviceType.STANDARD, DeviceType.GAMING}));
					cbxType.setMaximumRowCount(4);
					cbxType.setFont(new Font("Tahoma", Font.PLAIN, 20));
					cbxType.setBounds(251, 105, 235, 34);
					pnlAdminAddProduct.add(cbxType);
					
					JComboBox cbxColour = new JComboBox();
					cbxColour.setModel(new DefaultComboBoxModel(DeviceColour.values()));
					cbxColour.setMaximumRowCount(4);
					cbxColour.setFont(new Font("Tahoma", Font.PLAIN, 20));
					cbxColour.setBounds(251, 201, 235, 34);
					pnlAdminAddProduct.add(cbxColour);
						
					// Radio button group for the layout of new keyboards:
					ButtonGroup layoutGroup = new ButtonGroup();	
						
						JRadioButton rbtLayoutUK = new JRadioButton("UK");
						rbtLayoutUK.setSelected(true);
						rbtLayoutUK.setFont(new Font("Tahoma", Font.PLAIN, 20));
						rbtLayoutUK.setBounds(251, 442, 116, 32);
						layoutGroup.add(rbtLayoutUK);
						pnlAdminAddProduct.add(rbtLayoutUK);
						rbtLayoutUK.setVisible(false);
						
						JRadioButton rbtLayoutUS = new JRadioButton("US");
						rbtLayoutUS.setSelected(true);
						rbtLayoutUS.setFont(new Font("Tahoma", Font.PLAIN, 20));
						rbtLayoutUS.setBounds(369, 442, 116, 32);
						layoutGroup.add(rbtLayoutUS);
						pnlAdminAddProduct.add(rbtLayoutUS);
						rbtLayoutUS.setVisible(false);
						
						// Radio button group for the name of new products:
						ButtonGroup nameGroup = new ButtonGroup();
						// This button group also changes the options available depending on whether the user is adding a mouse or a keyboard.
						
							JRadioButton rbtNameMouse = new JRadioButton("Mouse");
							rbtNameMouse.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									// Change the options available to be appropriate for adding a mouse.
									cbxType.setModel(new DefaultComboBoxModel(new DeviceType[] {DeviceType.STANDARD, DeviceType.GAMING}));
									lblKeyboardLayout.setVisible(false);
									rbtLayoutUK.setVisible(false);
									rbtLayoutUS.setVisible(false);
									lblNumberOfButtons.setVisible(true);
									ftfNumberOfButtons.setVisible(true);
								}
							});
							rbtNameMouse.setSelected(true);
							rbtNameMouse.setFont(new Font("Tahoma", Font.PLAIN, 20));
							rbtNameMouse.setBounds(251, 58, 116, 32);
							nameGroup.add(rbtNameMouse);
							pnlAdminAddProduct.add(rbtNameMouse);	
							
							JRadioButton rbtNameKeyboard = new JRadioButton("Keyboard");
							rbtNameKeyboard.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									// Change the options available to be appropriate for adding a keyboard.
									cbxType.setModel(new DefaultComboBoxModel(DeviceType.values()));
									lblNumberOfButtons.setVisible(false);
									ftfNumberOfButtons.setVisible(false);
									lblKeyboardLayout.setVisible(true);
									rbtLayoutUK.setVisible(true);
									rbtLayoutUS.setVisible(true);
								}
							});
							rbtNameKeyboard.setFont(new Font("Tahoma", Font.PLAIN, 20));
							rbtNameKeyboard.setBounds(369, 58, 116, 32);
							nameGroup.add(rbtNameKeyboard);
							pnlAdminAddProduct.add(rbtNameKeyboard);
							
						// Radio button group for the connectivity of new products:
						ButtonGroup connectivityGroup = new ButtonGroup();
							
							JRadioButton rbtConnectWired = new JRadioButton("Wired");
							rbtConnectWired.setSelected(true);
							rbtConnectWired.setFont(new Font("Tahoma", Font.PLAIN, 20));
							rbtConnectWired.setBounds(251, 256, 116, 32);
							connectivityGroup.add(rbtConnectWired);
							pnlAdminAddProduct.add(rbtConnectWired);
							
							JRadioButton rbtConnectWireless = new JRadioButton("Wireless");
							rbtConnectWireless.setSelected(true);
							rbtConnectWireless.setFont(new Font("Tahoma", Font.PLAIN, 20));
							rbtConnectWireless.setBounds(369, 256, 116, 32);
							connectivityGroup.add(rbtConnectWireless);
							pnlAdminAddProduct.add(rbtConnectWireless);
							
						JButton btnAddProduct = new JButton("Add Product");
						btnAddProduct.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// Check no textfields are empty or contain invalid values:
								if (ftfBarcode.isEditValid() == false) {
									JOptionPane.showMessageDialog(null,"No barcode has been entered.","Warning Message",JOptionPane.WARNING_MESSAGE);
								} else if (txfBrand.getText().length() == 0) {
									JOptionPane.showMessageDialog(null,"No brand has been entered.","Warning Message",JOptionPane.WARNING_MESSAGE);
								} else if ((ftfQuantityOfStock.getText().length() == 0) || (Integer.parseInt(ftfQuantityOfStock.getText()) < 1)) {
									JOptionPane.showMessageDialog(null,"The stock quantity value entered is invalid.\nThere should be at least 1 item of this product in stock.","Warning Message",JOptionPane.WARNING_MESSAGE);
								} else if ((ftfOriginalCost.getText().length() == 0) || (Float.parseFloat(ftfOriginalCost.getText()) <= 0)) {
									JOptionPane.showMessageDialog(null,"The original cost value entered is invalid.\nThe cost should be greater than £0.00 .","Warning Message",JOptionPane.WARNING_MESSAGE);
								} else if ((ftfRetailPrice.getText().length() == 0) || (Float.parseFloat(ftfRetailPrice.getText()) <= 0)) {
									JOptionPane.showMessageDialog(null,"The retail price value entered is invalid.\nThe price should be greater than £0.00 .","Warning Message",JOptionPane.WARNING_MESSAGE);
								} else {
									// Check whether the item is already in stock and ask if the user wants to overwrite the details of an existing item.
									int foundIndex = stock.search_stockList(Integer.parseInt(ftfBarcode.getText()));
									int optionChosen = -1;
									if (foundIndex != -1) {
										String[] options = {"Yes, overwrite the product", "Cancel new product"};
										optionChosen =  JOptionPane.showOptionDialog(null, "A product with this barcode already exists in the stock.\nDo you want to overwrite the details of the existing product with the new product?", "Overwrite product?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
									}
									// Product has a unique barcode and has not been canceled:
									if (optionChosen != 1) {
										// All validation checks completed so add the new product to the stock.
										DeviceConnectivity connectivity = DeviceConnectivity.WIRELESS;
										if (rbtConnectWired.isSelected()) {
											connectivity = DeviceConnectivity.WIRED;
										}
										if (rbtNameMouse.isSelected()) {
											// The last validation check for Mouse products to check button number has been entered.
											if ((ftfNumberOfButtons.getText().length() == 0) || (Integer.parseInt(ftfNumberOfButtons.getText()) < 2)) {
												JOptionPane.showMessageDialog(null,"The number of buttons entered is invalid.\nThere should be at least 2 buttons on a Mouse.","Warning Message",JOptionPane.WARNING_MESSAGE);
											} else {
												Mouse newMouse = new Mouse(Integer.parseInt(ftfBarcode.getText()), ((DeviceType)cbxType.getSelectedItem()), txfBrand.getText(), ((DeviceColour)cbxColour.getSelectedItem()), connectivity, Integer.parseInt(ftfQuantityOfStock.getText()), Float.parseFloat(ftfOriginalCost.getText()), Float.parseFloat(ftfRetailPrice.getText()), Integer.parseInt(ftfNumberOfButtons.getText()));
												if (optionChosen == 0) {
													// Overwrite existing product.
													stock.insert_stockList(newMouse, foundIndex);
												} else {
													stock.append_stockList(newMouse);
												}
												JOptionPane.showMessageDialog(null,"New mouse was successfully added to the stock.");
											}
										} else {
											KeyboardLayout layout = KeyboardLayout.UK;
											if (rbtLayoutUS.isSelected()) {
												layout = KeyboardLayout.US;
											}
											Keyboard newKeyboard = new Keyboard(Integer.parseInt(ftfBarcode.getText()), ((DeviceType)cbxType.getSelectedItem()), txfBrand.getText(), ((DeviceColour)cbxColour.getSelectedItem()), connectivity, Integer.parseInt(ftfQuantityOfStock.getText()), Float.parseFloat(ftfOriginalCost.getText()), Float.parseFloat(ftfRetailPrice.getText()), layout);
											if (optionChosen == 0) {
												// Overwrite existing product.
												stock.insert_stockList(newKeyboard, foundIndex);
											} else {
												stock.append_stockList(newKeyboard);
											}
											JOptionPane.showMessageDialog(null,"New keyboard was successfully added to the stock.");
										}
										// Refill the table with the data from the new stock list.
										fillTableWithData(dtmAllStock, stock.get_stockList(), true);
									}
								}
							}
						});
						btnAddProduct.setFont(new Font("Tahoma", Font.PLAIN, 20));
						btnAddProduct.setBounds(586, 429, 235, 44);
						pnlAdminAddProduct.add(btnAddProduct);
						
			// Panel for showing the customer accessable tabs.
			JPanel pnlCustomerAccess = new JPanel();
			layeredPane.add(pnlCustomerAccess, "name_84359346416000");
			pnlCustomerAccess.setLayout(null);
			pnlCustomerAccess.setVisible(false);
			
				// Container for the tabs accessable to the customer.
				JTabbedPane tpnCustomerTabs = new JTabbedPane(JTabbedPane.TOP);
				tpnCustomerTabs.setFont(new Font("Tahoma", Font.PLAIN, 17));
				tpnCustomerTabs.setBounds(0, 0, 847, 529);
				pnlCustomerAccess.add(tpnCustomerTabs);
				
					// Panel showing the customer's view of the shop stock.
					JPanel pnlCustomerStockView = new JPanel();
					tpnCustomerTabs.addTab("View all Products", null, pnlCustomerStockView, null);
					pnlCustomerStockView.setLayout(null);
						
						JScrollPane scpCustomerStockScroll = new JScrollPane();
						scpCustomerStockScroll.setBounds(10, 133, 822, 293);
						pnlCustomerStockView.add(scpCustomerStockScroll);
						
							// Table showing the details of all the stock to the customer.
							tblAllStockCustomer = new JTable();
							tblAllStockCustomer.setFont(new Font("Tahoma", Font.PLAIN, 16));
							tblAllStockCustomer.setRowHeight(25);
							dtmAllStockCustomer = new DefaultTableModel();
							tblAllStockCustomer.setModel(dtmAllStockCustomer);
							dtmAllStockCustomer.setColumnIdentifiers(new Object[] {"Barcode", "Name", "Type", "Brand", "Colour", "Connectivity", "Quantity in Stock", "Retail Price (£)", "Number of Buttons / Keyboard Layout"});
							scpCustomerStockScroll.setViewportView(tblAllStockCustomer);
							tblAllStockCustomer.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
							tblAllStockCustomer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							tblAllStockCustomer.getColumnModel().getColumn(1).setPreferredWidth(90);
							tblAllStockCustomer.getColumnModel().getColumn(2).setPreferredWidth(90);
							tblAllStockCustomer.getColumnModel().getColumn(3).setPreferredWidth(90);
							tblAllStockCustomer.getColumnModel().getColumn(5).setPreferredWidth(110);
							tblAllStockCustomer.getColumnModel().getColumn(6).setPreferredWidth(140);
							tblAllStockCustomer.getColumnModel().getColumn(7).setPreferredWidth(120);
							tblAllStockCustomer.getColumnModel().getColumn(8).setPreferredWidth(290);
							// Add data from the Stock class using a private method.
							fillTableWithData(dtmAllStockCustomer, stock.get_stockList(), false);
					
						// The logout button takes the user back to the welcome screen.
						JButton btnLogoutCustomer = new JButton("Logout");
						btnLogoutCustomer.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// If the user still has items in their basket, ask if they want to cancel or save their basket for later.
								if (basket.get_basketList().size() > 0) {
									int optionChosen = 1;
									String[] options = {"Save basket for later", "Cancel basket"};
									optionChosen =  JOptionPane.showOptionDialog(null, "Your basket still contains items.\nDo you want to save the contents of your basket for later or cancel it?", "Basket not empty", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
									if (optionChosen == 0) {
										// Log all items saved for later in the basket and clear the shopping basket.
										for (int i = 0; i < dtmShoppingBasket.getRowCount(); i++) {
											activityLog.create_saveLog(accounts.get_loggedInUser(), basket.get_basketList().get(i));
										}
									} else {
										// Log all items cancelled in the basket and clear the shopping basket.
										for (int i = 0; i < dtmShoppingBasket.getRowCount(); i++) {
											activityLog.create_cancelLog(accounts.get_loggedInUser(), basket.get_basketList().get(i));
										}
									}
									basket.get_basketList().clear();
									fillBasketTableWithData(dtmShoppingBasket, basket.get_basketList());
								}
								// Switch to showing the panel: pnlWelcome
								pnlCustomerAccess.setVisible(false);
								pnlWelcome.setVisible(true);
							}
						});
						btnLogoutCustomer.setFont(new Font("Tahoma", Font.PLAIN, 20));
						btnLogoutCustomer.setBounds(10, 434, 235, 34);
						pnlCustomerStockView.add(btnLogoutCustomer);
						
						// The text field for the customer to enter the amount of the product they want to add to their basket. 
						JFormattedTextField ftfAmount = new JFormattedTextField(integerFormat);
						ftfAmount.setText("1");
						ftfAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
						ftfAmount.setBounds(517, 436, 70, 31);
						pnlCustomerStockView.add(ftfAmount);
						
						// This button is for adding products to the stock. 
						JButton btnAddToBasket = new JButton("Add to basket");
						btnAddToBasket.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// Add the selected product with the specified amount to the customer's basket.
								int selectedRow = tblAllStockCustomer.getSelectedRow();
								int amount = Integer.parseInt(ftfAmount.getText());
								if ((selectedRow != -1) && (amount > 0)) {
									int foundBarcode = (int)dtmAllStockCustomer.getValueAt(selectedRow, 0);
									int optionChosen = -1;
									if (basket.search_basketList(foundBarcode) != -1) {
										// If the item is already in the customer's basket.
										String[] options = {"Yes, change the amount", "Cancel adding product to basket"};
										optionChosen =  JOptionPane.showOptionDialog(null, "This product is already in your basket.\nDo you want to change the amount of this product in your basket?", "Product already in basket", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
									}
									Product productFound = stock.get_stockList().get(stock.search_stockList(foundBarcode));
									if (amount > productFound.get_quantityInStock()) {
										// If the amount the customer wants to add is greater than the amount in stock give them a warning.
										JOptionPane.showMessageDialog(null,"The amount to add to basket is greater than the amount in stock.");
									} else {
										if (optionChosen == 0) {
											// The item is already in the customer's basket but they have chosen to edit the amount.
											basket.get_basketList().get(basket.search_basketList(foundBarcode)).set_amount(amount);
											fillBasketTableWithData(dtmShoppingBasket, basket.get_basketList());
											JOptionPane.showMessageDialog(null, "Item amount succesfully changed.");
										} else if (optionChosen != 1) {
											// Add the product to the customer's basket.
											BasketItem item = new BasketItem(productFound, amount);
											basket.append_basketList(item);
											fillBasketTableWithData(dtmShoppingBasket, basket.get_basketList());
											JOptionPane.showMessageDialog(null, "Item(s) succesfully added to basket.");
										}
									}
								} else if (amount < 1) {
									JOptionPane.showMessageDialog(null,"The amount to add to basket needs to be greater than 1.");
								} else {
									JOptionPane.showMessageDialog(null,"No product is selected.");
								}
							}
						});
						btnAddToBasket.setFont(new Font("Tahoma", Font.PLAIN, 20));
						btnAddToBasket.setBounds(597, 434, 235, 34);
						pnlCustomerStockView.add(btnAddToBasket);
						
						JLabel lblAmount = new JLabel("Amount:");
						lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 22));
						lblAmount.setBounds(421, 436, 86, 32);
						pnlCustomerStockView.add(lblAmount);
						
						JLabel lblProductFilters = new JLabel("Product Filters");
						lblProductFilters.setFont(new Font("Tahoma", Font.BOLD, 22));
						lblProductFilters.setBounds(10, 10, 184, 32);
						pnlCustomerStockView.add(lblProductFilters);
						
						JLabel lblKeyboardLayoutSearch = new JLabel("Keyboard Layout:");
						lblKeyboardLayoutSearch.setFont(new Font("Tahoma", Font.PLAIN, 22));
						lblKeyboardLayoutSearch.setBounds(10, 52, 184, 32);
						pnlCustomerStockView.add(lblKeyboardLayoutSearch);
						
						JLabel lblBrandSearch = new JLabel("Brand:");
						lblBrandSearch.setFont(new Font("Tahoma", Font.PLAIN, 22));
						lblBrandSearch.setBounds(10, 91, 184, 32);
						pnlCustomerStockView.add(lblBrandSearch);
						
						txfBrandSearch = new JTextField();
						txfBrandSearch.setFont(new Font("Tahoma", Font.PLAIN, 20));
						txfBrandSearch.setColumns(10);
						txfBrandSearch.setBounds(204, 91, 221, 31);
						pnlCustomerStockView.add(txfBrandSearch);
						
						// Radio button group for the layout search filter:
						ButtonGroup layoutSearchGroup = new ButtonGroup();
						
							JRadioButton rbtSearchLayoutUK = new JRadioButton("UK");
							rbtSearchLayoutUK.setFont(new Font("Tahoma", Font.PLAIN, 20));
							rbtSearchLayoutUK.setBounds(231, 59, 62, 21);
							layoutSearchGroup.add(rbtSearchLayoutUK);
							pnlCustomerStockView.add(rbtSearchLayoutUK);
							
							JRadioButton rbtSearchLayoutUS = new JRadioButton("US");
							rbtSearchLayoutUS.setFont(new Font("Tahoma", Font.PLAIN, 20));
							rbtSearchLayoutUS.setBounds(329, 59, 62, 21);
							layoutSearchGroup.add(rbtSearchLayoutUS);
							pnlCustomerStockView.add(rbtSearchLayoutUS);
						
						// The button for applying the customer's chosen search criteria to the stock viewed.
						JButton btnSearch = new JButton("Search");
						btnSearch.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								KeyboardLayout layoutSelected = null;
								if (rbtSearchLayoutUK.isSelected()) {
									layoutSelected = KeyboardLayout.UK;
								} else if (rbtSearchLayoutUS.isSelected()) {
									layoutSelected = KeyboardLayout.US;
								}
								String brandEntered = null;
								if (txfBrandSearch.getText().length() > 0) {
									brandEntered = txfBrandSearch.getText().trim();
								}
								// Check something has been entered for the either the keyboard layout or the brand. 
								if (!((layoutSelected == null) && (brandEntered == null))) {
									fillTableWithData(dtmAllStockCustomer, stock.getFiltered_stockList(layoutSelected, brandEntered), false);
								}
							}
						});
						btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 20));
						btnSearch.setBounds(597, 89, 235, 34);
						pnlCustomerStockView.add(btnSearch);
						
						// Clear any search filters selected and reset the view of the stock to be able to view all items.
						JButton btnClearFilters = new JButton("Clear filters");
						btnClearFilters.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								fillTableWithData(dtmAllStockCustomer, stock.get_stockList(), false);
								layoutSearchGroup.clearSelection();
								txfBrandSearch.setText(null);
							}
						});
						btnClearFilters.setFont(new Font("Tahoma", Font.PLAIN, 20));
						btnClearFilters.setBounds(597, 20, 235, 34);
						pnlCustomerStockView.add(btnClearFilters);
						
					// Panel showing the customer's basket.
					JPanel pnlCustomerBasketView = new JPanel();
					pnlCustomerBasketView.setLayout(null);
					tpnCustomerTabs.addTab("Shopping Basket", null, pnlCustomerBasketView, null);
							
						JScrollPane scpShoppingBasketScroll = new JScrollPane();
						scpShoppingBasketScroll.setBounds(10, 10, 822, 345);
						pnlCustomerBasketView.add(scpShoppingBasketScroll);
						
							// Table showing the details of all the items in the customer's basket.
							tblShoppingBasket = new JTable();
							tblShoppingBasket.setFont(new Font("Tahoma", Font.PLAIN, 16));
							tblShoppingBasket.setRowHeight(25);
							dtmShoppingBasket = new DefaultTableModel();
							tblShoppingBasket.setModel(dtmShoppingBasket);
							dtmShoppingBasket.setColumnIdentifiers(new Object[] {"Barcode", "Price of Item (£)", "Amount to Buy", "Total price of buying item(s) (£)"});
							scpShoppingBasketScroll.setViewportView(tblShoppingBasket);
							tblShoppingBasket.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
							tblShoppingBasket.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
							tblShoppingBasket.getColumnModel().getColumn(3).setPreferredWidth(260);
							
						// Button allowing the customer to clear their basket.
						JButton btnCancelBasket = new JButton("Cancel Basket");
						btnCancelBasket.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// Log all items cancelled in the basket and clear the shopping basket.
								for (int i = 0; i < dtmShoppingBasket.getRowCount(); i++) {
									activityLog.create_cancelLog(accounts.get_loggedInUser(), basket.get_basketList().get(i));
								}
								basket.get_basketList().clear();
								fillBasketTableWithData(dtmShoppingBasket, basket.get_basketList());
							}
						});
						btnCancelBasket.setFont(new Font("Tahoma", Font.PLAIN, 20));
						btnCancelBasket.setBounds(10, 434, 235, 34);
						pnlCustomerBasketView.add(btnCancelBasket);
						
						// Button allowing the customer to save their basket for later.
						JButton btnSaveForLater = new JButton("Save for Later");
						btnSaveForLater.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// Log all items saved for later in the basket and clear the shopping basket.
								for (int i = 0; i < dtmShoppingBasket.getRowCount(); i++) {
									activityLog.create_saveLog(accounts.get_loggedInUser(), basket.get_basketList().get(i));
								}
								basket.get_basketList().clear();
								fillBasketTableWithData(dtmShoppingBasket, basket.get_basketList());
							}
						});
						btnSaveForLater.setFont(new Font("Tahoma", Font.PLAIN, 20));
						btnSaveForLater.setBounds(597, 434, 235, 34);
						pnlCustomerBasketView.add(btnSaveForLater);
						
						JLabel lblBasketTotal = new JLabel("Basket Total (£):");
						lblBasketTotal.setFont(new Font("Tahoma", Font.PLAIN, 22));
						lblBasketTotal.setBounds(412, 375, 174, 32);
						pnlCustomerBasketView.add(lblBasketTotal);
						
						// Uneditable text field to show the current total of the customer's basket. 
						txfTotalCost = new JTextField();
						txfTotalCost.setText("0.00");
						txfTotalCost.setEditable(false);
						txfTotalCost.setFont(new Font("Tahoma", Font.PLAIN, 20));
						txfTotalCost.setColumns(10);
						txfTotalCost.setBounds(596, 377, 236, 31);
						pnlCustomerBasketView.add(txfTotalCost);
						
					// Panel showing the payment options for the customer and allowing them to enter their payment details to pay for their basket.
					JPanel pnlCustomerPayment = new JPanel();
					tpnCustomerTabs.addTab("Payment", null, pnlCustomerPayment, null);
					pnlCustomerPayment.setLayout(null);
					
						JLabel lblChoosePayment = new JLabel("Choose a payment method:");
						lblChoosePayment.setFont(new Font("Tahoma", Font.PLAIN, 22));
						lblChoosePayment.setBounds(10, 43, 282, 32);
						pnlCustomerPayment.add(lblChoosePayment);
							
						JLabel lblEnterEmailAddress = new JLabel("Enter your PayPal email address:");
						lblEnterEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 22));
						lblEnterEmailAddress.setBounds(10, 108, 342, 32);
						pnlCustomerPayment.add(lblEnterEmailAddress);
							
						JLabel lblBasketTotalPayment = new JLabel("Basket Total (£):");
						lblBasketTotalPayment.setFont(new Font("Tahoma", Font.PLAIN, 22));
						lblBasketTotalPayment.setBounds(10, 337, 174, 32);
						pnlCustomerPayment.add(lblBasketTotalPayment);
						
						JLabel lblEnterCardNumber = new JLabel("Enter your 16 digit card number:");
						lblEnterCardNumber.setFont(new Font("Tahoma", Font.PLAIN, 22));
						lblEnterCardNumber.setBounds(10, 108, 342, 32);
						pnlCustomerPayment.add(lblEnterCardNumber);
						lblEnterCardNumber.setVisible(false);
						
						JLabel lblEnterSecurityCode = new JLabel("Enter your 3 digit security code:");
						lblEnterSecurityCode.setFont(new Font("Tahoma", Font.PLAIN, 22));
						lblEnterSecurityCode.setBounds(10, 215, 342, 32);
						pnlCustomerPayment.add(lblEnterSecurityCode);
						lblEnterSecurityCode.setVisible(false);
						
						// Text field for entering the customer's credit card security code.
						JFormattedTextField ftfSecurityCode = new JFormattedTextField(useFormatter("###"));
						ftfSecurityCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
						ftfSecurityCode.setBounds(10, 257, 136, 31);
						pnlCustomerPayment.add(ftfSecurityCode);
						ftfSecurityCode.setVisible(false);
						
						// Text field for entering the customer's credit card number.
						JFormattedTextField ftfCardNumber = new JFormattedTextField(useFormatter("################"));
						ftfCardNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
						ftfCardNumber.setBounds(10, 150, 545, 31);
						pnlCustomerPayment.add(ftfCardNumber);
						ftfCardNumber.setVisible(false);
						
						// Radio button group for the choice of payment:
						ButtonGroup paymentGroup = new ButtonGroup();
						
							JRadioButton rbtPayPal = new JRadioButton("PayPal");
							rbtPayPal.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									lblEnterCardNumber.setVisible(false);
									lblEnterSecurityCode.setVisible(false);
									ftfSecurityCode.setVisible(false);
									ftfCardNumber.setVisible(false);
									lblEnterEmailAddress.setVisible(true);
									txfPayPalEmail.setVisible(true);
								}
							});
							rbtPayPal.setSelected(true);
							rbtPayPal.setBounds(328, 49, 103, 21);
							rbtPayPal.setFont(new Font("Tahoma", Font.PLAIN, 20));
							paymentGroup.add(rbtPayPal);
							pnlCustomerPayment.add(rbtPayPal);
							
							JRadioButton rbtCreditCard = new JRadioButton("Credit Card");
							rbtCreditCard.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									lblEnterEmailAddress.setVisible(false);
									txfPayPalEmail.setVisible(false);
									lblEnterCardNumber.setVisible(true);
									lblEnterSecurityCode.setVisible(true);
									ftfSecurityCode.setVisible(true);
									ftfCardNumber.setVisible(true);
								}
							});
							rbtCreditCard.setBounds(492, 49, 136, 21);
							rbtCreditCard.setFont(new Font("Tahoma", Font.PLAIN, 20));
							paymentGroup.add(rbtCreditCard);
							pnlCustomerPayment.add(rbtCreditCard);
							
						// Uneditable text field showing the total of the customer's basket.
						txfTotalCostPayment = new JTextField();
						txfTotalCostPayment.setText("0.00");
						txfTotalCostPayment.setFont(new Font("Tahoma", Font.PLAIN, 20));
						txfTotalCostPayment.setEditable(false);
						txfTotalCostPayment.setColumns(10);
						txfTotalCostPayment.setBounds(182, 338, 236, 31);
						pnlCustomerPayment.add(txfTotalCostPayment);
						
						// Text field for entering the customer's PayPal email address.
						txfPayPalEmail = new JTextField();
						txfPayPalEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
						txfPayPalEmail.setColumns(10);
						txfPayPalEmail.setBounds(10, 150, 545, 31);
						pnlCustomerPayment.add(txfPayPalEmail);
						
						// Button for receiving the customer's payment details, logging the payment and adjusting stock quantities. 
						JButton btnPay = new JButton("Pay for all items");
						btnPay.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// Validate the customer's payment details and pay for all items in their basket.
								if (basket.get_basketList().isEmpty()) {
									JOptionPane.showMessageDialog(null, "There are no items in your basket to pay for.");
								} else {
									String paymentMessage = "£" + currencyFormat.format(basket.calculate_totalCost());
									if (rbtPayPal.isSelected()) {
										if (email_isValid(txfPayPalEmail.getText().trim())) {
											// The email entered is valid so log each item in the basket and deduct the respective quantities from the stock.
											for (int i = 0; i < dtmShoppingBasket.getRowCount(); i++) {
												BasketItem thisItem = basket.get_basketList().get(i);
												activityLog.create_purchaseLog(accounts.get_loggedInUser(), thisItem, true);
												thisItem.get_item().remove_stockQuantity(thisItem.get_amount());
											}
											basket.get_basketList().clear();
											fillBasketTableWithData(dtmShoppingBasket, basket.get_basketList());
											stock.sort_stockList();
											fillTableWithData(dtmAllStockCustomer, stock.get_stockList(), false);
											paymentMessage += " successfully paid using PayPal.";
											JOptionPane.showMessageDialog(null, paymentMessage);
										} else {
											JOptionPane.showMessageDialog(null,"Email entered is not valid.\nPlease enter again.","Invalid email",JOptionPane.WARNING_MESSAGE);
										}
									} else {
										if ((ftfCardNumber.isEditValid() == false) || (ftfSecurityCode.isEditValid() == false)) {
											JOptionPane.showMessageDialog(null,"The card number or security code entered is invalid.\nPlease enter again.","Invalid card details",JOptionPane.WARNING_MESSAGE);
										} else {
											// The credit card number and security code are validated by the formating mask on the text field boxes so log each item and deduct the respective quantities from stock.
											for (int i = 0; i < dtmShoppingBasket.getRowCount(); i++) {
												BasketItem thisItem = basket.get_basketList().get(i);
												activityLog.create_purchaseLog(accounts.get_loggedInUser(), thisItem, false);
												thisItem.get_item().remove_stockQuantity(thisItem.get_amount());
											}
											basket.get_basketList().clear();
											fillBasketTableWithData(dtmShoppingBasket, basket.get_basketList());
											stock.sort_stockList();
											fillTableWithData(dtmAllStockCustomer, stock.get_stockList(), false);
											paymentMessage += " successfully paid using Credit Card.";
											JOptionPane.showMessageDialog(null, paymentMessage);
										}
									}
								}
							}
						});
						btnPay.setFont(new Font("Tahoma", Font.PLAIN, 20));
						btnPay.setBounds(10, 423, 235, 34);
						pnlCustomerPayment.add(btnPay);
		
	}
	
	
	// A method for creating a specific MaskFormatter to use with Formatted Text Fields.
	private MaskFormatter useFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
		}
		return formatter;
	}
	
	// A method to fill the stock view table with stock data.
	private void fillTableWithData(DefaultTableModel dtm, ArrayList<Product> list, boolean admin) {
		dtm.setRowCount(0);
		for (Product p: list) {
			Object[] rowData;
			if (p instanceof Mouse) {
				Mouse m = (Mouse)p;
				// If this is the admin table show all details, otherwise show everything except the original cost.
				if (admin) {
					rowData = new Object[] {m.get_barcode(), "Mouse", m.get_type_toString(), m.get_brand(), m.get_colour_toString(), m.get_connectivity_toString(), m.get_quantityInStock(), currencyFormat.format(m.get_originalCost()), currencyFormat.format(m.get_retailPrice()), m.get_buttonNumber()};
				} else {
					rowData = new Object[] {m.get_barcode(), "Mouse", m.get_type_toString(), m.get_brand(), m.get_colour_toString(), m.get_connectivity_toString(), m.get_quantityInStock(), currencyFormat.format(m.get_retailPrice()), m.get_buttonNumber()};
				}
			} else {
				Keyboard k = (Keyboard)p;
				if (admin) {
					rowData = new Object[] {k.get_barcode(), "Keyboard", k.get_type_toString(), k.get_brand(), k.get_colour_toString(), k.get_connectivity_toString(), k.get_quantityInStock(), currencyFormat.format(k.get_originalCost()), currencyFormat.format(k.get_retailPrice()), k.get_layout()};
				} else {
					rowData = new Object[] {k.get_barcode(), "Keyboard", k.get_type_toString(), k.get_brand(), k.get_colour_toString(), k.get_connectivity_toString(), k.get_quantityInStock(), currencyFormat.format(k.get_retailPrice()), k.get_layout()};
				}
			}
			dtm.addRow(rowData);
		}
	}
	
	// A method to fill the customer's basket table with basket item data.
	private void fillBasketTableWithData(DefaultTableModel dtm, ArrayList<BasketItem> list) {
		dtm.setRowCount(0);
		for (BasketItem i: list) {
			Object[] rowData = new Object[] {i.get_item().get_barcode(), currencyFormat.format(i.get_item().get_retailPrice()), i.get_amount(), currencyFormat.format(i.get_amount()*i.get_item().get_retailPrice())};
			dtm.addRow(rowData);
		}
		// Calculate the total cost of all items currently in the basket and display it.
		txfTotalCost.setText(currencyFormat.format(basket.calculate_totalCost()));
		txfTotalCostPayment.setText(currencyFormat.format(basket.calculate_totalCost()));
	}
	
	// A method to check whether the email address used for PayPal payment is valid.
	private boolean email_isValid(String email) { 
		if (email == null) {
			return false; 
		}
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
	}
}
