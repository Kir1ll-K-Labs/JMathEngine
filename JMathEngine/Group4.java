package JMathEngine;
import java.math.BigDecimal;
import java.util.Arrays;
class Group4 {
    public static Number calculate(Number[] numbers_list,MathEngine calc){
       
        
        for (int i = numbers_list.length-2;i>=0;i--){
            Number cur = numbers_list[i];
            Number next = numbers_list[i+1];
            numbers_list[i]=calc.parameters.on_pow(cur, next);
        }
        return numbers_list[0];
    }
}
