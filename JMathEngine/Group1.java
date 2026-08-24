package JMathEngine;
import java.util.Arrays;
import java.util.HashMap;
class Group1 {
    public static void calculate(Character[] operators,Number[] num_operands,MathEngine calc){
       
        Number ret=num_operands[0];
        Number cur;
        for (int i = 0; i<operators.length;i++){
            cur=num_operands[i+1];
            if (operators[i]=='+'){
                ret=calc.parameters.on_add(ret, cur);
                
            } else if (operators[i]=='-'){
                ret=ret.subtract(cur);
            }
        }
        num_operands[num_operands.length-1]=ret;
    }
}
