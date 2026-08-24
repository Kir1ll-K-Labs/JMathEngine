package JMathEngine;


import java.util.Arrays;
import java.util.function.Function;
public class MathEngine {
    public MathEngineParameters parameters;


    private static String[] chrs_prev= new String[]{"*","/","+","%","^"};
    private static final String[] chrs_group0=new String[]{">","<","!","="};
    private static final String[] chrs_group2=new String[]{"*","/"};
    private static final String[] chrs_group3=new String[]{"%"};
    private static final String[] chrs_group4=new String[]{"^"};
    private static final String[] chrs_group5=new String[]{","};
    private static boolean contains_chr(String text){
        for (String chr:chrs_prev){
            if (chr.equals(text)){
                return true;
            }
        }
        return true;
    }

    private static String replace_all_in_text(String text){
        StringBuilder builder=new StringBuilder(text);
        int i=0;
        while (i<builder.length()){
            if ((builder.charAt(i)=='-')|(builder.charAt(i)=='+')){
                int j = i;
                boolean is_negative;
                if (builder.charAt(j)=='-'){
                    is_negative=true;
                }
                else {
                    is_negative=false;
                }
                while (true){
                    j+=1;
                    if (j>=builder.length()){
                        
                        throw new RuntimeException("Некорректное выражение");
                    }
                    if (builder.charAt(j)=='-'){
                        is_negative = !is_negative;
                    }else if (builder.charAt(j)=='+'){

                    }
                    else {
                        break;
                    }
                }
                if (is_negative){
                    builder.replace(i, j, "-");
                    
                }
                else{
                    
                    builder.replace(i, j, "+");
                }
                i=j;
                continue;
            }
            
            i+=1;
        }
        return builder.toString();
    }

    private Number parse_function(String function_name,String data){
        Function<Number[],Number> function_object=this.parameters.get_fun(function_name);
        if (function_object==null){
            throw new RuntimeException("Ненайдена функция "+function_name);
        }
        Number[] numbers = BasicSeparator.separate(data, chrs_group5,true,this,-1);
        

        Number function_responce=function_object.apply(numbers);
        return function_responce;
    }

    public MathEngine(MathEngineParameters parameters){
        this.parameters = parameters;
    }
    public MathEngine(){
        MathEngineParameters parameters = new MathEngineParameters();
        parameters.calculator=this;
        this.parameters = parameters;
    }

    public Number calc(String text){
        if (text.length()==0){
            throw new RuntimeException("Пустая строка");
        }
        text=text.replaceAll(" ", "");
        text = replace_all_in_text(text);
        //text = calc_in_breakets(text);
        return this.calculate_inner(text,-1);
    }

    Number calculate_inner(String text,int level){
       
        String[] operators;
        Number[] num_operands;
        StringBuilder builder = new StringBuilder(text);
        int open_breeaket_index=builder.indexOf("(");
        if (open_breeaket_index!=-1){
            int close_breaket_index=open_breeaket_index+1;
            int breakets_count=1;
            while (close_breaket_index<builder.length()){
                Character chr = builder.charAt(close_breaket_index);
                if (chr=='('){
                    breakets_count+=1;
                }
                else if (chr==')'){
                    breakets_count-=1;
                    if (breakets_count==0){
                        if (close_breaket_index==builder.length()-1){
                            if (open_breeaket_index==0){
                                return this.calculate_inner(builder.substring(1,builder.length()-1), -1);
                            }
                            else {
                                String f_name=builder.substring(0,open_breeaket_index);
                                if (this.parameters.contains_fun(f_name)){
                                    return this.parse_function(f_name, builder.substring(open_breeaket_index+1,close_breaket_index));
                                }
                            }
                        }
                        break;
                    }
                }
                close_breaket_index+=1;
            }
        }
        //text=builder.toString();
        text=builder.toString();
        if (level<1){
            operators = BasicSeparator.exclude_separate(text, chrs_group0);
             if (operators.length>0){
            
                num_operands = BasicSeparator.separate(text, chrs_group0,true,this,1);
                Group0.calculate(operators,num_operands,this);
                return num_operands[num_operands.length-1];
            }
        }
        if (level<2){
            Character[] operato = Group1separator.separate_operators(text);
         
            if (operato.length>0){
           
                num_operands = Group1separator.separate(text,new String[]{"+","-"},true,this,-1);
                
                Group1.calculate(operato,num_operands,this);
                return num_operands[num_operands.length-1];
            }
        }
        if (level<3){
            operators = BasicSeparator.exclude_separate(text, chrs_group2);
            if (operators.length>0){
                num_operands = BasicSeparator.separate(text, chrs_group2,true,this,3);
                
               
                Group2.calculate(operators,num_operands,this);
                return num_operands[num_operands.length-1];
            }
        }

        if (level<4){
            operators = BasicSeparator.exclude_separate(text, chrs_group3);
            if (operators.length>0){
            
                num_operands = BasicSeparator.separate(text, chrs_group3,true,this,4);
                Group3.calculate(operators,num_operands,this);
                return num_operands[num_operands.length-1];
            }
        }

        if (level<5){
            operators = BasicSeparator.exclude_separate(text, chrs_group4);

            if (operators.length>0){
                
                num_operands = BasicSeparator.separate(text,chrs_group4,true,this,5);
              
                Number num = Group4.calculate(num_operands,this);
                return num;
            }
        }
        try {
            Double.parseDouble(text);
            return new NotRational(text);
        }
        catch (NumberFormatException exc){
            
            if (this.parameters.has_var(text)){
                return this.parameters.get_var(text);
            }
        }
        throw new RuntimeException("Неизвестно: "+text+" ");

    }

    public static boolean is_number(String text){
        try {
            Double.parseDouble(text);
            return true;
        }
        catch (NumberFormatException exc){}
        return false;
    }
}