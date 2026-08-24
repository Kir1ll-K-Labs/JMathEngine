package JMathEngine;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Rational implements Number {
    protected final BigDecimal numerator;
    protected final BigDecimal denominator;

    @Override
    public BigDecimal toBigDecimal() {
        return this.numerator.divide(this.denominator,new MathContext(50));
    }

    @Override
    public Boolean toBoolean() {
        return this.numerator.compareTo(BigDecimal.ZERO)==0;
    }

    @Override
    public Long toLong() {
       return Long.valueOf(this.toBigDecimal().longValue());
    }

    @Override
    public Double toDouble() {
        return Double.valueOf(this.toBigDecimal().doubleValue());
    }

    @Override
    public Integer toInteger() {
        return Integer.valueOf(this.toBigDecimal().intValue());
    }
    private BigDecimal[] normalize(){
        int a = this.numerator.scale();
        int b = this.numerator.scale();
        a = Math.max(a, b);
        BigDecimal c = new BigDecimal(1);
        for (int i = 0;i<a;i++){
            c=c.multiply(new BigDecimal(10));
        }
        
        BigDecimal ab=this.numerator.multiply(c);
        BigDecimal bb;
        if (this.denominator.compareTo(new BigDecimal(this.denominator.toBigInteger()))==0){
            bb=c;
        }
        else {
            bb=this.denominator.multiply(c);
        }
        BigInteger lcm_result = lcm(ab.toBigInteger(),bb.toBigInteger());
        BigInteger max_big_a=lcm_result.divide(ab.toBigInteger());
        BigInteger max_big_b=lcm_result.divide(bb.toBigInteger());
        if (max_big_a.compareTo(max_big_b) > 0) {
            max_big_b=max_big_a;
        }
        BigDecimal new_numerator;
        BigDecimal new_denominator;
        new_numerator=this.numerator.multiply(new BigDecimal(max_big_b).multiply(c));
        new_denominator=this.denominator.multiply(new BigDecimal(max_big_b).multiply(c));
        BigInteger gcd = new_numerator.toBigInteger().gcd(new_denominator.toBigInteger());
        new_numerator=new_numerator.divide(new BigDecimal(gcd)).stripTrailingZeros();
        new_denominator=new_denominator.divide(new BigDecimal(gcd)).stripTrailingZeros();
        return new BigDecimal[]{new_numerator,new_denominator};
    }
    @Override
    public Number divide(Number other) {
        if (other.getClass()==Rational.class){
            Rational r = (Rational) other;
            BigDecimal new_numerator = this.numerator.multiply(r.denominator);
            BigDecimal new_denominator = this.denominator.multiply(r.numerator);
            return new Rational(new_numerator,new_denominator);
        }
        else if (other.getClass()==NotRational.class){
            NotRational notRational = (NotRational) other;
            return new Rational(this.numerator,this.denominator.multiply(notRational.toBigDecimal()));
        }
        throw new RuntimeException();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null){
            return false;
        }
        if (obj.getClass()!=this.getClass()){
            return false;
        }
        Rational rational = (Rational) obj;
        BigDecimal[] rat_spi = rational.normalize();
        BigDecimal[] this_spi = this.normalize();
        if (rat_spi[0].compareTo(this_spi[0])!=0){
            return false;
        }
        if (rat_spi[1].compareTo(this_spi[1])!=0){
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        BigDecimal[]resp=this.normalize();
        return ""+resp[0].toPlainString()+"/"+resp[1].toPlainString();
    }

    @Override
    public Number multiply(Number other) {
        if (other.getClass()==Rational.class){
            Rational rational = (Rational) other;
            BigDecimal new_numerator = this.numerator.multiply(rational.numerator);
            BigDecimal new_denominator = this.denominator.multiply(rational.denominator);
            try {
                BigDecimal c = new_numerator.divide(new_denominator);
                return new NotRational(c);
            }
            catch (ArithmeticException exc){
                return new Rational(new_numerator,new_denominator);
            }
        }
        else if (other.getClass()==NotRational.class){
            NotRational notRational = (NotRational) other;
            BigDecimal new_numerator = this.numerator.multiply(notRational.toBigDecimal());
            try {
                BigDecimal nBigDecimal = new_numerator.divide(this.denominator);
                return new NotRational(nBigDecimal);
            }
            catch (ArithmeticException exc){
                return new Rational(new_numerator,this.denominator);
            }
        }
        return other.multiply(this);
    }
    @Override
    public Number pow(Number other) {
       if (other.getClass()==NotRational.class){
            if (other.toBigDecimal().stripTrailingZeros().scale()<=0){
                
                BigDecimal bigInteger = other.toBigDecimal();
                boolean is_negative = false;
                 if (bigInteger.compareTo(BigDecimal.ZERO)<0){
                    is_negative=true;
                    bigInteger=bigInteger.multiply(new BigDecimal(-1));
                 }
                BigDecimal new_numerator = this.numerator;
                BigDecimal new_denominator=this.denominator;
                while (bigInteger.compareTo(BigDecimal.ONE)>0){
                    new_numerator=new_numerator.multiply(this.numerator);
                    new_denominator=new_denominator.multiply(this.denominator);
                    bigInteger=bigInteger.subtract(BigDecimal.ONE);
                }
                if (is_negative){
                    return new NotRational(new_denominator).divide(new NotRational(new_numerator));
                }
                return new Rational(new_numerator, new_denominator);
            }
        }
        throw new RuntimeException("Возведение в степень "+other.toString()+" невозможно");
    }

    public Rational(BigInteger val) {
        this.numerator=new BigDecimal("1");
        this.denominator=new BigDecimal("1");
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
    return a / gcd(a, b) * b; 
    }
    private static BigInteger lcm(BigInteger a,BigInteger b){
        return a.divide(a.gcd(b)).multiply(b);
    }

    public Rational(String numerator,String denominator){
        this.numerator=new BigDecimal(numerator);
        this.denominator=new BigDecimal(denominator);

    }

    public Rational(BigDecimal numerator, BigDecimal denominator){
        this.numerator = numerator;
        this.denominator=denominator;
    }

    public static Rational create(String numerator,String denominator){
        return new Rational(numerator,denominator);
    }

    @Override
    public Number subtract(Number other) {
       if (other.getClass()==Rational.class){
            Rational rational = (Rational) other;
            BigDecimal new_denominator_int = new BigDecimal(""+lcm(this.denominator.intValue(),rational.denominator.intValue()));
            BigDecimal numerator1 = this.numerator.multiply(new_denominator_int.divide(this.denominator));
            BigDecimal numerator2 = rational.numerator.multiply(new_denominator_int.divide(rational.denominator));
            BigDecimal new_numerator = numerator1.subtract(numerator2);
            
            BigDecimal c = new BigDecimal(""+gcd(new_numerator.intValue(), new_denominator_int.intValue()));
            new_numerator=new_numerator.divide(c);
            new_denominator_int=new_denominator_int.divide(c);
            try {
                BigDecimal new_big_decimal = new_numerator.divide(new_denominator_int);
                return new NotRational(new_big_decimal);
            }catch (ArithmeticException exc){
                return Rational.create(""+new_numerator, ""+new_denominator_int);
            }
        }
        else if (other.getClass()==NotRational.class){
            NotRational notRational=(NotRational) other;
            BigDecimal new_numerator = this.numerator.subtract(this.denominator.multiply(notRational.toBigDecimal()));
            return new Rational(new_numerator,this.denominator);
        }
        throw new RuntimeException();
    }


    @Override
    public Number add(Number other) {
        if (other.getClass()==Rational.class){
            Rational rational = (Rational) other;
            BigDecimal new_denominator_int = new BigDecimal(""+lcm(this.denominator.intValue(),rational.denominator.intValue()));
            BigDecimal numerator1 = this.numerator.multiply(new_denominator_int.divide(this.denominator));
            BigDecimal numerator2 = rational.numerator.multiply(new_denominator_int.divide(rational.denominator));
            BigDecimal new_numerator = numerator1.add(numerator2);
            
            BigDecimal c = new BigDecimal(""+gcd(new_numerator.intValue(), new_denominator_int.intValue()));
            new_numerator=new_numerator.divide(c);
            new_denominator_int=new_denominator_int.divide(c);
            try {
                BigDecimal new_big_decimal = new_numerator.divide(new_denominator_int);
                return new NotRational(new_big_decimal);
            }catch (ArithmeticException exc){
                return Rational.create(""+new_numerator, ""+new_denominator_int);
            }
        }
        else if (other.getClass()==NotRational.class){
            NotRational notRational=(NotRational) other;
            BigDecimal new_numerator = this.numerator.add(this.denominator.multiply(notRational.toBigDecimal()));
            return new Rational(new_numerator,this.denominator);
        }
        return other.add(this);
    }
}