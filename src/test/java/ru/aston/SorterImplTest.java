package ru.aston;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SorterImplTest {
    private final SorterImpl sorter = new SorterImpl();

    @Test
    void sort_sortsStrings() {
        String[] strings = {"mno", "def", "abc", "jkl", "ghi"};
        String[] expectedStrings = {"abc", "def", "ghi", "jkl", "mno"};
        String[] expectedStringsReversed = {"mno", "jkl", "ghi", "def", "abc"};

        sorter.sort(strings, strings.length, String::compareTo);
        for (int i = 0; i < strings.length; i++) {
            assertEquals(expectedStrings[i], strings[i]);
        }

        sorter.sort(strings, strings.length, Comparator.reverseOrder());
        for (int i = 0; i < strings.length; i++) {
            assertEquals(expectedStringsReversed[i], strings[i]);
        }
    }

    @Test
    void sort_sortsIntegers() {
        Integer[] integers = {9, 8, 5, 2, 3, 4, 4, 6, 1, 2, 7, 7, 10};
        Integer[] expectedIntegers = {1, 2, 2, 3, 4, 4, 5, 6, 7, 7, 8, 9, 10};
        Integer[] expectedIntegersReversed = {10, 9, 8, 7, 7, 6, 5, 4, 4, 3, 2, 2, 1};

        sorter.sort(integers, integers.length, Integer::compare);
        for (int i = 0; i < integers.length; i++) {
            assertEquals(expectedIntegers[i], integers[i]);
        }

        sorter.sort(integers, integers.length, Comparator.reverseOrder());
        for (int i = 0; i < integers.length; i++) {
            assertEquals(expectedIntegersReversed[i], integers[i]);
        }
    }
}
