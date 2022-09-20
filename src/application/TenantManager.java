package application;

public class TenantManager 
{
	protected Tenant[] tenants = new Tenant[0];
	protected TenantManager() {}
	protected TenantManager(Tenant[] tenantsIn)
	{
		tenants = tenantsIn;
	}
	protected void AddTenant(Tenant tenant) 
	{
		Tenant[] newTenants = new Tenant[tenants.length + 1];
		for(int i = 0; i < tenants.length; i++) 
			newTenants[i] = tenants[i];
		newTenants[tenants.length] = tenant;
		tenants = newTenants;
	}
	protected String DisplayTenants() 
	{
		String display = "Room    Name\n";
		for(int i = 0; i < tenants.length; i++)
		{
			Tenant tenant = tenants[i];
			display += tenant.rm;
			if(tenant.rm < 10)
				display += "      ";
			else if(tenant.rm < 100) 
				display += "    ";
			else if(tenant.rm < 1000)
				display += "  ";
			display += "      " + tenant.name + "\n";
		}
		return display;
	}
	//Need to refactor for bug. If rm number not found 
	//one item is removed from list
	protected String RemoveTenant(int rmEntered) 
	{
		String result = "";
		if(tenants.length == 0)
			return "There are no tenants to remove.";
		else 
		{
			Tenant[] newTenants = new Tenant[tenants.length - 1];
			int j = 0;
			for(int i = 0; i < tenants.length; i++) 
			{
				if(tenants[i].rm == rmEntered) 
				{
					result = tenants[i].name + " has been removed from room " + tenants[i].rm;
					if(i != tenants.length - 1)
					{
						j++;
						newTenants[i] = tenants[j];
					}
				}
				else if(j < newTenants.length)
					newTenants[i] = tenants[j];
				j++;
			}
			tenants = newTenants;
		}
		return result;
	}
	protected boolean CheckIfRmIsOccupied(int rmEntered) 
	{
		for(int i = 0; i < tenants.length; i++) 
		{
			if(tenants[i].rm == rmEntered) 
				return true;
		}
		return false;
	}
}
