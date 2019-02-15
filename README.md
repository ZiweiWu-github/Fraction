# The Purpose of this Project
In Java, there are no implementations to store numbers as fractions. We can only have them as floating points. That may be good enough for some applications. But eventually, floating point errors will happen because of the nature of storing decimal numbers into binary (ex: 0.1 + 0.2).

This project is to store numbers as fractions to eliminate floating point errors and allow for more precise calculations.

# Fraction.java
An implementation for fractions that uses the long data type to store the numerator and denominator

# BigFraction.java
An implementation of fractions that uses BigInteger to store the numerator and denominator.

Used when long isn't big enough to store the numerator or denominator.
