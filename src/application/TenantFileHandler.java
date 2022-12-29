package application;

import java.io.*;

class TenantFileHandler
{
    public static void saveRecords(TenantManager tenantManagerIn)
    {
    	String path = System.getProperty("user.home") + "\\UniversityHousing";
    	new File(path  + "\\UniversityHousing").mkdirs(); 
    	try
    	(
    		FileOutputStream tenantManagerFile = new FileOutputStream(path + "\\UniversityHousing.txt");
    		ObjectOutputStream tenantManagerWriter = new ObjectOutputStream(tenantManagerFile);
    	)
    	{
    		tenantManagerWriter.writeObject(tenantManagerIn);
    	}
    	catch(IOException e) 
    	{
    		System.out.println("There was a problem writing the file.");
    	}
    }
    public static TenantManager readRecords() 
    {
    	boolean endOfFile = false;
    	String path = System.getProperty("user.home") + "\\UniversityHousing\\UniversityHousing.dat";
    	try(
    			FileInputStream tenantManagerFile = new FileInputStream(path);
    			ObjectInputStream tenantManagerWriter = new ObjectInputStream(tenantManagerFile);
    		)
    		{
    			return (TenantManager) tenantManagerWriter.readObject();
    		}
		catch(Exception e)
		{
			System.out.println("\nThere was a problem reading the file");
			return new TenantManager(1);
		}
    }
}