public class main {

	public static void main(String[] args) {
		
		StudentAdd A = new StudentAdd();
		A.load();
        Student A1=new Student();
		System.out.println(A1.getBannerID() + A1.getEmail() + A1.getFirstName() + A1.getLastName());
		A1.save();
	}

}