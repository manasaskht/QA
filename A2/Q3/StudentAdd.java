import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
public class StudentAdd extends Student{
  
public void load()
	{
		try
		{
			Scanner in = new Scanner(new FileReader("student.txt"));
			bannerID = in.next();
			firstName = in.next();
			lastName = in.next();
			email = in.next();
		}
		catch (Exception e)
		{
			System.out.println("I am a bad programmer that hid an exception.");
		}
	}
    
}