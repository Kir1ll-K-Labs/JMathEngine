package JMathEngine;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import JMathEngine.Nodes.BreaketNode;
import JMathEngine.Nodes.OperationNode;
import JMathEngine.Nodes.TreeNode;

/* JADX INFO: loaded from: classes5.dex */
public class Utils {
    private static BigInteger lcm(BigInteger a, BigInteger b) {
        return a.divide(a.gcd(b)).multiply(b);
    }

    public static BigDecimal[] normalize_rational(BigDecimal numerator, BigDecimal denominator) {
        BigDecimal bb;
        int a = numerator.scale();
        int b = denominator.scale();
        int a2 = Math.max(a, b);
        BigDecimal c = new BigDecimal(1);
        for (int i = 0; i < a2; i++) {
            c = c.multiply(new BigDecimal(10));
        }
        BigDecimal ab = numerator.multiply(c);
        if (denominator.compareTo(new BigDecimal(denominator.toBigInteger())) == 0) {
            bb = c;
        } else {
            bb = denominator.multiply(c);
        }
        BigInteger lcm_result = lcm(ab.toBigInteger(), bb.toBigInteger());
        BigInteger max_big_a = lcm_result.divide(ab.toBigInteger());
        BigInteger max_big_b = lcm_result.divide(bb.toBigInteger());
        if (max_big_a.compareTo(max_big_b) > 0) {
            max_big_b = max_big_a;
        }
        BigDecimal new_numerator = numerator.multiply(new BigDecimal(max_big_b).multiply(c));
        BigDecimal new_denominator = denominator.multiply(new BigDecimal(max_big_b).multiply(c));
        BigInteger gcd = new_numerator.toBigInteger().gcd(new_denominator.toBigInteger());
        return new BigDecimal[]{new_numerator.divide(new BigDecimal(gcd)).stripTrailingZeros(), new_denominator.divide(new BigDecimal(gcd)).stripTrailingZeros()};
    }
    private static  class IndexData {
        private Integer index;
        IndexData(Integer index){
            this.index=index;
        }
        public Integer get(){
            return this.index;
        }

        public void minus(){
            this.index-=1;
        }
    }

    private static Integer get_priority(String operator){
        if (BasicSeparator.is_equal_one(operator, new String[]{"+","-"})){
            return 1;
        }
        if (BasicSeparator.is_equal_one(operator, new String[]{"*","/"})){
            return 2;
        }
        throw new RuntimeException();
    }

    private  static void make_breakets_node(TreeNode node){
        if (node instanceof OperationNode){
            OperationNode on = (OperationNode) node;
            make_breakets_node(node, get_priority(on.operators.get(0)));
        }
    }
    private  static void make_breakets_node(TreeNode node,Integer parent_priority){
        if (node instanceof OperationNode){
            OperationNode on = (OperationNode) node;

            for (int i = 0;i<on.childs.size();i++){
                TreeNode tn = on.childs.get(i);
                if (tn instanceof OperationNode){
                    OperationNode oninner = (OperationNode) tn;
                    Integer inner_priority = get_priority(oninner.operators.get(0));
                    make_breakets_node(oninner, inner_priority);
                    if (inner_priority<parent_priority){
                        on.childs.set(i,new BreaketNode(oninner));
                    }
                }
            }
        }
    }

     protected static TreeNode parse_postfix(ArrayList<Object> list){
        Integer index = list.size()-1;
        TreeNode node = parse_postfix(list, new IndexData(index));
        make_breakets_node(node);
        return node;
     }

    protected static TreeNode parse_postfix(ArrayList<Object> list,IndexData index){
        if (index.get()<0){
            throw new RuntimeException();
        }
        Object cur = list.get(index.get());
        index.minus();
        if (cur instanceof String){
            String cur_str = (String) cur;
            ArrayList<String> operators = new ArrayList<>(){{
                add(cur_str);
            }};
            TreeNode one = parse_postfix(list, index);
            
            TreeNode two = parse_postfix(list, index);

            ArrayList<TreeNode> childs = new ArrayList<>(){{
                add(two);
                add(one);
            }};
            OperationNode on = new OperationNode(operators, childs);
            return on;
        }
        if (cur instanceof TreeNode){
            TreeNode tn = (TreeNode) cur;
            return tn;
        }
        throw new RuntimeException();
    }
}
