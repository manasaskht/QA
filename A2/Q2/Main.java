public class Main
{
	public static void main(String[] args)
	{
		USDollarAccount Acc = new USDollarAccount();
		
		Acc.credit(5f);
        Acc.debit(6f);
        
        System.out.println(Acc.balance);
		
		
	}
}