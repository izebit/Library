package ru.izebit.structs.tree;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @author Artem Konovalov
 *         date 4/4/15
 */
public class SegmentTreeTest {

    @Test
    public void testGet() {
        int[] array = new int[100];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }
        SegmentTree tree = new SegmentTree(array);

        int value = tree.getSum(0, 100);
        int checkValue = 0;
        for (int anArray : array) {
            checkValue += anArray;
        }
        assertEquals(value, checkValue);

        value = tree.getSum(20, 20);
        checkValue = array[20];
        assertEquals(value, checkValue);


        value = tree.getSum(78, 92);
        checkValue = 0;
        for (int i = 78; i <= 92; i++) {
            checkValue += array[i];
        }
        assertEquals(value, checkValue);


        value = tree.getSum(66, 99);
        checkValue = 0;
        for (int i = 66; i <= 99; i++) {
            checkValue += array[i];
        }
        assertEquals(value, checkValue);


        value = tree.getSum(22, 42);
        checkValue = 0;
        for (int i = 22; i <= 42; i++) {
            checkValue += array[i];
        }
        assertEquals(value, checkValue);
    }

    @Test
    public void testSet() {
        int[] array = new int[100];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }
        SegmentTree tree = new SegmentTree(array);

        int insertValue = 42;
        tree.set(50, insertValue);
        array[50] = insertValue;
        int value = tree.getSum(0, 100);
        int checkValue = 0;
        for (int anArray : array) {
            checkValue += anArray;
        }
        assertEquals(value, checkValue);

        insertValue = -1;
        array[20] = insertValue;
        tree.set(20, insertValue);
        value = tree.getSum(20, 20);
        assertEquals(value, insertValue);


        insertValue = random.nextInt();
        tree.set(91, insertValue);
        tree.set(92, insertValue);
        tree.set(91, insertValue + 10);
        array[91] = insertValue + 10;
        array[92] = insertValue;
        value = tree.getSum(78, 92);
        checkValue = 0;
        for (int i = 78; i <= 92; i++) {
            checkValue += array[i];
        }
        assertEquals(value, checkValue);
    }
}
