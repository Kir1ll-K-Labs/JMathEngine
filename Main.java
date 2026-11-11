import JMathEngine.Formula;
import JMathEngine.MathEngine;
import JMathEngine.MathEngineParameters;
import JMathEngine.Number;
class DoubleNumber implements Number {
    private Double inner_double;
    DoubleNumber(Double number){
        this.inner_double=number;
    }

    DoubleNumber(String text){
        this.inner_double=Double.parseDouble(text);
    }

    @Override
    public Number add(Number number) {
       return new DoubleNumber(this.inner_double+number.toDouble());
    }

    @Override
    public Number multiply(Number number) {
        return new DoubleNumber(this.inner_double*number.toDouble());
    }
    @Override
    public Number divide(Number number) {
        return new DoubleNumber(this.inner_double/number.toDouble());
    }

    @Override
    public Number pow(Number number) {
        return new DoubleNumber(Math.pow(this.inner_double, number.toDouble()));
    }
    @Override
    public Double toDouble() {
        return this.inner_double;
    }
    @Override
    public Number subtract(Number number) {
         return new DoubleNumber(this.inner_double/number.toDouble());
    }

    @Override
    public Number percent(Number number) {
        return new DoubleNumber(this.inner_double%number.toDouble());
    }

    @Override
    public String toString() {
        return this.inner_double.toString();
    }


}
 class MyParamaters extends MathEngineParameters {
    @Override
    protected Number on_number_parse(String text) {
        return new DoubleNumber(text);
    }
}
public class Main {

    public static void main(String[] args){
        
        Number resp = MathEngine.class_run("1/0", new MathEngine(){{parameters=new MyParamaters();}});
        System.out.println(resp);
    }
}
