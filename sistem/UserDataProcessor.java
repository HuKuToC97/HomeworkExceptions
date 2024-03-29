package sistem;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class UserDataProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Введите данные: Фамилия Имя Отчество дата_рождения номер_телефона пол (разделенные пробелом):");
        String input = scanner.nextLine();
        processInput(input);
    }

    private static void processInput(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 6) {
            System.out.println(
                    "Вы ввели неверное количество данных. Требуется ввести: Фамилия Имя Отчество дата_рождения номер_телефона пол.");
            return;
        }

        try {
            validateAndProcessData(parts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void validateAndProcessData(String[] data) throws IOException {
        if (!Pattern.matches("\\d{2}\\.\\d{2}\\.\\d{4}", data[3])) {
            throw new IllegalArgumentException("Неверный формат даты рождения. Ожидается формат dd.mm.yyyy.");
        }

        if (!Pattern.matches("\\d+", data[4])) {
            throw new IllegalArgumentException("Номер телефона должен содержать только цифры.");
        }

        if (!data[5].matches("[fm]")) {
            throw new IllegalArgumentException("Пол должен быть указан как 'f' или 'm'.");
        }

        writeToFIle(data);
    }

    private static void writeToFIle(String[] data) throws IOException {
        String fileName = data[0] + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(String.join(" ", data));
            writer.newLine();
        }
    }

}
