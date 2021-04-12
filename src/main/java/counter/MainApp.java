package counter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Приложение, считающее количество дней до введенной даты
 */
public class MainApp {
    private static DateCounter counter = new DateCounter();;
    private static BufferedReader reader;
    private static String stringDate;

    /**
     * Считывает дату с консоли и выводит в консоль количество дней до введеной даты
     * После введения "quit" роисходит выход из приложения
     * @see MainApp#inputDate()
     * @see DateCounter#getDayBeforeBirthday(String)
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        stringDate = inputDate();
        while (!stringDate.equals("quit")){
            int daysBeforeBirthday = counter.getDayBeforeBirthday(stringDate);
            System.out.println(daysBeforeBirthday);
            stringDate = inputDate();
        }
        reader.close();
    }

    /**
     * Метод, считывающий дату с консоли
     * @return
     * @throws IOException
     */
    private static String inputDate() throws IOException {
        initReader();
        System.out.println("Введите дату рождения:");
        return reader.readLine();
    }

    /**
     * Инициализирует BufferedReader, если он не был инициализирован
     * @see MainApp#reader
     */
    private static void initReader(){
        if (reader==null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
    }
}
