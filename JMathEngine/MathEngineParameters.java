package JMathEngine;

import java.lang.invoke.LambdaMetafactory;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.function.Function;

import JMathEngine.Number;


interface VariableInterface {
    public Number get();
}
class Static_Var implements VariableInterface{
    private Number number;
    Static_Var(Number num){
        this.number=num;
    }
    
    public Number get() {
        return this.number;
    }
}
class Function_Var implements VariableInterface{
    private Function<Number,Number> number;
    Function_Var(Function<Number,Number> num){
        this.number=num;
    }
    
    public Number get() {
        return this.number.apply(null);
    }
}


public class MathEngineParameters {
    private HashMap<String,VariableInterface> variables = new HashMap<>();
    private HashMap<String,Function<Number[],Number>> functions = new HashMap<>();
    public MathEngine calculator;
    public Number get_var(String name){
        return this.variables.get(name).get();
    }

    public boolean contains_fun(String name){
        return this.functions.containsKey(name);
    }

    public Number on_add(Number a,Number b){
        return a.add(b);
    }

    public Number on_divide(Number a,Number b){
        return a.divide(b);
    }

    public Number on_multiply(Number a,Number b){
        return a.multiply(b);
    }

    public Number on_pow(Number a,Number b){
        return a.pow(b);
    }

    public Number add_var(String name,String value){
        MathEngine calc=this.calculator;
        if (this.calculator==null){
            calc = new MathEngine(this);
        }
        return this.add_var(name, value, calc);
    }

    public Number add_var(String name,String value,MathEngine calclator){
        return this.add_var(name, calclator.evaluate(value));
    }
    public Number add_var(String name,Number value){
        this.variables.put(name, new Static_Var(value));
        return value;
    }
    public void add_var(String name,Integer value){
        this.variables.put(name, new Static_Var(new NotRational(value)));
    }

    public void add_var(String name,Function<Number,Number> function){
        this.variables.put(name, new Function_Var(function));
    }
    
    public int var_count(){
        return this.variables.size();
    }

    public boolean has_var(String name){
        return this.variables.containsKey(name);
    }

    public boolean remove_var(String name){
        if (this.variables.containsKey(name)){
            this.variables.remove(name);
            return true;
        }
        return false;
    }

    public void add_fun(String function_name,Function<Number[],Number> function){
        if (this.functions.containsKey(function_name)){
            throw new RuntimeException("Функция "+function_name+" уже добавлена");
        }
        this.functions.put(function_name, function);
    }
    public void remove_fun(String function_name){
        if (this.functions.containsKey(function_name)){
            this.functions.remove(function_name);
        }
    }
    public Function<Number[],Number> get_fun(String function_name){
        if (this.functions.containsKey(function_name)){
            return this.functions.get(function_name);
        }
        return null;
    }
    public MathEngineParameters(){
        
    }
}
