# JMathEngine:

**Кастомизируемый математический движок для точных вычислений на Java.**

JMathEngine — это не просто калькулятор. Это библиотека, которая позволяет парсить и вычислять математические выражения с **абсолютной точностью** (благодаря `BigDecimal` и `Rational`). Поддерживает переменные, пользовательские функции и гибкую настройку через хуки.

---

## 🚀 Возможности

### ⚙️ Пользовательские функции

Вы можете добавлять свои функции в движок.

```java
MathEngine engine = new MathEngine();
engine.put_fun("min", (ArrayList<Number> list) -> {
    Number current = list.get(0);
    for (Number n : list) {
        if (n.compareTo(current) <0) {
            current = n;
        }
    }
    return current;
});
Number responce = engine.run("min(2+2,5,1/3)"); // 1/3
System.err.println("Ответ: "+responce);
```

### 🔁 Переменные (Variables)

Переменные позволяют сохранять значения и использовать их в выражениях.

```java
//глобальные переменные
MathEngine.put_global_var("PI", Number.valueOf("3.14"));
MathEngine engine = new MathEngine();

//переменные движка
engine.put_var("y", Number.valueOf(2));
Formula formula = engine.evaluate("PI+y*x");

//переменные формулы
formula.put_var("x", Number.valueOf(5));
        
Number resp = formula.run();
System.out.println("Ответ: "+resp); //13.14
//приоритет поиска переменных Formula -> MathEngine (instance) -> MathEngine (class)
```

### 🪝 Хуки (Hooks)

Хуки позволяют **перехватывать** операции в реальном времени. Это полезно для логирования, валидации, кэширования и отладки.

```java
class MyParameters extends MathEngineParameters{
    @Override
    protected Number on_add(Number a, Number b) {
        Number resp = super.on_add(a, b);
        System.out.println(a.toString()+"+"+b.toString()+"="+resp.toString());
        return resp;
    }
}
MathEngine engine = new MathEngine(new MyParameters());
engine.run("2+3+4");
//Вывод
//2+3=5
//5+4=9
```
### повторные вычисления (Formula)
```java
Formula formula = new Formula("(x+2)*2*3*x");
System.out.println("Сокращенная формула "+formula); // (x+2)*6*x
//сокращение формул может давать "некрасивое" сокращение
//но я старался что бы сокращение сохраняло корректную структуру

for (int i = 1; i<=1000000;i++){
    formula.put_var("x", Number.valueOf(i));
    Number responce = formula.run(); // быстрое вычисление
}

for (int i = 1; i<=1000000;i++){
    String text = "(x+2)*2*3*x".replaceAll("x",""+i);
    Number responce = MathEngine.class_run(text);
    // так-же работает но
    // библиотека каждый раз парсит строку заново
}
```
---

## 📂 Примеры

Все примеры находятся в папке [`examples/`](examples/):

| Пример | Описание |
|--------|----------|
| [BasicOperations.java](examples/BasicOperations.java) | Базовые вычисления (арифметика, логика, дроби) |
| [HooksExample.java](examples/HooksExample.java) | Хуки для логирования операций |
| [FunctionsExample.java](examples/FunctionsExample.java) | Пользовательские функции (`min`, `max`, `avg`) |
| [VariablesExample.java](examples/VariablesExample.java) | Статические и динамические переменные |

---

## ⚠️ Текущие ограничения

- `%` (процент) — работает только для положительных чисел.
- `^` (степень) — поддерживаются только **целые** степени (дробные вызовут ошибку).

---

## 📦 Установка

### Через JAR (рекомендуется)
1. Скачай `JMathEngine.jar` из [Releases](https://github.com/Kir1ll-K-Labs/JMathEngine/releases).
2. Добавь в проект: `Build Path → Add External JAR`.

---

## 📄 Лицензия

MIT License — свободно для использования в коммерческих и личных проектах.

---

## 🤝 Вклад

Если вы нашли баг или хотите добавить новую фичу — создавайте [Issue](https://github.com/Kir1ll-K-Labs/JMathEngine/issues) или [Pull Request](https://github.com/Kir1ll-K-Labs/JMathEngine/pulls).
---

**⭐ Поставьте звезду на GitHub, если проект полезен!**
