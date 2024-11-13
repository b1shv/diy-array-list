package ru.aston;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DiyArrayListTest {
    private DiyArrayList<Integer> testList;

    @BeforeEach
    void testListInit() {
        testList = new DiyArrayList<>(5);
        testList.add(22);
        testList.add(14);
        testList.add(27);
        testList.add(11);
        testList.add(6);
    }

    @Test
    void get_returnsElement() {
        assertEquals(22, testList.get(0));
        assertEquals(14, testList.get(1));
        assertEquals(27, testList.get(2));
        assertEquals(11, testList.get(3));
        assertEquals(6, testList.get(4));
    }

    @Test
    void get_throwsException_ifWrongIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> testList.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> testList.get(testList.size()));
    }

    @Test
    void add_addsElementToTheEndOfList() {
        testList.add(10);
        assertEquals(10, testList.get(testList.size() - 1));

        testList.add(2);
        assertEquals(2, testList.get(testList.size() - 1));

        testList.add(100);
        assertEquals(100, testList.get(testList.size() - 1));
    }

    @Test
    void add_addsElementByIndex() {
        testList.add(33, 0);
        testList.add(100, 3);
        testList.add(88, 6);

        DiyArrayList<Integer> expected = diyArrayList(new int[]{33, 22, 14, 100, 27, 11, 88, 6});
        assertEquals(expected, testList);
    }

    @Test
    void remove_removesElementByIndex() {
        testList.remove(4);
        testList.remove(1);
        testList.remove(0);

        DiyArrayList<Integer> expected = diyArrayList(new int[]{27, 11});
        assertEquals(testList.size(), 2);
        assertEquals(expected, testList);
    }

    @Test
    void remove_returnsElement() {
        int element1 = testList.remove(4);
        int element2 = testList.remove(2);
        int element3 = testList.remove(0);

        List<Integer> expected = List.of(6, 27, 22);
        List<Integer> actual = List.of(element1, element2, element3);

        assertEquals(expected, actual);
    }

    @Test
    void clear_removesAllElements() {
        int testListSize = testList.size();
        testList.clear();

        assertEquals(0, testList.size());
        for (int i = 0; i < testListSize; i++) {
            int index = i;
            assertThrows(IndexOutOfBoundsException.class, () -> testList.get(index));
        }
    }

    @Test
    void sort_sortsList() {
        DiyArrayList<Integer> list = diyArrayList(new int[]{9, -5, 6, 8, 7, 6, 1, 2, 3, 5, 4});
        DiyArrayList<Integer> expected = diyArrayList(new int[]{-5, 1, 2, 3, 4, 5, 6, 6, 7, 8, 9});
        list.sort(Integer::compare);

        assertEquals(expected, list);
    }

    DiyArrayList<Integer> diyArrayList(int[] testData) {
        DiyArrayList<Integer> diyArrayList = new DiyArrayList<>();
        for (int i : testData) {
            diyArrayList.add(i);
        }
        return diyArrayList;
    }
}
