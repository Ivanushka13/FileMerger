package davydoff.fileControl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Класс для получения всех файлов в директории, конкатенации содержимого файлов
 * @author davydoff
 */
public final class FileMerger {
    // корневая папка
    private final File rootDirectory;

    // все тесктовые файлы в корневой папке
    private List<String> files;

    public FileMerger(String rootPath) {
        rootDirectory = new File(rootPath);
    }

    /**
     * Получение всех текстовых файлов по текущему пути
     * @param path путь
     * @return лист файлов по текущему пути
     * @throws IOException .
     */
    private List<String> getFilesFromDirectory(Path path) throws IOException {
        try (Stream<Path> stream = Files.walk(path, Integer.MAX_VALUE)) {
            return stream
                    .map(String::valueOf)
                    .sorted()
                    .filter((line) -> {
                        var file = new File(line);

                        String fileName = file.toString();

                        return !file.isDirectory() && fileName.endsWith(".txt");
                    })
                    .toList();
        }
    }

    /**
     * Вызов метода getFilesFromDirectory для поля files
     * @throws IOException .
     */
    public void setFiles() throws IOException {
        try {
            files = getFilesFromDirectory(Path.of(rootDirectory.getPath()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Получение списка файлов
     * @return список всех текстовых файлов в директории
     */
    public List<String> getFiles() {
        return files;
    }

    /**
     * Получение упорядоченного списка файлов исходя из зависимости
     * переприсваивание поля files к полученному результату
     */
    public void getOrderedListOfFiles() {
        List<String> orderedList = new ArrayList<>();
        for(String path: files) {
            FileIterator iterator = new FileIterator(path);
            List<String> requires = new ArrayList<>();
            boolean hasRequire = false;
            while(iterator.hasNext()) {
                String[] words = iterator.next().split(" ");
                if(Objects.equals(words[0], "require")) {
                    hasRequire = true;
                    String newPath = words[1].replaceAll("'", "");
                    requires.add(newPath);
                }
            }
            if(!hasRequire && !orderedList.contains(path)) {
                orderedList.add(path);
            } else if(!orderedList.contains(path)) {
                int lastIndex = -1;
                for(String require: requires) {
                    if(!orderedList.contains(require)) {
                        orderedList.add(require);
                    }
                    if(orderedList.contains(require) && orderedList.lastIndexOf(require) > lastIndex) {
                        lastIndex = orderedList.lastIndexOf(require);
                    }
                }
                orderedList.add(lastIndex + 1, path);
            } else {
                int index = orderedList.lastIndexOf(path);
                for(String require: requires) {
                    if(!orderedList.contains(require)) {
                        orderedList.add(index, require);
                    } else if(orderedList.contains(require) && orderedList.lastIndexOf(require) > index) {
                        orderedList.remove(require);
                        orderedList.add(index, require);
                    }
                }
            }
        }
        files = orderedList;
    }

    /**
     * Конкатенация содержимого из всех файлов
     * @return строка, содержащая последовательный текст всех файлов
     */
    public String concatAllFiles() {
        StringBuilder result = new StringBuilder();
        for(String path: files) {
            FileIterator iterator = new FileIterator(path);
            while(iterator.hasNext()) {
                result.append(iterator.next());
            }
        }
        return result.toString();
    }

    /**
     * Создание результирующего файла в директории с текстом всех других файлов
     * @param resultText сконкатенированный текст всех файлов
     * @throws IOException .
     */
    public void creatResultFile(String resultText) throws IOException {
        try {
            String resultPath = rootDirectory.getPath() + "/result.txt";
            Files.createFile(Path.of(resultPath));
            File resultFile = new File(resultPath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(resultPath));
            writer.write(resultText);
            writer.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}