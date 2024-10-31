package string;

import jdk.jfr.internal.StringPool;

import java.io.Serializable;

public class StrPool {

    /**
     * Строки в Java сделаны иммутабельными по нескольким причинам:
     * Безопасность: Иммутабельные строки обеспечивают безопасность в многопоточной среде. Поскольку строки не могут быть изменены, несколько потоков могут безопасно использовать одну и ту же строку без необходимости синхронизации.
     * Производительность: Иммутабельность позволяет оптимизировать использование памяти. Java может повторно использовать одни и те же объекты строк вместо создания новых. Например, строки, используемые в пуле строк, могут быть переиспользованы, что снижает накладные расходы на создание объектов.
     *
     * String Pool в Java — это специальная область памяти, которая используется для хранения строковых литералов. Когда вы создаете строку, например, с помощью литерала, Java сначала проверяет, существует ли такая строка уже в пуле. Если она существует, то возвращается ссылка на уже созданный объект; если нет — создается новый объект и добавляется в пул.
     *
     * Проблемы, которые решает String Pool:
     * Экономия памяти: Вместо создания нескольких экземпляров одной и той же строки, Java использует один объект, что снижает потребление памяти.
     * Ускорение доступа: Так как одинаковые строки не создаются заново, доступ к уже существующим строкам становится быстрее.
     * Сравнение строк: Сравнение строк с помощью == (сравнение ссылок) становится более эффективным, так как можно сравнивать ссылки на один и тот же объект, а не содержимое.
     *
     * */
    public static void main(String[] args) {
        // Создание строковых литералов
        String str1 = "Hello";
        String str2 = "Hello";

        // Сравнение ссылок
        System.out.println("str1 == str2: " + (str1 == str2)); // true

        // Создание строки с помощью оператора new
        String str3 = new String("Hello");

        // Сравнение ссылок
        System.out.println("str1 == str3: " + (str1 == str3)); // false

        // Использование метода intern()
        String str4 = str3.intern();

        // Сравнение ссылок
        System.out.println("str1 == str4: " + (str1 == str4)); // true

        new Test11().func(new Serializable() {

        });
    }


}

class Test11{
    void func(Serializable s){

    }
}