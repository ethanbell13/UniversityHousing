package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Main extends Application 
{
	private int numOfRooms;
	private TenantManager tenantManager;
	
	private Label title = new Label("University Housing");
	
	private Label roomLb1 = new Label("Room");
	private TextField roomField1 = new TextField();
	private Label nameLb = new Label("Name");
	private TextField nameField = new TextField();
	
	private Button addTenantButton = new Button("Add Tenant");
	private Button displayTenantsButton = new Button("Display Tenant");
	private Button removeTenantButton = new Button("Remove Tenant");
	
	private TextArea textArea1 = new TextArea();
	
	private Label roomLb2 = new Label("Room");
	//private Label monthLb = new Label("Month(1-12)");
	private Label amountLb = new Label("Amount");
	private TextField roomField2 = new TextField();
	private ComboBox<String> monthCombo = new ComboBox<>();
	private String month;
	//private TextField monthField = new TextField();
	private TextField amountField = new TextField();
	
	private Button makeButton = new Button("Make Payment");
	private Button listButton = new Button("List Payments");
	
	private TextArea textArea2 = new TextArea();
	
	private Button saveAndQuitButton = new Button("Save & Quit");
	
	@Override
	public void start(Stage stage) 
	{	
		initiate();
		numOfRooms = tenantManager.getNumOfRooms();
		HBox titleBox = new HBox(title);
		titleBox.setAlignment(Pos.CENTER);
		
		roomField1.setMaxWidth(50);
		HBox hBox1 = new HBox(10);
		hBox1.getChildren().addAll(roomLb1, roomField1, nameLb, nameField);
		hBox1.setAlignment(Pos.CENTER);
		
		HBox hBox2 = new HBox(10);
		addTenantButton.setOnAction(e -> addHandler());
		displayTenantsButton.setOnAction(e -> displayHandler());
		removeTenantButton.setOnAction(e -> removeHandler());
		hBox2.getChildren().addAll(addTenantButton, displayTenantsButton,
				removeTenantButton);
		hBox2.setAlignment(Pos.CENTER);
		
		monthCombo.getItems().addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		monthCombo.setValue("Month");
		monthCombo.setOnAction(e -> month = monthCombo.getValue());
		HBox hBox3 = new HBox(10);
		hBox3.getChildren().addAll(roomLb2, roomField2, monthCombo, amountLb, amountField);
		hBox3.setAlignment(Pos.CENTER);
		
		HBox hBox4 = new HBox(10);
		makeButton.setOnAction(e -> makePaymentHandler());
		listButton.setOnAction(e -> listPaymentsHandler());
		hBox4.getChildren().addAll(makeButton, listButton);
		hBox4.setAlignment(Pos.CENTER);
		
		HBox hBox5 = new HBox(10);
		saveAndQuitButton.setOnAction(e -> {
			TenantFileHandler.saveRecords(tenantManager);
			Platform.exit();
											});
		hBox5.getChildren().addAll(saveAndQuitButton);
		hBox5.setAlignment(Pos.CENTER);
		
		VBox root = new VBox(10);
		root.getChildren().addAll(titleBox, hBox1, hBox2, textArea1, hBox3,
				hBox4, textArea2, hBox5);
		
		// Check if monthAndYear key already exist and ask user if they wish
		//to change the already existing key
		
		
		Scene scene = new Scene(root, 800, 650);
		stage.setScene(scene);
		stage.setTitle("University Housing");
		stage.show();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	private void initiate() 
	{
		String path = System.getProperty("user.home") + "\\UniversityHousing\\UniversityHousing.dat";
    	if(!new File(path).exists())
    	{
    		TextInputDialog dialog = new TextInputDialog();
    		dialog.setHeaderText("How many rooms?");
    		dialog.setTitle("Room Information Request");
    		String response = dialog.showAndWait().get();
    		boolean inputValid = false;
    		while(!inputValid)
    		{
    			if(tryParse(response) == null || Integer.parseInt(response) < 0)
    			{
    				Alert numOfRoomsAlert = new Alert(AlertType.INFORMATION);
    				numOfRoomsAlert.setContentText("The number  of rooms must be a positive integer.");
    				numOfRoomsAlert.setHeaderText("Invalid Input Alert");
    				numOfRoomsAlert.showAndWait();
    				response = dialog.showAndWait().get();
    			}
    			else
    				inputValid = true;
    		}
    		tenantManager = new TenantManager(Integer.parseInt(response));
    	}
    	else
    		tenantManager = TenantFileHandler.readRecords();
	}
	private void addHandler() 
	{
		Alert addAlert = new Alert(AlertType.INFORMATION);
		addAlert.setHeaderText("Invalid Input Alert");
		textArea1.setText("");
		String name = nameField.getText();
		String rm = roomField1.getText();
		if(rm.length() == 0 || name.length() == 0) 
		{
			addAlert.setContentText("Room number and name must be entered.");
			addAlert.showAndWait();
			//textArea1.setText("Room number and name must be entered.");
		}	
		else if(name.length() > 747) 
		{
			addAlert.setContentText("Please direct tenant to Guiness Book of World Records.");
			addAlert.showAndWait();
			//textArea1.setText("Please direct tenant to Guiness Book of World Records.");
		}
		else if(tryParse(rm) == null) 
		{
			addAlert.setContentText("Room must be an integer.");
			addAlert.showAndWait();
			//textArea1.setText("Room must be an integer.");
		}
		else if(Integer.parseInt(rm) < 1 || Integer.parseInt(rm) > numOfRooms)
		{
			addAlert.setContentText("Room must be a positive number that is less than " + (numOfRooms + 1));
			addAlert.showAndWait();
			//textArea1.setText("Room must be a positive number that is less than " + (numOfRooms + 1));
		}
		else if(tenantManager.getTenants()[Integer.parseInt(rm) - 1].getName() != "Empty")
		{
			addAlert.setContentText("Room " + rm + " is occupied.");
			addAlert.showAndWait();
			//textArea1.setText("Room " + rm + " is occupied.");
		}
		else 
		{
			tenantManager.setTenant(Integer.parseInt(rm), new Tenant(name));
			roomField1.setText("");
			nameField.setText("");
		}
	}
	private void displayHandler() 
	{
		Alert displayAlert = new Alert(AlertType.INFORMATION);
		displayAlert.setHeaderText("Invalid Input Alert");
		displayAlert.setContentText("All rooms are empty");
		if(tenantManager.getRoomsOccupied() == 0) 
		{
			displayAlert.showAndWait();
			//textArea1.setText("All rooms are empty.");
		}
		else
			textArea1.setText(tenantManager.displayTenants());
	}
	private void removeHandler() 
	{
		Alert removeAlert = new Alert(AlertType.INFORMATION);
		removeAlert.setHeaderText("Invalid Input Alert");
		String rm = roomField1.getText();
		if(rm.length() == 0) 
		{
			removeAlert.setContentText("Please enter a room number.");
			removeAlert.showAndWait();
			//textArea1.setText("Please enter a room number.");
		}
		else if(tryParse(rm) == null || Integer.parseInt(rm) < 1 || Integer.parseInt(rm) > numOfRooms)
		{
			removeAlert.setContentText("Room must be a positive number that is less than " + (numOfRooms + 1));
			removeAlert.showAndWait();
			//textArea1.setText("Room must be a positive number that is less than " + (numOfRooms + 1));
		}
		else if (tenantManager.getTenants()[Integer.parseInt(rm) - 1].getName() == "Empty")
		{
			removeAlert.setContentText(Integer.parseInt(rm) + " is already empty.");
			removeAlert.showAndWait();
			//textArea1.setText(Integer.parseInt(rm) + " is already empty.");
		}
			
		else 
		{
			textArea1.setText(tenantManager.removeTenant(Integer.parseInt(rm)));
			roomField1.setText("");
		}
	}
	private void makePaymentHandler() 
	{
		Alert makePaymentAlert = new Alert(AlertType.INFORMATION);
		makePaymentAlert.setHeaderText("Invalid Input Alert");
		String amount = amountField.getText();
		String rm = roomField2.getText();
		if(rm.length() == 0 || amount.length() == 0 || month == null) 
		{
			makePaymentAlert.setContentText("Room number, payment amount, and the month must be entered.");
			makePaymentAlert.showAndWait();
			//textArea2.setText("Room number, payment amount, and the month must be entered. Month must be in number format.");
		}
		else if(tryParse(rm) == null || doubleTryParse(amount) == null) 
		{
			makePaymentAlert.setContentText("Room number must be an integer. "
					+ "Payment amount must be a number with no more than two decimal places.");
			makePaymentAlert.showAndWait();
			//textArea2.setText("Room number must be an integer. "
			//		+ "Payment amount must be a number with no more than two decimal places.");
		}
		else if(Integer.parseInt(rm) < 1 || Integer.parseInt(rm) > numOfRooms)
		{
			makePaymentAlert.setContentText("Room must be a positive number that is less than " + (numOfRooms + 1));
			makePaymentAlert.showAndWait();
			//textArea2.setText("Room must be a positive number that is less than " + (numOfRooms + 1));
		}
		else if(tenantManager.getTenants()[Integer.parseInt(rm) - 1].getName() == "Empty")
		{
			makePaymentAlert.setContentText("Room " + rm + " is not occupied.");
			makePaymentAlert.showAndWait();
			//textArea2.setText("Room " + rm + " is not occupied.");
		}
		else if(month.length() != 3)
		{
			makePaymentAlert.setContentText("Please select a month.");
			makePaymentAlert.showAndWait();
			//textArea2.setText("Please select a month.");
		}
		else if(amount.indexOf(".") != -2 && amount.substring(amount.indexOf(".") + 1).length() > 2)
		{
			makePaymentAlert.setContentText("Payment amount must be a number with no more than two decimal places.");
			makePaymentAlert.showAndWait();
			//textArea2.setText("Payment amount must be a number with no more than two decimal places.");
		}
		else
		{
			tenantManager.getTenants()[Integer.parseInt(rm) - 1].recordPayment(monthToInt(month), 
					Double.parseDouble(amount));
			roomField2.setText("");
			amountField.setText("");
			textArea2.setText("Payment recorded.");
		}
	}
	private void listPaymentsHandler() 
	{
		Alert listPaymentAlert = new Alert(AlertType.INFORMATION);
		String rm = roomField2.getText();
		if(rm.length() == 0)
		{
			listPaymentAlert.setContentText("Please enter room number.");
			listPaymentAlert.showAndWait();
			//textArea2.setText("Please enter room number.");
		}
		else if(tryParse(rm) == null)
		{
			listPaymentAlert.setContentText("Room number must be a positive integer that is less than " + (numOfRooms + 1));
			listPaymentAlert.showAndWait();
			//textArea2.setText("Room number must be a positive integer that is less than " + (numOfRooms + 1));
		}
		else if(Integer.parseInt(rm) < 1 || Integer.parseInt(rm) > numOfRooms)
		{
			listPaymentAlert.setContentText("Room must be a positive number that is less than " + (numOfRooms + 1));
			listPaymentAlert.showAndWait();
			//textArea2.setText("Room must be a positive number that is less than " + (numOfRooms + 1));
		}
		else if(tenantManager.getTenants()[Integer.parseInt(rm) - 1].getName() == "Empty")
		{
			listPaymentAlert.setContentText("Room " + rm + " is not occupied.");
			listPaymentAlert.showAndWait();
			//textArea2.setText("Room " + rm + " is not occupied.");
		}
		else
		{
			textArea2.setText(tenantManager.getTenants()[Integer.parseInt(rm) - 1].listPayments());
			roomField2.setText("");
		}
	}
	private static Integer tryParse(String text) 
	{
		  try {return Integer.parseInt(text);} 
		  catch (NumberFormatException e) {return null;}
	}
	private static Double doubleTryParse(String text) 
	{
		  try {return Double.parseDouble(text);} 
		  catch (NumberFormatException e) {return null;}
	}
	private int monthToInt(String month) 
	{
		String[] months = new String[] 
				{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		for(int i = 0; i < 12; i++) 
		{
			if(months[i] == month)
				return i + 1;
		}
		return -999;
	}
}
/*
 * public class Main { public static void main(String[] args) { TenantManager
 * tenants = new TenantManager(); tenants.AddTenant(new Tenant(12, "Jacob"));
 * System.out.println(tenants.DisplayTenants()); } }
 */
