package language

/**
 * IntelliJ компилирует код Kotlin, используя компилятор kotlinc-jvm.
 * Это означает, что IntelliJ транслирует код на Kotlin в байт-код — язык, на
 * котором «разговаривает» JVM
 *
 * Каждая платформа (например, Windows или macOS) имеет свой набор инструк-
 * ций. Виртуальная машина JVM связывает байт-код с различными программными
 * и аппаратными средствами, на которых работает JVM; она читает байт-код и вы-
 * полняет соответствующие ему машинные инструкции. Это позволяет разработ-
 * чикам на языке Kotlin только один раз написать платформенно независимый код,
 * который после компиляции в байт-код будет выполняться на разных устройствах
 * вне зависимости от операционной системы.
 *
 * Так как Kotlin может транслироваться в байт-код для JVM, он считается JVM-
 * языком. Вероятно, Java стал самым известным JVM-языком, потому что он был
 * первым. Впоследствии появились другие JVM-языки — Scala, Groovy и Kotlin,
 * которые были призваны устранить некоторые недостатки Java с точки зрения
 * разработчиков
 * */

/**
 * Динамическая типизация позволяет изменять тип переменной во время выполнения программы,
 * тогда как статическая типизация определяет тип переменной на этапе компиляции и не позволяет
 * его изменять во время выполнения.
 * todo в Первой книге котлин более глубоко разобрано это понятие. Необходимо добавить.
 * */

/**
 * == Проверяет, что значение слева равно значению справа
 * != Проверяет, что значение слева не равно значению справа
 * === Проверяет, что две ссылки указывают на один экземпляр
 * !== Проверяет, что две ссылки указывают на разные экземпляры
 * || Логическое «или»: истинно, когда хотя бы одно выражение истинно (ложно, если оба
 * ложны)
 * */