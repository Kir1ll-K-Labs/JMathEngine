package JMathEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class VarsFunctionsParams {
    private interface VarInterface{
        Number get();
    }
    private class StaticVar implements VarInterface{
        private Number inner;
        @Override
        public Number get() {
            return inner;
        }
        StaticVar(Number number){
            this.inner=number;
        }
    }

    private HashMap<String,VarInterface> vars=new HashMap<>();
    private HashMap<String,Function<ArrayList<Number>,Number>> functions=new HashMap<>();
    public void put_var(String name,Number number){
        vars.put(name, new StaticVar(number));
    }
    public Number get_var(String name){
        VarInterface num = this.vars.getOrDefault(name, null);
        if (num!=null){
            return num.get();
        }
        return null;
    }
    public void del_var(String name){
        if (this.vars.containsKey(name)){
            this.vars.remove(name);
        }
    }
    public Function<ArrayList<Number>,Number> get_fun(String function_name){
        return this.functions.getOrDefault(function_name, null);
    }

    public void put_fun(String name,Function<ArrayList<Number>,Number> function){
        this.functions.put(name, function);
    }
    public void del_fun(String name){
        if (this.functions.containsKey(name)){
            this.functions.remove(name);
        }
    }
    public Number call_function(String name,ArrayList<Number> numbers_list){
        Function<ArrayList<Number>,Number> function = this.functions.getOrDefault(name, null);
        if (function!=null){
            return function.apply(numbers_list);
        }
        return null;
    }
}
