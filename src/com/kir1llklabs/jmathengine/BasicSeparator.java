package com.kir1llklabs.jmathengine;

import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: classes5.dex */
class BasicSeparator {
    BasicSeparator() {
    }

    protected static boolean is_equal_one(String chr, String[] separators) {
        for (String sep : separators) {
            if (chr.equals(sep)) {
                return true;
            }
        }
        return false;
    }
    public static ArrayList<Object> separate_postfix(String text,MathEngine engine) {
        ArrayList<Object> spi = new ArrayList<>();
        StringBuilder builder = new StringBuilder(text);
        int left = 0;
        int right = 0;
        int breakets_count = 0;
        while (right < builder.length()) {
            String current_s = "" + builder.charAt(right);
            if (current_s.equals("(")) {
                breakets_count++;
            } else if (current_s.equals(")")) {
                breakets_count--;
            }
            if (breakets_count>0){
                right++;
                continue;
            }
            if (left==right) {
                if (is_equal_one(current_s, new String[]{"+","-","*","/","%","^"})) { 
                    
                    spi.add(current_s);
                }
            } else {
                if (current_s.equals(" ")) {
                    if (is_equal_one(builder.substring(left, right).toString(), new String[]{"+","-","*","/","%","^"})) {
                        left=right+1;
                        right++;
                        continue;
                       
                    }
                    spi.add(TreeCreator.make_tree_inner(builder.substring(left, right),engine));
                     left = right+1;
                }
            }
            right++;
        }
        
        return spi;
    }

    public static ArrayList<Nodes.TreeNode> separate(String text, String[] separators, boolean multiply,MathEngine engine) {
        ArrayList<Nodes.TreeNode> spi = new ArrayList<>();
        StringBuilder builder = new StringBuilder(text);
        int left = 0;
        int right = 0;
        int breakets_count = 0;
        while (right < builder.length()) {
            String current_s = "" + builder.charAt(right);
            if (current_s.equals("(")) {
                breakets_count++;
            } else if (current_s.equals(")")) {
                breakets_count--;
            }
            if (breakets_count > 0) {
                right++;
            } else {
                if (is_equal_one(current_s, separators)) {
                    if ((right == left) & multiply) {
                        left = right + 1;
                        right++;
                    } else {
                        spi.add(TreeCreator.make_tree_inner(builder.substring(left, right),engine));
                        left = right + 1;
                    }
                }
                right++;
            }
        }
        spi.add(TreeCreator.make_tree_inner(builder.substring(left, right),engine));
        return spi;
    }

    public static ArrayList<String> exclude_separate(String text, String[] separators) {
        String current_s;
        ArrayList<String> spi = new ArrayList<>();
        int breakets_count = 0;
        StringBuilder builder = new StringBuilder(text);
        int right = 0;
        while (right < builder.length()) {
            String current_s2 = "" + builder.charAt(right);
            if (current_s2.equals("(")) {
                breakets_count++;
            } else if (current_s2.equals(")")) {
                breakets_count--;
            }
            if (breakets_count > 0) {
                right++;
            } else {
                if (is_equal_one(current_s2, separators)) {
                    int left = right + 1;
                    int right2 = left;
                    do {
                        current_s = "" + builder.charAt(right2);
                        right2++;
                    } while (is_equal_one(current_s, separators));
                    spi.add(builder.substring(left - 1, right2 - 1));
                    right = right2 - 2;
                }
                right++;
            }
        }
        return spi;
    }
}
