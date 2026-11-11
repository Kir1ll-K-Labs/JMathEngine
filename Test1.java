import JMathEngine.Formula;
import JMathEngine.MathEngine;
import JMathEngine.MathEngineParameters;
import JMathEngine.Number;
//Выражение 1/3+1+1+1+1
// повторений в цикле 100 000 000
//без оптимизаций = 247,77 сек
//AST дерево = 98,05 сек
//AST дерево + Пользовательский Double класс который реализовал Number = 3,35 сек
class Timer{
    private Long starttime;
    Timer(){
        starttime=System.nanoTime();
    }
    public void stop(){
        Long endtime=System.nanoTime();
        System.err.println("Наносекунд "+(endtime-starttime));
    }
}
class DoubleNumber implements Number{
    private Double innernum;
    DoubleNumber(String text){
        this.innernum=Double.parseDouble(text);
    }
    DoubleNumber(Double num){
        this.innernum=num;
    }
    @Override
    public Double toDouble() {
        return this.innernum;
    }
    @Override
    public Number add(Number number) {
        return new DoubleNumber(this.innernum+number.toDouble());
    }
    @Override
    public Number subtract(Number number) {
        return new DoubleNumber(this.innernum-number.toDouble());
    }
    @Override
    public Number divide(Number number) {
         return new DoubleNumber(this.innernum/number.toDouble());
    }
    @Override
    public Number multiply(Number number) {
         return new DoubleNumber(this.innernum*number.toDouble());
    }
    @Override
    public Number percent(Number number) {
         return new DoubleNumber(this.innernum%number.toDouble());
    }
    @Override
    public Number pow(Number number) {
         return new DoubleNumber(Math.pow(this.innernum, number.toDouble()));
    }

}
class MyParamaters extends MathEngineParameters{
    @Override
    protected Number on_number_parse(String text) {
       return new DoubleNumber(text);
    }
}
public class Test1 {
    public static void main(String[] args) {
        Timer t = new Timer();
        MathEngine engine = new MathEngine(new MyParamaters());
        Formula f = engine.evaluate("1/3+1+1+1+1");
        for (int i = 0;i<=100000000;i++){
            f.run();
        }
        t.stop();
    }
}
