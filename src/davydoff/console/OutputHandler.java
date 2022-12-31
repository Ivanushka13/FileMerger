package davydoff.console;

import java.util.List;

/**
 * Класс для вывода сообщений в консоль
 */
public final class OutputHandler {

    /**
     * Вывод в консоль всех файлов в директории
     * @param files все текстовые файлы в директории
     */
    public static void printAllFiles(List<String> files) {
        System.out.println("Всего файлов в директории " + files.size() + ":");
        for(String file: files) {
            System.out.println(file.substring(file.lastIndexOf("/") + 1));
        }
    }

    /**
     * Вывод в консоль упорядоченного списка всех файлов в директории
     * @param files упорядоченный список файлов
     */
    public static void printAllOrderedFiles(List<String> files) {
        System.out.println("Упорядоченная последовательность файлов:");
        for(String file: files) {
            System.out.println(file.substring(file.lastIndexOf("/") + 1));
        }
    }

    /**
     * Вывод в консоль сконкатенированной строки
     * @param result результирующая строка
     */
    public static void printResultText(String result) {
        System.out.println("Сконкатенированный текст:");
        System.out.println(result);
    }

    /**
     * Вывод в консоль заключительного сообщения
     */
    public static void conclusion() {
        System.out.println("В корневой директории создан файл result.txt.");
        System.out.println("В него была записана результирующая строка.");
    }
}
