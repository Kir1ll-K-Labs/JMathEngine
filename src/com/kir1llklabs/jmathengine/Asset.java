package com.kir1llklabs.jmathengine;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes5.dex */
public interface Asset {
    default Number get_var(String variable_name) {
        return null;
    }

    default Boolean has_var(String variable_name) {
        return false;
    }

    default Boolean has_fun(String function_name) {
        return false;
    }

    default Number get_fun(String function_name, ArrayList<Number> function_content) {
        return null;
    }

    default Number on_add(Number a, Number b) {
        return null;
    }

    default Number on_multiply(Number a, Number b) {
        return null;
    }

    default Number on_divide(Number a, Number b) {
        return null;
    }

    default Number on_subtract(Number a, Number b) {
        return null;
    }

    default Number on_percent(Number a, Number b) {
        return null;
    }

    default Number on_pow(Number a, Number b) {
        return null;
    }
}
