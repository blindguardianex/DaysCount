package easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Простая реализация счетчика дней до введенной даты
 * Работает также до введения строки "quit"
 */
public class EasyCounter {
    private static final String DATE_FORMAT = "d.M.yyyy";
    private static final String CHECK_PATTERN = "^\\d{2}[.]\\d{2}[.]\\d{4}$";
    private static DateTimeFormatter formatter;

    public static void main(String[] args) {
        formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try (BufferedReader bR = new BufferedReader(new InputStreamReader(System.in))){
            String stringDate;
            do{
                System.out.println("Введите дату рождения в формате dd.mm.yyyy");
                stringDate = bR.readLine();
                if (stringDate==null||stringDate.isBlank()){
                    System.out.println("Введена пустая строка");
                    continue;
                }
                if (!stringDate.matches(CHECK_PATTERN)){
                    System.out.println("Неверный формат даты");
                    continue;
                }
                LocalDate dateBDay = LocalDate.parse(stringDate,formatter);
                LocalDate dateNow = LocalDate.now();
                int dayBefore = dateBDay.getDayOfYear()-dateNow.getDayOfYear();
                if (dayBefore<0){
                    dayBefore+=365;
                }
                System.out.println(dayBefore);
            }
            while (!stringDate.equals("quit"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
