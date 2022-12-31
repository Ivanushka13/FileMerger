package davydoff.console;

import java.io.File;
import java.util.Scanner;

/**
 * @author davydoff
 * Класс для контроля ввода корневой директории
 */
public final class InputHandler {
    private static final Scanner IN = new Scanner(System.in);

    /**
     * Получение корректного пути к корневой директории с консоли
     * @return корректный путь к корневой директории
     */
    public static String getRootDirectory() {
        System.out.println("Введите путь к корневой директории");
        System.out.print("-->");

        File directory = new File(IN.nextLine());

        while(!directory.exists()) {
            System.out.println("Неверный ввод, попробуйте еще раз");
            System.out.print("-->");
            directory = new File(IN.nextLine());
        }

        return directory.getPath();
    }


}
