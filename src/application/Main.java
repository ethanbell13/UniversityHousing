package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.geometry.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

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
	private Label monthLb = new Label("Month(1-12)");
	private Label amountLb = new Label("Amount");
	private TextField roomField2 = new TextField();
	private TextField monthField = new TextField();
	private TextField amountField = new TextField();
	
	private Button makeButton = new Button("Make Payment");
	private Button listButton = new Button("List Payments");
	
	private TextArea textArea2 = new TextArea();
	
	@Override
	public void start(Stage stage) 
	{	
		numOfRooms = getNumOfRooms();
		tenantManager = new TenantManager(numOfRooms);
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
		
		HBox hBox3 = new HBox(10);
		hBox3.getChildren().addAll(roomLb2, roomField2, monthLb, 
				monthField, amountLb, amountField);
		hBox3.setAlignment(Pos.CENTER);
		
		HBox hBox4 = new HBox(10);
		makeButton.setOnAction(e -> makePaymentHandler());
		listButton.setOnAction(e -> listPaymentsHandler());
		hBox4.getChildren().addAll(makeButton, listButton);
		hBox4.setAlignment(Pos.CENTER);
		
		VBox root = new VBox(10);
		root.getChildren().addAll(titleBox, hBox1, hBox2, textArea1, hBox3,
				hBox4, textArea2);
		
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
	private int getNumOfRooms() 
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
				dialog.setHeaderText("The number  of rooms must be a positive integer.");
				response = dialog.showAndWait().get();
			}
			else
				inputValid = true;
		}
		return Integer.parseInt(response);
		
	}
	private void addHandler() 
	{
		textArea1.setText("");
		String name = nameField.getText();
		String rm = roomField1.getText();
		if(rm.length() == 0 || name.length() == 0)
			textArea1.setText("Room number and name must be entered.");
		else if(name.length() > 747) 
			textArea1.setText("Please direct tenant to Guiness Book of World Records.");
		else if(tryParse(rm) == null) 
			textArea1.setText("Room must be an integer.");
		else if(Integer.parseInt(rm) < 1 || Integer.parseInt(rm) > numOfRooms)
			textArea1.setText("Room must be a positive number that is less than " + (numOfRooms + 1));
		else if(tenantManager.getTenants()[Integer.parseInt(rm) - 1].getName() != "Empty")
				textArea1.setText("Room " + rm + " is occupied.");
		else 
		{
			tenantManager.setTenant(Integer.parseInt(rm), new Tenant(name));
			roomField1.setText("");
			nameField.setText("");
		}
	}
	private void displayHandler() 
	{
		if(tenantManager.getRoomsOccupied() == 0)
			textArea1.setText("All rooms are empty.");
		else
			textArea1.setText(tenantManager.displayTenants());
	}
	private void removeHandler() 
	{
		String rm = roomField1.getText();
		if(rm.length() == 0)
			textArea1.setText("Please enter a room number.");
		else if(tryParse(rm) == null || Integer.parseInt(rm) < 1 || Integer.parseInt(rm) > numOfRooms)
			textArea1.setText("Room must be a positive number that is less than " + (numOfRooms + 1));
		else if (tenantManager.getTenants()[Integer.parseInt(rm) - 1].getName() == "Empty")
			textArea1.setText(Integer.parseInt(rm) + " is already empty.");
		else 
		{
			textArea1.setText(tenantManager.removeTenant(Integer.parseInt(rm)));
			roomField1.setText("");
		}
	}
	private void makePaymentHandler() 
	{
		String rm = roomField2.getText();
		String month = monthField.getText();
		String amount = amountField.getText();
		if(rm.length() == 0 || amount.length() == 0 || month.length() == 0)
			textArea2.setText("Room number, payment amount, and the month must be entered. Month must be in number format.");
		else if(tryParse(rm) == null || tryParse(month) == null || doubleTryParse(amount) == null) 
		{
			textArea2.setText("Room number and month must be integers. "
					+ "Payment amount must be a number with no more than two decimal places.");
		}
		else if(Integer.parseInt(rm) < 1 || Integer.parseInt(rm) > numOfRooms)
			textArea2.setText("Room must be a positive number that is less than " + (numOfRooms + 1));
		else if(tenantManager.getTenants()[Integer.parseInt(rm) - 1].getName() == "Empty")
			textArea2.setText("Room " + rm + " is not occupied.");
		else if(Integer.parseInt(month) < 0 || Integer.parseInt(month) > 12)
			textArea2.setText("Please enter the number value of the month. eg. January = 1.");
		else if(amount.indexOf(".") != -1 && amount.substring(amount.indexOf(".") + 1).length() > 2)
			textArea2.setText("Payment amount must be a number with no more than two decimal places.");
		else
		{
			tenantManager.getTenants()[Integer.parseInt(rm) - 1].recordPayment(Integer.parseInt(month), 
					Double.parseDouble(amount));
			roomField2.setText("");
			monthField.setText("");
			amountField.setText("");
			textArea2.setText("Payment recorded.");
		}
	}
	private void listPaymentsHandler() 
	{
		String rm = roomField2.getText();
		if(rm.length() == 0)
			textArea2.setText("Please enter room numner.");
		else if(tryParse(rm) == null)
			textArea2.setText("Room number must be a positive integer that is less than " + (numOfRooms + 1));
		else if(Integer.parseInt(rm) < 1 || Integer.parseInt(rm) > numOfRooms)
			textArea2.setText("Room must be a positive number that is less than " + (numOfRooms + 1));
		else if(tenantManager.getTenants()[Integer.parseInt(rm) - 1].getName() == "Empty")
			textArea2.setText("Room " + rm + " is not occupied.");
		else
		{
			textArea2.setText(tenantManager.getTenants()[Integer.parseInt(rm) - 1].listPayments());
			roomField2.setText("");
		}
	}
	private static Integer tryParse(String text) 
	{
		  try {
		    return Integer.parseInt(text);
		  } catch (NumberFormatException e) {
		    return null;
		  }
	}
	private static Double doubleTryParse(String text) 
	{
		  try {
		    return Double.parseDouble(text);
		  } catch (NumberFormatException e) {
		    return null;
		  }
	}
}
/*
 * public class Main { public static void main(String[] args) { TenantManager
 * tenants = new TenantManager(); tenants.AddTenant(new Tenant(12, "Jacob"));
 * System.out.println(tenants.DisplayTenants()); } }
 */
