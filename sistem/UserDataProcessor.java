package sistem;

import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Pattern;

public class UserDataProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Введите данные в следующем формате, разделенные пробелом: \n" +
                        "Фамилия Имя Отчество дата_рождения номер_телефона пол\n" +
                        "Требования к данным:\n" +
                        "- Фамилия, Имя, Отчество - строки без цифр и специальных символов.\n" +
                        "- Дата рождения - в формате dd.mm.yyyy (например, 31.12.1990).\n" +
                        "- Номер телефона - целое число без пробелов и специальных символов.\n" +
                        "- Пол - один символ: 'f' (женский) или 'm' (мужской).\n" +
                        "Пример: Иванов Иван Иванович 01.01.1990 1234567890 m");
                String input = scanner.nextLine();
                processInput(input);
                break; // Выход из цикла после успешного ввода данных
            } catch (Exception e) {
                System.out.println("-----------------------------------");
                System.out.println("Ошибка: " + e.getMessage() + " Попробуйте еще раз.");
                System.out.println("-----------------------------------");
                // Цикл продолжится, пока данные не будут введены верно
            }
        }
    }

    private static void processInput(String input) throws IOException {
        String[] parts = input.split(" ");
        if (parts.length != 6) {
            throw new IllegalArgumentException(
                    "Вы ввели неверное количество данных. Требуется ввести данные в следующем формате: Фамилия Имя Отчество дата_рождения номер_телефона пол.");
        }

        validateAndProcessData(parts);
    }

    private static void validateAndProcessData(String[] data) throws IOException {
        if (!data[0].matches("[а-яА-ЯёЁa-zA-Z-]+") || !data[1].matches("[а-яА-ЯёЁa-zA-Z-]+")
                || !data[2].matches("[а-яА-ЯёЁa-zA-Z-]+")) {
            throw new IllegalArgumentException("Фамилия, имя и отчество должны содержать только буквы и дефисы.");
        }
        if (!Pattern.matches("\\d{2}\\.\\d{2}\\.\\d{4}", data[3])) {
            throw new IllegalArgumentException("Неверный формат даты рождения. Ожидается формат dd.mm.yyyy.");
        }

        if (!Pattern.matches("\\d+", data[4])) {
            throw new IllegalArgumentException("Номер телефона должен содержать только цифры.");
        }

        if (!data[5].matches("[fm]")) {
            throw new IllegalArgumentException("Пол должен быть указан как 'f' для женского или 'm' для мужского.");
        }

        writeToFIle(data);
    }

    private static void writeToFIle(String[] data) {
        String fileName = data[0] + ".txt";
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName, true), "UTF-8")) {
            writer.write(String.join(" ", data) + "\n");
        } catch (IOException e) {
            System.out.println("Произошла ошибка при записи в файл:");
            e.printStackTrace();
        }
    }
}
