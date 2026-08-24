package JMathEngine;
import java.util.ArrayList;
class AsNumber {
    private static String[] chrs_prev= new String[]{"*","/","+"};
    static Number get_number(String num,MathEngine calc,int level){
        try {
            Double.parseDouble(num);
            return new NotRational(num);
        }
        catch (NumberFormatException exc){
            String fs = ""+num.charAt(0);
            for (String chr:chrs_prev){
                if (fs.equals(chr)){
                    
                    throw new RuntimeException();
                }
            }
            return calc.calculate_inner(num,level);
        }
    }

    public static Number[] as_numbers(String[] operands,MathEngine calc,int level){
        ArrayList<Number> spi = new ArrayList<>();

        for (String num:operands){
            spi.add(AsNumber.get_number(num,calc,level));
        }
        
        Number[] new_spi = new Number[spi.size()];
        for (int i = 0;i<spi.size();i++){
            new_spi[i]=spi.get(i);
        }
        return new_spi;
    }
}
