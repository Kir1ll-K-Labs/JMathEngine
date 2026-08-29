package examples;
import java.util.Scanner;

import JMathEngine.MathEngine;
import JMathEngine.MathEngineParameters;
import JMathEngine.Number;
public class HooksExample {
    
    // 1️⃣ Создаём свой класс параметров, наследуя MathEngineParameters
    static class MyParameters extends MathEngineParameters {
        
        // 2️⃣ Переопределяем метод on_add — он вызывается при каждом сложении
        @Override
        public Number on_add(Number a, Number b) {
            Number result = super.on_add(a, b);
            System.out.println("" + a + " + " + b + " = " + result);
            return result;
        }
        
        // 3️⃣ Переопределяем метод on_multiply — он вызывается при каждом умножении
        @Override
        public Number on_multiply(Number a, Number b) {
            Number result = super.on_multiply(a, b);
            System.out.println("" + a + " * " + b + " = " + result);
            return result;
        }
        
        // 4️⃣ Можно добавить и другие хуки
        @Override
        public Number on_divide(Number a, Number b) {
            Number result = super.on_divide(a, b);
            System.out.println("" + a + " / " + b + " = " + result);
            return result;
        }
        
        @Override
        public Number on_subtract(Number a, Number b) {
            Number result = super.on_subtract(a, b);
            System.out.println("" + a + " - " + b + " = " + result);
            return result;
        }

        @Override
        public Number on_pow(Number a, Number b) {
            Number result = super.on_pow(a, b);
            System.out.println("" + a + " ^ " + b + " = " + result);
            return result;
        }

        @Override
        public Number on_percent(Number a, Number b) {
            Number result = super.on_percent(a, b);
            System.out.println("" + a + " % " + b + " = " + result);
            return result;
        }
    }
    
    public static void main(String[] args) {
        // 5️⃣ Создаём движок с нашими параметрами
        MathEngine engine = new MathEngine(new MyParameters());
        
        System.out.println("Введите выражение (или 'exit' для выхода):");
        System.out.println("Пример: 2 + 3 * 4");
        System.out.println("----------------------------------------");
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("> ");
            String expression = scanner.nextLine();
            
            if (expression.equalsIgnoreCase("exit")) {
                break;
            }
            
            try {
                Number result = engine.evaluate(expression);
                System.out.println("Результат: " + result);
                System.out.println("----------------------------------------");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        
        scanner.close();
        System.out.println("👋 До свидания!");
    }
}