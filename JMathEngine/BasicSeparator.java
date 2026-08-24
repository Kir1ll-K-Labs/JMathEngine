package JMathEngine;

import java.util.ArrayList;

class BasicSeparator {
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
        int border_a=0;
        int border_b=0;
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
                if (right==left & multiply){
                    left=right+1;
                    right+=1;
                    continue;
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

     public static Number[] separate(String text,String[] separators,MathEngine calc,int level){
        return separate(text, separators,false,calc,level);
     }

     public static String[] exclude_separate(String text,String[] separators){
        ArrayList<String> spi = new ArrayList<>();
        int breakets_count=0;
        StringBuilder builder = new StringBuilder(text);
        int left = 0;
        int right = 0;
        while (left<builder.length()){
            String current_s = ""+builder.charAt(left);
          
            if (current_s.equals("(")){
                
                breakets_count=breakets_count+1;
            }
            else if (current_s.equals(")")){
                breakets_count-=1;
            }
            if (breakets_count>0){
                left+=1;
                continue;
            }
            if (is_equal_one(current_s, separators)){
                right=left+=1;
                do {
                     current_s = ""+builder.charAt(right);
                     right+=1;
                } while (is_equal_one(current_s, separators));
                spi.add(builder.substring(left-1,right-1));
                left=right-2;
            }
            left+=1;
        }

        String[] nspi=new String[spi.size()];
        for (int i = 0;i<nspi.length;i++){
            nspi[i]=spi.get(i);
        }
        return nspi;
     }
}
