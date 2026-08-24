# JMathEngine:

**Кастомизируемый математический движок для точных вычислений на Java.**

JMathEngine — это не просто калькулятор. Это библиотека, которая позволяет парсить и вычислять математические выражения с **абсолютной точностью** (благодаря `BigDecimal` и `Rational`). Поддерживает переменные, пользовательские функции и гибкую настройку через хуки.

---

## 🚀 Возможности

### ⚙️ Пользовательские функции

Вы можете добавлять свои функции в движок.

```java
engine.parameters.add_fun("min", (Number[] list) -> {
    Number current = list[0];
    for (Number n : list) {
        if (n.toBigDecimal().compareTo(current.toBigDecimal()) < 0) {
            current = n;
        }
    }
    return current;
});

// Использование:
engine.evaluate("min(10, 20, 5, 15)"); // 5
```

### 🔁 Переменные (Variables)

Переменные позволяют сохранять значения и использовать их в выражениях.

```java
// Простые переменные
engine.setVar("x", "5");
engine.setVar("y", "10");
engine.evaluate("x + y"); // 15


// Динамические переменные
engine.parameters.add_var("randomint", (none) -> {
    return new NotRational(new Random().nextInt());
});
```

### 🪝 Хуки (Hooks)

Хуки позволяют **перехватывать** операции в реальном времени. Это полезно для логирования, валидации, кэширования и отладки.

```java
class MyParams extends MathEngineParameters {
    @Override
    public Number on_add(Number a, Number b) {
        Number result = super.on_add(a, b);
        System.out.println("➕ " + a + " + " + b + " = " + result);
        return result;
    }
}

MathEngine engine = new MathEngine(new MyParams());
engine.evaluate("2 + 3 * 4");
// Вывод:
// ✖️ 3 * 4 = 12
// ➕ 2 + 12 = 14
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
## 📋Планы
- [x] Парсинг выражений
- [x] Пользовательские функции.
- [x] Пользовательские переменные.
- [x] Хуки. 
- [ ] Добавить поддержку sqrt().
---

**⭐ Поставьте звезду на GitHub, если проект полезен!**
