
public class Main {

	public static void main(String[] args) {
		double test1 = 0.1;
		double test2 = 0.2;
		System.out.println(test1 + test2); //floating point errors! completely unacceptable!!
		
		Fraction fTest1 = new Fraction(1,10);
		Fraction fTest2 = new Fraction(2,10);
		System.out.println(fTest1.add(fTest2).doubleValue()); //clean, precise values!!
	}

}
