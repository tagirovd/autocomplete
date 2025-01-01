# Описание задачи

## Постановка задачи

Требуется разработать консольное Java-приложение (JDK 11), которое позволяет быстро искать аэропорты по заданному свойству. 

### Входные данные
Программа использует данные из файла `airports.csv`, представляющего таблицу аэропортов в формате CSV. Основные характеристики файла:
- **Название аэропорта** находится во **второй колонке**.
- **Другие колонки** могут быть использованы для поиска, их содержимое не важно.

### Логика работы
Поиск выполняется по номерам строк (первая колонка `airports.csv`) в указанной колонке (нумерация начинается с 1). Поиск является **префиксным**. 

**Пример:**
Если указана колонка поиска 2, то поиск строки `Bow` вернет номера: `3600, 4275, 7848`.

### Параметры запуска
Программа запускается с использованием следующих параметров:
- `--data airports.csv` — путь к файлу CSV с данными об аэропортах.
- `--indexed-column-id 3` — колонка, по которой выполняется поиск.
- `--input-file input-path-to-file.txt` — путь к файлу с входными строками поиска. Формат — текстовый файл, каждая строка которого содержит текст для поиска.
  - **Пример содержимого файла**:
    ```
    Bower
    Asa
    Ret
    ```
- `--output-file output-path-to-file.json` — путь к файлу с результатами поиска. Если файл не существует, он будет создан; если существует, он будет перезаписан.

### Формат выходного файла
Результат поиска сохраняется в формате JSON и содержит:
1. `initTime` — время (в миллисекундах) инициализации приложения от запуска до готовности выполнить первый поиск. Включает, например, чтение файла `--input-file`.
2. `result` — массив, где каждый элемент содержит:
   - `search` — строка поиска.
   - `result` — массив номеров строк, соответствующих поиску, отсортированных:
     - **Для строковых колонок** — лексикографически.
     - **Для числовых колонок** — по числовому значению.
   - `time` — время (в миллисекундах), затраченное на выполнение поиска.

**Пример содержимого выходного файла**:
```json
{
  "initTime": 100,
  "result": [
    { "search": "Bower", "result": [1, 2], "time": 10 },
    { "search": "Asa", "result": [8, 4], "time": 10 },
    { "search": "Ret", "result": [5, 100], "time": 10 }
  ]
}
```

## Нефункциональные требования

1. **Перечитывать все строки файла при каждом поиске нельзя.**
   - В том числе запрещается читать только определенную колонку у каждой строки.

2. **Создавать новые файлы или редактировать текущие нельзя.**
   - Также запрещено использовать СУБД.

3. **Хранить весь файл в памяти нельзя.**
   - Это касается как хранения в виде массива байт, так и любой другой структуры, содержащей все данные файла.

4. **Ограничение по памяти:** 
   - Для корректной работы программе требуется не более **7 МБ памяти**.
   - Все запуски `java -jar` должны выполняться с флагом JVM `-Xmx7m`.

5. **Скорость поиска должна быть максимально высокой с учетом вышеуказанных ограничений.**
   - Ориентировочные показатели производительности:
     - Поиск по строке `"Bo"`, который возвращает 68 строк, должен выполняться за **25 мс**.
     - Поиск по строке `"Bower"`, который возвращает 1 строку, должен выполняться за **5 мс**.

6. **Сложность поиска должна быть меньше, чем O(n), где n — количество строк в файле.**

7. **Соблюдение принципов ООП и SOLID.**

8. **Обработка ошибок:**
   - Ошибочные и краевые ситуации должны быть корректно обработаны.

9. **Использование готовых библиотек для парсинга CSV формата запрещено.**
