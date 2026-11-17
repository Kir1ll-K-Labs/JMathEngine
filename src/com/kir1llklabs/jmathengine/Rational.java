package com.kir1llklabs.jmathengine;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/* JADX INFO: loaded from: classes5.dex */
public class Rational implements Number {
    protected final BigDecimal denominator;
    protected final BigDecimal numerator;

    @Override // JMathEngine.Number
    public Boolean toBoolean() {
        return Boolean.valueOf(this.numerator.compareTo(BigDecimal.ZERO) == 0);
    }

    public BigDecimal getNumerator() {
        return this.numerator;
    }

    public BigDecimal getDenominator() {
        return this.denominator;
    }

    @Override // JMathEngine.Number
    public Long toLong() {
        return Long.valueOf(toBigDecimal().longValue());
    }

    @Override // JMathEngine.Number
    public Integer toInteger() {
        return Integer.valueOf(toBigDecimal().intValue());
    }

    @Override // JMathEngine.Number
    public int compareTo(Number number) {
        if (number instanceof Rational) {
            Rational rt = (Rational) number;
            BigDecimal one = this.numerator.multiply(rt.denominator);
            BigDecimal two = this.denominator.multiply(rt.numerator);
            return one.compareTo(two);
        }
        if (number instanceof NotRational) {
            NotRational ntr = (NotRational) number;
            BigDecimal rhs = ntr.toBigDecimal().multiply(this.denominator);
            return this.numerator.compareTo(rhs);
        }
        return number.compareTo(this) * (-1);
    }

    private BigDecimal[] normalize() {
        BigDecimal bb;
        try {
            int a = this.numerator.scale();
            int b = this.numerator.scale();
            int a2 = Math.max(a, b);
            BigDecimal c = new BigDecimal(1);
            for (int i = 0; i < a2; i++) {
                c = c.multiply(new BigDecimal(10));
            }
            BigDecimal ab = this.numerator.multiply(c);
            if (this.denominator.compareTo(new BigDecimal(this.denominator.toBigInteger())) == 0) {
                bb = c;
            } else {
                BigDecimal bb2 = this.denominator;
                bb = bb2.multiply(c);
            }
            BigInteger lcm_result = lcm(ab.toBigInteger(), bb.toBigInteger());
            BigInteger max_big_a = lcm_result.divide(ab.toBigInteger());
            BigInteger max_big_b = lcm_result.divide(bb.toBigInteger());
            if (max_big_a.compareTo(max_big_b) > 0) {
                max_big_b = max_big_a;
            }
            BigDecimal new_numerator = this.numerator.multiply(new BigDecimal(max_big_b).multiply(c));
            BigDecimal new_denominator = this.denominator.multiply(new BigDecimal(max_big_b).multiply(c));
            BigInteger gcd = new_numerator.toBigInteger().gcd(new_denominator.toBigInteger());
            return new BigDecimal[]{new_numerator.divide(new BigDecimal(gcd)).stripTrailingZeros(), new_denominator.divide(new BigDecimal(gcd)).stripTrailingZeros()};
        } catch (ArithmeticException e) {
            return new BigDecimal[]{this.numerator, this.denominator};
        }
    }

    @Override // JMathEngine.Number
    public Number divide(Number other) {
        if (other.getClass() == Rational.class) {
            Rational r = (Rational) other;
            BigDecimal new_numerator = this.numerator.multiply(r.denominator);
            BigDecimal new_denominator = this.denominator.multiply(r.numerator);
            try {
                return new NotRational(new_numerator.divide(new_denominator));
            } catch (ArithmeticException e) {
                return new Rational(new_numerator, new_denominator);
            }
        }
        if (other.getClass() == NotRational.class) {
            if (other.toBigDecimal().compareTo(BigDecimal.ZERO) == 0) {
                Number returning = MathEngine.on_divide_asset(this, other);
                if (returning != null) {
                    return returning;
                }
                throw new RuntimeException("Деление на ноль не поддерживается");
            }
            NotRational notRational = (NotRational) other;
            return new Rational(this.numerator, this.denominator.multiply(notRational.toBigDecimal()));
        }
        throw new RuntimeException();
    }

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Rational rational = (Rational) obj;
        BigDecimal[] rat_spi = rational.normalize();
        BigDecimal[] this_spi = normalize();
        if (rat_spi[0].compareTo(this_spi[0]) != 0 || rat_spi[1].compareTo(this_spi[1]) != 0) {
            return false;
        }
        return true;
    }

    @Override // JMathEngine.Number
    public String toString() {
        BigDecimal[] resp = normalize();
        return "" + resp[0].toString() + "/" + resp[1].toString();
    }

    @Override // JMathEngine.Number
    public String toPlainString() {
        BigDecimal[] resp = normalize();
        return "" + resp[0].toPlainString() + "/" + resp[1].toPlainString();
    }

    @Override // JMathEngine.Number
    public String toEngineeringString() {
        BigDecimal[] resp = normalize();
        return "" + resp[0].toEngineeringString() + "/" + resp[1].toEngineeringString();
    }

    @Override // JMathEngine.Number
    public Number multiply(Number other) {
        if (other.getClass() == Rational.class) {
            Rational rational = (Rational) other;
            BigDecimal new_numerator = this.numerator.multiply(rational.numerator);
            BigDecimal new_denominator = this.denominator.multiply(rational.denominator);
            try {
                BigDecimal c = new_numerator.divide(new_denominator);
                return new NotRational(c);
            } catch (ArithmeticException e) {
                return new Rational(new_numerator, new_denominator);
            }
        }
        if (other.getClass() == NotRational.class) {
            NotRational notRational = (NotRational) other;
            BigDecimal new_numerator2 = this.numerator.multiply(notRational.toBigDecimal());
            try {
                BigDecimal nBigDecimal = new_numerator2.divide(this.denominator);
                return new NotRational(nBigDecimal);
            } catch (ArithmeticException e2) {
                return new Rational(new_numerator2, this.denominator);
            }
        }
        return other.multiply(this);
    }

    @Override // JMathEngine.Number
    public Number pow(Number other) {
        if (other.getClass() == NotRational.class && other.toBigDecimal().stripTrailingZeros().scale() <= 0) {
            BigDecimal bigInteger = other.toBigDecimal();
            boolean is_negative = false;
            if (bigInteger.compareTo(BigDecimal.ZERO) < 0) {
                is_negative = true;
                bigInteger = bigInteger.multiply(new BigDecimal(-1));
            }
            BigDecimal new_numerator = this.numerator;
            BigDecimal new_denominator = this.denominator;
            while (bigInteger.compareTo(BigDecimal.ONE) > 0) {
                new_numerator = new_numerator.multiply(this.numerator);
                new_denominator = new_denominator.multiply(this.denominator);
                bigInteger = bigInteger.subtract(BigDecimal.ONE);
            }
            if (is_negative) {
                return new NotRational(new_denominator).divide(new NotRational(new_numerator));
            }
            return new Rational(new_numerator, new_denominator);
        }
        throw new RuntimeException("Возведение в степень " + other.toString() + " невозможно");
    }

    public Rational(BigInteger val) {
        this.numerator = new BigDecimal("1");
        this.denominator = new BigDecimal("1");
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static int lcm(int a, int b) {
        return (a / gcd(a, b)) * b;
    }

    private static BigInteger lcm(BigInteger a, BigInteger b) {
        return a.divide(a.gcd(b)).multiply(b);
    }

    public Rational(String numerator, String denominator) {
        this.numerator = new BigDecimal(numerator);
        this.denominator = new BigDecimal(denominator);
    }

    public Rational(BigDecimal numerator, BigDecimal denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Rational create(String numerator, String denominator) {
        return new Rational(numerator, denominator);
    }

    @Override // JMathEngine.Number
    public Number percent(Number other) {
        if (other.toBigDecimal().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("% от отрицательного числа не поддерживается.");
        }
        if (other.toBigDecimal().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("% отрицательного числа не поддерживается");
        }
        if (other.toBigDecimal().compareTo(toBigDecimal()) > 0) {
            return other;
        }
        Number compared = new NotRational(toBigDecimal().divide(other.toBigDecimal(), RoundingMode.FLOOR).setScale(0, RoundingMode.FLOOR));
        return subtract(other.multiply(compared));
    }

    @Override // JMathEngine.Number
    public Number subtract(Number other) {
        if (other.getClass() == Rational.class) {
            Rational rational = (Rational) other;
            BigDecimal new_denominator_int = new BigDecimal("" + lcm(this.denominator.intValue(), rational.denominator.intValue()));
            BigDecimal numerator1 = this.numerator.multiply(new_denominator_int.divide(this.denominator));
            BigDecimal numerator2 = rational.numerator.multiply(new_denominator_int.divide(rational.denominator));
            BigDecimal new_numerator = numerator1.subtract(numerator2);
            BigDecimal c = new BigDecimal("" + gcd(new_numerator.intValue(), new_denominator_int.intValue()));
            BigDecimal new_numerator2 = new_numerator.divide(c);
            BigDecimal new_denominator_int2 = new_denominator_int.divide(c);
            try {
                BigDecimal new_big_decimal = new_numerator2.divide(new_denominator_int2);
                return new NotRational(new_big_decimal);
            } catch (ArithmeticException e) {
                return create("" + new_numerator2, "" + new_denominator_int2);
            }
        }
        if (other.getClass() == NotRational.class) {
            NotRational notRational = (NotRational) other;
            return new Rational(this.numerator.subtract(this.denominator.multiply(notRational.toBigDecimal())), this.denominator);
        }
        throw new RuntimeException();
    }

    @Override // JMathEngine.Number
    public Number add(Number other) {
        if (other.getClass() == Rational.class) {
            Rational rational = (Rational) other;
            BigDecimal new_denominator_int = new BigDecimal("" + lcm(this.denominator.intValue(), rational.denominator.intValue()));
            BigDecimal numerator1 = this.numerator.multiply(new_denominator_int.divide(this.denominator));
            BigDecimal numerator2 = rational.numerator.multiply(new_denominator_int.divide(rational.denominator));
            BigDecimal new_numerator = numerator1.add(numerator2);
            BigDecimal c = new BigDecimal("" + gcd(new_numerator.intValue(), new_denominator_int.intValue()));
            BigDecimal new_numerator2 = new_numerator.divide(c);
            BigDecimal new_denominator_int2 = new_denominator_int.divide(c);
            try {
                BigDecimal new_big_decimal = new_numerator2.divide(new_denominator_int2);
                return new NotRational(new_big_decimal);
            } catch (ArithmeticException e) {
                return create("" + new_numerator2, "" + new_denominator_int2);
            }
        }
        if (other.getClass() == NotRational.class) {
            NotRational notRational = (NotRational) other;
            return new Rational(this.numerator.add(this.denominator.multiply(notRational.toBigDecimal())), this.denominator);
        }
        return other.add(this);
    }
}
