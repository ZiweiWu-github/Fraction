
public class Main {

	public static void main(String[] args) {
		Fraction fraction = new Fraction(10,0,7);
		fraction = fraction.exponent(3);
		//fraction = fraction.add(new Fraction(22, 6));
		fraction.printFraction();
		fraction.printDecimal();
	}

}
