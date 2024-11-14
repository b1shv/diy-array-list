package ru.aston;

import java.util.Comparator;

/**
 * Реализация динамического массива, работает с любыми данными ссылочного типа. Для хранения используется массив,
 * расширяющийся при необходимости.
 *
 * <p>Имеет ёмкость – размер массива, в котором хранятся данные. Если массив заполнен, при вставке нового элемента будет
 * произведена алокация – создание нового массива большего размера и перенос данных.
 *
 * <p>Средняя временная сложность вставки элемента в конец списка, метод {@code add}, – O(1), худшая – O(n).
 * Методы {@code size} и {@code get} работают за константное время, {@code remove}, {@code clear} и добавление элемента
 * по индексу имеют линейную сложность.
 *
 * <p>Список не потокобезопасный, для изменения списка в многопоточной среде используйте блоки {@code synchronized}.
 *
 * @param <T> тип элементов, хранящихся в списке
 */
public class DiyArrayList<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MIN_CAPACITY = 1;
    private static final double EXTENSION_FACTOR = 1.5;

    private Object[] data;
    private int size;
    private int targetCapacity;

    private final Sorter sorter;

    /**
     * Создаёт пустой список с ёмкостью по умолчанию – 10.
     * При инициализации списка будет создан пустой массив.
     * Расширение до указанной ёмкости произойдёт при добавлении первого элемента.
     */
    public DiyArrayList() {
        targetCapacity = DEFAULT_CAPACITY;
        data = new Object[0];
        size = 0;
        sorter = new SorterImpl();
    }

    /**
     * Создаёт пустой список указанной изначальной ёмкости.
     * Минимальная изначальная ёмкость списка – 1, при указании меньших значений,
     * списку будет присвоена минимальна ёмкость.
     * <p>
     * При инициализации списка будет создан пустой массив.
     * Расширение до указанной ёмкости произойдёт при добавлении первого элемента.
     *
     * @param capacity изначальная ёмкость списка
     */
    public DiyArrayList(int capacity) {
        targetCapacity = capacity > 0 ? capacity : MIN_CAPACITY;
        data = new Object[0];
        size = 0;
        sorter = new SorterImpl();
    }

    /**
     * Возвращает количество элементов в списке.
     *
     * @return количество элементов в списке
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает элемент по индексу.
     *
     * @param index индекс искомого элемента
     * @return элемент по индексу
     * @throws IndexOutOfBoundsException если в списке нет указанного индекса
     */
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    /**
     * Добавляет элемент в конец списка.
     * Если массив, хранящий элементы, заполнен, перед добавлением элемента производит алокацию.
     *
     * @param element элемент, добавляемый в конец с писка
     */
    public void add(T element) {
        if (data.length == size) {
            extend();
        }

        data[size++] = element;
    }

    /**
     * Вставляет элемент на указанную позицию.
     * Элемент, находящийся на указанной позиции, и все элементы справа от него, сдвигаются на один индекс вправо.
     * Если массив, хранящий элементы, заполнен, перед добавлением элемента производит алокацию.
     *
     * @param element элемент, добавляемый в конец с писка
     * @param index   индекс, по которому добавляется элемент
     * @throws IndexOutOfBoundsException если в списке нет указанного индекса
     */
    public void add(T element, int index) {
        checkIndex(index);

        if (data.length == size) {
            extend();
        }

        shift(false, index);
        data[index] = element;
        size++;
    }

    /**
     * Возвращает элемент по указанному индексу и удаляет его из списка.
     * Все элементы, находящиеся справа от удаляемого, сдвигаются на один индекс влево.
     *
     * @param index индекс удаляемого элемента
     * @return удаляемый элемент
     * @throws IndexOutOfBoundsException если в списке нет указанного индекса
     */
    public T remove(int index) {
        checkIndex(index);

        T element = get(index);
        shift(true, index + 1);
        data[--size] = null;

        return element;
    }

    /**
     * Удаляет все элементы из списка.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    /**
     * Сортирует список.
     *
     * @param comparator компаратор для сравнения элементов
     */
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

    /**
     * Сравнивает списки. При несовпадении ссылок и совпадении классов, сравнивает все элементы.
     * Возвращает {@code true}, если элементы двух списков равны по {@code equals} этих элементов
     * и имеют одинаковый порядок.
     * Ёмкость списков не имеет значения при сравнении.
     *
     * @param o список для сравнения
     * @return {@code true}, если списки равны; {@code false}, если не равны
     */
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

    /**
     * Возвращает хэш-код списка. Ёмкость списка в рассчёт не берётся, поэтому все списки,
     * равные по {@code equals}, будут иметь одинаковый хэш-код.
     *
     * @return хэш-код списка
     */
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (int i = 0; i < size; i++) {
            hashCode = 31 * hashCode + (data[i] == null ? 0 : data[i].hashCode());
        }
        return hashCode;
    }
}
