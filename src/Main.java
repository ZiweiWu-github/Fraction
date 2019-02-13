
public class Main {

	public static void main(String[] args) {
		Fraction fraction = new Fraction(1,1);
		fraction = fraction.subtract(new Fraction(0,7));
//		fraction = fraction.multiply(new Fraction(22, 7));
		//fraction = fraction.add(new Fraction(22, 6));
		fraction.printFraction();
		fraction.printDecimal();
	}

}
