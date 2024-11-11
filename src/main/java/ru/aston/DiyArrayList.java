package ru.aston;

public class DiyArrayList<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MIN_CAPACITY = 1;
    private static final double EXTENSION_FACTOR = 1.5;

    private Object[] data;
    private int size;
    private int targetCapacity;

    public DiyArrayList() {
        targetCapacity = DEFAULT_CAPACITY;
        data = new Object[0];
        size = 0;
    }

    public DiyArrayList(int capacity) {
        targetCapacity = capacity > 0 ? capacity : MIN_CAPACITY;
        data = new Object[0];
        size = 0;
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
    }

    public void sort() {

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
}
