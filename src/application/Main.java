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
import javafx.scene.text.Font;
import javafx.geometry.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

public class Main extends Application 
{
	private int numOfRooms;
	private TenantManager tenantManager = new TenantManager();
	
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
	private Label monthLb = new Label("Month");
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
		
		HBox titleBox = new HBox(title);
		titleBox.setAlignment(Pos.CENTER);
		
		roomField1.setMaxWidth(50);
		HBox hBox1 = new HBox(10);
		hBox1.getChildren().addAll(roomLb1, roomField1, nameLb, nameField);
		hBox1.setAlignment(Pos.CENTER);
		
		HBox hBox2 = new HBox(10);
		addTenantButton.setOnAction(e -> addHandler());
		displayTenantsButton.setOnAction(e -> displayHandler());
		removeTenantButton.setOnAction(e -> removeHandler(roomField1.getText()));
		hBox2.getChildren().addAll(addTenantButton, displayTenantsButton,
				removeTenantButton);
		hBox2.setAlignment(Pos.CENTER);
		
		HBox hBox3 = new HBox(10);
		hBox3.getChildren().addAll(roomLb2, roomField2, monthLb, 
				monthField, amountLb, amountField);
		hBox3.setAlignment(Pos.CENTER);
		
		HBox hBox4 = new HBox(10);
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
		if(tryParse(response) == null) 
		{
			dialog.setHeaderText("The number  of rooms must be a positive integer.");
			response = dialog.showAndWait().get();
		}
		int num = Integer.parseInt(response);
		if(num < 1)
		{
			dialog.setHeaderText("The number  of rooms must be a positive integer.");
			response = dialog.showAndWait().get();			
		}
		return num;
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
		else if(Integer.parseInt(rm) < 1 )// || Integer.parseInt(rm) > numOfRms)
			textArea1.setText("Please enter a positive number that is less than "); //+ numOfRms);
		else if(tenantManager.CheckIfRmIsOccupied(Integer.parseInt(rm)))
				textArea1.setText("Room " + rm + " is occupied.");
		else 
		{
			tenantManager.AddTenant(new Tenant(Integer.parseInt(rm), name));
			roomField1.setText("");
			nameField.setText("");
		}
	}
	private void displayHandler() 
	{
		if(tenantManager.tenants.length == 0)
			textArea1.setText("All rooms are empty.");
		else
			textArea1.setText(tenantManager.DisplayTenants());
	}
	private void removeHandler(String rmEntered) 
	{
		if(rmEntered.length() == 0)
			textArea1.setText("Please enter a room number.");
		else if(tryParse(rmEntered) == null)// || Integer.parseInt(rm) > numOfRms)
			textArea1.setText("Room must be an integer."); //+ numOfRms);
		else 
		{
			int rm = Integer.parseInt(rmEntered);
			String result = tenantManager.RemoveTenant(rm);
			if(result == null)
				textArea1.setText(rmEntered + " is not occupied.");
			else if(rm < 1 )// || Integer.parseInt(rm) > numOfRms)
				textArea1.setText("Please enter a positive integer that is less than "); //+ numOfRms);
			else 
				textArea1.setText(result);
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
}
/*
 * public class Main { public static void main(String[] args) { TenantManager
 * tenants = new TenantManager(); tenants.AddTenant(new Tenant(12, "Jacob"));
 * System.out.println(tenants.DisplayTenants()); } }
 */
