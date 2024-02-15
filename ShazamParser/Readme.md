# ShazamParser

TODO: опция -p не будет нормально работать с датой, если трек встречался ранее (и покрыть тестами)

У мобильного приложения shazam есть возможность выгружать историю поиска музыкальных композиций в виде текстового
файла в формате csv.
Я решил совместить в учебном проекте работу с разными форматами данных, такими как json, xml и csv и практику
создания консольных утилит с помощью библиотеки apache commons cli.
Моё приложение получает на вход файл выгрузки из shazam и преобразует его в удобный для человека формат и\или
преобразует в текстовый\json\xml файл.
В тестовых файлах есть достаточно объемные примеры настоящих выгрузок из приложения (см. `src/test/resources`).

## Что я использовал

* Возможности core java
  + Регулярные выражения для задания формата строки, формата даты и проверки формата файла
  + Optional для потенциально не инициализированных переменных
  + Stream Api для удобной фильтрации и обработки списков
  + Date/Time API для фильтрации по дате
  + Структурирование приложения с использованием логического разделения классов по пакетам
* Шаблоны проектирования и архитектурные решения
  + Шаблон builder
  + Шаблон service locator
  + Принципы SOLID при проектировании классов
  + Покрытие кода модульными тестами и конфигурация автоматического запуска тестов перед каждой сборкой
* Maven
  + Использую для автоматической сборки приложения
  + Сборка в fatjar (единый исполняемый jar файл, который содержит в себе все зависимости) assembly плагином
  + Запуск модульных тестов с помощью surefire плагина
  + Генерация кода в классах с аннотациями lombok
  + Проект использует Java 17 (задается property <maven.compiler.release>17</...> в pom.xml)
* Lombok
  + Для авто генерации шаблонного кода
  + Использовались аннотации: @Getter @Setter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode @UtilityClass @Slf4j
* OpenCsv
  + Для работы с данными в формате csv
  + Преобразую дату в LocalDate
  + Использую для считывания данных из выгрузки
* JAXB
  + Для работы с данными в формате xml
  + Использую для экспорта данных
* Jackson
  + Для работы с данными в формате json
  + Использую для экспорта данных
* Apache Commons CLI
  + Использую для удобного разбора аргументов передаваемых в приложение
  + Удобная работа с ключами (опциональные ключи, ключ с обязательным параметром, короткий и длинный формат ключей (-h и --help) и т.п.)
  + Полуавтоматическое формирование справки (-h или --help)
* Slf4j + Logback
  + Для журналирования всех этапов работы программы
  + Позволяет удобно настроить уровни журналирования (info, warn, error) и варианты выводы (файл или консоль)
  + Использую placehodler'ы для добавления деталей в сообщение
* Junit5
  + Использую для модульного тестирования кода
  + Написано 44 теста для 5 классов
* Mockito
  + Для создания мок объектов для тестов
  + Тестирование private методов

## Как собрать и запускать

```shell
mvn clean package
cd target
java -jar ShazamConv.jar -h
```

## Примеры данных

Пример файла из шазама:
```
Shazam Library
Index,TagTime,Title,Artist,URL,TrackKey
1,2023-05-25,"Tu Juguete","Dani J",https://www.shazam.com/track/612980416/tu-juguete,612980416
2,2023-05-25,"Drive By","Train",https://www.shazam.com/track/55489799/drive-by,55489799
3,2023-05-23,"Say Say Say (feat. Eve St. Jones)","48th St. Collective",https://www.shazam.com/track/444770287/say-say-say-feat-eve-st-jones,444770287
...
```

Вывод в консоль (с форматированием и датой):
```
Artist                   Track             Date
Chris Cornell            You Know My Name  20.05.2023
...
```
Вывод в консоль (без форматирования по умолчанию):
```
Chris Cornell - You Know My Name
...
```
Вывод в файл xml
```xml
<library>
	<entry>
	    <artist>
			Chris Cornell
    	</artist>
	    <track>
			You Know My Name
    	</track>
	    <date>
			20.05.2023
    	</date>
	</entry>
	<entry>
		<!-- ... some record -->
	</entry>
	<!-- ... more records -->
</library>
```
Вывод в файл json:
```json
{ "record" : [{ "artist":"Chris Cornell", "track":"You Know My Name", "date":"20.05.2023" }, { ... }] }
```

## Описание ключей
```
 -a,--sort-by-artist                   sort output by title artist
 -d,--date                             add date to console output
 -f,--format                           format the output
 -h,--help                             print help message
 -j,--json <json_file>                 write output to json formatted file
 -n,--sort-by-title                    sort output by title name
 -p,--filter-date-after <filterDate>   process only tracks added after
                                       desired date
 -s,--sort-by-date                     sort output by addition date to
                                       library
 -t,--target <csv_file>                write output to file
 -x,--xml <xml_file>                   write output to xml formatted file
```
## Примечания

1. Используемые regex для валидации даты в опции -p проверяют что число, обозначающее день месяца, находится в 
промежутке от 1 до 31. Дата с 31 февраля, например 31.02.2024 пройдет валидацию. Фильтрация по такой дате приложением
не будет произведена: будет выведено сообщение об ошибке.
  