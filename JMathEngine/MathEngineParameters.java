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

    public Number set_var(String name,String value){
        MathEngine calc=this.calculator;
        if (this.calculator==null){
            calc = new MathEngine(this);
        }
        return this.set_var(name, value, calc);
    }

    public Number set_var(String name,String value,MathEngine calclator){
        return this.set_var(name, calclator.calc(value));
    }
    public Number set_var(String name,Number value){
        this.variables.put(name, new Static_Var(value));
        return value;
    }
    public void set_var(String name,Integer value){
        this.variables.put(name, new Static_Var(new NotRational(value)));
    }

    public void set_var(String name,Function<Number,Number> function){
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

    public void bind_fun(String function_name,Function<Number[],Number> function){
        if (this.functions.containsKey(function_name)){
            throw new RuntimeException("Функция "+function_name+" уже добавлена");
        }
        this.functions.put(function_name, function);
    }
    public void unbind_fun(String function_name){
        if (this.functions.containsKey(function_name)){
            this.functions.remove(function_name);
        }
    }
    public Function<Number[],Number> get_func(String function_name){
        if (this.functions.containsKey(function_name)){
            return this.functions.get(function_name);
        }
        return null;
    }
    public Function<Number[],Number> get_fun(String function_name){
        return this.functions.get(function_name);
    }

    public MathEngineParameters(){
        this.bind_fun("sqrt",
            (Number[] spi)->{
                if (spi.length==0){
                    throw new RuntimeException("sqrt не может быть пустым");
                }
                Number a = spi[0];
                if (a.getClass()==Rational.class){
                    Rational rat = (Rational) a;
                    return new Rational(
                        rat.numerator.sqrt(new MathContext(50)),
                        rat.denominator.sqrt(new MathContext(50))
                    );
                }
                else if (a.getClass()==NotRational.class){
                    NotRational notRational = (NotRational) a;
                    return new NotRational(notRational.toBigDecimal().sqrt(new MathContext(50)));
                }
                throw new RuntimeException();
            }
        );

        this.bind_fun("min",
            (Number[] spi)->{
                if (spi.length==0){
                    throw new RuntimeException("min() не может быть пустым");
                }
                Number cur = spi[0];
                for (int i = 1;i<spi.length;i++){
                    if (spi[i].toDouble()<cur.toDouble()){
                        cur=spi[i];
                    }
                }
                return cur;
            }
        );

        this.bind_fun("max",
            (Number[] spi)->{
                if (spi.length==0){
                    throw new RuntimeException("min() не может быть пустым");
                }
                Number cur = spi[0];
                for (int i = 1;i<spi.length;i++){
                    if (spi[i].toDouble()>cur.toDouble()){
                        cur=spi[i];
                    }
                }
                return cur;
            }
        );
    }
}
