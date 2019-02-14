import java.io.Serializable;

public class Fraction extends Number implements Comparable<Fraction>, Serializable {
	private static final long serialVersionUID = 6503438752759257933L;
	
	/**
	 * The wholeNumber and numerator should have the same sign (easier math)
	 * while the denominator should always be positive
	 */
	private long wholeNumber;
	private long numerator;
	private long denominator;
	
	public Fraction(long numerator, long denominator) {
		if(denominator == 0)throw new Error("You can't divide by 0!");
		if(denominator <0) throw new Error("Why is the denominator negative?");
		this.wholeNumber = numerator/denominator;
		this.numerator = numerator % denominator;
		this.denominator = denominator;
		simplify();
	}
	
	public Fraction(long wholeNumber, long numerator, long denominator) {
		if(denominator == 0)throw new Error("You can't divide by 0!");
		if(denominator <0) throw new Error("Why is the denominator negative?");
		if(numerator < 0) throw new Error("Please put the negative sign on the whole number!");
		this.denominator = denominator;
		this.numerator = (wholeNumber < 0)?-numerator:numerator;
		this.wholeNumber = wholeNumber + (this.numerator / this.denominator);
		this.numerator %= this.denominator;
		simplify();
	}
	
	private long GCD(long n1, long n2) {
		n1 = ( n1 > 0) ? n1 : -n1;
        n2 = ( n2 > 0) ? n2 : -n2;
		while(n1 != n2){
            if(n1 > n2)
                n1 -= n2;
            else
                n2 -= n1;
        }
		return n1;
	}
	
	private void simplify() {
		if(numerator == 0) {
			denominator = 1;
			return;
		}
		long divide = 1;
		while((divide = GCD(numerator, denominator)) != 1) {
			numerator/=divide;
			denominator/=divide;
		}
	}
	
	/**
	 * Prints the fraction out.
	 * If the numerator is 0, then it will only print out the whole number
	 * If the whole number is 0, then only the fraction is printed
	 * Otherwise, it will print out "wholeNumber&numerator/denominator"
	 */
	public void printFraction() {
		System.out.println(this.toString());
	}
	
	/**
	 * Prints out the fraction as a double.
	 */
	public void printDecimal() {
		System.out.println(doubleValue());
	}
	
	//helper function for add and subtract
	private Fraction add(long fWholeNumber, long fNumerator, long fDenominator) {
		if(this.denominator == fDenominator) {
			return new Fraction(this.wholeNumber + fWholeNumber, 
					this.numerator + fNumerator, this.denominator);
		}
		else {
			long tempWholeNum  = this.wholeNumber + fWholeNumber;
			long tempNumerator = (tempWholeNum==0)?(this.numerator * fDenominator) + (fNumerator * this.denominator):
				Math.abs((this.numerator * fDenominator) + (fNumerator * this.denominator));
			
			return (tempWholeNum == 0)?
				new Fraction(tempNumerator, fDenominator * this.denominator):
				new Fraction(tempWholeNum, tempNumerator, fDenominator * this.denominator);
		}
	}
	
	/**
	 * Returns a Fraction that is this fraction added with the Fraction f.
	 * @param f: A Fraction that is not null.
	 * @return A fraction whose value is this + f
	 */
	public Fraction add(Fraction f) {
		return add(f.wholeNumber, f.numerator, f.denominator);
	}
	
	/**
	 * Returns a Fraction that is this Fraction subtract the inputed Fraction f.
	 * @param f: A Fraction that is not null.
	 * @return A Fraction whose value is this - f
	 */
	public Fraction subtract(Fraction f){
		return add(-f.wholeNumber, -f.numerator, f.denominator);
	}
	
	//helper function for multiply and divide
	private Fraction multiply(long fNumerator, long fDemoninator) {
		long tempThisNumerator = (this.wholeNumber * this.denominator) + this.numerator;
		long tempThisDenominator = this.denominator;
		
		long divide = 1;
		while(tempThisNumerator != 0 &&(divide = GCD(tempThisNumerator, fDemoninator))!=1) {
			tempThisNumerator/=divide;
			fDemoninator /=divide;
		}
		while(fNumerator != 0 && (divide = GCD(fNumerator, tempThisDenominator))!= 1) {
			fNumerator/=divide;
			tempThisDenominator/=divide;
		}
		
		return new Fraction(tempThisNumerator * fNumerator, tempThisDenominator * fDemoninator);
	}
	
	/**
	 * Returns a Fraction that is this Fraction multiplied by the inputed Fraction f.
	 * @param f: A Fraction that is not null.
	 * @return A Fraction whose value is this * f.
	 */
	public Fraction multiply(Fraction f) {
		return this.multiply((f.wholeNumber * f.denominator) + f.numerator, f.denominator);
	}
	
	/**
	 * Returns a Fraction that is this Fraction divided by the inputed Fraction f.
	 * @param f: A Fraction that is not null.
	 * @return A Fraction whose value is this/f.
	 */
	public Fraction divide(Fraction f) {
		if(f.wholeNumber == 0 && f.numerator == 0) throw new Error("Cannot divide by 0!!");
		return this.multiply(f.denominator, (f.wholeNumber * f.denominator) + f.numerator);
	}
	
	/**
	 * Return a Fraction that is this Fraction to the xth power.
	 * @param x: An integer.
	 * @return A Fraction whose value is this to the xth power.
	 */
	public Fraction exponent(int x) {
		if(x == 0) {
			if(wholeNumber == 0 && numerator == 0) throw new Error("0 to the 0th power is undefined!!!");
			else return new Fraction(1,1);
		}
		Fraction f = (x > 0)? this: new Fraction((wholeNumber < 0 || numerator <0)?-denominator:denominator, Math.abs((wholeNumber * denominator)+ numerator));
		Fraction multiply = f;
		
		for(int i = 1; i<Math.abs(x); ++i) {
			f = f.multiply(multiply);
		}
		return f;
	}
	
	
	//implement Comparable<Fraction>
	@Override
	public int compareTo(Fraction f) {
		double comp = this.doubleValue() - f.doubleValue();
		if(comp < 0) return -1;
		else if(comp > 0) return 1;
		else return 0;
	}

	//The below methods are for the Number class
	@Override
	public double doubleValue() {
		double d = Math.abs(wholeNumber);
		d += (Math.abs(numerator)*1.0/denominator);
		return (wholeNumber < 0 || numerator < 0)?-d:d;
	}

	@Override
	public float floatValue() {
		return (float) doubleValue();
	}

	@Override
	public int intValue() {
		return (int) wholeNumber;
	}

	@Override
	public long longValue() {
		return wholeNumber;
	}
	
	//The toString() method returns the String that would've been printed out with the printFraction() method
	@Override
	public String toString() {
		String s = "";
		if(numerator == 0) s = Long.toString(wholeNumber);
		else if(wholeNumber == 0) s = Long.toString(numerator) + "/" + Long.toString(denominator);
		else s = Long.toString(wholeNumber) + "&" + Long.toString(numerator) + "/" + Long.toString(denominator);
		return s;
	}
}
