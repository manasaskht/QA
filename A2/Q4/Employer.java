

public class Employer 
{
	
	public void outputWageCostsForAllStaff(IEmployer[] Employers, int hours)
	{
		float cost = 0.0f;
		for (int i = 0; i < Employers.length; i++)
		{
			
			cost += Employers[i].calculatePay(hours);
		}
	
		System.out.println("Total wage cost for all staff = $" + cost);
	}
}