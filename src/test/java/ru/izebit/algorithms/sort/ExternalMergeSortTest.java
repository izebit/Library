package ru.izebit.algorithms.sort;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Artem Konovalov
 *         date 4/1/16
 */
@RunWith(value = Parameterized.class)
public class ExternalMergeSortTest {
    private int frameSize;
    private int countLine;

    private File file;
    private List<Integer> originalArray;

    public ExternalMergeSortTest(int frameSize, int countLine) {
        this.frameSize = frameSize;
        this.countLine = countLine;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[]{10, 100_000});
        data.add(new Object[]{100, 100_000});
        data.add(new Object[]{1_000, 1_000_000});
        data.add(new Object[]{10, 1_000_071});


        return data;
    }

    @Before
    public void prepareFile() throws IOException {
        this.file = File.createTempFile("sort", "tmp");
        originalArray = SortTest.getArray(countLine, Integer.MAX_VALUE);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.file))) {
            for (Integer number : originalArray)
                bw.write(number.toString() + "\n");
        }
    }

    @After
    public void deleteFile() throws IOException {
        Assert.assertTrue(this.file.delete());
    }

    @Test
    public void sortTest() throws IOException {
        ExternalMergeSort<Integer> externalSort = new ExternalMergeSort<>();
        externalSort.sort(file.getAbsolutePath(), frameSize, Integer::valueOf);

        Collections.sort(originalArray);
        List<Integer> sortedList = Files
                .lines(Paths.get(file.getAbsolutePath()))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        for (int i = 0; i < originalArray.size(); i++)
            Assert.assertEquals(originalArray.get(i), sortedList.get(i));

    }
}
