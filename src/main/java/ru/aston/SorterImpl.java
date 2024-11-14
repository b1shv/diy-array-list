package ru.aston;

import java.util.Comparator;
import java.util.Random;

/**
 * Класс-сортировщик, имплементирует интерфейс {@code Sorter}.
 * Единственный публичный метод {@code sort} сортирует массивы любых объектов.
 * Алгоритм – быстрая сортировка in place.
 *
 * <p>Средняя временная сложность быстрой сортировки – O(nlogn),
 * но в худшем случае сортировка может выполняться за O(n^2).
 * Т.к. алгоритм in place производит все манипуляции с данными внутри исходного массива,
 * он имеет константную пространественную сложность.
 */
public class SorterImpl implements Sorter {
    /**
     * Сортирует переданный массив от нулевого элемента до индекса {@code size} не включительно.
     *
     * @param elements   массив с элементами
     * @param size       количество элементов, которые нужно отсортировать
     * @param comparator компаратор, сравнивающий элементы
     * @param <T>        тип элементов
     */
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
