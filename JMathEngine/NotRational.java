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
            return rational.subtract(this);
        }
        else if (other.getClass()==NotRational.class){
            return new NotRational(this.number.subtract(other.toBigDecimal()));
        }
        throw new RuntimeException();
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
        return new NotRational(this.number.divide(notRational.toBigDecimal()));
       }
       throw new RuntimeException();
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
        return this.number.toEngineeringString();
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
    public Number pow(Number other) {
        if (other.getClass()==NotRational.class){
            if (other.toBigDecimal().stripTrailingZeros().scale()<=0){
                boolean is_reversed=false;
                BigDecimal bigInteger = other.toBigDecimal();
                if (other.toBigDecimal().compareTo(BigDecimal.ZERO)<0){
                    is_reversed=true;
                    bigInteger=bigInteger.multiply(new BigDecimal(-1));
                }
                System.out.println(other.toString());
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
        throw new RuntimeException("Возведение в степень "+other.toString()+" невозможно");
    }
}
//0.99900000000000000000000000000000000001
//0.99900000000000000000000000000000000001