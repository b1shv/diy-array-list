package ru.aston;

import java.util.Comparator;

public interface Sorter {
    <T> void sort(T[] elements, int size, Comparator<T> comparator);
}
