import java.io.*;
import java.util.Scanner;
import java.io.PrintWriter;


public class Student
{
	String bannerID;
	String firstName;
	String lastName;
	String email;

	public Student()
	{
		bannerID = null;
		firstName = null;
		lastName = null;
		email = null;
	}
 public String getBannerID() {
		return bannerID;
	}
	public void setBannerID(String bannerID)
	{
		this.bannerID = bannerID;
	}
    
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
    public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
     public String getEmail() {
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}

	public void save()
	{
		try
		{
			PrintWriter writer = new PrintWriter("student.txt", "UTF-8");
			writer.println(bannerID);
			writer.println(firstName);
			writer.println(lastName);
			writer.println(email);
			writer.close();
		}
		catch (Exception e)
		{
			System.out.println("I am a bad programmer that hid an exception.");
		}
	}

	
}