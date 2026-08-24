package JMathEngine;

import java.util.ArrayList;
import java.util.Arrays;

class Group1separator {
    private static String[] chrs_prev= new String[]{"*","/","+","-","^","%"};
    private static boolean contain_prev_char(String chr){
        for (String ch:chrs_prev){
            if (chr.equals(ch)){
                return true;
            }
        }
        return false;
    }
    private static boolean is_equal_one(String chr,String[] separators){
        for (String sep:separators){
            if (chr.equals(sep)){
                return true;
            }
        }
        return false;
    }

    public static Number[] separate(String text,String[] separators,boolean multiply,MathEngine calc,int level){
        ArrayList<Number> spi = new ArrayList<>();
        StringBuilder builder = new StringBuilder(text);
        int left = 0;
        int right = 0;
        int breakets_count=0;
        while (right<builder.length()){
            String current_s = ""+builder.charAt(right);
            if (current_s.equals("(")){
                breakets_count+=1;
            }
            else if (current_s.equals(")")){
                breakets_count-=1;
            }
            if (breakets_count>0){
                right+=1;
                continue;
            }
            if (is_equal_one(current_s, separators)){
                if (current_s.equals("-")){
                    if (right==0||contain_prev_char(""+builder.charAt(right-1))){
                        right+=1;
                        continue;
                    }
                }
                spi.add(AsNumber.get_number(builder.substring(left, right),calc,level));
                left=right+1;
            }
            right+=1;
        }
        spi.add(AsNumber.get_number(builder.substring(left, right),calc,level));

        Number[] nspi=new Number[spi.size()];
        for (int i = 0;i<nspi.length;i++){
            nspi[i]=spi.get(i);
        }
       
        return nspi;
    }

    public static Character[] separate_operators(String text){
        ArrayList<Character> spi = new ArrayList<>();
        
        Character cur_char;
        int count_breakets=0;
        for (int i = 0;i<text.length();i++){
            cur_char=text.charAt(i);
            if (cur_char.equals('(')){
                count_breakets+=1;
            }
            else if (cur_char.equals(')')){
                count_breakets-=1;
            }
            if (count_breakets>0){
                continue;
            }
            if (cur_char=='+'){
                spi.add('+');
            }
            else if (cur_char=='-'){
                if (i==0||contain_prev_char(""+text.charAt(i-1))){
                    continue;
                }
                spi.add('-');
            }
        }
        Character[] nspi=new Character[spi.size()];
        for (int i = 0;i<spi.size();i++){
            nspi[i]=spi.get(i);
        }
        return nspi;
    }
}
