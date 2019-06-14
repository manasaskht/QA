public class SpecialOffer extends Customer
{

public SpecialOffer()
	{
		super();
	}
    public void emailCustomerSpecialOffer()
	{
		String msg = "Congratulations! Your purchase history has earned you a 10% discount on your next purchase!";
		EmailSender sender = new EmailSender();
		sender.sendEmail(email, "10% off your next order!", msg);
	}
}