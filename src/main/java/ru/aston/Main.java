package ru.aston;

public class Main {
    public static void main(String[] args) {
        DiyArrayList<Integer> list = new DiyArrayList<>();
        list.add(8);
        list.add(4);
        list.add(5);
        list.add(7);
        list.add(1);
        list.add(5);
        list.add(12);
        list.add(55);
        list.add(11);
        list.add(2);
        list.add(1);
        list.add(3);

        list.sort(Integer::compare);
        System.out.println(list.get(0));
    }
}