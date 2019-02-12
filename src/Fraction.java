import java.io.Serializable;

public class Fraction extends Number implements Comparable<Fraction>, Serializable {
	private static final long serialVersionUID = 6503438752759257933L;
	private long wholeNumber;
	private long numerator;
	private long denominator;
	
	public Fraction(long numerator, long denominator) {
		if(denominator <0) throw new Error("Why is the denominator negative?");
		this.wholeNumber = numerator/denominator;
		this.numerator = numerator % denominator;
		this.denominator = denominator;
		simplify();
	}
	
	public Fraction(long wholeNumber, long numerator, long denominator) {
		if(denominator <0) throw new Error("Why is the denominator negative?");
		if(numerator < 0) throw new Error("Please put the negative sign on the whole number!");
		this.wholeNumber = Math.abs(wholeNumber);
		this.wholeNumber += numerator/denominator;
		this.wholeNumber = (wholeNumber < 0)?-this.wholeNumber:this.wholeNumber;
		
		this.numerator = (wholeNumber < 0)?-numerator%denominator : numerator%denominator ;
		this.denominator = denominator;
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
		if(numerator == 0) return;
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
		if(numerator == 0)System.out.println(wholeNumber);
		else if(wholeNumber == 0) System.out.println(numerator + "/" +  denominator);
		else System.out.println(wholeNumber + "&" + Math.abs(numerator) + "/" +  denominator);
	}
	
	public void printDecimal() {
		System.out.println(doubleValue());
	}
	
	
	
	//Since this is an immutable class, we will return a new Fraction with common algebra
	
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
	
	public Fraction add(Fraction f) {
		return add(f.wholeNumber, f.numerator, f.denominator);
	}
	
	public Fraction subtract(Fraction f){
		return add(-f.wholeNumber, -f.numerator, f.denominator);
	}
	
	private Fraction multiply(long fNumerator, long fDemoninator) {
		long tempThisNumerator = (this.wholeNumber * this.denominator) + this.numerator;
		
		Fraction tempSwitch1 = new Fraction(tempThisNumerator, fDemoninator);
		Fraction tempSwitch2 = new Fraction(fNumerator, this.denominator);
		
		//attempt for further simplification before multiplying
		tempThisNumerator = (tempSwitch1.wholeNumber * tempSwitch1.denominator) + tempSwitch1.numerator;
		long tempOtherNumerator = (tempSwitch2.wholeNumber * tempSwitch2.denominator) + tempSwitch2.numerator;
		
		return new Fraction(tempThisNumerator * tempOtherNumerator, tempSwitch1.denominator * tempSwitch2.denominator);
	}
	
	public Fraction multiply(Fraction f) {
		return this.multiply((f.wholeNumber * f.denominator) + f.numerator, f.denominator);
	}
	
	public Fraction divide(Fraction f) {
		return this.multiply(f.denominator, (f.wholeNumber * f.denominator) + f.numerator);
	}
	
	public Fraction exponent(int x) {
		if(x == 0) {
			if(wholeNumber == 0 && numerator == 0) throw new Error("0 to the 0th power is undefined!!!");
			else return new Fraction(1,0,1);
		}
		Fraction f = this;
		for(int i = 1; i<x; ++i) {
			f = f.multiply(this);
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
		return (numerator < 0)?-d:d;
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

}
