package com.kir1llklabs.jmathengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

/* JADX INFO: loaded from: classes5.dex */
public class VarsFunctionsParams {
    protected HashMap<String, VarInterface> vars = new HashMap<>();
    protected HashMap<String, Function<ArrayList<Number>, Number>> functions = new HashMap<>();

    public interface VarInterface {
        Number get();
    }

    private class StaticVar implements VarInterface {
        private Number inner;

        @Override // JMathEngine.VarsFunctionsParams.VarInterface
        public Number get() {
            return this.inner;
        }

        StaticVar(Number number) {
            this.inner = number;
        }
    }

    public void put_var(String name, Number number) {
        this.vars.put(name, new StaticVar(number));
    }

    public Number get_var(String name) {
        VarInterface num = this.vars.getOrDefault(name, null);
        if (num != null) {
            return num.get();
        }
        return null;
    }

    public void del_var(String name) {
        if (this.vars.containsKey(name)) {
            this.vars.remove(name);
        }
    }

    public HashMap<String, VarInterface> get_vars() {
        return this.vars;
    }

    public void put_vars(HashMap<String, VarInterface> map) {
        this.vars = map;
    }

    public Function<ArrayList<Number>, Number> get_fun(String function_name) {
        return this.functions.getOrDefault(function_name, null);
    }

    public void put_fun(String name, Function<ArrayList<Number>, Number> function) {
        this.functions.put(name, function);
    }

    public void del_fun(String name) {
        if (this.functions.containsKey(name)) {
            this.functions.remove(name);
        }
    }

    public Number call_function(String name, ArrayList<Number> numbers_list) {
        Function<ArrayList<Number>, Number> function = this.functions.getOrDefault(name, null);
        if (function != null) {
            return function.apply(numbers_list);
        }
        return null;
    }
}
