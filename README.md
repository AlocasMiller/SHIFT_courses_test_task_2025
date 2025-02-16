#### Используемые версии
Версия Java: 23    
Версия Gradle: corretto-21

#### Инструкция по запуску:
В терминале, находясь в папке проекта, проделать следующие шаги
1. ``` javac -d out src/main/java/test/consoleApp/*.java ```
2. ``` jar cfm util.jar manifest.txt -C out . ```
3. ``` java -jar util.jar -f -a -o /some/path -p result_ in1.txt in2.txt in3.txt ```

### Доступные опции и их ограничения

##### Статистика
Доступна двух видов: полная(-f) и краткая(-s). Выводится в терминал. **Без выбора вида отображения статистики запуск не возможен.**

##### Путь до выходных файлов
Указывается после -o, за путь считается текст после опции(за исключением обозначения других опций). При отсутвие текста программа выведет ошибку.

##### Префикс имен выходных файлов
Указывается после -p, за путь считается текст после опции(за исключением обозначения других опций). При отсутвие текста программа выведет ошибку.

##### Режим добавления в существующие файлы
Включается при добавлении опции -a. По умолчанию файлы перезаписываются

##### Входные файлы
Входными фалйами считается все, что идет без указания опции
