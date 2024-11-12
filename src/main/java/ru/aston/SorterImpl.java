package ru.aston;

import java.util.Comparator;
import java.util.Random;

public class SorterImpl implements Sorter {
    @Override
    public <T> void sort(T[] elements, int size, Comparator<T> comparator) {
        Random random = new Random();
        quickSort(elements, 0, size - 1, random, comparator);
    }

    private <T> void quickSort(T[] elements, int start, int end, Random random, Comparator<T> comparator) {
        if (start < end) {
            T pivot = elements[random.nextInt(end - start) + start];
            int left = start;
            int right = end;

            while (left <= right) {
                if (comparator.compare(elements[left], pivot) < 0) {
                    left++;
                }
                if (comparator.compare(elements[right], pivot) > 0) {
                    right--;
                }
                if (left <= right && comparator.compare(elements[left], pivot) >= 0
                        && comparator.compare(elements[right], pivot) <= 0) {
                    T temp = elements[left];
                    elements[left] = elements[right];
                    elements[right] = temp;
                    left++;
                    right--;
                }
            }
            quickSort(elements, start, right, random, comparator);
            quickSort(elements, left, end, random, comparator);
        }
    }
}
