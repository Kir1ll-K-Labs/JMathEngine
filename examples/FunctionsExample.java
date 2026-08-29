package examples;

import java.math.BigDecimal;
import java.util.Scanner;

import JMathEngine.MathEngine;
import JMathEngine.NotRational;
import JMathEngine.Number;

import java.util.Scanner;

public class FunctionsExample {
    
    public static void main(String[] args) {
        // 1️⃣ Создаём движок
        MathEngine engine = new MathEngine();
        
        // 2️⃣ Добавляем функцию "min" (поиск минимума)
        engine.parameters.add_fun("min", (Number[] list) -> {
            if (list.length == 0) {
                throw new RuntimeException("min не может быть пустым");
            }
            Number current = list[0];
            for (int i = 1; i < list.length; i++) {
                if (list[i].toBigDecimal().compareTo(current.toBigDecimal()) < 0) {
                    current = list[i];
                }
            }
            return current;
        });
        
        // 3️⃣ Добавляем функцию "max" (поиск максимума)
        engine.parameters.add_fun("max", (Number[] list) -> {
            if (list.length == 0) {
                throw new RuntimeException("max не может быть пустым");
            }
            Number current = list[0];
            for (int i = 1; i < list.length; i++) {
                if (list[i].toBigDecimal().compareTo(current.toBigDecimal()) > 0) {
                    current = list[i];
                }
            }
            return current;
        });
        
        // 4️⃣ Добавляем функцию "avg" (среднее арифметическое)
        engine.parameters.add_fun("avg", (Number[] list) -> {
            if (list.length == 0) {
                throw new RuntimeException("avg не может быть пустым");
            }
            Number sum = new NotRational(BigDecimal.ZERO);
            for (Number n : list) {
                sum = sum.add(n);
            }
            return sum.divide(new NotRational(list.length));
        });
        
        // 5️⃣ Добавляем функцию "sum" (сумма)
        engine.parameters.add_fun("sum", (Number[] list) -> {
            Number sum = new NotRational(0);
            for (Number n : list) {
                sum = sum.add(n);
            }
            return sum;
        });
        
        // 6️⃣ Интерактивный режим
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (или 'exit' для выхода):");
        System.out.println("Примеры:");
        System.out.println("  min(2+2, 5, 2^3)  -> " + engine.evaluate("min(2+2, 5, 2^3)"));
        System.out.println("  max(1, 5, 3)      -> " + engine.evaluate("max(1, 5, 3)"));
        System.out.println("  avg(1, 2, 3, 4)   -> " + engine.evaluate("avg(1, 2, 3, 4)"));
        System.out.println("  sum(1, 2, 3, 4)   -> " + engine.evaluate("sum(1, 2, 3, 4)"));
        System.out.println("---");
        
        while (true) {
            System.out.print("> ");
            String expression = scanner.nextLine();
            
            if (expression.equalsIgnoreCase("exit")) {
                break;
            }
            
            try {
                Number result = engine.evaluate(expression);
                System.out.println("" + expression + " = " + result);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        
        scanner.close();
        System.out.println("До свидания!");
    }
}