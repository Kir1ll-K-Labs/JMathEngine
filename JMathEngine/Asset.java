package JMathEngine;

import java.util.ArrayList;

public interface Asset {
    public default Number get_var(String variable_name){return null;};

    public default Boolean has_var(String variable_name){return false;};

    public default Boolean has_fun(String function_name){return false;};

    public default Number get_fun(String function_name,ArrayList<Number> function_content){return null;};

    public default Number on_add(Number a,Number b){
        return null;
    };
    public default Number on_multiply(Number a,Number b){
        return null;
    };
    public default Number on_divide(Number a,Number b){
        return null;
    };
    public default Number on_subtract(Number a,Number b){
        return null;
    };
    public default Number on_percent(Number a,Number b){
        return null;
    };
    public default Number on_pow(Number a,Number b){
        return null;
    };
}
