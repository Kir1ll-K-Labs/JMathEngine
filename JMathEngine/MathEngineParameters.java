package JMathEngine;

public class MathEngineParameters {
    protected Number on_add(Number a,Number b){
        try {
        return a.add(b);
        }catch (Throwable exception){
            return b.add(a);
        }
    }
    protected  Number on_divide(Number a,Number b){
        try {
            return a.divide(b);
        } catch (Throwable exception){
            return b.divide_reverse(a);
        }
    }
    protected Number on_multiply(Number a,Number b){
        try {
            return a.multiply(b);
        }catch (Throwable exception){
            return b.multiply(a);
        }
    }

    protected Number on_subtract(Number a, Number b){
        try {
            return a.subtract(b);
        } catch (Throwable exception){
            return b.subtract_reverse(a);
        }
    }
    protected Number on_pow(Number a,Number b){
        try {
            return a.pow(b);
        }catch (Throwable exception){
            return b.pow_reverse(a);
        }
    }

    protected Number on_percent(Number a,Number b){
        try {
            return a.percent(b);
        } catch (Throwable exception){
            return b.percent_reverse(a);
        }
    }
}
