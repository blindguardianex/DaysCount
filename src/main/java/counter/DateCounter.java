package counter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, непосредственно считающий количество дней до определенной даты
 */
public class DateCounter {
    private static Map<String, String>patterns;
    private DateTimeFormatter formatter;
    
    public DateCounter() {
        initDatePatterns();
    }

    /**
     * Проверяет строку на пустоту
     * @see DateCounter#checkDateForEmpty(String)
     * Ищет подходящий паттерн среди установленных по-умолчанию
     * @see DateCounter#findValidPattern(String)
     * Считает и возвращает количество дней до даты
     * @see DateCounter#getDays(String)
     * @param stringDate
     * @return
     */
    public int getDayBeforeBirthday(String stringDate){
        int daysBeforeBirthday=0;
        try {
            checkDateForEmpty(stringDate);
            findValidPattern(stringDate);
            daysBeforeBirthday = getDays(stringDate);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Проверьте корректность введеной даты: " + stringDate);
        }
        return daysBeforeBirthday;
    }

    /**
     * Считает количество дней, оставшихся до переданной даты.
     * Если дата в этом году уже прошла (разница между датами отрицательна)
     * прибавляет 365 дней. Високосные года игнорируются.
     * Возвращает количество дней, округленное в бОльшую сторону (пока день не
     * закончился, он засчитывается целым)
     * @param stringDate
     * @return
     */
    private int getDays(String stringDate){
        LocalDate date = LocalDate.parse(stringDate, formatter);
        LocalDate dateNow = LocalDate.now();
        int dayBefore = date.getDayOfYear()-dateNow.getDayOfYear();
        if (dayBefore<0){
            dayBefore+=365;
        }
        return dayBefore;
    }

    /**
     * Проверяет входную строку на null и пустоту
     * @param stringDate
     * @return
     * @throws IllegalArgumentException
     */
    private boolean checkDateForEmpty(String stringDate) throws IllegalArgumentException{
        if (stringDate==null||stringDate.isBlank()){
            throw new IllegalArgumentException("Введена пустая строка");
        }
        return true;
    }

    /**
     * Ищет подходящий паттерн для парсинга даты и инициализирует DateTimeFormatter
     * @see DateCounter#formatter
     * @param stringDate
     * @return
     * @throws IllegalArgumentException - возникает в случае, если ни один их паттернов
     *                                  не подходит для парсинга переданной даты
     */
    private boolean findValidPattern(String stringDate) throws IllegalArgumentException{
        for (Map.Entry entry:patterns.entrySet()){
            if(stringDate.matches(String.valueOf(entry.getKey()))){
                formatter = DateTimeFormatter.ofPattern(String.valueOf(entry.getValue()));
                return true;
            }
        }
        throw new IllegalArgumentException("Введеный формат даты не поддерживается");
    }

    /**
     * Инициализирует ассоциативный массив с шаблонами парсинга даты.
     * Поддерживаются следующие форматы даты:
     * 11.08.1994
     * 11/08/1994
     * 1994/08/11
     * 1994-08-11
     */
    private void initDatePatterns(){
        patterns=new HashMap<>();
        patterns.put("^\\d{2}[.]\\d{2}[.]\\d{4}$", "d.M.yyyy");
        patterns.put("^\\d{2}[/]\\d{2}[/]\\d{4}$", "d/M/yyyy");
        patterns.put("^\\d{4}[/]\\d{2}[/]\\d{2}$", "yyyy/M/d");
        patterns.put("^\\d{4}[-]\\d{2}[-]\\d{2}$", "yyyy-M-d");
    }
}
