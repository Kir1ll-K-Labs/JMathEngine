package JMathEngine;
import java.math.BigDecimal;
import java.util.Arrays;
class Group3 {
    private static Number percent_inner(Number a,Number b){
        if (b.getClass()==Rational.class){
            Rational other = (Rational) b;
            
        }
        else if (b.getClass()==NotRational.class){
            NotRational other = (NotRational) b;
            if (other.toBigDecimal().compareTo(a.toBigDecimal())>0){
                return other;
            }
            
        }
        throw new RuntimeException("Остаток от деления "+a.toString()+" на "+b.toString()+" не получается найти.");
    }
    public static void calculate(String[] operators,Number[] num_operands,MathEngine calc){
        
        Number ret=num_operands[0];
        for (int i = 0; i<operators.length;i++){
            Number cur=num_operands[i+1];
            ret=percent_inner(ret, cur);
            
        }
        num_operands[num_operands.length-1]=ret;
    }
}
