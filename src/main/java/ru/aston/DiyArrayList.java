package ru.aston;

import java.util.Comparator;

public class DiyArrayList<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MIN_CAPACITY = 1;
    private static final double EXTENSION_FACTOR = 1.5;

    private Object[] data;
    private int size;
    private int targetCapacity;

    private final Sorter sorter;

    public DiyArrayList() {
        targetCapacity = DEFAULT_CAPACITY;
        data = new Object[0];
        size = 0;
        sorter = new SorterImpl();
    }

    public DiyArrayList(int capacity) {
        targetCapacity = capacity > 0 ? capacity : MIN_CAPACITY;
        data = new Object[0];
        size = 0;
        sorter = new SorterImpl();
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    public void add(T element) {
        if (data.length == size) {
            extend();
        }

        data[size++] = element;
    }

    public void add(T element, int index) {
        checkIndex(index);

        if (data.length == size) {
            extend();
        }

        shift(false, index);
        data[index] = element;
        size++;
    }

    public T remove(int index) {
        checkIndex(index);

        T element = get(index);
        shift(true, index + 1);
        data[--size] = null;

        return element;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    public void sort(Comparator<T> comparator) {
        sorter.sort((T[]) data, size, comparator);
    }

    private void extend() {
        Object[] dataExtended = new Object[targetCapacity];
        System.arraycopy(data, 0, dataExtended, 0, size);

        data = dataExtended;
        targetCapacity = targetCapacity > MIN_CAPACITY ? (int) (targetCapacity * EXTENSION_FACTOR) : targetCapacity + 1;
    }

    private void shift(boolean toTheLeft, int firstIndex) {
        int shift = toTheLeft ? -1 : 1;
        System.arraycopy(data, firstIndex, data, firstIndex + shift, size - firstIndex);
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(String.format("Index %s is out of bounds for length %s", index, size));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiyArrayList<?> that = (DiyArrayList<?>) o;
        if (size != that.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!data[i].equals(that.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (int i = 0; i < size; i++) {
            hashCode = 31 * hashCode + (data[i] == null ? 0 : data[i].hashCode());
        }
        return hashCode;
    }
}
