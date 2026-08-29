package JMathEngine;
import java.util.ArrayList;
import java.util.HashMap;

import JMathEngine.Number;
public class TreeCreator {

    public static class EvalParameters{
        public Formula formula;
        public MathEngine engine;
        EvalParameters(){

        }
     }

    private static final HashMap<Character,Boolean> chrs_group0=new HashMap<>();
    private static final HashMap<Character,Boolean> chrs_group1=new HashMap<>();
    private static final HashMap<Character,Boolean> chrs_group2=new HashMap<>();
    private static final HashMap<Character,Boolean> chrs_group3=new HashMap<>();
    private static final HashMap<Character,Boolean> chrs_group4=new HashMap<>();
    private static final HashMap<Character,Boolean> chrs_group5=new HashMap<>();
    static {
        chrs_group0.put('=', true);
        chrs_group0.put('<', true);
        chrs_group0.put('>', true);
        chrs_group0.put('!', true);

        chrs_group1.put('+', true);
        chrs_group1.put('-', true);

        chrs_group2.put('*', true);
        chrs_group2.put('/', true);

        chrs_group3.put('%', true);

        chrs_group4.put('^', true);

        chrs_group5.put(',', true);
    }
    private final StringBuilder builder;
    private int pos;

    public class TreeNode{}

    public class Operation_Node extends TreeNode{
        String operation;
        TreeNode left;
        TreeNode right;
        public Operation_Node(String operation,TreeNode left,TreeNode right){
            this.operation=operation;
            this.left=left;
            this.right=right;
        }
        @Override
        public String toString() {
            String left_string = this.left.toString();
            String right_string = this.right.toString();
            return left_string+this.operation+right_string;
        }
    }

    public class BreaketNode extends TreeNode {
        TreeNode innerNode;
        BreaketNode(TreeNode node){
            this.innerNode=node;
        }
        @Override
        public String toString() {
            return "("+this.innerNode.toString()+")";
        }
    }

    public class Number_Node extends TreeNode{
        public Number number;

        Number_Node(String text){
            this.number=new NotRational(text);
        }

        Number_Node(Number number){
            this.number=number;
        }

        @Override
        public String toString() {
           return this.number.toString();
        }
    }

    public class VariableNode extends TreeNode {
        public String name;
        VariableNode(String name){
            this.name=name;
        }
        @Override
        public String toString() {
           return this.name;
        }
    }

    public class FunctionNode extends TreeNode{
        public String name;
        public ArrayList<TreeNode> args;

        FunctionNode(String name, ArrayList<TreeNode> args){
            
            this.name=name;
            this.args=args;
        }
    }

    public TreeCreator(String input){
        this.builder=new StringBuilder(input);
        this.pos=0;
    }

    public TreeNode make_tree(){
        TreeNode node = parse_group_0();
        if (pos!=this.builder.length()){
            throw new IllegalArgumentException("Неизвестный символ на "+pos);
        }
        return node;
    }

    TreeNode parse_group_0(){
        TreeNode node = parse_group_1();
        while (this.pos<this.builder.length()){
            char c = builder.charAt(this.pos);
            if (chrs_group0.containsKey(c)){
                
                int spos=pos;
                while (spos<this.builder.length()&&chrs_group0.containsKey(this.builder.charAt(pos))){
                    pos+=1;
                }
                int end = this.pos;
                TreeNode r =parse_group_1();
                if (node instanceof Operation_Node){
                    throw new RuntimeException("Библиотека не поддерживает цепочики сравнений.");
                }
                node=new Operation_Node(builder.substring(spos, end), node, r);
            }
            else {
                break;
            }
        }
        return node;
    }

    TreeNode parse_group_1(){
        TreeNode node = parse_group_2();
        while (this.pos<this.builder.length()){
            char c = builder.charAt(this.pos);
            if (chrs_group1.containsKey(c)){
                if (this.pos==0){
                    break;
                }
                int spos=pos;
                while (spos<this.builder.length()&&chrs_group1.containsKey(this.builder.charAt(pos))){
                    pos+=1;
                }
                int end = this.pos;
                TreeNode r =parse_group_2();
                node=new Operation_Node(builder.substring(spos, end), node, r);
            }
            else {
                break;
            }
        }
        return node;
    }
    TreeNode parse_group_2(){
        TreeNode node = parse_group_3();
        while (this.pos<this.builder.length()){
            char c = builder.charAt(this.pos);
            if (chrs_group2.containsKey(c)){
                int spos=pos;
                while (spos<this.builder.length()&&chrs_group2.containsKey(this.builder.charAt(pos))){
                    pos+=1;
                }
                int end = this.pos;
                TreeNode r =parse_group_3();
                node=new Operation_Node(builder.substring(spos, end), node, r);
            }
            else {
                break;
            }
        }
        return node;
    }
    TreeNode parse_group_3(){
        TreeNode node = parse_group_4();
        while (this.pos<this.builder.length()){
            char c = builder.charAt(this.pos);
            if (chrs_group3.containsKey(c)){
                int spos=pos;
                //while (spos<this.builder.length()&&chrs_group3.containsKey(this.builder.charAt(pos))){
                    //pos+=1;
                //}
                int end = this.pos;
                TreeNode r =parse_group_4();
                node=new Operation_Node(builder.substring(spos, end), node, r);
            }
            else {
                break;
            }
        }
        return node;
    }
     TreeNode parse_group_4(){
        TreeNode node = parseFactor();
        while (this.pos<this.builder.length()){
            char c = builder.charAt(this.pos);
            if (chrs_group4.containsKey(c)){
                int spos=pos;
                while (spos<this.builder.length()&&chrs_group4.containsKey(this.builder.charAt(pos))){
                    pos+=1;
                }
                int end = this.pos;
                TreeNode r =parse_group_4();
                node=new Operation_Node(builder.substring(spos, end), node, r);
            }
            else {
                break;
            }
        }
        return node;
     }
     TreeNode parseFactor(){
        if (pos>=this.builder.length()) throw new IllegalArgumentException();
        char c = this.builder.charAt(pos);
        if (Character.isLetter(c)||c=='_'||c=='-'){
            String[] spi = parseIdentifier();
            if (spi[1].equals("1")){
                if (pos<this.builder.length()&&this.builder.charAt(pos)=='('){
                    return parseFunctionCall(spi[0]);
                }
                return new VariableNode(spi[0]);
            }
            else {
                return new Number_Node(spi[0]);
            }
        }
        if (Character.isDigit(c)||c=='-'){
            return parseNumber();
        }
        if (c=='('){
            this.pos+=1;
            TreeNode inner = parse_group_0();
            if (pos>=builder.length()||builder.charAt(pos)!=')'){
                throw new IllegalArgumentException("");
            }
            this.pos+=1;
            if (inner instanceof BreaketNode bn){
                inner = bn.innerNode;
            }
            return new BreaketNode(inner);
        }
        throw new IllegalArgumentException();
     }

     private String[] parseIdentifier(){
        int start = pos;
        String is_var="0";
        if (builder.charAt(pos)=='-'){
            this.pos+=1;
        }
        while (pos <this.builder.length()&&(Character.isLetterOrDigit(this.builder.charAt(pos))||this.builder.charAt(pos)=='=')){
            if (Character.isLetter(this.builder.charAt(pos))){
                is_var="1";
            }
            this.pos+=1;
        }
        return new String[]{this.builder.substring(start, this.pos),is_var};
     }

     private TreeNode parseFunctionCall(String name){
        this.pos+=1;
        ArrayList<TreeNode> args = new ArrayList<>();
        if (pos<builder.length()&&builder.charAt(pos)==')'){
            return new FunctionNode(name, args);
        }
        args.add(parse_group_0());
        while (pos<builder.length()&&builder.charAt(pos)==','){
            this.pos+=1;
            args.add(parse_group_0());
        }
        this.pos+=1;
        return new FunctionNode(name, args);
     }

     TreeNode parseNumber(){
        int start = this.pos;
        if (this.builder.charAt(this.pos)=='-'){
            this.pos+=1;
        }
        boolean has_point=false;
        while (pos<this.builder.length()&&(Character.isDigit(this.builder.charAt(pos))||this.builder.charAt(pos)=='.')){
            if (this.builder.charAt(pos)=='.'){
                if (has_point){
                    throw new IllegalArgumentException("Double Point in one number");
                }
                has_point=true;
            }
            this.pos+=1;
        }
        return new Number_Node(this.builder.substring(start,this.pos));
     }

     TreeNode parseVariable(){
        int start=pos;
        while (pos<this.builder.length()&&(Character.isLetterOrDigit(this.builder.charAt(this.pos))||this.builder.charAt(this.pos)=='_')){
            pos+=1;
        }
        return new VariableNode(this.builder.substring(start, pos));
     }

     public static Number eval(TreeNode node,EvalParameters evalParameters){
        if (node instanceof Number_Node n) return n.number;
        if (node instanceof VariableNode v){
            boolean is_negative=false;
            String new_name = v.name;
            Number variable = null;
            if (new_name.charAt(0)=='-'){
                
                is_negative=true;
                new_name=new_name.substring(1);
            }
            if (evalParameters.formula!=null){
                variable = evalParameters.formula.get_var(new_name);
                if (variable!=null){
                if (is_negative){
                    variable=variable.multiply(Number.valueOf(-1));
                }
                return variable;
            }
            }
            if (evalParameters.engine!=null){
                variable = evalParameters.engine.get_var(new_name);
                if (variable!=null){
                if (is_negative){
                    variable=variable.multiply(Number.valueOf(-1));
                }
                return variable;
                }
            }
            variable = MathEngine.get_global_var(new_name);
            if (variable!=null){
                if (is_negative){
                    variable=variable.multiply(Number.valueOf(-1));
                }
                return variable;
                }
            throw new IllegalStateException("Ненайдена переменная "+new_name);
        }
        if (node instanceof Operation_Node op){
            Number l = eval(op.left,evalParameters);
            Number r = eval(op.right,evalParameters);
            
            if (op.operation.equals(">")){
                if (l.compareTo(r)>0){
                    return Number.valueOf(1);
                }
                return Number.valueOf(0);
            }
            if (op.operation.equals("<")){
                if (l.compareTo(r)<0){
                    return Number.valueOf(1);
                }
                return Number.valueOf(0);
            }
            if (op.operation.equals(">=")){
                if (l.compareTo(r)>=0){
                    return Number.valueOf(1);
                }
            }
            if (op.operation.equals("<=")){
                if (l.compareTo(r)<=0){
                    return Number.valueOf(1);
                }
            }
            if (op.operation.equals("==")){
                if (l.compareTo(r)==0){
                    return Number.valueOf(1);
                }
            }
            if (op.operation.equals("!=")){
                if (l.compareTo(r)!=0){
                    return Number.valueOf(1);
                }
            }
            
            return switch (op.operation){
                case "+" -> evalParameters.engine.parameters.on_add(l, r);
                case "*" -> evalParameters.engine.parameters.on_multiply(l, r);
                case "/" -> evalParameters.engine.parameters.on_divide(l, r);
                case "-" -> evalParameters.engine.parameters.on_subtract(l, r);
                case "^" -> evalParameters.engine.parameters.on_pow(l, r);
                case "%" -> evalParameters.engine.parameters.on_percent(l, r);
                default -> throw new IllegalArgumentException();
            };
        }
        if (node instanceof FunctionNode fn){
            boolean is_negative=false;
            String new_name = fn.name;
            if (new_name.charAt(0)=='-'){
                is_negative=true;
                new_name=new_name.substring(1);
            }
            ArrayList<Number> spi = new ArrayList<>();
            for (int i = 0;i<fn.args.size();i++){
                spi.add(eval(fn.args.get(i), evalParameters));
            }
            Number ret = evalParameters.engine.call_function(new_name, spi);
            if (ret!=null){
                if (is_negative){
                    ret=ret.multiply(Number.valueOf(-1));
                }
                return ret;
            }
            ret = MathEngine.call_global_fun(new_name, spi);
            if (ret!=null){
                if (is_negative){
                    ret=ret.multiply(Number.valueOf(-1));
                }
                return ret;
            }
            throw new IllegalStateException("Ненайдена функция "+new_name);
        }
        if (node instanceof BreaketNode bn){
            return eval(bn.innerNode, evalParameters);
        }
        throw new IllegalStateException();
     }


     public static String printNode(TreeNode node){
        return node.toString();
     }
    TreeNode simplify(TreeNode node,MathEngine engine){
         if (node == null) return null;

        // Листья
        if (node instanceof Number_Node || node instanceof VariableNode) {
            return node;
        }

    // BreaketNode: упрощаем внутри, если результат число - убираем скобки
    if (node instanceof BreaketNode) {
        BreaketNode bNode = (BreaketNode) node;
        TreeNode inner = simplify(bNode.innerNode,engine);
        
        if (inner instanceof Number_Node) {
            return inner; // (2+2) -> 4
        }
        return new BreaketNode(inner);
    }

    // BinaryOpNode: ГЛАВНОЕ МЕСТО ДЛЯ ИСПРАВЛЕНИЯ
    if (node instanceof Operation_Node) {
        Operation_Node binNode = (Operation_Node) node;
        
        // 1. Рекурсивно упрощаем детей (это раскроет цепочки снизу вверх)
        TreeNode left = simplify(binNode.left,engine);
        TreeNode right = simplify(binNode.right,engine);
        // 2. Пытаемся свернуть константы
        if (left instanceof Operation_Node && ((Operation_Node) left).operation.equals(binNode.operation)) {
            Operation_Node leftAdd = (Operation_Node) left;
        // Если у левого + правый ребёнок — число, а текущий правый ребёнок — тоже число
        if (leftAdd.right instanceof Number_Node && right instanceof Number_Node) {
            Number lVal = ((Number_Node) leftAdd.right).number;
            Number rVal = ((Number_Node) right).number;
            String real_op = binNode.operation;
            if (binNode.operation.equals("/")){
                real_op="*";
            }
            try {
                Number sum =switch (real_op){
                case "+" -> engine.parameters.on_add(lVal, rVal);
                case "*" -> engine.parameters.on_multiply(lVal, rVal);
                case "/" -> engine.parameters.on_divide(lVal, rVal);
                case "-" -> engine.parameters.on_subtract(lVal, rVal);
                case "^" -> engine.parameters.on_pow(lVal, rVal);
                case "%" -> engine.parameters.on_percent(lVal, rVal);
                default -> throw new IllegalArgumentException();
            };
                // Строим новое дерево: (левый левый) + (сумма чисел)
                return new Operation_Node(binNode.operation, leftAdd.left, new Number_Node(sum));
            } catch (Exception e) {
                // Если не получилось — оставляем как есть
            }
        }
    }

        if (left instanceof Number_Node && right instanceof Number_Node) {
            Number lVal = ((Number_Node) left).number;
            Number rVal = ((Number_Node) right).number;
            try {
                Number result = switch (binNode.operation){
                case "+" -> engine.parameters.on_add(lVal, rVal);
                case "*" -> engine.parameters.on_multiply(lVal, rVal);
                case "/" -> engine.parameters.on_divide(lVal, rVal);
                case "-" -> engine.parameters.on_subtract(lVal, rVal);
                case "^" -> engine.parameters.on_pow(lVal, rVal);
                case "%" -> engine.parameters.on_percent(lVal, rVal);
                default -> throw new IllegalArgumentException();
            };
                return new Number_Node(result);
            } catch (Exception e) {
                // Если ошибка (деление на 0 и т.п.), возвращаем упрощенных детей
                return new Operation_Node(binNode.operation, left, right);
            }
        }

        // 4. Возвращаем новый узел с уже упрощенными детьми
        // Даже если мы не свернули в число, дети могли стать проще (например, из дерева в число)
        return new Operation_Node(binNode.operation, left, right);
    }

    return node;
     }

}