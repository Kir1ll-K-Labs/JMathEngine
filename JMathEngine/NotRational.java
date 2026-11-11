package JMathEngine;

import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes5.dex */
public class NotRational implements Number {
    private final BigDecimal number;

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        NotRational other = (NotRational) obj;
        if (other.number.compareTo(this.number) != 0) {
            return false;
        }
        return true;
    }

    public NotRational(String number) {
        this.number = new BigDecimal(number);
    }

    public NotRational(BigDecimal nuber) {
        this.number = nuber;
    }

    public NotRational(BigInteger bigInteger) {
        this.number = new BigDecimal(bigInteger);
    }

    public NotRational(Integer value) {
        this.number = new BigDecimal(value.intValue());
    }

    public NotRational(Double value) {
        this.number = new BigDecimal(value.doubleValue());
    }

    @Override // JMathEngine.Number
    public Number subtract(Number other) {
        if (other.getClass() == Rational.class) {
            Rational rational = (Rational) other;
            BigDecimal new_numerator = this.number.multiply(rational.denominator);
            return new Rational(new_numerator.subtract(rational.numerator), rational.denominator);
        }
        if (other.getClass() == NotRational.class) {
            return new NotRational(this.number.subtract(other.toBigDecimal()));
        }
        return other.subtract_reverse(this);
    }

    private static boolean isTerminatingDecimal(BigInteger numerator, BigInteger denominator) {
        BigInteger temp = denominator;
        while (temp.remainder(BigInteger.TWO).equals(BigInteger.ZERO)) {
            temp = temp.divide(BigInteger.TWO);
        }
        while (temp.remainder(BigInteger.valueOf(5L)).equals(BigInteger.ZERO)) {
            temp = temp.divide(BigInteger.valueOf(5L));
        }
        return temp.equals(BigInteger.ONE);
    }

    @Override // JMathEngine.Number
    public Number divide(Number other) {
        if (other.getClass() == Rational.class) {
            Rational rational = (Rational) other;
            BigDecimal new_BigDecimal = this.number.multiply(rational.denominator).divide(rational.numerator);
            return new NotRational(new_BigDecimal);
        }
        if (other.getClass() == NotRational.class) {
            NotRational notRational = (NotRational) other;
            if (other.toBigDecimal().compareTo(BigDecimal.ZERO) == 0) {
                Number num = MathEngine.on_divide_asset(this, notRational);
                if (num != null) {
                    return num;
                }
                throw new RuntimeException("Деление на ноль не поддерживается");
            }
            try {
                return new NotRational(this.number.divide(notRational.number));
            } catch (ArithmeticException e) {
                return new Rational(this.number, notRational.number);
            }
        }
        return other.divide_reverse(this);
    }

    @Override // JMathEngine.Number
    public Number add(Number other) {
        if (other.getClass() == Rational.class) {
            Rational rational = (Rational) other;
            return rational.add(this);
        }
        if (other.getClass() == NotRational.class) {
            return new NotRational(this.number.add(other.toBigDecimal()));
        }
        return other.add(this);
    }

    @Override // JMathEngine.Number
    public BigDecimal toBigDecimal() {
        return this.number;
    }

    @Override // JMathEngine.Number
    public Boolean toBoolean() {
        return Boolean.valueOf(this.number.compareTo(BigDecimal.ZERO) == 0);
    }

    @Override // JMathEngine.Number
    public Integer toInteger() {
        return Integer.valueOf(this.number.intValue());
    }

    @Override // JMathEngine.Number
    public Double toDouble() {
        return this.number.doubleValue();
    }

    @Override // JMathEngine.Number
    public Long toLong() {
        return Long.valueOf(this.number.longValue());
    }

    public NotRational(Long long_value) {
        this.number = new BigDecimal(long_value.longValue());
    }

    @Override // JMathEngine.Number
    public String toString() {
        BigDecimal resp = this.number.stripTrailingZeros();
        return resp.toString();
    }

    @Override // JMathEngine.Number
    public String toPlainString() {
        BigDecimal resp = this.number.stripTrailingZeros();
        return resp.toPlainString();
    }

    @Override // JMathEngine.Number
    public String toEngineeringString() {
        BigDecimal resp = this.number.stripTrailingZeros();
        return resp.toEngineeringString();
    }

    @Override // JMathEngine.Number
    public Number multiply(Number other) {
        if (other.getClass() == Rational.class) {
            Rational rational = (Rational) other;
            return rational.multiply(this);
        }
        if (other.getClass() == NotRational.class) {
            return new NotRational(this.number.multiply(other.toBigDecimal()));
        }
        return other.multiply(this);
    }

    @Override // JMathEngine.Number
    public Number percent(Number other) {
        if (other.getClass() == NotRational.class || other.getClass() == Rational.class) {
            if (other.toBigDecimal().compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("% от отрицательного числа не поддерживается.");
            }
            if (other.toBigDecimal().compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("% отрицательного числа не поддерживается");
            }
            if (other.toBigDecimal().compareTo(toBigDecimal()) > 0) {
                return other;
            }
            Integer divided = divide(other).toInteger();
            Number c = other.multiply(Number.valueOf(divided.intValue()));
            return subtract(c);
        }
        return other.percent_reverse(this);
    }

    @Override // JMathEngine.Number
    public int compareTo(Number number) {
        if (number instanceof Rational) {
            Rational rt = (Rational) number;
            BigDecimal rhs = toBigDecimal().multiply(rt.denominator);
            return rt.numerator.compareTo(rhs) * (-1);
        }
        if (number instanceof NotRational) {
            NotRational ntr = (NotRational) number;
            return this.number.compareTo(ntr.toBigDecimal());
        }
        return number.compareTo(this) * (-1);
    }

    @Override // JMathEngine.Number
    public Number pow(Number other) {
        if (other.getClass() == NotRational.class && other.toBigDecimal().stripTrailingZeros().scale() <= 0) {
            boolean is_reversed = false;
            BigDecimal bigInteger = other.toBigDecimal();
            if (other.toBigDecimal().compareTo(BigDecimal.ZERO) < 0) {
                is_reversed = true;
                bigInteger = bigInteger.multiply(new BigDecimal(-1));
            }
            BigDecimal new_number = this.number;
            while (bigInteger.compareTo(BigDecimal.ONE) != 0) {
                new_number = new_number.multiply(this.number);
                bigInteger = bigInteger.subtract(BigDecimal.ONE);
            }
            if (is_reversed) {
                return new Rational(BigDecimal.ONE, new_number);
            }
            return new NotRational(new_number);
        }
        return other.pow_reverse(this);
    }
}
