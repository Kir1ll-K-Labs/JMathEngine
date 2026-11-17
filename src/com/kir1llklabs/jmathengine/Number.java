package jmathengine;

import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes5.dex */
public interface Number {
    Number add(Number number);

    Number divide(Number number);

    Number multiply(Number number);

    Number percent(Number number);

    Number pow(Number number);

    Number subtract(Number number);

    default Integer toInteger(){
        throw new RuntimeException(toString() + " не может быть преобразован в Integer");
    };

    default Long toLong(){
        throw new RuntimeException(toString() + " не может быть преобразован в Long");
    };

    String toString();

    default Double toDouble() {
        throw new RuntimeException(toString() + " не может быть преобразован в Double");
    }

    default Boolean toBoolean() {
        throw new RuntimeException(toString() + " не может быть преобразован в Boolean");
    }

    default BigDecimal toBigDecimal() {
        throw new RuntimeException(toString() + " не может быть преобразован в BigDecimal");
    }

    default Number divide(int integer) {
        return divide(valueOf(integer));
    }

    default Number divide(long long_val) {
        return divide(valueOf(long_val));
    }

    default Number divide(double double_val) {
        return divide(valueOf(double_val));
    }

    default Number divide(BigDecimal bigDecimal) {
        return divide(valueOf(bigDecimal));
    }

    default Number divide(BigInteger bigInteger) {
        return divide(valueOf(bigInteger));
    }

    default Number divide(String text) {
        return divide(valueOf(text));
    }

    default Number multiply(int integer) {
        return multiply(valueOf(integer));
    }

    default Number multiply(long long_val) {
        return multiply(valueOf(long_val));
    }

    default Number multiply(double double_val) {
        return multiply(valueOf(double_val));
    }

    default Number multiply(BigDecimal bigDecimal) {
        return multiply(valueOf(bigDecimal));
    }

    default Number multiply(BigInteger bigInteger) {
        return multiply(valueOf(bigInteger));
    }

    default Number multiply(String text) {
        return multiply(valueOf(text));
    }

    default Number add(int integer) {
        return add(valueOf(integer));
    }

    default Number add(long long_val) {
        return add(valueOf(long_val));
    }

    default Number add(double double_val) {
        return add(valueOf(double_val));
    }

    default Number add(BigDecimal bigDecimal) {
        return add(valueOf(bigDecimal));
    }

    default Number add(BigInteger bigInteger) {
        return add(valueOf(bigInteger));
    }

    default Number add(String text) {
        return add(valueOf(text));
    }

    default Number subtract(int integer) {
        return subtract(valueOf(integer));
    }

    default Number subtract(long long_val) {
        return subtract(valueOf(long_val));
    }

    default Number subtract(double double_val) {
        return subtract(valueOf(double_val));
    }

    default Number subtract(BigDecimal bigDecimal) {
        return subtract(valueOf(bigDecimal));
    }

    default Number subtract(BigInteger bigInteger) {
        return subtract(valueOf(bigInteger));
    }

    default Number subtract(String text) {
        return subtract(valueOf(text));
    }

    default Number percent(int integer) {
        return percent(valueOf(integer));
    }

    default Number percent(long long_val) {
        return percent(valueOf(long_val));
    }

    default Number percent(double double_val) {
        return percent(valueOf(double_val));
    }

    default Number percent(BigDecimal bigDecimal) {
        return percent(valueOf(bigDecimal));
    }

    default Number percent(BigInteger bigInteger) {
        return percent(valueOf(bigInteger));
    }

    default Number percent(String text) {
        return percent(valueOf(text));
    }

    default Number pow(int integer) {
        return pow(valueOf(integer));
    }

    default Number pow(long long_val) {
        return pow(valueOf(long_val));
    }

    default Number pow(double double_val) {
        return pow(valueOf(double_val));
    }

    default Number pow(BigDecimal bigDecimal) {
        return pow(valueOf(bigDecimal));
    }

    default Number pow(BigInteger bigInteger) {
        return pow(valueOf(bigInteger));
    }

    default Number pow(String text) {
        return pow(valueOf(text));
    }

    default Number pow_reverse(Number other) {
        throw new RuntimeException("Возведение " + other.toString() + " в степень " + toString() + " невозможно.");
    }

    default Number percent_reverse(Number other) {
        throw new RuntimeException("% " + toString() + " от " + other.toString() + " невозможен.");
    }

    default Number divide_reverse(Number other) {
        throw new RuntimeException("Деление " + other.toString() + " на " + toString() + " невозможно.");
    }

    default Number subtract_reverse(Number other) {
        throw new RuntimeException("Вычитание " + toString() + " из " + other.toString() + " невозможно.");
    }

    default int compareTo(Number number) {
        return toBigDecimal().compareTo(number.toBigDecimal());
    }

    default String toPlainString() {
        return toString();
    }

    default String toEngineeringString() {
        return toString();
    }

    static Boolean is_arifmetic_string(String text) {
        Character[] chrs_list = {'+', '-', '*', '/', '%', '^', '=', '<', '>', '!'};
        for (char c : text.toCharArray()) {
            Character character = Character.valueOf(c);
            for (Character cur_char : chrs_list) {
                if (character == cur_char) {
                    return true;
                }
            }
        }
        return false;
    }

    static NotRational valueOf(BigDecimal bigDecimal) {
        return new NotRational(bigDecimal);
    }

    static NotRational valueOf(BigInteger bigInteger) {
        return new NotRational(bigInteger);
    }

    static Number valueOf(String text) {
        return new NotRational(text);
    }

    static NotRational valueOf(long long_val) {
        return new NotRational(Long.valueOf(long_val));
    }

    static NotRational valueOf(double double_val) {
        return new NotRational(Double.valueOf(double_val));
    }

    static NotRational valueOf(int integer) {
        return new NotRational(Integer.valueOf(integer));
    }

    static Number valueOf(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        return new NotRational(bigDecimal).divide(new NotRational(bigDecimal2));
    }

    static Number valueOf(BigInteger bigInteger, BigInteger bigInteger2) {
        return new NotRational(bigInteger).divide(new NotRational(bigInteger2));
    }

    static Number valueOf(long long_val, long long_val2) {
        return new NotRational(Long.valueOf(long_val)).divide(new NotRational(Long.valueOf(long_val2)));
    }

    static Number valueOf(double double_val, double double_val2) {
        return new NotRational(Double.valueOf(double_val)).divide(new NotRational(Double.valueOf(double_val2)));
    }

    static Number valueOf(int integer, int integer2) {
        return new NotRational(Integer.valueOf(integer)).divide(new NotRational(Integer.valueOf(integer2)));
    }

    static Boolean isNumber(String text) {
        boolean z;
        int i = 0;
        boolean z2 = true;
        Character[] chrs_list = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        boolean slashed = false;
        boolean point = false;
        boolean minus = false;
        if (text.charAt(text.length() - 1) == '.' || text.charAt(text.length() - 1) == '/' || text.charAt(0) == '/') {
            return false;
        }
        char[] charArray = text.toCharArray();
        int length = charArray.length;
        int i2 = 0;
        while (i2 < length) {
            Character character = Character.valueOf(charArray[i2]);
            if (character.charValue() == '-') {
                if (minus) {
                    return false;
                }
                minus = true;
                z = z2;
            } else if (character.charValue() == '.') {
                if (point) {
                    return false;
                }
                point = true;
                z = z2;
            } else if (character.charValue() == '/') {
                if (slashed) {
                    return false;
                }
                point = false;
                minus = false;
                slashed = true;
                z = z2;
            } else {
                boolean stop = true;
                int length2 = chrs_list.length;
                while (true) {
                    if (i >= length2) {
                        z = z2;
                        break;
                    }
                    z = z2;
                    Character other_Character = chrs_list[i];
                    if (character == other_Character) {
                        stop = false;
                        break;
                    }
                    i++;
                    z2 = z;
                }
                if (stop) {
                    return false;
                }
            }
            i2++;
            z2 = z;
            i = 0;
        }
        return Boolean.valueOf(z2);
    }
}
