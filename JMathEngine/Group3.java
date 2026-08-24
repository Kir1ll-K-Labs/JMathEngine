package JMathEngine;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
class Group3 {
    private static Number percent_inner(Number a,Number b){
        if (b.toBigDecimal().compareTo(BigDecimal.ZERO)<0){
            throw new RuntimeException("% от отрицательного числа не поддерживается.");
        }
        if (a.toBigDecimal().compareTo(BigDecimal.ZERO)<0){
            throw new RuntimeException("% отрицательного числа не поддерживается");
        }
        if (b.toBigDecimal().compareTo(a.toBigDecimal())>0){
            return b;
        }
        Number compared = new NotRational(a.toBigDecimal().divide(b.toBigDecimal(),RoundingMode.FLOOR).setScale(0,RoundingMode.FLOOR));
        return a.subtract(b.multiply(compared));
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
