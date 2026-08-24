package JMathEngine;

import java.math.BigDecimal;

public interface Number {
    @Override
    String toString();

    Integer toInteger();

    Double toDouble();

    Long toLong();

    

    Boolean toBoolean();

    BigDecimal toBigDecimal();

    Number add(Number other);

    Number subtract(Number other);

    Number multiply(Number other);

    Number divide(Number other);

    Number pow(Number other);
}