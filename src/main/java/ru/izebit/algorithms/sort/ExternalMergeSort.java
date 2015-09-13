package ru.izebit.algorithms.sort;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Внешняя сортировка слиянием,
 * предназначена для сортировки файлов которые не умещаются целиком в оперативную память
 * the algorithm implementation is not thread-safe
 *
 * @see <a href="http://www.algolib.narod.ru/Sort/PolyPhaseMerge.html">Внешняя сортировка</a>
 * Created by Artem Konovalov on 9/13/15.
 */
public class ExternalMergeSort<T extends Comparable<T>> {
    private static final String FIRST_FILE_NAME = "a.txt";
    private static final String SECOND_FILE_NAME = "b.txt";
    private static final String THIRD_FILE_NAME = "c.txt";
    private static final String FOUR_FILE_NAME = "d.txt";

    private static BufferedWriter getWriter(boolean isDirect, boolean isNextFirst) throws IOException {
        String fileName;
        if (isDirect) {
            fileName = isNextFirst ? THIRD_FILE_NAME : FOUR_FILE_NAME;
        } else {
            fileName = isNextFirst ? FIRST_FILE_NAME : SECOND_FILE_NAME;
        }

        return new BufferedWriter(new FileWriter(fileName, true));
    }

    private static void prepareFile(boolean isCreate) throws IOException {
        File[] files = new File[]{
                new File(FIRST_FILE_NAME),
                new File(SECOND_FILE_NAME),
                new File(THIRD_FILE_NAME),
                new File(FOUR_FILE_NAME)};
        if (isCreate) {
            for (File file : files) {
                if (!file.exists() && !file.createNewFile()) {
                    throw new IOException("can't create  additional file");
                }
            }
        } else {
            for (File file : files) {
                if (!file.delete()) {
                    throw new IOException("can't delete additional file");
                }
            }
        }
    }

    private static void clearFile(String... fileNames) throws IOException {
        for (String fileName : fileNames) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                bw.write("");
            }
        }
    }

    /**
     * сортировка содежимого файла
     *
     * @param fileName  название файла
     * @param frameSize размер буфера, загружаемого в оперативную память
     * @param transform функция преобразующая строку в объекты, которые нужно упорядочить
     * @throws IOException в случае, если нельза создать дополнительные файлы, или прочитать содержимое файла
     */
    public void sort(String fileName, int frameSize, Function<String, T> transform) throws IOException {
        prepareFile(true);
        //сортировка
        long lineCount = divideAndSort(fileName, frameSize, transform);

        //слияние
        merge(fileName, frameSize, lineCount, transform);
        prepareFile(false);
    }

    private void merge(String fileName, int frameSize, long lineCount, Function<String, T> transform) throws IOException {
        int bufferSize = frameSize;

        boolean isDirect = true, isNextFirst = true;

        while (bufferSize / 2 <= lineCount) {
            //слияние двух файлов
            Path firstInputFile = Paths.get(isDirect ? FIRST_FILE_NAME : THIRD_FILE_NAME);
            Path secondInputFile = Paths.get(isDirect ? SECOND_FILE_NAME : FOUR_FILE_NAME);
            clearFile(isDirect
                    ? new String[]{THIRD_FILE_NAME, FOUR_FILE_NAME}
                    : new String[]{SECOND_FILE_NAME, FIRST_FILE_NAME});

            try (Stream<String> firstStream = Files.lines(firstInputFile);
                 Stream<String> secondStream = Files.lines(secondInputFile)) {

                Iterator<String> firstIterator = firstStream.iterator();
                Iterator<String> secondIterator = secondStream.iterator();
                while (!Thread.currentThread().isInterrupted()) {

                    try (BufferedWriter writer = getWriter(isDirect, isNextFirst)) {
                        int firstPartCount = 0;
                        int secondPartCount = 0;
                        String currentLine, firstLine = null, secondLine = null;
                        isNextFirst = !isNextFirst;
                        while (firstIterator.hasNext() || secondIterator.hasNext()) {
                            if (firstPartCount < bufferSize && firstLine == null) {
                                firstLine = firstIterator.hasNext() ? firstIterator.next() : null;
                                firstPartCount++;
                            }
                            if (secondPartCount < bufferSize && secondLine == null) {
                                secondLine = secondIterator.hasNext() ? secondIterator.next() : null;
                                secondPartCount++;
                            }
                            if (firstLine == null && secondLine == null) {
                                break;
                            }

                            if (firstLine == null) {
                                currentLine = secondLine;
                                secondLine = null;
                            } else if (secondLine == null) {
                                currentLine = firstLine;
                                firstLine = null;
                            } else {
                                int result = transform.apply(firstLine).compareTo(transform.apply(secondLine));
                                if (result == 0) {
                                    currentLine = firstLine + "\n" + secondLine;
                                    firstLine = secondLine = null;
                                } else if (result > 0) {
                                    currentLine = secondLine;
                                    secondLine = null;
                                } else {
                                    currentLine = firstLine;
                                    firstLine = null;
                                }
                            }

                            writer.write(currentLine);
                            writer.newLine();
                        }
                        if (firstLine != null || secondLine != null) {
                            writer.write(firstLine == null ? secondLine : firstLine);
                            writer.newLine();
                        }
                        if (firstPartCount == 0 && secondPartCount == 0) break;
                    }
                }
            }

            //копирование результатов в исходный файл
            if (bufferSize > lineCount) {
                Path inputPath;
                if (isDirect) {
                    inputPath = isNextFirst ? Paths.get(THIRD_FILE_NAME) : Paths.get(FOUR_FILE_NAME);
                } else {
                    inputPath = isNextFirst ? Paths.get(FIRST_FILE_NAME) : Paths.get(SECOND_FILE_NAME);
                }
                Files.copy(inputPath, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);

            }
            bufferSize *= 2;
            isDirect = !isDirect;
        }
    }

    /**
     * создаем два файла с отсортированными последовательностями
     *
     * @param fileName  исходный файл
     * @param frameSize размер буфера, загружаемого в оперативную память
     * @return количество строк, содержащихся в исходном файле
     * @throws IOException в случае если нельзя прочесть содержимое файла или создать дополнительные файлы
     */
    private long divideAndSort(String fileName, int frameSize, Function<String, T> transform) throws IOException {
        boolean isNextFirstWriter = true;
        List<T> buffer = new ArrayList<>(frameSize);
        long lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
             BufferedWriter firstWriter = new BufferedWriter(new FileWriter(FIRST_FILE_NAME));
             BufferedWriter secondWriter = new BufferedWriter(new FileWriter(SECOND_FILE_NAME))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                buffer.add(transform.apply(line));
                if (buffer.size() == frameSize) {
                    Collections.sort(buffer);
                    BufferedWriter writer = isNextFirstWriter ? firstWriter : secondWriter;
                    for (T number : buffer) {
                        writer.write(number.toString());
                        writer.newLine();
                    }

                    isNextFirstWriter = !isNextFirstWriter;
                    buffer.clear();
                }
            }
            if (buffer.size() != 0) {
                Collections.sort(buffer);
                BufferedWriter writer = isNextFirstWriter ? firstWriter : secondWriter;
                for (T number : buffer) {
                    if (number == null) break;
                    writer.write(number.toString());
                    writer.newLine();
                }
            }
        }
        return lineCount;
    }
}
