package JMathEngine;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Utils {
    private static BigInteger lcm(BigInteger a,BigInteger b){
        return a.divide(a.gcd(b)).multiply(b);
    }
    public static BigDecimal[] normalize_rational(BigDecimal numerator,BigDecimal denominator){
         int a = numerator.scale();
        int b = denominator.scale();
        a = Math.max(a, b);
        BigDecimal c = new BigDecimal(1);
        for (int i = 0;i<a;i++){
            c=c.multiply(new BigDecimal(10));
        }
        
        BigDecimal ab=numerator.multiply(c);
        BigDecimal bb;
        if (denominator.compareTo(new BigDecimal(denominator.toBigInteger()))==0){
            bb=c;
        }
        else {
            bb=denominator.multiply(c);
        }
        BigInteger lcm_result = lcm(ab.toBigInteger(),bb.toBigInteger());
        BigInteger max_big_a=lcm_result.divide(ab.toBigInteger());
        BigInteger max_big_b=lcm_result.divide(bb.toBigInteger());
        if (max_big_a.compareTo(max_big_b) > 0) {
            max_big_b=max_big_a;
        }
        BigDecimal new_numerator;
        BigDecimal new_denominator;
        new_numerator=numerator.multiply(new BigDecimal(max_big_b).multiply(c));
        new_denominator=denominator.multiply(new BigDecimal(max_big_b).multiply(c));
        BigInteger gcd = new_numerator.toBigInteger().gcd(new_denominator.toBigInteger());
        new_numerator=new_numerator.divide(new BigDecimal(gcd)).stripTrailingZeros();
        new_denominator=new_denominator.divide(new BigDecimal(gcd)).stripTrailingZeros();
        return new BigDecimal[]{new_numerator,new_denominator};
    }
}
