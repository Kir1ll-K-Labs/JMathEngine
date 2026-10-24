import JMathEngine.Formula;
import JMathEngine.MathEngine;

public class Main {
    public static void main(String[] args){
        MathEngine engine = new MathEngine();
        Formula f = engine.evaluatePostfix("2 2 + 2 2 + *");
        System.out.println(f.toString()); //1*(2+min(1 , 2))
    }
}
