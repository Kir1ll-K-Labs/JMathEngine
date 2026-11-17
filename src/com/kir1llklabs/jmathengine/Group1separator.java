package jmathengine;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes5.dex */
class Group1separator {
    private static String[] chrs_prev = {"*", "/", "+", "-", "^"};

    Group1separator() {
    }

    private static boolean contain_prev_char(String chr) {
        for (String ch : chrs_prev) {
            if (chr.equals(ch)) {
                return true;
            }
        }
        return false;
    }

    private static boolean is_equal_one(String chr, String[] separators) {
        for (String sep : separators) {
            if (chr.equals(sep)) {
                return true;
            }
        }
        return false;
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
                    if (right == left && multiply) {
                        right++;
                    } else if (current_s.equals("-") && contain_prev_char("" + builder.charAt(right - 1))) {
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

    public static ArrayList<String> separate_operators(String text) {
        ArrayList<String> spi = new ArrayList<>();
        int count_breakets = 0;
        for (int i = 0; i < text.length(); i++) {
            Character cur_char = Character.valueOf(text.charAt(i));
            if (cur_char.equals('(')) {
                count_breakets++;
            } else if (cur_char.equals(')')) {
                count_breakets--;
            }
            if (count_breakets <= 0) {
                if (cur_char.charValue() == '+') {
                    spi.add("+");
                } else if (cur_char.charValue() == '-' && i != 0 && !contain_prev_char("" + text.charAt(i - 1))) {
                    spi.add("-");
                }
            }
        }
        return spi;
    }
}
