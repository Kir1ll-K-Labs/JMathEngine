package JMathEngine;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface Number {


    @Override
    String toString();

    Integer toInteger();

    default Double toDouble(){
         throw new RuntimeException(this.toString()+" не может быть преобразован в Double");
    };

    Long toLong();

    

    default Boolean toBoolean(){
        throw new RuntimeException(this.toString()+" не может быть преобразован в Boolean");
    };

    default BigDecimal toBigDecimal(){
        throw new RuntimeException(this.toString()+" не может быть преобразован в BigDecimal");
    };

    Number add(Number other);

    Number subtract(Number other);

    Number multiply(Number other);

    Number divide(Number other);

    //Divide
    default Number divide(int integer){
        return this.divide(Number.valueOf(integer));
    }

    default Number divide(long long_val){
        return this.divide(Number.valueOf(long_val));
    }

    default Number divide(double double_val){
        return this.divide(Number.valueOf(double_val));
    }
    default Number divide(BigDecimal bigDecimal){
        return this.divide(Number.valueOf(bigDecimal));
    }
    default Number divide(BigInteger bigInteger){
        return this.divide(Number.valueOf(bigInteger));
    }
    default Number divide(String text){
        return this.divide(Number.valueOf(text));
    }
    //Multiply

    default Number multiply(int integer){
        return this.multiply(Number.valueOf(integer));
    }

    default Number multiply(long long_val){
        return this.multiply(Number.valueOf(long_val));
    }

    default Number multiply(double double_val){
        return this.multiply(Number.valueOf(double_val));
    }
    default Number multiply(BigDecimal bigDecimal){
        return this.multiply(Number.valueOf(bigDecimal));
    }
    default Number multiply(BigInteger bigInteger){
        return this.multiply(Number.valueOf(bigInteger));
    }
    default Number multiply(String text){
        return this.multiply(Number.valueOf(text));
    }

    //Add
    default Number add(int integer){
        return this.add(Number.valueOf(integer));
    }

    default Number add(long long_val){
        return this.add(Number.valueOf(long_val));
    }

    default Number add(double double_val){
        return this.add(Number.valueOf(double_val));
    }
    default Number add(BigDecimal bigDecimal){
        return this.add(Number.valueOf(bigDecimal));
    }
    default Number add(BigInteger bigInteger){
        return this.add(Number.valueOf(bigInteger));
    }
    default Number add(String text){
        return this.add(Number.valueOf(text));
    }

    //Subtract
    default Number subtract(int integer){
        return this.subtract(Number.valueOf(integer));
    }

    default Number subtract(long long_val){
        return this.subtract(Number.valueOf(long_val));
    }

    default Number subtract(double double_val){
        return this.subtract(Number.valueOf(double_val));
    }
    default Number subtract(BigDecimal bigDecimal){
        return this.subtract(Number.valueOf(bigDecimal));
    }
    default Number subtract(BigInteger bigInteger){
        return this.subtract(Number.valueOf(bigInteger));
    }
    default Number subtract(String text){
        return this.subtract(Number.valueOf(text));
    }
    //Percent
    default Number percent(int integer){
        return this.percent(Number.valueOf(integer));
    }

    default Number percent(long long_val){
        return this.percent(Number.valueOf(long_val));
    }

    default Number percent(double double_val){
        return this.percent(Number.valueOf(double_val));
    }
    default Number percent(BigDecimal bigDecimal){
        return this.percent(Number.valueOf(bigDecimal));
    }
    default Number percent(BigInteger bigInteger){
        return this.percent(Number.valueOf(bigInteger));
    }
    default Number percent(String text){
        return this.percent(Number.valueOf(text));
    }
    //Pow
    default Number pow(int integer){
        return this.pow(Number.valueOf(integer));
    }

    default Number pow(long long_val){
        return this.pow(Number.valueOf(long_val));
    }

    default Number pow(double double_val){
        return this.pow(Number.valueOf(double_val));
    }
    default Number pow(BigDecimal bigDecimal){
        return this.pow(Number.valueOf(bigDecimal));
    }
    default Number pow(BigInteger bigInteger){
        return this.pow(Number.valueOf(bigInteger));
    }
    default Number pow(String text){
        return this.pow(Number.valueOf(text));
    }

    // numer^other
    Number pow(Number other);
    // other^number
    default Number pow_reverse(Number other){
         throw new RuntimeException("Возведение "+other.toString()+ " в степень "+this.toString()+" невозможно.");
    };
    default Number percent_reverse(Number other){
         throw new RuntimeException("% "+this.toString()+ " от "+other.toString()+" невозможен.");
    };

    default Number divide_reverse(Number other){
        throw new RuntimeException("Деление "+other.toString()+" на "+this.toString()+" невозможно.");
    }

    default Number subtract_reverse(Number other){
        throw new RuntimeException("Вычитание "+this.toString()+" из "+other.toString()+" невозможно.");
    }

    Number percent(Number other);

    default int compareTo(Number number){
        return this.toBigDecimal().compareTo(number.toBigDecimal());
    }

    static Boolean is_arifmetic_string(String text){
        Character[] chrs_list = new Character[]{'+','-','*','/','%','^','=','<','>','!'};
        for (Character character:text.toCharArray()){
            for (Character cur_char:chrs_list){
                if (character==cur_char){return true;}
            }
        }
        return false;
    }

    static NotRational valueOf(BigDecimal bigDecimal){
        return new NotRational(bigDecimal);
    }

    static NotRational valueOf(BigInteger bigInteger){
        return new NotRational(bigInteger);
    }

    static Number valueOf(String text){return new NotRational(text);}

    static NotRational valueOf(long long_val){return new NotRational(long_val);}
    static NotRational valueOf(double double_val){return new NotRational(double_val);}

    static NotRational valueOf(int integer){return new NotRational(integer);}

    static Number valueOf(BigDecimal bigDecimal,BigDecimal bigDecimal2){return new NotRational(bigDecimal).divide(new NotRational(bigDecimal2));}

    static Number valueOf(BigInteger bigInteger,BigInteger bigInteger2){return new NotRational(bigInteger).divide(new NotRational(bigInteger2));}

    static Number valueOf(long long_val,long long_val2){return new NotRational(long_val).divide(new NotRational(long_val2));}
    static Number valueOf(double double_val,double double_val2){return new NotRational(double_val).divide(new NotRational(double_val2));}



    static Number valueOf(int integer,int integer2){return new NotRational(integer).divide(new NotRational(integer2));}
    public static Boolean isNumber(String text){
        Character[] chrs_list = new Character[]{'0','1','2','3','4','5','6','7','8','9'};
        boolean slashed=false;
        boolean point=false;
        boolean minus=false;
        if (text.charAt(text.length()-1)=='.'||text.charAt(text.length()-1)=='/'){
            return false;
        }
        if (text.charAt(0)=='/'){
            return false;
        }
        for (Character character:text.toCharArray()){
            if (character=='-'){
                if (minus){return false;}
                minus=true;
                continue;
            }
            else if (character=='.'){
                if (point){return false;}
                point=true;continue;
            }
            else if (character=='/'){
                if (slashed){return false;}
                point=false;
                minus=false;
                slashed=true;continue;
            }
            boolean stop=true;
            for (Character other_Character:chrs_list){
                if (character==other_Character){stop=false;break;}
            }
            if (stop){return false;}
        }
        return true;
    }
}