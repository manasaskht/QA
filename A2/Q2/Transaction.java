public  abstract class Transaction extends Account
{
     protected float balance;
    public float getBalance()
	{
		return balance;
	}
    public  abstract void credit(float amount);
    public  abstract void debit(float amount);
}