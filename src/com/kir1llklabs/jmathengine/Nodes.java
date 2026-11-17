package jmathengine;

import jmathengine.exceptions.VarNotFoundException;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes5.dex */
public class Nodes {
    
    public interface TreeNode {
        Number eval(TreeCreator.EvalParameters evalParameters);
    }

    public static class NumberNode implements TreeNode {
        public Number number;

        NumberNode(Number number) {
            this.number = number;
        }

        @Override // JMathEngine.Nodes.TreeNode
        public Number eval(TreeCreator.EvalParameters parameters) {
            return this.number;
        }

        public String toString() {
            return this.number.toString();
        }
    }

    public static class FunctionNode implements TreeNode {
        public ArrayList<TreeNode> childs;
        public String name;

        public FunctionNode(String name, ArrayList<TreeNode> childs) {
            this.name = name;
            this.childs = childs;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder(this.name);
            builder.append("(");
            if (this.childs.size() > 0) {
                for (int i = 0; i < this.childs.size() - 1; i++) {
                    builder.append(this.childs.get(i).toString());
                    builder.append(",");
                }
                builder.append(this.childs.get(this.childs.size() - 1).toString());
            }
            builder.append(")");
            return builder.toString();
        }

        @Override // JMathEngine.Nodes.TreeNode
        public Number eval(TreeCreator.EvalParameters parameters) {
            Number resp;
            Number resp2;
            ArrayList<Number> spi = new ArrayList<>();
            for (TreeNode node : this.childs) {
                spi.add(node.eval(parameters));
            }
            if (parameters.formula != null && (resp2 = parameters.formula.call_function(this.name, spi)) != null) {
                return resp2;
            }
            if (parameters.engine != null && (resp = parameters.engine.call_function(this.name, spi)) != null) {
                return resp;
            }
            Number resp3 = MathEngine.call_global_fun(this.name, spi);
            if (resp3 != null) {
                return resp3;
            }
            throw new RuntimeException("Ненайдена функция " + this.name);
        }
    }

    public static class BreaketNode implements TreeNode {
        public TreeNode innerNode;

        BreaketNode(TreeNode innerNode) {
            this.innerNode = innerNode;
        }

        @Override // JMathEngine.Nodes.TreeNode
        public Number eval(TreeCreator.EvalParameters parameters) {
            return this.innerNode.eval(parameters);
        }

        public String toString() {
            return "(" + this.innerNode.toString() + ")";
        }
    }

    public static class OperationNode implements TreeNode {
        public ArrayList<TreeNode> childs;
        public ArrayList<String> operators;

        OperationNode(ArrayList<String> operators, ArrayList<TreeNode> childs) {
            this.operators = operators;
            this.childs = childs;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder(this.childs.get(0).toString());
            for (int i = 0; i < this.operators.size(); i++) {
                builder.append(this.operators.get(i));
                builder.append(this.childs.get(i + 1).toString());
            }
            return builder.toString();
        }

        @Override // JMathEngine.Nodes.TreeNode
        public Number eval(TreeCreator.EvalParameters parameters) {
            Number cur = this.childs.get(0).eval(parameters);
            for (int i = 1; i <= this.operators.size(); i++) {
                Number next = this.childs.get(i).eval(parameters);
                String operator = this.operators.get(i - 1);
                if (operator.equals("+")) {
                    cur = parameters.engine.parameters.on_add(cur, next);
                } else if (operator.equals("*")) {
                    cur = parameters.engine.parameters.on_multiply(cur, next);
                } else if (operator.equals("/")) {
                    cur = parameters.engine.parameters.on_divide(cur, next);
                } else if (operator.equals("-")) {
                    cur = parameters.engine.parameters.on_subtract(cur, next);
                } else if (operator.equals("^")) {
                    cur = parameters.engine.parameters.on_pow(cur, next);
                } else if (operator.equals("%")) {
                    cur = parameters.engine.parameters.on_percent(cur, next);
                } else {
                    throw new IllegalArgumentException();
                }
            }
            return cur;
        }
    }

    public static class VariableNode implements TreeNode {
        public String name;

        VariableNode(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        @Override // JMathEngine.Nodes.TreeNode
        public Number eval(TreeCreator.EvalParameters parameters) {
            Number resp;
            Number resp2;
            if (parameters.formula != null && (resp2 = parameters.formula.get_var(this.name)) != null) {
                return resp2;
            }
            if (parameters.engine != null && (resp = parameters.engine.get_var(this.name)) != null) {
                return resp;
            }
            Number resp3 = MathEngine.get_global_var(this.name);
            if (resp3 != null) {
                return resp3;
            }
            throw new VarNotFoundException(this.name);
        }
    }
}
