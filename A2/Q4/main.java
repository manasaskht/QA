public class main {

	public static void main(String[] args) {
    IEmployer[] Employers = new IEmployer[10];
        for (int i = 0; i < 5; i++)
		{
			Employers[i]=new SalaryWorker();
		}
        for (int i = 0; i < 5; i++)
		{
			Employers[i]=new HourlyWorker();
		}

		Employer E = new Employer();
		E.outputWageCostsForAllStaff(Employers, 5);

	}

}
