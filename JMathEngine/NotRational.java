package JMathEngine;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NotRational implements Number {
    private final BigDecimal number;
    @Override
    public boolean equals(Object obj) {
        if (obj==null){
            return false;
        }
        if (obj.getClass()!=this.getClass()){
            return false;
        }
        NotRational other = (NotRational) obj;
        if (other.number.compareTo(this.number)!=0){
            return false;
        }
        return true;
    }
    public NotRational(String number){
        this.number=new BigDecimal(number);
    }

    public NotRational(BigDecimal nuber){
        this.number=nuber;
    }

    public NotRational(BigInteger bigInteger){
        this.number=new BigDecimal(bigInteger);
    }

    public NotRational(Integer value){
        this.number=new BigDecimal(value);
    }
    public NotRational(Double value){
        this.number=new BigDecimal(value);
    }

    @Override
    public Number subtract(Number other) {
        if (other.getClass()==Rational.class){
            Rational rational = (Rational) other;
            BigDecimal new_numerator = this.number.multiply(rational.denominator);
            new_numerator=new_numerator.subtract(rational.numerator);
            return new Rational(new_numerator,rational.denominator);
        }
        else if (other.getClass()==NotRational.class){
            return new NotRational(this.number.subtract(other.toBigDecimal()));
        }
        return other.subtract_reverse(this);
    }
    private static boolean isTerminatingDecimal(BigInteger numerator, BigInteger denominator) {
    
    // Копируем знаменатель
    BigInteger temp = denominator;
    
    // Делим на 2 пока делится
    while (temp.remainder(BigInteger.TWO).equals(BigInteger.ZERO)) {
        temp = temp.divide(BigInteger.TWO);
    }
    
    // Делим на 5 пока делится
    while (temp.remainder(BigInteger.valueOf(5)).equals(BigInteger.ZERO)) {
        temp = temp.divide(BigInteger.valueOf(5));
    }
    
    return temp.equals(BigInteger.ONE);
    }
    @Override
    public Number divide(Number other) {
        
       if (other.getClass()==Rational.class){
            Rational rational = (Rational) other;
            BigDecimal new_BigDecimal=this.number.multiply(rational.denominator).divide(rational.numerator);
            return new NotRational(new_BigDecimal);
       }
       else if (other.getClass()==NotRational.class){
        
        NotRational notRational = (NotRational) other;
        if (other.toBigDecimal().compareTo(BigDecimal.ZERO)==0){
            Number num = MathEngine.on_divide_asset(this, notRational);
            if (num!=null){
                return num;
            }
            throw new RuntimeException("Деление на ноль не поддерживается");
        }
        try {
            return new NotRational(this.number.divide(notRational.number));
        }
        catch (ArithmeticException exc){
            return new Rational(this.number,notRational.number);
        }
       }
       return other.divide_reverse(this);
    }

    @Override
    public Number add(Number other) {
        if (other.getClass()==Rational.class){
            Rational rational = (Rational) other;
            return rational.add(this);
        }
        else if (other.getClass()==NotRational.class){
            return new NotRational(this.number.add(other.toBigDecimal()));
        }
        return other.add(this);
    }

    @Override
    public BigDecimal toBigDecimal() {
        return this.number;
    }

    @Override
    public Boolean toBoolean() {
        return this.number.compareTo(BigDecimal.ZERO)==0;
    }

    @Override
    public Integer toInteger() {
       return Integer.valueOf(this.number.intValue());
    }

    @Override
    public Double toDouble() {
        return Double.valueOf(this.number.doubleValue());
    }

    @Override
    public Long toLong() {
       return Long.valueOf(this.number.longValue());
    }

    public NotRational(Long long_value){
        this.number=new BigDecimal(long_value);
    }
    

    @Override
    public String toString() {
        BigDecimal resp = this.number.stripTrailingZeros();
        return resp.toEngineeringString();
    }
    @Override
    public Number multiply(Number other) {
       if (other.getClass()==Rational.class){
            Rational rational = (Rational) other;
            return rational.multiply(this);
       }
       else if (other.getClass()==NotRational.class){
            return new NotRational(this.number.multiply(other.toBigDecimal()));
       }
       return other.multiply(this);
    }

    @Override
    public Number percent(Number other) {
        if (other.getClass()==NotRational.class||other.getClass()==Rational.class){
            if (other.toBigDecimal().compareTo(BigDecimal.ZERO)<0){
                throw new RuntimeException("% от отрицательного числа не поддерживается.");
            }
            if (other.toBigDecimal().compareTo(BigDecimal.ZERO)<0){
                throw new RuntimeException("% отрицательного числа не поддерживается");
            }
            if (other.toBigDecimal().compareTo(this.toBigDecimal())>0){
                return other;
            }
            Integer divided = this.divide(other).toInteger();
            Number c = other.multiply(Number.valueOf(divided));
            return this.subtract(c);
        }
        return other.percent_reverse(this);
    }

    @Override
    public int compareTo(Number number) {
        if (number instanceof Rational rt){
            BigDecimal rhs = this.toBigDecimal().multiply(rt.denominator);
            return rt.numerator.compareTo(rhs)*-1;
        }
        if (number instanceof NotRational ntr){
            return this.number.compareTo(ntr.toBigDecimal());
        }
        return number.compareTo(this)*-1;
    }

    @Override
    public Number pow(Number other) {
        if (other.getClass()==NotRational.class){
            if (other.toBigDecimal().stripTrailingZeros().scale()<=0){
                boolean is_reversed=false;
                BigDecimal bigInteger = other.toBigDecimal();
                if (other.toBigDecimal().compareTo(BigDecimal.ZERO)<0){
                    is_reversed=true;
                    bigInteger=bigInteger.multiply(new BigDecimal(-1));
                }
                
                BigDecimal new_number = this.number;
                while (bigInteger.compareTo(BigDecimal.ONE)>0){
                    
                    new_number=new_number.multiply(this.number);
                    bigInteger=bigInteger.subtract(BigDecimal.ONE);
                }
                if (is_reversed){
                    return new Rational(BigDecimal.ONE,new_number);
                }
                return new NotRational(new_number);
            }
        }
        return other.pow_reverse(this);
    }
}