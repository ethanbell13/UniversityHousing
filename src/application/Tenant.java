package application;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;


	public class Tenant
{
		protected Map<String, Double> payHis = new HashMap<>();
		protected String name;
		protected int rm;
		
		protected Tenant(int roomIn, String nameIn)
		{
			name = nameIn;
			rm = roomIn;
		}
		protected void RecordPayment(String yearMonth, Double amt) 
		{
			payHis.put(yearMonth, amt);
		}
		protected String ListPayments() 
		{
			String payments = "Month and Year    Amount\n";
			Set<String> keys = payHis.keySet();
			int total = 0;  
			for(String key: keys) 
			{
				total += payHis.get(key);
				payments += key + "            " + payHis.get(key) + "\n";
			}
			return payments + "Total Paid So Far: $" + total;
		}
}
