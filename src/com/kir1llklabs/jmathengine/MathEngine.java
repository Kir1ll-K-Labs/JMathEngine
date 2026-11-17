package com.kir1llklabs.jmathengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import com.kir1llklabs.jmathengine.Nodes.TreeNode;

/* JADX INFO: loaded from: classes5.dex */
public class MathEngine extends VarsFunctionsParams {
    public MathEngineParameters parameters;
    private static HashMap<String, Number> global_vars = new HashMap<>();
    private static HashMap<String, Function<ArrayList<Number>, Number>> global_functions = new HashMap<>();
    private static ArrayList<Asset> assets_list = new ArrayList<>();

    public MathEngine() {
        this.parameters = new MathEngineParameters();
    }

    public MathEngine(MathEngineParameters parameters) {
        this.parameters = parameters;
    }

    public static void put_global_var(String name, Number number) {
        global_vars.put(name, number);
    }

    public static Number get_global_var(String var_name) {
        Number num = global_vars.getOrDefault(var_name, null);
        if (num != null) {
            return num;
        }
        for (Asset asset : assets_list) {
            Number num2 = asset.get_var(var_name);
            if (num2 != null) {
                return num2;
            }
        }
        return null;
    }

    public static void put_global_fun(String name, Function<ArrayList<Number>, Number> function) {
        global_functions.put(name, function);
    }

    public static void del_global_fun(String name) {
        if (global_functions.containsKey(name)) {
            global_functions.remove(name);
        }
    }

    public static Number call_global_fun(String name, ArrayList<Number> function_content) {
        Function<ArrayList<Number>, Number> function = global_functions.getOrDefault(name, null);
        if (function != null) {
            return function.apply(function_content);
        }
        for (Asset asset : assets_list) {
            Number num = asset.get_fun(name, function_content);
            if (num != null) {
                return num;
            }
        }
        return null;
    }

    static Boolean has_var_assets(String var_name) {
        for (Asset asset : assets_list) {
            if (asset.has_var(var_name).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    static Number on_add_asset(Number a, Number b) {
        Number num = null;
        for (Asset asset : assets_list) {
            num = asset.on_add(a, b);
            if (num != null) {
                return num;
            }
        }
        return num;
    }

    static Number on_subtract_asset(Number a, Number b) {
        Number num = null;
        for (Asset asset : assets_list) {
            num = asset.on_subtract(a, b);
            if (num != null) {
                return num;
            }
        }
        return num;
    }

    static Number on_multiply_asset(Number a, Number b) {
        Number num = null;
        for (Asset asset : assets_list) {
            num = asset.on_multiply(a, b);
            if (num != null) {
                return num;
            }
        }
        return num;
    }

    static Number on_divide_asset(Number a, Number b) {
        Number num = null;
        for (Asset asset : assets_list) {
            num = asset.on_divide(a, b);
            if (num != null) {
                return num;
            }
        }
        return num;
    }

    public static Number class_run(String text) {
        return class_run(text, new MathEngine());
    }

    public static Number class_run(String text, MathEngine engine) {
        TreeCreator tree = new TreeCreator(make_text(text));
        Nodes.TreeNode responce = tree.make_tree(engine);
        TreeCreator.EvalParameters evalParameters = new TreeCreator.EvalParameters();
        evalParameters.engine = engine;
        return TreeCreator.eval(responce, evalParameters);
    }

    public Number run(String text) {
        return class_run(text, this);
    }

    static Number on_pow_asset(Number a, Number b) {
        Number num = null;
        for (Asset asset : assets_list) {
            num = asset.on_pow(a, b);
            if (num != null) {
                return num;
            }
        }
        return num;
    }

    static Number on_percent_asset(Number a, Number b) {
        Number num = null;
        for (Asset asset : assets_list) {
            num = asset.on_percent(a, b);
            if (num != null) {
                return num;
            }
        }
        return num;
    }

    public static void addAsset(Asset asset) {
        if (assets_list.contains(asset)) {
            return;
        }
        assets_list.add(asset);
    }

    public static void delAsset(Asset asset) {
    }

    private static void replace_all_in_text(StringBuilder builder) {
        boolean is_negative;
        int i = 0;
        while (i < builder.length()) {
            if ((builder.charAt(i) == '-') | (builder.charAt(i) == '+')) {
                int j = i;
                if (builder.charAt(j) == '-') {
                    is_negative = true;
                } else {
                    is_negative = false;
                }
                while (true) {
                    j++;
                    if (j >= builder.length()) {
                        throw new RuntimeException("Некорректное выражение");
                    }
                    if (builder.charAt(j) == '-') {
                        is_negative = !is_negative;
                    } else if (builder.charAt(j) != '+') {
                        if (is_negative) {
                            builder.replace(i, j, "-");
                        } else {
                            builder.replace(i, j, "+");
                        }
                        i = j;
                        break;
                    }
                }
            } else {
                i++;
            }
        }
    }

    private static void delete_spaces(StringBuilder builder) {
        int i = 0;
        while (i<builder.length()){
             if (builder.charAt(i) == ' ') {
                builder.deleteCharAt(i);
                continue;
            }
            i++;
        }
    }

    static String make_text(String text) {
        StringBuilder builder = new StringBuilder(text);
        if (builder.length() == 0) {
            throw new RuntimeException("Пустая строка");
        }
        delete_spaces(builder);
        replace_all_in_text(builder);
        if (builder.charAt(0) == '+') {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    public Formula evaluate(String text) {
        if (BasicSeparator.is_equal_one(text.substring(text.length()-1, text.length()), new String[]{"+","*","/","%","^","-"})){
            return evaluatePostfix(text);
        }
        return evaluate_inner(make_text(text));
    }

    public Formula evaluatePostfix(String text){
        ArrayList<Object> main_list = BasicSeparator.separate_postfix(text,this);
        TreeNode node = Utils.parse_postfix(main_list);
        
        Formula f = new Formula(node,new MathEngine());
        return f;
    }

    Formula evaluate_inner(String text) {
        TreeCreator tree = new TreeCreator(text);
        Nodes.TreeNode responce = tree.make_tree(this);
        return new Formula(responce, this);
    }
}
