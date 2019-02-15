import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public final class BigFraction extends Number implements Comparable<BigFraction>{
	private static final long serialVersionUID = 4519423672338393848L;
	
	BigInteger numerator, denominator;
	
	public BigFraction(BigInteger numerator, BigInteger denominator) {
		if(denominator.compareTo(BigInteger.ZERO) == -1) throw new Error("Why is the denominator negative?");
		if(denominator.compareTo(BigInteger.ZERO) == 0)throw new Error("You cannot divide by 0!");
		this.numerator = numerator;
		this.denominator = denominator;
		simplify();
	}
	
	public BigFraction(String numerator, String denominator) {
		this(new BigInteger(numerator), new BigInteger(denominator));
	}
	
	public BigFraction(long numerator, long denominator) {
		this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
	}
	
	private BigInteger GCD(BigInteger n1, BigInteger n2) {
		n1 = n1.abs();
		n2 = n2.abs();
		
		while( n1.compareTo(n2) != 0) {
			if(n1.compareTo(n2) == 1)
				n1 = n1.subtract(n2);
			else 
				n2 = n2.subtract(n1);
		}
		return n1;
	}
	
	private void simplify() {
		if(numerator.compareTo(BigInteger.ZERO) == 0) {
			denominator = BigInteger.ONE;
			return;
		}
		if(numerator.compareTo(BigInteger.ONE) == 0
				|| denominator.compareTo(BigInteger.ONE) == 0) return;
		
		BigInteger divide = new BigInteger("1");
		while((divide = GCD(numerator, denominator)).compareTo(BigInteger.ONE) != 0  ) {
			numerator = numerator.divide(divide);
			denominator = denominator.divide(divide);
		}
	}
	
	public void printFraction() {
		System.out.println(toString());
	}
	
	public void printDecimal() {
		System.out.println(asBigDecimal());
	}
	
	public BigDecimal asBigDecimal() {
		BigDecimal d = new BigDecimal(numerator);
		d = d.divide(new BigDecimal(denominator), MathContext.DECIMAL128);
		return d;
	}
	
	private BigFraction add(BigInteger fNumerator, BigInteger fDenominator) {
		if(this.denominator.compareTo(fDenominator) == 0) {
			return new BigFraction(this.numerator.add(fNumerator), this.denominator);
		}
		else {
			return new BigFraction(
					this.numerator.multiply(fDenominator).add(fNumerator.multiply(this.denominator)),
					this.denominator.multiply(fDenominator));
		}
	}
	
	public BigFraction add(BigFraction f) {
		return this.add(f.numerator, f.denominator);
	}
	
	public BigFraction subtract(BigFraction f) {
		return this.add(f.numerator.multiply(BigInteger.valueOf(-1)), f.denominator);
	}
	
	private BigFraction multiply(BigInteger fNumerator, BigInteger fDenominator) {
		return new BigFraction(fNumerator.multiply(this.numerator), fDenominator.multiply(this.denominator));
	}
	
	public BigFraction multiply(BigFraction f) {
		return this.multiply(f.numerator, f.denominator);
	}
	
	public BigFraction divide(BigFraction f) {
		return this.multiply(
			(f.numerator.compareTo(BigInteger.ZERO) == -1)?f.denominator.multiply(BigInteger.valueOf(-1)):f.denominator,
			f.numerator.abs());
	}
	
	public BigFraction exponent(int n) {
		if(n == 0) {
			if(numerator.compareTo(BigInteger.ZERO) == 0)throw new Error("0 to the 0th power is undefined!!");
			else return new BigFraction(BigInteger.ONE, BigInteger.ONE);
		}
		else if(n == 1) return this;
		else {
			BigFraction exp = new BigFraction(BigInteger.ONE, BigInteger.ONE);
			if(n > 0) {
				for(int i = 0; i < n; ++i) {
					exp = exp.multiply(this);
				}
			}
			else {
				for(int i = 0; i < Math.abs(n); ++i) {
					exp = exp.divide(this);
				}
			}
			
			return exp;
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		BigInteger wholeNumber = numerator.divide(denominator);
		if(numerator.mod(denominator).compareTo(BigInteger.ZERO) == 0) {
			s += wholeNumber ;
		}
		else if(wholeNumber.compareTo(BigInteger.ZERO) == 0) {
			s += numerator + "/" + denominator;
		}
		else s += wholeNumber + "&" + numerator.abs().mod(denominator) + "/" + denominator;
		return s;
	}

	@Override
	public int compareTo(BigFraction o) {
		return asBigDecimal().compareTo(o.asBigDecimal());
	}

	@Override
	public double doubleValue() {
		return asBigDecimal().doubleValue();
	}

	@Override
	public float floatValue() {
		return asBigDecimal().floatValue();
	}

	@Override
	public int intValue() {
		return numerator.divide(denominator).intValue();
	}

	@Override
	public long longValue() {
		return numerator.divide(denominator).longValue();
	}
}
