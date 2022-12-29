package application;

import java.io.Serializable;

public class TenantManager implements Serializable
{
	private Tenant[] tenants;
	private int roomsOccupied = 0;
	public int numOfRooms;
	
	public TenantManager(int numOfRoomsIn)
	{
		numOfRooms = numOfRoomsIn;
		tenants = new Tenant[numOfRooms];
		for(int i = 0; i < numOfRooms; i++)
			tenants[i] = new Tenant();
	}
	public Tenant[] getTenants() {return tenants;}
	public int getRoomsOccupied() {return roomsOccupied;}
	public int getNumOfRooms() {return numOfRooms;}
	public Tenant getTenant(int position) {return tenants[position];}
	public void setTenant(int room, Tenant tenant) 
	{
		tenants[room - 1] = tenant;
		roomsOccupied++;
	}
	public String displayTenants() 
	{
		String display = "Room    Name\n";
		for(int i = 0; i < tenants.length; i++)
		{
			if(tenants[i].getName() != "Empty")
			{
				Tenant tenant = tenants[i];
				display += (i + 1);
				if(i < 10)
					display += "      ";
				else if(i < 100) 
					display += "    ";
				else if(i < 1000)
					display += "  ";
				display += "      " + tenant.getName() + "\n";
			}
		}
		return display;
	}
	public String removeTenant(int rmEntered) 
	{
		String result = tenants[rmEntered - 1].getName() + " has been removed from room " + rmEntered;
		tenants[rmEntered -1] = new Tenant();
		roomsOccupied--;
		return result;
	}
}
