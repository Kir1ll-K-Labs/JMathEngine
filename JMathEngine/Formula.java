package JMathEngine;

import java.util.HashMap;

import JMathEngine.TreeCreator.BreaketNode;
import JMathEngine.TreeCreator.EvalParameters;
import JMathEngine.TreeCreator.TreeNode;
import JMathEngine.TreeCreator.EvalParameters;

public class Formula extends VarsFunctionsParams {
    private TreeNode node;
    private MathEngine engine;

    Formula(TreeNode node,MathEngine engine){
        this.node=node;
        this.engine=engine;
    }

    public Formula(String text){
        this.engine=new MathEngine();
        text=MathEngine.make_text(text);
        TreeCreator tree = new TreeCreator(text);
        TreeNode responce = tree.make_tree();
        this.node=tree.simplify(responce, engine);

    }

    public Number run(){
        EvalParameters evalParameters= new EvalParameters();
        evalParameters.engine=this.engine;
        evalParameters.formula=this;
        return TreeCreator.eval(this.node,evalParameters);
    }

    @Override
    public String toString() {
        if (this.node instanceof BreaketNode bn){
            return TreeCreator.printNode(bn.innerNode);
        }
       return TreeCreator.printNode(this.node);
    }
}
