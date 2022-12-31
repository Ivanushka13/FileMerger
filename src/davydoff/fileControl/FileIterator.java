package davydoff.fileControl;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс итератор из домашнего задания
 */
public final class FileIterator implements Iterator<String> {
    private final BufferedReader reader;

    private String line;

    public FileIterator(String path) {
        line = null;

        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    @Override
    public boolean hasNext() {
        if (line == null) {
            try {
                line = reader.readLine();
                return line != null;
            } catch (IOException exception) {
                throw new UncheckedIOException(exception);
            }
        }
        return true;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            line = null;
            try {
                reader.close();
            } catch (IOException exception) {
                throw new UncheckedIOException(exception);
            }
            throw new NoSuchElementException();
        }
        String curLine = line;
        line = null;
        return curLine;
    }
}

