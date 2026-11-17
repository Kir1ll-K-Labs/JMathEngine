package jmathengine;

import java.util.ArrayList;
import java.util.HashMap;

import jmathengine.Nodes.BreaketNode;
import jmathengine.Nodes.OperationNode;
import jmathengine.Nodes.TreeNode;

/* JADX INFO: loaded from: classes5.dex */
public class Formula extends VarsFunctionsParams {
    private MathEngine engine;
    private Nodes.TreeNode node;

    Formula(Nodes.TreeNode node, MathEngine engine) {
        this.node = node;
        this.engine = engine;
    }

    public Formula(String text) {
        this.engine = new MathEngine();
        TreeCreator tree = new TreeCreator(MathEngine.make_text(text));
        Nodes.TreeNode responce = tree.make_tree(this.engine);
        this.node = responce;
    }

    public void simplify() {
        TreeCreator.SimplifyParameters simplifyParameters = new TreeCreator.SimplifyParameters();
        simplifyParameters.engine = this.engine;
        simplifyParameters.total_number = new NotRational((Integer) 0);
        simplifyParameters.vars_count = new HashMap<>();
        this.node = TreeCreator.simplify(this.node, simplifyParameters);
    }

    public Number run() {
        TreeCreator.EvalParameters evalParameters = new TreeCreator.EvalParameters();
        evalParameters.engine = this.engine;
        evalParameters.formula = this;
        return TreeCreator.eval(this.node, evalParameters);
    }

    public HashMap<String, Integer> get_variables_map() {
        return TreeCreator.get_variables_map(this.node);
    }

    public Nodes.TreeNode getNode() {
        return this.node;
    }

    public void put_variables_map(HashMap<String, Number> map) {
        for (String name : map.keySet()) {
            put_var(name, map.get(name));
        }
    }

    private void toPostfixInner(ArrayList<Object> list,TreeNode node){
        if (node instanceof BreaketNode){
            BreaketNode bn = (BreaketNode) node;
            toPostfixInner(list, bn.innerNode);
            return;
        }
        if (node instanceof OperationNode){
            OperationNode on = (OperationNode) node;
            if (on.childs.size()==0){return ;}
            toPostfixInner(list, on.childs.get(0));
            for (int i = 1; i<on.childs.size();i++){
                toPostfixInner(list, on.childs.get(i));
                list.add(on.operators.get(i-1));
            }
            return ;
        }
        list.add(node.toString());
    }

    public String toPostfixString(){
        ArrayList<Object> list = new ArrayList<>();
        toPostfixInner(list, this.node);
        StringBuilder builder = new StringBuilder();
        for (Object obj:list){
            builder.append(obj.toString());
            builder.append(" ");
        }
        return builder.toString();
    }

    public String toString() {
        return TreeCreator.printNode(this.node);
    }
}
