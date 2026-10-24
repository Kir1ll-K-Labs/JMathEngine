package JMathEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes5.dex */
public class TreeCreator {
    private String text_inner;
    private static String[] chrs_prev = {"*", "/", "+", "%", "^"};
    private static final String[] chrs_group0 = {">", "<", "!", "="};
    private static final String[] chrs_group2 = {"*", "/"};
    private static final String[] chrs_group3 = {"%"};
    private static final String[] chrs_group4 = {"^"};
    private static final String[] chrs_group5 = {","};

    static class EvalParameters {
        public MathEngine engine;
        public Formula formula;

        EvalParameters() {
        }
    }

    static class SimplifyParameters {
        public MathEngine engine;
        public Nodes.TreeNode node;
        public Number total_number;
        public HashMap<String, Integer> vars_count = new HashMap<>();

        SimplifyParameters() {
        }
    }

    TreeCreator(String text) {
        this.text_inner = text;
    }

    public Nodes.TreeNode make_tree() {
        return make_tree_inner(this.text_inner);
    }

    public static Nodes.TreeNode simplify(Nodes.TreeNode node, SimplifyParameters simplifyParameters) {
        Nodes.TreeNode node_new = simplify_inner(node, simplifyParameters);
        if (node_new instanceof Nodes.BreaketNode) {
            Nodes.BreaketNode bn = (Nodes.BreaketNode) node_new;
            return bn.innerNode;
        }
        return node_new;
    }

    private static Nodes.TreeNode simplify_inner(Nodes.TreeNode node, SimplifyParameters simplifyParameters) {
        if (node instanceof Nodes.BreaketNode) {
            Nodes.BreaketNode bn = (Nodes.BreaketNode) node;
            Nodes.TreeNode new_node = simplify_inner(bn.innerNode, simplifyParameters);
            if ((new_node instanceof Nodes.VariableNode) || (new_node instanceof Nodes.NumberNode)) {
                return new_node;
            }
        }
        if (node instanceof Nodes.OperationNode) {
            Nodes.OperationNode on = (Nodes.OperationNode) node;
            for (int i = 0; i < on.childs.size(); i++) {
                on.childs.set(i, simplify_inner(on.childs.get(i), simplifyParameters));
            }
            int i2 = 1;
            for (Nodes.TreeNode inner_node : on.childs) {
                if (!(inner_node instanceof Nodes.NumberNode)) {
                    i2 = 0;
                    break;
                }
            }
            if (i2 != 0) {
                Nodes.NumberNode cur_node = (Nodes.NumberNode) on.childs.get(0);
                Number cur = cur_node.number;
                for (int i3 = 1; i3 <= on.operators.size(); i3++) {
                    Nodes.NumberNode next_node = (Nodes.NumberNode) on.childs.get(i3);
                    Number next = next_node.number;
                    if (on.operators.get(i3 - 1).equals("+")) {
                        cur = simplifyParameters.engine.parameters.on_add(cur, next);
                    } else if (on.operators.get(i3 - 1).equals("*")) {
                        cur = simplifyParameters.engine.parameters.on_multiply(cur, next);
                    } else if (on.operators.get(i3 - 1).equals("/")) {
                        cur = simplifyParameters.engine.parameters.on_divide(cur, next);
                    } else if (on.operators.get(i3 - 1).equals("-")) {
                        cur = simplifyParameters.engine.parameters.on_subtract(cur, next);
                    } else if (on.operators.get(i3 - 1).equals("^")) {
                        cur = simplifyParameters.engine.parameters.on_pow(cur, next);
                    } else if (on.operators.get(i3 - 1).equals("%")) {
                        cur = simplifyParameters.engine.parameters.on_percent(cur, next);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
                return new Nodes.NumberNode(cur);
            }
        }
        return node;
    }

    public static Number eval(Nodes.TreeNode node, EvalParameters parameters) {
        return node.eval(parameters);
    }

    public static HashMap<String, Integer> get_variables_map(Nodes.TreeNode node) {
        HashMap<String, Integer> map = new HashMap<>();
        return get_variables_map(node, map);
    }

    public static HashMap<String, Integer> get_variables_map(Nodes.TreeNode node, HashMap<String, Integer> map) {
        if (node instanceof Nodes.VariableNode) {
            Nodes.VariableNode vn = (Nodes.VariableNode) node;
            map.put(vn.name, Integer.valueOf(map.getOrDefault(vn.name, 0).intValue() + 1));
        } else {
            if (node instanceof Nodes.BreaketNode) {
                Nodes.BreaketNode bn = (Nodes.BreaketNode) node;
                return get_variables_map(bn.innerNode, map);
            }
            if (node instanceof Nodes.OperationNode) {
                Nodes.OperationNode on = (Nodes.OperationNode) node;
                for (Nodes.TreeNode tnode : on.childs) {
                    get_variables_map(tnode, map);
                }
            }
        }
        return map;
    }

    public static String printNode(Nodes.TreeNode node) {
        return node.toString();
    }

    static Nodes.TreeNode make_tree_inner(String text) {
        if (text.charAt(0) == '+') {
            text = text.substring(1);
        }
        StringBuilder builder = new StringBuilder(text);
        int open_breeaket_index = builder.indexOf("(");
        if (open_breeaket_index != -1) {
            int breakets_count = 1;
            for (int close_breaket_index = open_breeaket_index + 1; close_breaket_index < builder.length(); close_breaket_index++) {
                Character chr = Character.valueOf(builder.charAt(close_breaket_index));
                if (chr.charValue() == '(') {
                    breakets_count++;
                } else if (chr.charValue() == ')' && (breakets_count = breakets_count - 1) == 0) {
                    if (close_breaket_index != builder.length() - 1) {
                        break;
                    }
                    if (open_breeaket_index == 0) {
                        return new Nodes.BreaketNode(make_tree_inner(builder.substring(1, builder.length() - 1)));
                    }
                    String f_name = builder.substring(0, open_breeaket_index);
                    if (!Pattern.matches("[+,*,-,/]", f_name)) {
                        break;
                    }
                    return new Nodes.FunctionNode(f_name, BasicSeparator.separate(builder.substring(open_breeaket_index + 1, close_breaket_index), chrs_group5, true));
                }
            }
        }
        ArrayList<String> operators = BasicSeparator.exclude_separate(text, chrs_group0);
        if (operators.size() > 0) {
            ArrayList<Nodes.TreeNode> tree_nodes = BasicSeparator.separate(text, chrs_group0, true);
            return new Nodes.OperationNode(operators, tree_nodes);
        }
        ArrayList<String> operators2 = Group1separator.separate_operators(text);
        if (operators2.size() > 0) {
            ArrayList<Nodes.TreeNode> tree_nodes2 = Group1separator.separate(text, new String[]{"+", "-"}, true);
            System.out.println(tree_nodes2);
            return new Nodes.OperationNode(operators2, tree_nodes2);
        }
        ArrayList<String> operators3 = BasicSeparator.exclude_separate(text, chrs_group2);
        if (operators3.size() > 0) {
            ArrayList<Nodes.TreeNode> tree_nodes3 = BasicSeparator.separate(text, chrs_group2, true);
            return new Nodes.OperationNode(operators3, tree_nodes3);
        }
        ArrayList<String> operators4 = BasicSeparator.exclude_separate(text, chrs_group3);
        if (operators4.size() > 0) {
            ArrayList<Nodes.TreeNode> tree_nodes4 = BasicSeparator.separate(text, chrs_group3, true);
            return new Nodes.OperationNode(operators4, tree_nodes4);
        }
        ArrayList<String> operators5 = BasicSeparator.exclude_separate(text, chrs_group4);
        if (operators5.size() > 0) {
            ArrayList<Nodes.TreeNode> tree_nodes5 = BasicSeparator.separate(text, chrs_group4, true);
            return new Nodes.OperationNode(operators5, tree_nodes5);
        }
        try {
            Double.parseDouble(text);
            return new Nodes.NumberNode(Number.valueOf(text));
        } catch (NumberFormatException e) {
            return new Nodes.VariableNode(text);
        }
    }
}
