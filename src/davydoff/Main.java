package davydoff;

import davydoff.console.InputHandler;
import davydoff.console.OutputHandler;
import davydoff.fileControl.FileMerger;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Считываем путь к корневой папке с консоли
        String rootPath = InputHandler.getRootDirectory();

        // Создаем объект основного класса
        FileMerger merger = new FileMerger(rootPath);

        // Получение списка всех файлов в директории и их вывод в консоль
        merger.setFiles();
        OutputHandler.printAllFiles(merger.getFiles());

        // Получение упорядоченного списка всех файлов и их вывод в консоль
        merger.getOrderedListOfFiles();
        OutputHandler.printAllOrderedFiles(merger.getFiles());

        // Конкатенация содержимого всех файлов
        String resultString = merger.concatAllFiles();
        OutputHandler.printResultText(resultString);

        // Создание результирующего файла и вывод соответствующего сообщения в консоль
        merger.creatResultFile(resultString);
        OutputHandler.conclusion();
    }
}
