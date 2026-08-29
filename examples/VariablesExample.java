package examples;

import java.util.Random;
import java.util.Scanner;

import JMathEngine.MathEngine;
import JMathEngine.NotRational;
import JMathEngine.Number;
import JMathEngine.Rational;

public class VariablesExample {
    public static void main(String[] args) {
        MathEngine engine = new MathEngine();
        Scanner scanner = new Scanner(System.in);
        
        // ============================================================
        // 1️⃣ СТАТИЧЕСКАЯ ПЕРЕМЕННАЯ (рациональное число)
        // ============================================================
        // x = 1/3
        engine.parameters.add_var("x", new Rational("1", "3"));
        System.out.println("x = 1/3");
        
        // ============================================================
        // 2️⃣ ДИНАМИЧЕСКАЯ ПЕРЕМЕННАЯ (случайное число)
        // ============================================================
        // randomint — при каждом обращении даёт новое случайное число
        engine.parameters.add_var("randomint", (Number none) -> {
            // Безопасный способ: nextInt() без аргументов даёт int от -2^31 до 2^31-1
            int value = new Random().nextInt();
            return new NotRational(value);
        });
        System.out.println("randomint = случайное целое число (при каждом обращении)");
        
        // ============================================================
        // 3️⃣ ДИНАМИЧЕСКАЯ ПЕРЕМЕННАЯ (время)
        // ============================================================
        engine.parameters.add_var("now", (Number none) -> {
            return new NotRational(System.currentTimeMillis());
        });
        System.out.println("now = текущее время в миллисекундах");
        
        System.out.println("\n Введите выражение (или 'exit' для выхода):");
        System.out.println("Примеры:");
        System.out.println("  x * 3           -> " + engine.evaluate("x * 3"));
        System.out.println("  x + 1/3         -> " + engine.evaluate("x + 1/3"));
        System.out.println("  randomint       -> " + engine.evaluate("randomint"));
        System.out.println("  randomint % 100 -> " + engine.evaluate("randomint % 100"));
        System.out.println("  now             -> " + engine.evaluate("now"));
        System.out.println("---");
        
        while (true) {
            System.out.print("> ");
            String expression = scanner.nextLine();
            
            if (expression.equalsIgnoreCase("exit")) {
                break;
            }
            
            try {
                Number result = engine.evaluate(expression);
                System.out.println(expression + " = " + result);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        
        scanner.close();
        System.out.println("👋 До свидания!");
    }
}