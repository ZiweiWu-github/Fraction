import java.math.BigInteger;

public class Main {
	
	public static BigInteger factorial(int n) {
		if(n==0 || n==1) return BigInteger.ONE;
		BigInteger fact = BigInteger.valueOf(n);
		
		
		for(int i = n-1; i>0; --i) {
			fact = fact.multiply(BigInteger.valueOf(i));
		}
		
		return fact;
	}
	
	public static void main(String[] args) {
//		double test1 = 0.1;
//		double test2 = 0.2;
//		System.out.println(test1 + test2); //floating point errors! completely unacceptable!!
//		
//		Fraction fTest1 = new Fraction(1,10);
//		Fraction fTest2 = new Fraction(2,10);
//		System.out.println(fTest1.subtract(fTest2).doubleValue()); //clean, precise values!!
		
		//Euler's number with BigFraction
		BigFraction f = new BigFraction(BigInteger.ONE, factorial(0));
		for(int i = 1; i < 100; ++i) {
			f = f.add(new BigFraction(BigInteger.ONE, factorial(i)));
		}
		f.printDecimal();
		
		
	}

}
