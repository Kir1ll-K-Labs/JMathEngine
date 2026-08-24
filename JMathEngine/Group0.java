package JMathEngine;
import java.math.BigDecimal;
import java.util.Arrays;
class Group0 {
    public static void calculate(String[] operators,Number[] num_operands,MathEngine calc){
        
        for (int i = 0; i<operators.length;i++){
            Number cur=num_operands[i];
            Number next=num_operands[i+1];
           
            if (operators[i].equals(">")){
                if (cur.toDouble()<=next.toDouble()){
                    
                     num_operands[num_operands.length-1]=new NotRational("0");
                     return;
                }
            }
            else if (operators[i].equals("<")){
                if (cur.toDouble()>=next.toDouble()){
                     num_operands[num_operands.length-1]=new NotRational("0");
                     return;
                }
            }
            else if (operators[i].equals("==")){
                if (cur.toBigDecimal().compareTo(next.toBigDecimal())!=0){
                     num_operands[num_operands.length-1]=new NotRational("0");
                     return;
                }
            }
            else if (operators[i].equals(">=")){
                if (cur.toDouble()<next.toDouble()){
                     num_operands[num_operands.length-1]=new NotRational("0");
                     return;
                }
            }
             else if (operators[i].equals("<=")){
                if (cur.toDouble()>next.toDouble()){
                     num_operands[num_operands.length-1]=new NotRational("0");
                     return;
                }
            }
            else if (operators[i].equals("!=")){
                if (cur.toBigDecimal().compareTo(next.toBigDecimal())==0){
                     num_operands[num_operands.length-1]=new NotRational("0");
                     return;
                }
            }
        
    }
    num_operands[num_operands.length-1]=new NotRational("1");
}
}
