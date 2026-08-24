package JMathEngine;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
class Group2 {
    public static void calculate(String[] operators,Number[] num_operands,MathEngine calc){
        
        Number ret=num_operands[0];
        
        for (int i = 0; i<num_operands.length-1;i++){

            Number cur=num_operands[i+1];


            if (operators[i].equals("*")){
                
                ret=ret.multiply(cur);
            } else if (operators[i].equals("/")){
                try{
                    ret=calc.parameters.on_divide(ret, cur);
                }
                catch (ArithmeticException exc){
                    ret=new Rational(ret.toBigDecimal(),cur.toBigDecimal());
                }
            }
            else if (operators[i].equals("//")){
                ret=new NotRational(ret.toBigDecimal().divideToIntegralValue(cur.toBigDecimal()));
            }
        }
        num_operands[num_operands.length-1]=ret;
    }
}
