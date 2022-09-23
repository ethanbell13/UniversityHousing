package application;

public class Tenant
{
	private String name;
	//Vaue of -1 signifies no payment entered.
	private double[] payHis = new double[] {-1, -1, -1, -1, -1, -1,-1, -1, -1, -1, -1, -1};
	public Tenant() {name = "Empty";}										
	public Tenant(String nameIn)
	{
		name = nameIn;
	}
	public double[] getPayHis() {return payHis;}
	public String getName() {return name;}
	public void recordPayment(int month, Double amount) {payHis[month -1] = amount;}
	public String listPayments() 
	{
		String[] month = new String[]
				 {"January", "February", "March", "April", "May", "June", 
				  "July", "August", "September", "October", "November", "December"};
		String payments = "Month and Year    Amount\n";
		double total = 0;  
		for(int i = 0; i < 12; i++) 
		{
			if(payHis[i] != -1) 
			{
				total += payHis[i];
				payments += month[i] + "             $" + payHis[i] + "\n";
			}
		}
		return payments + "Total Paid So Far: $" + total;
	}
}
