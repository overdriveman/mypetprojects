# SmsDriverPerformanceProfiler

## Задача

* спроектировать и написать сервис, который разбирает и преобразует одну строку в объект с NNNN K O (как отдельные поля)
* постараться учесть весь уже полученный опыт и сделать максимально качетсвенно
* задача многоэтапная и новые датали будут появляться по мере реализации проекта :)

## Справка по API

Необходимо создать объекты InputParser и ParsingResult. InputParser получает на вход 
строку, которую необходимо отработать. Если строка валидная - в ParseResult будет 
готовый объект Message. Узнать результат обработки можно методом isValid() объекта ParseResult.
Код ошибки и человеко-читаемую ошибку можно получить методами getErrorCode(), getErrorDescription().

## Запуск

```shell
mvn clean package
cd target
java -jar SmsProfiler.jar
```

## Используемые технологии

* [Maven](https://maven.apache.org/guides/index.html)
* [Junit5](https://junit.org/junit5/docs/current/user-guide/)
* [slf4j](https://www.slf4j.org/docs.html)
* [logback](https://logback.qos.ch/documentation.html)
